package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.DiscountDTORequest;
import com.enigma.challengespringrestful.entity.Discount;
import com.enigma.challengespringrestful.repository.DiscountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiscountServiceTest {

    @InjectMocks
    private DiscountService discountService;

    @Mock
    private DiscountRepository discountRepository;

// --Commented out by Inspection START (9/3/24, 2:34 AM):
//    @Mock
//    private ValidationUtils validationUtils;
// --Commented out by Inspection STOP (9/3/24, 2:34 AM)

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDiscount() {
        DiscountDTORequest request = new DiscountDTORequest("1", "Summer Sale", LocalDateTime.now(), LocalDateTime.now().plusDays(10), true, BigDecimal.valueOf(15));
        Discount discount = Discount.builder().id("1").name("Summer Sale").build();

        when(discountRepository.saveAndFlush(any(Discount.class))).thenReturn(discount);

        Discount createdDiscount = discountService.create(request);

        assertNotNull(createdDiscount);
        assertEquals("1", createdDiscount.getId());
        verify(discountRepository, times(1)).saveAndFlush(any(Discount.class));
    }

    @Test
    void testFindById() {
        Discount discount = new Discount("1", "Summer Sale", LocalDateTime.now(), LocalDateTime.now().plusDays(10), true, BigDecimal.valueOf(15), null);
        when(discountRepository.findById("1")).thenReturn(Optional.of(discount));

        Discount foundDiscount = discountService.findById("1");

        assertNotNull(foundDiscount);
        assertEquals("1", foundDiscount.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(discountRepository.findById("1")).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> discountService.findById("1"));
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
    }

    @Test
    void testUpdateDiscount() {
        DiscountDTORequest request = new DiscountDTORequest("1", "Winter Sale", LocalDateTime.now(), LocalDateTime.now().plusDays(10), true, BigDecimal.valueOf(20));
        Discount existingDiscount = new Discount("1", "Summer Sale", LocalDateTime.now(), LocalDateTime.now().plusDays(10), true, BigDecimal.valueOf(15), null);

        when(discountRepository.findById("1")).thenReturn(Optional.of(existingDiscount));
        when(discountRepository.saveAndFlush(any(Discount.class))).thenReturn(existingDiscount);

        Discount updatedDiscount = discountService.update(request);

        assertNotNull(updatedDiscount);
        assertEquals("Winter Sale", updatedDiscount.getName());
        verify(discountRepository, times(1)).saveAndFlush(any(Discount.class));
    }

    @Test
    void testDeleteDiscount() {
        Discount discount = new Discount("1", "Summer Sale", LocalDateTime.now(), LocalDateTime.now().plusDays(10), true, BigDecimal.valueOf(15), null);

        when(discountRepository.findById("1")).thenReturn(Optional.of(discount));

        discountService.deleteById("1");

        verify(discountRepository, times(1)).delete(discount);
    }
}
