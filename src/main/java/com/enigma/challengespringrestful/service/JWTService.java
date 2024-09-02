package com.enigma.challengespringrestful.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.challengespringrestful.dto.response.JWTClaims;
import com.enigma.challengespringrestful.entity.UserAccount;
import com.enigma.challengespringrestful.security.JWTTokenProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    private final JWTTokenProvider tokenProvider;

    public JWTService(JWTTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public String generateToken(UserAccount userAccount) {
        return tokenProvider.createToken(userAccount.getId(),
                userAccount.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new));
    }

    public boolean verifyJwtToken(String bearerToken) {
        try {
            String token = parseJwt(bearerToken);
            tokenProvider.verifyToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public JWTClaims getClaimsByToken(String bearerToken) {
        try {
            String token = parseJwt(bearerToken);
            DecodedJWT decodedJWT = tokenProvider.verifyToken(token);
            return JWTClaims.builder()
                    .userAccountID(decodedJWT.getSubject())
                    .roles(decodedJWT.getClaim("roles").asList(String.class))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    private String parseJwt(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
