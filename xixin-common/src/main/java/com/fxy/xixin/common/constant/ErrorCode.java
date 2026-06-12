package com.fxy.xixin.common.constant;

import lombok.Getter;

/**
 * 统一错误码枚举
 * <p>
 * 定义系统中所有业务异常的错误码和对应提示消息。
 * 按模块分段管理：
 * <ul>
 *   <li>1xxx - 用户模块（登录、注册、账号管理）</li>
 *   <li>2xxx - 体检模块（预约、套餐、机构）</li>
 *   <li>3xxx - 报告模块（体检报告生成与查询）</li>
 *   <li>4xxx - 系统模块（角色、权限、菜单、字典）</li>
 *   <li>5xxx - 认证模块（Token 校验）</li>
 * </ul>
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Getter
public enum ErrorCode {

    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未认证，请先登录"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "服务器内部错误"),

    // 1xxx: 用户模块
    USER_NOT_EXIST(1001, "用户不存在"),
    USER_PASSWORD_ERROR(1002, "密码错误"),
    USER_ACCOUNT_DISABLED(1003, "账号已被禁用"),
    USER_PHONE_EXIST(1004, "手机号已注册"),
    USERNAME_EXIST(1005, "用户名已存在"),

    // 2xxx: 体检模块
    APPOINTMENT_CONFLICT(2001, "预约时间冲突"),
    EXAM_PACKAGE_NOT_EXIST(2002, "体检套餐不存在"),
    APPOINTMENT_NOT_EXIST(2003, "预约记录不存在"),
    APPOINTMENT_CANNOT_CANCEL(2004, "当前状态不可取消"),

    // 3xxx: 报告模块
    REPORT_NOT_EXIST(3001, "报告不存在"),
    REPORT_ALREADY_GENERATED(3002, "报告已生成，不可重复生成"),
    REPORT_TEMPLATE_NOT_EXIST(3003, "报告模板不存在"),

    // 4xxx: 系统模块
    ROLE_NAME_EXIST(4001, "角色名已存在"),
    ROLE_NOT_EXIST(4002, "角色不存在"),
    PERMISSION_NOT_EXIST(4003, "权限不存在"),
    DICT_TYPE_EXIST(4004, "字典类型已存在"),
    MENU_NOT_EXIST(4005, "菜单不存在"),

    // 5xxx: 认证模块
    TOKEN_EXPIRED(5001, "令牌已过期"),
    TOKEN_INVALID(5002, "令牌无效"),
    REFRESH_TOKEN_INVALID(5003, "刷新令牌无效");

    private final Integer code;
    private final String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
