package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.CustomerAcquisition;
import com.enigma.challengespringrestful.entity.CustomerMembership;
import com.enigma.challengespringrestful.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.enigma.challengespringrestful.entity.Customer}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTOResponse implements Serializable {
    String id;
    String name;
    String phoneNumber;
    List<Transaction> transactions;
    CustomerAcquisition customerAcquisition;
    CustomerMembership customerMembership;
}