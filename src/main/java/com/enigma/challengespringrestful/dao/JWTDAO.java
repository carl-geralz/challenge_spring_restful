package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.dto.response.JWTClaims;
import com.enigma.challengespringrestful.entity.UserAccount;
import org.springframework.stereotype.Component;


@Component
public interface JWTDAO {
    String generateToken(UserAccount userAccount);

    boolean verifyJwtToken(String token);

    JWTClaims getClaimsByToken(String token);
}
