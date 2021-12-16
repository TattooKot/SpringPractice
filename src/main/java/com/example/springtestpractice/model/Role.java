package com.example.springtestpractice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "roles", indexes = {
        @Index(name = "roles_name_key", columnList = "name", unique = true)
})
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<User> users;

}