package com.example.springtestpractice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.example.springtestpractice.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
@Slf4j
public class S3ServiceImpl implements S3Service {

    @Value("${aws.bucket}")
    private String bucket;

    private final AmazonS3 s3Client;

    public S3ServiceImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(String login, MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();

        if(fileName == null){
            log.warn("IN S3ServiceImpl uploadFile - request without file in body");
            throw new IllegalArgumentException();
        }

        fileName = login + "_" + fileName;

        File file = getFileFromMultipartFile(fileName, multipartFile);

        s3Client.putObject(bucket, fileName, file);

        log.info("IN S3ServiceImpl uploadFile - file {} successfully uploaded", fileName);

        file.delete();

        return s3Client.getUrl(bucket, fileName).toString();
    }

    @Override
    public void deleteFile(String name){
        s3Client.deleteObject(bucket, name);

        log.info("IN S3ServiceImpl deleteFile - file {} successfully deleted", name);
    }



    private File getFileFromMultipartFile(String fileName,MultipartFile multipartFile) {
        File file = new java.io.File(fileName);

        try(BufferedInputStream inputStream =
                    new BufferedInputStream(multipartFile.getInputStream());
            BufferedOutputStream outputStream =
                    new BufferedOutputStream(
                            new FileOutputStream(file));
            ByteArrayOutputStream buffer =
                    new ByteArrayOutputStream())
        {
            byte[] data = new byte[1024];
            int current;
            while((current = inputStream.read(data,0, data.length)) != -1){
                buffer.write(data,0,current);
            }
            outputStream.write(buffer.toByteArray());
        } catch (IOException e) {
            log.warn("IN S3ServiceImpl getFileFromMultipartFile - problem with {} file", fileName);
            e.printStackTrace();
        }

        log.info("IN S3ServiceImpl getFileFromMultipartFile - file {} created", fileName);

        return file;
    }
}
