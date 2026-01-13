package com.example.wisper_one;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@MapperScan({"com.example.wisper_one.Login.mapper",
        "com.example.wisper_one.Login.usercode.mapper",
        "com.example.wisper_one.websocket.chat.mapper",
        "com.example.wisper_one.websocket.chat_group.mapper"
})



/**
 *  MyBatis 启动或第一次使用 Mapper启动 ApplicationContext 的时候。

    MyBatis会解析所有MapperXML文件把它们都加载进内存。

 一旦某个MapperXML有问题（比如 resultMap 指向不存在的 id 或者 SQL 写错）整个 MyBatis 配置解析会失败。
 **/
public class WisperOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(WisperOneApplication.class, args);
    }
}
