package com.example.springtestpractice.service;

import com.example.springtestpractice.model.File;

import java.util.List;

public interface FileService {

    List<File> findAll();

    File findById(Long id);

    File create(File file);

    File update(File file);

    void delete(Long id);
}
