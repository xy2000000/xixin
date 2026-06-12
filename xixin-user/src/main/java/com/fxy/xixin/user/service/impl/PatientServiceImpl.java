package com.fxy.xixin.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.xixin.user.entity.Patient;
import com.fxy.xixin.user.mapper.PatientMapper;
import com.fxy.xixin.user.service.PatientService;
import org.springframework.stereotype.Service;

/**
 * 体检人服务实现类
 * <p>
 * 继承 MyBatis-Plus ServiceImpl，提供体检人档案管理的默认实现。
 * 体检预约流程中，用户需先维护体检人档案信息，
 * 然后才能进行体检项目的预约。
 * </p>
 *
 * @author dev
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {
}
