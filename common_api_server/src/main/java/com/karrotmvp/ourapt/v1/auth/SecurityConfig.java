package com.karrotmvp.ourapt.v1.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
    // TODO Auto-generated method stub
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.formLogin().disable();
    http.addFilterBefore(new KarrotOAuthFilter(karrotOAuthProvider), UsernamePasswordAuthenticationFilter.class);
  }


  
}
