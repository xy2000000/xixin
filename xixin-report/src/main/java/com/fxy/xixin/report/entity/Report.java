package com.fxy.xixin.report.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 体检报告实体类
 * <p>
 * 对应数据库 t_report 表，继承 {@link com.fxy.xixin.common.base.BaseEntity} 获得通用字段。
 * 记录用户的各项检查结果、综合评估和健康建议，是体检预约流程的最终输出产物。
 * 医生完成体检后录入报告，报告直接发布（status=2），患者可立即查看。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_report")
public class Report extends BaseEntity {

    /**
     * 用户ID，关联体检用户
     */
    private Long userId;
    /**
     * 预约记录ID，关联体检预约
     */
    private Long appointmentId;
    /**
     * 医生ID，生成报告的医生
     */
    private Long doctorId;
    /**
     * 体检摘要
     */
    private String summary;
    /**
     * 体检结论及健康建议
     */
    private String conclusion;
    /**
     * 报告状态：2=已发布（医生生成报告后直接发布，患者可立即查看）
     */
    private Integer status;
    /**
     * 报告生成时间
     */
    private LocalDateTime generateTime;
}
