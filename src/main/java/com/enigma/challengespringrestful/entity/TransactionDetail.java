package com.enigma.challengespringrestful.entity;

import com.enigma.challengespringrestful.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.TRANSACTION_DETAIL)
@Builder
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "invoice_qty", precision = 10, scale = 2, nullable = false)
    private BigDecimal invoiceQty;

    @Column(name = "vat_percentage", precision = 10, scale = 2)
    @ColumnDefault("0.00")
    private BigDecimal vatPercentage;

    @Column(name = "invoice_price", precision = 10, scale = 2)
    private BigDecimal invoicePrice;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "outlet_id", nullable = false)
    private Outlet outlet;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "membership_id", nullable = false)
    private CustomerMembership customerMembership;

}