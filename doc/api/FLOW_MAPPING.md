# 东软熙心健康体检管理系统 — 流程功能对照表

> 本文档将流程图中的每一个步骤，映射到具体的 API 接口和代码实现，说明对应功能的作用。

---

## 一、患者 (PATIENT) 流程图

```
注册/登录 → 选择预约类型 → 选择体检机构 → 查看套餐列表 → 选择套餐预约 → 选择日期 → 确认预约 → 查看我的预约 → 取消预约 → 查看体检报告
```

| 流程图步骤 | 接口 | 方法 | 作用 |
|-----------|------|:--:|------|
| **注册** | `/api/auth/register` | POST | 患者自主注册账号，填写用户名、密码、姓名、手机号；系统将密码 MD5 加密后存入 `t_user` 表，角色自动设为 `PATIENT` |
| **登录** | `/api/auth/login` | POST | 验证用户名密码，签发 JWT 令牌（含 userId、username、role），后续所有请求携带此令牌识别身份 |
| **选择预约类型** | `/api/system/dict-items/by-type/4` | GET | 获取预约类型字典：个人/团体/入职/出入境，用于前端下拉框。每个类型关联不同机构（`t_appt_type_institution`） |
| **选择体检机构** | `/api/exam/appointment-types/{type}/institutions` | GET | 根据所选预约类型，返回可用的机构列表（含名称、地址、电话、营业时间），通过 `t_appt_type_institution` 关联表过滤 |
| **查看套餐列表** | `/api/exam/institutions/{id}/packages` | GET | 根据所选机构，返回该机构提供的套餐（含名称、价格、描述），通过 `t_institution_package` 关联表过滤 |
| **套餐详情（含检查项目）** | `/api/exam/packages/{id}` | GET | 查看套餐完整信息及包含的检查项目（`t_exam_item` 关联） |
| **提交预约** | `/api/exam/appointments` | POST | 提交 institutionId、packageId、appointmentDate、timeSlot，生成待确认记录（status=0） |
| **查看我的预约** | `/api/exam/appointments?page=1&size=10` | GET | 分页查询本人的预约列表，显示机构、套餐、日期、状态 |
| **取消预约** | `/api/exam/appointments/{id}/cancel` | PUT | 将预约状态设为"已取消"（status=2），在医生确认前可随时取消 |
| **查看体检报告** | `/api/report/list?page=1&size=10` | GET | 查看属于自己的体检报告列表 |
| **查看报告详情** | `/api/report/{id}` | GET | 查看单份报告的摘要、结论和各项检查结果（`t_report_item`） |

---

## 二、医生 (DOCTOR) 流程图

```
登录 → 查看预约列表 → 确认/取消预约 → 查看患者档案 → 录入体检结果 → 审核报告 → 发布报告
```

| 流程图步骤 | 接口 | 方法 | 作用 |
|-----------|------|:--:|------|
| **登录** | `/api/auth/login` | POST | 与患者相同，系统根据 role 字段识别医生身份 |
| **查看预约列表** | `/api/exam/appointments?page=1&size=10` | GET | 查看所有患者的预约，按日期筛选待处理的预约 |
| **确认预约** | `/api/exam/appointments/{id}/confirm` | PUT | 医生确认患者的预约有效（status 0→1），患者按约定时间前来体检 |
| **取消预约** | `/api/exam/appointments/{id}/cancel` | PUT | 医生因故取消预约（status→2），如设备故障或医生临时停诊 |
| **查看患者档案** | `/api/user/patients?page=1&size=10` | GET | 查询患者基本信息（姓名、性别、出生日期、联系方式），为体检评估提供参考 |
| **查看患者详情** | `/api/user/patients/{id}` | GET | 查看单个患者的完整档案，含身份证号、地址、紧急联系人 |
| **录入体检结果** | `/api/report/generate` | POST | 医生完成体检后，录入摘要(summary)和总检结论(conclusion)，在 `t_report` 表生成报告（status=1，已生成待审核） |
| **修改报告** | `/api/report/{id}` | PUT | 医生在审核发布前修改报告的摘要或结论 |
| **审核报告(通过/退回)** | `/api/report/{id}/review` | PUT | 上级医生审核报告，通过则 status→2(已发布)，退回则 status→0(草稿)并附退回原因 |
| **发布报告** | `/api/report/{id}/publish` | PUT | 医生直接发布报告（status→2），患者可在前端查看 |
| **查看已生成报告** | `/api/report/list?page=1&size=10` | GET | 查看自己生成的报告及其审核状态 |

