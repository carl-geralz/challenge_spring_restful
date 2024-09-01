package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.TransactionDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.enigma.challengespringrestful.entity.Outlet}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutletDTOResponse implements Serializable {
    String id;
    String name;
    String location;
    List<TransactionDetail> transactionDetails;
}