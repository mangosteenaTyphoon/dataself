# DataSelf OKR-Todo 模块 API 接口文档

## 📋 文档说明

**模块名称**：OKR-Todo 目标与任务管理系统  
**基础路径**：`http://localhost:8080/todo`  
**版本**：v1.0.0  
**更新时间**：2026-01-11

---

## 🔐 认证说明

所有接口都需要在请求头中携带 JWT Token：

```http
Authorization: Bearer {your_access_token}
```

---

## 📚 目录

1. [目标管理接口](#1-目标管理接口)
2. [任务管理接口](#2-任务管理接口)
3. [统计分析接口](#3-统计分析接口)
4. [数据模型](#4-数据模型)
5. [错误码说明](#5-错误码说明)

---

## 1. 目标管理接口

### 1.1 分页查询目标列表

**接口地址**：`GET /todo/goal/pageQuery`

**接口描述**：分页查询当前用户的目标列表，支持多条件筛选和排序

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| pageNum | Integer | 是 | 页码（从1开始） | 1 |
| pageSize | Integer | 是 | 每页数量 | 10 |
| title | String | 否 | 目标标题（模糊查询） | "学习Java" |
| status | String | 否 | 目标状态 | "IN_PROGRESS" |
| priority | String | 否 | 优先级 | "HIGH" |
| startTime | String | 否 | 开始时间（起） | "2026-01-01" |
| endTime | String | 否 | 结束时间（止） | "2026-12-31" |

**状态枚举值**：
- `DRAFT`：草稿
- `NOT_STARTED`：未开始
- `IN_PROGRESS`：进行中
- `COMPLETED`：已完成
- `NOT_ACHIEVED`：未达成
- `ARCHIVED`：已归档

**优先级枚举值**：
- `HIGH`：高
- `MEDIUM`：中
- `LOW`：低

**请求示例**：

```bash
curl -X GET "http://localhost:8080/todo/goal/pageQuery?pageNum=1&pageSize=10&status=IN_PROGRESS" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "total": 25,
    "rows": [
      {
        "goalId": 1,
        "userId": 1,
        "title": "学习Spring Boot 3.x",
        "description": "深入学习Spring Boot 3.x新特性",
        "status": "IN_PROGRESS",
        "priority": "HIGH",
        "progress": 60,
        "score": null,
        "expectedStartTime": "2026-01-01 00:00:00",
        "expectedEndTime": "2026-03-31 23:59:59",
        "actualStartTime": "2026-01-05 10:30:00",
        "actualEndTime": null,
        "summary": null,
        "tags": "技术,学习",
        "createTime": "2026-01-01 10:00:00",
        "updateTime": "2026-01-10 15:30:00",
        "remark": null
      }
    ],
    "pageNum": 1,
    "pageSize": 10
  }
}
```

---

### 1.2 查询目标详情

**接口地址**：`GET /todo/goal/{goalId}`

**接口描述**：根据目标ID查询目标详细信息

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| goalId | Long | 是 | 目标ID |

**请求示例**：

```bash
curl -X GET "http://localhost:8080/todo/goal/1" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "goalId": 1,
    "userId": 1,
    "title": "学习Spring Boot 3.x",
    "description": "深入学习Spring Boot 3.x新特性，包括虚拟线程、AOT编译等",
    "status": "IN_PROGRESS",
    "priority": "HIGH",
    "progress": 60,
    "score": null,
    "expectedStartTime": "2026-01-01 00:00:00",
    "expectedEndTime": "2026-03-31 23:59:59",
    "actualStartTime": "2026-01-05 10:30:00",
    "actualEndTime": null,
    "summary": null,
    "tags": "技术,学习,Spring",
    "createTime": "2026-01-01 10:00:00",
    "updateTime": "2026-01-10 15:30:00",
    "remark": "重点学习新特性"
  }
}
```

---

### 1.3 新增目标

**接口地址**：`POST /todo/goal`

**接口描述**：创建一个新的目标

**请求体**：

```json
{
  "title": "学习Spring Boot 3.x",
  "description": "深入学习Spring Boot 3.x新特性",
  "priority": "HIGH",
  "expectedStartTime": "2026-01-01 00:00:00",
  "expectedEndTime": "2026-03-31 23:59:59",
  "tags": "技术,学习,Spring",
  "remark": "重点学习新特性"
}
```

**字段说明**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| title | String | 是 | 目标标题（最多100字） |
| description | String | 否 | 目标描述（最多500字） |
| priority | String | 是 | 优先级（HIGH/MEDIUM/LOW） |
| expectedStartTime | String | 是 | 预期开始时间 |
| expectedEndTime | String | 是 | 预期结束时间 |
| tags | String | 否 | 标签（逗号分隔） |
| remark | String | 否 | 备注 |

**请求示例**：

```bash
curl -X POST "http://localhost:8080/todo/goal" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "学习Spring Boot 3.x",
    "description": "深入学习Spring Boot 3.x新特性",
    "priority": "HIGH",
    "expectedStartTime": "2026-01-01 00:00:00",
    "expectedEndTime": "2026-03-31 23:59:59",
    "tags": "技术,学习,Spring"
  }'
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "新增成功",
  "data": {
    "goalId": 1
  }
}
```

---

### 1.4 编辑目标

**接口地址**：`PUT /todo/goal`

**接口描述**：编辑目标信息（未归档的目标可编辑）

**请求体**：

```json
{
  "goalId": 1,
  "title": "学习Spring Boot 3.x（更新）",
  "description": "深入学习Spring Boot 3.x新特性，包括虚拟线程",
  "priority": "HIGH",
  "expectedStartTime": "2026-01-01 00:00:00",
  "expectedEndTime": "2026-04-30 23:59:59",
  "tags": "技术,学习,Spring,虚拟线程",
  "remark": "增加虚拟线程学习"
}
```

**字段说明**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| goalId | Long | 是 | 目标ID |
| title | String | 是 | 目标标题 |
| description | String | 否 | 目标描述 |
| priority | String | 是 | 优先级 |
| expectedStartTime | String | 是 | 预期开始时间 |
| expectedEndTime | String | 是 | 预期结束时间 |
| tags | String | 否 | 标签 |
| remark | String | 否 | 备注 |

**请求示例**：

```bash
curl -X PUT "http://localhost:8080/todo/goal" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "goalId": 1,
    "title": "学习Spring Boot 3.x（更新）",
    "priority": "HIGH",
    "expectedEndTime": "2026-04-30 23:59:59"
  }'
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "修改成功",
  "data": null
}
```

---

### 1.5 删除目标

**接口地址**：`DELETE /todo/goal/{goalIds}`

**接口描述**：批量删除目标（会级联删除关联的任务）

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| goalIds | String | 是 | 目标ID列表（逗号分隔） |

**请求示例**：

```bash
# 删除单个目标
curl -X DELETE "http://localhost:8080/todo/goal/1" \
  -H "Authorization: Bearer {token}"

# 批量删除
curl -X DELETE "http://localhost:8080/todo/goal/1,2,3" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

---

### 1.6 开始目标

**接口地址**：`POST /todo/goal/start/{goalId}`

**接口描述**：开始执行目标，状态变更为"进行中"，记录实际开始时间

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| goalId | Long | 是 | 目标ID |

**前置条件**：
- 目标状态必须是 `NOT_STARTED`（未开始）

**请求示例**：

```bash
curl -X POST "http://localhost:8080/todo/goal/start/1" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "目标已开始",
  "data": {
    "goalId": 1,
    "status": "IN_PROGRESS",
    "actualStartTime": "2026-01-11 10:30:00"
  }
}
```

---

### 1.7 完成目标

**接口地址**：`POST /todo/goal/complete/{goalId}`

**接口描述**：完成目标，系统自动计算得分

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| goalId | Long | 是 | 目标ID |

**请求体**：

```json
{
  "summary": "完成了Spring Boot 3.x的学习，掌握了虚拟线程、AOT编译等新特性"
}
```

**字段说明**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| summary | String | 是 | 完成总结（最多1000字） |

**前置条件**：
- 目标状态必须是 `IN_PROGRESS`（进行中）
- 所有任务必须已完成或未达成

**请求示例**：

```bash
curl -X POST "http://localhost:8080/todo/goal/complete/1" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "summary": "完成了Spring Boot 3.x的学习"
  }'
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "目标已完成",
  "data": {
    "goalId": 1,
    "status": "COMPLETED",
    "actualEndTime": "2026-03-25 18:00:00",
    "score": 88.50,
    "progress": 100
  }
}
```

---

### 1.8 归档目标

**接口地址**：`POST /todo/goal/archive/{goalId}`

**接口描述**：归档目标，归档后不可再编辑

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| goalId | Long | 是 | 目标ID |

**前置条件**：
- 目标状态必须是 `COMPLETED`（已完成）或 `NOT_ACHIEVED`（未达成）

**请求示例**：

```bash
curl -X POST "http://localhost:8080/todo/goal/archive/1" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "目标已归档",
  "data": {
    "goalId": 1,
    "status": "ARCHIVED"
  }
}
```

---

## 2. 任务管理接口

### 2.1 分页查询任务列表

**接口地址**：`GET /todo/task/pageQuery`

**接口描述**：分页查询当前用户的任务列表

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| pageNum | Integer | 是 | 页码 | 1 |
| pageSize | Integer | 是 | 每页数量 | 10 |
| goalId | Long | 否 | 目标ID | 1 |
| title | String | 否 | 任务标题 | "学习虚拟线程" |
| status | String | 否 | 任务状态 | "IN_PROGRESS" |
| priority | String | 否 | 优先级 | "HIGH" |

**任务状态枚举值**：
- `NOT_STARTED`：未开始
- `IN_PROGRESS`：进行中
- `COMPLETED`：已完成
- `NOT_ACHIEVED`：未达成

**请求示例**：

```bash
curl -X GET "http://localhost:8080/todo/task/pageQuery?pageNum=1&pageSize=10&goalId=1" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "total": 5,
    "rows": [
      {
        "taskId": 1,
        "goalId": 1,
        "userId": 1,
        "title": "学习虚拟线程基础",
        "description": "了解虚拟线程的概念和使用场景",
        "status": "COMPLETED",
        "priority": "HIGH",
        "score": "EXCELLENT",
        "expectedStartTime": "2026-01-01 00:00:00",
        "expectedEndTime": "2026-01-10 23:59:59",
        "actualStartTime": "2026-01-02 09:00:00",
        "actualEndTime": "2026-01-08 17:00:00",
        "summary": "完成了虚拟线程基础学习",
        "sortOrder": 1,
        "createTime": "2026-01-01 10:00:00",
        "updateTime": "2026-01-08 17:00:00"
      }
    ],
    "pageNum": 1,
    "pageSize": 10
  }
}
```

---

### 2.2 查询目标下的任务列表

**接口地址**：`GET /todo/task/goal/{goalId}`

**接口描述**：查询指定目标下的所有任务（不分页）

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| goalId | Long | 是 | 目标ID |

**请求示例**：

```bash
curl -X GET "http://localhost:8080/todo/task/goal/1" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "taskId": 1,
      "goalId": 1,
      "title": "学习虚拟线程基础",
      "status": "COMPLETED",
      "priority": "HIGH",
      "score": "EXCELLENT",
      "sortOrder": 1
    },
    {
      "taskId": 2,
      "goalId": 1,
      "title": "学习AOT编译",
      "status": "IN_PROGRESS",
      "priority": "MEDIUM",
      "score": null,
      "sortOrder": 2
    }
  ]
}
```

---

### 2.3 查询任务详情

**接口地址**：`GET /todo/task/{taskId}`

**接口描述**：根据任务ID查询任务详细信息

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| taskId | Long | 是 | 任务ID |

**请求示例**：

```bash
curl -X GET "http://localhost:8080/todo/task/1" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "taskId": 1,
    "goalId": 1,
    "userId": 1,
    "title": "学习虚拟线程基础",
    "description": "了解虚拟线程的概念、使用场景和基本API",
    "status": "COMPLETED",
    "priority": "HIGH",
    "score": "EXCELLENT",
    "expectedStartTime": "2026-01-01 00:00:00",
    "expectedEndTime": "2026-01-10 23:59:59",
    "actualStartTime": "2026-01-02 09:00:00",
    "actualEndTime": "2026-01-08 17:00:00",
    "summary": "完成了虚拟线程基础学习，掌握了Thread.ofVirtual()等API",
    "sortOrder": 1,
    "createTime": "2026-01-01 10:00:00",
    "updateTime": "2026-01-08 17:00:00",
    "remark": "重点学习"
  }
}
```

---

### 2.4 新增任务

**接口地址**：`POST /todo/task`

**接口描述**：在目标下创建新任务

**请求体**：

```json
{
  "goalId": 1,
  "title": "学习虚拟线程基础",
  "description": "了解虚拟线程的概念和使用场景",
  "priority": "HIGH",
  "expectedStartTime": "2026-01-01 00:00:00",
  "expectedEndTime": "2026-01-10 23:59:59",
  "sortOrder": 1,
  "remark": "重点学习"
}
```

**字段说明**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| goalId | Long | 是 | 所属目标ID |
| title | String | 是 | 任务标题（最多100字） |
| description | String | 否 | 任务描述（最多500字） |
| priority | String | 是 | 优先级（HIGH/MEDIUM/LOW） |
| expectedStartTime | String | 是 | 预期开始时间 |
| expectedEndTime | String | 是 | 预期结束时间 |
| sortOrder | Integer | 否 | 排序号（默认0） |
| remark | String | 否 | 备注 |

**请求示例**：

```bash
curl -X POST "http://localhost:8080/todo/task" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "goalId": 1,
    "title": "学习虚拟线程基础",
    "priority": "HIGH",
    "expectedStartTime": "2026-01-01 00:00:00",
    "expectedEndTime": "2026-01-10 23:59:59"
  }'
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "新增成功",
  "data": {
    "taskId": 1
  }
}
```

---

### 2.5 编辑任务

**接口地址**：`PUT /todo/task`

**接口描述**：编辑任务信息

**请求体**：

```json
{
  "taskId": 1,
  "title": "学习虚拟线程基础（更新）",
  "description": "深入了解虚拟线程的概念和使用场景",
  "priority": "HIGH",
  "expectedStartTime": "2026-01-01 00:00:00",
  "expectedEndTime": "2026-01-15 23:59:59",
  "sortOrder": 1,
  "remark": "增加深入学习"
}
```

**请求示例**：

```bash
curl -X PUT "http://localhost:8080/todo/task" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "taskId": 1,
    "title": "学习虚拟线程基础（更新）",
    "expectedEndTime": "2026-01-15 23:59:59"
  }'
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "修改成功",
  "data": null
}
```

---

### 2.6 删除任务

**接口地址**：`DELETE /todo/task/{taskIds}`

**接口描述**：批量删除任务（会触发目标进度重新计算）

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| taskIds | String | 是 | 任务ID列表（逗号分隔） |

**请求示例**：

```bash
# 删除单个任务
curl -X DELETE "http://localhost:8080/todo/task/1" \
  -H "Authorization: Bearer {token}"

