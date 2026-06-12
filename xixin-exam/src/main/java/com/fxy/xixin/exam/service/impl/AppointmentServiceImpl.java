package com.fxy.xixin.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.xixin.exam.entity.Appointment;
import com.fxy.xixin.exam.mapper.AppointmentMapper;
import com.fxy.xixin.exam.service.AppointmentService;
import org.springframework.stereotype.Service;

/**
 * 体检预约业务实现类
 * <p>
 * 继承 MyBatis-Plus 的 ServiceImpl 获得通用 CRUD 业务实现。
 * 实现 {@link AppointmentService} 接口，提供预约提交、预约查询、预约取消、号源管理等业务逻辑。
 * 在体检预约流程中，此实现类是核心业务逻辑所在：
 * <ol>
 *   <li>用户选择套餐和机构后提交预约申请</li>
 *   <li>系统校验号源是否充足，防止重复预约</li>
 *   <li>用户可查看预约状态，在允许的时段内取消预约</li>
 *   <li>支付前锁定号源，防止超卖</li>
 * </ol>
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {
}
