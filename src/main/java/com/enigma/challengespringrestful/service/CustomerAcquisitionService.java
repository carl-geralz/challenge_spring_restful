package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.CustomerAcquisitionDAO;
import com.enigma.challengespringrestful.dto.request.CustomerAcquisitionDTORequest;
import com.enigma.challengespringrestful.entity.CustomerAcquisition;
import com.enigma.challengespringrestful.repository.CustomerAcquisitionRepository;
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

public class CustomerAcquisitionService implements CustomerAcquisitionDAO {

    private final CustomerAcquisitionRepository customerAcquisitionRepository;
    @Getter
    private final ValidationUtils validationUtils;

    @Override
    public CustomerAcquisition create(CustomerAcquisitionDTORequest customerAcquisitionDTORequest) {
        validateCustomerAcquisitionDTORequest(customerAcquisitionDTORequest);

        CustomerAcquisition customerAcquisition = CustomerAcquisition.builder().id(customerAcquisitionDTORequest.getId()).name(customerAcquisitionDTORequest.getName()).build();
        return customerAcquisitionRepository.saveAndFlush(customerAcquisition);
    }

    @Override
    public CustomerAcquisition findById(String id) {
        Optional<CustomerAcquisition> optionalCustomerAcquisition = customerAcquisitionRepository.findById(id);
        if (optionalCustomerAcquisition.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalCustomerAcquisition.get();
    }

    @Override
    public List<CustomerAcquisition> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return customerAcquisitionRepository.findAllByNameLike("%" + name + "%");
        }
        return customerAcquisitionRepository.findAll();
    }

    @Override
    public CustomerAcquisition update(CustomerAcquisitionDTORequest customerAcquisitionDTORequest) {
        validateCustomerAcquisitionDTORequest(customerAcquisitionDTORequest);

        CustomerAcquisition existingCustomerAcquisition = findById(customerAcquisitionDTORequest.getId());
        existingCustomerAcquisition.setName(customerAcquisitionDTORequest.getName());
        return customerAcquisitionRepository.saveAndFlush(existingCustomerAcquisition);
    }

    @Override
    public void deleteById(String id) {
        CustomerAcquisition currentlySelectedCustomerAcquisition = findById(id);
        customerAcquisitionRepository.delete(currentlySelectedCustomerAcquisition);
    }

    private void validateCustomerAcquisitionDTORequest(CustomerAcquisitionDTORequest request) {
        if (ValidationUtils.isNotEmpty(request.getId())) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        if (ValidationUtils.isNotEmpty(request.getName())) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }
}
