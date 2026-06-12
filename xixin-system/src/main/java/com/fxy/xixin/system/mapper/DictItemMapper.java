package com.fxy.xixin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.system.entity.DictItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典项数据访问层接口
 * <p>
 * 继承 MyBatis-Plus BaseMapper，提供字典项表的 CRUD 基础操作。
 * 用于系统管理中字典枚举值的管理和维护。
 * </p>
 *
 * @author dev
 */
@Mapper
public interface DictItemMapper extends BaseMapper<DictItem> {
}
