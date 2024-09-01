package com.enigma.challengespringrestful.repository;

import com.enigma.challengespringrestful.entity.CustomerMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMembershipRepository extends JpaRepository<CustomerMembership, String> {
    List<CustomerMembership> findAllByNameLike(String name);
}