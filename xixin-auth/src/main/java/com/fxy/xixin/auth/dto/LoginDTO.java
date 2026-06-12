package com.fxy.xixin.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录请求数据传输对象
 * <p>
 * 接收前端登录表单提交的用户名和密码，
 * 经过参数校验后传递给 AuthService 进行认证处理。
 * </p>
 *
 * @author dev
 */
@Data
public class LoginDTO {

    /** 登录用户名，不能为空 */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /** 登录密码，不能为空 */
    @NotBlank(message = "密码不能为空")
    private String password;
}
