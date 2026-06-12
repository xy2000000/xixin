package com.fxy.xixin.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.exam.entity.Appointment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 体检预约数据访问层
 * <p>
 * 基于 MyBatis-Plus 的 BaseMapper，提供 t_appointment 表的增删改查操作。
 * 在体检预约流程中，用户提交预约、查询预约状态、取消预约等操作均通过此 Mapper 进行。
 * 是预约流程中最核心的数据访问接口。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
}
