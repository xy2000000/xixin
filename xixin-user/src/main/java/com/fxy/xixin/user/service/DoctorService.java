package com.fxy.xixin.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.xixin.user.entity.Doctor;

/**
 * 医生服务接口
 * <p>
 * 继承 MyBatis-Plus IService，提供医生信息管理的业务接口。
 * 医生信息用于体检预约时选择科室和医生的参考。
 * </p>
 *
 * @author dev
 */
public interface DoctorService extends IService<Doctor> {
}
