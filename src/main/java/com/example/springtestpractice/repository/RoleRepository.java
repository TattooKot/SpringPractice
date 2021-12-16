package com.example.springtestpractice.repository;

import com.example.springtestpractice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}