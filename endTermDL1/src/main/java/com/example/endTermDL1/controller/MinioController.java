package com.example.endTermDL1.controller;

import com.example.endTermDL1.service.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class MinioController {

    private final MinioService minioService;

    @PostMapping("/upload/txt")
    public ResponseEntity<Map<String, Object>> uploadTxtFile(
            @RequestParam("file") MultipartFile file) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (file.isEmpty()) {
                response.put("success", false);
                response.put("message", "Файл не может быть пустым");
                return ResponseEntity.badRequest().body(response);
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".txt")) {
                response.put("success", false);
                response.put("message", "Файл должен иметь расширение .txt");
                return ResponseEntity.badRequest().body(response);
            }

            minioService.uploadFileFromStream(
                    originalFilename,
                    file.getInputStream(),
                    "text/plain",
                    file.getSize()
            );

            response.put("success", true);
            response.put("message", "TXT файл успешно загружен");
            response.put("filename", originalFilename);
            response.put("size", file.getSize());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Ошибка при загрузке TXT файла: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "Ошибка при загрузке файла: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/upload/png")
    public ResponseEntity<Map<String, Object>> uploadPngFile(
            @RequestParam("file") MultipartFile file) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (file.isEmpty()) {
                response.put("success", false);
                response.put("message", "Файл не может быть пустым");
                return ResponseEntity.badRequest().body(response);
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".png")) {
                response.put("success", false);
                response.put("message", "Файл должен иметь расширение .png");
                return ResponseEntity.badRequest().body(response);
            }

            minioService.uploadFileFromStream(
                    originalFilename,
                    file.getInputStream(),
                    "image/png",
                    file.getSize()
            );

            response.put("success", true);
            response.put("message", "PNG файл успешно загружен");
            response.put("filename", originalFilename);
            response.put("size", file.getSize());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Ошибка при загрузке PNG файла: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "Ошибка при загрузке файла: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

