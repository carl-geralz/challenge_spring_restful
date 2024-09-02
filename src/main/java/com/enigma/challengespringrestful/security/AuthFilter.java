package com.enigma.challengespringrestful.security;

import com.enigma.challengespringrestful.dao.JWTDAO;
import com.enigma.challengespringrestful.dao.UserAccountDAO;
import com.enigma.challengespringrestful.dto.response.JWTClaims;
import com.enigma.challengespringrestful.entity.UserAccount;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthFilter extends OncePerRequestFilter {
    static final String AUTH_HEADER = "Authorization";
    private final JWTDAO jwtDAO;
    private final UserAccountDAO userAccountDAO;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader(AUTH_HEADER);

            if (bearerToken != null && jwtDAO.verifyJwtToken(bearerToken)) {
                JWTClaims decodeJwt = jwtDAO.getClaimsByToken(bearerToken);

                if (decodeJwt != null) {
                    UserAccount userAccountBySub = userAccountDAO.getByUserID(decodeJwt.getUserAccountID());

                    if (userAccountBySub != null) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAccountBySub.getUsername(), null, userAccountBySub.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } else {
                        log.warn("UserAccount not found for ID: {}", decodeJwt.getUserAccountID());
                    }
                } else {
                    log.warn("JWTClaims could not be decoded from token");
                }
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication", e);
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
