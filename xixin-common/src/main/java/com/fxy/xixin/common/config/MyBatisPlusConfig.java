package com.fxy.xixin.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.fxy.xixin.common.context.UserContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 配置类
 * <p>
 * 配置 MyBatis-Plus 的核心组件：
 * <ul>
 *   <li>{@link MybatisPlusInterceptor} - 插件拦截器，用于分页等扩展功能</li>
 *   <li>{@link MetaObjectHandler} - 元对象自动填充处理器，在插入时自动填充
 *       createTime、updateTime、createBy、updateBy 和 deleted 字段，
 *       在更新时自动填充 updateTime 字段</li>
 * </ul>
 * 配合 {@link com.fxy.xixin.common.base.BaseEntity} 使用，实现公共字段的自动化管理，
 * 减少业务代码中的重复赋值操作。体检预约系统中所有数据表均继承 BaseEntity，
 * 通过此配置确保时间戳和操作人信息的自动维护。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * 注册 MyBatis-Plus 插件拦截器
     *
     * @return MybatisPlusInterceptor 实例（当前仅用于启用以插件方式工作的分页功能）
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        return new MybatisPlusInterceptor();
    }

    /**
     * 注册元对象自动填充处理器
     * <p>
     * 插入时自动填充 createTime、updateTime、createBy、updateBy 为当前操作人和时间；
     * deleted 默认为 0（未删除）。更新时自动填充 updateTime。
     * </p>
     *
     * @return MetaObjectHandler 实例
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
                this.strictInsertFill(metaObject, "createBy", () -> UserContext.getUserId(), Long.class);
                this.strictInsertFill(metaObject, "updateBy", () -> UserContext.getUserId(), Long.class);
                this.setFieldValByName("deleted", 0, metaObject);
            }



            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
                this.strictUpdateFill(metaObject, "updateBy", () -> UserContext.getUserId(), Long.class);
            }
        };
    }
}
