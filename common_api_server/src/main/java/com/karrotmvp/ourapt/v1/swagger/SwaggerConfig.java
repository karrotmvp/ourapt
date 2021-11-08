package com.karrotmvp.ourapt.v1.swagger;

import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.auth.springsecurity.ApiSecurityConfig;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


@Configuration
@EnableWebMvc
public class SwaggerConfig {

  @Bean
//  @Profile({"!production"})
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .securityContexts(List.of(this.securityContexts()))
      .securitySchemes(List.of(this.apiKey()))
      .ignoredParameterTypes(CurrentUser.class)
      .ignoredParameterTypes(RequestHeader.class)
      .useDefaultResponseMessages(false)
      .globalResponses(HttpMethod.GET, Arrays.asList(
        new ResponseBuilder().code("200").description("status 가 'SUCCESS' 가 아닌경우").build(),
        new ResponseBuilder().code("400").description("파라미터 입력값의 형식이 틀림").build(),
        new ResponseBuilder().code("401").description("인증 실패").build(),
        new ResponseBuilder().code("500").description("서버 측에서 예상치 못한 에러").build()
      ))
//      .globalRequestParameters(defaultGlobalParameters())
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
      Arrays.stream(ApiSecurityConfig.AUTHORIZATION_CHECK_EXCLUSION_PATTERNS)
        .noneMatch((pattern) -> pathMatcher.match(pattern, oc.requestMappingPattern()));
    return SecurityContext.builder()
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

  private List<RequestParameter> defaultGlobalParameters() {
    List<RequestParameter> headers = new ArrayList<>();
    headers.add(new RequestParameterBuilder()
      .name("Instance-Id")
      .description("앱 인스턴스 ID")
      .in(ParameterType.HEADER)
      .required(true)
      .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
      .build());
    headers.add(new RequestParameterBuilder()
      .name("Region-Id")
      .description("리전 ID")
      .in(ParameterType.HEADER)
      .required(true)
      .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
      .build());
    return headers;
  }


  private SecurityScheme apiKey() {
    // Make common API Key scheme named "KarrotAccessToken"
    return new ApiKey("KarrotAccessToken", HttpHeaders.AUTHORIZATION, In.HEADER.name());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("우리아파트 API 문서")
      .description("")
      .version("1.0")
      .build();
  }
}

