package com.enigma.challengespringrestful.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.enigma.challengespringrestful.entity.Discount}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountDTORequest implements Serializable {
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
    @Future
    LocalDateTime endDate;
    @NotNull
    Boolean isActive;
    @NotNull
    @Max(30)
    @PositiveOrZero
    BigDecimal discountPercentage;
}