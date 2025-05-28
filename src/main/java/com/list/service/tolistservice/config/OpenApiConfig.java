package com.list.service.tolistservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server()
                        .url("https://api.hfmf.ru")  // Основной продакшен URL
                        .description("Production Server"))
                .addServersItem(new Server()
                        .url("http://localhost:8080")  // Локальный для разработки
                        .description("Local Development"));
    }
}
