package com.enigma.challengespringrestful.repository;

import com.enigma.challengespringrestful.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findAllByNameLike(String name);
}