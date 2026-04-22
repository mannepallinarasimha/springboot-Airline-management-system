package com.nara.service;

import com.nara.dto.UserDTO;
import com.nara.model.User;

import java.util.List;

public interface UserService {
    UserDTO getUserByEmail(String email) throws Exception;
    UserDTO getUserById(Long userId) throws Exception;
    List<UserDTO> getAllUsers();
}
