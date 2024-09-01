package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.APIURL;
import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.CustomerMembershipDAO;
import com.enigma.challengespringrestful.dto.request.CustomerMembershipDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.CustomerMembership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIURL.CUSTOMER_MEMBERSHIP)
public class CustomerMembershipController {
    private final CustomerMembershipDAO customerMembershipDAO;

    @PostMapping
    public ResponseEntity<CommonResponse<CustomerMembership>> createCustomerMembership(@RequestBody CustomerMembershipDTORequest customerMembershipDTORequest) {
        CustomerMembership newCustomerMembership = customerMembershipDAO.create(customerMembershipDTORequest);

        CommonResponse<CustomerMembership> commonResponse = CommonResponse.<CustomerMembership>builder().statusCode(HttpStatus.CREATED.value()).message(ConstantMessage.CREATED).data(newCustomerMembership).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> findCustomerMembershipsById(@PathVariable String id) {
        CustomerMembership customerMembership = customerMembershipDAO.findById(id);

        CommonResponse<CustomerMembership> commonResponse = CommonResponse.<CustomerMembership>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(customerMembership).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerMembership>>> findAllCustomerMemberships(@RequestParam(name = "name", required = false) String name) {
        List<CustomerMembership> customerMembershipList = customerMembershipDAO.findAll(name);

        CommonResponse<List<CustomerMembership>> commonResponse = CommonResponse.<List<CustomerMembership>>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(customerMembershipList).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<CustomerMembership>> updateCustomerMembership(@RequestBody CustomerMembershipDTORequest customerMembershipDTORequest) {
        CustomerMembership updateCustomerMembership = customerMembershipDAO.update(customerMembershipDTORequest);

        CommonResponse<CustomerMembership> commonResponse = CommonResponse.<CustomerMembership>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.UPDATED).data(updateCustomerMembership).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteCustomerMembership(@PathVariable String id) {
        customerMembershipDAO.deleteById(id);
        CommonResponse<?> commonResponse = CommonResponse.builder().statusCode(HttpStatus.NO_CONTENT.value()).message(ConstantMessage.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
    }

}
