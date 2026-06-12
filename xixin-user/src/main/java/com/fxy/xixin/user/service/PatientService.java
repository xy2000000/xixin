package com.fxy.xixin.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.xixin.user.entity.Patient;

/**
 * 体检人服务接口
 * <p>
 * 继承 MyBatis-Plus IService，提供体检人档案管理的业务接口。
 * 体检人档案是体检预约的前置条件，预约前需先建立体检人档案。
 * </p>
 *
 * @author dev
 */
public interface PatientService extends IService<Patient> {
}
