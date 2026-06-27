package com.fxy.xixin.file.service.impl;

import com.fxy.xixin.file.config.FileStorageConfig;
import com.fxy.xixin.file.service.FileStorageService;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioFileStorageService implements FileStorageService {

    private final MinioClient minioClient;
    private final FileStorageConfig storageConfig;

    @Override
    public String upload(InputStream inputStream, String objectKey, String contentType) {
        try {
            ensureBucketExists();

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(storageConfig.getBucket())
                    .object(objectKey)
                    .stream(inputStream, -1, 10485760)
                    .contentType(contentType)
                    .build());

            log.info("文件上传成功: bucket={}, objectKey={}", storageConfig.getBucket(), objectKey);
            return "%s/%s/%s".formatted(storageConfig.getEndpoint(), storageConfig.getBucket(), objectKey);
        } catch (Exception e) {
            log.error("文件上传失败: objectKey={}", objectKey, e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public String getPresignedUrl(String objectKey) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(storageConfig.getBucket())
                    .object(objectKey)
                    .expiry((int) storageConfig.getPresignedUrlExpiry(), TimeUnit.SECONDS)
                    .build());
        } catch (Exception e) {
            log.error("获取预签名URL失败: objectKey={}", objectKey, e);
            return null;
        }
    }

    @Override
    public void delete(String objectKey) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(storageConfig.getBucket())
                    .object(objectKey)
                    .build());
            log.info("文件删除成功: bucket={}, objectKey={}", storageConfig.getBucket(), objectKey);
        } catch (Exception e) {
            log.error("文件删除失败: objectKey={}", objectKey, e);
            throw new RuntimeException("文件删除失败", e);
        }
    }

    @Override
    public InputStream getObject(String objectKey) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(storageConfig.getBucket())
                    .object(objectKey)
                    .build());
        } catch (Exception e) {
            log.error("获取文件对象失败: objectKey={}", objectKey, e);
            throw new RuntimeException("文件获取失败", e);
        }
    }

    private void ensureBucketExists() {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(storageConfig.getBucket())
                    .build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(storageConfig.getBucket())
                        .build());
                log.info("MinIO Bucket 已自动创建: {}", storageConfig.getBucket());
            }
        } catch (Exception e) {
            log.warn("检查/创建Bucket失败: {}", e.getMessage());
        }
    }
}