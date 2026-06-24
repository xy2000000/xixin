package com.fxy.xixin.exam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 体检预约实体类
 * <p>
 * 对应数据库 t_appointment 表，继承 {@link com.fxy.xixin.common.base.BaseEntity} 获得通用字段。
 * 记录用户的体检预约信息，包括预约日期、时间段、所选套餐、体检机构和分配医生。
 * 一个套餐可对应多名医生，患者选择套餐后指定具体医生，系统自动确认预约。
 * 预约状态流转：1-已确认（预约时自动确认）→ 2-已取消 / 3-已完成。
 * 在体检预约流程中，这是连接用户、套餐、机构、医生的核心纽带实体。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_appointment")
public class Appointment extends BaseEntity {

    /**
     * 用户ID，关联预约用户
     */
    private Long userId;
    /**
     * 套餐ID，关联选择的体检套餐
     */
    private Long packageId;
    /**
     * 机构ID，关联选择的体检机构
     */
    private Long institutionId;
    /**
     * 分配的医生对应的系统用户ID。
     * <p><b>重要：此字段存储的是 t_doctor.user_id（即 t_user.id），而非 t_doctor.id。</b>
     * 联表查询时应使用 {@code JOIN t_doctor d ON t_appointment.doctor_id = d.user_id}，
     * 而非 {@code ON t_appointment.doctor_id = d.id}。</p>
     */
    private Long doctorId;
    /**
     * 预约日期
     */
    private LocalDate appointmentDate;
    /**
     * 预约时间段（如：08:00-10:00）
     */
    private String timeSlot;
    /**
     * 预约状态：1=已确认（预约时自动确认），2=已取消，3=已完成
     */
    private Integer status;
    /**
     * 用户备注
     */
    private String remark;
}
