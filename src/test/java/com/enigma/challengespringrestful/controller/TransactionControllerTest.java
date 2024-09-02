package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.TransactionDAO;
import com.enigma.challengespringrestful.dto.request.TransactionDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.Transaction;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class TransactionControllerTest {

    @Mock
    private TransactionDAO dao;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private TransactionController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransaction() {
        TransactionDTORequest request = new TransactionDTORequest("1", LocalDateTime.now());
        Transaction transaction = Transaction.builder().id("1").invoiceDate(LocalDateTime.now()).build();

        when(dao.create(any(TransactionDTORequest.class))).thenReturn(transaction);

        ResponseEntity<CommonResponse<Transaction>> response = controller.createTransaction(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ConstantMessage.CREATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindTransactionById() {
        Transaction transaction = Transaction.builder().id("1").invoiceDate(LocalDateTime.now()).build();

        when(dao.findById(anyString())).thenReturn(transaction);

        ResponseEntity<CommonResponse<?>> response = controller.findTransactionsById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindAllTransactions() {
        List<Transaction> list = List.of(Transaction.builder().id("1").invoiceDate(LocalDateTime.now()).build());

        when(dao.findAll(anyString())).thenReturn(list);

        ResponseEntity<CommonResponse<List<Transaction>>> response = controller.findAllTransactions(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateTransaction() {
        TransactionDTORequest request = new TransactionDTORequest("1", LocalDateTime.now());
        Transaction transaction = Transaction.builder().id("1").invoiceDate(LocalDateTime.now()).build();

        when(dao.update(any(TransactionDTORequest.class))).thenReturn(transaction);

        ResponseEntity<CommonResponse<Transaction>> response = controller.updateTransaction(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.UPDATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testDeleteTransaction() {
        doNothing().when(dao).deleteById(anyString());

        ResponseEntity<CommonResponse<?>> response = controller.deleteTransaction("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ConstantMessage.NO_CONTENT, Objects.requireNonNull(response.getBody()).getMessage());
    }
}
