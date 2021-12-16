package com.example.springtestpractice.dto;

import com.example.springtestpractice.model.Event;
import com.example.springtestpractice.model.Role;
import com.example.springtestpractice.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {

    private Long id;
    private String login;
    private Date created;
    private Date updated;
    private List<Role> roles;
    private List<Event> events;

    public static AdminUserDto fromUser(User user){
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setLogin(user.getLogin());
        adminUserDto.setCreated(user.getCreated());
        adminUserDto.setUpdated(user.getUpdated());
        adminUserDto.setRoles(user.getRoles());
        adminUserDto.setEvents(user.getEvents());

        if(!adminUserDto.isDataOk()){
            log.warn("IN AdminUserDto fromUser - Incorrect data in AdminUserDto (User '{}')", user.getLogin());
            throw new IllegalArgumentException("Incorrect data in AdminUserDto");
        }

        log.info("IN AdminUserDto fromUser - Successfully created AdminUserDto from user '{}'", user.getLogin());

        return adminUserDto;
    }

    private boolean isDataOk(){
        return
                id !=null
                && login!=null
                && created!=null
                && updated != null;
    }
}
