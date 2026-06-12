package com.fxy.xixin.common.exception;

import com.fxy.xixin.common.constant.ErrorCode;
import lombok.Getter;

/**
 * 业务异常类
 * <p>
 * 用于在业务逻辑中抛出可预期的异常，包含错误码和错误消息。
 * 由 {@link GlobalExceptionHandler} 统一捕获并转换为标准响应 {@link com.fxy.xixin.common.result.R}。
 * 在体检预约流程中，如套餐不存在、预约冲突等场景均使用此异常。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 业务错误码
     */
    private final Integer code;

    /**
     * 使用 ErrorCode 枚举构建异常，消息使用枚举的默认消息
     *
     * @param errorCode 错误码枚举
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    /**
     * 使用 ErrorCode 枚举构建异常，自定义消息覆盖默认消息
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误消息
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    /**
     * 使用自定义错误码和消息构建异常
     *
     * @param code    自定义错误码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
