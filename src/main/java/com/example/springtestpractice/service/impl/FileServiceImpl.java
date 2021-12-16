package com.example.springtestpractice.service.impl;

import com.example.springtestpractice.model.File;
import com.example.springtestpractice.repository.FileRepository;
import com.example.springtestpractice.service.FileService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public List<File> findAll() {
        List<File> result = fileRepository.findAll();
        log.info("IN FileServiceImpl getAll - {} files found", result.size());

        return result;
    }

    @Override
    public File findById(Long id) {
        File result = fileRepository.findById(id).orElse(null);

        if(Objects.isNull(result)){
            log.warn("IN FileServiceImpl getById - file with id {} does not exist", id);
            return null;
        }

        log.info("IN FileServiceImpl getById - file with id {} found", id);

        return result;
    }

    @SneakyThrows
    @Override
    public File create(File file) {
        String fileName = file.getFileName();

        File createdFile = fileRepository.save(file);

        log.info("IN FileServiceImpl create - file {} successfully created", fileName);

        return createdFile;
    }

    @Override
    public File update(File file) {
        String fileName = file.getFileName();

        if(findById(file.getId()) == null){
            log.warn("IN FileServiceImpl update - file {} does not exist", fileName);
            throw new IllegalArgumentException("File " + fileName + " does not exist");
        }

        File updatedFile = fileRepository.save(file);

        log.info("IN FileServiceImpl update - file {} successfully updated", fileName);

        return updatedFile;
    }

    @Override
    public void delete(Long id) {
        File file = findById(id);

        if(Objects.isNull(file)){
            log.warn("IN FileServiceImpl delete - file with id {} does not exist", id);
            return;
        }

        fileRepository.deleteById(id);
        log.info("IN FileServiceImpl delete - file with id {} successfully deleted", id);
    }
}
