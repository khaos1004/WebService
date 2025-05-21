package com.WebProject.WebService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI jcodeLabOpenAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url("http://localhost:8080").description("로컬 서버")))
                .info(new Info()
                        .title("JcodeLab API 문서")
                        .description("JcodeLab 프로젝트의 Swagger 기반 API 명세서입니다.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("JcodeLab 개발팀")
                                .url("https://jcodelab.com")
                                .email("contact@jcodelab.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("JcodeLab API")
                .pathsToMatch("/**")
                .build();
    }
}
