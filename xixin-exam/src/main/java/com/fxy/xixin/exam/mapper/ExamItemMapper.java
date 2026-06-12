package com.fxy.xixin.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.exam.entity.ExamItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 体检项目数据访问层
 * <p>
 * 基于 MyBatis-Plus 的 BaseMapper，提供 t_exam_item 表的增删改查操作。
 * 在体检预约流程中，用户查看套餐详情时通过此 Mapper 查询套餐包含的体检项目列表。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Mapper
public interface ExamItemMapper extends BaseMapper<ExamItem> {
}
