package com.enigma.challengespringrestful.repository;

import com.enigma.challengespringrestful.constant.ConstantUserRole;
import com.enigma.challengespringrestful.entity.Transaction;
import com.enigma.challengespringrestful.entity.UserRole;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    Optional<UserRole> findAllByRole(String role);
}
