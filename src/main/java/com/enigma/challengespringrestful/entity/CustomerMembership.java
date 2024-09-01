package com.enigma.challengespringrestful.entity;

import com.enigma.challengespringrestful.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.CUSTOMER_MEMBERSHIP)
@Builder
public class CustomerMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "duration", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime duration;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "discount_percentage", precision = 10, scale = 2, nullable = false)
    private BigDecimal discountPercentage;

    @Column(name = "monthly_fee", precision = 10, scale = 2, nullable = false)
    private BigDecimal monthlyFee;

    @OneToMany(mappedBy = "customerMembership")
    @JsonManagedReference
    private List<TransactionDetail> transactionDetails;

    @OneToMany(mappedBy = "customerMembership")
    @JsonManagedReference
    private List<Customer> customers;

}