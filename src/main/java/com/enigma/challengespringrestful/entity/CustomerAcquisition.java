package com.enigma.challengespringrestful.entity;

import com.enigma.challengespringrestful.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.CUSTOMER_ACQUISITION)
@Builder
public class CustomerAcquisition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "customerAcquisition")
    @JsonManagedReference
    private List<Customer> customers;

}