# 批量删除
curl -X DELETE "http://localhost:8080/todo/task/1,2,3" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

---

### 2.7 开始任务

**接口地址**：`POST /todo/task/start/{taskId}`

**接口描述**：开始执行任务，状态变更为"进行中"

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| taskId | Long | 是 | 任务ID |

**前置条件**：
- 任务状态必须是 `NOT_STARTED`（未开始）

**请求示例**：

```bash
curl -X POST "http://localhost:8080/todo/task/start/1" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "任务已开始",
  "data": {
    "taskId": 1,
    "status": "IN_PROGRESS",
    "actualStartTime": "2026-01-11 10:30:00"
  }
}
```

---

### 2.8 完成任务

**接口地址**：`POST /todo/task/complete/{taskId}`

**接口描述**：完成任务，需要填写评分和总结

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| taskId | Long | 是 | 任务ID |

**请求体**：

```json
{
  "score": "EXCELLENT",
  "summary": "完成了虚拟线程基础学习，掌握了相关API"
}
```

**字段说明**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| score | String | 是 | 任务评分（EXCELLENT/GOOD/QUALIFIED/POOR） |
| summary | String | 是 | 完成总结（最多500字） |

**评分说明**：
- `EXCELLENT`：优秀（95分）
- `GOOD`：良好（85分）
- `QUALIFIED`：合格（75分）
- `POOR`：较差（60分）

