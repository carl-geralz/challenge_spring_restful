package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.Customer;
import com.enigma.challengespringrestful.entity.TransactionDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerMembershipDTOResponse implements Serializable {

    private String id;
    private String name;
    private LocalDateTime startDate;
    private Boolean isActive;
    private BigDecimal discountPercentage;
    private BigDecimal monthlyFee;
    private List<TransactionDetail> transactionDetails;
    private List<Customer> customers;

    @Override
    public String toString() {
        return "CustomerMembershipDTOResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", isActive=" + isActive +
                ", discountPercentage=" + discountPercentage +
                ", monthlyFee=" + monthlyFee +
                ", transactionDetails=" + transactionDetails +
                ", customers=" + customers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerMembershipDTOResponse that = (CustomerMembershipDTOResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(discountPercentage, that.discountPercentage) &&
                Objects.equals(monthlyFee, that.monthlyFee) &&
                Objects.equals(transactionDetails, that.transactionDetails) &&
                Objects.equals(customers, that.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, isActive, discountPercentage, monthlyFee, transactionDetails, customers);
    }
}
