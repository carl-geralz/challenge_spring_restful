package com.enigma.challengespringrestful.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class TransactionDTORequest implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    String id;
    @NotNull
    LocalDateTime invoiceDate;
}