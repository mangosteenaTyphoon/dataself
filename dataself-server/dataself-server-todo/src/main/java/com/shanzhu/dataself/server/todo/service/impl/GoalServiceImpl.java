package com.shanzhu.dataself.server.todo.service.impl;

import com.shanzhu.dataself.api.todo.domain.Goal;
import com.shanzhu.dataself.api.todo.domain.Task;
import com.shanzhu.dataself.api.todo.enums.GoalStatus;
import com.shanzhu.dataself.api.todo.enums.TaskScore;
import com.shanzhu.dataself.framework.core.exception.TWTException;
import com.shanzhu.dataself.framework.datascope.annotation.MicroDataScope;
import com.shanzhu.dataself.server.todo.mapper.GoalMapper;
import com.shanzhu.dataself.server.todo.mapper.TaskMapper;
import com.shanzhu.dataself.server.todo.service.IGoalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 目标管理Service实现
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
@Service
public class GoalServiceImpl implements IGoalService {

	private static final Logger log = LoggerFactory.getLogger(GoalServiceImpl.class);

	@Autowired
	private GoalMapper goalMapper;

	@Autowired
	private TaskMapper taskMapper;

	/**
	 * 查询目标列表
	 * @param goal 目标信息
	 * @return 目标集合
	 */
	@Override
	@MicroDataScope(userIdField = "user_id")
	public List<Goal> selectGoalList(Goal goal) {
		return goalMapper.selectGoalList(goal);
	}

	/**
	 * 查询目标详情
	 * @param goalId 目标ID
	 * @return 目标信息
	 */
	@Override
	public Goal selectGoalById(Long goalId) {
		return goalMapper.selectGoalById(goalId);
	}

