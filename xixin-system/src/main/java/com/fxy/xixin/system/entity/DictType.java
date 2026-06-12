package com.fxy.xixin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型实体类，对应数据库表 t_dict_type
 * <p>
 * 定义数据字典的分类信息。在体检预约系统中，
 * 字典类型用于对各类枚举值进行分组管理，
 * 例如：体检项目类别、性别、预约状态、科室类型等。
 * 每个字典类型下包含多个字典项（DictItem）。
 * </p>
 *
 * @author dev
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_dict_type")
public class DictType extends BaseEntity {

    /** 字典名称（如"体检项目类别"） */
    private String dictName;

    /** 字典类型编码（如"exam_category"） */
    private String dictType;

    /** 状态：0-禁用，1-启用 */
    private Integer status;
}
