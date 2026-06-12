package com.fxy.xixin.auth.controller;

import com.fxy.xixin.auth.dto.LoginDTO;
import com.fxy.xixin.auth.dto.RegisterDTO;
import com.fxy.xixin.auth.dto.TokenResult;
import com.fxy.xixin.auth.service.AuthService;
import com.fxy.xixin.common.result.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * <p>
 * 处理用户登录和注册请求，是体检预约系统的入口。
 * 登录成功后返回 JWT 令牌，注册成功后自动创建用户账号。
 * 此控制器的所有接口均为公开访问，无需登录即可调用。
 * </p>
 *
 * @author dev
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     * <p>
     * 校验用户名密码，验证通过后签发 JWT 访问令牌和刷新令牌。
     * </p>
     *
     * <p><b>权限：公开（无需认证）</b></p>
     *
     * @param dto 登录请求参数（用户名、密码）
     * @return JWT令牌信息（accessToken、refreshToken、过期时间）
     */
    @PostMapping("/login")
    public R<TokenResult> login(@Valid @RequestBody LoginDTO dto) {
        return R.ok(authService.login(dto));
    }

    /**
     * 用户注册
     * <p>
     * 创建新用户账号，密码经 MD5 加密后存入数据库。
     * </p>
     *
     * <p><b>权限：公开（无需认证）</b></p>
     *
     * @param dto 注册请求参数（用户名、密码、姓名、手机号、角色等）
     * @return 操作结果
     */
    @PostMapping("/register")
    public R<Void> register(@Valid @RequestBody RegisterDTO dto) {
        authService.register(dto);
        return R.ok();
    }
}