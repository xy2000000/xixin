-- ==============================================
-- 东软熙心健康体检管理系统 - 数据库初始化脚本
-- ==============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS xixin_user DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS xixin_exam DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS xixin_report DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS xixin_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ==============================================
-- xixin_user 数据库
-- ==============================================
USE xixin_user;

CREATE TABLE IF NOT EXISTS t_user (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64) NOT NULL COMMENT '用户名',
    password VARCHAR(128) NOT NULL COMMENT '密码(明文)',
    real_name VARCHAR(64) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(128) COMMENT '邮箱',
    avatar VARCHAR(256) COMMENT '头像URL',
    role VARCHAR(32) NOT NULL DEFAULT 'PATIENT' COMMENT '角色: ADMIN/DOCTOR/PATIENT',
    status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1启用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT UNSIGNED COMMENT '创建人',
    update_by BIGINT UNSIGNED COMMENT '更新人',
    deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0未删除 1已删除',
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS t_patient (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL COMMENT '关联用户ID',
    name VARCHAR(64) COMMENT '姓名',
    gender TINYINT(1) COMMENT '性别: 0女 1男',
    birthday DATE COMMENT '出生日期',
    id_card VARCHAR(18) COMMENT '身份证号',
    address VARCHAR(256) COMMENT '地址',
    emergency_contact VARCHAR(64) COMMENT '紧急联系人',
    emergency_phone VARCHAR(20) COMMENT '紧急联系电话',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by BIGINT UNSIGNED,
    update_by BIGINT UNSIGNED,
    deleted TINYINT(1) NOT NULL DEFAULT 0,
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='患者档案表';

CREATE TABLE IF NOT EXISTS t_doctor (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL COMMENT '关联用户ID',
    package_id BIGINT UNSIGNED COMMENT '负责的体检套餐ID(关联t_exam_package.id)',
    department VARCHAR(64) COMMENT '科室',
    title VARCHAR(32) COMMENT '职称',
    specialty VARCHAR(256) COMMENT '专长',
    introduction TEXT COMMENT '简介',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by BIGINT UNSIGNED,
    update_by BIGINT UNSIGNED,
    deleted TINYINT(1) NOT NULL DEFAULT 0,
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医生档案表';

-- 插入默认管理员
INSERT INTO t_user (username, password, real_name, role, status) VALUES
('admin', '123456', '系统管理员', 'ADMIN', 1);

-- ==============================================
-- xixin_exam 数据库
-- ==============================================
USE xixin_exam;

CREATE TABLE IF NOT EXISTS t_exam_package (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL COMMENT '套餐名称',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    description TEXT COMMENT '套餐描述',
    doctor_id BIGINT UNSIGNED COMMENT '绑定医生对应的系统用户ID(关联t_doctor.user_id, 非t_doctor.id)',
    status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态: 0下架 1上架',
    sort_order INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by BIGINT UNSIGNED,
    update_by BIGINT UNSIGNED,
    deleted TINYINT(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='体检套餐表';

CREATE TABLE IF NOT EXISTS t_exam_item (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    package_id BIGINT UNSIGNED NOT NULL COMMENT '所属套餐ID',
    name VARCHAR(128) NOT NULL COMMENT '项目名称',
    type VARCHAR(32) COMMENT '项目类型',
    description TEXT COMMENT '项目描述',
    sort_order INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by BIGINT UNSIGNED,
    update_by BIGINT UNSIGNED,
    deleted TINYINT(1) NOT NULL DEFAULT 0,
    KEY idx_package_id (package_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='体检项目表';

CREATE TABLE IF NOT EXISTS t_institution (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL COMMENT '机构名称',
    address VARCHAR(256) COMMENT '地址',
    phone VARCHAR(20) COMMENT '电话',
    business_hours VARCHAR(128) COMMENT '营业时间',
    description TEXT COMMENT '描述',
    status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
    sort_order INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by BIGINT UNSIGNED,
    update_by BIGINT UNSIGNED,
    deleted TINYINT(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='体检机构表';

CREATE TABLE IF NOT EXISTS t_appt_type_institution (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    appt_type VARCHAR(64) NOT NULL COMMENT '预约类型(dict_item.value)',
    institution_id BIGINT UNSIGNED NOT NULL COMMENT '机构ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_type_inst (appt_type, institution_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预约类型-机构关联表';

CREATE TABLE IF NOT EXISTS t_institution_package (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    institution_id BIGINT UNSIGNED NOT NULL COMMENT '机构ID',
    package_id BIGINT UNSIGNED NOT NULL COMMENT '套餐ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_inst_pkg (institution_id, package_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机构-套餐关联表';

CREATE TABLE IF NOT EXISTS t_appointment (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    package_id BIGINT UNSIGNED NOT NULL COMMENT '套餐ID',
    institution_id BIGINT UNSIGNED COMMENT '体检机构ID',
    doctor_id BIGINT UNSIGNED COMMENT '分配的医生对应的系统用户ID(关联t_doctor.user_id, 非t_doctor.id)。联表: JOIN t_doctor d ON t_appointment.doctor_id = d.user_id',
    appointment_date DATE NOT NULL COMMENT '预约日期',
    time_slot VARCHAR(32) COMMENT '时间段',
    status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态: 1已确认(预约后自动确认), 2已取消, 3已完成',
    remark VARCHAR(512) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by BIGINT UNSIGNED,
    update_by BIGINT UNSIGNED,
    deleted TINYINT(1) NOT NULL DEFAULT 0,
    KEY idx_user_id (user_id),
    KEY idx_package_id (package_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预约记录表';

-- ==============================================
-- xixin_report 数据库
-- ==============================================
USE xixin_report;

CREATE TABLE IF NOT EXISTS t_report (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    appointment_id BIGINT UNSIGNED COMMENT '预约ID',
    doctor_id BIGINT UNSIGNED COMMENT '生成报告的医生对应的系统用户ID(关联t_doctor.user_id, 非t_doctor.id)。联表: JOIN t_doctor d ON t_report.doctor_id = d.user_id',
    summary TEXT COMMENT '检查摘要',
    conclusion TEXT COMMENT '总检结论',
    status TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态: 0草稿 1已生成 2已发布',
    generate_time DATETIME COMMENT '生成时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by BIGINT UNSIGNED,
    update_by BIGINT UNSIGNED,
    deleted TINYINT(1) NOT NULL DEFAULT 0,
    KEY idx_user_id (user_id),
    KEY idx_appointment_id (appointment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='体检报告表';

CREATE TABLE IF NOT EXISTS t_report_item (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    report_id BIGINT UNSIGNED NOT NULL COMMENT '报告ID',
    exam_item_id BIGINT UNSIGNED COMMENT '体检项目ID',
    exam_item_name VARCHAR(128) COMMENT '项目名称',
    result VARCHAR(256) COMMENT '检查结果',
    reference_range VARCHAR(128) COMMENT '参考范围',
    abnormal_flag TINYINT(1) DEFAULT 0 COMMENT '异常标志: 0正常 1异常',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by BIGINT UNSIGNED,
    update_by BIGINT UNSIGNED,
    deleted TINYINT(1) NOT NULL DEFAULT 0,
    KEY idx_report_id (report_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报告项目表';

-- ==============================================
-- xixin_system 数据库
-- ==============================================
USE xixin_system;

CREATE TABLE IF NOT EXISTS t_role (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(64) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(64) NOT NULL COMMENT '角色编码',
    description VARCHAR(256) COMMENT '角色描述',
    status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by BIGINT UNSIGNED,
    update_by BIGINT UNSIGNED,
    deleted TINYINT(1) NOT NULL DEFAULT 0,
    UNIQUE KEY uk_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE IF NOT EXISTS t_dict_type (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    dict_name VARCHAR(128) NOT NULL COMMENT '字典名称',
    dict_type VARCHAR(64) NOT NULL COMMENT '字典类型',
    status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by BIGINT UNSIGNED,
    update_by BIGINT UNSIGNED,
    deleted TINYINT(1) NOT NULL DEFAULT 0,
    UNIQUE KEY uk_dict_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

CREATE TABLE IF NOT EXISTS t_dict_item (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    dict_type_id BIGINT UNSIGNED NOT NULL COMMENT '字典类型ID',
    label VARCHAR(128) NOT NULL COMMENT '标签',
    value VARCHAR(128) NOT NULL COMMENT '值',
    sort_order INT DEFAULT 0 COMMENT '排序',
    status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by BIGINT UNSIGNED,
    update_by BIGINT UNSIGNED,
    deleted TINYINT(1) NOT NULL DEFAULT 0,
    KEY idx_dict_type_id (dict_type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典项表';

-- 插入默认角色
INSERT IGNORE INTO t_role (role_name, role_code, description, status) VALUES
('超级管理员', 'ADMIN', '系统超级管理员', 1),
('医生', 'DOCTOR', '体检医生', 1),
('患者', 'PATIENT', '体检用户', 1);

-- 插入默认机构
INSERT IGNORE INTO t_institution (id, name, address, phone, business_hours, description, status, sort_order) VALUES
(1, '北京旗舰体检中心',    '北京市东城区长安街100号东方广场3层',   '010-88881001', '周一至周五 08:00-17:00, 周六 08:00-12:00', '旗舰级体检中心，配备全套进口设备，支持所有体检套餐', 1, 1),
(2, '上海浦东体检分院',    '上海市浦东新区世纪大道200号金融大厦',  '021-66662002', '周一至周日 07:30-16:30',                     '新近装修，配备最新CT和磁共振设备',                   1, 2),
(3, '广州天河体检分院',    '广州市天河区天河路300号正佳广场旁',   '020-55553003', '周一至周六 08:30-17:30',                     '地铁直达交通便利，女性健康检查专区',                 1, 3),
(4, '成都锦江社区体检站',  '成都市锦江区红星路400号社区中心',     '028-44444004', '周一至周五 09:00-18:00',                     '社区便民体检站，提供基础检查和常规体检',               0, 4),
(5, '南京鼓楼体检中心',    '南京市鼓楼区中山路500号大学附属楼',   '025-33335005', '周一至周日 08:00-16:00',                     '大学附属体检中心，老年保健特色，提供健康管理服务',     1, 5);

-- 插入预约类型字典
INSERT IGNORE INTO t_dict_type (id, dict_name, dict_type, status) VALUES (4, '预约类型', 'exam_appt_type', 1);
INSERT IGNORE INTO t_dict_item (dict_type_id, label, value, sort_order, status) VALUES
(4, '个人体检',  'individual',  1, 1),
(4, '团体体检',  'group',       2, 1),
(4, '入职体检',  'onboarding',  3, 1),
(4, '出入境体检','travel',      4, 1);

-- 插入预约类型-机构关联
INSERT IGNORE INTO t_appt_type_institution (appt_type, institution_id) VALUES
('individual', 1), ('individual', 2), ('individual', 3), ('individual', 4), ('individual', 5),
('group', 1), ('group', 2),
('onboarding', 1), ('onboarding', 2), ('onboarding', 3),
('travel', 1), ('travel', 3);

-- 插入机构-套餐关联
INSERT IGNORE INTO t_institution_package (institution_id, package_id) VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),
(2,1),(2,2),(2,3),(2,4),(2,5),
(3,1),(3,2),(3,3),(3,4),
(4,1),(4,2),
(5,1),(5,2),(5,3),(5,5);
