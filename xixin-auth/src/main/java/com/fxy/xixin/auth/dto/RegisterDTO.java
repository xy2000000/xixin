package com.fxy.xixin.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 注册请求数据传输对象
 * <p>
 * 接收前端注册表单提交的用户注册信息，
 * 包含用户名、密码、姓名、手机号、邮箱和角色等字段。
 * </p>
 *
 * @author dev
 */
@Data
public class RegisterDTO {

    /** 登录用户名，不能为空 */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /** 登录密码，不能为空 */
    @NotBlank(message = "密码不能为空")
    private String password;

    /** 用户真实姓名，不能为空 */
    @NotBlank(message = "姓名不能为空")
    private String realName;

    /** 手机号码，需符合中国大陆手机号格式 */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /** 电子邮箱 */
    private String email;

    /** 用户角色（patient/doctor/admin），不能为空 */
    @NotBlank(message = "角色不能为空")
    private String role;
}
