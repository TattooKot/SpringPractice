package com.example.springtestpractice.dto;

import com.example.springtestpractice.model.Event;
import com.example.springtestpractice.model.File;
import com.example.springtestpractice.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String login;
    private List<Event> events;
//    private List<File> files;

    public static UserDto toUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setEvents(user.getEvents());
//        userDto.setFiles(user.getFiles());

        return userDto;
    }
}
