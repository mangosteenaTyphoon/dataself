package com.shanzhu.dataself.server.todo.controller;

import com.shanzhu.dataself.api.todo.domain.Goal;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.application.domain.JsonResult;
import com.shanzhu.dataself.framework.core.application.page.TableDataInfo;
import com.shanzhu.dataself.framework.jdbc.web.utils.PageUtils;
import com.shanzhu.dataself.framework.log.annotation.Log;
import com.shanzhu.dataself.framework.log.enums.BusinessType;
import com.shanzhu.dataself.framework.security.utils.SecurityUtils;
import com.shanzhu.dataself.server.todo.service.IGoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 目标管理Controller
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
@Tag(description = "GoalController", name = "目标管理")
@RestController
@RequestMapping("/goal")
public class GoalController extends TWTController {

	@Autowired
	private IGoalService goalService;

	/**
	 * 分页查询目标列表
	 * @param goal 目标信息
	 * @return 目标集合
	 */
	@Operation(summary = "分页查询目标列表")
	@GetMapping("/pageQuery")
	@PreAuthorize("@role.hasPermi('todo:goal:list')")
	public JsonResult<TableDataInfo<Goal>> pageQuery(Goal goal) {
		PageUtils.startPage();
		List<Goal> list = goalService.selectGoalList(goal);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 查询目标详情
	 * @param goalId 目标ID
	 * @return 目标信息
	 */
	@Operation(summary = "查询目标详情")
	@GetMapping("/{goalId}")
	@PreAuthorize("@role.hasPermi('todo:goal:query')")
	public JsonResult<Goal> getInfo(@PathVariable Long goalId) {
		return JsonResult.success(goalService.selectGoalById(goalId));
	}

	/**
	 * 新增目标
	 * @param goal 目标信息
	 * @return 结果
	 */
	@Operation(summary = "新增目标")
	@PostMapping
	@Log(service = "目标管理", businessType = BusinessType.INSERT)
	@PreAuthorize("@role.hasPermi('todo:goal:add')")
	public JsonResult<String> add(@Validated @RequestBody Goal goal) {
		goal.setCreateBy(SecurityUtils.getUsername());
		goal.setUserId(SecurityUtils.getLoginUser().getUserId());
		return json(goalService.insertGoal(goal));
	}

	/**
	 * 修改目标
	 * @param goal 目标信息
	 * @return 结果
	 */
	@Operation(summary = "修改目标")
	@PutMapping
	@Log(service = "目标管理", businessType = BusinessType.UPDATE)
	@PreAuthorize("@role.hasPermi('todo:goal:edit')")
	public JsonResult<String> edit(@Validated @RequestBody Goal goal) {
		goal.setUpdateBy(SecurityUtils.getUsername());
		return json(goalService.updateGoal(goal));
	}

	/**
	 * 删除目标
	 * @param goalIds 目标ID数组
	 * @return 结果
	 */
	@Operation(summary = "删除目标")
	@DeleteMapping("/{goalIds}")
	@Log(service = "目标管理", businessType = BusinessType.DELETE)
	@PreAuthorize("@role.hasPermi('todo:goal:remove')")
	public JsonResult<String> remove(@PathVariable Long[] goalIds) {
		return json(goalService.deleteGoalByIds(goalIds));
	}

	/**
	 * 开始目标
	 * @param goalId 目标ID
	 * @return 结果
	 */
	@Operation(summary = "开始目标")
	@PostMapping("/start/{goalId}")
	@Log(service = "目标管理", businessType = BusinessType.UPDATE)
	@PreAuthorize("@role.hasPermi('todo:goal:edit')")
	public JsonResult<String> start(@PathVariable Long goalId) {
		return json(goalService.startGoal(goalId));
	}

	/**
	 * 完成目标
	 * @param goalId 目标ID
	 * @param summary 目标总结
	 * @return 结果
	 */
	@Operation(summary = "完成目标")
	@PostMapping("/complete/{goalId}")
	@Log(service = "目标管理", businessType = BusinessType.UPDATE)
	@PreAuthorize("@role.hasPermi('todo:goal:edit')")
	public JsonResult<String> complete(@PathVariable Long goalId, @RequestParam(required = false) String summary) {
		return json(goalService.completeGoal(goalId, summary));
	}

	/**
	 * 归档目标
	 * @param goalId 目标ID
	 * @return 结果
	 */
	@Operation(summary = "归档目标")
	@PostMapping("/archive/{goalId}")
	@Log(service = "目标管理", businessType = BusinessType.UPDATE)
	@PreAuthorize("@role.hasPermi('todo:goal:edit')")
	public JsonResult<String> archive(@PathVariable Long goalId) {
		return json(goalService.archiveGoal(goalId));
	}

}
