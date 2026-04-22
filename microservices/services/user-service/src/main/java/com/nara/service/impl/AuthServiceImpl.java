package com.nara.service.impl;

import com.nara.dto.UserDTO;
import com.nara.payload.response.AuthResponse;
import com.nara.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public AuthResponse signup(UserDTO req) {
        return null;
    }
    @Override
    public AuthResponse login(String email, String password) {
        return null;
    }


}
