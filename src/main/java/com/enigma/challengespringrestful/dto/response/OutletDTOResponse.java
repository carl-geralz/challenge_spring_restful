package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.TransactionDetail;
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
public class OutletDTOResponse implements Serializable {

    private String id;
    private String name;
    private String location;
    private List<TransactionDetail> transactionDetails;

    @Override
    public String toString() {
        return "OutletDTOResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", transactionDetails=" + transactionDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutletDTOResponse that = (OutletDTOResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(location, that.location) &&
                Objects.equals(transactionDetails, that.transactionDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, transactionDetails);
    }
}
