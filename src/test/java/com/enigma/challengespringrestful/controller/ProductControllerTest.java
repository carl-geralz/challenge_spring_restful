package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.ProductDAO;
import com.enigma.challengespringrestful.dto.request.ProductDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.Product;
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

public class ProductControllerTest {

    @Mock
    private ProductDAO dao;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private ProductController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductDTORequest request = new ProductDTORequest("1", "Product A", BigDecimal.TEN, BigDecimal.valueOf(5.00), BigDecimal.valueOf(10.00), "Description", BigDecimal.valueOf(7.00));
        Product product = Product.builder().id("1").name("Product A").inventoryQty(BigDecimal.TEN).purchasePrice(BigDecimal.valueOf(5.00)).retailPrice(BigDecimal.valueOf(10.00)).description("Description").salesPrice(BigDecimal.valueOf(7.00)).build();

        when(dao.create(any(ProductDTORequest.class))).thenReturn(product);

        ResponseEntity<CommonResponse<Product>> response = controller.createProduct(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ConstantMessage.CREATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindProductById() {
        Product product = Product.builder().id("1").name("Product A").inventoryQty(BigDecimal.TEN).purchasePrice(BigDecimal.valueOf(5.00)).retailPrice(BigDecimal.valueOf(10.00)).description("Description").salesPrice(BigDecimal.valueOf(7.00)).build();

        when(dao.findById(anyString())).thenReturn(product);

        ResponseEntity<CommonResponse<?>> response = controller.findProductsById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testFindAllProducts() {
        List<Product> list = List.of(Product.builder().id("1").name("Product A").inventoryQty(BigDecimal.TEN).purchasePrice(BigDecimal.valueOf(5.00)).retailPrice(BigDecimal.valueOf(10.00)).description("Description").salesPrice(BigDecimal.valueOf(7.00)).build());

        when(dao.findAll(anyString())).thenReturn(list);

        ResponseEntity<CommonResponse<List<Product>>> response = controller.findAllProducts(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.OK, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateProduct() {
        ProductDTORequest request = new ProductDTORequest("1", "Updated Product", BigDecimal.valueOf(20), BigDecimal.valueOf(10.00), BigDecimal.valueOf(15.00), "Updated Description", BigDecimal.valueOf(12.00));
        Product product = Product.builder().id("1").name("Updated Product").inventoryQty(BigDecimal.valueOf(20)).purchasePrice(BigDecimal.valueOf(10.00)).retailPrice(BigDecimal.valueOf(15.00)).description("Updated Description").salesPrice(BigDecimal.valueOf(12.00)).build();

        when(dao.update(any(ProductDTORequest.class))).thenReturn(product);

        ResponseEntity<CommonResponse<Product>> response = controller.updateProduct(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessage.UPDATED, Objects.requireNonNull(response.getBody()).getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(dao).deleteById(anyString());

        ResponseEntity<CommonResponse<?>> response = controller.deleteProduct("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ConstantMessage.NO_CONTENT, Objects.requireNonNull(response.getBody()).getMessage());
    }
}
