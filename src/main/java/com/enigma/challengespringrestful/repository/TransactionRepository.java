package com.enigma.challengespringrestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.challengespringrestful.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}