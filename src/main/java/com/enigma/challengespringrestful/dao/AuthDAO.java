package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.dto.request.AuthRequest;
import com.enigma.challengespringrestful.dto.response.LoginResponse;
import com.enigma.challengespringrestful.dto.response.RegisterResponse;

public interface AuthDAO {
    RegisterResponse register(AuthRequest request);
    LoginResponse login(AuthRequest request);
}

