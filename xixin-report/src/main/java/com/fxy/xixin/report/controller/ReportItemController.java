package com.fxy.xixin.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.annotation.RequireRole;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.report.entity.ReportItem;
import com.fxy.xixin.report.service.ReportItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报告项目控制器
 * <p>
 * 提供体检报告项目明细的 RESTful API 接口，项目管理嵌套在报告路径下。
 * 每个接口的 URL 都包含所属报告的 ID（{reportId}），表明项目归属于特定报告。
 * </p>
 *
 * <p>在体检报告流程中的位置：</p>
 * <ol>
 *   <li>患者查看报告时，GET 接口返回所有项目明细及其异常标记</li>
 *   <li>医生调用 POST 接口逐项录入检查结果（创建 ReportItem）</li>
 *   <li>医生调用 PUT 接口修正某个项目的检查结果</li>
 * </ol>
 *
 * <p><b>权限：查询接口供 PATIENT、DOCTOR、ADMIN / 写入接口仅 DOCTOR</b></p>
 *
 * @author dev
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/report/{reportId}/items")
@RequiredArgsConstructor
public class ReportItemController {

    private final ReportItemService reportItemService;

    /**
     * 查询指定报告下的所有检查项目列表（分页）
     * <p>
     * 按创建时间升序返回。异常项目（abnormalFlag=1）会在前端高亮显示。
     * </p>
     *
     * <p><b>权限：PATIENT、DOCTOR、ADMIN</b></p>
     *
     * @param reportId 报告ID
     * @param page     分页参数
     * @return 该报告下的检查项目分页数据
     */
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    @GetMapping
    public R<PageResult<ReportItem>> list(@PathVariable Long reportId, PageDTO page) {
        IPage<ReportItem> result = reportItemService.page(
                page.toPage(),
                new LambdaQueryWrapper<ReportItem>()
                        .eq(ReportItem::getReportId, reportId)
                        .orderByAsc(ReportItem::getCreateTime));
        return R.ok(PageResult.of(result));
    }

    /**
     * 查询指定报告下的所有检查项目（不分页，用于报告详情页一次性展示）
     *
     * <p><b>权限：PATIENT、DOCTOR、ADMIN</b></p>
     *
     * @param reportId 报告ID
     * @return 该报告下的全部检查项目列表
     */
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    @GetMapping("/all")
    public R<List<ReportItem>> listAll(@PathVariable Long reportId) {
        List<ReportItem> items = reportItemService.list(
                new LambdaQueryWrapper<ReportItem>()
                        .eq(ReportItem::getReportId, reportId)
                        .orderByAsc(ReportItem::getCreateTime));
        return R.ok(items);
    }

    /**
     * 根据ID查询单个报告项目详情
     *
     * <p><b>权限：PATIENT、DOCTOR、ADMIN</b></p>
     *
     * @param reportId 报告ID
     * @param id       项目ID
     * @return 项目详情（含检查结果、参考范围、异常标志）
     */
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    @GetMapping("/{id}")
    public R<ReportItem> getById(@PathVariable Long reportId, @PathVariable Long id) {
        return R.ok(reportItemService.getById(id));
    }

    /**
     * 在指定报告下新增一条检查项目结果
     * <p>
     * 医生完成体检后，逐项录入检查结果。每条 ReportItem 包含：
     * <ul>
     *   <li>检查项目名称（如"血常规"）</li>
     *   <li>检查结果值（如"白细胞6.8, 红细胞5.2"）</li>
     *   <li>参考范围（如"白细胞4-10"）</li>
     *   <li>异常标志（结果超出参考范围时标记为1）</li>
     * </ul>
     * </p>
     *
     * <p><b>权限：DOCTOR</b></p>
     *
     * @param reportId 报告ID
     * @param item     项目的检查结果信息
     * @return 操作结果
     */
    @RequireRole({"DOCTOR"})
    @PostMapping
    public R<Void> save(@PathVariable Long reportId, @RequestBody ReportItem item) {
        item.setReportId(reportId);

        // 如果前端未明确设置异常标志，默认为正常
        if (item.getAbnormalFlag() == null) {
            item.setAbnormalFlag(0);
        }

        reportItemService.save(item);
        return R.ok();
    }

    /**
     * 批量录入报告项目结果
     * <p>
     * 医生完成体检后，可以一次性提交所有检查项的结果。
     * 相比逐条调用 POST 接口，此接口效率更高，能减少网络请求次数。
     * </p>
     *
     * <p><b>权限：DOCTOR</b></p>
     *
     * @param reportId 报告ID
     * @param items    检查项目结果列表
     * @return 操作结果
     */
    @RequireRole({"DOCTOR"})
    @PostMapping("/batch")
    public R<Void> saveBatch(@PathVariable Long reportId, @RequestBody List<ReportItem> items) {
        for (ReportItem item : items) {
            item.setReportId(reportId);
            if (item.getAbnormalFlag() == null) {
                item.setAbnormalFlag(0);
            }
        }
        reportItemService.saveBatch(items);
        return R.ok();
    }

    /**
     * 更新报告项目的检查结果
     * <p>
     * 医生修正某个检查项的结果时，通过此接口更新。
     * 例如：发现血常规数值录入有误，修改 result 字段并重新设置 abnormalFlag。
     * </p>
     *
     * <p><b>权限：DOCTOR</b></p>
     *
     * @param reportId 报告ID
     * @param id       项目ID
     * @param item     更新后的项目信息
     * @return 操作结果
     */
    @RequireRole({"DOCTOR"})
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long reportId, @PathVariable Long id, @RequestBody ReportItem item) {
        item.setId(id);
        item.setReportId(reportId);
        reportItemService.updateById(item);
        return R.ok();
    }

    /**
     * 删除报告中的某个检查项目
     * <p>
     * 如某个检查项目录入错误需要重新录入，可先删除再新增。
     * 此操作为逻辑删除，数据不会物理删除。
     * </p>
     *
     * <p><b>权限：DOCTOR</b></p>
     *
     * @param reportId 报告ID
     * @param id       项目ID
     * @return 操作结果
     */
    @RequireRole({"DOCTOR"})
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long reportId, @PathVariable Long id) {
        reportItemService.removeById(id);
        return R.ok();
    }
}