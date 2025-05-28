package com.list.service.tolistservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Your API Title")
                        .version("1.0")
                        .description("API Description"))
                .addServersItem(new Server()
                        .url("https://api.hfmf.ru")
                        .description("Production"))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Local"));
    }
}
