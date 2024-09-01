package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.CustomerMembershipDTORequest;
import com.enigma.challengespringrestful.entity.CustomerMembership;
import com.enigma.challengespringrestful.repository.CustomerMembershipRepository;
import com.enigma.challengespringrestful.dao.CustomerMembershipDAO;
import com.enigma.challengespringrestful.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerMembershipService implements CustomerMembershipDAO {

    private final CustomerMembershipRepository customerMembershipRepository;
    private final ValidationUtils validationUtils;

    /**
     * @param customerMembershipDTORequest
     * @return
     */
    @Override
    public CustomerMembership create(CustomerMembershipDTORequest customerMembershipDTORequest) {
        validationUtils.validate(customerMembershipDTORequest);
        CustomerMembership customerMembership = CustomerMembership.builder()
                .id(customerMembershipDTORequest.getId())
                .build();
        return customerMembershipRepository.saveAndFlush(customerMembership);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CustomerMembership findById(String id) {
        Optional<CustomerMembership> optionalCustomerMembership = customerMembershipRepository.findById(id);
        if (optionalCustomerMembership.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalCustomerMembership.get();
    }

    /**
     * @return
     */
    @Override
    public List<CustomerMembership> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return customerMembershipRepository.findAllByNameLike("%" + name + "%");
        }
        return customerMembershipRepository.findAll();
    }

    /**
     * @param customerMembershipDTORequest
     * @return
     */
    @Override
    public CustomerMembership update(CustomerMembershipDTORequest customerMembershipDTORequest) {
        validationUtils.validate(customerMembershipDTORequest);
        CustomerMembership existingCustomerMembership = findById(customerMembershipDTORequest.getId());
        existingCustomerMembership.setName(customerMembershipDTORequest.getName());
        return customerMembershipRepository.saveAndFlush(existingCustomerMembership);
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(String id) {
        CustomerMembership currentlySelectedCustomerMembershipID = findById(id);
        customerMembershipRepository.delete(currentlySelectedCustomerMembershipID);
    }

}
