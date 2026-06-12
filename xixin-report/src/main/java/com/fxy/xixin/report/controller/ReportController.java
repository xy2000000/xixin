package com.fxy.xixin.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.annotation.RequireRole;
import com.fxy.xixin.common.constant.ErrorCode;
import com.fxy.xixin.common.context.UserContext;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.exception.BusinessException;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.report.entity.Report;
import com.fxy.xixin.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 体检报告控制器
 * <p>
 * 提供体检报告的 RESTful API 接口，包含报告的增删改查和生成功能。
 * 在体检预约流程中：
 * <ul>
 *   <li>医生完成体检后调用 /generate 录入结果，报告直接发布</li>
 *   <li>/list 和 /{id} 接口供患者/医生查询报告列表和详情（仅返回各自有权查看的数据）</li>
 *   <li>/update 接口供医生修正报告内容</li>
 *   <li>仅管理员可删除报告</li>
 * </ul>
 * </p>
 *
 * <p><b>权限：查询接口供所有角色 / 写入接口供 DOCTOR / 删除仅 ADMIN</b></p>
 *
 * @author dev
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * 分页查询报告列表
     * <p>
     * 根据角色返回不同数据范围：
     * <ul>
     *   <li>PATIENT：仅返回本人的报告</li>
     *   <li>DOCTOR：仅返回自己生成的报告</li>
     *   <li>ADMIN：返回全部报告</li>
     * </ul>
     * </p>
     *
     * <p><b>权限：PATIENT、DOCTOR、ADMIN</b></p>
     *
     * @param page 分页参数
     * @return 分页报告数据
     */
    @GetMapping("/list")
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    public R<PageResult<Report>> list(PageDTO page) {
        Long userId = UserContext.getUserId();
        String role = UserContext.getRole();
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<Report>()
                .orderByDesc(Report::getCreateTime);

        if ("PATIENT".equalsIgnoreCase(role)) {
            // 患者：只能查看自己的报告
            wrapper.eq(Report::getUserId, userId);
        } else if ("DOCTOR".equalsIgnoreCase(role)) {
            // 医生：只能查看自己生成的报告
            wrapper.eq(Report::getDoctorId, userId);
        }
        // 管理员：不过滤，查看全部

        IPage<Report> result = reportService.page(page.toPage(), wrapper);
        return R.ok(PageResult.of(result));
    }

    /**
     * 根据ID查询单份报告详情
     * <p>
     * PATIENT 只能查看自己的报告，DOCTOR 只能查看自己生成的报告，ADMIN 可查看任意报告。
     * </p>
     *
     * <p><b>权限：PATIENT、DOCTOR、ADMIN</b></p>
     *
     * @param id 报告ID
     * @return 报告详情
     */
    @GetMapping("/{id}")
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    public R<Report> getById(@PathVariable Long id) {
        Report report = reportService.getById(id);
        if (report == null) {
            return R.ok();
        }

        String role = UserContext.getRole();
        Long userId = UserContext.getUserId();

        // 患者只能看自己的报告，医生只能看自己生成的报告
        if ("PATIENT".equalsIgnoreCase(role) && !report.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权查看他人的报告");
        }
        if ("DOCTOR".equalsIgnoreCase(role) && !report.getDoctorId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权查看非本人生成的报告");
        }

        return R.ok(report);
    }

    /**
     * 生成体检报告（直接发布）
     * <p>
     * 医生完成体检后，通过此接口录入检查摘要、总检结论等信息。
     * 报告生成后直接设为"已发布"状态（status=2），患者可立即查看。
     * 医生ID从当前登录用户的 Token 中自动获取。
     * 如需修改，医生可通过更新接口（PUT /{id}）修正错误。
     * </p>
     *
     * <p><b>权限：DOCTOR</b></p>
     *
     * @param report 报告信息（含用户ID、预约ID、摘要、结论等；doctorId 由系统自动填充）
     * @return 生成的报告记录
     */
    @PostMapping("/generate")
    @RequireRole({"DOCTOR"})
    public R<Report> generate(@RequestBody Report report) {
        // 从 Token 中获取当前医生ID，确保报告归属正确
        Long doctorId = UserContext.getUserId();
        report.setDoctorId(doctorId);

        // 报告直接发布，患者可立即查看
        report.setStatus(2);
        reportService.save(report);
        return R.ok(report);
    }

    /**
     * 更新报告信息
     * <p>
     * 医生发现报告内容有误时，通过此接口修正摘要、结论等字段。
     * 例如：补填遗漏的检查发现、修正笔误、调整健康建议等。
     * 只能更新自己生成的报告。
     * </p>
     *
     * <p><b>权限：DOCTOR</b></p>
     *
     * @param id     报告ID
     * @param report 更新的报告内容
     * @return 操作结果
     */
    @PutMapping("/{id}")
    @RequireRole({"DOCTOR"})
    public R<Void> update(@PathVariable Long id, @RequestBody Report report) {
        Report existing = reportService.getById(id);
        if (existing == null) {
            return R.ok();
        }

        // 医生只能修改自己生成的报告
        Long doctorId = UserContext.getUserId();
        if (!existing.getDoctorId().equals(doctorId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "只能修改自己生成的报告");
        }

        report.setId(id);
        reportService.updateById(report);
        return R.ok();
    }

    /**
     * 删除报告（逻辑删除）
     * <p>
     * 仅管理员可执行此操作。数据不会从数据库中物理删除，
     * 而是将 deleted 标记设置为 1。
     * </p>
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 报告ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @RequireRole({"ADMIN"})
    public R<Void> delete(@PathVariable Long id) {
        reportService.removeById(id);
        return R.ok();
    }
}
