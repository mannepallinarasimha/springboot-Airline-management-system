package com.nara.controller;

import com.nara.dto.UserDTO;
import com.nara.mapper.UserMapper;
import com.nara.model.User;
import com.nara.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path="/{userId}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable Long userId
    ) throws Exception {
        return ResponseEntity.ok( userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() throws Exception {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
