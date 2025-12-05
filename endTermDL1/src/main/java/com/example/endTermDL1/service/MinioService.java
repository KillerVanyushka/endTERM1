package com.example.endTermDL1.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    public void uploadFile(String objectName, InputStream inputStream, String contentType, long size) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, size, -1)
                    .contentType(contentType)
                    .build());
            log.info("Файл '{}' успешно загружен в бакет '{}'", objectName, bucketName);
        } catch (Exception e) {
            log.error("Ошибка при загрузке файла '{}': {}", objectName, e.getMessage(), e);
            throw new RuntimeException("Не удалось загрузить файл: " + objectName, e);
        }
    }

    public void uploadFileFromStream(String objectName, InputStream inputStream, String contentType, long size) {
        uploadFile(objectName, inputStream, contentType, size);
    }
}

