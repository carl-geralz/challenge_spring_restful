package com.enigma.challengespringrestful.repository;

import com.enigma.challengespringrestful.entity.CustomerAcquisition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAcquisitionRepository extends JpaRepository<CustomerAcquisition, String> {
    List<CustomerAcquisition> findAllByNameLike(String name);
}