**前置条件**：
- 任务状态必须是 `IN_PROGRESS`（进行中）

**请求示例**：

```bash
curl -X POST "http://localhost:8080/todo/task/complete/1" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "score": "EXCELLENT",
    "summary": "完成了虚拟线程基础学习"
  }'
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "任务已完成",
  "data": {
    "taskId": 1,
    "status": "COMPLETED",
    "score": "EXCELLENT",
    "actualEndTime": "2026-01-08 17:00:00",
    "goalProgress": 20
  }
}
```

---

### 2.9 标记任务未达成

**接口地址**：`POST /todo/task/notAchieved/{taskId}`

**接口描述**：标记任务为未达成状态

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| taskId | Long | 是 | 任务ID |

**前置条件**：
- 任务状态必须是 `IN_PROGRESS`（进行中）

**请求示例**：

```bash
curl -X POST "http://localhost:8080/todo/task/notAchieved/1" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "任务已标记为未达成",
  "data": {
    "taskId": 1,
    "status": "NOT_ACHIEVED"
  }
}
```

---

## 3. 统计分析接口

### 3.1 获取目标统计

**接口地址**：`GET /todo/statistics/goal`

**接口描述**：获取当前用户的目标统计数据

**请求参数**：无

**请求示例**：

```bash
curl -X GET "http://localhost:8080/todo/statistics/goal" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "totalCount": 25,
    "draftCount": 2,
    "notStartedCount": 3,
    "inProgressCount": 10,
    "completedCount": 8,
    "notAchievedCount": 1,
    "archivedCount": 1,
    "completionRate": 80.0,
    "averageScore": 85.5,
    "highPriorityCount": 8,
    "mediumPriorityCount": 12,
    "lowPriorityCount": 5
  }
}
```

