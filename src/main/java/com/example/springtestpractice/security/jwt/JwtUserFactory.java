package com.example.springtestpractice.security.jwt;

import com.example.springtestpractice.model.Role;
import com.example.springtestpractice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getUpdated(),
                getAuthorityList(user.getRoles())
        );
    }

    private static List<GrantedAuthority> getAuthorityList(List<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
