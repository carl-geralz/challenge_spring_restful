package com.enigma.challengespringrestful.repository;

import com.enigma.challengespringrestful.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
    List<TransactionDetail> findAllByIdLike(String name);
}