**字段说明**：

| 字段名 | 类型 | 说明 |
|--------|------|------|
| totalCount | Integer | 总目标数 |
| draftCount | Integer | 草稿数 |
| notStartedCount | Integer | 未开始数 |
| inProgressCount | Integer | 进行中数 |
| completedCount | Integer | 已完成数 |
| notAchievedCount | Integer | 未达成数 |
| archivedCount | Integer | 已归档数 |
| completionRate | Double | 完成率（%） |
| averageScore | Double | 平均得分 |
| highPriorityCount | Integer | 高优先级数 |
| mediumPriorityCount | Integer | 中优先级数 |
| lowPriorityCount | Integer | 低优先级数 |

---

### 3.2 获取任务统计

**接口地址**：`GET /todo/statistics/task`

**接口描述**：获取当前用户的任务统计数据

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| goalId | Long | 否 | 目标ID（不传则统计所有任务） |

**请求示例**：

```bash
# 统计所有任务
curl -X GET "http://localhost:8080/todo/statistics/task" \
  -H "Authorization: Bearer {token}"

# 统计指定目标的任务
curl -X GET "http://localhost:8080/todo/statistics/task?goalId=1" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "totalCount": 50,
    "notStartedCount": 10,
    "inProgressCount": 20,
    "completedCount": 18,
    "notAchievedCount": 2,
    "completionRate": 90.0,
    "excellentCount": 8,
    "goodCount": 6,
    "qualifiedCount": 3,
    "poorCount": 1,
    "averageScore": 87.5
  }
}
```

