package com.fxy.xixin.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fxy.xixin.auth.dto.LoginDTO;
import com.fxy.xixin.auth.dto.RegisterDTO;
import com.fxy.xixin.auth.dto.TokenResult;
import com.fxy.xixin.auth.entity.Patient;
import com.fxy.xixin.auth.entity.User;
import com.fxy.xixin.auth.mapper.PatientMapper;
import com.fxy.xixin.auth.mapper.UserMapper;
import com.fxy.xixin.auth.service.AuthService;
import com.fxy.xixin.common.constant.ErrorCode;
import com.fxy.xixin.common.exception.BusinessException;
import com.fxy.xixin.common.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现类
 * <p>
 * 实现用户登录和注册的核心业务逻辑：
 * <ul>
 *   <li>登录：校验用户是否存在、账号是否启用、密码是否正确（明文比对），通过后签发JWT令牌</li>
 *   <li>注册：校验用户名唯一性，密码明文存储，默认状态为启用</li>
 * </ul>
 * 登录流程是体检预约系统的第一步，只有认证通过的用户才能进行后续的预约操作。
 * </p>
 *
 * @author dev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PatientMapper patientMapper;

    /**
     * 用户登录认证实现
     * <p>
     * 登录流程分三步：
     * <ol>
     *   <li>根据用户名查询用户，不存在则报错</li>
     *   <li>检查账号状态，被禁用则报错</li>
     *   <li>校验密码（明文比对），不匹配则报错</li>
     * </ol>
     * 全部通过后签发 JWT 访问令牌和刷新令牌。
     * </p>
     *
     * @param dto 登录请求参数（用户名、密码）
     * @return JWT令牌结果，包含accessToken、refreshToken和过期时间（7200秒）
     * @throws BusinessException 用户不存在/账号被禁用/密码错误时抛出
     */
    @Override
    public TokenResult login(LoginDTO dto) {
        // 第一步：查询用户
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, dto.getUsername()));
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST);
        }

        // 第二步：检查账号是否被禁用
        if (user.getStatus() == 0) {
            throw new BusinessException(ErrorCode.USER_ACCOUNT_DISABLED);
        }

        // 第三步：校验密码（明文比对）
        if (!dto.getPassword().equals(user.getPassword())) {
            throw new BusinessException(ErrorCode.USER_PASSWORD_ERROR);
        }

        // 登录成功，签发 JWT 令牌
        String accessToken = JwtUtils.generateAccessToken(user.getId(), user.getUsername(), user.getRole());
        String refreshToken = JwtUtils.generateRefreshToken(user.getId(), user.getUsername());
        return new TokenResult(accessToken, refreshToken, 7200L);
    }

    /**
     * 用户注册实现
     * <p>
     * 注册流程：
     * <ol>
     *   <li>检查用户名是否已被占用</li>
     *   <li>创建用户记录（密码明文存储，默认状态为启用）</li>
     * </ol>
     * </p>
     *
     * @param dto 注册请求参数（用户名、密码、姓名、手机号、角色等）
     * @throws BusinessException 用户名已存在时抛出
     */
    @Override
    public void updateAvatar(Long userId, String avatarUrl) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST);
        }
        user.setAvatar(avatarUrl);
        userMapper.updateById(user);
    }

    @Override
    public void register(RegisterDTO dto) {
        // 检查用户名是否已存在
        boolean usernameTaken = userMapper.exists(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, dto.getUsername()));
        if (usernameTaken) {
            throw new BusinessException(ErrorCode.USERNAME_EXIST);
        }

        // 创建用户对象，密码明文存储
        User user = BeanUtil.copyProperties(dto, User.class);

        // 新注册用户默认启用
        user.setStatus(1);

        userMapper.insert(user);

        // 如果注册的是患者，同步创建体检人档案
        if ("PATIENT".equalsIgnoreCase(user.getRole())) {
            Patient patient = new Patient();
            patient.setUserId(user.getId());
            patient.setName(user.getRealName());
            patientMapper.insert(patient);
        }
    }
}
