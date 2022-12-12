/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Swagger configuration class
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-12-7
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    /**
     * The above function is used to configure the swagger documentation.
     *
     * @return A Docket object is being returned.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ideas2it.ideameds"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    /**
     * It provides the information about the API.
     *
     * @return ApiInfo object is being returned.
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Ideameds",
                "This api provides Users to purchase a medicine",
                "1.0",
                "T&C",
                new Contact("Parthasarathy", "www.ideas2it.com", "parthasarathy.elumalai@ideas2it.com"),
                "", "", Collections.emptyList());
    }
}
