package com.example.springtestpractice.security.jwt;

import com.example.springtestpractice.model.Role;
import com.example.springtestpractice.model.User;
import com.example.springtestpractice.service.UserService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secretWord;

    @Value("${jwt.token.expired}")
    private Long milliseconds;

    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public JwtTokenProvider(UserDetailsService userDetailsService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostConstruct
    private void init(){
        secretWord = Base64.getEncoder().encodeToString(secretWord.getBytes());
    }

    public String createToken(String login){
        User user = userService.findByLogin(login);

        if (user == null) {
            log.warn("IN JwtTokenProvider createToken - user {} does not exist", login);
            throw new UsernameNotFoundException("User " + login + " not found");
        }

        Claims claims = Jwts.claims().setSubject(login);
        claims.put("roles", getRoleNames(user.getRoles()));

        Date now = new Date();
        Date expired = new Date(now.getTime()+milliseconds);

        log.info("IN JwtTokenProvider createToken - Token for user {} successfully created created", login);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, secretWord)
                .compact();
    }

    public String resolveToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if(token != null && token.startsWith("Bearer_")){
            log.info("IN JwtTokenProvider resolveToken - Token successfully resolved");

            return token.substring(7);
        }

        log.warn("IN JwtTokenProvider resolveToken - Token not found");

        return null;
    }

    public boolean validateToken(String token){

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                log.warn("IN JwtTokenProvider validateToken - Token expired");

                return false;
            }

            log.info("IN JwtTokenProvider validateToken - Token valid");

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    public String getUserName(String token){
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private List<String> getRoleNames(List<Role> roles){
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
