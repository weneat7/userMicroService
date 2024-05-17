package com.example.usermicroservice.controllers;

import com.example.usermicroservice.dtos.LoginRequestDto;
import com.example.usermicroservice.dtos.SignUpRequestDto;
import com.example.usermicroservice.models.Token;
import com.example.usermicroservice.models.User;
import com.example.usermicroservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto){
        //check if email and password in db
        //if yes return user
        // else throw some error
        Token loggedInUser = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        if(loggedInUser == null)
            throw new NullPointerException("No User is Found with the email");
        return loggedInUser;
    }

    @GetMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        // no need to hash password for now
        // just store user as it is in the db
        String userName = signUpRequestDto.getName();
        String userEmail = signUpRequestDto.getEmail();
        String userPassword = bCryptPasswordEncoder.encode(signUpRequestDto.getPassword());

        // for now no need to have email verified
        return userService.signUp(userName,userPassword,userEmail);
    }

    @GetMapping("/logout/{id}")
    public ResponseEntity<Void> logOut(@PathVariable("id") String token){
        // delete token if exists -> return 200
        //if doesn't exist -> return 404
        userService.logout(token);
        return null;
    }

    @GetMapping("/validate/{tokenValue}")
    public User validateToken(@PathVariable("tokenValue") String tokenValue){

        return userService.validateToken(tokenValue);
    }
}
