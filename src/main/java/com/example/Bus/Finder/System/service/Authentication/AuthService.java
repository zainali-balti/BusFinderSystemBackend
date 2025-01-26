package com.example.Bus.Finder.System.service.Authentication;

import com.example.Bus.Finder.System.dto.SignUpRequestDto;
import com.example.Bus.Finder.System.dto.UserDto;

public interface AuthService {
    UserDto signUpCustomer(SignUpRequestDto signUpRequestDto);
    UserDto signUpCompany(SignUpRequestDto signUpRequestDto);
    Boolean CheckingEmailPresent(String email);
}
