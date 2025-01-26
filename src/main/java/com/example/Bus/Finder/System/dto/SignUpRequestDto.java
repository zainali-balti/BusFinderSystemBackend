package com.example.Bus.Finder.System.dto;

import com.example.Bus.Finder.System.enums.UserRole;
import lombok.Data;

@Data
public class SignUpRequestDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private UserRole role;
}
