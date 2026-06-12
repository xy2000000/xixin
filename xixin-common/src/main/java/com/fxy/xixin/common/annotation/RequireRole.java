package com.fxy.xixin.common.annotation;

import java.lang.annotation.*;

/**
 * 角色权限注解
 * <p>
 * 标注在控制器方法或类上，声明访问该接口所需的角色。
 * 由 {@link com.fxy.xixin.common.aspect.RoleAspect} AOP 切面在运行时校验。
 * </p>
 *
 * <p>使用示例：</p>
 * <pre>
 *   // 仅管理员可访问
 *   {@code @RequireRole("ADMIN")}
 *   public R<Void> deleteUser(...) { ... }
 *
 *   // 患者和管理员均可访问
 *   {@code @RequireRole({"PATIENT", "ADMIN"})}
 *   public R<ExamPackage> getById(...) { ... }
 * </pre>
 *
 * <p>如果注解在类级别，则对类中所有方法生效（方法级别的注解会覆盖类级别）。
 * 如果当前用户角色不在声明的列表中，AOP 切面会抛出 403 无权限异常。</p>
 *
 * @author dev
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRole {

    /**
     * 允许访问的角色列表
     *
     * @return 角色编码数组，如 {"ADMIN"}、{"PATIENT", "ADMIN"}、{"DOCTOR", "ADMIN"}
     */
    String[] value();
}
