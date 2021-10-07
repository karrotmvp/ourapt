package com.karrotmvp.ourapt.v1.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug=false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final KarrotOAuthProvider karrotOAuthProvider;


  public SecurityConfig(KarrotOAuthProvider karrotOAuthProvider) {
    this.karrotOAuthProvider = karrotOAuthProvider; 
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers(HttpMethod.GET,
            "/v1/app/health-check",
            "/v1/app/api-doc",
            "/**",
            "/swagger/**",
            "/swagger-ui/",
            "/swagger-resources/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable()
        .csrf().disable()
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
        .authorizeRequests()
        .antMatchers("/v1", "/swagger-ui/**").permitAll()
        .anyRequest().authenticated()
      .and()
      //.exceptionHandling().authenticationEntryPoint((request, response, exception) -> {
      //   logger.info("authentication error");
      // });
        .addFilterBefore(new KarrotOAuthFilter(karrotOAuthProvider), UsernamePasswordAuthenticationFilter.class);
  }
}
