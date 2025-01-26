package com.example.Bus.Finder.System.service.Jwt;


import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      User user = userRepository.findFirstByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User Not Found",null);
        return new org.springframework.security.core.userdetails.User
                (user.getEmail(),user.getPassword(),new ArrayList<>());
    }
}
