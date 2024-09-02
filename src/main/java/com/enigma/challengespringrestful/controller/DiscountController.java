package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.APIURL;
import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.DiscountDAO;
import com.enigma.challengespringrestful.dto.request.DiscountDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.Discount;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIURL.DISCOUNT)
@Validated
@AutoConfigureRestDocs
public class DiscountController {
    private final DiscountDAO discountDAO;

    @PostMapping
    public ResponseEntity<CommonResponse<Discount>> createDiscount(@RequestBody DiscountDTORequest discountDTORequest) {
        Discount newDiscount = discountDAO.create(discountDTORequest);

        CommonResponse<Discount> commonResponse = CommonResponse.<Discount>builder().statusCode(HttpStatus.CREATED.value()).message(ConstantMessage.CREATED).data(newDiscount).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> findDiscountsById(@PathVariable String id) {
        Discount discount = discountDAO.findById(id);

        CommonResponse<Discount> commonResponse = CommonResponse.<Discount>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(discount).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<Discount>>> findAllDiscounts(@RequestParam(name = "name", required = false) String name) {
        List<Discount> discountList = discountDAO.findAll(name);

        CommonResponse<List<Discount>> commonResponse = CommonResponse.<List<Discount>>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(discountList).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Discount>> updateDiscount(@RequestBody DiscountDTORequest discountDTORequest) {
        Discount updateDiscount = discountDAO.update(discountDTORequest);

        CommonResponse<Discount> commonResponse = CommonResponse.<Discount>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.UPDATED).data(updateDiscount).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteDiscount(@PathVariable String id) {
        discountDAO.deleteById(id);
        CommonResponse<?> commonResponse = CommonResponse.builder().statusCode(HttpStatus.NO_CONTENT.value()).message(ConstantMessage.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
    }
}
