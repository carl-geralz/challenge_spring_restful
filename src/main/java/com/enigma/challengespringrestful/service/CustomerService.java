package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.CustomerDTORequest;
import com.enigma.challengespringrestful.dto.request.CustomerDTORequest;
import com.enigma.challengespringrestful.entity.Customer;
import com.enigma.challengespringrestful.entity.Customer;
import com.enigma.challengespringrestful.repository.CustomerRepository;
import com.enigma.challengespringrestful.dao.CustomerDAO;
import com.enigma.challengespringrestful.utils.ValidationUtils;
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
    private final ValidationUtils validationUtils;

    /**
     * @param customerDTORequest
     * @return
     */
    @Override
    public Customer create(Customer customerDTORequest) {
        validationUtils.validate(customerDTORequest);
        Customer customer = Customer.builder()
                .id(customerDTORequest.getId())
                .build();
        return customerRepository.saveAndFlush(customer);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Customer findById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalCustomer.get();
    }

    /**
     * @return
     */
    @Override
    public List<Customer> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return customerRepository.findAllByNameLike("%" + name + "%");
        }
        return customerRepository.findAll();
    }

    /**
     * @param customerDTORequest
     * @return
     */
    @Override
    public Customer update(CustomerDTORequest customerDTORequest) {
        validationUtils.validate(customerDTORequest);
        Customer existingCustomer = findById(customerDTORequest.getId());
        existingCustomer.setName(customerDTORequest.getName());
        return customerRepository.saveAndFlush(existingCustomer);
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(String id) {
        Customer currentlySelectedCustomerID = findById(id);
        customerRepository.delete(currentlySelectedCustomerID);
    }

}
