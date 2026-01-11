package com.shanzhu.dataself.api.todo.domain;

import com.shanzhu.dataself.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 任务实体类
 *
 * @author shanzhu
 */
@Schema(description = "任务实体")
public class Task extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Long taskId;

    /**
     * 目标ID
     */
    @Schema(description = "目标ID")
    private Long goalId;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 任务标题
     */
    @Schema(description = "任务标题")
    private String title;

    /**
     * 任务描述
     */
    @Schema(description = "任务描述")
    private String description;

    /**
     * 任务状态：NOT_STARTED-未开始，IN_PROGRESS-进行中，COMPLETED-已完成，NOT_ACHIEVED-未达成
     */
    @Schema(description = "任务状态")
    private String status;

    /**
     * 优先级：HIGH-高，MEDIUM-中，LOW-低
     */
    @Schema(description = "优先级")
    private String priority;

    /**
     * 任务评分：EXCELLENT-优秀，GOOD-良，QUALIFIED-合格，POOR-差
     */
    @Schema(description = "任务评分")
    private String score;

    /**
     * 预期开始时间
     */
    @Schema(description = "预期开始时间")
    private LocalDateTime expectedStartTime;

    /**
     * 预期结束时间
     */
    @Schema(description = "预期结束时间")
    private LocalDateTime expectedEndTime;

    /**
     * 实际开始时间
     */
    @Schema(description = "实际开始时间")
    private LocalDateTime actualStartTime;

    /**
     * 实际结束时间
     */
    @Schema(description = "实际结束时间")
    private LocalDateTime actualEndTime;

    /**
     * 任务总结
     */
    @Schema(description = "任务总结")
    private String summary;

    /**
     * 排序顺序
     */
    @Schema(description = "排序顺序")
    private Integer sortOrder;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @Schema(description = "删除标志")
    private String delFlag;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public LocalDateTime getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(LocalDateTime expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public LocalDateTime getExpectedEndTime() {
        return expectedEndTime;
    }

    public void setExpectedEndTime(LocalDateTime expectedEndTime) {
        this.expectedEndTime = expectedEndTime;
    }

    public LocalDateTime getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(LocalDateTime actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public LocalDateTime getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(LocalDateTime actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("taskId", getTaskId())
                .append("goalId", getGoalId())
                .append("userId", getUserId())
                .append("title", getTitle())
                .append("description", getDescription())
                .append("status", getStatus())
                .append("priority", getPriority())
                .append("score", getScore())
                .append("expectedStartTime", getExpectedStartTime())
                .append("expectedEndTime", getExpectedEndTime())
                .append("actualStartTime", getActualStartTime())
                .append("actualEndTime", getActualEndTime())
                .append("summary", getSummary())
                .append("sortOrder", getSortOrder())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }

}
