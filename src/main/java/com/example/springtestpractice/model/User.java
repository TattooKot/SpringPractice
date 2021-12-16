package com.example.springtestpractice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "users", indexes = {
        @Index(name = "users_login_key", columnList = "login", unique = true)
})
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login", nullable = false, length = 100)
    private String login;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "roles_id", referencedColumnName = "id")})
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Event> events;

}