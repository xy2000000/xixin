package com.fxy.xixin.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.user.entity.Patient;
import org.apache.ibatis.annotations.Mapper;

/**
 * 体检人数据访问层接口
 * <p>
 * 继承 MyBatis-Plus BaseMapper，提供体检人档案表的 CRUD 基础操作。
 * 用于体检预约流程中体检人信息的查询和维护。
 * </p>
 *
 * @author dev
 */
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
}
