package com.fxy.xixin.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.auth.entity.Patient;
import org.apache.ibatis.annotations.Mapper;

/**
 * 体检人数据访问层接口（认证模块使用）
 * <p>
 * 继承 MyBatis-Plus BaseMapper，提供体检人档案表的 CRUD 基础操作。
 * 用于注册时同步创建体检人档案。
 * </p>
 *
 * @author dev
 */
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
}
