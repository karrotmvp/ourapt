package com.karrotmvp.ourapt.v1.swagger;

import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.auth.springsecurity.SecurityConfig;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    @Profile({"!production"})
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(List.of(this.securityContexts()))
                .securitySchemes(List.of(this.apiKey()))
                .ignoredParameterTypes(CurrentUser.class)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.karrotmvp.ourapt.v1"))
                .paths(PathSelectors.any())
                .build();
    }

    private SecurityContext securityContexts() {
        // Make SecurityContext including the below SecurityReference
        AntPathMatcher pathMatcher = new AntPathMatcher();
        Predicate<OperationContext> securityExclusionPathSelector = (oc) ->
                Arrays.stream(SecurityConfig.AUTHORIZATION_CHECK_EXCLUSION_PATTERNS)
                        .noneMatch((pattern) -> pathMatcher.match(pattern, oc.requestMappingPattern()));
        return  SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .operationSelector(securityExclusionPathSelector)
                        .build();
    }

    private List<SecurityReference> defaultAuth() {
        // Make the SecurityReference for the "KarrotAccessToken" scheme
        AuthorizationScope[] authScopes = new AuthorizationScope[]{
                new AuthorizationScope("global", "accessEverything")
        };
        return List.of(new SecurityReference("KarrotAccessToken", authScopes));
    }

    private SecurityScheme apiKey() {
        // Make common API Key scheme named "KarrotAccessToken"
        return new ApiKey("KarrotAccessToken", HttpHeaders.AUTHORIZATION, In.HEADER.name());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Our APT")
                .description("Our APT common_api service")
                .version("1.0")
                .build();
    }
}

