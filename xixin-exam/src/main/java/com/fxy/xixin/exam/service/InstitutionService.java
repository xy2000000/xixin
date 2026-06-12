package com.fxy.xixin.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.xixin.exam.entity.Institution;

/**
 * 体检机构业务接口
 * <p>
 * 继承 MyBatis-Plus 的 IService 获得通用业务方法。
 * 定义体检机构相关的核心业务操作，由 {@link com.fxy.xixin.exam.service.impl.InstitutionServiceImpl} 实现。
 * 在体检预约流程中，用户选择套餐后需要选择体检机构，此接口提供机构列表查询和详情展示功能。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
public interface InstitutionService extends IService<Institution> {
}
