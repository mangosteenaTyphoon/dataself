package com.shanzhu.dataself.api.todo.enums;

/**
 * 任务评分枚举
 *
 * @author shanzhu
 */
public enum TaskScore {

    /**
     * 优秀 - 95分
     */
    EXCELLENT("EXCELLENT", "优秀", 95),

    /**
     * 良 - 85分
     */
    GOOD("GOOD", "良", 85),

    /**
     * 合格 - 75分
     */
    QUALIFIED("QUALIFIED", "合格", 75),

    /**
     * 差 - 60分
     */
    POOR("POOR", "差", 60);

    private final String code;
    private final String desc;
    private final int score;

    TaskScore(String code, String desc, int score) {
        this.code = code;
        this.desc = desc;
        this.score = score;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public int getScore() {
        return score;
    }

    /**
     * 根据code获取分数
     */
    public static int getScoreByCode(String code) {
        for (TaskScore taskScore : values()) {
            if (taskScore.getCode().equals(code)) {
                return taskScore.getScore();
            }
        }
        return 0;
    }

}
