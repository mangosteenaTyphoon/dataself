-- ----------------------------
-- OKR-Todo 菜单权限 SQL
-- ----------------------------

-- 菜单 ID 说明：
-- 2000: OKR-Todo 一级菜单
-- 2001: 目标管理
-- 2002: 任务管理  
-- 2003: 数据统计

-- ----------------------------
-- 1. OKR-Todo 一级菜单
-- ----------------------------
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2000, 'OKR-Todo', 0, 5, 'todo', NULL, '1', 'M', '0', '0', NULL, 'list', 'admin', sysdate(), '', NULL, 'OKR目标与任务管理');

-- ----------------------------
-- 2. 目标管理菜单
-- ----------------------------
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2001, '目标管理', 2000, 1, 'goal', 'todo/goal/index', '1', 'C', '0', '0', 'todo:goal:list', 'target', 'admin', sysdate(), '', NULL, '目标管理菜单');

-- 目标管理按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2011, '目标查询', 2001, 1, '#', '', '1', 'F', '0', '0', 'todo:goal:query', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2012, '目标新增', 2001, 2, '#', '', '1', 'F', '0', '0', 'todo:goal:add', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2013, '目标修改', 2001, 3, '#', '', '1', 'F', '0', '0', 'todo:goal:edit', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2014, '目标删除', 2001, 4, '#', '', '1', 'F', '0', '0', 'todo:goal:remove', '#', 'admin', sysdate(), '', NULL, '');

-- ----------------------------
-- 3. 任务管理菜单
-- ----------------------------
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2002, '任务管理', 2000, 2, 'task', 'todo/task/index', '1', 'C', '0', '0', 'todo:task:list', 'checkbox', 'admin', sysdate(), '', NULL, '任务管理菜单');

-- 任务管理按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2021, '任务查询', 2002, 1, '#', '', '1', 'F', '0', '0', 'todo:task:query', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2022, '任务新增', 2002, 2, '#', '', '1', 'F', '0', '0', 'todo:task:add', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2023, '任务修改', 2002, 3, '#', '', '1', 'F', '0', '0', 'todo:task:edit', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2024, '任务删除', 2002, 4, '#', '', '1', 'F', '0', '0', 'todo:task:remove', '#', 'admin', sysdate(), '', NULL, '');

-- ----------------------------
-- 4. 数据统计菜单
-- ----------------------------
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2003, '数据统计', 2000, 3, 'statistics', 'todo/statistics/index', '1', 'C', '0', '0', 'todo:statistics:query', 'chart', 'admin', sysdate(), '', NULL, '数据统计菜单');

-- 数据统计按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2031, '统计查询', 2003, 1, '#', '', '1', 'F', '0', '0', 'todo:statistics:query', '#', 'admin', sysdate(), '', NULL, '');
