package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.TransactionDAO;
import com.enigma.challengespringrestful.dto.request.TransactionDTORequest;
import com.enigma.challengespringrestful.entity.Transaction;
import com.enigma.challengespringrestful.repository.TransactionRepository;
import com.enigma.challengespringrestful.utils.ValidationUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionDAO {

    private final TransactionRepository transactionRepository;
    @Getter
    private final ValidationUtils validationUtils;

    @Override
    public Transaction create(TransactionDTORequest transactionDTORequest) {
        validateTransactionDTORequest(transactionDTORequest);

        Transaction transaction = Transaction.builder().id(transactionDTORequest.getId()).build();
        return transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public Transaction findById(String id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalTransaction.get();
    }

    @Override
    public List<Transaction> findAll(String invoiceDate) {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction update(TransactionDTORequest transactionDTORequest) {
        validateTransactionDTORequest(transactionDTORequest);

        Transaction existingTransaction = findById(transactionDTORequest.getId());
        existingTransaction.setId(transactionDTORequest.getId());
        return transactionRepository.saveAndFlush(existingTransaction);
    }

    @Override
    public void deleteById(String id) {
        Transaction currentlySelectedTransaction = findById(id);
        transactionRepository.delete(currentlySelectedTransaction);
    }

    private void validateTransactionDTORequest(TransactionDTORequest request) {
        if (ValidationUtils.isNotEmpty(request.getId())) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        if (request.getInvoiceDate() == null) {
            throw new IllegalArgumentException("Invoice date cannot be null");
        }
    }
}
