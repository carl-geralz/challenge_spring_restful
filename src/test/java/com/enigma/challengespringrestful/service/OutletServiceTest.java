package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.OutletDTORequest;
import com.enigma.challengespringrestful.entity.Outlet;
import com.enigma.challengespringrestful.repository.OutletRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OutletServiceTest {

    @Mock
    private OutletRepository outletRepository;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private OutletService outletService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOutlet() {
        OutletDTORequest request = new OutletDTORequest("1", "Main Outlet", "Downtown");
        Outlet outlet = new Outlet("1", "Main Outlet", "Downtown", null);

        when(outletRepository.saveAndFlush(any(Outlet.class))).thenReturn(outlet);

        Outlet createdOutlet = outletService.create(request);

        assertNotNull(createdOutlet);
        assertEquals("1", createdOutlet.getId());
        assertEquals("Main Outlet", createdOutlet.getName());
        verify(outletRepository, times(1)).saveAndFlush(any(Outlet.class));
    }

    @Test
    void testFindById() {
        Outlet outlet = new Outlet("1", "Main Outlet", "Downtown", null);

        when(outletRepository.findById(anyString())).thenReturn(Optional.of(outlet));

        Outlet foundOutlet = outletService.findById("1");

        assertNotNull(foundOutlet);
        assertEquals("1", foundOutlet.getId());
        assertEquals("Main Outlet", foundOutlet.getName());
        verify(outletRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindByIdNotFound() {
        when(outletRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> outletService.findById("non-existing-id"));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(outletRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindAll() {
        List<Outlet> list = List.of(new Outlet("1", "Main Outlet", "Downtown", null));

        when(outletRepository.findAll()).thenReturn(list);

        List<Outlet> outlets = outletService.findAll(null);

        assertNotNull(outlets);
        assertFalse(outlets.isEmpty());
        assertEquals("1", outlets.get(0).getId());
        verify(outletRepository, times(1)).findAll();
    }

    @Test
    void testFindAllWithName() {
        List<Outlet> list = List.of(new Outlet("1", "Main Outlet", "Downtown", null));

        when(outletRepository.findAllByNameLike(anyString())).thenReturn(list);

        List<Outlet> outlets = outletService.findAll("Main");

        assertNotNull(outlets);
        assertFalse(outlets.isEmpty());
        assertEquals("1", outlets.get(0).getId());
        verify(outletRepository, times(1)).findAllByNameLike(anyString());
    }

    @Test
    void testUpdateOutlet() {
        OutletDTORequest request = new OutletDTORequest("1", "Updated Outlet", "Uptown");
        Outlet existingOutlet = new Outlet("1", "Old Outlet", "Downtown", null);
        Outlet updatedOutlet = new Outlet("1", "Updated Outlet", "Uptown", null);

        when(outletRepository.findById(anyString())).thenReturn(Optional.of(existingOutlet));
        when(outletRepository.saveAndFlush(any(Outlet.class))).thenReturn(updatedOutlet);

        Outlet result = outletService.update(request);

        assertNotNull(result);
        assertEquals("Updated Outlet", result.getName());
        assertEquals("Uptown", result.getLocation());
        verify(outletRepository, times(1)).findById(anyString());
        verify(outletRepository, times(1)).saveAndFlush(any(Outlet.class));
    }

    @Test
    void testUpdateOutletNotFound() {
        OutletDTORequest request = new OutletDTORequest("non-existing-id", "Updated Outlet", "Uptown");

        when(outletRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> outletService.update(request));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(outletRepository, times(1)).findById(anyString());
        verify(outletRepository, never()).saveAndFlush(any(Outlet.class));
    }

    @Test
    void testDeleteById() {
        Outlet existingOutlet = new Outlet("1", "Main Outlet", "Downtown", null);

        when(outletRepository.findById(anyString())).thenReturn(Optional.of(existingOutlet));
        doNothing().when(outletRepository).delete(any(Outlet.class));

        outletService.deleteById("1");

        verify(outletRepository, times(1)).findById(anyString());
        verify(outletRepository, times(1)).delete(any(Outlet.class));
    }

    @Test
    void testDeleteByIdNotFound() {
        when(outletRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> outletService.deleteById("non-existing-id"));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(outletRepository, times(1)).findById(anyString());
        verify(outletRepository, never()).delete(any(Outlet.class));
    }

}
