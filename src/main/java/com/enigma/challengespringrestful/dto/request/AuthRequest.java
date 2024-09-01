package com.enigma.challengespringrestful.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

///**
// * DTO for {@link com.enigma.challengesprintrestful.entity.UserRole}
// */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    private String username;
    @NotNull
    @NotBlank
    @NotEmpty
    private String password;
}
