package com.fxy.xixin.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.xixin.user.entity.Doctor;
import com.fxy.xixin.user.mapper.DoctorMapper;
import com.fxy.xixin.user.service.DoctorService;
import org.springframework.stereotype.Service;

/**
 * 医生服务实现类
 * <p>
 * 继承 MyBatis-Plus ServiceImpl，提供医生信息管理的默认实现。
 * 在体检预约流程中，体检人选择科室和医生时使用此服务查询医生信息。
 * </p>
 *
 * @author dev
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {
}
