package com.fxy.xixin.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 医生实体类，对应数据库表 t_doctor
 * <p>
 * 存储体检预约系统中医生的专业信息，
 * 包括所属科室、职称、专长和个人简介等。
 * 医生通过关联 userId 与系统登录账号绑定，
 * 通过 packageId 关联负责的体检套餐（一个医生负责一个套餐，一个套餐可有多个医生）。
 * 在体检预约流程中负责执行各项检查项目。
 * </p>
 *
 * @author dev
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_doctor")
public class Doctor extends BaseEntity {

    /** 关联的系统用户ID */
    private Long userId;

    /** 负责的体检套餐ID（关联 t_exam_package.id），每个医生负责一个套餐，一个套餐可有多个医生 */
    private Long packageId;

    /** 所属科室（如内科、外科、眼科等） */
    private String department;

    /** 职称（如主任医师、副主任医师等） */
    private String title;

    /** 专业专长 */
    private String specialty;

    /** 医生个人简介 */
    private String introduction;
}
