package com.shanzhu.dataself.server.todo.service;

import com.shanzhu.dataself.api.todo.domain.dto.StatisticsDTO;

/**
 * 统计分析Service接口
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
public interface IStatisticsService {

	/**
	 * 获取目标统计
	 * @param userId 用户ID
	 * @return 统计数据
	 */
	StatisticsDTO getGoalStatistics(Long userId);

	/**
	 * 获取任务统计
	 * @param userId 用户ID
	 * @return 统计数据
	 */
	StatisticsDTO getTaskStatistics(Long userId);

}
