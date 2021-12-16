package com.example.springtestpractice.dto;

import com.example.springtestpractice.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class AuthRequestDto {

    private String login;
    private String password;

    public User toUser(){
        if(!isDataOk()){
            throw new IllegalArgumentException("IN AuthRequestDto toUser: Bad data");
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setCreated(new Date());
        user.setUpdated(new Date());

        return user;
    }

    private boolean isDataOk(){
        return login != null && password != null;
    }
}
