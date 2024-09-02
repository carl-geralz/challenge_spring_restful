package com.enigma.challengespringrestful.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerMembershipDTORequest implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    private String id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @FutureOrPresent
    private LocalDateTime startDate;

    @NotNull
    private Boolean isActive;

    @NotNull
    @Min(20)
    @Max(50)
    @Positive
    private BigDecimal discountPercentage;

    @NotNull
    @PositiveOrZero
    private BigDecimal monthlyFee;
}
