package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.CustomerDAO;
import com.enigma.challengespringrestful.dto.request.CustomerDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CustomerDTORequest request = new CustomerDTORequest("1", "John Doe", "0123456789");
        Customer customer = new Customer("1", "John Doe", "0123456789", null, null, null, true, null);

        when(customerDAO.create(any(CustomerDTORequest.class))).thenReturn(customer);

        ResponseEntity<CommonResponse<Customer>> response = customerController.createCustomer(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());  // Expecting 201 Created
        assertEquals(ConstantMessage.CREATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }


    @Test
    void testFindCustomerById() {
        Customer customer = new Customer("1", "John Doe", "0123456789", null, null, null, true, null);

        when(customerDAO.findById(anyString())).thenReturn(customer);

        ResponseEntity<CommonResponse<?>> response = customerController.findCustomersById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindAllCustomers() {
        List<Customer> list = List.of(new Customer("1", "John Doe", "0123456789", null, null, null, true, null));

        when(customerDAO.findAll(anyString())).thenReturn(list);

        ResponseEntity<CommonResponse<List<Customer>>> response = customerController.findAllCustomers(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateCustomer() {
        CustomerDTORequest request = new CustomerDTORequest("1", "Jane Doe", "0987654321");
        Customer updatedCustomer = new Customer("1", "Jane Doe", "0987654321", null, null, null, true, null);

        when(customerDAO.update(any(CustomerDTORequest.class))).thenReturn(updatedCustomer);

        ResponseEntity<CommonResponse<Customer>> response = customerController.updateCustomer(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.UPDATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerDAO).deleteById(anyString());

        ResponseEntity<CommonResponse<?>> response = customerController.deleteCustomer("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ConstantMessage.NO_CONTENT, Objects.requireNonNull(response.getBody()).getMessage());
    }

}
