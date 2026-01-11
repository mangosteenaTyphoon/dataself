package com.shanzhu.dataself.api.todo.enums;

/**
 * 目标状态枚举
 *
 * @author shanzhu
 */
public enum GoalStatus {

    /**
     * 草稿
     */
    DRAFT("DRAFT", "草稿"),

    /**
     * 未开始
     */
    NOT_STARTED("NOT_STARTED", "未开始"),

    /**
     * 进行中
     */
    IN_PROGRESS("IN_PROGRESS", "进行中"),

    /**
     * 已完成
     */
    COMPLETED("COMPLETED", "已完成"),

    /**
     * 未达成
     */
    NOT_ACHIEVED("NOT_ACHIEVED", "未达成"),

    /**
     * 已归档
     */
    ARCHIVED("ARCHIVED", "已归档");

    private final String code;
    private final String desc;

    GoalStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
