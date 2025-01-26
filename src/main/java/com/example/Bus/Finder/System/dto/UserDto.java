package com.example.Bus.Finder.System.dto;

import com.example.Bus.Finder.System.enums.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private UserRole role;
}
