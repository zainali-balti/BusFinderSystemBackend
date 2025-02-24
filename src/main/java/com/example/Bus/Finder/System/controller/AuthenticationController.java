package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.AuthenticationRequest;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.enums.UserRole;
import com.example.Bus.Finder.System.repository.UserRepository;
import com.example.Bus.Finder.System.service.Authentication.AuthService;
import com.example.Bus.Finder.System.dto.SignUpRequestDto;
import com.example.Bus.Finder.System.dto.UserDto;
import com.example.Bus.Finder.System.service.Jwt.JwtUserDetailsServiceImplementation;
import com.example.Bus.Finder.System.service.Mail.GoogleAuthService;
import com.example.Bus.Finder.System.utils.JwtUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @Autowired
    private GoogleAuthService googleAuthService;

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTH_HEADER = "Authorization";


    @PostMapping(path = "/sign-up",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        if (authService.checkingEmailPresent(signUpRequestDto.getEmail())) {
            return new ResponseEntity<>("Email is Already Present", HttpStatus.NOT_ACCEPTABLE);
        }
        if (signUpRequestDto.getRole() == null) {
            return new ResponseEntity<>("Role is required", HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = authService.signUp(signUpRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
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
                        .put("role", user.getRole().toString())
                        .put("token",jwt)
                        .toString()
        );
        System.out.println("Sending Role: " + user.getRole());
        response.addHeader("Access-Control-Expose-Headers","Authorization");
        response.addHeader("Access-Control-Allow-Headers","Authorization," +
                " X-PINGOTHER,Origin,X-Requested-With,Content_Type,Accept,X-Custom-header");
        response.addHeader(AUTH_HEADER,TOKEN_PREFIX+jwt);

    }
    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        String username = principal.getName();
        User user = userRepository.findFirstByEmail(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> userDtoList = authService.getAllUsers();
        return ResponseEntity.ok(userDtoList);
    }
    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> request) {
        String idToken = request.get("idToken");

        GoogleIdToken.Payload payload = googleAuthService.verifyGoogleToken(idToken);
        if (payload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google Token");
        }

        String email = payload.getEmail();
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            String token = jwtUtils.generateToken(user.getEmail());
            return ResponseEntity.ok(Map.of("token", token, "userId", user.getId(), "role", user.getRole()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not registered.");
        }
    }

}
