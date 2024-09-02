package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.CustomerAcquisitionDAO;
import com.enigma.challengespringrestful.dto.request.CustomerAcquisitionDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.CustomerAcquisition;
import com.enigma.challengespringrestful.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;
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
import static org.mockito.Mockito.*;

public class CustomerAcquisitionControllerTests {

    @Mock
    private CustomerAcquisitionDAO dao;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private CustomerAcquisitionController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomerAcquisition() {
        CustomerAcquisitionDTORequest request = new CustomerAcquisitionDTORequest("1", "Customer1");
        CustomerAcquisition customerAcquisition = new CustomerAcquisition("1", "Customer1", null);

        when(dao.create(any(CustomerAcquisitionDTORequest.class))).thenReturn(customerAcquisition);

        ResponseEntity<CommonResponse<CustomerAcquisition>> response = controller.createCustomerAcquisition(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ConstantMessage.CREATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindCustomerAcquisitionsById() {
        CustomerAcquisition customerAcquisition = new CustomerAcquisition("1", "Customer1", null);

        when(dao.findById(anyString())).thenReturn(customerAcquisition);

        ResponseEntity<CommonResponse<?>> response = controller.findCustomerAcquisitionsById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindAllCustomerAcquisitions() {
        List<CustomerAcquisition> list = List.of(new CustomerAcquisition("1", "Customer1", null));

        when(dao.findAll(anyString())).thenReturn(list);

        ResponseEntity<CommonResponse<List<CustomerAcquisition>>> response = controller.findAllCustomerAcquisitions(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateCustomerAcquisition() {
        CustomerAcquisitionDTORequest request = new CustomerAcquisitionDTORequest("1", "UpdatedName");
        CustomerAcquisition customerAcquisition = new CustomerAcquisition("1", "UpdatedName", null);

        when(dao.update(any(CustomerAcquisitionDTORequest.class))).thenReturn(customerAcquisition);

        ResponseEntity<CommonResponse<CustomerAcquisition>> response = controller.updateCustomerAcquisition(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.UPDATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testDeleteCustomerAcquisition() {
        doNothing().when(dao).deleteById(anyString());

        ResponseEntity<CommonResponse<?>> response = controller.deleteCustomerAcquisition("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ConstantMessage.NO_CONTENT, Objects.requireNonNull(response.getBody()).getMessage());
    }

}
