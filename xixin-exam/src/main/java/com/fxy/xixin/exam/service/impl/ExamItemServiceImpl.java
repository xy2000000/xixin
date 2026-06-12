package com.fxy.xixin.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.xixin.exam.entity.ExamItem;
import com.fxy.xixin.exam.mapper.ExamItemMapper;
import com.fxy.xixin.exam.service.ExamItemService;
import org.springframework.stereotype.Service;

/**
 * 体检项目业务实现类
 * <p>
 * 继承 MyBatis-Plus 的 ServiceImpl 获得通用 CRUD 业务实现。
 * 实现 {@link ExamItemService} 接口，提供体检项目的增删改查和按套餐查询等功能。
 * 在体检预约流程中，用户查看套餐详情时，通过此实现类获取该套餐包含的所有检查项目。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Service
public class ExamItemServiceImpl extends ServiceImpl<ExamItemMapper, ExamItem> implements ExamItemService {
}
