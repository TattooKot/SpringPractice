package com.example.springtestpractice.service;

import com.example.springtestpractice.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User register(User user);

    User update(User user);

    User findByLogin(String login);

    void delete(Long id);
}
