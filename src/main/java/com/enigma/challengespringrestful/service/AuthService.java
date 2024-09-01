package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantUserRole;
import com.enigma.challengespringrestful.dao.AuthDAO;
import com.enigma.challengespringrestful.dao.CustomerDAO;
import com.enigma.challengespringrestful.dao.JWTDAO;
import com.enigma.challengespringrestful.dao.UserRoleDAO;
import com.enigma.challengespringrestful.dto.request.AuthRequest;
import com.enigma.challengespringrestful.dto.response.LoginResponse;
import com.enigma.challengespringrestful.dto.response.RegisterResponse;
import com.enigma.challengespringrestful.entity.Customer;
import com.enigma.challengespringrestful.entity.UserAccount;
import com.enigma.challengespringrestful.entity.UserRole;
import com.enigma.challengespringrestful.repository.UserAccountRepository;
import com.enigma.challengespringrestful.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthDAO {
    private final UserAccountRepository userAccountRepository;
    private final UserRoleDAO userRoleDAO;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDAO customerDAO;
    private final JWTDAO jwtDAO;
    private final ValidationUtils validationUtils;

    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(AuthRequest request) throws DataIntegrityViolationException {
        UserRole role = userRoleDAO.getOrSave(ConstantUserRole.ROLE_USER);
        validationUtils.validate(request);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .roles(List.of(role))
                .isEnable(true)
                .build();
        userAccountRepository.saveAndFlush(account);

        Customer customer = Customer.builder()
                .status(true)
                .userAccount(account)
                .build();
        customerDAO.create(customer);


        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public LoginResponse login(AuthRequest request) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(authentication);

        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();

        String token = jwtDAO.generateToken(userAccount);
        return LoginResponse.builder()
                .token(token)
                .username(userAccount.getUsername())
                .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}