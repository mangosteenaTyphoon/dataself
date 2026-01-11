package com.shanzhu.dataself.server.todo.service;

import com.shanzhu.dataself.api.todo.domain.Task;

import java.util.List;

/**
 * 任务管理Service接口
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
public interface ITaskService {

	/**
	 * 查询任务列表
	 * @param task 任务信息
	 * @return 任务集合
	 */
	List<Task> selectTaskList(Task task);

	/**
	 * 查询任务详情
	 * @param taskId 任务ID
	 * @return 任务信息
	 */
	Task selectTaskById(Long taskId);

	/**
	 * 查询目标下的任务
	 * @param goalId 目标ID
	 * @return 任务集合
	 */
	List<Task> selectTasksByGoalId(Long goalId);

	/**
	 * 新增任务
	 * @param task 任务信息
	 * @return 结果
	 */
	int insertTask(Task task);

	/**
	 * 更新任务
	 * @param task 任务信息
	 * @return 结果
	 */
	int updateTask(Task task);

	/**
	 * 删除任务
	 * @param taskIds 需要删除的任务ID
	 * @return 结果
	 */
	int deleteTaskByIds(Long[] taskIds);

	/**
	 * 开始任务
	 * @param taskId 任务ID
	 * @return 结果
	 */
	int startTask(Long taskId);

	/**
	 * 完成任务
	 * @param taskId 任务ID
	 * @param score 任务评分
	 * @param summary 任务总结
	 * @return 结果
	 */
	int completeTask(Long taskId, String score, String summary);

	/**
	 * 标记未达成
	 * @param taskId 任务ID
	 * @return 结果
	 */
	int markNotAchieved(Long taskId);

}
