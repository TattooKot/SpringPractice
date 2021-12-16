package com.example.springtestpractice.service.impl;

import com.example.springtestpractice.model.Role;
import com.example.springtestpractice.model.User;
import com.example.springtestpractice.repository.RoleRepository;
import com.example.springtestpractice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findAll() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        doReturn(users).when(userRepository).findAll();

        List<User> result = userService.findAll();

        verify(userRepository, atLeastOnce()).findAll();
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(users, result);
    }

    @Test
    void findById() {
        User user = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        verify(userRepository, atLeastOnce()).findById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

    @Test
    void register() {
        String login = "login";
        String password = "password";

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        Role role = new Role();

        when(userRepository.findByLogin(login)).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(role);
        when(passwordEncoder.encode(password)).thenReturn(password);

        User result = userService.register(user);

        verify(userRepository, atLeastOnce()).save(user);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

    @Test
    void update() {
        String login = "login";

        User user = new User();
        user.setLogin(login);

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByLogin(login)).thenReturn(user);

        User result = userService.update(user);

        verify(userRepository, atLeastOnce()).save(user);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

    @Test
    void findByLogin() {
        String login = "login";

        User user = new User();
        user.setLogin(login);

        when(userRepository.findByLogin(login)).thenReturn(user);

        User result = userService.findByLogin(login);

        verify(userRepository, atLeastOnce()).findByLogin(login);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

    @Test
    void delete() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }
}