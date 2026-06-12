package com.fxy.xixin.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.exam.entity.ApptTypeInstitution;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约类型-机构关联数据访问层
 * <p>
 * 基于 MyBatis-Plus 的 BaseMapper，提供 t_appt_type_institution 表的增删改查操作。
 * 在体检预约流程中，用于查询某机构支持哪些预约类型，
 * 或在选择预约类型后筛选可用的体检机构。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Mapper
public interface ApptTypeInstitutionMapper extends BaseMapper<ApptTypeInstitution> {
}
