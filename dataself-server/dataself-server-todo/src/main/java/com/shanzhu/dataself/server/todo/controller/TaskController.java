package com.shanzhu.dataself.server.todo.controller;

import com.shanzhu.dataself.api.todo.domain.Task;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.application.domain.JsonResult;
import com.shanzhu.dataself.framework.core.application.page.TableDataInfo;
import com.shanzhu.dataself.framework.jdbc.web.utils.PageUtils;
import com.shanzhu.dataself.framework.log.annotation.Log;
import com.shanzhu.dataself.framework.log.enums.BusinessType;
import com.shanzhu.dataself.framework.security.utils.SecurityUtils;
import com.shanzhu.dataself.server.todo.service.ITaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务管理Controller
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
@Tag(description = "TaskController", name = "任务管理")
@RestController
@RequestMapping("/task")
public class TaskController extends TWTController {

	@Autowired
	private ITaskService taskService;

	/**
	 * 分页查询任务列表
	 * @param task 任务信息
	 * @return 任务集合
	 */
	@Operation(summary = "分页查询任务列表")
	@GetMapping("/pageQuery")
	@PreAuthorize("@role.hasPermi('todo:task:list')")
	public JsonResult<TableDataInfo<Task>> pageQuery(Task task) {
		PageUtils.startPage();
		List<Task> list = taskService.selectTaskList(task);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 查询任务详情
	 * @param taskId 任务ID
	 * @return 任务信息
	 */
	@Operation(summary = "查询任务详情")
	@GetMapping("/{taskId}")
	@PreAuthorize("@role.hasPermi('todo:task:query')")
	public JsonResult<Task> getInfo(@PathVariable Long taskId) {
		return JsonResult.success(taskService.selectTaskById(taskId));
	}

	/**
	 * 查询目标下的任务列表
	 * @param goalId 目标ID
	 * @return 任务集合
	 */
	@Operation(summary = "查询目标下的任务列表")
	@GetMapping("/goal/{goalId}")
	@PreAuthorize("@role.hasPermi('todo:task:list')")
	public JsonResult<List<Task>> listByGoalId(@PathVariable Long goalId) {
		return JsonResult.success(taskService.selectTasksByGoalId(goalId));
	}

	/**
	 * 新增任务
	 * @param task 任务信息
	 * @return 结果
	 */
	@Operation(summary = "新增任务")
	@PostMapping
	@Log(service = "任务管理", businessType = BusinessType.INSERT)
	@PreAuthorize("@role.hasPermi('todo:task:add')")
	public JsonResult<String> add(@Validated @RequestBody Task task) {
		task.setCreateBy(SecurityUtils.getUsername());
		task.setUserId(SecurityUtils.getLoginUser().getUserId());
		return json(taskService.insertTask(task));
	}

	/**
	 * 修改任务
	 * @param task 任务信息
	 * @return 结果
	 */
	@Operation(summary = "修改任务")
	@PutMapping
	@Log(service = "任务管理", businessType = BusinessType.UPDATE)
	@PreAuthorize("@role.hasPermi('todo:task:edit')")
	public JsonResult<String> edit(@Validated @RequestBody Task task) {
		task.setUpdateBy(SecurityUtils.getUsername());
		return json(taskService.updateTask(task));
	}

	/**
	 * 删除任务
	 * @param taskIds 任务ID数组
	 * @return 结果
	 */
	@Operation(summary = "删除任务")
	@DeleteMapping("/{taskIds}")
	@Log(service = "任务管理", businessType = BusinessType.DELETE)
	@PreAuthorize("@role.hasPermi('todo:task:remove')")
	public JsonResult<String> remove(@PathVariable Long[] taskIds) {
		return json(taskService.deleteTaskByIds(taskIds));
	}

	/**
	 * 开始任务
	 * @param taskId 任务ID
	 * @return 结果
	 */
	@Operation(summary = "开始任务")
	@PostMapping("/start/{taskId}")
	@Log(service = "任务管理", businessType = BusinessType.UPDATE)
	@PreAuthorize("@role.hasPermi('todo:task:edit')")
	public JsonResult<String> start(@PathVariable Long taskId) {
		return json(taskService.startTask(taskId));
	}

	/**
	 * 完成任务
	 * @param taskId 任务ID
	 * @param score 任务评分
	 * @param summary 任务总结
	 * @return 结果
	 */
	@Operation(summary = "完成任务")
	@PostMapping("/complete/{taskId}")
	@Log(service = "任务管理", businessType = BusinessType.UPDATE)
	@PreAuthorize("@role.hasPermi('todo:task:edit')")
	public JsonResult<String> complete(@PathVariable Long taskId, 
			@RequestParam String score, 
			@RequestParam(required = false) String summary) {
		return json(taskService.completeTask(taskId, score, summary));
	}

	/**
	 * 标记未达成
	 * @param taskId 任务ID
	 * @return 结果
	 */
	@Operation(summary = "标记未达成")
	@PostMapping("/notAchieved/{taskId}")
	@Log(service = "任务管理", businessType = BusinessType.UPDATE)
	@PreAuthorize("@role.hasPermi('todo:task:edit')")
	public JsonResult<String> markNotAchieved(@PathVariable Long taskId) {
		return json(taskService.markNotAchieved(taskId));
	}

}
