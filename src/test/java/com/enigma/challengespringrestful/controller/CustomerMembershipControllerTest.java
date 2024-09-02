package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.CustomerMembershipDAO;
import com.enigma.challengespringrestful.dto.request.CustomerMembershipDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.CustomerMembership;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CustomerMembershipControllerTest {

    @Mock
    private CustomerMembershipDAO dao;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private CustomerMembershipController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomerMembership() {
        CustomerMembershipDTORequest request = new CustomerMembershipDTORequest("1", "Gold", LocalDateTime.now(), true, BigDecimal.valueOf(25), BigDecimal.valueOf(100));
        CustomerMembership customerMembership = new CustomerMembership("1", "Gold", LocalDateTime.now(), true, BigDecimal.valueOf(25), BigDecimal.valueOf(100), null, null);

        when(dao.create(any(CustomerMembershipDTORequest.class))).thenReturn(customerMembership);

        ResponseEntity<CommonResponse<CustomerMembership>> response = controller.createCustomerMembership(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ConstantMessage.CREATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindCustomerMembershipsById() {
        CustomerMembership customerMembership = new CustomerMembership("1", "Gold", LocalDateTime.now(), true, BigDecimal.valueOf(25), BigDecimal.valueOf(100), null, null);

        when(dao.findById(anyString())).thenReturn(customerMembership);

        ResponseEntity<CommonResponse<?>> response = controller.findCustomerMembershipsById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindAllCustomerMemberships() {
        List<CustomerMembership> list = List.of(new CustomerMembership("1", "Gold", LocalDateTime.now(), true, BigDecimal.valueOf(25), BigDecimal.valueOf(100), null, null));

        when(dao.findAll(anyString())).thenReturn(list);

        ResponseEntity<CommonResponse<List<CustomerMembership>>> response = controller.findAllCustomerMemberships(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateCustomerMembership() {
        CustomerMembershipDTORequest request = new CustomerMembershipDTORequest("1", "Platinum", LocalDateTime.now(), true, BigDecimal.valueOf(30), BigDecimal.valueOf(150));
        CustomerMembership customerMembership = new CustomerMembership("1", "Platinum", LocalDateTime.now(), true, BigDecimal.valueOf(30), BigDecimal.valueOf(150), null, null);

        when(dao.update(any(CustomerMembershipDTORequest.class))).thenReturn(customerMembership);

        ResponseEntity<CommonResponse<CustomerMembership>> response = controller.updateCustomerMembership(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.UPDATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testDeleteCustomerMembership() {
        doNothing().when(dao).deleteById(anyString());

        ResponseEntity<CommonResponse<?>> response = controller.deleteCustomerMembership("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ConstantMessage.NO_CONTENT, Objects.requireNonNull(response.getBody()).getMessage());
    }
}
