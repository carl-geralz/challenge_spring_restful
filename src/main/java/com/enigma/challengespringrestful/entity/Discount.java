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
@Table(name = ConstantTable.DISCOUNT)
@Builder
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "discount_percentage", precision = 10, scale = 2, nullable = false)
    private BigDecimal discountPercentage;

    @OneToMany(mappedBy = "discount")
    @JsonManagedReference
    private List<Product> products;

}