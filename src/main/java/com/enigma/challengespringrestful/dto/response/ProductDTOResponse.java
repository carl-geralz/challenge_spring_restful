package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.Discount;
import com.enigma.challengespringrestful.entity.TransactionDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
public class ProductDTOResponse implements Serializable {
    String id;
    String name;
    BigDecimal inventoryQty;
    BigDecimal purchasePrice;
    BigDecimal retailPrice;
    String description;
    BigDecimal salesPrice;
    List<TransactionDetail> transactionDetails;
    Discount discount;
}