package com.enigma.challengespringrestful.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@Component
public class JWTTokenProvider {

    private final String jwtSecret;
    private final String issuer;
    private final long jwtExpiration;

    public JWTTokenProvider(@Value("${handsonpractice.jwt.secret_key}") String jwtSecret,
                            @Value("${handsonpractice.jwt.issuer}") String issuer,
                            @Value("${handsonpractice.jwt.expirationInSecond}") long jwtExpiration) {
        this.jwtSecret = jwtSecret;
        this.issuer = issuer;
        this.jwtExpiration = jwtExpiration;
    }

    public String createToken(String userId, String[] roles) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        return JWT.create()
                .withSubject(userId)
                .withClaim("roles", Collections.singletonList(roles))
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(Instant.now().plusSeconds(jwtExpiration)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
    }
}
