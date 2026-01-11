package com.shanzhu.dataself.server.todo.mapper;

import com.shanzhu.dataself.api.todo.domain.Goal;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 目标管理Mapper接口
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
public interface GoalMapper {

	/**
	 * 分页查询目标列表
	 * @param goal 目标信息
	 * @return 目标集合
	 */
	List<Goal> selectGoalList(Goal goal);

	/**
	 * 根据ID查询目标
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
	 * 批量删除目标
	 * @param goalIds 需要删除的目标ID
	 * @return 结果
	 */
	int deleteGoalByIds(Long[] goalIds);

	/**
	 * 更新目标进度
	 * @param goalId 目标ID
	 * @param progress 进度值
	 * @return 结果
	 */
	int updateGoalProgress(@Param("goalId") Long goalId, @Param("progress") Integer progress);

	/**
	 * 更新目标得分
	 * @param goalId 目标ID
	 * @param score 得分值
	 * @return 结果
	 */
	int updateGoalScore(@Param("goalId") Long goalId, @Param("score") BigDecimal score);

	/**
	 * 更新目标状态
	 * @param goalId 目标ID
	 * @param status 状态值
	 * @return 结果
	 */
	int updateGoalStatus(@Param("goalId") Long goalId, @Param("status") String status);

	/**
	 * 统计用户的目标总数
	 * @param userId 用户ID
	 * @return 目标总数
	 */
	int countGoalsByUserId(Long userId);

	/**
	 * 统计用户指定状态的目标数
	 * @param userId 用户ID
	 * @param status 状态
	 * @return 目标数量
	 */
	int countGoalsByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);

	/**
	 * 计算用户的平均目标得分
	 * @param userId 用户ID
	 * @return 平均得分
	 */
	BigDecimal getAverageScoreByUserId(Long userId);

}
