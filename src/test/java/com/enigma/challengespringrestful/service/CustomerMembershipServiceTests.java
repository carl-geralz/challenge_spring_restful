package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.dto.request.CustomerMembershipDTORequest;
import com.enigma.challengespringrestful.entity.CustomerMembership;
import com.enigma.challengespringrestful.repository.CustomerMembershipRepository;
import com.enigma.challengespringrestful.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CustomerMembershipServiceTests {

    @Mock
    private CustomerMembershipRepository repository;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private CustomerMembershipService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomerMembership() {
        CustomerMembershipDTORequest request = new CustomerMembershipDTORequest("1", "Gold", LocalDateTime.now(), true, BigDecimal.valueOf(25), BigDecimal.valueOf(100));
        CustomerMembership customerMembership = new CustomerMembership("1", "Gold", LocalDateTime.now(), true, BigDecimal.valueOf(25), BigDecimal.valueOf(100), null, null);

        when(repository.saveAndFlush(any(CustomerMembership.class))).thenReturn(customerMembership);

        CustomerMembership result = service.create(request);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Gold", result.getName());
    }

    @Test
    void testFindByIdSuccess() {
        CustomerMembership customerMembership = new CustomerMembership("1", "Gold", LocalDateTime.now(), true, BigDecimal.valueOf(25), BigDecimal.valueOf(100), null, null);

        when(repository.findById(anyString())).thenReturn(Optional.of(customerMembership));

        CustomerMembership result = service.findById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> service.findById("1"));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
    }

    @Test
    void testUpdateCustomerMembership() {
        CustomerMembershipDTORequest request = new CustomerMembershipDTORequest("1", "Platinum", LocalDateTime.now(), true, BigDecimal.valueOf(30), BigDecimal.valueOf(150));
        CustomerMembership existing = new CustomerMembership("1", "Gold", LocalDateTime.now(), true, BigDecimal.valueOf(25), BigDecimal.valueOf(100), null, null);
        CustomerMembership updated = new CustomerMembership("1", "Platinum", LocalDateTime.now(), true, BigDecimal.valueOf(30), BigDecimal.valueOf(150), null, null);

        when(repository.findById(anyString())).thenReturn(Optional.of(existing));
        when(repository.saveAndFlush(any(CustomerMembership.class))).thenReturn(updated);

        CustomerMembership result = service.update(request);

        assertNotNull(result);
        assertEquals("Platinum", result.getName());
    }

    @Test
    void testDeleteCustomerMembership() {
        CustomerMembership existing = new CustomerMembership("1", "Gold", LocalDateTime.now(), true, BigDecimal.valueOf(25), BigDecimal.valueOf(100), null, null);

        when(repository.findById(anyString())).thenReturn(Optional.of(existing));
        doNothing().when(repository).delete(any(CustomerMembership.class));

        assertDoesNotThrow(() -> service.deleteById("1"));
    }
}
