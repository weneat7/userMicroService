package com.example.usermicroservice.repositories;

import com.example.usermicroservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByValue(String value);

    Optional<Token> findByValueAndIsDeletedEqualsAndExpiryAtGreaterThan(String value, boolean val, Date date);
}