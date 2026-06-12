package com.fxy.xixin.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户实体类，对应数据库表 t_user
 * <p>
 * 存储体检预约系统中所有用户（体检人、医生、管理员）的账号信息。
 * 用户登录认证、权限校验均依赖此实体。
 * </p>
 *
 * @author dev
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class User extends BaseEntity {

    /** 登录用户名 */
    private String username;

    /** 登录密码（明文存储） */
    private String password;

    /** 用户真实姓名 */
    private String realName;

    /** 手机号码 */
    private String phone;

    /** 电子邮箱 */
    private String email;

    /** 头像地址 */
    private String avatar;

    /** 用户角色（patient/doctor/admin） */
    private String role;

    /** 账号状态：0-禁用，1-启用 */
    private Integer status;
}
