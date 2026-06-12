package com.fxy.xixin.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.xixin.report.entity.Report;

/**
 * 体检报告业务接口
 * <p>
 * 继承 MyBatis-Plus 的 IService 获得通用业务方法。
 * 定义报告相关的核心业务操作，由 {@link com.fxy.xixin.report.service.impl.ReportServiceImpl} 实现。
 * 在体检预约流程中，用户完成体检后可通过此接口查询和下载报告，
 * 医护人员通过此接口审核和发布报告。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
public interface ReportService extends IService<Report> {
}