	/**
	 * 新增目标
	 * @param goal 目标信息
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int insertGoal(Goal goal) {
		// 设置默认状态为草稿
		if (goal.getStatus() == null || goal.getStatus().isEmpty()) {
			goal.setStatus(GoalStatus.DRAFT.name());
		}
		// 设置默认进度为0
		if (goal.getProgress() == null) {
			goal.setProgress(0);
		}
		return goalMapper.insertGoal(goal);
	}

	/**
	 * 更新目标
	 * @param goal 目标信息
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int updateGoal(Goal goal) {
		Goal existGoal = goalMapper.selectGoalById(goal.getGoalId());
		if (existGoal == null) {
			throw new TWTException("目标不存在");
		}
		
		// 已归档的目标不允许修改
		if (GoalStatus.ARCHIVED.name().equals(existGoal.getStatus())) {
			throw new TWTException("已归档的目标不允许修改");
		}
		
		// 不允许手动修改状态、进度、得分、实际开始时间、实际结束时间
		goal.setStatus(null);
		goal.setProgress(null);
		goal.setScore(null);
		goal.setActualStartTime(null);
		goal.setActualEndTime(null);
		
		return goalMapper.updateGoal(goal);
	}

	/**
	 * 删除目标
	 * @param goalIds 需要删除的目标ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int deleteGoalByIds(Long[] goalIds) {
		// 删除目标时，关联的任务会通过数据库外键级联删除
		return goalMapper.deleteGoalByIds(goalIds);
	}

	/**
	 * 开始目标
	 * @param goalId 目标ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int startGoal(Long goalId) {
		Goal goal = goalMapper.selectGoalById(goalId);
		if (goal == null) {
			throw new TWTException("目标不存在");
		}
		
		// 只有未开始状态的目标才能开始
		if (!GoalStatus.NOT_STARTED.name().equals(goal.getStatus())) {
			throw new TWTException("只有未开始状态的目标才能开始");
		}
		
		// 更新状态为进行中，记录实际开始时间
		goal.setStatus(GoalStatus.IN_PROGRESS.name());
		goal.setActualStartTime(LocalDateTime.now());
		goalMapper.updateGoal(goal);
		
		return goalMapper.updateGoalStatus(goalId, GoalStatus.IN_PROGRESS.name());
	}

	/**
	 * 完成目标
	 * @param goalId 目标ID
	 * @param summary 目标总结
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int completeGoal(Long goalId, String summary) {
		Goal goal = goalMapper.selectGoalById(goalId);
		if (goal == null) {
			throw new TWTException("目标不存在");
		}
		
		// 只有进行中状态的目标才能完成
		if (!GoalStatus.IN_PROGRESS.name().equals(goal.getStatus())) {
			throw new TWTException("只有进行中状态的目标才能完成");
		}
		
		// 检查是否所有任务都已完成
		int totalTasks = taskMapper.countTasksByGoalId(goalId);
		int completedTasks = taskMapper.countCompletedTasksByGoalId(goalId);
		
		if (totalTasks > 0 && completedTasks < totalTasks) {
			throw new TWTException("还有未完成的任务，无法完成目标");
		}
		
		// 更新状态为已完成，记录实际结束时间和总结
		goal.setStatus(GoalStatus.COMPLETED.name());
		goal.setActualEndTime(LocalDateTime.now());
		goal.setSummary(summary);
		goalMapper.updateGoal(goal);
		
		// 计算目标得分
		calculateGoalScore(goalId);
		
		return 1;
	}

	/**
	 * 归档目标
	 * @param goalId 目标ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int archiveGoal(Long goalId) {
		Goal goal = goalMapper.selectGoalById(goalId);
		if (goal == null) {
			throw new TWTException("目标不存在");
		}
		
		// 只有已完成或未达成状态的目标才能归档
		if (!GoalStatus.COMPLETED.name().equals(goal.getStatus()) 
				&& !GoalStatus.NOT_ACHIEVED.name().equals(goal.getStatus())) {
			throw new TWTException("只有已完成或未达成状态的目标才能归档");
		}
		
		return goalMapper.updateGoalStatus(goalId, GoalStatus.ARCHIVED.name());
	}

	/**
	 * 计算目标进度
	 * @param goalId 目标ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int calculateGoalProgress(Long goalId) {
		int totalTasks = taskMapper.countTasksByGoalId(goalId);
		int completedTasks = taskMapper.countCompletedTasksByGoalId(goalId);
		
		int progress = 0;
		if (totalTasks > 0) {
			progress = (int) Math.round((double) completedTasks / totalTasks * 100);
		}
		
		return goalMapper.updateGoalProgress(goalId, progress);
	}

	/**
	 * 计算目标得分
	 * @param goalId 目标ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int calculateGoalScore(Long goalId) {
		Goal goal = goalMapper.selectGoalById(goalId);
		if (goal == null) {
			throw new TWTException("目标不存在");
		}
		
		// 获取目标下的所有任务
		List<Task> tasks = taskMapper.selectTasksByGoalId(goalId);
		if (tasks == null || tasks.isEmpty()) {
			return 0;
		}
		
		// 计算任务质量得分（70%）
		int totalScore = 0;
		int taskCount = 0;
		for (Task task : tasks) {
			if (task.getScore() != null && !task.getScore().isEmpty()) {
				totalScore += TaskScore.getScoreByCode(task.getScore());
				taskCount++;
			}
		}
		
		double qualityScore = 0;
		if (taskCount > 0) {
			qualityScore = (double) totalScore / taskCount;
		}
		
		// 计算时间完成得分（30%）
		double timeScore = calculateTimeScore(goal);
		
		// 计算最终得分
		double finalScore = qualityScore * 0.7 + timeScore * 0.3;
		BigDecimal score = BigDecimal.valueOf(finalScore).setScale(2, RoundingMode.HALF_UP);
		
		return goalMapper.updateGoalScore(goalId, score);
	}

	/**
	 * 计算时间完成得分
	 * @param goal 目标信息
	 * @return 时间得分
	 */
	private double calculateTimeScore(Goal goal) {
		if (goal.getExpectedEndTime() == null || goal.getActualEndTime() == null) {
			return 90; // 默认按时完成
		}
		
		LocalDateTime expectedEnd = goal.getExpectedEndTime();
		LocalDateTime actualEnd = goal.getActualEndTime();
		
		// 提前完成
		if (actualEnd.isBefore(expectedEnd)) {
			return 100;
		}
		
		// 按时完成
		if (actualEnd.isEqual(expectedEnd) || actualEnd.isBefore(expectedEnd.plusDays(1))) {
			return 90;
		}
		
		// 计算逾期天数
		long overdueDays = ChronoUnit.DAYS.between(expectedEnd, actualEnd);
		
		if (overdueDays <= 3) {
			return 80;
		} else if (overdueDays <= 7) {
			return 70;
		} else if (overdueDays <= 14) {
			return 60;
		} else {
			return 50;
		}
	}

}
