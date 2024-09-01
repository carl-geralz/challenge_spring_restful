package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.CustomerMembership;
import com.enigma.challengespringrestful.entity.Outlet;
import com.enigma.challengespringrestful.entity.Product;
import com.enigma.challengespringrestful.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.enigma.challengespringrestful.entity.TransactionDetail}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDetailDTOResponse implements Serializable {
    String id;
    BigDecimal invoiceQty;
    BigDecimal vatPercentage;
    BigDecimal invoicePrice;
    Outlet outlet;
    Transaction transaction;
    Product product;
    CustomerMembership customerMembership;
}