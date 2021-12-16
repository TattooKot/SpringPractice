package com.example.springtestpractice.dto;

import com.example.springtestpractice.model.Role;
import com.example.springtestpractice.model.User;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationDto {

    private Long id;
    private String login;
    private List<Role> roles;

    public static RegistrationDto toDto(User user){

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setId(user.getId());
        registrationDto.setLogin(user.getLogin());
        registrationDto.setRoles(user.getRoles());

        return registrationDto;
    }
}
