package com.example.usermicroservice.services;

import com.example.usermicroservice.models.Token;
import com.example.usermicroservice.models.User;
import com.example.usermicroservice.repositories.TokenRepository;
import com.example.usermicroservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       TokenRepository tokenRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Token login(String email, String password){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty())
            return null;
        User user   = optionalUser.get();
        String hashedPassword = user.getHashedPassword();

        if(!bCryptPasswordEncoder.matches(password,hashedPassword))
            return null;

        Token token = new Token();
        token.setUser(optionalUser.get());
        LocalDateTime now = LocalDateTime.now();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        Date expiryDate = calendar.getTime();
        token.setExpiryAt(expiryDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);

        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setDeleted(false);
        return tokenRepository.save(token);
    }

    public User signUp(String userName, String userPassword, String userEmail){
        User user = new User();
        user.setEmail(userEmail);
        user.setName(userName);
        user.setEmailVerified(true);
        user.setHashedPassword(userPassword);
        return userRepository.save(user);
    }

    public boolean logout(String tokenValue){
        Optional<Token> optionalToken = tokenRepository.findByValue(tokenValue);
        if(optionalToken.isEmpty())
            return false;

        Token token = optionalToken.get();
        token.setDeleted(true);
        tokenRepository.save(token);
        return true;
    }

    public User validateToken(String tokenValue){
            Optional<Token> optionalToken = tokenRepository.findByValueAndIsDeletedEqualsAndExpiryAtGreaterThan(tokenValue,false,new Date());
            return optionalToken.map(Token::getUser).orElse(null);

    }
}
