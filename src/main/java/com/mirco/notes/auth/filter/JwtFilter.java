package com.mirco.notes.auth.filter;

import com.mirco.notes.auth.model.entities.SystemUser;
import com.mirco.notes.auth.model.repository.ISystemUserRepository;
import com.mirco.notes.auth.services.jwt.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * JWT filter that runs once for every HTTP request.
 *
 * This filter:
 * - Skips authentication routes like /api/auth
 * - Reads the JWT token from the Authorization header
 * - Extracts the user email from the token
 * - Validates that the token belongs to an existing user and is still valid
 * - Sets the Spring Security context with the authenticated user
 *
 * If the token is missing or invalid, the request continues without authentication
 * and is handled by other filters or security rules.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final ISystemUserRepository iSystemUserRepository;
    private final IJwtService iJwtService;
    private final UserDetailsService userDetailsService;

    public JwtFilter(ISystemUserRepository iSystemUserRepository, IJwtService iJwtService, UserDetailsService userDetailsService) {
        this.iSystemUserRepository = iSystemUserRepository;
        this.iJwtService = iJwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().contains("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ") ) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String email = iJwtService.extractUsername(jwt);

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(email == null || authentication != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        request.getAttribute("email");

        final Optional<SystemUser> systemUserEntity = iSystemUserRepository.findSystemUserByEmail(email);

        if(systemUserEntity.isPresent()) {
            final boolean isTokenValid = iJwtService.isTokenValid(jwt, systemUserEntity.get());

            if(isTokenValid) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
