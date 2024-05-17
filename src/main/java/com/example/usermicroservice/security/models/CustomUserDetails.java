package com.example.usermicroservice.security.models;

import com.example.usermicroservice.models.Role;
import com.example.usermicroservice.models.User;
import com.example.usermicroservice.security.services.CustomUserDetailService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonDeserialize
@Getter
@Setter
public class CustomUserDetails implements UserDetails {


    private List<GrantedAuthority> Authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public CustomUserDetails(){}

   public CustomUserDetails(User user){
       this.password = user.getHashedPassword();
       this.username = user.getEmail();
       this.accountNonExpired = true;
       this.accountNonLocked = true;
       this.credentialsNonExpired = true;
       this.enabled = true;
       List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

       for(Role role : user.getRoles()){
           grantedAuthorities.add(new CustomGrantedAuthority(role));
       }
       this.Authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return Authorities;
    }

    @Override
    public String getPassword() {
//   return user.getHashedPassword();
        return password;
   }

    @Override
    public String getUsername() {
//        return user.getEmail();
        return username;
   }


    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
