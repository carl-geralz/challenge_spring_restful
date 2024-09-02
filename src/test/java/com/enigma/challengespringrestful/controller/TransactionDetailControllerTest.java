package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.TransactionDetailDAO;
import com.enigma.challengespringrestful.dto.request.TransactionDetailDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.TransactionDetail;
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
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class TransactionDetailControllerTest {

    @Mock
    private TransactionDetailDAO dao;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private TransactionDetailController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransactionDetail() {
        TransactionDetailDTORequest request = new TransactionDetailDTORequest("1", BigDecimal.TEN, BigDecimal.valueOf(5.00), BigDecimal.valueOf(100.00));
        TransactionDetail transactionDetail = TransactionDetail.builder().id("1").invoiceQty(BigDecimal.TEN).vatPercentage(BigDecimal.valueOf(5.00)).invoicePrice(BigDecimal.valueOf(100.00)).build();

        when(dao.create(any(TransactionDetailDTORequest.class))).thenReturn(transactionDetail);

        ResponseEntity<CommonResponse<TransactionDetail>> response = controller.createTransactionDetail(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ConstantMessage.CREATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindTransactionDetailById() {
        TransactionDetail transactionDetail = TransactionDetail.builder().id("1").invoiceQty(BigDecimal.TEN).vatPercentage(BigDecimal.valueOf(5.00)).invoicePrice(BigDecimal.valueOf(100.00)).build();

        when(dao.findById(anyString())).thenReturn(transactionDetail);

        ResponseEntity<CommonResponse<?>> response = controller.findTransactionDetailsById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindAllTransactionDetails() {
        List<TransactionDetail> list = List.of(TransactionDetail.builder().id("1").invoiceQty(BigDecimal.TEN).vatPercentage(BigDecimal.valueOf(5.00)).invoicePrice(BigDecimal.valueOf(100.00)).build());

        when(dao.findAll(anyString())).thenReturn(list);

        ResponseEntity<CommonResponse<List<TransactionDetail>>> response = controller.findAllTransactionDetails(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateTransactionDetail() {
        TransactionDetailDTORequest request = new TransactionDetailDTORequest("1", BigDecimal.valueOf(20), BigDecimal.valueOf(10.00), BigDecimal.valueOf(200.00));
        TransactionDetail transactionDetail = TransactionDetail.builder().id("1").invoiceQty(BigDecimal.valueOf(20)).vatPercentage(BigDecimal.valueOf(10.00)).invoicePrice(BigDecimal.valueOf(200.00)).build();

        when(dao.update(any(TransactionDetailDTORequest.class))).thenReturn(transactionDetail);

        ResponseEntity<CommonResponse<TransactionDetail>> response = controller.updateTransactionDetail(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.UPDATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testDeleteTransactionDetail() {
        doNothing().when(dao).deleteById(anyString());

        ResponseEntity<CommonResponse<?>> response = controller.deleteTransactionDetail("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ConstantMessage.NO_CONTENT, Objects.requireNonNull(response.getBody()).getMessage());
    }
}
