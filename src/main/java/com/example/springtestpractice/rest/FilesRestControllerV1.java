package com.example.springtestpractice.rest;

import com.example.springtestpractice.dto.FileDto;
import com.example.springtestpractice.model.Event;
import com.example.springtestpractice.model.File;
import com.example.springtestpractice.model.User;
import com.example.springtestpractice.security.jwt.JwtTokenProvider;
import com.example.springtestpractice.service.EventService;
import com.example.springtestpractice.service.FileService;
import com.example.springtestpractice.service.S3Service;
import com.example.springtestpractice.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/files/")
public class FilesRestControllerV1 {

    private final JwtTokenProvider jwtTokenProvider;
    private final S3Service s3Service;
    private final UserService userService;
    private final FileService fileService;
    private final EventService eventService;

    public FilesRestControllerV1(
            JwtTokenProvider jwtTokenProvider,
            S3Service s3Service,
            UserService userService,
            FileService fileService,
            EventService eventService
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.s3Service = s3Service;
        this.userService = userService;
        this.fileService = fileService;
        this.eventService = eventService;
    }

    @SneakyThrows
    @PostMapping(value = "upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileDto> createFile(@RequestHeader("Authorization") String token, @RequestPart("file") MultipartFile multipartFile){
        String login = jwtTokenProvider.getUserName(token.substring(7));

        String fileName = login + "_" + multipartFile.getOriginalFilename();
        String fileLocation = s3Service.uploadFile(login, multipartFile);

        File uploaded = fileService.create(new File(fileName, fileLocation));

        User user = userService.findByLogin(login);

        eventService.create(new Event(user.getId(), uploaded.getId()));

        log.info("IN FilesRestControllerV1 createFile - {} successfully added", fileName);

        FileDto fileDto = new FileDto(uploaded, login);

        return new ResponseEntity<>(fileDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity deleteFile(@PathVariable Long id){
        File file = fileService.findById(id);
        String fileName = file.getFileName();

        Event event = eventService.findByFile_Id(id);
        eventService.delete(event.getId());

        fileService.delete(id);

        s3Service.deleteFile(fileName);

        log.info("IN FilesRestControllerV1 deleteFile - {} successfully deleted", fileName);

        return new ResponseEntity(HttpStatus.OK);
    }
}
