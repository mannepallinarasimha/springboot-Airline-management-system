package com.nara.service.impl;

import com.nara.dto.UserDTO;
import com.nara.mapper.UserMapper;
import com.nara.model.User;
import com.nara.respository.UserRepository;
import com.nara.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("USER not found with email :"+email);
        }

        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserById(Long userId) throws Exception {
        return UserMapper.toDTO(userRepository.findById(userId).orElseThrow(() -> new Exception("USER not found with ID :"+userId)));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return UserMapper.toDTOLIst(userRepository.findAll());
    }
}
