package com.example.usermicroservice.security.services;

import com.example.usermicroservice.models.User;
import com.example.usermicroservice.repositories.UserRepository;
import com.example.usermicroservice.security.models.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;
    @Autowired
    public CustomUserDetailService (UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Email : " + username + " doesn't exist in database.");
        }

       return new CustomUserDetails(optionalUser.get());
    }
}
