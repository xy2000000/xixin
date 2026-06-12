package com.fxy.xixin.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.report.entity.ReportItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 报告项目数据访问层
 * <p>
 * 基于 MyBatis-Plus 的 BaseMapper，提供 t_report_item 表的增删改查操作。
 * 继承 BaseMapper 后自动获得 CRUD、分页、条件查询等通用方法，
 * 无需编写 XML 映射文件。
 * </p>
 *
 * <p>主要使用场景：</p>
 * <ul>
 *   <li>录入报告项目时，逐条插入检查结果</li>
 *   <li>查询报告详情时，根据 report_id 查询该报告的所有检查项目</li>
 *   <li>医生修改某个检查项的结果时，更新对应记录</li>
 * </ul>
 *
 * @author dev
 * @since 1.0.0
 */
@Mapper
public interface ReportItemMapper extends BaseMapper<ReportItem> {
}