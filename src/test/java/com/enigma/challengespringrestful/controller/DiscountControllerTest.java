package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.DiscountDAO;
import com.enigma.challengespringrestful.dto.request.DiscountDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.Discount;
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

public class DiscountControllerTest {

    @Mock
    private DiscountDAO dao;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private DiscountController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDiscount() {
        DiscountDTORequest request = new DiscountDTORequest("1", "Summer Sale", LocalDateTime.now(), LocalDateTime.now().plusDays(10), true, BigDecimal.valueOf(15));
        Discount discount = new Discount("1", "Summer Sale", LocalDateTime.now(), LocalDateTime.now().plusDays(10), true, BigDecimal.valueOf(15), null);

        when(dao.create(any(DiscountDTORequest.class))).thenReturn(discount);

        ResponseEntity<CommonResponse<Discount>> response = controller.createDiscount(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ConstantMessage.CREATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindDiscountsById() {
        Discount discount = new Discount("1", "Summer Sale", LocalDateTime.now(), LocalDateTime.now().plusDays(10), true, BigDecimal.valueOf(15), null);

        when(dao.findById(anyString())).thenReturn(discount);

        ResponseEntity<CommonResponse<?>> response = controller.findDiscountsById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindAllDiscounts() {
        List<Discount> list = List.of(new Discount("1", "Summer Sale", LocalDateTime.now(), LocalDateTime.now().plusDays(10), true, BigDecimal.valueOf(15), null));

        when(dao.findAll(anyString())).thenReturn(list);

        ResponseEntity<CommonResponse<List<Discount>>> response = controller.findAllDiscounts(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateDiscount() {
        DiscountDTORequest request = new DiscountDTORequest("1", "Winter Sale", LocalDateTime.now(), LocalDateTime.now().plusDays(10), true, BigDecimal.valueOf(20));
        Discount discount = new Discount("1", "Winter Sale", LocalDateTime.now(), LocalDateTime.now().plusDays(10), true, BigDecimal.valueOf(20), null);

        when(dao.update(any(DiscountDTORequest.class))).thenReturn(discount);

        ResponseEntity<CommonResponse<Discount>> response = controller.updateDiscount(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.UPDATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testDeleteDiscount() {
        doNothing().when(dao).deleteById(anyString());

        ResponseEntity<CommonResponse<?>> response = controller.deleteDiscount("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ConstantMessage.NO_CONTENT, Objects.requireNonNull(response.getBody()).getMessage());
    }
}
