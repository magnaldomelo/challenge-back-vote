package com.southsystem.challengebackvote.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private static final String api_package = "com.southsystem.challengebackvote.application.controller";

    @Bean
    public Docket assemblyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName("1.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage(api_package))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo("1.0"));
    }

    private ApiInfo apiInfo(String version) {
        return new ApiInfoBuilder().title("Challenge Back Vote")
                .description("Challenge Back Vote")
                .version(version)
                .build();
    }
}
