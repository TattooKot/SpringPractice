package com.example.springtestpractice.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "files", indexes = {
        @Index(name = "files_location_key", columnList = "location", unique = true)
})
@Entity
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "location", nullable = false)
    private String location;

    public File() {
    }

    public File(String fileName, String location) {
        this.fileName = fileName;
        this.location = location;
    }
}