package com.fxy.xixin.report.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxy.xixin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 报告项目实体类，对应数据库表 t_report_item
 * <p>
 * 存储体检报告中每个检查项目的具体结果明细。
 * 一份体检报告（{@link Report}）通常包含多个检查项目的结果，
 * 如血常规、尿常规、心电图、B超等，每个项目独立记录为一条 ReportItem。
 * </p>
 *
 * <p>字段说明：</p>
 * <ul>
 *   <li>reportId - 所属报告ID，关联 t_report 表</li>
 *   <li>examItemId - 对应的体检项目ID，关联 t_exam_item 表</li>
 *   <li>examItemName - 项目名称（冗余存储，方便查询时无需联表）</li>
 *   <li>result - 该项目的检查结果值</li>
 *   <li>referenceRange - 参考范围（正常值范围）</li>
 *   <li>abnormalFlag - 异常标志：0=正常，1=异常</li>
 * </ul>
 *
 * <p>在体检报告流程中的位置：</p>
 * <pre>
 * 体检完成 → 生成报告(Report) → 逐项录入结果(ReportItem) → 汇总结论 → 审核发布
 * </pre>
 *
 * @author dev
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_report_item")
public class ReportItem extends BaseEntity {

    /**
     * 所属报告ID，关联 t_report 表的 id
     */
    private Long reportId;

    /**
     * 对应的体检项目ID，关联 t_exam_item 表的 id
     */
    private Long examItemId;

    /**
     * 检查项目名称（冗余字段，如"血常规"、"心电图"）
     * <p>设计说明：冗余存储项目名称可避免每次查询报告明细时都去联表查 t_exam_item，
     * 提高读取性能。生成报告时从体检套餐的 ExamItem 中复制过来。</p>
     */
    private String examItemName;

    /**
     * 检查结果（如"白细胞6.8, 红细胞5.2, 血红蛋白148g/L"）
     */
    private String result;

    /**
     * 参考范围（正常值范围，如"白细胞4-10, 红细胞4.5-5.8, 血红蛋白130-175"）
     */
    private String referenceRange;

    /**
     * 异常标志：0=正常（结果在参考范围内），1=异常（结果超出参考范围）
     * <p>用于报告界面高亮显示异常项目，帮助医生和患者快速定位问题指标。</p>
     */
    private Integer abnormalFlag;
}