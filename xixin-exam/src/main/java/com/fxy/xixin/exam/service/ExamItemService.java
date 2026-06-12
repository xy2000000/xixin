package com.fxy.xixin.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.xixin.exam.entity.ExamItem;

/**
 * 体检项目业务接口
 * <p>
 * 继承 MyBatis-Plus 的 IService 获得通用业务方法。
 * 定义体检项目相关的核心业务操作，由 {@link com.fxy.xixin.exam.service.impl.ExamItemServiceImpl} 实现。
 * 在体检预约流程中，用户查看套餐详情时，通过此接口获取套餐包含的检查项目列表。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
public interface ExamItemService extends IService<ExamItem> {
}
