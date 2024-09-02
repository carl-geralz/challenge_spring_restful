package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.dto.request.CustomerAcquisitionDTORequest;
import com.enigma.challengespringrestful.entity.CustomerAcquisition;

import java.util.List;

public interface CustomerAcquisitionDAO {
    CustomerAcquisition create(CustomerAcquisitionDTORequest customerAcquisition);

    CustomerAcquisition findById(String id);

    List<CustomerAcquisition> findAll(String name);

    CustomerAcquisition update(CustomerAcquisitionDTORequest customerAcquisition);

    void deleteById(String id);
}
