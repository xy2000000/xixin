package com.fxy.xixin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.system.entity.DictType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典类型数据访问层接口
 * <p>
 * 继承 MyBatis-Plus BaseMapper，提供字典类型表的 CRUD 基础操作。
 * 用于系统管理中数据字典分类的管理和维护。
 * </p>
 *
 * @author dev
 */
@Mapper
public interface DictTypeMapper extends BaseMapper<DictType> {
}
