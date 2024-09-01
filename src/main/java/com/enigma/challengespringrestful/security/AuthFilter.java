package com.enigma.challengespringrestful.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.enigma.challengespringrestful.dao.JWTDAO;
import com.enigma.challengespringrestful.dao.UserAccountDAO;
import com.enigma.challengespringrestful.dto.response.JwtClaims;
import com.enigma.challengespringrestful.entity.UserAccount;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthFilter extends OncePerRequestFilter {
    static final String AUTH_HEADER = "Authorization";
    private final JWTDAO jwtDAO;
    private final UserAccountDAO userAccountDAO;

    @Override
    @NonNull
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader(AUTH_HEADER);

            if (bearerToken != null && jwtDAO.verifyJwtToken(bearerToken)) {
                JwtClaims decodeJwt = jwtDAO.getClaimsByToken(bearerToken);

                UserAccount userAccountBySub = userAccountDAO.getByUserID(decodeJwt.getUserAccountID());

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAccountBySub.getUsername(), null, userAccountBySub.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }


        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
