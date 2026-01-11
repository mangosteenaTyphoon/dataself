package com.shanzhu.dataself.api.todo.enums;

/**
 * 优先级枚举
 *
 * @author shanzhu
 */
public enum Priority {

    /**
     * 高
     */
    HIGH("HIGH", "高"),

    /**
     * 中
     */
    MEDIUM("MEDIUM", "中"),

    /**
     * 低
     */
    LOW("LOW", "低");

    private final String code;
    private final String desc;

    Priority(String code, String desc) {
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
