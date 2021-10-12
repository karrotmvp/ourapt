package com.karrotmvp.ourapt.v1.auth.springsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public GenericFilterBean customFilter() throws Exception {
        return new KarrotOAuthFilter(authenticationManagerBean());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                // Never make SecurityFilterChain for this pattern.
                .antMatchers(HttpMethod.GET,
                        "/error",
                        "/favicon.ico",
                        "/swagger/**",
                        "/swagger-ui**",
                        "/swagger-ui/**",
                        "/swagger-resources/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(this.customFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                // Try to authenticate this pattern, but I don't check the authority.
                .antMatchers(
                        "/api/v1/app/**",
                        "/api/v1/oauth/karrot",
                        "/api/v1/preopen/voting/count"
                )
                .permitAll()
                .anyRequest().authenticated()
                .and()
                // Handle failure to authority check
                .exceptionHandling().authenticationEntryPoint((request, response, exception) -> {
                    response.sendError(
                            HttpStatus.UNAUTHORIZED.value(),
                            String.valueOf(request.getAttribute("firstExceptionMessage")));
                });
//                .accessDeniedHandler()

    }
}
