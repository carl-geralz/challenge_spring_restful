package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.ProductDTORequest;
import com.enigma.challengespringrestful.entity.Product;
import com.enigma.challengespringrestful.repository.ProductRepository;
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

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductDTORequest request = new ProductDTORequest("1", "Product A", BigDecimal.TEN, BigDecimal.valueOf(5.00), BigDecimal.valueOf(10.00), "Description", BigDecimal.valueOf(7.00));
        Product product = Product.builder().id("1").name("Product A").inventoryQty(BigDecimal.TEN).purchasePrice(BigDecimal.valueOf(5.00)).retailPrice(BigDecimal.valueOf(10.00)).description("Description").salesPrice(BigDecimal.valueOf(7.00)).build();

        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.create(request);

        assertNotNull(createdProduct);
        assertEquals("1", createdProduct.getId());
        assertEquals("Product A", createdProduct.getName());
        verify(productRepository, times(1)).saveAndFlush(any(Product.class));
    }

    @Test
    void testFindById() {
        Product product = Product.builder().id("1").name("Product A").inventoryQty(BigDecimal.TEN).purchasePrice(BigDecimal.valueOf(5.00)).retailPrice(BigDecimal.valueOf(10.00)).description("Description").salesPrice(BigDecimal.valueOf(7.00)).build();

        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));

        Product foundProduct = productService.findById("1");

        assertNotNull(foundProduct);
        assertEquals("1", foundProduct.getId());
        assertEquals("Product A", foundProduct.getName());
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindByIdNotFound() {
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> productService.findById("non-existing-id"));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindAll() {
        List<Product> list = List.of(Product.builder().id("1").name("Product A").inventoryQty(BigDecimal.TEN).purchasePrice(BigDecimal.valueOf(5.00)).retailPrice(BigDecimal.valueOf(10.00)).description("Description").salesPrice(BigDecimal.valueOf(7.00)).build());

        when(productRepository.findAll()).thenReturn(list);

        List<Product> products = productService.findAll(null);

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals("1", products.get(0).getId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindAllWithName() {
        List<Product> list = List.of(Product.builder().id("1").name("Product A").inventoryQty(BigDecimal.TEN).purchasePrice(BigDecimal.valueOf(5.00)).retailPrice(BigDecimal.valueOf(10.00)).description("Description").salesPrice(BigDecimal.valueOf(7.00)).build());

        when(productRepository.findAllByNameLike(anyString())).thenReturn(list);

        List<Product> products = productService.findAll("Product A");

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals("1", products.get(0).getId());
        verify(productRepository, times(1)).findAllByNameLike(anyString());
    }

    @Test
    void testUpdateProduct() {
        ProductDTORequest request = new ProductDTORequest("1", "Updated Product", BigDecimal.valueOf(20), BigDecimal.valueOf(10.00), BigDecimal.valueOf(15.00), "Updated Description", BigDecimal.valueOf(12.00));
        Product existingProduct = Product.builder().id("1").name("Old Product").inventoryQty(BigDecimal.TEN).purchasePrice(BigDecimal.valueOf(5.00)).retailPrice(BigDecimal.valueOf(10.00)).description("Old Description").salesPrice(BigDecimal.valueOf(7.00)).build();
        Product updatedProduct = Product.builder().id("1").name("Updated Product").inventoryQty(BigDecimal.valueOf(20)).purchasePrice(BigDecimal.valueOf(10.00)).retailPrice(BigDecimal.valueOf(15.00)).description("Updated Description").salesPrice(BigDecimal.valueOf(12.00)).build();

        when(productRepository.findById(anyString())).thenReturn(Optional.of(existingProduct));
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.update(request);

        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals(BigDecimal.valueOf(20), result.getInventoryQty());
        verify(productRepository, times(1)).findById(anyString());
        verify(productRepository, times(1)).saveAndFlush(any(Product.class));
    }

    @Test
    void testUpdateProductNotFound() {
        ProductDTORequest request = new ProductDTORequest("non-existing-id", "Updated Product", BigDecimal.valueOf(20), BigDecimal.valueOf(10.00), BigDecimal.valueOf(15.00), "Updated Description", BigDecimal.valueOf(12.00));

        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> productService.update(request));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(productRepository, times(1)).findById(anyString());
        verify(productRepository, never()).saveAndFlush(any(Product.class));
    }

    @Test
    void testDeleteById() {
        Product existingProduct = Product.builder().id("1").name("Product A").inventoryQty(BigDecimal.TEN).purchasePrice(BigDecimal.valueOf(5.00)).retailPrice(BigDecimal.valueOf(10.00)).description("Description").salesPrice(BigDecimal.valueOf(7.00)).build();

        when(productRepository.findById(anyString())).thenReturn(Optional.of(existingProduct));
        doNothing().when(productRepository).delete(any(Product.class));

        productService.deleteById("1");

        verify(productRepository, times(1)).findById(anyString());
        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    void testDeleteByIdNotFound() {
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> productService.deleteById("non-existing-id"));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ConstantMessage.NOT_FOUND, thrown.getReason());
        verify(productRepository, times(1)).findById(anyString());
        verify(productRepository, never()).delete(any(Product.class));
    }

}
