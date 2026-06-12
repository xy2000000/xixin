package com.fxy.xixin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体类，对应数据库表 t_role
 * <p>
 * 定义系统中的角色信息，用于权限控制。
 * 体检预约系统中常见的角色包括：体检人（patient）、
 * 医生（doctor）、管理员（admin）等。
 * 角色编码（roleCode）与用户表中的 role 字段关联。
 * </p>
 *
 * @author dev
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_role")
public class Role extends BaseEntity {

    /** 角色名称 */
    private String roleName;

    /** 角色编码（patient/doctor/admin） */
    private String roleCode;

    /** 角色描述 */
    private String description;

    /** 状态：0-禁用，1-启用 */
    private Integer status;
}
