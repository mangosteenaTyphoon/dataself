package com.shanzhu.dataself.server.todo.service;

import com.shanzhu.dataself.api.todo.domain.Goal;

import java.util.List;

/**
 * 目标管理Service接口
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
public interface IGoalService {

	/**
	 * 查询目标列表
	 * @param goal 目标信息
	 * @return 目标集合
	 */
	List<Goal> selectGoalList(Goal goal);

	/**
	 * 查询目标详情
	 * @param goalId 目标ID
	 * @return 目标信息
	 */
	Goal selectGoalById(Long goalId);

	/**
	 * 新增目标
	 * @param goal 目标信息
	 * @return 结果
	 */
	int insertGoal(Goal goal);

	/**
	 * 更新目标
	 * @param goal 目标信息
	 * @return 结果
	 */
	int updateGoal(Goal goal);

	/**
	 * 删除目标
	 * @param goalIds 需要删除的目标ID
	 * @return 结果
	 */
	int deleteGoalByIds(Long[] goalIds);

	/**
	 * 开始目标
	 * @param goalId 目标ID
	 * @return 结果
	 */
	int startGoal(Long goalId);

	/**
	 * 完成目标
	 * @param goalId 目标ID
	 * @param summary 目标总结
	 * @return 结果
	 */
	int completeGoal(Long goalId, String summary);

	/**
	 * 归档目标
	 * @param goalId 目标ID
	 * @return 结果
	 */
	int archiveGoal(Long goalId);

	/**
	 * 计算目标进度
	 * @param goalId 目标ID
	 * @return 结果
	 */
	int calculateGoalProgress(Long goalId);

	/**
	 * 计算目标得分
	 * @param goalId 目标ID
	 * @return 结果
	 */
	int calculateGoalScore(Long goalId);

}
