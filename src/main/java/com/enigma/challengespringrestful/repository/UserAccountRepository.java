package com.enigma.challengespringrestful.repository;

import com.enigma.challengespringrestful.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    UserDetails findByUsername(String username);
}
