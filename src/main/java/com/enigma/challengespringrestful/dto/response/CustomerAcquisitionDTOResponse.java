package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.Customer;
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
public class CustomerAcquisitionDTOResponse implements Serializable {

    private String id;
    private String name;
    private List<Customer> customers;

    @Override
    public String toString() {
        return "CustomerAcquisitionDTOResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", customers=" + customers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerAcquisitionDTOResponse that = (CustomerAcquisitionDTOResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(customers, that.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, customers);
    }
}
