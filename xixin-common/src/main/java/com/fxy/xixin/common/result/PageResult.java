package com.fxy.xixin.common.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果封装类
 * <p>
 * 用于封装分页查询的返回结果，包含总记录数、当前页码、每页大小和数据列表。
 * 提供静态工厂方法，可从 MyBatis-Plus 的 IPage 对象或原始参数快速构建分页结果。
 * 在体检套餐列表、预约记录查询等列表接口中使用。
 * </p>
 *
 * @param <T> 数据记录的类型
 * @author dev
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    /**
     * 总记录数
     */
    private Long total;
    /**
     * 当前页码
     */
    private Long page;
    /**
     * 每页大小
     */
    private Long size;
    /**
     * 当前页数据列表
     */
    private List<T> records;

    /**
     * 从 MyBatis-Plus 分页对象构建分页结果
     *
     * @param page MyBatis-Plus 分页对象
     * @param <T>  数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(IPage<T> page) {
        return new PageResult<>(page.getTotal(), page.getCurrent(), page.getSize(), page.getRecords());
    }

    /**
     * 从原始参数构建分页结果
     *
     * @param total   总记录数
     * @param page    当前页码
     * @param size    每页大小
     * @param records 数据列表
     * @param <T>     数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(long total, long page, long size, List<T> records) {
        return new PageResult<>(total, page, size, records);
    }
}
