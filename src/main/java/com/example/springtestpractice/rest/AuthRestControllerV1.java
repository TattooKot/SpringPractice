package com.example.springtestpractice.rest;

import com.example.springtestpractice.dto.AuthRequestDto;
import com.example.springtestpractice.dto.RegistrationDto;
import com.example.springtestpractice.dto.TokenDto;
import com.example.springtestpractice.model.User;
import com.example.springtestpractice.security.jwt.JwtTokenProvider;
import com.example.springtestpractice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthRequestDto authRequestDto){
        String login = authRequestDto.getLogin();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, authRequestDto.getPassword()));

        String token = jwtTokenProvider.createToken(login);

        TokenDto tokenDto = new TokenDto();
        tokenDto.setLogin(login);
        tokenDto.setToken(token);

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @PostMapping("reg")
    public ResponseEntity<RegistrationDto> registration(@RequestBody AuthRequestDto authRequestDto){
        User user = authRequestDto.toUser();
        User created = userService.register(user);

        RegistrationDto result = RegistrationDto.toDto(created);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
