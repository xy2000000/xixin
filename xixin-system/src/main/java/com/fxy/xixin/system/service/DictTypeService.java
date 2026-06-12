package com.fxy.xixin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.xixin.system.entity.DictType;

/**
 * 字典类型服务接口
 * <p>
 * 继承 MyBatis-Plus IService，提供字典类型管理的业务接口。
 * 数据字典为体检预约系统提供统一的枚举值管理，
 * 前端下拉选项、状态标签等均依赖数据字典。
 * </p>
 *
 * @author dev
 */
public interface DictTypeService extends IService<DictType> {
}
