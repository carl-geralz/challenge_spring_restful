package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.dto.request.TransactionDTORequest;
import com.enigma.challengespringrestful.entity.Transaction;

import java.util.List;

public interface TransactionDAO {
    Transaction create(TransactionDTORequest transaction);

    Transaction findById(String id);

    List<Transaction> findAll(String name);

    Transaction update(TransactionDTORequest transaction);

    void deleteById(String id);
}
