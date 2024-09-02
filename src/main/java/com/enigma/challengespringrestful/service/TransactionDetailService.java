package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.TransactionDetailDAO;
import com.enigma.challengespringrestful.dto.request.TransactionDetailDTORequest;
import com.enigma.challengespringrestful.entity.TransactionDetail;
import com.enigma.challengespringrestful.repository.TransactionDetailRepository;
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
public class TransactionDetailService implements TransactionDetailDAO {

    private final TransactionDetailRepository transactionDetailRepository;
    @Getter
    private final ValidationUtils validationUtils;

    @Override
    public TransactionDetail create(TransactionDetailDTORequest transactionDetailDTORequest) {
        validateTransactionDetailDTORequest(transactionDetailDTORequest);

        TransactionDetail transactionDetail = TransactionDetail.builder().id(transactionDetailDTORequest.getId()).invoiceQty(transactionDetailDTORequest.getInvoiceQty()).vatPercentage(transactionDetailDTORequest.getVatPercentage()).invoicePrice(transactionDetailDTORequest.getInvoicePrice()).build();
        return transactionDetailRepository.saveAndFlush(transactionDetail);
    }

    @Override
    public TransactionDetail findById(String id) {
        Optional<TransactionDetail> optionalTransactionDetail = transactionDetailRepository.findById(id);
        if (optionalTransactionDetail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalTransactionDetail.get();
    }

    @Override
    public List<TransactionDetail> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return transactionDetailRepository.findAllByIdLike("%" + name + "%");
        }
        return transactionDetailRepository.findAll();
    }

    @Override
    public TransactionDetail update(TransactionDetailDTORequest transactionDetailDTORequest) {
        validateTransactionDetailDTORequest(transactionDetailDTORequest);

        TransactionDetail existingTransactionDetail = findById(transactionDetailDTORequest.getId());
        existingTransactionDetail.setInvoiceQty(transactionDetailDTORequest.getInvoiceQty());
        existingTransactionDetail.setVatPercentage(transactionDetailDTORequest.getVatPercentage());
        existingTransactionDetail.setInvoicePrice(transactionDetailDTORequest.getInvoicePrice());
        return transactionDetailRepository.saveAndFlush(existingTransactionDetail);
    }

    @Override
    public void deleteById(String id) {
        TransactionDetail currentlySelectedTransactionDetail = findById(id);
        transactionDetailRepository.delete(currentlySelectedTransactionDetail);
    }

    private void validateTransactionDetailDTORequest(TransactionDetailDTORequest request) {
        if (request.getId() == null || request.getId().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        if (request.getVatPercentage() == null) {
            throw new IllegalArgumentException("VAT percentage cannot be null");
        }
    }
}
