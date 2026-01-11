# Skills 微服务模块代码生成规范

> **文档版本**: v1.0  
> **创建日期**: 2026-01-09  
> **参考模块**: dataself-server-system  
> **目标**: 为 AI 代码生成提供统一的编码规范和流程指导

---

## 📋 目录

1. [项目概述](#项目概述)
2. [模块结构规范](#模块结构规范)
3. [代码分层架构](#代码分层架构)
4. [命名规范](#命名规范)
5. [代码编写规范](#代码编写规范)
6. [数据库设计规范](#数据库设计规范)
7. [API 接口规范](#api-接口规范)
8. [代码生成流程](#代码生成流程)
9. [注意事项](#注意事项)

---

## 项目概述

### 业务背景
Skills 模块是一个 **OKR 目标与任务管理系统**,用于帮助用户进行目标设定、任务分解、进度跟踪和复盘总结。

### 核心实体
1. **Goal (目标)**: 用户设定的宏观目标
2. **Task (任务)**: 为达成目标而需要完成的具体行动

### 技术栈
- **框架**: Spring Boot 3.x + Spring Cloud Alibaba
- **持久层**: MyBatis + MySQL
- **注册中心**: Nacos
- **熔断限流**: Sentinel
- **API 文档**: Swagger 3 (OpenAPI)
- **安全认证**: Spring Security + OAuth2

---

## 模块结构规范

### 1. API 模块结构

```
dataself-api/
└── dataself-api-skills/
    ├── pom.xml
    └── src/main/java/com/shanzhu/dataself/api/skills/
        ├── domain/          # 实体类
        │   ├── Goal.java
        │   └── Task.java
        ├── feign/           # Feign 客户端接口(如需要)
        └── model/           # DTO/VO 模型(如需要)
```

### 2. 微服务模块结构

```
dataself-server/
└── dataself-server-skills/
    ├── pom.xml
    ├── Jenkinsfile-k8s  # CI/CD 配置
    └── src/
        ├── main/
        │   ├── java/com/shanzhu/dataself/server/skills/
        │   │   ├── SkillsApplication.java        # 启动类
        │   │   ├── controller/                   # 控制器层
        │   │   │   ├── GoalController.java
        │   │   │   ├── TaskController.java
        │   │   │   └── api/                      # 内部 API
        │   │   │       ├── GoalApi.java
        │   │   │       └── TaskApi.java
        │   │   ├── service/                      # 服务接口层
        │   │   │   ├── IGoalService.java
        │   │   │   ├── ITaskService.java
        │   │   │   └── impl/                     # 服务实现层
        │   │   │       ├── GoalServiceImpl.java
        │   │   │       └── TaskServiceImpl.java
        │   │   ├── mapper/                       # Mapper 接口层
        │   │   │   ├── GoalMapper.java
        │   │   │   └── TaskMapper.java
        │   │   └── utils/                        # 工具类(如需要)
        │   └── resources/
        │       ├── application.yml               # 应用配置
        │       ├── bootstrap.yml                 # 启动配置(如需要)
        │       └── mapper/skills/                # MyBatis XML
        │           ├── GoalMapper.xml
        │           └── TaskMapper.xml
        └── test/                                 # 测试代码
```

---

## 代码分层架构

### 分层说明

```
Controller (控制器层)
    ↓
Service Interface (服务接口层)
    ↓
Service Impl (服务实现层)
    ↓
Mapper Interface (数据访问接口层)
    ↓
Mapper XML (SQL 映射层)
    ↓
Database (数据库)
```

### 各层职责

| 层级 | 职责 | 示例 |
|------|------|------|
| **Controller** | 接收请求、参数校验、调用 Service、返回响应 | `GoalController.java` |
| **Service Interface** | 定义业务接口 | `IGoalService.java` |
| **Service Impl** | 实现业务逻辑、事务控制 | `GoalServiceImpl.java` |
| **Mapper Interface** | 定义数据访问方法 | `GoalMapper.java` |
| **Mapper XML** | 编写 SQL 语句 | `GoalMapper.xml` |
| **Domain** | 实体类定义 | `Goal.java` |

---

## 命名规范

### 1. 包命名规范

```java
// 基础包路径
com.shanzhu.dataself

// API 模块
com.shanzhu.dataself.api.skills.domain
com.shanzhu.dataself.api.skills.feign
com.shanzhu.dataself.api.skills.model

// 微服务模块
com.shanzhu.dataself.server.skills
com.shanzhu.dataself.server.skills.controller
com.shanzhu.dataself.server.skills.controller.api
com.shanzhu.dataself.server.skills.service
com.shanzhu.dataself.server.skills.service.impl
com.shanzhu.dataself.server.skills.mapper
com.shanzhu.dataself.server.skills.utils
```

### 2. 类命名规范

| 类型 | 命名规则 | 示例 |
|------|----------|------|
| **实体类** | 名词,首字母大写 | `Goal.java`, `Task.java` |
| **Controller** | 实体名 + Controller | `GoalController.java` |
| **Service 接口** | I + 实体名 + Service | `IGoalService.java` |
| **Service 实现** | 实体名 + ServiceImpl | `GoalServiceImpl.java` |
| **Mapper 接口** | 实体名 + Mapper | `GoalMapper.java` |
| **Mapper XML** | 实体名 + Mapper.xml | `GoalMapper.xml` |
| **API 接口** | 实体名 + Api | `GoalApi.java` |

### 3. 方法命名规范

| 操作类型 | 前缀 | 示例 |
|----------|------|------|
| **查询单个** | select...By... | `selectGoalById(Long goalId)` |
| **查询列表** | select...List | `selectGoalList(Goal goal)` |
| **新增** | insert... | `insertGoal(Goal goal)` |
| **修改** | update... | `updateGoal(Goal goal)` |
| **删除** | delete...By... | `deleteGoalById(Long goalId)` |
| **批量删除** | delete...ByIds | `deleteGoalByIds(Long[] goalIds)` |
| **校验** | check... | `checkGoalAllowed(Goal goal)` |

### 4. 变量命名规范

```java
// 实体对象: 小驼峰
Goal goal;
Task task;

// 列表: 实体名 + List
List<Goal> goalList;
List<Task> taskList;

// ID 数组: 实体名 + Ids
Long[] goalIds;
Long[] taskIds;

// Mapper: 实体名 + Mapper (小驼峰)
private GoalMapper goalMapper;
private TaskMapper taskMapper;

// Service: i + 实体名 + Service (小驼峰)
private IGoalService iGoalService;
private ITaskService iTaskService;
```

---

## 代码编写规范

### 1. 实体类 (Domain)

**位置**: `dataself-api-skills/src/main/java/com/shanzhu/dataself/api/skills/domain/`

**规范**:
```java
package com.shanzhu.dataself.api.skills.domain;

import com.shanzhu.dataself.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.util.Date;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 目标实体
 */
@Schema(description = "目标实体")
public class Goal extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 目标ID
     */
    @Schema(description = "目标ID")
    private Long goalId;

    /**
     * 目标标题
     */
    @Schema(description = "目标标题")
    @NotBlank(message = "目标标题不能为空")
    @Size(max = 200, message = "目标标题长度不能超过200个字符")
    private String title;

    // ... 其他字段

    // Getter 和 Setter 方法
    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    // ... 其他 Getter/Setter

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("goalId", getGoalId())
                .append("title", getTitle())
                // ... 其他字段
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
```

**关键点**:
1. ✅ 继承 `BaseEntity` (包含 createBy, createTime, updateBy, updateTime, remark 等公共字段)
2. ✅ 使用 `@Schema` 注解标注类和字段说明
3. ✅ 使用 Jakarta Validation 注解进行参数校验 (`@NotBlank`, `@Size` 等)
4. ✅ 实现 `serialVersionUID`
5. ✅ 重写 `toString()` 方法,使用 `ToStringBuilder`
6. ✅ 所有字段使用 private 修饰,提供 public 的 Getter/Setter

### 2. Controller 层

**位置**: `dataself-server-skills/src/main/java/com/shanzhu/dataself/server/skills/controller/`

**规范**:
```java
package com.shanzhu.dataself.server.skills.controller;

import com.shanzhu.dataself.api.skills.domain.Goal;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.application.domain.JsonResult;
import com.shanzhu.dataself.framework.core.application.page.TableDataInfo;
import com.shanzhu.dataself.framework.jdbc.web.utils.PageUtils;
import com.shanzhu.dataself.framework.log.annotation.Log;
import com.shanzhu.dataself.framework.log.enums.BusinessType;
import com.shanzhu.dataself.framework.security.utils.SecurityUtils;
import com.shanzhu.dataself.server.skills.service.IGoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 目标管理
 */
@Tag(description = "GoalController", name = "目标管理")
@RestController
@RequestMapping("/goal")
public class GoalController extends TWTController {

    @Autowired
    private IGoalService iGoalService;

    /**
     * 获取目标列表
     * @param goal Goal
     * @return JsonResult<TableDataInfo>
     */
    @Operation(summary = "获取目标列表")
    @GetMapping("/pageQuery")
    @PreAuthorize("@role.hasPermi('skills:goal:list')")
    public JsonResult<TableDataInfo<Goal>> pageQuery(Goal goal) {
        PageUtils.startPage();
        List<Goal> list = iGoalService.selectGoalList(goal);
        return JsonResult.success(PageUtils.getDataTable(list));
    }

    /**
     * 根据目标ID获取详细信息
     * @param goalId Long
     * @return JsonResult<Goal>
     */
    @Operation(summary = "根据目标ID获取详细信息")
    @PreAuthorize("@role.hasPermi('skills:goal:query')")
    @GetMapping("/{goalId}")
    public JsonResult<Goal> getInfo(@PathVariable Long goalId) {
        return JsonResult.success(iGoalService.selectGoalById(goalId));
    }

    /**
     * 新增目标
     * @param goal Goal
     * @return JsonResult<String>
     */
    @Operation(summary = "新增目标")
    @PostMapping
    @Log(service = "目标管理", businessType = BusinessType.INSERT)
    @PreAuthorize("@role.hasPermi('skills:goal:insert')")
    public JsonResult<String> insert(@Validated @RequestBody Goal goal) {
        goal.setCreateBy(SecurityUtils.getUsername());
        goal.setUserId(SecurityUtils.getLoginUser().getUserId());
        return json(iGoalService.insertGoal(goal));
    }

    /**
     * 修改目标
     * @param goal Goal
     * @return JsonResult<String>
     */
    @Operation(summary = "修改目标")
    @PutMapping
    @Log(service = "目标管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@role.hasPermi('skills:goal:edit')")
    public JsonResult<String> edit(@Validated @RequestBody Goal goal) {
        goal.setUpdateBy(SecurityUtils.getUsername());
        return json(iGoalService.updateGoal(goal));
    }

    /**
     * 删除目标
     * @param goalIds Long[]
     * @return JsonResult<String>
     */
    @Operation(summary = "删除目标")
    @PreAuthorize("@role.hasPermi('skills:goal:remove')")
    @Log(service = "目标管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{goalIds}")
    public JsonResult<String> remove(@PathVariable Long[] goalIds) {
        return json(iGoalService.deleteGoalByIds(goalIds));
    }
}
```

**关键点**:
1. ✅ 继承 `TWTController`
2. ✅ 使用 `@Tag` 和 `@Operation` 注解标注 API 文档
3. ✅ 使用 `@PreAuthorize` 进行权限控制
4. ✅ 使用 `@Log` 注解记录操作日志
5. ✅ 使用 `@Validated` 进行参数校验
6. ✅ 分页查询使用 `PageUtils.startPage()` 和 `PageUtils.getDataTable()`
7. ✅ 统一返回 `JsonResult` 类型
8. ✅ 设置 `createBy`, `updateBy`, `userId` 等字段

### 3. Service 接口层

**位置**: `dataself-server-skills/src/main/java/com/shanzhu/dataself/server/skills/service/`

**规范**:
```java
package com.shanzhu.dataself.server.skills.service;

import com.shanzhu.dataself.api.skills.domain.Goal;

import java.util.List;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 目标管理服务接口
 */
public interface IGoalService {

    /**
     * 根据条件分页查询目标列表
     * @param goal 目标信息
     * @return 目标信息集合
     */
    List<Goal> selectGoalList(Goal goal);

    /**
     * 通过目标ID查询目标
     * @param goalId 目标ID
     * @return 目标对象信息
     */
    Goal selectGoalById(Long goalId);

    /**
     * 新增目标信息
     * @param goal 目标信息
     * @return 结果
     */
    int insertGoal(Goal goal);

    /**
     * 修改目标信息
     * @param goal 目标信息
     * @return 结果
     */
    int updateGoal(Goal goal);

    /**
     * 批量删除目标信息
     * @param goalIds 需要删除的目标ID
     * @return 结果
     */
    int deleteGoalByIds(Long[] goalIds);

    /**
     * 通过目标ID删除目标
     * @param goalId 目标ID
     * @return 结果
     */
    int deleteGoalById(Long goalId);
}
```

**关键点**:
1. ✅ 接口名以 `I` 开头
2. ✅ 每个方法都有详细的 JavaDoc 注释
3. ✅ 方法命名遵循规范 (select, insert, update, delete)

### 4. Service 实现层

**位置**: `dataself-server-skills/src/main/java/com/shanzhu/dataself/server/skills/service/impl/`

**规范**:
```java
package com.shanzhu.dataself.server.skills.service.impl;

import com.shanzhu.dataself.api.skills.domain.Goal;
import com.shanzhu.dataself.framework.core.exception.TWTException;
import com.shanzhu.dataself.framework.datascope.annotation.MicroDataScope;
import com.shanzhu.dataself.server.skills.mapper.GoalMapper;
import com.shanzhu.dataself.server.skills.mapper.TaskMapper;
import com.shanzhu.dataself.server.skills.service.IGoalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 目标管理服务实现
 */
@Service
public class GoalServiceImpl implements IGoalService {

    private static final Logger log = LoggerFactory.getLogger(GoalServiceImpl.class);

    @Autowired
    private GoalMapper goalMapper;

    @Autowired
    private TaskMapper taskMapper;

    /**
     * 根据条件分页查询目标列表
     * @param goal 目标信息
     * @return 目标信息集合
     */
    @Override
    @MicroDataScope(userIdField = "user_id")
    public List<Goal> selectGoalList(Goal goal) {
        return goalMapper.selectGoalList(goal);
    }

    /**
     * 通过目标ID查询目标
     * @param goalId 目标ID
     * @return 目标对象信息
     */
    @Override
    public Goal selectGoalById(Long goalId) {
        return goalMapper.selectGoalById(goalId);
    }

    /**
     * 新增目标信息
     * @param goal 目标信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = TWTException.class)
    public int insertGoal(Goal goal) {
        // 业务逻辑处理
        return goalMapper.insertGoal(goal);
    }

    /**
     * 修改目标信息
     * @param goal 目标信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = TWTException.class)
    public int updateGoal(Goal goal) {
        // 业务逻辑处理
        return goalMapper.updateGoal(goal);
    }

    /**
     * 批量删除目标信息
     * @param goalIds 需要删除的目标ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = TWTException.class)
    public int deleteGoalByIds(Long[] goalIds) {
        // 删除关联的任务
        for (Long goalId : goalIds) {
            taskMapper.deleteTaskByGoalId(goalId);
        }
        return goalMapper.deleteGoalByIds(goalIds);
    }

    /**
     * 通过目标ID删除目标
     * @param goalId 目标ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = TWTException.class)
    public int deleteGoalById(Long goalId) {
        // 删除关联的任务
        taskMapper.deleteTaskByGoalId(goalId);
        return goalMapper.deleteGoalById(goalId);
    }
}
```

**关键点**:
1. ✅ 使用 `@Service` 注解
2. ✅ 实现对应的 Service 接口
3. ✅ 使用 `@Autowired` 注入 Mapper
4. ✅ 使用 `@Transactional` 进行事务控制
5. ✅ 使用 `@MicroDataScope` 进行数据权限控制
6. ✅ 使用 Logger 记录日志
7. ✅ 处理关联数据的删除逻辑

### 5. Mapper 接口层

**位置**: `dataself-server-skills/src/main/java/com/shanzhu/dataself/server/skills/mapper/`

**规范**:
```java
package com.shanzhu.dataself.server.skills.mapper;

import com.shanzhu.dataself.api.skills.domain.Goal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 目标管理Mapper
 */
public interface GoalMapper {

    /**
     * 根据条件分页查询目标列表
     * @param goal 目标信息
     * @return 目标信息集合
     */
    List<Goal> selectGoalList(Goal goal);

    /**
     * 通过目标ID查询目标
     * @param goalId 目标ID
     * @return 目标对象信息
     */
    Goal selectGoalById(Long goalId);

    /**
     * 新增目标信息
     * @param goal 目标信息
     * @return 结果
     */
    int insertGoal(Goal goal);

    /**
     * 修改目标信息
     * @param goal 目标信息
     * @return 结果
     */
    int updateGoal(Goal goal);

    /**
     * 通过目标ID删除目标
     * @param goalId 目标ID
     * @return 结果
     */
    int deleteGoalById(Long goalId);

    /**
     * 批量删除目标信息
     * @param goalIds 需要删除的目标ID
     * @return 结果
     */
    int deleteGoalByIds(Long[] goalIds);
}
```

**关键点**:
1. ✅ 接口方法与 Service 方法对应
2. ✅ 多参数方法使用 `@Param` 注解
3. ✅ 每个方法都有详细的 JavaDoc 注释

### 6. Mapper XML 层

**位置**: `dataself-server-skills/src/main/resources/mapper/skills/`

**规范**:
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.shanzhu.dataself.server.skills.mapper.GoalMapper">

    <resultMap type="Goal" id="GoalResult">
        <id property="goalId" column="goal_id"/>
        <result property="userId" column="user_id"/>
        <result property="title" column="title"/>
        <result property="description" column="description"/>
        <result property="status" column="status"/>
        <result property="priority" column="priority"/>
        <result property="expectedStartTime" column="expected_start_time"/>
        <result property="expectedEndTime" column="expected_end_time"/>
        <result property="actualStartTime" column="actual_start_time"/>
        <result property="actualEndTime" column="actual_end_time"/>
        <result property="progress" column="progress"/>
        <result property="score" column="score"/>
        <result property="summary" column="summary"/>
        <result property="tags" column="tags"/>
        <result property="delFlag" column="del_flag"/>
        <result property="createBy" column="create_by"/>
        <result property="createTime" column="create_time"/>
        <result property="updateBy" column="update_by"/>
        <result property="updateTime" column="update_time"/>
        <result property="remark" column="remark"/>
    </resultMap>

    <sql id="selectGoalVo">
        select goal_id, user_id, title, description, status, priority,
               expected_start_time, expected_end_time, actual_start_time, actual_end_time,
               progress, score, summary, tags, del_flag,
               create_by, create_time, update_by, update_time, remark
        from skills_goal
    </sql>

    <select id="selectGoalList" parameterType="Goal" resultMap="GoalResult">
        <include refid="selectGoalVo"/>
        where del_flag = '0'
        <if test="userId != null">
            AND user_id = #{userId}
        </if>
        <if test="title != null and title != ''">
            AND title like concat('%', #{title}, '%')
        </if>
        <if test="status != null">
            AND status = #{status}
        </if>
        <if test="priority != null">
            AND priority = #{priority}
        </if>
        <if test="beginTime != null and beginTime != ''">
            AND date_format(create_time,'%y%m%d') &gt;= date_format(#{beginTime},'%y%m%d')
        </if>
        <if test="endTime != null and endTime != ''">
            AND date_format(create_time,'%y%m%d') &lt;= date_format(#{endTime},'%y%m%d')
        </if>
        <!-- 数据范围过滤 -->
        ${params.dataScope}
        order by create_time desc
    </select>

    <select id="selectGoalById" parameterType="Long" resultMap="GoalResult">
        <include refid="selectGoalVo"/>
        where goal_id = #{goalId}
    </select>

    <insert id="insertGoal" parameterType="Goal" useGeneratedKeys="true" keyProperty="goalId">
        insert into skills_goal(
        <if test="userId != null">user_id,</if>
        <if test="title != null and title != ''">title,</if>
        <if test="description != null and description != ''">description,</if>
        <if test="status != null">status,</if>
        <if test="priority != null">priority,</if>
        <if test="expectedStartTime != null">expected_start_time,</if>
        <if test="expectedEndTime != null">expected_end_time,</if>
        <if test="tags != null and tags != ''">tags,</if>
        <if test="createBy != null and createBy != ''">create_by,</if>
        <if test="remark != null and remark != ''">remark,</if>
        create_time
        )values(
        <if test="userId != null">#{userId},</if>
        <if test="title != null and title != ''">#{title},</if>
        <if test="description != null and description != ''">#{description},</if>
        <if test="status != null">#{status},</if>
        <if test="priority != null">#{priority},</if>
        <if test="expectedStartTime != null">#{expectedStartTime},</if>
        <if test="expectedEndTime != null">#{expectedEndTime},</if>
        <if test="tags != null and tags != ''">#{tags},</if>
        <if test="createBy != null and createBy != ''">#{createBy},</if>
        <if test="remark != null and remark != ''">#{remark},</if>
        sysdate()
        )
    </insert>

    <update id="updateGoal" parameterType="Goal">
        update skills_goal
        <set>
            <if test="title != null and title != ''">title = #{title},</if>
            <if test="description != null and description != ''">description = #{description},</if>
            <if test="status != null">status = #{status},</if>
            <if test="priority != null">priority = #{priority},</if>
            <if test="expectedStartTime != null">expected_start_time = #{expectedStartTime},</if>
            <if test="expectedEndTime != null">expected_end_time = #{expectedEndTime},</if>
            <if test="actualStartTime != null">actual_start_time = #{actualStartTime},</if>
            <if test="actualEndTime != null">actual_end_time = #{actualEndTime},</if>
            <if test="progress != null">progress = #{progress},</if>
            <if test="score != null">score = #{score},</if>
            <if test="summary != null">summary = #{summary},</if>
            <if test="tags != null">tags = #{tags},</if>
            <if test="updateBy != null and updateBy != ''">update_by = #{updateBy},</if>
            <if test="remark != null">remark = #{remark},</if>
            update_time = sysdate()
        </set>
        where goal_id = #{goalId}
    </update>

    <delete id="deleteGoalById" parameterType="Long">
        update skills_goal set del_flag = '2' where goal_id = #{goalId}
    </delete>

    <delete id="deleteGoalByIds" parameterType="Long">
        update skills_goal set del_flag = '2' where goal_id in
        <foreach collection="array" item="goalId" open="(" separator="," close=")">
            #{goalId}
        </foreach>
    </delete>

</mapper>
```

**关键点**:
1. ✅ namespace 对应 Mapper 接口的全限定名
2. ✅ 定义 `resultMap` 映射字段
3. ✅ 使用 `<sql>` 标签定义可复用的 SQL 片段
4. ✅ 使用 `<if>` 标签进行动态 SQL 拼接
5. ✅ 插入操作使用 `useGeneratedKeys="true"` 返回主键
6. ✅ 删除操作使用逻辑删除 (del_flag = '2')
7. ✅ 使用 `sysdate()` 函数设置时间
8. ✅ 支持数据权限过滤 `${params.dataScope}`

### 7. 启动类

**位置**: `dataself-server-skills/src/main/java/com/shanzhu/dataself/server/skills/`

**规范**:
```java
package com.shanzhu.dataself.server.skills;

import com.shanzhu.dataself.framework.openfeign.annotation.EnableTWTFeignClients;
import com.shanzhu.dataself.framework.core.annotation.EnableShanZhuConfig;
import com.shanzhu.dataself.framework.security.annotation.EnableTWTResourceServer;
import com.shanzhu.dataself.framework.swagger.annotation.EnableShanZhuSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: Skills 微服务启动程序
 */
@EnableShanZhuSwagger2
@EnableTWTResourceServer
@MapperScan("com.shanzhu.**.mapper")
@EnableShanZhuConfig
@EnableTWTFeignClients
@SpringBootApplication
public class SkillsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillsApplication.class, args);
    }

}
```

**关键点**:
1. ✅ 使用 `@EnableShanZhuSwagger2` 启用 Swagger
2. ✅ 使用 `@EnableTWTResourceServer` 启用资源服务器
3. ✅ 使用 `@MapperScan` 扫描 Mapper 接口
4. ✅ 使用 `@EnableShanZhuConfig` 启用配置
5. ✅ 使用 `@EnableTWTFeignClients` 启用 Feign 客户端

### 8. 配置文件

**位置**: `dataself-server-skills/src/main/resources/application.yml`

**规范**:
```yaml
server:
  port: 8083

spring:
  application:
    # 应用名称
    name: dataself-server-skills
  profiles:
    # 环境配置
    active: dev
  messages:
    # 国际化资源文件路径
    basename: i18n/messages
    # 缓存持续时间（秒）
    cache-duration: 3600
  cloud:
    nacos:
      username: nacos
      password: nacos
      discovery:
        # 服务注册地址
        server-addr: 127.0.0.1:8848
        # 命名空间
        namespace: dev
      config:
        # 配置中心地址
        server-addr: ${spring.cloud.nacos.discovery.server-addr}
        # 配置文件格式
        file-extension: yml
        # 命名空间
        namespace: dev
        # 配置组
        group: DEFAULT_GROUP
  config:
    import:
      - optional:nacos:dataself-app-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension}
      - optional:nacos:${spring.application.name}-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension}
```

**关键点**:
1. ✅ 设置独立的端口号
2. ✅ 配置应用名称
3. ✅ 配置 Nacos 注册中心和配置中心
4. ✅ 配置国际化资源

---

## 数据库设计规范

### 表命名规范

```sql
-- 表名格式: 模块名_实体名
skills_goal      -- 目标表
skills_task      -- 任务表
```

### 字段命名规范

| 字段类型 | 命名规则 | 示例 |
|----------|----------|------|
| **主键** | 实体名_id | `goal_id`, `task_id` |
| **外键** | 关联实体名_id | `goal_id`, `user_id` |
| **时间字段** | 描述_time | `create_time`, `update_time`, `expected_start_time` |
| **状态字段** | status | `status` |
| **标志字段** | 描述_flag | `del_flag` |
| **普通字段** | 下划线命名 | `title`, `description`, `sort_order` |

### 公共字段

每个表都应包含以下公共字段:

```sql
create_by varchar(64) comment '创建者',
create_time datetime comment '创建时间',
update_by varchar(64) comment '更新者',
update_time datetime comment '更新时间',
remark varchar(500) comment '备注',
del_flag char(1) default '0' comment '删除标志(0=存在,2=删除)'
```

### 示例建表语句

```sql
-- 目标表
create table skills_goal (
    goal_id bigint(20) not null auto_increment comment '目标ID',
    user_id bigint(20) not null comment '用户ID',
    title varchar(200) not null comment '目标标题',
    description varchar(2000) comment '目标描述',
    status int(1) default 0 comment '目标状态(0=草稿,1=未开始,2=进行中,3=已完成,4=未完成,5=已归档)',
    priority int(1) default 1 comment '优先级(0=低,1=中,2=高,3=紧急)',
    expected_start_time datetime comment '预期开始时间',
    expected_end_time datetime comment '预期结束时间',
    actual_start_time datetime comment '实际开始时间',
    actual_end_time datetime comment '实际结束时间',
    progress int(3) default 0 comment '完成进度(0-100)',
    score decimal(5,2) comment '目标得分(0-100)',
    summary varchar(5000) comment '目标总结',
    tags varchar(500) comment '标签',
    create_by varchar(64) comment '创建者',
    create_time datetime comment '创建时间',
    update_by varchar(64) comment '更新者',
    update_time datetime comment '更新时间',
    remark varchar(500) comment '备注',
    del_flag char(1) default '0' comment '删除标志(0=存在,2=删除)',
    primary key (goal_id),
    key idx_user_id (user_id),
    key idx_status (status),
    key idx_create_time (create_time)
) engine=innodb auto_increment=1 comment='目标表';

-- 任务表
create table skills_task (
    task_id bigint(20) not null auto_increment comment '任务ID',
    goal_id bigint(20) not null comment '目标ID',
    user_id bigint(20) not null comment '用户ID',
    title varchar(200) not null comment '任务标题',
    description varchar(2000) comment '任务描述',
    status int(1) default 0 comment '任务状态(0=未开始,1=进行中,2=已完成,3=未完成)',
    priority int(1) default 1 comment '优先级(0=低,1=中,2=高,3=紧急)',
    expected_start_time datetime comment '预期开始时间',
    expected_end_time datetime comment '预期结束时间',
    actual_start_time datetime comment '实际开始时间',
    actual_end_time datetime comment '实际结束时间',
    rating int(1) comment '任务评分(0=差,1=合格,2=良,3=优秀)',
    summary varchar(5000) comment '任务总结',
    sort_order int(4) default 0 comment '排序号',
    tags varchar(500) comment '标签',
    create_by varchar(64) comment '创建者',
    create_time datetime comment '创建时间',
    update_by varchar(64) comment '更新者',
    update_time datetime comment '更新时间',
    remark varchar(500) comment '备注',
    del_flag char(1) default '0' comment '删除标志(0=存在,2=删除)',
    primary key (task_id),
    key idx_goal_id (goal_id),
    key idx_user_id (user_id),
    key idx_status (status),
    key idx_sort_order (sort_order)
) engine=innodb auto_increment=1 comment='任务表';
```

---

## API 接口规范

### RESTful API 设计

| 操作 | HTTP 方法 | URL 示例 | 说明 |
|------|-----------|----------|------|
| **查询列表** | GET | `/goal/pageQuery` | 分页查询 |
| **查询详情** | GET | `/goal/{id}` | 根据 ID 查询 |
| **新增** | POST | `/goal` | 新增数据 |
| **修改** | PUT | `/goal` | 修改数据 |
| **删除** | DELETE | `/goal/{ids}` | 删除数据 |

### 统一响应格式

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    // 业务数据
  }
}
```

### 分页响应格式

```json
{
  "code": 200,
  "msg": "查询成功",
  "data": {
    "total": 100,
    "rows": [
      // 数据列表
    ]
  }
}
```

### 权限标识规范

```
模块:实体:操作

示例:
skills:goal:list    - 查看目标列表
skills:goal:query   - 查看目标详情
skills:goal:insert  - 新增目标
skills:goal:edit    - 修改目标
skills:goal:remove  - 删除目标
skills:goal:export  - 导出目标

skills:task:list    - 查看任务列表
skills:task:query   - 查看任务详情
skills:task:insert  - 新增任务
skills:task:edit    - 修改任务
skills:task:remove  - 删除任务
```

---

## 代码生成流程

### 第一步：创建 API 模块

1. **创建模块目录结构**
```bash
mkdir -p dataself-api/dataself-api-skills/src/main/java/com/shanzhu/dataself/api/skills/domain
```

2. **创建 pom.xml**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <parent>
        <artifactId>dataself-api</artifactId>
        <groupId>com.shanzhu</groupId>
        <version>3.5.4</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>dataself-api-skills</artifactId>
    <description>技能目标服务API</description>
</project>
```

3. **创建实体类**
   - 创建 `Goal.java`
   - 创建 `Task.java`

4. **更新父 pom.xml**
   - 在 `dataself-api/pom.xml` 的 `<modules>` 中添加 `<module>dataself-api-skills</module>`

### 第二步：创建微服务模块

1. **创建模块目录结构**
```bash
mkdir -p dataself-server/dataself-server-skills/src/main/java/com/shanzhu/dataself/server/skills/{controller,service,mapper,utils}
mkdir -p dataself-server/dataself-server-skills/src/main/java/com/shanzhu/dataself/server/skills/controller/api
mkdir -p dataself-server/dataself-server-skills/src/main/java/com/shanzhu/dataself/server/skills/service/impl
mkdir -p dataself-server/dataself-server-skills/src/main/resources/mapper/skills
```

2. **创建 pom.xml**
   - 参考 `dataself-server-system/pom.xml`
   - 修改 `artifactId` 为 `dataself-server-skills`
   - 修改 `description` 为 `技能目标核心服务`
   - 添加 `dataself-api-skills` 依赖

3. **创建启动类**
   - 创建 `SkillsApplication.java`

4. **创建配置文件**
   - 创建 `application.yml`
   - 设置端口号为 `8083`
   - 设置应用名称为 `dataself-server-skills`

5. **更新父 pom.xml**
   - 在 `dataself/pom.xml` 的 `<modules>` 中添加 `<module>dataself-server/dataself-server-skills</module>`

### 第三步：生成业务代码

**按照以下顺序生成代码:**

1. **Mapper 接口** (`GoalMapper.java`, `TaskMapper.java`)
2. **Mapper XML** (`GoalMapper.xml`, `TaskMapper.xml`)
3. **Service 接口** (`IGoalService.java`, `ITaskService.java`)
4. **Service 实现** (`GoalServiceImpl.java`, `TaskServiceImpl.java`)
5. **Controller** (`GoalController.java`, `TaskController.java`)
6. **API 接口** (可选, `GoalApi.java`, `TaskApi.java`)

### 第四步：创建数据库表

执行建表 SQL 语句,创建 `skills_goal` 和 `skills_task` 表。

### 第五步：测试验证

1. 启动 Nacos
2. 启动 Skills 微服务
3. 访问 Swagger 文档: `http://localhost:8083/doc.html`
4. 测试 API 接口

---

## 注意事项

### ⚠️ 必须遵守的规范

1. **包名规范**
   - ✅ 必须使用 `com.shanzhu.dataself` 作为基础包名
   - ✅ API 模块: `com.shanzhu.dataself.api.skills`
   - ✅ 微服务模块: `com.shanzhu.dataself.server.skills`

2. **类名规范**
   - ✅ Controller 必须以 `Controller` 结尾
   - ✅ Service 接口必须以 `I` 开头, `Service` 结尾
   - ✅ Service 实现必须以 `ServiceImpl` 结尾
   - ✅ Mapper 接口必须以 `Mapper` 结尾

3. **继承和实现**
   - ✅ 实体类必须继承 `BaseEntity`
   - ✅ Controller 必须继承 `TWTController`
   - ✅ Service 实现必须实现对应的 Service 接口

4. **注解使用**
   - ✅ 必须使用 `@Schema` 标注 API 文档
   - ✅ 必须使用 `@PreAuthorize` 进行权限控制
   - ✅ 必须使用 `@Log` 记录操作日志
   - ✅ 必须使用 `@Validated` 进行参数校验
   - ✅ 必须使用 `@Transactional` 进行事务控制

5. **数据权限**
   - ✅ 查询列表方法必须使用 `@MicroDataScope` 注解
   - ✅ 必须设置 `userIdField` 字段进行用户数据隔离

6. **删除操作**
   - ✅ 必须使用逻辑删除 (del_flag = '2')
   - ✅ 删除主表数据时必须同时删除关联表数据

7. **时间字段**
   - ✅ 创建时间使用 `sysdate()` 函数
   - ✅ 更新时间使用 `sysdate()` 函数

8. **返回值**
   - ✅ Controller 方法必须返回 `JsonResult` 类型
   - ✅ 分页查询必须返回 `JsonResult<TableDataInfo>` 类型

### 🔧 代码生成工具建议

1. **使用 MyBatis Generator**
   - 可以快速生成 Mapper 接口和 XML 文件
   - 需要手动调整以符合项目规范

2. **使用项目内置代码生成器**
   - 访问代码生成模块: `dataself-server-gen`
   - 可以生成符合项目规范的完整代码

3. **使用 AI 辅助生成**
   - 提供本规范文档给 AI
   - AI 会按照规范生成代码
   - 需要人工审查和调整

### 📝 代码审查清单

生成代码后,请按照以下清单进行审查:

- [ ] 包名是否正确
- [ ] 类名是否符合规范
- [ ] 是否继承了正确的父类
- [ ] 是否实现了正确的接口
- [ ] 注解是否完整
- [ ] JavaDoc 注释是否完整
- [ ] 方法命名是否规范
- [ ] SQL 语句是否正确
- [ ] 是否处理了关联数据
- [ ] 是否使用了逻辑删除
- [ ] 是否添加了数据权限控制
- [ ] 配置文件是否正确

---

## 附录

### A. 常用注解说明

| 注解 | 说明 | 使用位置 |
|------|------|----------|
| `@RestController` | 标识 RESTful 控制器 | Controller 类 |
| `@RequestMapping` | 映射请求路径 | Controller 类/方法 |
| `@GetMapping` | 映射 GET 请求 | Controller 方法 |
| `@PostMapping` | 映射 POST 请求 | Controller 方法 |
| `@PutMapping` | 映射 PUT 请求 | Controller 方法 |
| `@DeleteMapping` | 映射 DELETE 请求 | Controller 方法 |
| `@PathVariable` | 获取路径参数 | Controller 方法参数 |
| `@RequestBody` | 获取请求体参数 | Controller 方法参数 |
| `@RequestParam` | 获取查询参数 | Controller 方法参数 |
| `@Validated` | 参数校验 | Controller 方法参数 |
| `@Service` | 标识服务类 | Service 实现类 |
| `@Autowired` | 自动注入 | 字段 |
| `@Transactional` | 事务控制 | Service 方法 |
| `@MicroDataScope` | 数据权限控制 | Service 方法 |
| `@PreAuthorize` | 权限控制 | Controller 方法 |
| `@Log` | 操作日志 | Controller 方法 |
| `@Schema` | API 文档 | 类/字段 |
| `@Operation` | API 文档 | Controller 方法 |
| `@Tag` | API 文档 | Controller 类 |

### B. 常用工具类

| 工具类 | 说明 | 常用方法 |
|--------|------|----------|
| `SecurityUtils` | 安全工具类 | `getUsername()`, `getLoginUser()`, `encryptPassword()` |
| `PageUtils` | 分页工具类 | `startPage()`, `getDataTable()` |
| `StrUtils` | 字符串工具类 | `isEmpty()`, `isNotEmpty()` |
| `JsonResult` | 统一响应类 | `success()`, `error()` |

### C. 参考文档

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [MyBatis 官方文档](https://mybatis.org/mybatis-3/)
- [Spring Cloud Alibaba 官方文档](https://spring-cloud-alibaba-group.github.io/)
- [Swagger 官方文档](https://swagger.io/docs/)

---

**文档结束**

**最后更新**: 2026-01-09  
**维护者**: shanzhu  
**联系方式**: shanzhu.cloud
