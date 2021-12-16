package com.example.springtestpractice.service.impl;

import com.example.springtestpractice.model.File;
import com.example.springtestpractice.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    @Test
    void findAll() {
        List<File> files = new ArrayList<>();
        files.add(new File());
        files.add(new File());

        doReturn(files).when(fileRepository).findAll();

        List<File> result = fileService.findAll();

        verify(fileRepository, atLeastOnce()).findAll();
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(files, result);
    }

    @Test
    void findById() {
        File file = new File();

        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));

        File result = fileService.findById(1L);

        verify(fileRepository, atLeastOnce()).findById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(file, result);
    }

    @Test
    void create() {
        File file = new File();

        when(fileRepository.save(file)).thenReturn(file);

        File result = fileService.create(file);

        verify(fileRepository, atLeastOnce()).save(file);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(file, result);
    }

    @Test
    void update() {
        File file = new File();
        file.setId(1L);

        when(fileRepository.save(file)).thenReturn(file);
        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));

        File result = fileService.update(file);

        verify(fileRepository, atLeastOnce()).save(file);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(file, result);
    }

    @Test
    void delete() {
        File file = new File();

        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));

        fileService.delete(1L);

        verify(fileRepository).deleteById(1L);
    }
}