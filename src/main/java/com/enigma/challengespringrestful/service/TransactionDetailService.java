package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.TransactionDetailDTORequest;
import com.enigma.challengespringrestful.entity.TransactionDetail;
import com.enigma.challengespringrestful.repository.TransactionDetailRepository;
import com.enigma.challengespringrestful.dao.TransactionDetailDAO;
import com.enigma.challengespringrestful.utils.ValidationUtils;
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
    private final ValidationUtils validationUtils;

    /**
     * @param transactionDetailDTORequest
     * @return
     */
    @Override
    public TransactionDetail create(TransactionDetailDTORequest transactionDetailDTORequest) {
        validationUtils.validate(transactionDetailDTORequest);
        TransactionDetail transactionDetail = TransactionDetail.builder()
                .id(transactionDetailDTORequest.getId())
                .build();
        return transactionDetailRepository.saveAndFlush(transactionDetail);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public TransactionDetail findById(String id) {
        Optional<TransactionDetail> optionalTransactionDetail = transactionDetailRepository.findById(id);
        if (optionalTransactionDetail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalTransactionDetail.get();
    }

    /**
     * @return
     */
    @Override
    public List<TransactionDetail> findAll(String id) {
        if (id != null && !id.isEmpty()) {
            return transactionDetailRepository.findAllByIdLike("%" + id + "%");
        }
        return transactionDetailRepository.findAll();
    }

    /**
     * @param transactionDetailDTORequest
     * @return
     */
    @Override
    public TransactionDetail update(TransactionDetailDTORequest transactionDetailDTORequest) {
        validationUtils.validate(transactionDetailDTORequest);
        TransactionDetail existingTransactionDetail = findById(transactionDetailDTORequest.getId());
        existingTransactionDetail.setVatPercentage(transactionDetailDTORequest.getVatPercentage());
        return transactionDetailRepository.saveAndFlush(existingTransactionDetail);
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(String id) {
        TransactionDetail currentlySelectedTransactionDetailID = findById(id);
        transactionDetailRepository.delete(currentlySelectedTransactionDetailID);
    }

}
