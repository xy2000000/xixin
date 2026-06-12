# 东软熙心健康体检管理系统 API 文档

> Base URL: `http://localhost:8080` | 生成时间: 2026-06-04

---

## 目录

- [1. 认证服务](#1-认证服务)
- [2. 套餐管理](#2-套餐管理)
- [3. 检查项目](#3-检查项目)
- [4. 预约管理](#4-预约管理)
- [5. 报告管理](#5-报告管理)
- [3. 机构管理](#3-机构管理)
- [4. 检查项目](#4-检查项目)
- [5. 预约管理](#5-预约管理)
- [6. 报告管理](#6-报告管理)
- [7. 用户管理](#7-用户管理)
- [8. 角色管理](#8-角色管理)
- [9. 字典维护](#9-字典维护)
- [10. 医生/患者查询](#10-医生患者查询)
- [附录: 统一响应 & 错误码](#附录)

---

## 1. 认证服务

网关路由: `/api/auth` → `xixin-auth:8100`

### 1.1 登录

```
POST /api/auth/login
```

**权限:** 公开

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| username | String | ✅ | 用户名 |
| password | String | ✅ | 密码（明文，服务端 MD5 比对） |

**响应 (200):**
```json
{
  "code": 200,
  "data": {
    "accessToken": "eyJhbGciOiJIUzM4NCJ9...",
    "refreshToken": "eyJhbGciOiJIUzM4NCJ9...",
    "expiresIn": 7200
  }
}
```

### 1.2 注册

```
POST /api/auth/register
```

**权限:** 公开

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| username | String | ✅ | 用户名（唯一） |
| password | String | ✅ | 密码 |
| realName | String | ✅ | 真实姓名 |
| role | String | ✅ | `ADMIN` / `DOCTOR` / `PATIENT` |
| phone | String | | 手机号 |
| email | String | | 邮箱 |

---

## 2. 套餐管理

网关路由: `/api/exam/packages` → `xixin-exam:8120`

| 方法 | 路径 | 权限 | 说明 |
|------|------|:--:|------|
| GET | `/api/exam/packages?page=1&size=10` | PATIENT/ADMIN | 分页列表 |
| GET | `/api/exam/packages/{id}` | PATIENT/ADMIN | 查询详情 |
| POST | `/api/exam/packages` | ADMIN | 新增套餐 |
| PUT | `/api/exam/packages` | ADMIN | 更新套餐 |
| PUT | `/api/exam/packages/{id}/status?status=0` | ADMIN | 上架/下架 |
| DELETE | `/api/exam/packages/{id}` | ADMIN | 删除 |

**套餐 JSON:**
```json
{
  "name": "基础体检套餐",
  "price": 299.00,
  "description": "包含血常规、尿常规、心电图",
  "status": 1,
  "sortOrder": 1
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| name | String | 套餐名称 |
| price | BigDecimal | 价格 |
| description | String | 描述 |
| status | Integer | 0=下架, 1=上架 |
| sortOrder | Integer | 排序 |

---

## 3. 机构管理

网关路由: `/api/exam/institutions` → `xixin-exam:8120`

| 方法 | 路径 | 权限 | 说明 |
|------|------|:--:|------|
| GET | `/api/exam/institutions?page=1&size=10` | ADMIN | 分页列表 |
| GET | `/api/exam/institutions/active` | PATIENT | 已启用机构 |
| GET | `/api/exam/institutions/{id}` | ADMIN | 查询详情 |
| POST | `/api/exam/institutions` | ADMIN | 新增机构 |
| PUT | `/api/exam/institutions` | ADMIN | 更新机构 |
| DELETE | `/api/exam/institutions/{id}` | ADMIN | 删除 |

**机构 JSON:**
```json
{
  "name": "北京旗舰中心",
  "address": "北京市东城区长安街100号",
  "phone": "010-88881001",
  "businessHours": "周一至五 08:00-17:00",
  "description": "全套设备旗舰中心",
  "status": 1,
  "sortOrder": 1
}
```

---

## 4. 检查项目

网关路由: `/api/exam/packages/{packageId}/items` → `xixin-exam:8120`

| 方法 | 路径 | 权限 | 说明 |
|------|------|:--:|------|
| GET | `.../packages/{id}/items?page=1&size=10` | ADMIN | 项目列表 |
| GET | `.../packages/{id}/items/{itemId}` | ADMIN | 查询项目 |
| POST | `.../packages/{id}/items` | ADMIN | 新增项目 |
| PUT | `.../packages/{id}/items/{itemId}` | ADMIN | 更新项目 |
| DELETE | `.../packages/{id}/items/{itemId}` | ADMIN | 删除项目 |

**请求 JSON:**
```json
{
  "name": "视力检查",
  "type": "Physical",
  "description": "标准视力表检查",
  "sortOrder": 4
}
```

---

## 5. 预约管理

网关路由: `/api/exam/appointments` → `xixin-exam:8120`

| 方法 | 路径 | 权限 | 说明 |
|------|------|:--:|------|
| GET | `/api/exam/appointments?page=1&size=10` | ALL | 预约列表 |
| GET | `/api/exam/appointments/{id}` | ALL | 查询预约 |
| POST | `/api/exam/appointments` | PATIENT | 新增预约 |
| PUT | `/api/exam/appointments/{id}/confirm` | DOCTOR | 确认预约 |
| PUT | `/api/exam/appointments/{id}/assign?doctorId=N` | ADMIN | 分配医生 |
| PUT | `/api/exam/appointments/{id}/cancel` | DOCTOR/PATIENT | 取消预约 |
| DELETE | `/api/exam/appointments/{id}` | ADMIN | 删除 |

**请求 JSON:**
```json
{
  "userId": 2,
  "packageId": 1,
  "appointmentDate": "2026-07-10",
  "timeSlot": "Morning",
  "remark": "空腹前来"
}
```

**status:** 0=待确认, 1=已确认, 2=已取消, 3=已完成

---

## 6. 报告管理

网关路由: `/api/report` → `xixin-report:8130`

| 方法 | 路径 | 权限 | 说明 |
|------|------|:--:|------|
| GET | `/api/report/list?page=1&size=10` | ALL | 报告列表 |
| GET | `/api/report/{id}` | ALL | 查询报告 |
| POST | `/api/report/generate` | DOCTOR | 生成报告 |
| PUT | `/api/report/{id}` | DOCTOR | 更新报告 |
| PUT | `/api/report/{id}/review?approved=true` | DOCTOR | 审核通过 |
| PUT | `/api/report/{id}/review?approved=false&reason=xxx` | DOCTOR | 审核退回 |
| PUT | `/api/report/{id}/publish` | DOCTOR | 发布 |
| DELETE | `/api/report/{id}` | ADMIN | 删除 |

**生成报告 JSON:**
```json
{
  "userId": 2,
  "appointmentId": 1,
  "doctorId": 1,
  "summary": "各项指标基本正常",
  "conclusion": "体检结果正常，建议保持良好生活习惯"
}
```

**status:** 0=草稿, 1=已生成, 2=已发布

---

## 7. 用户管理

网关路由: `/api/auth/users` → `xixin-auth:8100`

| 方法 | 路径 | 权限 | 说明 |
|------|------|:--:|------|
| GET | `/api/auth/users?page=1&size=10` | ADMIN | 用户列表 |
| GET | `/api/auth/users/{id}` | ADMIN | 查询用户 |
| PUT | `/api/auth/users/{id}` | ADMIN | 更新信息 |
| PUT | `/api/auth/users/{id}/status?status=0` | ADMIN | 启用/禁用 |
| PUT | `/api/auth/users/{id}/reset-password?newPassword=xxx` | ADMIN | 重置密码 |

---

## 8. 角色管理

网关路由: `/api/system/roles` → `xixin-system:8140`

| 方法 | 路径 | 权限 | 说明 |
|------|------|:--:|------|
| GET | `/api/system/roles?page=1&size=10` | ADMIN | 角色列表 |
| GET | `/api/system/roles/{id}` | ADMIN | 查询角色 |
| POST | `/api/system/roles` | ADMIN | 新增 |
| PUT | `/api/system/roles` | ADMIN | 更新 |
| DELETE | `/api/system/roles/{id}` | ADMIN | 删除 |

**JSON:**
```json
{
  "roleName": "实习生",
  "roleCode": "INTERN",
  "description": "实习人员",
  "status": 1
}
```

---

## 9. 字典维护

网关路由: `/api/system/dict-types`, `/dict-items` → `xixin-system:8140`

### 字典类型

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/system/dict-types?page=1&size=10` | 列表 |
| GET/POST/PUT/DELETE | `/api/system/dict-types[/{id}]` | CRUD |

**JSON:** `{"dictName":"科室","dictType":"sys_department","status":1}`

### 字典项

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/system/dict-items/by-type/{typeId}?page=1&size=10` | 按类型查询 |
| GET/POST/PUT/DELETE | `/api/system/dict-items[/{id}]` | CRUD |

**JSON:** `{"dictTypeId":1,"label":"男性","value":"1","sortOrder":1,"status":1}`

---

## 10. 医生/患者查询

网关路由: `/api/user/*` → `xixin-user:8110`

| 方法 | 路径 | 权限 | 说明 |
|------|------|:--:|------|
| GET | `/api/user/doctors?page=1&size=10` | DOCTOR/ADMIN | 医生列表 |
| GET | `/api/user/doctors/{id}` | DOCTOR/ADMIN | 医生详情 |
| GET | `/api/user/patients?page=1&size=10` | DOCTOR/ADMIN | 患者列表 |
| GET | `/api/user/patients/{id}` | DOCTOR/ADMIN | 患者详情 |

---

## 附录

### 统一响应格式

```json
{ "code": 200, "message": "操作成功", "data": { ... } }
```
分页: `data` 含 `total/page/size/records`

### 通用错误码

| 码 | 含义 |
|----|------|
| 200 | 成功 |
| 400 | 参数错误 |
| 401 | 未认证 |
| 403 | 无权限 |
| 404 | 不存在 |
| 500 | 内部错误 |

### 数据库

| 库 | 表 |
|----|-----|
| xixin_user | t_user, t_patient, t_doctor |
| xixin_exam | t_exam_package, t_exam_item, t_appointment |
| xixin_report | t_report, t_report_item |
| xixin_system | t_role, t_dict_type, t_dict_item |

所有表含 `create_time/update_time/create_by/update_by/deleted` 审计字段。
