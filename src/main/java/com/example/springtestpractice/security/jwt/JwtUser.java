package com.example.springtestpractice.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

public class JwtUser implements UserDetails {

    private final Long id;
    private final String login;
    private final String password;
    private final Date lastPasswordResetDate;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long id, String login, String password, Date lastPasswordResetDate, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
    }


    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return login;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getLogin() {
        return login;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
