package com.fxy.xixin.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.report.entity.Report;
import org.apache.ibatis.annotations.Mapper;

/**
 * 体检报告数据访问层
 * <p>
 * 基于 MyBatis-Plus 的 BaseMapper，提供 t_report 表的增删改查操作。
 * 继承 BaseMapper 后自动获得 CRUD、分页、条件查询等通用方法，
 * 无需编写 XML 映射文件。在报告查询、生成、发布等业务场景中使用。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Mapper
public interface ReportMapper extends BaseMapper<Report> {
}
