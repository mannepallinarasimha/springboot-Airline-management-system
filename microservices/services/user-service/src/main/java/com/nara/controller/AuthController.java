package com.nara.controller;

import com.nara.dto.UserDTO;
import com.nara.payload.request.LoginRequest;
import com.nara.payload.response.AuthResponse;
import com.nara.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path="signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody @Valid UserDTO userDTO) throws Exception {
        return ResponseEntity.ok(authService.signup(userDTO));
    }

    @PostMapping(path="login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        return ResponseEntity.ok(authService.login(loginRequest.getEmail(), loginRequest.getPassword()));
    }
}
