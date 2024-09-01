package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountDAO extends UserDetailsService {
    UserAccount getByUserID(String id);
}