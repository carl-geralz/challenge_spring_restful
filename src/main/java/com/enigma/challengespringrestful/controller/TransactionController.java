package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.APIURL;
import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.TransactionDAO;
import com.enigma.challengespringrestful.dto.request.TransactionDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIURL.TRANSACTION)
@Validated
@AutoConfigureRestDocs
public class TransactionController {
    private final TransactionDAO transactionDAO;

    @PostMapping
    public ResponseEntity<CommonResponse<Transaction>> createTransaction(@RequestBody TransactionDTORequest transactionDTORequest) {
        Transaction newTransaction = transactionDAO.create(transactionDTORequest);

        CommonResponse<Transaction> commonResponse = CommonResponse.<Transaction>builder().statusCode(HttpStatus.CREATED.value()).message(ConstantMessage.CREATED).data(newTransaction).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> findTransactionsById(@PathVariable String id) {
        Transaction transaction = transactionDAO.findById(id);

        CommonResponse<Transaction> commonResponse = CommonResponse.<Transaction>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(transaction).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<Transaction>>> findAllTransactions(@RequestParam(name = "name", required = false) String name) {
        List<Transaction> transactionList = transactionDAO.findAll(name);

        CommonResponse<List<Transaction>> commonResponse = CommonResponse.<List<Transaction>>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(transactionList).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Transaction>> updateTransaction(@RequestBody TransactionDTORequest transactionDTORequest) {
        Transaction updateTransaction = transactionDAO.update(transactionDTORequest);

        CommonResponse<Transaction> commonResponse = CommonResponse.<Transaction>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.UPDATED).data(updateTransaction).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteTransaction(@PathVariable String id) {
        transactionDAO.deleteById(id);
        CommonResponse<?> commonResponse = CommonResponse.builder().statusCode(HttpStatus.NO_CONTENT.value()).message(ConstantMessage.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
    }

}
