package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountDTOResponse implements Serializable {
    String id;
    String name;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Boolean isActive;
    BigDecimal discountPercentage;
    List<Product> products;
}
