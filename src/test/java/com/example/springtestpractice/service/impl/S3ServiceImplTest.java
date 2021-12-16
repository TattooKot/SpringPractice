package com.example.springtestpractice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3ServiceImplTest {
    @Value("${aws.bucket}")
    private String bucket;

    @Mock
    private AmazonS3 s3Client;

    @InjectMocks
    private S3ServiceImpl s3Service;

    @SneakyThrows
    @Test
    void uploadFile() {
        String login = "test";
        String filePath = "src/test/java/com/example/springtestpractice/service/impl/testFile.jpg";

        File file = new File(filePath);

        String fileName = login + "_";
        String expected = "https://"+ bucket + ".s3.us-west-2.amazonaws.com/" + fileName;

        when(s3Client.getUrl(bucket,fileName)).thenReturn(new URL(expected));

        MultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(file));

        String result = s3Service.uploadFile(login, multipartFile);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void deleteFile() {
        String fileName = "file.txt";

        s3Service.deleteFile(fileName);

        verify(s3Client).deleteObject(bucket, fileName);
    }
}