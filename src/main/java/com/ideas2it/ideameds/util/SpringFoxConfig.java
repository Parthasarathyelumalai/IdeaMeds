package com.ideas2it.ideameds.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

/**
 * Swagger configuration class
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-12-7
 */
@Configuration
@EnableSwagger2
@Component
public class SpringFoxConfig {

    private static final String BASIC_AUTH = "basicAuth";
    private static final String BEARER_AUTH = "Bearer";

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
                .build().apiInfo(apiInfo()).securitySchemes(securitySchemes()).securityContexts(List.of(securityContext()));
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

    /**
     * It creates a list of security schemes that are to be used.
     *
     * @return A list of security schemes.
     */
    private List<SecurityScheme> securitySchemes() {
        return List.of(new ApiKey(BEARER_AUTH, "Authorization", "header"));
    }

    /**
     *  This function creates a security context that uses the bearerAuthReference() function to create a security
     * reference that is used to secure all paths
     *
     * @return A SecurityContext object.
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(List.of(bearerAuthReference())).forPaths(PathSelectors.ant("/**")).build();
    }

    /**
     * It creates a security reference with the name "Bearer" and an empty array of authorization scopes
     *
     * @return A SecurityReference object.
     */
    private SecurityReference bearerAuthReference() {
        return new SecurityReference(BEARER_AUTH, new AuthorizationScope[0]);
    }
}
