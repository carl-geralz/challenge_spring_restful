package com.enigma.challengespringrestful.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.enigma.challengespringrestful.entity.Product}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTORequest implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    String id;
    @NotNull
    @NotEmpty
    @NotBlank
    String name;
    @NotNull
    @Positive
    BigDecimal inventoryQty;
    @NotNull
    @PositiveOrZero
    BigDecimal purchasePrice;
    BigDecimal retailPrice;
    String description;
    @NotNull
    @PositiveOrZero
    BigDecimal salesPrice;
}