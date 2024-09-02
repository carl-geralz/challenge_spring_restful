package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.dto.request.CustomerAcquisitionDTORequest;
import com.enigma.challengespringrestful.entity.CustomerAcquisition;
import com.enigma.challengespringrestful.repository.CustomerAcquisitionRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CustomerAcquisitionServiceTests {

    @Mock
    private CustomerAcquisitionRepository repository;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private CustomerAcquisitionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomerAcquisition() {
        CustomerAcquisitionDTORequest request = new CustomerAcquisitionDTORequest("1", "Customer1");
        CustomerAcquisition customerAcquisition = new CustomerAcquisition("1", "Customer1", null);

        when(repository.saveAndFlush(any(CustomerAcquisition.class))).thenReturn(customerAcquisition);

        CustomerAcquisition result = service.create(request);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Customer1", result.getName());
    }

    @Test
    void testFindByIdSuccess() {
        CustomerAcquisition customerAcquisition = new CustomerAcquisition("1", "Customer1", null);

        when(repository.findById(anyString())).thenReturn(Optional.of(customerAcquisition));

        CustomerAcquisition result = service.findById("1");

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
    void testUpdateCustomerAcquisition() {
        CustomerAcquisitionDTORequest request = new CustomerAcquisitionDTORequest("1", "UpdatedName");
        CustomerAcquisition existing = new CustomerAcquisition("1", "OldName", null);
        CustomerAcquisition updated = new CustomerAcquisition("1", "UpdatedName", null);

        when(repository.findById(anyString())).thenReturn(Optional.of(existing));
        when(repository.saveAndFlush(any(CustomerAcquisition.class))).thenReturn(updated);

        CustomerAcquisition result = service.update(request);

        assertNotNull(result);
        assertEquals("UpdatedName", result.getName());
    }

    @Test
    void testDeleteCustomerAcquisition() {
        CustomerAcquisition existing = new CustomerAcquisition("1", "Customer1", null);

        when(repository.findById(anyString())).thenReturn(Optional.of(existing));
        doNothing().when(repository).delete(any(CustomerAcquisition.class));

        assertDoesNotThrow(() -> service.deleteById("1"));
    }

}
