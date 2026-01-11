-- ----------------------------
-- 任务表
-- ----------------------------
DROP TABLE IF EXISTS `todo_task`;
CREATE TABLE `todo_task` (
  `task_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `goal_id` BIGINT(20) NOT NULL COMMENT '目标ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `title` VARCHAR(200) NOT NULL COMMENT '任务标题',
  `description` TEXT COMMENT '任务描述',
  `status` VARCHAR(20) NOT NULL DEFAULT 'NOT_STARTED' COMMENT '任务状态：NOT_STARTED-未开始，IN_PROGRESS-进行中，COMPLETED-已完成，NOT_ACHIEVED-未达成',
  `priority` VARCHAR(20) DEFAULT 'MEDIUM' COMMENT '优先级：HIGH-高，MEDIUM-中，LOW-低',
  `score` VARCHAR(20) DEFAULT NULL COMMENT '任务评分：EXCELLENT-优秀，GOOD-良，QUALIFIED-合格，POOR-差',
  `expected_start_time` DATETIME DEFAULT NULL COMMENT '预期开始时间',
  `expected_end_time` DATETIME DEFAULT NULL COMMENT '预期结束时间',
  `actual_start_time` DATETIME DEFAULT NULL COMMENT '实际开始时间',
  `actual_end_time` DATETIME DEFAULT NULL COMMENT '实际结束时间',
  `summary` TEXT COMMENT '任务总结',
  `sort_order` INT(11) DEFAULT 0 COMMENT '排序顺序',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`task_id`),
  KEY `idx_goal_id` (`goal_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_del_flag` (`del_flag`),
  CONSTRAINT `fk_task_goal` FOREIGN KEY (`goal_id`) REFERENCES `todo_goal` (`goal_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='任务表';
