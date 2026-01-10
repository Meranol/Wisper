package com.example.wisper_one.Login.usercode.service;

import com.example.wisper_one.Login.usercode.mapper.UserCodeMapper;
import com.example.wisper_one.Login.usercode.UserCodeSeq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * File: UserCodeService
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
@Service
public class UserCodeService {

    @Autowired
    private UserCodeMapper userCodeDao;

    @Transactional
    public String generateUserCode() {
        int year = java.time.LocalDateTime.now().getYear();

        UserCodeSeq seq = userCodeDao.findByYear(year);

        if (seq == null) {
            seq = new UserCodeSeq();
            seq.setYear(year);
            seq.setLastSerial(1);
            userCodeDao.insert(seq);
        } else {
            int newSerial = seq.getLastSerial() + 1;
            seq.setLastSerial(newSerial);
            userCodeDao.update(seq);
        }

        return String.format("U%d%05d", seq.getYear(), seq.getLastSerial());
    }
}
