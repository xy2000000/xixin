package com.fxy.xixin.common.result;

import com.fxy.xixin.common.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一响应结果封装类
 * <p>
 * 用于封装所有 Controller 接口的返回结果，包含状态码、提示消息和响应数据。
 * 提供静态工厂方法快速构建成功或失败响应，是整个体检预约系统前后端交互的标准数据格式。
 * </p>
 *
 * @param <T> 响应数据的类型
 * @author dev
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {

    /**
     * 状态码，与 HTTP 状态码及业务错误码对应
     */
    private Integer code;
    /**
     * 提示消息，用于前端展示
     */
    private String message;
    /**
     * 响应数据，可为任意类型
     */
    private T data;

    /**
     * 构建成功响应（带数据）
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return 成功响应结果
     */
    public static <T> R<T> ok(T data) {
        return new R<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), data);
    }

    /**
     * 构建成功响应（无数据）
     *
     * @param <T> 数据类型
     * @return 成功响应结果
     */
    public static <T> R<T> ok() {
        return ok(null);
    }

    /**
     * 构建失败响应（自定义状态码和消息）
     *
     * @param code    错误码
     * @param message 错误消息
     * @param <T>     数据类型
     * @return 失败响应结果
     */
    public static <T> R<T> fail(Integer code, String message) {
        return new R<>(code, message, null);
    }

    /**
     * 构建失败响应（使用 ErrorCode 枚举）
     *
     * @param error 错误码枚举
     * @param <T>   数据类型
     * @return 失败响应结果
     */
    public static <T> R<T> fail(ErrorCode error) {
        return new R<>(error.getCode(), error.getMsg(), null);
    }

    /**
     * 构建失败响应（使用 ErrorCode 枚举，自定义消息覆盖默认消息）
     *
     * @param error   错误码枚举
     * @param message 自定义错误消息
     * @param <T>     数据类型
     * @return 失败响应结果
     */
    public static <T> R<T> fail(ErrorCode error, String message) {
        return new R<>(error.getCode(), message, null);
    }
}
