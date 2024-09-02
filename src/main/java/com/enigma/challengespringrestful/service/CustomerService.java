package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.CustomerDAO;
import com.enigma.challengespringrestful.dto.request.CustomerDTORequest;
import com.enigma.challengespringrestful.entity.Customer;
import com.enigma.challengespringrestful.repository.CustomerRepository;
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
public class CustomerService implements CustomerDAO {

    private final CustomerRepository customerRepository;
    @Getter
    private final ValidationUtils validationUtils;

    @Override
    public Customer create(CustomerDTORequest customerDTORequest) {
        validateCustomerDTORequest(customerDTORequest);

        Customer customer = Customer.builder().id(customerDTORequest.getId()).name(customerDTORequest.getName()).phoneNumber(customerDTORequest.getPhoneNumber()).build();

        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalCustomer.get();
    }

    @Override
    public List<Customer> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return customerRepository.findAllByNameLike("%" + name + "%");
        }
        return customerRepository.findAll();
    }

    @Override
    public Customer update(CustomerDTORequest request) {
        Customer existing = customerRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        existing.setName(request.getName());
        existing.setPhoneNumber(request.getPhoneNumber());

        return customerRepository.save(existing);
    }

    @Override
    public void deleteById(String id) {
        Customer currentlySelectedCustomer = findById(id);
        customerRepository.delete(currentlySelectedCustomer);
    }

    private void validateCustomerDTORequest(CustomerDTORequest request) {
        if (ValidationUtils.isNotEmpty(request.getId())) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        if (ValidationUtils.isNotEmpty(request.getName())) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }
}
