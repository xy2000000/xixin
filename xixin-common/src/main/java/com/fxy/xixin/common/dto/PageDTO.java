package com.fxy.xixin.common.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * 分页查询请求参数封装类
 * <p>
 * 接收前端分页请求参数，包含页码、每页大小、排序字段和排序方向。
 * 提供 {@link #toPage()} 方法将参数转换为 MyBatis-Plus 的 Page 对象，
 * 供 Service 层直接使用进行分页查询。
 * 在体检套餐列表、预约记录查询等分页接口中作为入参。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Data
public class PageDTO {

    /**
     * 当前页码，默认第1页
     */
    private Long page = 1L;
    /**
     * 每页大小，默认10条
     */
    private Long size = 10L;
    /**
     * 排序字段名（数据库列名）
     */
    private String sortField;
    /**
     * 是否升序，默认 true
     */
    private Boolean sortAsc = true;

    /**
     * 将分页参数转换为 MyBatis-Plus 的 Page 对象
     *
     * @param <T> 实体类型
     * @return MyBatis-Plus 分页对象，包含排序配置
     */
    public <T> Page<T> toPage() {
        Page<T> p = new Page<>(page, size);
        if (sortField != null && !sortField.isBlank()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(sortField);
            orderItem.setAsc(sortAsc);
            p.addOrder(orderItem);
        }
        return p;
    }
}
