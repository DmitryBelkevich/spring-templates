package com.hard.controllers;

import com.hard.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping(value = "/preview/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<InputStreamResource> getFileContent(@PathVariable("id") long id) {
        InputStream inputStream = fileService.getFileInputStreamById(id);

        if (inputStream == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(new InputStreamResource(inputStream), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<InputStreamResource> getFile(@PathVariable("id") long id) {
        InputStream inputStream = fileService.getFileInputStreamById(id);

        if (inputStream == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + "\"");

        return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileService.add(file.getOriginalFilename(), bytes);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/multi", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addFiles(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                byte[] bytes = null;
                try {
                    bytes = file.getBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                fileService.add(file.getOriginalFilename(), bytes);
            }
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable("id") long id) {
        File file = fileService.getById(id);

        if (file == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        fileService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteFiles() {
        fileService.clear();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
