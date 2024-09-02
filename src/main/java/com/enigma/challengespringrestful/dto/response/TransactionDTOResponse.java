package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTOResponse implements Serializable {
    private String id;
    private LocalDateTime invoiceDate;
    private Customer customer;
}
