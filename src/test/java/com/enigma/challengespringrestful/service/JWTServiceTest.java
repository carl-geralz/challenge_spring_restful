package com.enigma.challengespringrestful.service;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.challengespringrestful.constant.ConstantUserRole;
import com.enigma.challengespringrestful.dto.response.JWTClaims;
import com.enigma.challengespringrestful.entity.UserAccount;
import com.enigma.challengespringrestful.entity.UserRole;
import com.enigma.challengespringrestful.security.JWTTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JWTServiceTest {

    @Mock
    private JWTTokenProvider tokenProvider;

    @InjectMocks
    private JWTService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateToken() {
        UserRole userRole = mock(UserRole.class);
        when(userRole.getRole()).thenReturn(ConstantUserRole.valueOf("ROLE_USER"));

        UserAccount userAccount = UserAccount.builder()
                .id("test-id")
                .roles(Collections.singletonList(userRole))
                .build();

        String token = "jwt-token";
        when(tokenProvider.createToken(anyString(), any())).thenReturn(token);

        String generatedToken = jwtService.generateToken(userAccount);

        assertEquals(token, generatedToken);
        verify(tokenProvider, times(1)).createToken(anyString(), any());
    }

    @Test
    void testVerifyJwtToken_ValidToken() {
        String token = "Bearer valid-token";
        when(tokenProvider.verifyToken(anyString())).thenReturn(mock(DecodedJWT.class));

        boolean isValid = jwtService.verifyJwtToken(token);

        assertTrue(isValid);
        verify(tokenProvider, times(1)).verifyToken(anyString());
    }

    @Test
    void testVerifyJwtToken_InvalidToken() {
        String token = "Bearer invalid-token";
        when(tokenProvider.verifyToken(anyString())).thenThrow(new RuntimeException("Invalid Token"));

        boolean isValid = jwtService.verifyJwtToken(token);

        assertFalse(isValid);
        verify(tokenProvider, times(1)).verifyToken(anyString());
    }

    @Test
    void testGetClaimsByToken_ValidToken() {
        String token = "Bearer valid-token";
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(decodedJWT.getSubject()).thenReturn("test-id");
        when(decodedJWT.getClaim("roles")).thenReturn(mock(Claim.class));
        when(decodedJWT.getClaim("roles").asList(String.class)).thenReturn(Collections.singletonList("ROLE_USER"));
        when(tokenProvider.verifyToken(anyString())).thenReturn(decodedJWT);

        JWTClaims claims = jwtService.getClaimsByToken(token);

        assertNotNull(claims);
        assertEquals("test-id", claims.getUserAccountID());
        assertTrue(claims.getRoles().contains("ROLE_USER"));
        verify(tokenProvider, times(1)).verifyToken(anyString());
    }


    @Test
    void testGetClaimsByToken_InvalidToken() {
        String token = "Bearer invalid-token";
        when(tokenProvider.verifyToken(anyString())).thenThrow(new RuntimeException("Invalid Token"));

        JWTClaims claims = jwtService.getClaimsByToken(token);

        assertNull(claims);
        verify(tokenProvider, times(1)).verifyToken(anyString());
    }
}
