package com.nara.mapper;

import com.nara.dto.UserDTO;
import com.nara.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class UserMapper {
    public static UserDTO toDTO(User user) {
        if(user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .build();
    }

    public static List<UserDTO> toDTOLIst(List<User> all) {
        if(all.isEmpty()) return null;
        return all.stream().map(UserMapper::toDTO).toList();
    }

}
