package com.example.springtestpractice.dto;

import com.example.springtestpractice.model.File;
import lombok.Data;

@Data
public class FileDto {

    private Long id;
    private String user;
    private String fileName;
    private String location;

    public FileDto(File file, String login) {
        this.id = file.getId();
        this.user = login;
        this.fileName = file.getFileName();
        this.location = file.getLocation();
    }
}
