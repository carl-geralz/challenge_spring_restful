package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.dto.request.CustomerMembershipDTORequest;
import com.enigma.challengespringrestful.entity.CustomerMembership;

import java.util.List;

public interface CustomerMembershipDAO {
    CustomerMembership create(CustomerMembershipDTORequest customerMembership);

    CustomerMembership findById(String id);

    List<CustomerMembership> findAll(String name);

    CustomerMembership update(CustomerMembershipDTORequest customerMembership);

    void deleteById(String id);
}
