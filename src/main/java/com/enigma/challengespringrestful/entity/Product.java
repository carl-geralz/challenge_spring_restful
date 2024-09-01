package com.enigma.challengespringrestful.entity;

import com.enigma.challengespringrestful.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.PRODUCT)
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "inventory_qty", precision = 10, scale = 4, nullable = false)
    private BigDecimal inventoryQty;

    @Column(name = "purchase_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal purchasePrice;

    @Column(name = "retail_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal retailPrice;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "sales_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal salesPrice;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<TransactionDetail> transactionDetails;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "discount_id")
    private Discount discount;

}