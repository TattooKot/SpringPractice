package com.example.springtestpractice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "events")
@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    @JsonBackReference
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="file_id", referencedColumnName = "id")
    private File file;

    @Column(name = "created")
    private Date created;


    public Event() {
    }

    public Event(Long userId, Long fileId) {
        this.user = new User();
        this.user.setId(userId);
        this.file = new File();
        this.file.setId(fileId);
    }
}