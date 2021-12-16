package com.example.springtestpractice.rest;


import com.example.springtestpractice.dto.AdminUserDto;
import com.example.springtestpractice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/admin/")
public class AdminRestControllerV1 {

    private final UserService userService;

    public AdminRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<AdminUserDto>> getAllUsers(){
        List<AdminUserDto> result = userService.findAll()
                .stream()
                .map(AdminUserDto::fromUser)
                .collect(Collectors.toList());

        log.info("IN AdminRestControllerV1 getAllUsers - {} user found", result.size());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        userService.delete(id);

        log.info("IN AdminRestControllerV1 deleteUser - user with id {} successfully deleted", id);

        return new ResponseEntity(HttpStatus.OK);
    }

}
