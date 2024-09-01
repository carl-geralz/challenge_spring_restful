package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.dto.response.JwtClaims;
import com.enigma.challengespringrestful.entity.UserAccount;

public interface JWTDAO {
    String generateToken(UserAccount userAccount);

    boolean verifyJwtToken(String token);

    JwtClaims getClaimsByToken(String token);
}
