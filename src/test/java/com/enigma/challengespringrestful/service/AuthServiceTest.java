package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantUserRole;
import com.enigma.challengespringrestful.dao.CustomerDAO;
import com.enigma.challengespringrestful.dao.JWTDAO;
import com.enigma.challengespringrestful.dao.UserRoleDAO;
import com.enigma.challengespringrestful.dto.request.AuthRequest;
import com.enigma.challengespringrestful.dto.request.CustomerDTORequest;
import com.enigma.challengespringrestful.dto.response.LoginResponse;
import com.enigma.challengespringrestful.dto.response.RegisterResponse;
import com.enigma.challengespringrestful.entity.Customer;
import com.enigma.challengespringrestful.entity.UserAccount;
import com.enigma.challengespringrestful.entity.UserRole;
import com.enigma.challengespringrestful.repository.CustomerRepository;
import com.enigma.challengespringrestful.repository.UserAccountRepository;
import com.enigma.challengespringrestful.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private UserRoleDAO userRoleDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomerDAO customerDAO;

    @Mock
    private JWTDAO jwtDAO;

    @Setter
    @Getter
    @Mock
    private ValidationUtils validationUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AuthService authService;

    private UserAccount userAccount;
    private UserRole userRole;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userRole = UserRole.builder().role(ConstantUserRole.ROLE_USER).build();
        userAccount = UserAccount.builder().username("testuser").password("hashedpassword").roles(List.of(userRole)).isEnable(true).build();
        customer = Customer.builder().id(String.valueOf(1L)).name("testuser").phoneNumber("123456789").build();
    }

    @Test
    public void testRegister() {
        AuthRequest authRequest = new AuthRequest("testuser", "password");

        // Mock behavior
        when(userRoleDAO.getOrSave(ConstantUserRole.ROLE_USER)).thenReturn(userRole);
        when(passwordEncoder.encode(authRequest.getPassword())).thenReturn("hashedpassword");
        when(userAccountRepository.saveAndFlush(any(UserAccount.class))).thenReturn(userAccount);
        when(customerRepository.findAllByNameLike(authRequest.getUsername())).thenReturn(List.of(customer));
        when(customerDAO.create(any(CustomerDTORequest.class))).thenReturn(null);

        RegisterResponse registerResponse = authService.register(authRequest);

        assertEquals("testuser", registerResponse.getUsername());

        // Convert expected roles to String
        List<String> expectedRoles = List.of(ConstantUserRole.ROLE_USER.name());
        List<String> actualRoles = registerResponse.getRoles();

        assertEquals(expectedRoles, actualRoles);
    }

    @Test
    public void testLogin() {
        AuthRequest authRequest = new AuthRequest("testuser", "password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAccount, null, userAccount.getAuthorities());

        // Mock behavior
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(jwtDAO.generateToken(userAccount)).thenReturn("jwtToken");

        LoginResponse loginResponse = authService.login(authRequest);

        assertEquals("jwtToken", loginResponse.getToken());
        assertEquals("testuser", loginResponse.getUsername());

        // Convert expected roles to String
        List<String> expectedRoles = List.of(ConstantUserRole.ROLE_USER.name());
        List<String> actualRoles = loginResponse.getRoles();

        assertEquals(expectedRoles, actualRoles);
    }


}
