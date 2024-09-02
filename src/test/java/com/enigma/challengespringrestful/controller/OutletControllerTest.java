package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.OutletDAO;
import com.enigma.challengespringrestful.dto.request.OutletDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.Outlet;
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

public class OutletControllerTest {

    @Mock
    private OutletDAO dao;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private OutletController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOutlet() {
        OutletDTORequest request = new OutletDTORequest("1", "Main Outlet", "Downtown");
        Outlet outlet = new Outlet("1", "Main Outlet", "Downtown", null);

        when(dao.create(any(OutletDTORequest.class))).thenReturn(outlet);

        ResponseEntity<CommonResponse<Outlet>> response = controller.createOutlet(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ConstantMessage.CREATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindOutletsById() {
        Outlet outlet = new Outlet("1", "Main Outlet", "Downtown", null);

        when(dao.findById(anyString())).thenReturn(outlet);

        ResponseEntity<CommonResponse<?>> response = controller.findOutletsById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindAllOutlets() {
        List<Outlet> list = List.of(new Outlet("1", "Main Outlet", "Downtown", null));

        when(dao.findAll(anyString())).thenReturn(list);

        ResponseEntity<CommonResponse<List<Outlet>>> response = controller.findAllOutlets(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateOutlet() {
        OutletDTORequest request = new OutletDTORequest("1", "Updated Outlet", "Uptown");
        Outlet outlet = new Outlet("1", "Updated Outlet", "Uptown", null);

        when(dao.update(any(OutletDTORequest.class))).thenReturn(outlet);

        ResponseEntity<CommonResponse<Outlet>> response = controller.updateOutlet(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.UPDATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testDeleteOutlet() {
        doNothing().when(dao).deleteById(anyString());

        ResponseEntity<CommonResponse<?>> response = controller.deleteOutlet("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ConstantMessage.NO_CONTENT, Objects.requireNonNull(response.getBody()).getMessage());
    }
}