---

## 三、管理员 (ADMIN) 流程图

```
登录 → 用户管理 → 角色管理 → 套餐管理 → 检查项目管理 → 预约管理 → 字典维护
```

### 3.1 用户管理

| 流程图步骤 | 接口 | 方法 | 作用 |
|-----------|------|:--:|------|
| **用户列表** | `/api/auth/users?page=1&size=10` | GET | 查看系统所有注册用户（含患者、医生、管理员） |
| **查看用户** | `/api/auth/users/{id}` | GET | 查看单个用户的详细信息 |
| **编辑用户** | `/api/auth/users/{id}` | PUT | 修改用户的真实姓名、手机号、邮箱、角色，用于更正错误信息或调整角色 |
| **启用/禁用** | `/api/auth/users/{id}/status?status=0` | PUT | 禁用违规用户（status=0）或重新启用（status=1），禁用后无法登录 |
| **重置密码** | `/api/auth/users/{id}/reset-password?newPassword=xxx` | PUT | 为用户重置密码（MD5 加密存储），解决用户忘记密码的问题 |

### 3.2 角色管理

| 流程图步骤 | 接口 | 方法 | 作用 |
|-----------|------|:--:|------|
| **角色列表** | `/api/system/roles?page=1&size=10` | GET | 查看系统所有角色及其编码、描述 |
| **新增角色** | `/api/system/roles` | POST | 创建新角色（如护士、技师），后续注册时可分配该角色 |
| **编辑角色** | `/api/system/roles` | PUT | 修改角色名称或描述 |
| **删除角色** | `/api/system/roles/{id}` | DELETE | 删除不再使用的角色（逻辑删除，deleted=1） |

### 3.3 机构管理

| 流程图步骤 | 接口 | 方法 | 作用 |
|-----------|------|:--:|------|
| **机构列表** | `/api/exam/institutions?page=1&size=10` | GET | 查看所有体检机构（含已停用的） |
| **已启用机构** | `/api/exam/institutions/active` | GET | 患者端用，只返回 status=1 的机构 |
| **新增机构** | `/api/exam/institutions` | POST | 创建新的体检分支机构，填写名称、地址、电话、营业时间 |
| **编辑机构** | `/api/exam/institutions` | PUT | 修改机构信息（地址变更、营业时间调整） |
| **删除机构** | `/api/exam/institutions/{id}` | DELETE | 移除已关闭的机构 |

### 3.4 套餐管理

| 流程图步骤 | 接口 | 方法 | 作用 |
|-----------|------|:--:|------|
| **套餐列表** | `/api/exam/packages?page=1&size=10` | GET | 查看所有套餐（含已下架的） |
| **新增套餐** | `/api/exam/packages` | POST | 创建新的体检套餐，设置名称、价格、描述 |
| **编辑套餐** | `/api/exam/packages` | PUT | 修改套餐信息（调价、更新描述） |
| **上架/下架** | `/api/exam/packages/{id}/status?status=0` | PUT | 下架过时套餐（status=0，患者不可见）或重新上架（status=1） |
| **删除套餐** | `/api/exam/packages/{id}` | DELETE | 彻底移除套餐（逻辑删除） |

### 3.5 检查项目管理

| 流程图步骤 | 接口 | 方法 | 作用 |
|-----------|------|:--:|------|
| **项目列表** | `/api/exam/packages/{packageId}/items?page=1&size=10` | GET | 查看某个套餐包含的所有检查项目 |
| **新增项目** | `/api/exam/packages/{packageId}/items` | POST | 在套餐中添加新检查项（如血常规、心电图），设置项目名称、类型（Lab/Imaging/Physical/Cardio） |
| **编辑项目** | `/api/exam/packages/{packageId}/items/{itemId}` | PUT | 修改检查项目的名称或描述 |
| **删除项目** | `/api/exam/packages/{packageId}/items/{itemId}` | DELETE | 从套餐中移除某个检查项目 |

### 3.6 预约管理

