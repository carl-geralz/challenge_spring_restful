package com.enigma.challengespringrestful.dto.response;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JWTClaims {

    private String userAccountID;
    private List<String> roles;

    @Override
    public String toString() {
        return "JWTClaims{" +
                "userAccountID='" + userAccountID + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JWTClaims jwtClaims = (JWTClaims) o;
        return Objects.equals(userAccountID, jwtClaims.userAccountID) &&
                Objects.equals(roles, jwtClaims.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAccountID, roles);
    }
}
