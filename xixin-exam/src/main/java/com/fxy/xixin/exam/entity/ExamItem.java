package com.fxy.xixin.exam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 体检项目实体类
 * <p>
 * 对应数据库 t_exam_item 表，继承 {@link com.fxy.xixin.common.base.BaseEntity} 获得通用字段。
 * 体检项目是套餐的组成部分，每个套餐包含多个具体的检查项目（如血常规、心电图、B超等）。
 * 通过 packageId 关联所属的 {@link ExamPackage} 套餐。
 * 在体检预约流程中，用户查看套餐详情时可以看到套餐包含的所有体检项目。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_exam_item")
public class ExamItem extends BaseEntity {

    /**
     * 所属套餐ID，关联 t_exam_package 表
     */
    private Long packageId;
    /**
     * 项目名称（如：血常规、心电图）
     */
    private String name;
    /**
     * 项目类型（如：检验、影像、功能检查）
     */
    private String type;
    /**
     * 项目描述
     */
    private String description;
    /**
     * 排序序号，数值越小越靠前
     */
    private Integer sortOrder;
}
