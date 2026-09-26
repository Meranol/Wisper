package com.example.wisper_one.userUpdata.ImageController;

import com.example.wisper_one.utils.Exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")

public class ImageUploadController {

    // 从配置文件读取上传目录，如果没有则使用默认路径
    @Value("${file.upload.dir:/home/admin/uploads}")
    private String baseUploadDir;

    @PostMapping("/image")
    public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file,
                                           @RequestParam("type") String type)
            throws IOException {

        if (file.isEmpty()) {
            throw new BusinessException("未传输图片~~");
        }

        // 提取图片后缀
        String ext = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));

        if (!ext.equals(".png") && !ext.equals(".jpg") && !ext.equals(".jpeg")) {
            throw new BusinessException("请上传 png / jpg / jpeg 格式图片");
        }

        String filename = UUID.randomUUID().toString().replace("-", "") + ext;

        // 根据类型创建子目录
        String subDir;
        switch (type) {
            case "user":
                subDir = "user/";
                break;
            case "group":
                subDir = "group/";
                break;
            case "chat":
                subDir = "chat/";
                break;
            case "temp":
                subDir = "temp/";
                break;
            default:
                throw new BusinessException("非法上传类型");
        }

        String fullDir = baseUploadDir + "/" + subDir;
        File dir = new File(fullDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File saveFile = new File(fullDir + filename);
        file.transferTo(saveFile);

        Map<String, Object> result = new HashMap<>();
        // 返回前端可访问的 URL（通过代理）
        result.put("url", "/api/uploads/" + type + "/" + filename);
        return result;
    }
}