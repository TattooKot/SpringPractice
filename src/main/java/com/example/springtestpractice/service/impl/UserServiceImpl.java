package com.example.springtestpractice.service.impl;

import com.example.springtestpractice.model.Role;
import com.example.springtestpractice.model.User;
import com.example.springtestpractice.repository.RoleRepository;
import com.example.springtestpractice.repository.UserRepository;
import com.example.springtestpractice.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        List<User> result = userRepository.findAll();
        log.info("IN UserServiceImpl getAll - {} users found", result.size());

        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if(Objects.isNull(result)){
            log.warn("IN UserServiceImpl getById - user with id {} does not exist", id);
            return null;
        }

        log.info("IN UserServiceImpl getById - user with id {} found", id);

        return result;
    }

    @SneakyThrows
    @Override
    public User register(User user) {
        String login = user.getLogin();

        if(isUserPresent(login)){
            log.warn("IN UserServiceImpl register - user with username {} already exist", login);
            throw new InstanceAlreadyExistsException("User with login " + login + " already exist");
        }

        Role userRole = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);

        User registeredUser = userRepository.save(user);

        log.info("IN UserServiceImpl register - user with username {} successfully created", login);

        return registeredUser;
    }

    @Override
    public User update(User user) {
        String login = user.getLogin();

        if(!isUserPresent(login)){
            log.warn("IN UserServiceImpl update - user with username {} does not exist", login);
            throw new UsernameNotFoundException("User with username " + login + " does not exist");
        }

        User updatedUser = userRepository.save(user);

        log.info("IN UserServiceImpl register - user with username {} successfully updated", login);

        return updatedUser;
    }

    @Override
    public User findByLogin(String login) {
        User result = userRepository.findByLogin(login);

        if (result == null) {
            log.warn("IN UserServiceImpl findByLogin - no user with '{}' login", login);
            return null;
        }

        log.info("IN UserServiceImpl findByLogin - user {} found", login);
        return result;
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);

        if(Objects.isNull(user)){
            log.warn("IN UserServiceImpl delete - user with id {} does not exist", id);
            return;
        }

        userRepository.deleteById(id);
        log.info("IN UserServiceImpl delete - user with id: {} successfully deleted", id);
    }

    private boolean isUserPresent(String login){
        User user = findByLogin(login);

        return user != null;
    }
}
