package com.example.springtestpractice.rest;

import com.example.springtestpractice.dto.UserDto;
import com.example.springtestpractice.model.User;
import com.example.springtestpractice.security.jwt.JwtTokenProvider;
import com.example.springtestpractice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/")
@Slf4j
public class UserRestControllerV1 {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public UserRestControllerV1(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<UserDto> getInfo(@RequestHeader("Authorization") String token){
        String login = jwtTokenProvider.getUserName(token.substring(7));

        User user = userService.findByLogin(login);

        UserDto result = UserDto.toUserDto(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
