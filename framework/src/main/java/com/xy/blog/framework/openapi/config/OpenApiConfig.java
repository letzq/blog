package com.xy.blog.framework.openapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 文档配置。
 * 统一维护 Swagger 页面展示的标题、版本、联系人等基础信息。
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI blogOpenApi() {
        // 构建博客后台接口文档的基础元信息。
        return new OpenAPI()
            .info(new Info()
                .title("Blog Admin API")
                .version("1.0.0")
                .description("Blog backend API documentation")
                .contact(new Contact()
                    .name("wn915.cn")
                    .url("https://wn915.cn")
                    .email("489572770@qq.com"))
                .license(new License()
                    .name("wn915.cn")
                    .url("https://wn915.cn")));
    }
}
