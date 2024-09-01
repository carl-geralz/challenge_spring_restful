package com.enigma.challengespringrestful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.challengespringrestful.entity.CustomerAcquisition;

@Repository
public interface CustomerAcquisitionRepository extends JpaRepository<CustomerAcquisition, String> {
    List<CustomerAcquisition> findAllByNameLike(String name);
}