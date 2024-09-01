package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.dto.request.TransactionDetailDTORequest;
import com.enigma.challengespringrestful.entity.*;

import java.util.List;

public interface TransactionDetailDAO {
    TransactionDetail create(TransactionDetailDTORequest transactionDetail);
    TransactionDetail findById(String id);
    List<TransactionDetail> findAll(String name);
    TransactionDetail update(TransactionDetailDTORequest transactionDetail);
    void deleteById(String id);
}
