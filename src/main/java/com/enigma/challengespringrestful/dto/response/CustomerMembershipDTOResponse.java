package com.enigma.challengespringrestful.dto.response;

import com.enigma.challengespringrestful.entity.Customer;
import com.enigma.challengespringrestful.entity.TransactionDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.enigma.challengespringrestful.entity.CustomerMembership}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerMembershipDTOResponse implements Serializable {
    String id;
    String name;
    LocalDateTime startDate;
    Boolean isActive;
    BigDecimal discountPercentage;
    BigDecimal monthlyFee;
    List<TransactionDetail> transactionDetails;
    List<Customer> customers;
}