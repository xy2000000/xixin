package com.fxy.xixin.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.xixin.report.entity.ReportItem;

/**
 * 报告项目业务接口
 * <p>
 * 继承 MyBatis-Plus 的 IService 获得通用业务方法。
 * 定义报告项目明细相关的核心业务操作，由 {@link com.fxy.xixin.report.service.impl.ReportItemServiceImpl} 实现。
 * </p>
 *
 * <p>在体检报告流程中，报告项目的录入和查询是关键环节：</p>
 * <ol>
 *   <li>医生完成体检后，逐项录入检查结果到报告项目中</li>
 *   <li>患者查看报告时，系统展示所有报告项目及其异常标记</li>
 *   <li>医生修改报告时，可调整特定项目的检查结果</li>
 * </ol>
 *
 * @author dev
 * @since 1.0.0
 */
public interface ReportItemService extends IService<ReportItem> {
}