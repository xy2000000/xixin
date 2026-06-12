package com.fxy.xixin.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.xixin.report.entity.Report;
import com.fxy.xixin.report.mapper.ReportMapper;
import com.fxy.xixin.report.service.ReportService;
import org.springframework.stereotype.Service;

/**
 * 体检报告业务实现类
 * <p>
 * 继承 MyBatis-Plus 的 ServiceImpl 获得通用 CRUD 业务实现。
 * 实现 {@link ReportService} 接口，提供报告查询、生成、审核、发布等业务逻辑。
 * 在体检预约流程中：医护人员录入检查结果后生成报告；报告经审核通过后发布；
 * 用户可在客户端查看和下载 PDF 格式的体检报告。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {
}
