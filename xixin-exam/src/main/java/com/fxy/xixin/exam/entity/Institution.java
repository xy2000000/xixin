package com.fxy.xixin.exam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 体检机构实体类
 * <p>
 * 对应数据库 t_institution 表，继承 {@link com.fxy.xixin.common.base.BaseEntity} 获得通用字段。
 * 体检机构是提供体检服务的场所（如医院体检中心、专业体检机构等），
 * 每个机构可提供多个体检套餐（通过 {@link InstitutionPackage} 关联）。
 * 在体检预约流程中，用户选择套餐后需要选择就近或合适的体检机构进行预约。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_institution")
public class Institution extends BaseEntity {

    /**
     * 机构名称
     */
    private String name;
    /**
     * 机构地址
     */
    private String address;
    /**
     * 机构联系电话
     */
    private String phone;
    /**
     * 营业时间（如：08:00-17:00）
     */
    private String businessHours;
    /**
     * 机构描述
     */
    private String description;
    /**
     * 机构状态：0-停用，1-启用
     */
    private Integer status;
    /**
     * 排序序号，数值越小越靠前
     */
    private Integer sortOrder;
}
