package com.fxy.xixin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.xixin.system.entity.DictType;
import com.fxy.xixin.system.mapper.DictTypeMapper;
import com.fxy.xixin.system.service.DictTypeService;
import org.springframework.stereotype.Service;

/**
 * 字典类型服务实现类
 * <p>
 * 继承 MyBatis-Plus ServiceImpl，提供字典类型管理的默认实现。
 * 字典类型对系统中的枚举数据进行分类，如体检项目类别、
 * 预约状态、科室等，是系统数据标准化的重要组件。
 * </p>
 *
 * @author dev
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictTypeService {
}
