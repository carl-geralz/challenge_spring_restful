package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.APIURL;
import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.CustomerAcquisitionDAO;
import com.enigma.challengespringrestful.dto.request.CustomerAcquisitionDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.CustomerAcquisition;
import com.enigma.challengespringrestful.utils.ValidationUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIURL.CUSTOMER_ACQUISITION)
@Validated
@AutoConfigureRestDocs
public class CustomerAcquisitionController {
    private final CustomerAcquisitionDAO customerAcquisitionDAO;
    @Getter
    private final ValidationUtils validationUtils;

    @PostMapping
    public ResponseEntity<CommonResponse<CustomerAcquisition>> createCustomerAcquisition(@RequestBody CustomerAcquisitionDTORequest customerAcquisitionDTORequest) {
        validateCustomerAcquisition(customerAcquisitionDTORequest);

        CustomerAcquisition newCustomerAcquisition = customerAcquisitionDAO.create(customerAcquisitionDTORequest);

        CommonResponse<CustomerAcquisition> commonResponse = CommonResponse.<CustomerAcquisition>builder().statusCode(HttpStatus.CREATED.value()).message(ConstantMessage.CREATED).data(newCustomerAcquisition).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> findCustomerAcquisitionsById(@PathVariable String id) {
        CustomerAcquisition customerAcquisition = customerAcquisitionDAO.findById(id);

        CommonResponse<CustomerAcquisition> commonResponse = CommonResponse.<CustomerAcquisition>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(customerAcquisition).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerAcquisition>>> findAllCustomerAcquisitions(@RequestParam(name = "name", required = false) String name) {
        List<CustomerAcquisition> customerAcquisitionList = customerAcquisitionDAO.findAll(name);

        CommonResponse<List<CustomerAcquisition>> commonResponse = CommonResponse.<List<CustomerAcquisition>>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(customerAcquisitionList).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<CustomerAcquisition>> updateCustomerAcquisition(@RequestBody CustomerAcquisitionDTORequest customerAcquisitionDTORequest) {
        validateCustomerAcquisition(customerAcquisitionDTORequest);

        CustomerAcquisition updateCustomerAcquisition = customerAcquisitionDAO.update(customerAcquisitionDTORequest);

        CommonResponse<CustomerAcquisition> commonResponse = CommonResponse.<CustomerAcquisition>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.UPDATED).data(updateCustomerAcquisition).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteCustomerAcquisition(@PathVariable String id) {
        customerAcquisitionDAO.deleteById(id);
        CommonResponse<?> commonResponse = CommonResponse.builder().statusCode(HttpStatus.NO_CONTENT.value()).message(ConstantMessage.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
    }

    private void validateCustomerAcquisition(CustomerAcquisitionDTORequest request) {
        if (ValidationUtils.isNotEmpty(request.getName())) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }
}
