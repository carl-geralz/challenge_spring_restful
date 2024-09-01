package com.enigma.challengespringrestful.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.enigma.challengespringrestful.entity.CustomerMembership}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerMembershipDTORequest implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    String id;
    @NotNull
    @NotEmpty
    @NotBlank
    String name;
    @NotNull
    @FutureOrPresent
    LocalDateTime startDate;
    @NotNull
    Boolean isActive;
    @NotNull
    @Min(20)
    @Max(50)
    @Positive
    BigDecimal discountPercentage;
    @NotNull
    @PositiveOrZero
    BigDecimal monthlyFee;
}