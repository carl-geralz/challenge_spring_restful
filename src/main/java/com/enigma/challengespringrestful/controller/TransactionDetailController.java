package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.APIURL;
import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.TransactionDetailDAO;
import com.enigma.challengespringrestful.dto.request.TransactionDetailDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.TransactionDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIURL.TRANSACTION_DETAIL)
public class TransactionDetailController {
    private final TransactionDetailDAO transactionDetailDAO;

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionDetail>> createTransactionDetail(@RequestBody TransactionDetailDTORequest transactionDetailDTORequest) {
        TransactionDetail newTransactionDetail = transactionDetailDAO.create(transactionDetailDTORequest);

        CommonResponse<TransactionDetail> commonResponse = CommonResponse.<TransactionDetail>builder().statusCode(HttpStatus.CREATED.value()).message(ConstantMessage.CREATED).data(newTransactionDetail).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> findTransactionDetailsById(@PathVariable String id) {
        TransactionDetail transactionDetail = transactionDetailDAO.findById(id);

        CommonResponse<TransactionDetail> commonResponse = CommonResponse.<TransactionDetail>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(transactionDetail).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionDetail>>> findAllTransactionDetails(@RequestParam(name = "name", required = false) String name) {
        List<TransactionDetail> transactionDetailList = transactionDetailDAO.findAll(name);

        CommonResponse<List<TransactionDetail>> commonResponse = CommonResponse.<List<TransactionDetail>>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(transactionDetailList).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<TransactionDetail>> updateTransactionDetail(@RequestBody TransactionDetailDTORequest transactionDetailDTORequest) {
        TransactionDetail updateTransactionDetail = transactionDetailDAO.update(transactionDetailDTORequest);

        CommonResponse<TransactionDetail> commonResponse = CommonResponse.<TransactionDetail>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.UPDATED).data(updateTransactionDetail).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteTransactionDetail(@PathVariable String id) {
        transactionDetailDAO.deleteById(id);
        CommonResponse<?> commonResponse = CommonResponse.builder().statusCode(HttpStatus.NO_CONTENT.value()).message(ConstantMessage.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
    }

}
