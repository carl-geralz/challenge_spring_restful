package com.enigma.challengespringrestful.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.TransactionDAO;
import com.enigma.challengespringrestful.dto.request.TransactionDTORequest;
import com.enigma.challengespringrestful.entity.Transaction;
import com.enigma.challengespringrestful.repository.TransactionRepository;
import com.enigma.challengespringrestful.utils.ValidationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionDAO {

    private final TransactionRepository transactionRepository;
    private final ValidationUtils validationUtils;

    /**
     * @param transactionDTORequest
     * @return
     */
    @Override
    public Transaction create(TransactionDTORequest transactionDTORequest) {
        validationUtils.validate(transactionDTORequest);
        Transaction transaction = Transaction.builder()
                .id(transactionDTORequest.getId())
                .build();
        return transactionRepository.saveAndFlush(transaction);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Transaction findById(String id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalTransaction.get();
    }

    /**
     * @return
     */
    @Override
    public List<Transaction> findAll(String invoiceDate) {
        return transactionRepository.findAll();
    }

    /**
     * @param transactionDTORequest
     * @return
     */
    @Override
    public Transaction update(TransactionDTORequest transactionDTORequest) {
        validationUtils.validate(transactionDTORequest);
        Transaction existingTransaction = findById(transactionDTORequest.getId());
        existingTransaction.setId(transactionDTORequest.getId());
        return transactionRepository.saveAndFlush(existingTransaction);
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(String id) {
        Transaction currentlySelectedTransactionID = findById(id);
        transactionRepository.delete(currentlySelectedTransactionID);
    }

}
