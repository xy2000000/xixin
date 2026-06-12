package com.fxy.xixin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典项实体类，对应数据库表 t_dict_item
 * <p>
 * 存储数据字典的具体枚举值。每条字典项属于一个字典类型（DictType）。
 * 在体检预约系统中，字典项用于提供前端下拉框、单选按钮等
 * 组件的选项数据，如体检项目类别下的"常规检查"、"血液检查"等。
 * </p>
 *
 * @author dev
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_dict_item")
public class DictItem extends BaseEntity {

    /** 所属字典类型ID，关联 t_dict_type 表 */
    private Long dictTypeId;

    /** 字典项显示标签（如"常规检查"） */
    private String label;

    /** 字典项存储值（如"routine"） */
    private String value;

    /** 排序序号 */
    private Integer sortOrder;

    /** 状态：0-禁用，1-启用 */
    private Integer status;
}
