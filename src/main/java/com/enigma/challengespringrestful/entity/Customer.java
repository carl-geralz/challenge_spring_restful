package com.enigma.challengespringrestful.entity;

import com.enigma.challengespringrestful.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.CUSTOMER)
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Transaction> transactions;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_acquisition_id")
    private CustomerAcquisition customerAcquisition;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_membership_id")
    private CustomerMembership customerMembership;

    @Column(name = "status")
    private Boolean status;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id", unique = true)
    private UserAccount userAccount;
}
