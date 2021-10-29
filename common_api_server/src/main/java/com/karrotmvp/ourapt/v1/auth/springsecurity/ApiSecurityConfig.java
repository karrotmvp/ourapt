package com.karrotmvp.ourapt.v1.auth.springsecurity;

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

@Order(1)
@Configuration
@EnableWebSecurity(debug = false)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
    // Apply Spring Security FilterChain for all API

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String[] AUTHORIZATION_CHECK_EXCLUSION_PATTERNS = new String[]{
            "/api/v1/app/**",
            "/api/v1/oauth/karrot",
            "/api/v1/apartment**",
            "/api/v1/apartment/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
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
                    response.sendError(
                            HttpStatus.UNAUTHORIZED.value(),
                            String.valueOf(request.getAttribute("firstExceptionMessage")));
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
