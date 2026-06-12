package com.fxy.xixin.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.exam.entity.InstitutionPackage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 机构-套餐关联数据访问层
 * <p>
 * 基于 MyBatis-Plus 的 BaseMapper，提供 t_institution_package 表的增删改查操作。
 * 在体检预约流程中，用于查询某机构可提供的套餐列表，
 * 或查询某套餐在哪些机构可用，实现机构和套餐的双向筛选。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Mapper
public interface InstitutionPackageMapper extends BaseMapper<InstitutionPackage> {
}
