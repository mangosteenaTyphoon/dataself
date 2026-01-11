package com.shanzhu.dataself.server.todo.service.impl;

import com.shanzhu.dataself.api.todo.domain.dto.StatisticsDTO;
import com.shanzhu.dataself.api.todo.enums.GoalStatus;
import com.shanzhu.dataself.api.todo.enums.TaskStatus;
import com.shanzhu.dataself.server.todo.mapper.GoalMapper;
import com.shanzhu.dataself.server.todo.mapper.TaskMapper;
import com.shanzhu.dataself.server.todo.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 统计分析Service实现
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {

	@Autowired
	private GoalMapper goalMapper;

	@Autowired
	private TaskMapper taskMapper;

	/**
	 * 获取目标统计
	 * @param userId 用户ID
	 * @return 统计数据
	 */
	@Override
	public StatisticsDTO getGoalStatistics(Long userId) {
		StatisticsDTO statistics = new StatisticsDTO();

		// 统计总数
		int total = goalMapper.countGoalsByUserId(userId);
		statistics.setTotal(total);

		// 统计各状态数量
		statistics.setDraftCount(goalMapper.countGoalsByUserIdAndStatus(userId, GoalStatus.DRAFT.name()));
		statistics.setNotStartedCount(goalMapper.countGoalsByUserIdAndStatus(userId, GoalStatus.NOT_STARTED.name()));
		statistics.setInProgressCount(goalMapper.countGoalsByUserIdAndStatus(userId, GoalStatus.IN_PROGRESS.name()));
		int completedCount = goalMapper.countGoalsByUserIdAndStatus(userId, GoalStatus.COMPLETED.name());
		statistics.setCompletedCount(completedCount);
		statistics.setNotAchievedCount(goalMapper.countGoalsByUserIdAndStatus(userId, GoalStatus.NOT_ACHIEVED.name()));
		statistics.setArchivedCount(goalMapper.countGoalsByUserIdAndStatus(userId, GoalStatus.ARCHIVED.name()));

		// 计算完成率
		if (total > 0) {
			BigDecimal completionRate = BigDecimal.valueOf(completedCount).multiply(BigDecimal.valueOf(100))
					.divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP);
			statistics.setCompletionRate(completionRate);
		}
		else {
			statistics.setCompletionRate(BigDecimal.ZERO);
		}

		// 获取平均得分
		BigDecimal averageScore = goalMapper.getAverageScoreByUserId(userId);
		statistics.setAverageScore(averageScore != null ? averageScore.setScale(2, RoundingMode.HALF_UP)
				: BigDecimal.ZERO);

		return statistics;
	}

	/**
	 * 获取任务统计
	 * @param userId 用户ID
	 * @return 统计数据
	 */
	@Override
	public StatisticsDTO getTaskStatistics(Long userId) {
		StatisticsDTO statistics = new StatisticsDTO();

		// 统计总数
		int total = taskMapper.countTasksByUserId(userId);
		statistics.setTotal(total);

		// 统计各状态数量
		statistics.setNotStartedCount(taskMapper.countTasksByUserIdAndStatus(userId, TaskStatus.NOT_STARTED.name()));
		statistics.setInProgressCount(taskMapper.countTasksByUserIdAndStatus(userId, TaskStatus.IN_PROGRESS.name()));
		int completedCount = taskMapper.countTasksByUserIdAndStatus(userId, TaskStatus.COMPLETED.name());
		statistics.setCompletedCount(completedCount);
		statistics.setNotAchievedCount(taskMapper.countTasksByUserIdAndStatus(userId, TaskStatus.NOT_ACHIEVED.name()));

		// 计算完成率
		if (total > 0) {
			BigDecimal completionRate = BigDecimal.valueOf(completedCount).multiply(BigDecimal.valueOf(100))
					.divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP);
			statistics.setCompletionRate(completionRate);
		}
		else {
			statistics.setCompletionRate(BigDecimal.ZERO);
		}

		return statistics;
	}

}
