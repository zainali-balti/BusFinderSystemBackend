package com.example.Bus.Finder.System.service.Authentication;

import com.example.Bus.Finder.System.dto.SignUpRequestDto;
import com.example.Bus.Finder.System.dto.UserDto;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.enums.UserRole;
import com.example.Bus.Finder.System.repository.UserRepository;
import com.example.Bus.Finder.System.repository.WalletRepository;
import com.example.Bus.Finder.System.service.Mail.MailService;
import com.example.Bus.Finder.System.service.Wallet.WalletService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImplementation implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;
    @Autowired
    private WalletService walletService;

    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
    if (checkingEmailPresent(signUpRequestDto.getEmail())) {
        throw new IllegalArgumentException("Email already exists");
    }

    User user = new User();
    user.setEmail(signUpRequestDto.getEmail());
    user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
    user.setFirstName(signUpRequestDto.getFirstName());
    user.setLastName(signUpRequestDto.getLastName());
    user.setPhoneNo(signUpRequestDto.getPhoneNo());

    if (signUpRequestDto.getRole() == UserRole.CUSTOMER || signUpRequestDto.getRole() == UserRole.COMPANY) {
        user.setRole(signUpRequestDto.getRole());
    } else {
        throw new IllegalArgumentException("Invalid role specified");
    }
        User savedUser = userRepository.save(user);
        walletService.addWallet(savedUser.getId(), BigDecimal.valueOf(1000));
        mailService.sendSignUpEmail(savedUser.getEmail(), savedUser.getFirstName());
    return savedUser.getDto();
}

    public Boolean checkingEmailPresent(String email){
        return userRepository.findFirstByEmail(email) !=null;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(User::getDto).collect(Collectors.toList());
    }


}
