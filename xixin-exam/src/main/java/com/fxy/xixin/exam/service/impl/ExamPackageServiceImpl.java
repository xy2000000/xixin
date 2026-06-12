package com.fxy.xixin.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.xixin.exam.entity.ExamPackage;
import com.fxy.xixin.exam.mapper.ExamPackageMapper;
import com.fxy.xixin.exam.service.ExamPackageService;
import org.springframework.stereotype.Service;

/**
 * 体检套餐业务实现类
 * <p>
 * 继承 MyBatis-Plus 的 ServiceImpl 获得通用 CRUD 业务实现。
 * 实现 {@link ExamPackageService} 接口，提供套餐的上架/下架、列表查询、详情查询等业务逻辑。
 * 在体检预约流程中，用户进入系统后首先看到上架的套餐列表，选择感兴趣的套餐查看详情。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Service
public class ExamPackageServiceImpl extends ServiceImpl<ExamPackageMapper, ExamPackage> implements ExamPackageService {
}
