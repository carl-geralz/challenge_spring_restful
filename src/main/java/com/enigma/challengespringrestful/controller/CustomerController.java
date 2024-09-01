package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.APIURL;
import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.CustomerDAO;
import com.enigma.challengespringrestful.dto.request.CustomerDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIURL.CUSTOMER)
public class CustomerController {
    private final CustomerDAO customerDAO;

    @GetMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> findCustomersById(@PathVariable String id) {
        Customer customer = customerDAO.findById(id);

        CommonResponse<Customer> commonResponse = CommonResponse.<Customer>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(customer).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<Customer>>> findAllCustomers(@RequestParam(name = "name", required = false) String name) {
        List<Customer> customerList = customerDAO.findAll(name);

        CommonResponse<List<Customer>> commonResponse = CommonResponse.<List<Customer>>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(customerList).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Customer>> updateCustomer(@RequestBody CustomerDTORequest customerDTORequest) {
        Customer updateCustomer = customerDAO.update(customerDTORequest);

        CommonResponse<Customer> commonResponse = CommonResponse.<Customer>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.UPDATED).data(updateCustomer).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteCustomer(@PathVariable String id) {
        customerDAO.deleteById(id);
        CommonResponse<?> commonResponse = CommonResponse.builder().statusCode(HttpStatus.NO_CONTENT.value()).message(ConstantMessage.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
    }

}
