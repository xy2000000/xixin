package com.fxy.xixin.common.aspect;

import com.fxy.xixin.common.annotation.RequireRole;
import com.fxy.xixin.common.constant.ErrorCode;
import com.fxy.xixin.common.context.UserContext;
import com.fxy.xixin.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 角色权限校验切面
 * <p>
 * 拦截所有标注了 {@link RequireRole} 注解的方法，
 * 从 {@link UserContext} 中获取当前用户的角色，与注解声明的允许角色进行比对。
 * </p>
 *
 * <p>校验逻辑：</p>
 * <ol>
 *   <li>如果当前用户角色为 null（未登录或网关未注入），抛出 403 异常，提示"请先登录"</li>
 *   <li>如果当前用户角色不在允许列表中，抛出 403 异常，提示"无权限访问"</li>
 *   <li>校验通过则正常执行目标方法</li>
 * </ol>
 *
 * <p>注意：不使用 @annotation 参数绑定方式获取注解（在 CGLIB + Sentinel 多层代理下不可靠），
 * 而是通过反射从方法签名中读取注解。</p>
 *
 * @author dev
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
public class RoleAspect {

    /**
     * 环绕通知：校验当前用户是否拥有访问目标方法所需的角色
     * <p>
     * 使用完全限定类名的 @annotation 切点，不绑定注解参数，
     * 改为通过反射从 MethodSignature 中获取 @RequireRole 注解。
     * 这样可以避免 CGLIB 代理多层嵌套时注解绑定失败的问题。
     * </p>
     *
     * @param joinPoint 切点（被拦截的方法）
     * @return 目标方法的返回值
     * @throws Throwable 目标方法抛出的异常或权限校验失败的 BusinessException
     */
    @Around("@annotation(com.fxy.xixin.common.annotation.RequireRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 从方法上获取 @RequireRole 注解
        RequireRole requireRole = method.getAnnotation(RequireRole.class);
        if (requireRole == null) {
            // 如果方法上没有，尝试从实现类的方法上获取（处理 CGLIB 代理的情况）
            Method realMethod = joinPoint.getTarget().getClass()
                    .getMethod(method.getName(), method.getParameterTypes());
            requireRole = realMethod.getAnnotation(RequireRole.class);
        }
        if (requireRole == null) {
            log.warn("未找到 RequireRole 注解，放行: {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        String currentRole = UserContext.getRole();

        // 未登录（网关白名单路径不会注入角色信息）
        if (currentRole == null) {
            log.warn("未登录用户尝试访问受保护接口: {}", joinPoint.getSignature());
            throw new BusinessException(ErrorCode.FORBIDDEN, "请先登录");
        }

        // 检查当前用户角色是否在允许列表中
        String[] allowedRoles = requireRole.value();
        for (String allowed : allowedRoles) {
            if (allowed.equalsIgnoreCase(currentRole)) {
                return joinPoint.proceed();
            }
        }

        log.warn("角色权限不足: 当前角色={}, 要求角色={}, 接口={}",
                currentRole, allowedRoles, joinPoint.getSignature());
        throw new BusinessException(ErrorCode.FORBIDDEN, "无权限访问，当前角色: " + currentRole);
    }
}