| 流程图步骤 | 接口 | 方法 | 作用 |
|-----------|------|:--:|------|
| **预约列表** | `/api/exam/appointments?page=1&size=10` | GET | 查看全部预约，按日期和状态筛选 |
| **确认预约** | `/api/exam/appointments/{id}/confirm` | PUT | 管理员或医生确认预约有效 |
| **分配医生** | `/api/exam/appointments/{id}/assign?doctorId=N` | PUT | 将预约分配给指定医生负责，记录在 `t_appointment` 的 `create_by` 字段 |
| **取消预约** | `/api/exam/appointments/{id}/cancel` | PUT | 取消预约 |
| **删除预约** | `/api/exam/appointments/{id}` | DELETE | 移除预约记录 |

### 3.7 字典维护

| 流程图步骤 | 接口 | 方法 | 作用 |
|-----------|------|:--:|------|
| **字典类型列表** | `/api/system/dict-types?page=1&size=10` | GET | 查看所有字典分类（性别、预约状态、报告状态等） |
| **新增字典类型** | `/api/system/dict-types` | POST | 创建新的字典分类，如"科室"、"血型" |
| **编辑/删除类型** | `/api/system/dict-types[/{id}]` | PUT/DELETE | 修改或删除字典分类 |
| **字典项列表** | `/api/system/dict-items/by-type/{typeId}` | GET | 查看某分类下的所有选项（如性别的"男/女"） |
| **新增字典项** | `/api/system/dict-items` | POST | 添加新的选项值（如预约状态新增"已过期"） |
| **编辑/删除字典项** | `/api/system/dict-items[/{id}]` | PUT/DELETE | 修改或删除某个选项 |

---

## 四、接口总数汇总

| 模块 | 接口数 | 角色 |
|------|:-----:|------|
| 认证（登录/注册） | 2 | ALL |
| 预约类型→机构查询 | 1 | PATIENT |
| 机构→套餐查询 | 1 | PATIENT |
| 机构管理 | 6 | PATIENT + ADMIN |
| 套餐管理 | 6 | PATIENT + ADMIN |
| 检查项目管理 | 5 | ADMIN |
| 预约管理 | 7 | PATIENT + DOCTOR + ADMIN |
| 报告管理 | 8 | DOCTOR + ADMIN |
| 用户管理 | 5 | ADMIN |
| 角色管理 | 5 | ADMIN |
| 字典维护 | 14 | ADMIN (含预约类型) |
| 医生/患者查询 | 4 | DOCTOR + ADMIN |
| **合计** | **64** | |

---

## 五、核心业务流程时序

```
         PATIENT                DOCTOR                   ADMIN
            │                      │                        │
            │── 注册 ──────────────│────────────────────────│
            │── 登录 ──────────────│── 登录 ────────────────│── 登录
            │                      │                        │
            │── 查看套餐 ─────────│                        │── 管理套餐/项目/字典/用户/角色
            │── 预约 ─────────────→│                        │
            │                      │── 确认预约 ───────────→│
            │                      │                        │── 分配医生
            │                      │── 查看患者档案 ───────│
            │── 前来体检 ─────────→│                        │
            │                      │── 录入结果/生成报告 ──→│
            │                      │── 审核报告(通过/退回)  │
            │                      │── 发布报告            │
            │── 查看报告 ─────────│                        │
            │                      │                        │
```

---

> 生成时间: 2026-06-04 | 项目: 东软熙心健康体检管理系统

---

## 六、代码对应关系

| 流程图步骤类别 | Controller | Service | 数据库表 |
|--------------|-----------|---------|---------|
| 登录/注册 | `AuthController` | `AuthService` | `t_user` |
| 用户管理 | `UserController` | `UserMapper` | `t_user` |
| 医生查询 | `DoctorController` | `DoctorService` | `t_doctor` |
| 患者查询 | `PatientController` | `PatientService` | `t_patient` |
| 套餐管理 | `ExamPackageController` | `ExamPackageService` | `t_exam_package` |
| 检查项目 | `ExamItemController` | `ExamItemService` | `t_exam_item` |
| 机构管理 | `InstitutionController` | `InstitutionService` | `t_institution` |
| 预约流程（类型→机构→套餐） | `AppointmentFlowController` | — | `t_appt_type_institution`, `t_institution_package` |
| 预约管理 | `AppointmentController` | `AppointmentService` | `t_appointment` |
| 报告管理 | `ReportController` | `ReportService` | `t_report`, `t_report_item` |
| 角色管理 | `RoleController` | `RoleService` | `t_role` |
| 字典维护 | `DictTypeController`, `DictItemController` | `DictTypeService`, `DictItemMapper` | `t_dict_type`, `t_dict_item` |

