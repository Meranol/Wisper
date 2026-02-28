package com.example.wisper_one.RedPacket.service.servicelmpl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.RedPacket.POJO.DO.RedPacketDO;
import com.example.wisper_one.RedPacket.POJO.DTO.GrabRedPacketRequestDTO;
import com.example.wisper_one.RedPacket.POJO.DTO.GrabRedPacketResponseDTO;
import com.example.wisper_one.RedPacket.POJO.DTO.SendRedPacketRequestDTO;
import com.example.wisper_one.RedPacket.POJO.DTO.SendRedPacketResponseDTO;
import com.example.wisper_one.RedPacket.POJO.PO.RedPacketPO;
import com.example.wisper_one.RedPacket.POJO.PO.RedPacketRecordPO;
import com.example.wisper_one.RedPacket.mapper.RedPacketMapper;
import com.example.wisper_one.RedPacket.mapper.RedPacketRecordMapper;
import com.example.wisper_one.RedPacket.service.RedPacketService;
import com.example.wisper_one.useraccount.PO.UserAccountPO;
import com.example.wisper_one.useraccount.mapper.UserAccountMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class RedPacketServiceImpl implements RedPacketService {

    private final Random random = new Random();

    @Resource
    private final RedPacketMapper redPacketMapper;
    @Resource
    private RedPacketRecordMapper recordMapper;
    @Resource
    private UserAccountMapper accountMapper;

    @Resource
    private UserMapper userMapper;

    public RedPacketServiceImpl(RedPacketMapper redPacketMapper) {
        this.redPacketMapper = redPacketMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GrabRedPacketResponseDTO grab(GrabRedPacketRequestDTO requestDTO) {

        Long redPacketId = requestDTO.getRedPacketId();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String userCode = userMapper.selectCodeByUname(name);

        RedPacketPO po = redPacketMapper.selectById(redPacketId);
        if (po == null) throw new BusinessException("红包不存在");

        RedPacketDO packet = new RedPacketDO();
        BeanUtils.copyProperties(po, packet);

        if (!packet.canGrab()) throw new BusinessException("红包已无效");

        if (recordMapper.exists(redPacketId, userCode)) throw new BusinessException("你已抢过该红包");

        BigDecimal amount = calculateAmount(packet);

        int updated = redPacketMapper.updateWithVersion(
                redPacketId,
                packet.getRemainCount() - 1,
                packet.getRemainAmount().subtract(amount),
                packet.getVersion()
        );
        if (updated == 0) throw new BusinessException("抢红包失败，请稍后再试");

        UserAccountPO accountPO = accountMapper.selectByUserCode(userCode);
        if (accountPO == null) throw new BusinessException("账户不存在");

        int accUpdated = accountMapper.updateBalanceWithVersion(userCode, amount, accountPO.getVersion());
        if (accUpdated == 0) throw new BusinessException("账户更新失败，请重试");

        RedPacketRecordPO recordPO = new RedPacketRecordPO();
        recordPO.setRedPacketId(redPacketId);
        recordPO.setUserCode(userCode);
        recordPO.setAmount(amount);
        recordPO.setCreatedAt(LocalDateTime.now());
        recordMapper.insert(recordPO);

        GrabRedPacketResponseDTO responseDTO = new GrabRedPacketResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setAmount(amount);
        responseDTO.setMsg("抢到红包啦！");
        return responseDTO;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SendRedPacketResponseDTO send(SendRedPacketRequestDTO requestDTO) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String userCode = userMapper.selectCodeByUname(name);

        if (requestDTO.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("红包金额必须大于0");
        }

        if (requestDTO.getTotalCount() <= 0) {
            throw new BusinessException("红包数量必须大于0");
        }

        if (requestDTO.getType() == 2 && requestDTO.getTotalAmount()
                .compareTo(new BigDecimal("0.01")
                        .multiply(BigDecimal.valueOf(requestDTO.getTotalCount()))) < 0) {
            throw new BusinessException("拼手气红包每份最少0.01元");
        }

        // ===== 2️⃣ 查询账户 =====
        UserAccountPO accountPO = accountMapper.selectByUserCode(userCode);
        if (accountPO == null) {
            throw new BusinessException("账户不存在");
        }

        if (accountPO.getBalance().compareTo(requestDTO.getTotalAmount()) < 0) {
            throw new BusinessException("账户余额不足，无法发红包");
        }

        int accUpdated = accountMapper.updateBalanceWithVersion(
                userCode,
                requestDTO.getTotalAmount().negate(), // 扣钱
                accountPO.getVersion()
        );

        if (accUpdated == 0) {
            throw new BusinessException("账户更新失败，请重试");
        }

        accountMapper.insertAccountRecord(
                userCode,
                requestDTO.getTotalAmount().negate(), // 负数表示支出
                "发红包扣款",
                null,
                LocalDateTime.now()
        );

        RedPacketPO po = new RedPacketPO();
        po.setUserCode(userCode);
        po.setTotalAmount(requestDTO.getTotalAmount());
        po.setRemainAmount(requestDTO.getTotalAmount());
        po.setTotalCount(requestDTO.getTotalCount());
        po.setRemainCount(requestDTO.getTotalCount());
        po.setType(requestDTO.getType());
        po.setStatus(1); // 进行中
        po.setExpireTime(LocalDateTime.now().plusSeconds(10)); // 10秒后过期
        po.setVersion(0);
        po.setCreatedAt(LocalDateTime.now());
        po.setUpdatedAt(LocalDateTime.now());

        int rows = redPacketMapper.insert(po);

        if (rows != 1) {
            throw new BusinessException("发红包失败");
        }

        // ===== 6️⃣ 返回结果 =====
        SendRedPacketResponseDTO response = new SendRedPacketResponseDTO();
        response.setSuccess(true);
        response.setRedPacketId(po.getId()); // 自增回填
        response.setMsg("发红包成功");

        return response;
    }

    /**
     * type = 1 普通红包，平均分
     * type = 2 拼手气红包，随机金额
     */
    private BigDecimal calculateAmount(RedPacketDO packet) {
        BigDecimal amount;
        if (packet.getType() == 1) {
            amount = packet.getRemainAmount()
                    .divide(BigDecimal.valueOf(packet.getRemainCount()), 2, RoundingMode.DOWN);
        } else { // 拼手气红包
            int remainCount = packet.getRemainCount();
            BigDecimal remainAmount = packet.getRemainAmount();
            if (remainCount == 1) {
                amount = remainAmount;
            } else {
                double max = remainAmount
                        .subtract(BigDecimal.valueOf(remainCount - 1).multiply(new BigDecimal("0.01")))
                        .doubleValue();
                double min = 0.01;
                double rand = min + (max - min) * random.nextDouble();
                amount = new BigDecimal(rand).setScale(2, RoundingMode.DOWN);

                // 防止小于0
                if (amount.compareTo(remainAmount) > 0) amount = remainAmount;
            }
        }
        return amount;
    }
}