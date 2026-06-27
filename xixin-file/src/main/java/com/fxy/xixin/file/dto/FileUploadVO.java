package com.fxy.xixin.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 文件上传返回值
 */
@Data
@AllArgsConstructor
public class FileUploadVO {

    private String objectKey;
    private String fileUrl;
    private Long fileSize;
    private String contentType;
}