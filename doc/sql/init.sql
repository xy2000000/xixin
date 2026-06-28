-- ==============================================
-- 东软熙心健康体检管理系统 - 数据库初始化脚本
-- 生成日期：2026-06-28
-- 基于最新 Java 实体类 & 实际数据库 dump 生成
-- ==============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS xixin_user   DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS xixin_exam   DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS xixin_report DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS xixin_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ==============================================
-- 1. xixin_user 数据库（用户 / 患者 / 医生）
-- ==============================================
USE xixin_user;

-- 1.1 用户表（extends BaseEntity）
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(64)  NOT NULL              COMMENT '登录用户名',
    password    VARCHAR(128) NOT NULL              COMMENT '密码（明文）',
    real_name   VARCHAR(64)  DEFAULT NULL          COMMENT '真实姓名',
    phone       VARCHAR(20)  DEFAULT NULL          COMMENT '手机号',
    email       VARCHAR(128) DEFAULT NULL          COMMENT '邮箱',
    avatar      VARCHAR(256) DEFAULT NULL          COMMENT '头像URL（MinIO返回的地址）',
    role        VARCHAR(32)  NOT NULL DEFAULT 'PATIENT' COMMENT '角色: ADMIN / DOCTOR / PATIENT',
    status      TINYINT(1)   NOT NULL DEFAULT 1   COMMENT '账号状态: 0=禁用, 1=启用',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by   BIGINT UNSIGNED DEFAULT NULL       COMMENT '创建人',
    update_by   BIGINT UNSIGNED DEFAULT NULL       COMMENT '更新人',
    deleted     TINYINT(1)   NOT NULL DEFAULT 0   COMMENT '逻辑删除: 0=未删除, 1=已删除',
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 1.2 患者档案表（extends BaseEntity）
DROP TABLE IF EXISTS t_patient;
CREATE TABLE t_patient (
    id                BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id           BIGINT UNSIGNED NOT NULL     COMMENT '关联用户ID (t_user.id)',
    name              VARCHAR(64)  DEFAULT NULL    COMMENT '姓名',
    gender            TINYINT(1)   DEFAULT NULL    COMMENT '性别: 0=女, 1=男',
    birthday          DATE         DEFAULT NULL    COMMENT '出生日期',
    id_card           VARCHAR(18)  DEFAULT NULL    COMMENT '身份证号',
    address           VARCHAR(256) DEFAULT NULL    COMMENT '地址',
    emergency_contact VARCHAR(64)  DEFAULT NULL    COMMENT '紧急联系人',
    emergency_phone   VARCHAR(20)  DEFAULT NULL    COMMENT '紧急联系电话',
    create_time       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by         BIGINT UNSIGNED DEFAULT NULL,
    update_by         BIGINT UNSIGNED DEFAULT NULL,
    deleted           TINYINT(1)   NOT NULL DEFAULT 0,
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='患者档案表';

-- 1.3 医生档案表（extends BaseEntity）
DROP TABLE IF EXISTS t_doctor;
CREATE TABLE t_doctor (
    id           BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT UNSIGNED NOT NULL          COMMENT '关联用户ID (t_user.id)',
    package_id   BIGINT UNSIGNED DEFAULT NULL      COMMENT '负责的体检套餐ID (t_exam_package.id)',
    department   VARCHAR(64)  DEFAULT NULL         COMMENT '科室',
    title        VARCHAR(32)  DEFAULT NULL         COMMENT '职称',
    specialty    VARCHAR(256) DEFAULT NULL         COMMENT '专长',
    introduction TEXT         DEFAULT NULL         COMMENT '简介',
    create_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by    BIGINT UNSIGNED DEFAULT NULL,
    update_by    BIGINT UNSIGNED DEFAULT NULL,
    deleted      TINYINT(1)   NOT NULL DEFAULT 0,
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='医生档案表';

-- ---------- 种子数据 ----------
-- 管理员 + 测试患者 + 测试医生
INSERT INTO t_user (username, password, real_name, phone, email, role, status) VALUES
('admin',       '123456', '系统管理员', NULL,          NULL,              'ADMIN',  1),
('testuser01',  '123456', '测试患者甲', '13911111111', 'test01@test.com',  'PATIENT', 1),
('testdoc01',   '123456', '张医生',     '13900139001', 'doc01@test.com',   'DOCTOR',  1),
('testdoc02',   '123456', '李放射医生', '13900139002', 'doc02@test.com',   'DOCTOR',  1),
('patient001',  '123456', '王患者乙',   '13800001001', 'zhangwei@test.com','PATIENT', 1),
('patient002',  '123456', '李患者丙',   '13800001002', 'lijuan@test.com',  'PATIENT', 1),
('newpatient99', '123456', '赵六',       '13900001111', 'zhaoliu@test.com', 'PATIENT', 1);

INSERT INTO t_patient (user_id, name, gender, birthday, id_card, address, emergency_contact, emergency_phone) VALUES
(2, '测试患者甲', 1, '1990-03-15', '110101199003150011', '北京市海淀区中关村大街1号',   '张父',   '13900001001'),
(5, '王患者乙',   1, '1985-07-22', '310101198507220012', '上海市浦东新区世纪大道100号', '王母',   '13900001002'),
(6, '李患者丙',   0, '1992-11-08', '440101199211080013', '广州市天河区天河路200号',     '李丈夫', '13900001003');

INSERT INTO t_doctor (user_id, package_id, department, title, specialty, introduction) VALUES
(1, 1, '全科医学科', '主任医师', '心血管疾病',   '从事内科临床工作逾25年，擅长冠心病、高血压、心力衰竭的诊治'),
(3, 2, '呼吸内科',   '副主任医师','呼吸系统疾病', '专长：慢性阻塞性肺疾病、支气管哮喘、肺部感染性疾病'),
(4, 3, '放射影像科', '主治医师', '医学影像诊断', 'CT/磁共振影像诊断专家，擅长肿瘤影像学评估');


-- ==============================================
-- 2. xixin_exam 数据库（套餐 / 项目 / 机构 / 预约）
-- ==============================================
USE xixin_exam;

-- 2.1 体检套餐表（extends BaseEntity）
DROP TABLE IF EXISTS t_exam_package;
CREATE TABLE t_exam_package (
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(128)   NOT NULL              COMMENT '套餐名称',
    price       DECIMAL(10,2)  NOT NULL              COMMENT '价格',
    description TEXT           DEFAULT NULL          COMMENT '套餐描述',
    doctor_id   BIGINT UNSIGNED DEFAULT NULL         COMMENT '绑定医生对应的系统用户ID (t_doctor.user_id)',
    status      TINYINT(1)     NOT NULL DEFAULT 1   COMMENT '状态: 0=下架, 1=上架',
    sort_order  INT            DEFAULT 0            COMMENT '排序',
    create_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by   BIGINT UNSIGNED DEFAULT NULL,
    update_by   BIGINT UNSIGNED DEFAULT NULL,
    deleted     TINYINT(1)     NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='体检套餐表';

-- 2.2 体检项目表（extends BaseEntity）
DROP TABLE IF EXISTS t_exam_item;
CREATE TABLE t_exam_item (
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    package_id  BIGINT UNSIGNED NOT NULL             COMMENT '所属套餐ID (t_exam_package.id)',
    name        VARCHAR(128)   NOT NULL              COMMENT '项目名称',
    type        VARCHAR(32)    DEFAULT NULL          COMMENT '项目类型（检验/影像/心电/体格）',
    description TEXT           DEFAULT NULL          COMMENT '项目描述',
    sort_order  INT            DEFAULT 0            COMMENT '排序',
    create_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by   BIGINT UNSIGNED DEFAULT NULL,
    update_by   BIGINT UNSIGNED DEFAULT NULL,
    deleted     TINYINT(1)     NOT NULL DEFAULT 0,
    KEY idx_package_id (package_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='体检项目表';

-- 2.3 体检机构表（extends BaseEntity）
DROP TABLE IF EXISTS t_institution;
CREATE TABLE t_institution (
    id             BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(128)   NOT NULL           COMMENT '机构名称',
    address        VARCHAR(256)   DEFAULT NULL       COMMENT '地址',
    phone          VARCHAR(20)    DEFAULT NULL       COMMENT '电话',
    business_hours VARCHAR(128)   DEFAULT NULL       COMMENT '营业时间',
    description    TEXT           DEFAULT NULL       COMMENT '描述',
    status         TINYINT(1)     NOT NULL DEFAULT 1 COMMENT '状态: 0=停用, 1=启用',
    sort_order     INT            DEFAULT 0         COMMENT '排序',
    create_time    DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by      BIGINT UNSIGNED DEFAULT NULL,
    update_by      BIGINT UNSIGNED DEFAULT NULL,
    deleted        TINYINT(1)     NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='体检机构表';

-- 2.4 预约类型-机构关联表（独立实体，不继承 BaseEntity）
DROP TABLE IF EXISTS t_appt_type_institution;
CREATE TABLE t_appt_type_institution (
    id             BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    appt_type      VARCHAR(64)   NOT NULL            COMMENT '预约类型 (对应 dict_item.value)',
    institution_id BIGINT UNSIGNED NOT NULL          COMMENT '机构ID (t_institution.id)',
    create_time    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_type_inst (appt_type, institution_id),
    KEY idx_type (appt_type),
    KEY idx_inst (institution_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预约类型-机构关联表';

-- 2.5 机构-套餐关联表（独立实体，不继承 BaseEntity）
DROP TABLE IF EXISTS t_institution_package;
CREATE TABLE t_institution_package (
    id             BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    institution_id BIGINT UNSIGNED NOT NULL          COMMENT '机构ID (t_institution.id)',
    package_id     BIGINT UNSIGNED NOT NULL          COMMENT '套餐ID (t_exam_package.id)',
    create_time    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_inst_pkg (institution_id, package_id),
    KEY idx_inst (institution_id),
    KEY idx_pkg  (package_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='机构-套餐关联表';

-- 2.6 预约记录表（extends BaseEntity）
DROP TABLE IF EXISTS t_appointment;
CREATE TABLE t_appointment (
    id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT UNSIGNED NOT NULL        COMMENT '用户ID (t_user.id)',
    package_id       BIGINT UNSIGNED NOT NULL        COMMENT '套餐ID (t_exam_package.id)',
    institution_id   BIGINT UNSIGNED DEFAULT NULL    COMMENT '体检机构ID (t_institution.id)',
    doctor_id        BIGINT UNSIGNED DEFAULT NULL    COMMENT '分配的医生对应的系统用户ID (t_doctor.user_id)',
    appointment_date DATE           NOT NULL         COMMENT '预约日期',
    time_slot        VARCHAR(32)    DEFAULT NULL     COMMENT '时间段',
    status           TINYINT(1)     NOT NULL DEFAULT 1 COMMENT '状态: 1=已确认, 2=已取消, 3=已完成',
    remark           VARCHAR(512)   DEFAULT NULL     COMMENT '备注',
    create_time      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by        BIGINT UNSIGNED DEFAULT NULL,
    update_by        BIGINT UNSIGNED DEFAULT NULL,
    deleted          TINYINT(1)     NOT NULL DEFAULT 0,
    KEY idx_user_id    (user_id),
    KEY idx_package_id (package_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预约记录表';

-- ---------- 种子数据 ----------
INSERT INTO t_exam_package (name, price, description, status, sort_order) VALUES
('基础健康体检', 199.00,  '血常规、尿常规、心电图三项基础检查',                                        1, 1),
('标准健康体检', 599.00,  '基础项目+肝功能全项+肾功能全项+胸部X光片',                                 1, 2),
('高端全面体检', 1299.00, '标准项目+肿瘤标志物筛查+甲状腺彩超+低剂量螺旋CT',                          1, 3),
('女性专项体检', 899.00,  '妇科检查+乳腺彩超+HPV-DNA分型检测',                                      1, 4),
('银发关怀体检', 1699.00, '高端项目+骨密度检测+颈动脉彩超+心脏彩超',                                 1, 5);

INSERT INTO t_exam_item (package_id, name, type, description, sort_order) VALUES
(1, '血常规',          '检验', '白细胞、红细胞、血红蛋白、血小板等全血细胞计数',                    1),
(1, '尿常规',          '检验', '尿液化学分析及显微镜检查',                                        2),
(1, '十二导联心电图',  '心电', '静息十二导联心电图检查',                                          3),
(2, '肝功能全项',      '检验', '谷丙转氨酶、谷草转氨酶、谷氨酰转肽酶、总胆红素等',                1),
(2, '肾功能全项',      '检验', '肌酐、尿素氮、尿酸测定',                                        2),
(2, '胸部X光片',       '影像', '后前位胸部正位摄片',                                            3),
(3, '肿瘤标志物七项',  '检验', '甲胎蛋白、癌胚抗原、CA19-9、CA125等七项肿瘤标志物',              1),
(3, '甲状腺彩超',      '影像', 'B型超声甲状腺及颈部淋巴结扫描',                                 2),
(3, '低剂量螺旋CT',    '影像', '胸部低剂量螺旋CT肺癌筛查',                                      3),
(4, '妇科常规检查',    '体格', '盆腔检查及宫颈液基薄层细胞学检查(TCT)',                           1),
(4, '乳腺彩超',        '影像', '双侧乳腺B型超声检查',                                          2),
(4, 'HPV-DNA分型',     '检验', '高危型人乳头瘤病毒(HPV)基因分型检测',                            3),
(5, '骨密度检测',      '影像', '双能X线骨密度仪(DEXA)检测骨质疏松',                             1),
(5, '颈动脉彩超',      '影像', '颈动脉内膜中层厚度及斑块评估',                                 2),
(5, '心脏彩超',        '心电', '经胸超声心动图检查',                                          3);

INSERT INTO t_institution (name, address, phone, business_hours, description, status, sort_order) VALUES
('北京旗舰体检中心',   '北京市东城区长安街100号东方广场3层',     '010-88881001', '周一至周五 08:00-17:00, 周六 08:00-12:00', '旗舰级体检中心，配备全套进口设备，支持所有体检套餐',               1, 1),
('上海浦东体检分院',   '上海市浦东新区世纪大道200号金融大厦',     '021-66662002', '周一至周日 07:30-16:30',                   '新近装修，配备最新CT和磁共振设备',                               1, 2),
('广州天河体检分院',   '广州市天河区天河路300号正佳广场旁',       '020-55553003', '周一至周六 08:30-17:30',                   '地铁直达交通便利，女性健康检查专区',                             1, 3),
('成都锦江社区体检站', '成都市锦江区红星路400号社区中心',         '028-44444004', '周一至周五 09:00-18:00',                   '社区便民体检站，提供基础检查和常规体检',                         0, 4),
('南京鼓楼体检中心',   '南京市鼓楼区中山路500号大学附属楼',       '025-33335005', '周一至周日 08:00-16:00',                   '大学附属体检中心，老年保健特色，提供健康管理服务',               1, 5);

INSERT INTO t_appt_type_institution (appt_type, institution_id) VALUES
('individual', 1), ('individual', 2), ('individual', 3), ('individual', 4), ('individual', 5),
('group',       1), ('group',       2),
('onboarding',  1), ('onboarding',  2), ('onboarding',  3),
('travel',      1), ('travel',      3);

INSERT INTO t_institution_package (institution_id, package_id) VALUES
(1,1), (1,2), (1,3), (1,4), (1,5),
(2,1), (2,2), (2,3), (2,4), (2,5),
(3,1), (3,2), (3,3), (3,4),
(4,1), (4,2),
(5,1), (5,2), (5,3), (5,5);

INSERT INTO t_appointment (user_id, package_id, institution_id, doctor_id, appointment_date, time_slot, status, remark) VALUES
(2, 1, 1, 1, '2026-06-10', '上午', 1, '需空腹前来'),
(5, 2, 2, 3, '2026-06-11', '上午', 1, '首次体检'),
(6, 3, 3, 4, '2026-06-12', '下午', 1, NULL),
(5, 5, 1, 1, '2026-07-01', '上午', 1, '糖尿病风险评估'),
(6, 4, 3, 1, '2026-07-02', '下午', 3, '甲状腺结节复查');


-- ==============================================
-- 3. xixin_report 数据库（报告 / 报告明细）
-- ==============================================
USE xixin_report;

-- 3.1 体检报告表（extends BaseEntity）
DROP TABLE IF EXISTS t_report;
CREATE TABLE t_report (
    id             BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id        BIGINT UNSIGNED NOT NULL          COMMENT '用户ID (t_user.id)',
    appointment_id BIGINT UNSIGNED DEFAULT NULL      COMMENT '预约ID (t_appointment.id)',
    doctor_id      BIGINT UNSIGNED DEFAULT NULL      COMMENT '生成报告的医生对应的系统用户ID (t_doctor.user_id)',
    summary        TEXT           DEFAULT NULL       COMMENT '检查摘要',
    conclusion     TEXT           DEFAULT NULL       COMMENT '总检结论',
    status         TINYINT(1)     NOT NULL DEFAULT 0 COMMENT '状态: 0=草稿, 1=已生成, 2=已发布',
    generate_time  DATETIME       DEFAULT NULL       COMMENT '生成时间',
    create_time    DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by      BIGINT UNSIGNED DEFAULT NULL,
    update_by      BIGINT UNSIGNED DEFAULT NULL,
    deleted        TINYINT(1)     NOT NULL DEFAULT 0,
    KEY idx_user_id        (user_id),
    KEY idx_appointment_id (appointment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='体检报告表';

-- 3.2 报告项目明细表（extends BaseEntity）
DROP TABLE IF EXISTS t_report_item;
CREATE TABLE t_report_item (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    report_id       BIGINT UNSIGNED NOT NULL         COMMENT '报告ID (t_report.id)',
    exam_item_id    BIGINT UNSIGNED DEFAULT NULL     COMMENT '体检项目ID (t_exam_item.id)',
    exam_item_name  VARCHAR(128)    DEFAULT NULL     COMMENT '项目名称（冗余）',
    result          VARCHAR(256)    DEFAULT NULL     COMMENT '检查结果',
    reference_range VARCHAR(128)    DEFAULT NULL     COMMENT '参考范围',
    abnormal_flag   TINYINT(1)      DEFAULT 0       COMMENT '异常标志: 0=正常, 1=异常',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by       BIGINT UNSIGNED DEFAULT NULL,
    update_by       BIGINT UNSIGNED DEFAULT NULL,
    deleted         TINYINT(1)      NOT NULL DEFAULT 0,
    KEY idx_report_id (report_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报告项目明细表';

-- ---------- 种子数据 ----------
INSERT INTO t_report (user_id, appointment_id, doctor_id, summary, conclusion, status, generate_time) VALUES
(2, 1, 1, '生命体征平稳，血常规各项指标在正常范围，心电图示窦性心律',
          '身体健康，建议继续保持良好生活习惯', 2, '2026-06-10 14:00:00'),
(5, 2, 3, '肝功能谷丙转氨酶轻度升高(65 U/L)，其余指标正常',
          '提示轻度脂肪肝可能，建议减少饮酒、控制饮食，3个月后复查', 1, '2026-06-12 10:30:00'),
(6, 3, 4, '肿瘤标志物均为阴性，CT示右肺上叶微小结节(3mm, 良性表现)',
          '肺微小结节倾向良性，建议6个月后CT随访', 2, '2026-06-13 16:00:00'),
(6, 5, 1, '宫颈TCT阴性，乳腺彩超示左乳纤维腺瘤(1.5cm, BI-RADS 3类)',
          '纤维腺瘤倾向良性，建议6个月短期随访复查', 1, '2026-07-03 11:00:00');

INSERT INTO t_report_item (report_id, exam_item_id, exam_item_name, result, reference_range, abnormal_flag) VALUES
(1, 1, '血常规',     '白细胞6.8, 红细胞5.2, 血红蛋白148g/L',        '白细胞4-10, 红细胞4.5-5.8, 血红蛋白130-175', 0),
(1, 3, '心电图',     '窦性心律，心率72次/分，电轴正常',              '正常窦性心律',                              0),
(2, 4, '肝功能全项', '谷丙转氨酶65 U/L(↑)，谷草转氨酶38 U/L',        '谷丙转氨酶10-40, 谷草转氨酶10-35',            1),
(2, 5, '肾功能全项', '肌酐82 umol/L，尿素氮4.5 mmol/L',             '肌酐60-110, 尿素氮3.2-7.1',                  0),
(3, 7, '肿瘤标志物', '甲胎蛋白3.2 ng/mL，癌胚抗原1.5 ng/mL',         '甲胎蛋白<7, 癌胚抗原<5',                      0),
(4, 10,'妇科常规',   '未见上皮内病变或恶性细胞(NILM)',                '未见异常',                                   0);


-- ==============================================
-- 4. xixin_system 数据库（角色 / 字典）
-- ==============================================
USE xixin_system;

-- 4.1 角色表（extends BaseEntity）
DROP TABLE IF EXISTS t_role;
CREATE TABLE t_role (
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    role_name   VARCHAR(64)  NOT NULL               COMMENT '角色名称',
    role_code   VARCHAR(64)  NOT NULL               COMMENT '角色编码（ADMIN / DOCTOR / PATIENT）',
    description VARCHAR(256) DEFAULT NULL            COMMENT '角色描述',
    status      TINYINT(1)   NOT NULL DEFAULT 1    COMMENT '状态: 0=禁用, 1=启用',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by   BIGINT UNSIGNED DEFAULT NULL,
    update_by   BIGINT UNSIGNED DEFAULT NULL,
    deleted     TINYINT(1)   NOT NULL DEFAULT 0,
    UNIQUE KEY uk_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 4.2 字典类型表（extends BaseEntity）
DROP TABLE IF EXISTS t_dict_type;
CREATE TABLE t_dict_type (
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    dict_name   VARCHAR(128) NOT NULL               COMMENT '字典名称（如"性别"）',
    dict_type   VARCHAR(64)  NOT NULL               COMMENT '字典类型编码（如"sys_gender"）',
    status      TINYINT(1)   NOT NULL DEFAULT 1    COMMENT '状态: 0=禁用, 1=启用',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by   BIGINT UNSIGNED DEFAULT NULL,
    update_by   BIGINT UNSIGNED DEFAULT NULL,
    deleted     TINYINT(1)   NOT NULL DEFAULT 0,
    UNIQUE KEY uk_dict_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型表';

-- 4.3 字典项表（extends BaseEntity）
DROP TABLE IF EXISTS t_dict_item;
CREATE TABLE t_dict_item (
    id           BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    dict_type_id BIGINT UNSIGNED NOT NULL           COMMENT '字典类型ID (t_dict_type.id)',
    label        VARCHAR(128)   NOT NULL            COMMENT '显示标签',
    value        VARCHAR(128)   NOT NULL            COMMENT '存储值',
    sort_order   INT            DEFAULT 0          COMMENT '排序',
    status       TINYINT(1)     NOT NULL DEFAULT 1  COMMENT '状态: 0=禁用, 1=启用',
    create_time  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by    BIGINT UNSIGNED DEFAULT NULL,
    update_by    BIGINT UNSIGNED DEFAULT NULL,
    deleted      TINYINT(1)     NOT NULL DEFAULT 0,
    KEY idx_dict_type_id (dict_type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典项表';

-- ---------- 种子数据 ----------
INSERT INTO t_role (role_name, role_code, description, status) VALUES
('超级管理员', 'ADMIN',   '系统超级管理员', 1),
('医生',       'DOCTOR',  '体检医生',       1),
('患者',       'PATIENT', '体检用户',       1);

INSERT INTO t_dict_type (dict_name, dict_type, status) VALUES
('性别',       'sys_gender',        1),
('预约状态',   'exam_appt_status',  1),
('报告状态',   'report_status',     1),
('预约类型',   'exam_appt_type',    1);

-- sys_gender
INSERT INTO t_dict_item (dict_type_id, label, value, sort_order, status) VALUES
(1, '男', '1', 1, 1),
(1, '女', '0', 2, 1);

-- exam_appt_status
INSERT INTO t_dict_item (dict_type_id, label, value, sort_order, status) VALUES
(2, '已确认', '1', 1, 1),
(2, '已取消', '2', 2, 1),
(2, '已完成', '3', 3, 1);

-- report_status
INSERT INTO t_dict_item (dict_type_id, label, value, sort_order, status) VALUES
(3, '草稿',   '0', 1, 1),
(3, '已生成', '1', 2, 1),
(3, '已发布', '2', 3, 1);

-- exam_appt_type
INSERT INTO t_dict_item (dict_type_id, label, value, sort_order, status) VALUES
(4, '个人体检',   'individual',  1, 1),
(4, '团体体检',   'group',       2, 1),
(4, '入职体检',   'onboarding',  3, 1),
(4, '出入境体检', 'travel',      4, 1);