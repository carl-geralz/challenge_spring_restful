package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.enigma.challengespringrestful.entity.Transaction}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTOResponse implements Serializable {
    String id;
    LocalDateTime invoiceDate;
    Customer customer;
}