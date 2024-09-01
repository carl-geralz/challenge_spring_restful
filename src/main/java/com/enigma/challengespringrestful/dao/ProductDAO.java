package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.dto.request.ProductDTORequest;
import com.enigma.challengespringrestful.entity.*;

import java.util.List;

public interface ProductDAO {
    Product create(ProductDTORequest product);
    Product findById(String id);
    List<Product> findAll(String name);
    Product update(ProductDTORequest product);
    void deleteById(String id);
}