**字段说明**：

| 字段名 | 类型 | 说明 |
|--------|------|------|
| totalCount | Integer | 总任务数 |
| notStartedCount | Integer | 未开始数 |
| inProgressCount | Integer | 进行中数 |
| completedCount | Integer | 已完成数 |
| notAchievedCount | Integer | 未达成数 |
| completionRate | Double | 完成率（%） |
| excellentCount | Integer | 优秀任务数 |
| goodCount | Integer | 良好任务数 |
| qualifiedCount | Integer | 合格任务数 |
| poorCount | Integer | 较差任务数 |
| averageScore | Double | 平均得分 |

---

### 3.3 获取数据看板

**接口地址**：`GET /todo/statistics/dashboard`

**接口描述**：获取综合数据看板（包含目标和任务的关键指标）

**请求参数**：无

**请求示例**：

```bash
curl -X GET "http://localhost:8080/todo/statistics/dashboard" \
  -H "Authorization: Bearer {token}"
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "goalStatistics": {
      "totalCount": 25,
      "inProgressCount": 10,
      "completedCount": 8,
      "completionRate": 80.0,
      "averageScore": 85.5
    },
    "taskStatistics": {
      "totalCount": 50,
      "inProgressCount": 20,
      "completedCount": 18,
      "completionRate": 90.0,
      "averageScore": 87.5
    },
    "recentGoals": [
      {
        "goalId": 1,
        "title": "学习Spring Boot 3.x",
        "status": "IN_PROGRESS",
        "progress": 60,
        "updateTime": "2026-01-10 15:30:00"
      }
    ],
    "recentTasks": [
      {
        "taskId": 1,
        "title": "学习虚拟线程基础",
        "status": "COMPLETED",
        "score": "EXCELLENT",
        "updateTime": "2026-01-08 17:00:00"
      }
    ]
  }
}
```

