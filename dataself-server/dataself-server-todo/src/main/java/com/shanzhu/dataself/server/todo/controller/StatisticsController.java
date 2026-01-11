package com.shanzhu.dataself.server.todo.controller;

import com.shanzhu.dataself.api.todo.domain.dto.StatisticsDTO;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.application.domain.JsonResult;
import com.shanzhu.dataself.framework.security.utils.SecurityUtils;
import com.shanzhu.dataself.server.todo.service.IStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 统计分析Controller
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
@Tag(description = "StatisticsController", name = "统计分析")
@RestController
@RequestMapping("/statistics")
public class StatisticsController extends TWTController {

	@Autowired
	private IStatisticsService statisticsService;

	/**
	 * 获取目标统计
	 * @return 目标统计数据
	 */
	@Operation(summary = "获取目标统计")
	@GetMapping("/goal")
	@PreAuthorize("@role.hasPermi('todo:statistics:query')")
	public JsonResult<StatisticsDTO> getGoalStatistics() {
		Long userId = SecurityUtils.getLoginUser().getUserId();
		StatisticsDTO statistics = statisticsService.getGoalStatistics(userId);
		return JsonResult.success(statistics);
	}

	/**
	 * 获取任务统计
	 * @return 任务统计数据
	 */
	@Operation(summary = "获取任务统计")
	@GetMapping("/task")
	@PreAuthorize("@role.hasPermi('todo:statistics:query')")
	public JsonResult<StatisticsDTO> getTaskStatistics() {
		Long userId = SecurityUtils.getLoginUser().getUserId();
		StatisticsDTO statistics = statisticsService.getTaskStatistics(userId);
		return JsonResult.success(statistics);
	}

	/**
	 * 获取数据看板
	 * @return 数据看板
	 */
	@Operation(summary = "获取数据看板")
	@GetMapping("/dashboard")
	@PreAuthorize("@role.hasPermi('todo:statistics:query')")
	public JsonResult<Map<String, Object>> getDashboard() {
		Long userId = SecurityUtils.getLoginUser().getUserId();

		Map<String, Object> dashboard = new HashMap<>(2);
		dashboard.put("goalStatistics", statisticsService.getGoalStatistics(userId));
		dashboard.put("taskStatistics", statisticsService.getTaskStatistics(userId));

		return JsonResult.success(dashboard);
	}

}
