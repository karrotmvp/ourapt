package com.karrotmvp.ourapt.v1.auth.springsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String[] AUTHORIZATION_CHECK_EXCLUSION_PATTERNS = new String[]{
            "/api/v1/app/**",
            "/api/v1/oauth/karrot",
            "/api/v1/apartment/**"
    };

    public GenericFilterBean customFilter() throws Exception {
        return new KarrotOAuthFilter(authenticationManagerBean());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                // Never make SecurityFilterChain for this pattern.
                .antMatchers(HttpMethod.GET,
                        "/error",
                        "/favicon.ico",
                        "/api/v1/docs",
                        "/v2/api-docs",
                        "/swagger-ui/**",
                        "/swagger-resources**",
                        "/swagger-resources/**",
                        "/admin**",
                        "/admin/**"
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
