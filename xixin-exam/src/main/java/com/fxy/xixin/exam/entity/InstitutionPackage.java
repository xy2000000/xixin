package com.fxy.xixin.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机构-套餐关联实体类
 * <p>
 * 对应数据库 t_institution_package 表。
 * 描述体检机构与体检套餐的多对多关联关系，即哪些机构提供哪些体检套餐。
 * 在体检预约流程中，用户选择机构后，根据此关联表展示该机构可提供的套餐列表；
 * 或用户选择套餐后，展示可提供该套餐的机构列表。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Data
@TableName("t_institution_package")
public class InstitutionPackage implements Serializable {

    /**
     * 主键ID，自增策略
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 机构ID，关联 t_institution 表
     */
    private Long institutionId;
    /**
     * 套餐ID，关联 t_exam_package 表
     */
    private Long packageId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
