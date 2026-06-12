package com.fxy.xixin.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.user.entity.Doctor;
import org.apache.ibatis.annotations.Mapper;

/**
 * 医生数据访问层接口
 * <p>
 * 继承 MyBatis-Plus BaseMapper，提供医生信息表的 CRUD 基础操作。
 * 用于体检预约流程中医生的排班查询和信息展示。
 * </p>
 *
 * @author dev
 */
@Mapper
public interface DoctorMapper extends BaseMapper<Doctor> {
}
