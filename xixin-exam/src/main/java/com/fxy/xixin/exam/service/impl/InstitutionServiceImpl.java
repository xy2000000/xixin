package com.fxy.xixin.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.xixin.exam.entity.Institution;
import com.fxy.xixin.exam.mapper.InstitutionMapper;
import com.fxy.xixin.exam.service.InstitutionService;
import org.springframework.stereotype.Service;

/**
 * 体检机构业务实现类
 * <p>
 * 继承 MyBatis-Plus 的 ServiceImpl 获得通用 CRUD 业务实现。
 * 实现 {@link InstitutionService} 接口，提供体检机构的查询和管理功能。
 * 在体检预约流程中，用户选择套餐后通过此实现类查询可提供该套餐的机构列表，
 * 包括机构地址、营业时间、联系电话等信息，帮助用户选择合适的体检场所。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Service
public class InstitutionServiceImpl extends ServiceImpl<InstitutionMapper, Institution> implements InstitutionService {
}
