package com.fxy.xixin.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.xixin.report.entity.ReportItem;
import com.fxy.xixin.report.mapper.ReportItemMapper;
import com.fxy.xixin.report.service.ReportItemService;
import org.springframework.stereotype.Service;

/**
 * 报告项目业务实现类
 * <p>
 * 继承 MyBatis-Plus 的 ServiceImpl 获得通用 CRUD 业务实现。
 * 实现 {@link ReportItemService} 接口，提供报告项目明细的查询、录入、修改等业务逻辑。
 * </p>
 *
 * <p>在体检报告流程中，此实现类负责：</p>
 * <ol>
 *   <li>医生录入检查结果时，批量保存报告项目明细</li>
 *   <li>患者查看报告时，按报告ID查询所有项目明细</li>
 *   <li>医生修正结果时，更新特定项目的检查值和异常标记</li>
 * </ol>
 *
 * @author dev
 * @since 1.0.0
 */
@Service
public class ReportItemServiceImpl extends ServiceImpl<ReportItemMapper, ReportItem> implements ReportItemService {
}