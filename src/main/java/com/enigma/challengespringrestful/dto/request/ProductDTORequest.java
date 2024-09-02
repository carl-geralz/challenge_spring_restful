package com.enigma.challengespringrestful.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTORequest implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    private String id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal inventoryQty;

    @NotNull
    @PositiveOrZero
    private BigDecimal purchasePrice;

    private BigDecimal retailPrice;

    private String description;

    @NotNull
    @PositiveOrZero
    private BigDecimal salesPrice;
}
