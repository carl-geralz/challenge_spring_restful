package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.CustomerMembership;
import com.enigma.challengespringrestful.entity.Outlet;
import com.enigma.challengespringrestful.entity.Product;
import com.enigma.challengespringrestful.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDetailDTOResponse implements Serializable {
    private String id;
    private BigDecimal invoiceQty;
    private BigDecimal vatPercentage;
    private BigDecimal invoicePrice;
    private Outlet outlet;
    private Transaction transaction;
    private Product product;
    private CustomerMembership customerMembership;
}
