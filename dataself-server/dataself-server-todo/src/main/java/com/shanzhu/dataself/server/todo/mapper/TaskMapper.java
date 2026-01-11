package com.shanzhu.dataself.server.todo.mapper;

import com.shanzhu.dataself.api.todo.domain.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务管理Mapper接口
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
public interface TaskMapper {

	/**
	 * 分页查询任务列表
	 * @param task 任务信息
	 * @return 任务集合
	 */
	List<Task> selectTaskList(Task task);

	/**
	 * 根据ID查询任务
	 * @param taskId 任务ID
	 * @return 任务信息
	 */
	Task selectTaskById(Long taskId);

	/**
	 * 根据目标ID查询任务列表
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
	 * 批量删除任务
	 * @param taskIds 需要删除的任务ID
	 * @return 结果
	 */
	int deleteTaskByIds(Long[] taskIds);

	/**
	 * 更新任务状态
	 * @param taskId 任务ID
	 * @param status 状态值
	 * @return 结果
	 */
	int updateTaskStatus(@Param("taskId") Long taskId, @Param("status") String status);

	/**
	 * 统计目标下的任务数
	 * @param goalId 目标ID
	 * @return 任务数量
	 */
	int countTasksByGoalId(Long goalId);

	/**
	 * 统计目标下已完成的任务数
	 * @param goalId 目标ID
	 * @return 已完成任务数量
	 */
	int countCompletedTasksByGoalId(Long goalId);

	/**
	 * 统计用户的任务总数
	 * @param userId 用户ID
	 * @return 任务总数
	 */
	int countTasksByUserId(Long userId);

	/**
	 * 统计用户指定状态的任务数
	 * @param userId 用户ID
	 * @param status 状态
	 * @return 任务数量
	 */
	int countTasksByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);

}
