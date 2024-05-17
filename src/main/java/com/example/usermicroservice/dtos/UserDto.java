package com.example.usermicroservice.dtos;

import com.example.usermicroservice.models.Role;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private String hashedPassword;
    private List<RoleDto> roles;
    private boolean isEmailVerified;
}
