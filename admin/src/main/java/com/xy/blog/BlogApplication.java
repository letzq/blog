package com.xy.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 博客后台启动类。
 * 负责启动 Spring Boot 应用、扫描 MyBatis Mapper，并开启缓存和异步能力。
 */
@MapperScan("com.xy.blog.system.mapper")
@EnableCaching
@EnableAsync
@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}