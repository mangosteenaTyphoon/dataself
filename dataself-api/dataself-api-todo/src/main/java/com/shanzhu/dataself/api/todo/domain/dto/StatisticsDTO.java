package com.shanzhu.dataself.api.todo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 统计数据DTO
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2026-01-09
 */
@Schema(description = "统计数据DTO")
public class StatisticsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "总数")
	private Integer total;

	@Schema(description = "草稿数")
	private Integer draftCount;

	@Schema(description = "未开始数")
	private Integer notStartedCount;

	@Schema(description = "进行中数")
	private Integer inProgressCount;

	@Schema(description = "已完成数")
	private Integer completedCount;

	@Schema(description = "未达成数")
	private Integer notAchievedCount;

	@Schema(description = "已归档数")
	private Integer archivedCount;

	@Schema(description = "完成率")
	private BigDecimal completionRate;

	@Schema(description = "平均得分")
	private BigDecimal averageScore;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getDraftCount() {
		return draftCount;
	}

	public void setDraftCount(Integer draftCount) {
		this.draftCount = draftCount;
	}

	public Integer getNotStartedCount() {
		return notStartedCount;
	}

	public void setNotStartedCount(Integer notStartedCount) {
		this.notStartedCount = notStartedCount;
	}

	public Integer getInProgressCount() {
		return inProgressCount;
	}

	public void setInProgressCount(Integer inProgressCount) {
		this.inProgressCount = inProgressCount;
	}

	public Integer getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(Integer completedCount) {
		this.completedCount = completedCount;
	}

	public Integer getNotAchievedCount() {
		return notAchievedCount;
	}

	public void setNotAchievedCount(Integer notAchievedCount) {
		this.notAchievedCount = notAchievedCount;
	}

	public Integer getArchivedCount() {
		return archivedCount;
	}

	public void setArchivedCount(Integer archivedCount) {
		this.archivedCount = archivedCount;
	}

	public BigDecimal getCompletionRate() {
		return completionRate;
	}

	public void setCompletionRate(BigDecimal completionRate) {
		this.completionRate = completionRate;
	}

	public BigDecimal getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(BigDecimal averageScore) {
		this.averageScore = averageScore;
	}

	@Override
	public String toString() {
		return "StatisticsDTO{" + "total=" + total + ", draftCount=" + draftCount + ", notStartedCount="
				+ notStartedCount + ", inProgressCount=" + inProgressCount + ", completedCount=" + completedCount
				+ ", notAchievedCount=" + notAchievedCount + ", archivedCount=" + archivedCount + ", completionRate="
				+ completionRate + ", averageScore=" + averageScore + '}';
	}

}
