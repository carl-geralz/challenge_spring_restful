package com.enigma.challengespringrestful.repository;

import com.enigma.challengespringrestful.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, String> {
    List<Discount> findAllByNameLike(String name);
}