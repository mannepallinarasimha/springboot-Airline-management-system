package com.nara.service;

import com.nara.dto.UserDTO;
import com.nara.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password);
    AuthResponse signup(UserDTO req) throws Exception;
}
