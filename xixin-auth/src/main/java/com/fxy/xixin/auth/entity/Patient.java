package com.fxy.xixin.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 体检人实体类（认证模块使用），对应数据库表 t_patient
 * <p>
 * 用于注册时同步创建体检人档案。
 * </p>
 *
 * @author dev
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_patient")
public class Patient extends BaseEntity {

    /** 关联的系统用户ID */
    private Long userId;

    /** 体检人姓名 */
    private String name;

    /** 性别：0-女，1-男 */
    private Integer gender;

    /** 出生日期 */
    private LocalDate birthday;

    /** 身份证号码 */
    private String idCard;

    /** 居住地址 */
    private String address;

    /** 紧急联系人姓名 */
    private String emergencyContact;

    /** 紧急联系人电话 */
    private String emergencyPhone;
}
