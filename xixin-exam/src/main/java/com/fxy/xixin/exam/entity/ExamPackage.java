package com.fxy.xixin.exam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 体检套餐实体类
 * <p>
 * 对应数据库 t_exam_package 表，继承 {@link com.fxy.xixin.common.base.BaseEntity} 获得通用字段。
 * 体检套餐是体检预约系统的核心实体，包含套餐名称、价格、描述等信息。
 * 一个套餐可以包含多个体检项目（{@link ExamItem}）。
 * 在体检预约流程中，用户首先浏览套餐列表，选择心仪的套餐后进行预约。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_exam_package")
public class ExamPackage extends BaseEntity {

    /**
     * 套餐名称
     */
    private String name;
    /**
     * 套餐价格
     */
    private BigDecimal price;
    /**
     * 套餐描述
     */
    private String description;
    /**
     * 绑定医生对应的系统用户ID。
     * <p><b>重要：此字段存储的是 t_doctor.user_id（即 t_user.id），而非 t_doctor.id。</b>
     * 联表查询时应使用 {@code JOIN t_doctor d ON t_exam_package.doctor_id = d.user_id}，
     * 而非 {@code ON t_exam_package.doctor_id = d.id}。</p>
     */
    private Long doctorId;
    /**
     * 套餐状态：0-下架，1-上架
     */
    private Integer status;
    /**
     * 排序序号，数值越小越靠前
     */
    private Integer sortOrder;

    /**
     * 套餐包含的检查项目列表（非数据库字段，仅详情查询时填充）
     */
    @TableField(exist = false)
    private List<ExamItem> items;
}
