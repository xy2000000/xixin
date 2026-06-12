package com.fxy.xixin.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.xixin.exam.entity.Appointment;

/**
 * 体检预约业务接口
 * <p>
 * 继承 MyBatis-Plus 的 IService 获得通用业务方法。
 * 定义体检预约的核心业务操作，由 {@link com.fxy.xixin.exam.service.impl.AppointmentServiceImpl} 实现。
 * 在体检预约流程中，此接口承载了预约提交、预约查询、预约取消、号源锁定等关键业务逻辑，
 * 是整个体检预约系统的核心业务接口。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
public interface AppointmentService extends IService<Appointment> {
}
