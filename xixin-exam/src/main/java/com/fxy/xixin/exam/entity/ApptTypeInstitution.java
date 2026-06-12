package com.fxy.xixin.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 预约类型-机构关联实体类
 * <p>
 * 对应数据库 t_appt_type_institution 表。
 * 描述体检预约类型（如：个人预约、团体预约、VIP预约等）与体检机构的对应关系。
 * 用于管理不同机构支持哪些预约方式，在预约流程中根据此关联进行机构筛选。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Data
@TableName("t_appt_type_institution")
public class ApptTypeInstitution implements Serializable {

    /**
     * 主键ID，自增策略
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 预约类型（如：个人、团体、VIP）
     */
    private String apptType;
    /**
     * 机构ID，关联 t_institution 表
     */
    private Long institutionId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
