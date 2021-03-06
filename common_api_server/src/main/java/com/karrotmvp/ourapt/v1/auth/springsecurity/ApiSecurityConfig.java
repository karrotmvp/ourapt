package com.karrotmvp.ourapt.v1.auth.springsecurity;

import com.karrotmvp.ourapt.v1.common.exception.event.ExceptionOccurEventPublisher;
import com.karrotmvp.ourapt.v1.log.event.AccessEventPublisher;
import com.karrotmvp.ourapt.v1.log.vo.LogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.naming.AuthenticationException;

@Order(1)
@Configuration
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
  // Apply Spring Security FilterChain for all API

  private Logger logger = LoggerFactory.getLogger(getClass());

  private final ExceptionOccurEventPublisher exceptionEventPublisher;
  private final AccessEventPublisher accessEventPublisher;

  public static final String[] AUTHORIZATION_CHECK_EXCLUSION_PATTERNS = new String[]{
    "/api/v1/app/**",
    "/api/v1/oauth/karrot"
  };

  public ApiSecurityConfig(ExceptionOccurEventPublisher exceptionEventPublisher, AccessEventPublisher accessEventPublisher) {
    this.exceptionEventPublisher = exceptionEventPublisher;
    this.accessEventPublisher = accessEventPublisher;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .antMatcher("/api/v1/**")
      .httpBasic().disable()
      .csrf().disable()
      .formLogin().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .addFilterBefore(this.karrotOAuthFilter(), UsernamePasswordAuthenticationFilter.class)
      .cors()
      .and()
      .authorizeRequests()
      // Try to authenticate this pattern, but I don't check the authority.
      .requestMatchers(CorsUtils::isPreFlightRequest)
      .permitAll()
      .antMatchers(AUTHORIZATION_CHECK_EXCLUSION_PATTERNS)
      .permitAll()
      .anyRequest().authenticated()
      .and()
      // Handle failure to authority check
      .exceptionHandling().authenticationEntryPoint((request, response, exception) -> {
        String firstException = String.valueOf(request.getAttribute("firstExceptionMessage"));
        accessEventPublisher.publish(
          LogVo.builder()
            .method(request.getMethod())
            .path(request.getRequestURI())
            .status(401)
            .regionId(request.getHeader("Region-Id"))
            .userId(null)
            .build()
        );
        exceptionEventPublisher.publish(
          request.getMethod() + " " + request.getRequestURI() + " " + 401,
          new AuthenticationException(firstException)
        );
        response.sendError(
          HttpStatus.UNAUTHORIZED.value(),
          firstException);
      });
  }

  public GenericFilterBean karrotOAuthFilter() throws Exception {
    return new KarrotOAuthFilter(authenticationManagerBean());
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("*");
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
