package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.CustomerAcquisition;
import com.enigma.challengespringrestful.entity.CustomerMembership;
import com.enigma.challengespringrestful.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTOResponse implements Serializable {

    private String id;
    private String name;
    private String phoneNumber;
    private List<Transaction> transactions;
    private CustomerAcquisition customerAcquisition;
    private CustomerMembership customerMembership;

    @Override
    public String toString() {
        return "CustomerDTOResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", transactions=" + transactions +
                ", customerAcquisition=" + customerAcquisition +
                ", customerMembership=" + customerMembership +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTOResponse that = (CustomerDTOResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(transactions, that.transactions) &&
                Objects.equals(customerAcquisition, that.customerAcquisition) &&
                Objects.equals(customerMembership, that.customerMembership);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumber, transactions, customerAcquisition, customerMembership);
    }
}
