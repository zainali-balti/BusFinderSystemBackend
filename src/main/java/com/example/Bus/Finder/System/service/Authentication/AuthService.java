package com.example.Bus.Finder.System.service.Authentication;

import com.example.Bus.Finder.System.dto.SignUpRequestDto;
import com.example.Bus.Finder.System.dto.UserDto;

import java.util.List;

public interface AuthService {
    Boolean checkingEmailPresent(String email);
    UserDto signUp(SignUpRequestDto signUpRequestDto);
    List<UserDto> getAllUsers();
}
