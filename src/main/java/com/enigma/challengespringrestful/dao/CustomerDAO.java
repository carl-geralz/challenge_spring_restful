package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.dto.request.CustomerDTORequest;
import com.enigma.challengespringrestful.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    Customer create(CustomerDTORequest customer);

    Customer findById(String id);

    List<Customer> findAll(String name);

    Customer update(CustomerDTORequest customer);

    void deleteById(String id);
}
