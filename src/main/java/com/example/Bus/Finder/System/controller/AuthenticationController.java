package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.AuthenticationRequest;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.repository.UserRepository;
import com.example.Bus.Finder.System.service.Authentication.AuthService;
import com.example.Bus.Finder.System.dto.SignUpRequestDto;
import com.example.Bus.Finder.System.dto.UserDto;
import com.example.Bus.Finder.System.service.Jwt.JwtUserDetailsServiceImplementation;
import com.example.Bus.Finder.System.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUserDetailsServiceImplementation userDetailsServiceImplementation;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTH_HEADER = "Authorization";

    @PostMapping("/customer/sign-up")
    public ResponseEntity<?> SignUpCustomer(@RequestBody SignUpRequestDto signUpRequestDto){
        if (authService.CheckingEmailPresent(signUpRequestDto.getEmail())){
            return new ResponseEntity<>("Email is Already Present", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = authService.signUpCustomer(signUpRequestDto);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
    @PostMapping("/company/sign-up")
    public ResponseEntity<?> SignUpCompany(@RequestBody SignUpRequestDto signUpRequestDto){
        if (authService.CheckingEmailPresent(signUpRequestDto.getEmail())){
            return new ResponseEntity<>("Email is Already Present", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = authService.signUpCompany(signUpRequestDto);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public void createAuthentication(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {

        try{
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Username or Password",e);
        }

        final UserDetails userDetails = userDetailsServiceImplementation.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails.getUsername());
        User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());

        response.getWriter().write(
                new JSONObject()
                        .put("userId", user.getId())
                        .put("role", user.getRole())
                        .put("token",jwt)
                        .toString()
        );
        response.addHeader("Access-Control-Expose-Headers","Authorization");
        response.addHeader("Access-Control-Allow-Headers","Authorization," +
                " X-PINGOTHER,Origin,X-Requested-With,Content_Type,Accept,X-Custom-header");
        response.addHeader(AUTH_HEADER,TOKEN_PREFIX+jwt);

    }
}
