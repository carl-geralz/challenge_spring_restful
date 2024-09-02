package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.TransactionDTORequest;
import com.enigma.challengespringrestful.entity.Transaction;
import com.enigma.challengespringrestful.repository.TransactionRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private TransactionService transactionService;

    private LocalDateTime fixedDateTime;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fixedDateTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        // Use fixedDateTime for all date comparisons
    }

    @Test
    void testCreateTransaction() {
        TransactionDTORequest request = new TransactionDTORequest("1", fixedDateTime);
        Transaction transaction = Transaction.builder()
                .id("1")
                .invoiceDate(fixedDateTime)
                .build();

        when(transactionRepository.saveAndFlush(any(Transaction.class))).thenReturn(transaction);

        Transaction createdTransaction = transactionService.create(request);

        assertNotNull(createdTransaction);
        assertEquals("1", createdTransaction.getId());
        assertEquals(fixedDateTime, createdTransaction.getInvoiceDate());
        verify(transactionRepository, times(1)).saveAndFlush(any(Transaction.class));
    }

    @Test
    void testFindById() {
        Transaction transaction = Transaction.builder()
                .id("1")
                .invoiceDate(fixedDateTime)
                .build();

        when(transactionRepository.findById(anyString())).thenReturn(Optional.of(transaction));

        Transaction foundTransaction = transactionService.findById("1");

        assertNotNull(foundTransaction);
        assertEquals("1", foundTransaction.getId());
        assertEquals(fixedDateTime, foundTransaction.getInvoiceDate());
        verify(transactionRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindByIdNotFound() {
        when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> transactionService.findById("non-existing-id"));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(transactionRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindAll() {
        List<Transaction> list = List.of(Transaction.builder()
                .id("1")
                .invoiceDate(fixedDateTime)
                .build());

        when(transactionRepository.findAll()).thenReturn(list);

        List<Transaction> transactions = transactionService.findAll(null);

        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
        assertEquals("1", transactions.get(0).getId());
        assertEquals(fixedDateTime, transactions.get(0).getInvoiceDate());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void testUpdateTransaction() {
        TransactionDTORequest request = new TransactionDTORequest("1", fixedDateTime);
        Transaction existingTransaction = Transaction.builder()
                .id("1")
                .invoiceDate(fixedDateTime.minusDays(1))
                .build();
        Transaction updatedTransaction = Transaction.builder()
                .id("1")
                .invoiceDate(fixedDateTime)
                .build();

        when(transactionRepository.findById(anyString())).thenReturn(Optional.of(existingTransaction));
        when(transactionRepository.saveAndFlush(any(Transaction.class))).thenReturn(updatedTransaction);

        Transaction result = transactionService.update(request);

        assertNotNull(result);
        assertEquals(fixedDateTime, result.getInvoiceDate());
        verify(transactionRepository, times(1)).findById(anyString());
        verify(transactionRepository, times(1)).saveAndFlush(any(Transaction.class));
    }

    @Test
    void testUpdateTransactionNotFound() {
        TransactionDTORequest request = new TransactionDTORequest("non-existing-id", fixedDateTime);

        when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> transactionService.update(request));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(transactionRepository, times(1)).findById(anyString());
        verify(transactionRepository, never()).saveAndFlush(any(Transaction.class));
    }

    @Test
    void testDeleteById() {
        Transaction existingTransaction = Transaction.builder()
                .id("1")
                .invoiceDate(fixedDateTime)
                .build();

        when(transactionRepository.findById(anyString())).thenReturn(Optional.of(existingTransaction));
        doNothing().when(transactionRepository).delete(any(Transaction.class));

        transactionService.deleteById("1");

        verify(transactionRepository, times(1)).findById(anyString());
        verify(transactionRepository, times(1)).delete(any(Transaction.class));
    }

    @Test
    void testDeleteByIdNotFound() {
        when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> transactionService.deleteById("non-existing-id"));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(transactionRepository, times(1)).findById(anyString());
        verify(transactionRepository, never()).delete(any(Transaction.class));
    }
}
