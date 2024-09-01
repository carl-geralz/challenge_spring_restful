package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.CustomerAcquisitionDTORequest;
import com.enigma.challengespringrestful.entity.CustomerAcquisition;
import com.enigma.challengespringrestful.repository.CustomerAcquisitionRepository;
import com.enigma.challengespringrestful.dao.CustomerAcquisitionDAO;
import com.enigma.challengespringrestful.utils.ValidationUtils;
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
    private final ValidationUtils validationUtils;

    /**
     * @param customerAcquisitionDTORequest
     * @return
     */
    @Override
    public CustomerAcquisition create(CustomerAcquisitionDTORequest customerAcquisitionDTORequest) {
        validationUtils.validate(customerAcquisitionDTORequest);
        CustomerAcquisition customerAcquisition = CustomerAcquisition.builder()
                .id(customerAcquisitionDTORequest.getId())
                .build();
        return customerAcquisitionRepository.saveAndFlush(customerAcquisition);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CustomerAcquisition findById(String id) {
        Optional<CustomerAcquisition> optionalCustomerAcquisition = customerAcquisitionRepository.findById(id);
        if (optionalCustomerAcquisition.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalCustomerAcquisition.get();
    }

    /**
     * @return
     */
    @Override
    public List<CustomerAcquisition> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return customerAcquisitionRepository.findAllByNameLike("%" + name + "%");
        }
        return customerAcquisitionRepository.findAll();
    }

    /**
     * @param customerAcquisitionDTORequest
     * @return
     */
    @Override
    public CustomerAcquisition update(CustomerAcquisitionDTORequest customerAcquisitionDTORequest) {
        validationUtils.validate(customerAcquisitionDTORequest);
        CustomerAcquisition existingCustomerAcquisition = findById(customerAcquisitionDTORequest.getId());
        existingCustomerAcquisition.setName(customerAcquisitionDTORequest.getName());
        return customerAcquisitionRepository.saveAndFlush(existingCustomerAcquisition);
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(String id) {
        CustomerAcquisition currentlySelectedCustomerAcquisitionID = findById(id);
        customerAcquisitionRepository.delete(currentlySelectedCustomerAcquisitionID);
    }

}
