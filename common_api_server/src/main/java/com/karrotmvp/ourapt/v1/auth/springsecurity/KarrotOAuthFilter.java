package com.karrotmvp.ourapt.v1.auth.springsecurity;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class KarrotOAuthFilter extends GenericFilterBean {

    private AuthenticationManager authenticationManager;

    public KarrotOAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private String extractBearerToken(HttpServletRequest request) throws AuthenticationServiceException {
        String authHeader = request.getHeader("Authorization");
        // TODO: Refactor, 에러전파
        if (authHeader == null) {
            String msg = "Fail to find 'Authorization' header from request.";
            throw new AuthenticationServiceException(msg);
        }
        if (!authHeader.startsWith("Bearer ")) {
            String msg = "Authorization header has invalid BearerToken.";
            throw new AuthenticationServiceException(msg);
        }
        return authHeader;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // generate unauthenticated token.
        // Later, this token is validated by KarrotOAuthProvider via AuthenticationManager
        try {
            String karrotAccessToken = extractBearerToken((HttpServletRequest) request);
            KarrotAuthenticationToken unAuthorizedAuthentication = new KarrotAuthenticationToken(karrotAccessToken);
            Authentication authorizedAuthentication = authenticationManager.authenticate(unAuthorizedAuthentication);
            this.successfulAuthentication(request, response, chain, authorizedAuthentication);
        } catch (AuthenticationException e) {
            this.unsuccessfulAuthentication(request, response, chain, e);
        }
    }

    private void successfulAuthentication(ServletRequest request, ServletResponse response, FilterChain chain, Authentication authentication)
            throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private void unsuccessfulAuthentication(ServletRequest request, ServletResponse response, FilterChain chain, AuthenticationException exception)
            throws ServletException, IOException {
        request.setAttribute("firstExceptionMessage", exception.getMessage());
        SecurityContextHolder.clearContext();
        chain.doFilter(request, response);
    }
}
