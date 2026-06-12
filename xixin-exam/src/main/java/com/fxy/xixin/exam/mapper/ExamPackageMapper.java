package com.fxy.xixin.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.exam.entity.ExamPackage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 体检套餐数据访问层
 * <p>
 * 基于 MyBatis-Plus 的 BaseMapper，提供 t_exam_package 表的增删改查操作。
 * 在体检预约流程中，用户浏览套餐列表、查看套餐详情时通过此 Mapper 查询数据。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Mapper
public interface ExamPackageMapper extends BaseMapper<ExamPackage> {
}