---

## 4. 数据模型

### 4.1 目标（Goal）

```typescript
interface Goal {
  goalId: number;              // 目标ID
  userId: number;              // 用户ID
  title: string;               // 目标标题
  description?: string;        // 目标描述
  status: GoalStatus;          // 目标状态
  priority: Priority;          // 优先级
  progress: number;            // 进度（0-100）
  score?: number;              // 得分（0-100）
  expectedStartTime: string;   // 预期开始时间
  expectedEndTime: string;     // 预期结束时间
  actualStartTime?: string;    // 实际开始时间
  actualEndTime?: string;      // 实际结束时间
  summary?: string;            // 完成总结
  tags?: string;               // 标签
  createTime: string;          // 创建时间
  updateTime: string;          // 更新时间
  remark?: string;             // 备注
}

type GoalStatus = 
  | 'DRAFT'          // 草稿
  | 'NOT_STARTED'    // 未开始
  | 'IN_PROGRESS'    // 进行中
  | 'COMPLETED'      // 已完成
  | 'NOT_ACHIEVED'   // 未达成
  | 'ARCHIVED';      // 已归档

type Priority = 
  | 'HIGH'           // 高
  | 'MEDIUM'         // 中
  | 'LOW';           // 低
```

---

### 4.2 任务（Task）

```typescript
interface Task {
  taskId: number;              // 任务ID
  goalId: number;              // 所属目标ID
  userId: number;              // 用户ID
  title: string;               // 任务标题
  description?: string;        // 任务描述
  status: TaskStatus;          // 任务状态
  priority: Priority;          // 优先级
  score?: TaskScore;           // 任务评分
  expectedStartTime: string;   // 预期开始时间
  expectedEndTime: string;     // 预期结束时间
  actualStartTime?: string;    // 实际开始时间
  actualEndTime?: string;      // 实际结束时间
  summary?: string;            // 完成总结
  sortOrder: number;           // 排序号
  createTime: string;          // 创建时间
  updateTime: string;          // 更新时间
  remark?: string;             // 备注
}

type TaskStatus = 
  | 'NOT_STARTED'    // 未开始
  | 'IN_PROGRESS'    // 进行中
  | 'COMPLETED'      // 已完成
  | 'NOT_ACHIEVED';  // 未达成

type TaskScore = 
  | 'EXCELLENT'      // 优秀（95分）
  | 'GOOD'           // 良好（85分）
  | 'QUALIFIED'      // 合格（75分）
  | 'POOR';          // 较差（60分）
```

---

## 5. 错误码说明

### 5.1 通用错误码

| 错误码 | 说明 | 解决方案 |
|--------|------|----------|
| 200 | 操作成功 | - |
| 400 | 请求参数错误 | 检查请求参数格式和必填项 |
| 401 | 未授权或token过期 | 重新登录获取token |
| 403 | 无权限访问 | 检查用户权限配置 |
| 404 | 资源不存在 | 检查资源ID是否正确 |
| 500 | 服务器内部错误 | 联系技术支持 |

---

### 5.2 业务错误码

| 错误码 | 说明 | 场景 |
|--------|------|------|
| 1001 | 目标不存在 | 查询、编辑、删除目标时 |
| 1002 | 目标状态不允许此操作 | 状态流转时 |
| 1003 | 目标下存在未完成的任务 | 完成目标时 |
| 1004 | 目标已归档，不可编辑 | 编辑已归档的目标时 |
| 2001 | 任务不存在 | 查询、编辑、删除任务时 |
| 2002 | 任务状态不允许此操作 | 状态流转时 |
| 2003 | 任务评分不能为空 | 完成任务时未填写评分 |
| 2004 | 任务总结不能为空 | 完成任务时未填写总结 |
| 3001 | 数据权限不足 | 访问其他用户的数据时 |

