package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.dto.request.CustomerDTORequest;
import com.enigma.challengespringrestful.entity.Customer;
import com.enigma.challengespringrestful.repository.CustomerRepository;
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

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CustomerDTORequest request = new CustomerDTORequest("1", "John Doe", "0123456789");
        Customer customer = new Customer("1", "John Doe", "0123456789", null, null, null, true, null);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.create(request);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testFindByIdSuccess() {
        Customer customer = new Customer("1", "John Doe", "0123456789", null, null, null, true, null);

        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));

        Customer result = customerService.findById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(customerRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> customerService.findById("1"));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
    }

    @Test
    void testUpdateCustomer() {
        CustomerDTORequest request = new CustomerDTORequest("1", "Jane Doe", "0987654321");

        Customer existing = Customer.builder().id("1").name("John Doe").phoneNumber("0123456789").build();

        Customer updated = Customer.builder().id("1").name("Jane Doe").phoneNumber("0987654321").build();

        when(customerRepository.findById(anyString())).thenReturn(Optional.of(existing));
        when(customerRepository.save(any(Customer.class))).thenReturn(updated);

        Customer result = customerService.update(request);

        assertNotNull(result, "The result should not be null");
        assertEquals("Jane Doe", result.getName(), "The name should be updated");
        assertEquals("0987654321", result.getPhoneNumber(), "The phone number should be updated");
    }


    @Test
    void testDeleteCustomer() {
        Customer existing = new Customer("1", "John Doe", "0123456789", null, null, null, true, null);

        when(customerRepository.findById(anyString())).thenReturn(Optional.of(existing));
        doNothing().when(customerRepository).delete(any(Customer.class));

        assertDoesNotThrow(() -> customerService.deleteById("1"));
    }

}
