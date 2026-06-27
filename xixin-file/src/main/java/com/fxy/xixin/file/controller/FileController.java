package com.fxy.xixin.file.controller;

import com.fxy.xixin.common.annotation.RequireRole;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.file.dto.FileUploadVO;
import com.fxy.xixin.file.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * 文件上传控制器
 * <p>
 * 提供文件上传和下载功能，底层使用 MinIO 对象存储。
 * 主要用于用户头像等图片文件的上传。
 * </p>
 */
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    /**
     * 上传文件（头像/图片等）
     * <p>
     * 上传成功后返回文件访问 URL，前端拿到 URL 后调用业务接口保存。
     * 例如头像：上传 → 拿到 URL → PUT /api/auth/avatar?avatarUrl=...
     * </p>
     *
     * <p><b>权限：PATIENT、DOCTOR、ADMIN</b></p>
     */
    @PostMapping("/upload")
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    public R<FileUploadVO> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return R.fail(400, "上传文件不能为空");
        }

        String originalName = file.getOriginalFilename();
        String ext = "";
        if (originalName != null && originalName.contains(".")) {
            ext = originalName.substring(originalName.lastIndexOf("."));
        }
        String objectKey = "uploads/%s%s".formatted(UUID.randomUUID(), ext);
        String contentType = file.getContentType();
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        try (InputStream is = file.getInputStream()) {
            String fileUrl = fileStorageService.upload(is, objectKey, contentType);
            return R.ok(new FileUploadVO(objectKey, fileUrl, file.getSize(), contentType));
        } catch (Exception e) {
            return R.fail(500, "文件上传失败: " + e.getMessage());
        }
    }
}