---

## 6. 业务规则说明

### 6.1 目标状态流转规则

```
DRAFT（草稿）
  ↓ 提交
NOT_STARTED（未开始）
  ↓ 开始
IN_PROGRESS（进行中）
  ↓ 完成（所有任务完成）
COMPLETED（已完成）
  ↓ 归档
ARCHIVED（已归档）
```

**特殊规则**：
- 完成状态下添加新任务 → 自动回到进行中状态
- 归档后不可再编辑
- 删除目标会级联删除所有任务

---

### 6.2 任务状态流转规则

```
NOT_STARTED（未开始）
  ↓ 开始
IN_PROGRESS（进行中）
  ↓ 完成/未达成
COMPLETED（已完成）或 NOT_ACHIEVED（未达成）
```

**特殊规则**：
- 任务完成会触发目标进度更新
- 最后一个任务完成会触发目标完成
- 任务评分会影响目标得分计算

---

### 6.3 进度计算规则

**目标进度**：
```
进度 = (已完成任务数 / 总任务数) × 100%
```

- 无任务时进度为 0%
- 实时自动计算，不可手动修改
- 删除任务会重新计算进度

---

### 6.4 得分计算规则

**目标得分**：
```
目标得分 = 任务质量得分 × 70% + 时间完成得分 × 30%

任务质量得分 = Σ(任务评分) / 任务数

时间完成得分：
- 提前完成：100分
- 按时完成：90分
- 逾期1-3天：80分
- 逾期4-7天：70分
- 逾期8-14天：60分
- 逾期15天以上：50分
```

**任务评分映射**：
- EXCELLENT（优秀）：95分
- GOOD（良好）：85分
- QUALIFIED（合格）：75分
- POOR（较差）：60分

---

## 7. 前端开发建议

### 7.1 页面结构建议

**主要页面**：
1. **目标列表页**
   - 展示目标卡片（标题、状态、进度、优先级）
   - 支持筛选（状态、优先级、时间范围）
   - 支持排序（创建时间、更新时间、优先级）
   - 快速操作（开始、完成、归档、删除）

2. **目标详情页**
   - 目标基本信息
   - 任务列表（可拖拽排序）
   - 进度可视化（进度条、百分比）
   - 得分展示（雷达图）
   - 操作按钮（编辑、开始、完成、归档）

3. **任务管理页**
   - 任务列表（按目标分组）
   - 任务状态看板（未开始、进行中、已完成）
   - 快速操作（开始、完成、标记未达成）

4. **数据统计页**
   - 目标统计卡片
   - 任务统计卡片
   - 趋势图表（完成率、得分趋势）
   - 最近动态列表

---

### 7.2 组件建议

**通用组件**：
- `GoalCard`：目标卡片
- `TaskCard`：任务卡片
- `StatusBadge`：状态标签
- `PriorityTag`：优先级标签
- `ProgressBar`：进度条
- `ScoreDisplay`：得分展示
- `DateRangePicker`：日期范围选择器
- `ConfirmDialog`：确认对话框

**表单组件**：
- `GoalForm`：目标表单
- `TaskForm`：任务表单
- `CompleteForm`：完成表单（带评分和总结）

---

### 7.3 状态管理建议

**推荐使用 Pinia/Vuex/Redux 管理以下状态**：

```typescript
interface AppState {
  // 目标相关
  goals: Goal[];
  currentGoal: Goal | null;
  goalFilters: GoalFilters;
  
  // 任务相关
  tasks: Task[];
  currentTask: Task | null;
  taskFilters: TaskFilters;
  
  // 统计数据
  goalStatistics: GoalStatistics;
  taskStatistics: TaskStatistics;
  
  // UI状态
  loading: boolean;
  error: string | null;
}
```

---

### 7.4 API 调用示例（TypeScript）

