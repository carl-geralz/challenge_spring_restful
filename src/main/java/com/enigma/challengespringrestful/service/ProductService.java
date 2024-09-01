package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.ProductDTORequest;
import com.enigma.challengespringrestful.dto.request.ProductDTORequest;
import com.enigma.challengespringrestful.entity.Product;
import com.enigma.challengespringrestful.entity.Product;
import com.enigma.challengespringrestful.repository.ProductRepository;
import com.enigma.challengespringrestful.dao.ProductDAO;
import com.enigma.challengespringrestful.utils.ValidationUtils;
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
    private final ValidationUtils validationUtils;

    /**
     * @param productDTORequest
     * @return
     */
    @Override
    public Product create(ProductDTORequest productDTORequest) {
        validationUtils.validate(productDTORequest);
        Product product = Product.builder()
                .id(productDTORequest.getId())
                .build();
        return productRepository.saveAndFlush(product);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Product findById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalProduct.get();
    }

    /**
     * @return
     */
    @Override
    public List<Product> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return productRepository.findAllByNameLike("%" + name + "%");
        }
        return productRepository.findAll();
    }

    /**
     * @param productDTORequest
     * @return
     */
    @Override
    public Product update(ProductDTORequest productDTORequest) {
        validationUtils.validate(productDTORequest);
        Product existingProduct = findById(productDTORequest.getId());
        existingProduct.setName(productDTORequest.getName());
        return productRepository.saveAndFlush(existingProduct);
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(String id) {
        Product currentlySelectedProductID = findById(id);
        productRepository.delete(currentlySelectedProductID);
    }

}
