package com.example.usermicroservice.security.models;

import com.example.usermicroservice.models.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {

    private Role role;
    private String authority;
    public CustomGrantedAuthority(){}
    public CustomGrantedAuthority(Role role){
        this.role = role;
        this.authority = role.getRoleName();
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
