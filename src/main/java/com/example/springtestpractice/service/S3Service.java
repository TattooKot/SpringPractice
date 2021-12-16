package com.example.springtestpractice.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

//    List<String> getAllFileNames();

    String uploadFile(String login, MultipartFile multipartFile);

    void deleteFile(String name);

}
