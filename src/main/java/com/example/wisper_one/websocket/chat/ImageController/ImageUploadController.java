package com.example.wisper_one.websocket.chat.ImageController;

import com.example.wisper_one.utils.Exception.BusinessException;
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

/**
 * File: ImageUploadController
 * Author: [周玉诚]
 * Date: 2026/1/12
 * Description:
 */
@RestController
@RequestMapping("/api/upload")
public class ImageUploadController {


    @PostMapping("/image")
    public Map<String,Object> uploadImage(@RequestParam("file") MultipartFile file)
            throws IOException {

        if (file.isEmpty()) {
            throw new BusinessException("未传输图片~~");
        }

        //提取图片后缀   requireNonNull确保上传文件不为空，是空直接报异常
        // getOriginalFilename为获取原文件名称    substring是截取字符
        String ext = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));

        if (!ext.equals(".png") && !ext.equals(".jpg") && !ext.equals(".jpeg")) {
            throw new BusinessException("请上传 png / jpg / jpeg 格式图片");
        }

        String filename = UUID.randomUUID().toString().replace("-", "") + ext;


        String baseDir = "E:/wisperimage/";
        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }


        File saveFile = new File(baseDir + filename);
        file.transferTo(saveFile);


        Map<String, Object> result = new HashMap<>();
        result.put("url", "/uploads/images/" + filename);
        return result;


    }



}
