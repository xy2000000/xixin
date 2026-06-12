package com.fxy.xixin.common.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体基类，所有数据库实体类的父类
 * <p>
 * 封装通用字段：主键ID、创建/更新时间、创建/更新人、逻辑删除标记。
 * 配合 MyBatis-Plus 的自动填充功能，在插入和更新时自动设置时间戳和操作人。
 * 子类通过继承即可获得这些公共属性，无需重复定义。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Data
public abstract class BaseEntity implements Serializable {

    /**
     * 主键ID，自增策略
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间，插入时自动填充
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间，插入和更新时自动填充
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人ID，插入时自动填充
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 更新人ID，插入和更新时自动填充
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * 逻辑删除标记（0-未删除，1-已删除），插入时默认0
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
