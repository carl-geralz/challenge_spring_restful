package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.TransactionDetailDTORequest;
import com.enigma.challengespringrestful.entity.TransactionDetail;
import com.enigma.challengespringrestful.repository.TransactionDetailRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionDetailServiceTest {

    @Mock
    private TransactionDetailRepository transactionDetailRepository;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private TransactionDetailService transactionDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransactionDetail() {
        TransactionDetailDTORequest request = new TransactionDetailDTORequest("1", BigDecimal.TEN, BigDecimal.valueOf(5.00), BigDecimal.valueOf(100.00));
        TransactionDetail transactionDetail = TransactionDetail.builder()
                .id("1")
                .invoiceQty(BigDecimal.TEN)
                .vatPercentage(BigDecimal.valueOf(5.00))
                .invoicePrice(BigDecimal.valueOf(100.00))
                .build();

        when(transactionDetailRepository.saveAndFlush(any(TransactionDetail.class))).thenReturn(transactionDetail);

        TransactionDetail createdTransactionDetail = transactionDetailService.create(request);

        assertNotNull(createdTransactionDetail);
        assertEquals("1", createdTransactionDetail.getId());
        verify(transactionDetailRepository, times(1)).saveAndFlush(any(TransactionDetail.class));
    }

    @Test
    void testFindById() {
        TransactionDetail transactionDetail = TransactionDetail.builder()
                .id("1")
                .invoiceQty(BigDecimal.TEN)
                .vatPercentage(BigDecimal.valueOf(5.00))
                .invoicePrice(BigDecimal.valueOf(100.00))
                .build();

        when(transactionDetailRepository.findById(anyString())).thenReturn(Optional.of(transactionDetail));

        TransactionDetail foundTransactionDetail = transactionDetailService.findById("1");

        assertNotNull(foundTransactionDetail);
        assertEquals("1", foundTransactionDetail.getId());
        verify(transactionDetailRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindByIdNotFound() {
        when(transactionDetailRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> transactionDetailService.findById("non-existing-id"));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(transactionDetailRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindAll() {
        List<TransactionDetail> list = List.of(TransactionDetail.builder()
                .id("1")
                .invoiceQty(BigDecimal.TEN)
                .vatPercentage(BigDecimal.valueOf(5.00))
                .invoicePrice(BigDecimal.valueOf(100.00))
                .build());

        when(transactionDetailRepository.findAll()).thenReturn(list);

        List<TransactionDetail> transactionDetails = transactionDetailService.findAll(null);

        assertNotNull(transactionDetails);
        assertFalse(transactionDetails.isEmpty());
        assertEquals("1", transactionDetails.get(0).getId());
        verify(transactionDetailRepository, times(1)).findAll();
    }

    @Test
    void testFindAllWithName() {
        List<TransactionDetail> list = List.of(TransactionDetail.builder()
                .id("1")
                .invoiceQty(BigDecimal.TEN)
                .vatPercentage(BigDecimal.valueOf(5.00))
                .invoicePrice(BigDecimal.valueOf(100.00))
                .build());

        when(transactionDetailRepository.findAllByIdLike(anyString())).thenReturn(list);

        List<TransactionDetail> transactionDetails = transactionDetailService.findAll("1");

        assertNotNull(transactionDetails);
        assertFalse(transactionDetails.isEmpty());
        assertEquals("1", transactionDetails.get(0).getId());
        verify(transactionDetailRepository, times(1)).findAllByIdLike(anyString());
    }

    @Test
    void testUpdateTransactionDetail() {
        TransactionDetailDTORequest request = new TransactionDetailDTORequest("1", BigDecimal.valueOf(20), BigDecimal.valueOf(10.00), BigDecimal.valueOf(200.00));
        TransactionDetail existingTransactionDetail = TransactionDetail.builder()
                .id("1")
                .invoiceQty(BigDecimal.TEN)
                .vatPercentage(BigDecimal.valueOf(5.00))
                .invoicePrice(BigDecimal.valueOf(100.00))
                .build();
        TransactionDetail updatedTransactionDetail = TransactionDetail.builder()
                .id("1")
                .invoiceQty(BigDecimal.valueOf(20))
                .vatPercentage(BigDecimal.valueOf(10.00))
                .invoicePrice(BigDecimal.valueOf(200.00))
                .build();

        when(transactionDetailRepository.findById(anyString())).thenReturn(Optional.of(existingTransactionDetail));
        when(transactionDetailRepository.saveAndFlush(any(TransactionDetail.class))).thenReturn(updatedTransactionDetail);

        TransactionDetail result = transactionDetailService.update(request);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(20), result.getInvoiceQty());
        assertEquals(BigDecimal.valueOf(10.00), result.getVatPercentage());
        assertEquals(BigDecimal.valueOf(200.00), result.getInvoicePrice());
        verify(transactionDetailRepository, times(1)).findById(anyString());
        verify(transactionDetailRepository, times(1)).saveAndFlush(any(TransactionDetail.class));
    }

    @Test
    void testUpdateTransactionDetailNotFound() {
        TransactionDetailDTORequest request = new TransactionDetailDTORequest("non-existing-id", BigDecimal.valueOf(20), BigDecimal.valueOf(10.00), BigDecimal.valueOf(200.00));

        when(transactionDetailRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> transactionDetailService.update(request));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(transactionDetailRepository, times(1)).findById(anyString());
        verify(transactionDetailRepository, never()).saveAndFlush(any(TransactionDetail.class));
    }

    @Test
    void testDeleteById() {
        TransactionDetail existingTransactionDetail = TransactionDetail.builder()
                .id("1")
                .invoiceQty(BigDecimal.TEN)
                .vatPercentage(BigDecimal.valueOf(5.00))
                .invoicePrice(BigDecimal.valueOf(100.00))
                .build();

        when(transactionDetailRepository.findById(anyString())).thenReturn(Optional.of(existingTransactionDetail));
        doNothing().when(transactionDetailRepository).delete(any(TransactionDetail.class));

        transactionDetailService.deleteById("1");

        verify(transactionDetailRepository, times(1)).findById(anyString());
        verify(transactionDetailRepository, times(1)).delete(any(TransactionDetail.class));
    }

    @Test
    void testDeleteByIdNotFound() {
        when(transactionDetailRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> transactionDetailService.deleteById("non-existing-id"));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(transactionDetailRepository, times(1)).findById(anyString());
        verify(transactionDetailRepository, never()).delete(any(TransactionDetail.class));
    }
}
