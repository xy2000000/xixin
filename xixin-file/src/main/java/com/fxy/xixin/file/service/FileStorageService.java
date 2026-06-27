package com.fxy.xixin.file.service;

import java.io.InputStream;

/**
 * 文件存储服务接口
 * <p>
 * 抽象对象存储操作，支持 MinIO / S3 兼容协议。
 * 后续如需切换到阿里云OSS等，添加新实现类即可。
 * </p>
 */
public interface FileStorageService {

    /**
     * 上传文件
     *
     * @param inputStream 文件流
     * @param objectKey   对象在存储中的路径（如 external-reports/1/uuid.pdf）
     * @param contentType MIME类型
     * @return 可访问的文件URL
     */
    String upload(InputStream inputStream, String objectKey, String contentType);

    /**
     * 获取预签名下载URL（临时有效）
     *
     * @param objectKey 对象路径
     * @return 预签名URL，过期后不可访问
     */
    String getPresignedUrl(String objectKey);

    /**
     * 删除文件
     *
     * @param objectKey 对象路径
     */
    void delete(String objectKey);

    /**
     * 获取文件对象（用于直接下载代理）
     *
     * @param objectKey 对象路径
     * @return 文件输入流
     */
    InputStream getObject(String objectKey);
}