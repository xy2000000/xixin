package com.fxy.xixin.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.exam.entity.Institution;
import org.apache.ibatis.annotations.Mapper;

/**
 * 体检机构数据访问层
 * <p>
 * 基于 MyBatis-Plus 的 BaseMapper，提供 t_institution 表的增删改查操作。
 * 在体检预约流程中，用户选择体检机构时通过此 Mapper 查询机构列表和详情。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Mapper
public interface InstitutionMapper extends BaseMapper<Institution> {
}
