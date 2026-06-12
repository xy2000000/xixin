package com.fxy.xixin.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.xixin.exam.entity.ExamPackage;

/**
 * 体检套餐业务接口
 * <p>
 * 继承 MyBatis-Plus 的 IService 获得通用业务方法。
 * 定义套餐相关的核心业务操作，由 {@link com.fxy.xixin.exam.service.impl.ExamPackageServiceImpl} 实现。
 * 在体检预约流程中，提供套餐列表查询、套餐详情展示等核心功能。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
public interface ExamPackageService extends IService<ExamPackage> {
}
