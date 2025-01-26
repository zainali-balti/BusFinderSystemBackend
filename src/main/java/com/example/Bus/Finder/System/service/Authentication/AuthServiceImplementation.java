package com.example.Bus.Finder.System.service.Authentication;

import com.example.Bus.Finder.System.dto.SignUpRequestDto;
import com.example.Bus.Finder.System.dto.UserDto;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.enums.UserRole;
import com.example.Bus.Finder.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto signUpCustomer(SignUpRequestDto signUpRequestDto){
        User user = new User();
        user.setEmail(signUpRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        user.setFirstName(signUpRequestDto.getFirstName());
        user.setLastName(signUpRequestDto.getLastName());
        user.setPhoneNo(signUpRequestDto.getPhoneNo());
        user.setRole(UserRole.CUSTOMER);
        return userRepository.save(user).getDto();
    }
    public UserDto signUpCompany(SignUpRequestDto signUpRequestDto){
        User user = new User();
        user.setEmail(signUpRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        user.setFirstName(signUpRequestDto.getFirstName());
        user.setPhoneNo(signUpRequestDto.getPhoneNo());
        user.setRole(UserRole.COMPANY);
        return userRepository.save(user).getDto();
    }
    public Boolean CheckingEmailPresent(String email){
        return userRepository.findFirstByEmail(email) !=null;
    }


}
