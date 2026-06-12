package com.fxy.xixin.common.exception;

import com.fxy.xixin.common.constant.ErrorCode;
import com.fxy.xixin.common.result.R;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * <p>
 * 使用 Spring 的 {@code @RestControllerAdvice} 统一拦截 Controller 层抛出的异常，
 * 转换为标准响应格式 {@link R} 返回给前端。
 * 处理三种异常类型：
 * <ul>
 *   <li>{@link BusinessException} - 业务异常，返回对应的业务错误码和消息</li>
 *   <li>{@link MethodArgumentNotValidException} - 请求参数校验失败（@Valid 注解触发）</li>
 *   <li>{@link BindException} - 表单参数绑定失败</li>
 *   <li>{@link Exception} - 其他未知异常，返回 500 内部错误，避免敏感信息泄露</li>
 * </ul>
 * 在体检预约流程中，当用户提交不完整的预约信息、访问不存在的套餐等场景下，
 * 此处理器会将异常转换为友好的提示信息。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param e 业务异常
     * @return 包含错误码和消息的失败响应
     */
    @ExceptionHandler(BusinessException.class)
    public R<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常: code={}, message={}", e.getCode(), e.getMessage());
        return R.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理请求参数校验失败异常（@Valid 注解触发的校验）
     * <p>
     * 当请求体的 @Valid 校验不通过时，Spring 会抛出此异常。
     * 此方法从异常中提取所有字段级错误信息，拼接成易读的字符串返回。
     * </p>
     *
     * @param e 校验异常
     * @return 400 失败响应，包含字段级校验错误详情
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleValidation(MethodArgumentNotValidException e) {
        String msg = buildFieldErrorMsg(e.getBindingResult().getFieldErrors(), "参数校验失败");
        return R.fail(ErrorCode.BAD_REQUEST, msg);
    }

    /**
     * 处理表单参数绑定失败异常
     * <p>
     * 当请求参数无法绑定到目标对象时（如类型不匹配），抛出此异常。
     * 处理方式与 @Valid 校验失败一致，提取各字段的错误描述。
     * </p>
     *
     * @param e 绑定异常
     * @return 400 失败响应，包含字段级错误详情
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleBind(BindException e) {
        String msg = buildFieldErrorMsg(e.getBindingResult().getFieldErrors(), "参数绑定失败");
        return R.fail(ErrorCode.BAD_REQUEST, msg);
    }

    /**
     * 兜底异常处理，捕获所有未被明确处理的异常
     * <p>
     * 作为最后的安全网，避免系统内部错误细节直接暴露给前端。
     * 如果异常带有消息则使用该消息，否则使用异常类名作为提示。
     * </p>
     *
     * @param e 异常对象
     * @return 500 失败响应
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Void> handleException(Exception e) {
        log.error("服务器异常", e);

        // 提取异常消息：优先使用异常自带的 message，没有则用类名
        String msg;
        if (e.getMessage() != null) {
            msg = e.getMessage();
        } else {
            msg = e.getClass().getSimpleName();
        }

        return R.fail(ErrorCode.INTERNAL_ERROR.getCode(), msg);
    }

    /**
     * 将字段校验错误列表拼接为可读的错误信息字符串
     * <p>
     * 例如输入三条错误会拼接为：
     * "username: 不能为空; password: 长度至少6位; email: 格式不正确"
     * </p>
     *
     * @param fieldErrors   字段错误列表
     * @param defaultMsg    无错误详情时的默认提示
     * @return 拼接后的错误信息字符串
     */
    private String buildFieldErrorMsg(List<org.springframework.validation.FieldError> fieldErrors, String defaultMsg) {
        if (fieldErrors == null || fieldErrors.isEmpty()) {
            return defaultMsg;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldErrors.size(); i++) {
            org.springframework.validation.FieldError err = fieldErrors.get(i);
            if (i > 0) {
                sb.append("; ");
            }
            sb.append(err.getField()).append(": ").append(err.getDefaultMessage());
        }
        return sb.toString();
    }
}
