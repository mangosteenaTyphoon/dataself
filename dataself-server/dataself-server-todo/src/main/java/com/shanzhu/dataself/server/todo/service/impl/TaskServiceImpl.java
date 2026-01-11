package com.shanzhu.dataself.server.todo.service.impl;

import com.shanzhu.dataself.api.todo.domain.Goal;
import com.shanzhu.dataself.api.todo.domain.Task;
import com.shanzhu.dataself.api.todo.enums.GoalStatus;
import com.shanzhu.dataself.api.todo.enums.TaskStatus;
import com.shanzhu.dataself.framework.core.exception.TWTException;
import com.shanzhu.dataself.framework.datascope.annotation.MicroDataScope;
import com.shanzhu.dataself.server.todo.mapper.GoalMapper;
import com.shanzhu.dataself.server.todo.mapper.TaskMapper;
import com.shanzhu.dataself.server.todo.service.IGoalService;
import com.shanzhu.dataself.server.todo.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务管理Service实现
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
@Service
public class TaskServiceImpl implements ITaskService {

	private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private GoalMapper goalMapper;

	@Autowired
	private IGoalService goalService;

	/**
	 * 查询任务列表
	 * @param task 任务信息
	 * @return 任务集合
	 */
	@Override
	@MicroDataScope(userIdField = "user_id")
	public List<Task> selectTaskList(Task task) {
		return taskMapper.selectTaskList(task);
	}

	/**
	 * 查询任务详情
	 * @param taskId 任务ID
	 * @return 任务信息
	 */
	@Override
	public Task selectTaskById(Long taskId) {
		return taskMapper.selectTaskById(taskId);
	}

	/**
	 * 查询目标下的任务
	 * @param goalId 目标ID
	 * @return 任务集合
	 */
	@Override
	public List<Task> selectTasksByGoalId(Long goalId) {
		return taskMapper.selectTasksByGoalId(goalId);
	}

	/**
	 * 新增任务
	 * @param task 任务信息
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int insertTask(Task task) {
		// 检查目标是否存在
		Goal goal = goalMapper.selectGoalById(task.getGoalId());
		if (goal == null) {
			throw new TWTException("目标不存在");
		}
		
		// 设置默认状态为未开始
		if (task.getStatus() == null || task.getStatus().isEmpty()) {
			task.setStatus(TaskStatus.NOT_STARTED.name());
		}
		
		int result = taskMapper.insertTask(task);
		
		// 如果目标是已完成状态，添加任务后需要重新激活目标
		if (GoalStatus.COMPLETED.name().equals(goal.getStatus())) {
			goal.setStatus(GoalStatus.IN_PROGRESS.name());
			goal.setActualEndTime(null);
			goal.setScore(null);
			goalMapper.updateGoal(goal);
		}
		
		// 重新计算目标进度
		goalService.calculateGoalProgress(task.getGoalId());
		
		return result;
	}

	/**
	 * 更新任务
	 * @param task 任务信息
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int updateTask(Task task) {
		Task existTask = taskMapper.selectTaskById(task.getTaskId());
		if (existTask == null) {
			throw new TWTException("任务不存在");
		}
		
		// 不允许手动修改状态、实际开始时间、实际结束时间
		task.setStatus(null);
		task.setActualStartTime(null);
		task.setActualEndTime(null);
		
		return taskMapper.updateTask(task);
	}

	/**
	 * 删除任务
	 * @param taskIds 需要删除的任务ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int deleteTaskByIds(Long[] taskIds) {
		if (taskIds == null || taskIds.length == 0) {
			return 0;
		}
		
		// 获取第一个任务的目标ID用于重新计算进度
		Task task = taskMapper.selectTaskById(taskIds[0]);
		Long goalId = task != null ? task.getGoalId() : null;
		
		int result = taskMapper.deleteTaskByIds(taskIds);
		
		// 重新计算目标进度
		if (goalId != null) {
			goalService.calculateGoalProgress(goalId);
		}
		
		return result;
	}

	/**
	 * 开始任务
	 * @param taskId 任务ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int startTask(Long taskId) {
		Task task = taskMapper.selectTaskById(taskId);
		if (task == null) {
			throw new TWTException("任务不存在");
		}
		
		// 只有未开始状态的任务才能开始
		if (!TaskStatus.NOT_STARTED.name().equals(task.getStatus())) {
			throw new TWTException("只有未开始状态的任务才能开始");
		}
		
		// 更新状态为进行中，记录实际开始时间
		task.setStatus(TaskStatus.IN_PROGRESS.name());
		task.setActualStartTime(LocalDateTime.now());
		taskMapper.updateTask(task);
		
		return taskMapper.updateTaskStatus(taskId, TaskStatus.IN_PROGRESS.name());
	}

	/**
	 * 完成任务
	 * @param taskId 任务ID
	 * @param score 任务评分
	 * @param summary 任务总结
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int completeTask(Long taskId, String score, String summary) {
		Task task = taskMapper.selectTaskById(taskId);
		if (task == null) {
			throw new TWTException("任务不存在");
		}
		
		// 只有进行中状态的任务才能完成
		if (!TaskStatus.IN_PROGRESS.name().equals(task.getStatus())) {
			throw new TWTException("只有进行中状态的任务才能完成");
		}
		
		// 更新状态为已完成，记录实际结束时间、评分和总结
		task.setStatus(TaskStatus.COMPLETED.name());
		task.setActualEndTime(LocalDateTime.now());
		task.setScore(score);
		task.setSummary(summary);
		taskMapper.updateTask(task);
		
		// 重新计算目标进度
		goalService.calculateGoalProgress(task.getGoalId());
		
		// 检查是否所有任务都已完成
		int totalTasks = taskMapper.countTasksByGoalId(task.getGoalId());
		int completedTasks = taskMapper.countCompletedTasksByGoalId(task.getGoalId());
		
		// 如果所有任务都已完成，自动完成目标
		if (totalTasks > 0 && completedTasks == totalTasks) {
			Goal goal = goalMapper.selectGoalById(task.getGoalId());
			if (goal != null && GoalStatus.IN_PROGRESS.name().equals(goal.getStatus())) {
				goal.setStatus(GoalStatus.COMPLETED.name());
				goal.setActualEndTime(LocalDateTime.now());
				goalMapper.updateGoal(goal);
				
				// 计算目标得分
				goalService.calculateGoalScore(task.getGoalId());
			}
		}
		
		return 1;
	}

	/**
	 * 标记未达成
	 * @param taskId 任务ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int markNotAchieved(Long taskId) {
		Task task = taskMapper.selectTaskById(taskId);
		if (task == null) {
			throw new TWTException("任务不存在");
		}
		
		// 只有进行中状态的任务才能标记为未达成
		if (!TaskStatus.IN_PROGRESS.name().equals(task.getStatus())) {
			throw new TWTException("只有进行中状态的任务才能标记为未达成");
		}
		
		// 更新状态为未达成
		task.setStatus(TaskStatus.NOT_ACHIEVED.name());
		task.setActualEndTime(LocalDateTime.now());
		taskMapper.updateTask(task);
		
		return taskMapper.updateTaskStatus(taskId, TaskStatus.NOT_ACHIEVED.name());
	}

}
