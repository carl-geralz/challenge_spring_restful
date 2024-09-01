package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.repository.UserAccountRepository;
import com.enigma.challengespringrestful.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserAccountService implements com.enigma.challengespringrestful.dao.UserAccountDAO {
    private final UserAccountRepository userAccountRepository;
    
    @Transactional(readOnly = true)
    @Override
    public UserAccount getByUserID(String id) {
        return userAccountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username);
    }
}

