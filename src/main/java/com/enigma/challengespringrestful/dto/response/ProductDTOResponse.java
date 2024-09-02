package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.Discount;
import com.enigma.challengespringrestful.entity.TransactionDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTOResponse implements Serializable {
    private String id;
    private String name;
    private BigDecimal inventoryQty;
    private BigDecimal purchasePrice;
    private BigDecimal retailPrice;
    private String description;
    private BigDecimal salesPrice;
    private List<TransactionDetail> transactionDetails;
    private Discount discount;
}
