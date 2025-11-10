package com.riwi.codeup.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ríwi CodeUp Academic System API")
                        .version("1.0.0")
                        .description("Sistema académico completo para gestión de coders, team leaders y clanes")
                        .contact(new Contact()
                                .name("Ríwi CodeUp Team")
                                .email("support@riwi.codeup")
                                .url("https://riwi.io"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}