```typescript
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/todo';

// 创建axios实例
const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

// 请求拦截器（添加token）
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// 响应拦截器（统一错误处理）
api.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response?.status === 401) {
      // token过期，跳转登录
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// API方法
export const goalApi = {
  // 查询目标列表
  getGoalList: (params: GoalQueryParams) => 
    api.get('/goal/pageQuery', { params }),
  
  // 查询目标详情
  getGoalDetail: (goalId: number) => 
    api.get(`/goal/${goalId}`),
  
  // 新增目标
  createGoal: (data: CreateGoalDto) => 
    api.post('/goal', data),
  
  // 编辑目标
  updateGoal: (data: UpdateGoalDto) => 
    api.put('/goal', data),
  
  // 删除目标
  deleteGoal: (goalIds: number[]) => 
    api.delete(`/goal/${goalIds.join(',')}`),
  
  // 开始目标
  startGoal: (goalId: number) => 
    api.post(`/goal/start/${goalId}`),
  
  // 完成目标
  completeGoal: (goalId: number, summary: string) => 
    api.post(`/goal/complete/${goalId}`, { summary }),
  
  // 归档目标
  archiveGoal: (goalId: number) => 
    api.post(`/goal/archive/${goalId}`)
};

export const taskApi = {
  // 查询任务列表
  getTaskList: (params: TaskQueryParams) => 
    api.get('/task/pageQuery', { params }),
  
  // 查询目标下的任务
  getTasksByGoal: (goalId: number) => 
    api.get(`/task/goal/${goalId}`),
  
  // 新增任务
  createTask: (data: CreateTaskDto) => 
    api.post('/task', data),
  
  // 完成任务
  completeTask: (taskId: number, data: CompleteTaskDto) => 
    api.post(`/task/complete/${taskId}`, data)
};

export const statisticsApi = {
  // 获取目标统计
  getGoalStatistics: () => 
    api.get('/statistics/goal'),
  
  // 获取任务统计
  getTaskStatistics: (goalId?: number) => 
    api.get('/statistics/task', { params: { goalId } }),
  
  // 获取数据看板
  getDashboard: () => 
    api.get('/statistics/dashboard')
};
```

---

## 8. 测试建议

### 8.1 单元测试

**测试覆盖**：
- 所有API方法
- 状态管理逻辑
- 工具函数

**测试工具**：
- Jest / Vitest
- Testing Library

---

### 8.2 集成测试

**测试场景**：
- 完整的业务流程（创建目标 → 添加任务 → 完成任务 → 完成目标）
- 状态流转逻辑
- 数据联动（任务完成 → 目标进度更新）

---

### 8.3 E2E测试

**测试工具**：
- Cypress
- Playwright

**测试场景**：
- 用户完整操作流程
- 异常场景处理
- 权限控制验证

---

## 9. 附录

### 9.1 Postman Collection

可以导入以下 Postman Collection 进行接口测试：

```json
{
  "info": {
    "name": "DataSelf OKR-Todo API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "目标管理",
      "item": [
        {
          "name": "查询目标列表",
          "request": {
            "method": "GET",
            "url": "{{baseUrl}}/goal/pageQuery?pageNum=1&pageSize=10"
          }
        }
      ]
    }
  ],
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8080/todo"
    },
    {
      "key": "token",
      "value": "your_token_here"
    }
  ]
}
```

---

### 9.2 常见问题

**Q1：如何获取 token？**  
A：通过认证接口登录后获取，详见认证文档。

**Q2：为什么接口返回 401？**  
A：token 过期或无效，需要重新登录。

**Q3：目标完成后还能添加任务吗？**  
A：可以，添加任务后目标会自动回到"进行中"状态。

**Q4：如何修改目标的实际开始时间？**  
A：实际开始时间由系统自动记录，不可手动修改。

**Q5：删除目标会删除关联的任务吗？**  
A：是的，删除目标会级联删除所有关联的任务。

---

## 10. 更新日志

### v1.0.0 (2026-01-11)
- 初始版本发布
- 实现目标管理功能
- 实现任务管理功能
- 实现统计分析功能
- 实现进度和得分自动计算

---

## 📞 技术支持

如有问题，请联系：
- 邮箱：support@dataself.com
- 文档：http://docs.dataself.com
- Swagger UI：http://localhost:8080/doc.html

---

**文档结束**
