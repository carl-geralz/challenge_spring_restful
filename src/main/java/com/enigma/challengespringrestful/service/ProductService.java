package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.ProductDAO;
import com.enigma.challengespringrestful.dto.request.ProductDTORequest;
import com.enigma.challengespringrestful.entity.Product;
import com.enigma.challengespringrestful.repository.ProductRepository;
import com.enigma.challengespringrestful.utils.ValidationUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductDAO {

    private final ProductRepository productRepository;
    @Getter
    private final ValidationUtils validationUtils;

    @Override
    public Product create(ProductDTORequest productDTORequest) {
        validateProductDTORequest(productDTORequest);

        Product product = Product.builder().id(productDTORequest.getId()).name(productDTORequest.getName()).build();
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product findById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return productRepository.findAllByNameLike("%" + name + "%");
        }
        return productRepository.findAll();
    }

    @Override
    public Product update(ProductDTORequest productDTORequest) {
        validateProductDTORequest(productDTORequest);

        Product existingProduct = findById(productDTORequest.getId());
        existingProduct.setName(productDTORequest.getName());
        return productRepository.saveAndFlush(existingProduct);
    }

    @Override
    public void deleteById(String id) {
        Product currentlySelectedProduct = findById(id);
        productRepository.delete(currentlySelectedProduct);
    }

    private void validateProductDTORequest(ProductDTORequest request) {
        if (ValidationUtils.isNotEmpty(request.getId())) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        if (ValidationUtils.isNotEmpty(request.getName())) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

}
