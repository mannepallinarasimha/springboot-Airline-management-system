package com.nara.controller;

import com.nara.dto.UserDTO;
import com.nara.model.User;
import com.nara.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/users")
public class UserController {
    private final UserService userService;

    @GetMapping(path="profile")
    public ResponseEntity<UserDTO> getUserProfile(
            @RequestHeader("X-User-Email") String email
            ) throws Exception {
        return ResponseEntity.ok( userService.getUserByEmail(email));
    }
}