---

## 七、数据库表说明

### xixin_user 数据库（用户与角色）

| 表名 | 作用 | 存储内容 |
|------|------|------|
| **t_user** | 系统用户表 | 用户名、密码(MD5加密)、真实姓名、手机号、邮箱、头像、角色(ADMIN/DOCTOR/PATIENT)、启用状态。注册时写入，登录时校验密码 |
| **t_patient** | 患者档案表 | 关联用户ID、姓名、性别、出生日期、身份证号、地址、紧急联系人及电话。医生查看患者信息时读取此表 |
| **t_doctor** | 医生档案表 | 关联用户ID、科室、职称、专长、简介。管理员分配医生和患者选医生时读取此表 |

### xixin_exam 数据库（体检业务）

| 表名 | 作用 | 存储内容 |
|------|------|------|
| **t_exam_package** | 体检套餐表 | 套餐名称、价格、描述、上架/下架状态、排序。患者选套餐时查询 status=1 的记录 |
| **t_exam_item** | 检查项目表 | 所属套餐ID、项目名称、类型(Lab/Imaging/Physical/Cardio)、描述、排序。一个套餐包含多个检查项目 |
| **t_institution** | 体检机构表 | 机构名称、地址、电话、营业时间、描述、启用状态、排序。患者选机构时查询 status=1 的记录 |
| **t_appt_type_institution** | 预约类型↔机构关联表 | 预约类型编码(individual/group/onboarding/travel)与机构ID的多对多映射。例如"团体体检"只关联北京和上海2家机构 |
| **t_institution_package** | 机构↔套餐关联表 | 机构ID与套餐ID的多对多映射。例如成都锦江诊所(社区级别)只提供基础套餐和标准套餐 |
| **t_appointment** | 预约记录表 | 用户ID、套餐ID、机构ID、预约日期、时间段、状态(0待确认→1已确认→2已取消/3已完成)、备注。贯穿整个预约生命周期的核心表 |

### xixin_report 数据库（体检报告）

| 表名 | 作用 | 存储内容 |
|------|------|------|
| **t_report** | 体检报告表 | 用户ID、关联预约ID、医生ID、检查摘要、总检结论、状态(0草稿/1已生成/2已发布)、生成时间。医生录入后生成，管理员审核后发布 |
| **t_report_item** | 报告明细表 | 报告ID、检查项目ID、项目名称、检查结果、参考范围、异常标志。每条记录对应一项检查的具体结果 |

### xixin_system 数据库（系统配置）

| 表名 | 作用 | 存储内容 |
|------|------|------|
| **t_role** | 角色表 | 角色名称、角色编码(唯一)、描述、状态。预设ADMIN/DOCTOR/PATIENT三种角色 |
| **t_dict_type** | 字典类型表 | 字典名称、字典类型编码(唯一)、状态。现有：性别(sys_gender)、预约状态(exam_appt_status)、报告状态(report_status)、预约类型(exam_appt_type) |
| **t_dict_item** | 字典项表 | 所属字典类型ID、标签、值、排序、状态。如"性别"字典下有"男(1)/女(0)"；"预约类型"下有"个人体检/团体体检/入职体检/出入境体检" |

### 数据流程图

```
注册 → t_user(账号) + t_patient(档案)
  ↓
登录 → JWT Token(含userId/role)
  ↓
选预约类型 → t_dict_type[id=4] → t_dict_item(4条)
  ↓
选机构 → t_appt_type_institution(type→institutionIds) → t_institution(status=1)
  ↓
选套餐 → t_institution_package(institutionId→packageIds) → t_exam_package(status=1)
  ↓
确认预约 → t_appointment(userId,packageId,institutionId,date,timeSlot)
  ↓
医生确认 → t_appointment(status=0→1)
  ↓
录入结果 → t_report(userId,doctorId,summary,conclusion) + t_report_item(results)
  ↓
审核发布 → t_report(status=0→1→2)
```
