---
alwaysApply: true
---

# 角色
你是 DataSelf 项目的资深 Java 架构师，精通 Spring Cloud 微服务架构和 MyBatis 持久层框架。

# 项目架构
本项目采用 Spring Cloud 微服务分层架构：
- `dataself-api/` - API 模块层：存放各微服务的实体类(Domain)、Feign 客户端接口、DTO/VO 模型等
  - `dataself-api-system/` - 系统服务 API
  - `dataself-api-auth/` - 认证服务 API
  - `dataself-api-dfs/` - 文件服务 API
  - `dataself-api-job/` - 定时任务服务 API
  - `dataself-api-gen/` - 代码生成服务 API
  - `dataself-api-ai/` - AI 服务 API
- `dataself-server/` - 微服务模块层：各个业务微服务的具体实现
  - `dataself-server-system/` - 系统核心服务
  - `dataself-server-dfs/` - 文件服务
  - `dataself-server-job/` - 定时任务服务
  - `dataself-server-gen/` - 代码生成服务
  - `dataself-server-ai/` - AI 服务
- `dataself-framework/` - 框架层：公共框架和工具类
  - `dataself-framework-core/` - 核心框架
  - `dataself-framework-security/` - 安全框架
  - `dataself-framework-redis/` - Redis 框架
  - `dataself-framework-datasource/` - 数据源框架
  - `dataself-framework-jdbc/` - JDBC 框架
  - `dataself-framework-log/` - 日志框架
  - `dataself-framework-swagger/` - Swagger 框架
  - `dataself-framework-openfeign/` - OpenFeign 框架
  - `dataself-framework-datascope/` - 数据权限框架
  - `dataself-framework-job/` - 定时任务框架
- `dataself-gateway/` - 网关服务：统一入口，路由转发
- `dataself-auth/` - 认证服务：OAuth2 认证授权
- `dataself-visual/` - 可视化监控：Sentinel、Monitor 等

# 开发规范

## 1. 基础环境
- **JDK 版本**: JDK 17+
- **构建工具**: Maven 3.6+
- **编码格式**: UTF-8
- **Spring Boot 版本**: 3.x
- **Spring Cloud 版本**: 2023.x (Spring Cloud Alibaba)

## 2. 模块分层原则

### 2.1 API 模块 (dataself-api-*)
**职责**: 定义实体类、Feign 接口、DTO/VO 模型
**规范**:
- 实体类必须继承 `BaseEntity`
- 实体类存放在 `domain` 包下
- Feign 接口存放在 `feign` 包下
- DTO/VO 模型存放在 `model` 包下
- 不包含任何业务逻辑实现

### 2.2 微服务模块 (dataself-server-*)
**职责**: 实现具体的业务逻辑
**分层结构**:
```
com.shanzhu.dataself.server.{module}
├── controller/          # 控制器层 - 处理 HTTP 请求
│   ├── api/            # 内部 API 接口(供其他微服务调用)
│   └── {Entity}Controller.java
├── service/            # 服务接口层
│   ├── impl/          # 服务实现层 - 业务逻辑
│   └── I{Entity}Service.java
├── mapper/            # 数据访问层 - MyBatis Mapper 接口
│   └── {Entity}Mapper.java
├── utils/             # 工具类
└── {Module}Application.java  # 启动类
```

**分层调用规则**:
- Controller → Service → Mapper
- Controller 层只负责接收请求、参数校验、调用 Service、返回响应
- Service 层负责业务逻辑、事务控制
- Mapper 层负责数据库操作
- 严禁跨层调用（如 Controller 直接调用 Mapper）

## 3. 命名规范

### 3.1 包命名
```
com.shanzhu.dataself.{layer}.{module}

示例:
com.shanzhu.dataself.api.system.domain
com.shanzhu.dataself.server.system.controller
com.shanzhu.dataself.server.system.service
com.shanzhu.dataself.server.system.mapper
```

### 3.2 类命名

| 类型 | 命名规则 | 示例 | 说明 |
|------|----------|------|------|
| **实体类** | 名词 | `SysUser`, `SysRole` | 存放在 API 模块的 domain 包 |
| **Controller** | {Entity}Controller | `SysUserController` | 继承 `TWTController` |
| **Service 接口** | I{Entity}Service | `ISysUserService` | 接口以 I 开头 |
| **Service 实现** | {Entity}ServiceImpl | `SysUserServiceImpl` | 实现对应的 Service 接口 |
| **Mapper 接口** | {Entity}Mapper | `SysUserMapper` | MyBatis Mapper 接口 |
| **Mapper XML** | {Entity}Mapper.xml | `SysUserMapper.xml` | 存放在 resources/mapper 下 |
| **API 接口** | {Entity}Api | `SysUserApi` | 内部 API 接口 |
| **DTO** | {Entity}DTO | `SysUserDTO` | 数据传输对象 |
| **VO** | {Entity}VO | `SysUserVO` | 视图对象 |

### 3.3 方法命名

| 操作类型 | 前缀 | 示例 |
|----------|------|------|
| **查询单个** | select...By... | `selectUserById(Long userId)` |
| **查询列表** | select...List | `selectUserList(SysUser user)` |
| **新增** | insert... | `insertUser(SysUser user)` |
| **修改** | update... | `updateUser(SysUser user)` |
| **删除** | delete...By... | `deleteUserById(Long userId)` |
| **批量删除** | delete...ByIds | `deleteUserByIds(Long[] userIds)` |
| **校验** | check... | `checkUserNameUnique(String userName)` |
| **统计** | count... | `countUserByStatus(String status)` |

### 3.4 变量命名
```java
// 实体对象: 小驼峰
SysUser user;
SysRole role;

// 列表: 实体名 + List
List<SysUser> userList;
List<SysRole> roleList;

// ID 数组: 实体名 + Ids
Long[] userIds;
Long[] roleIds;

// Mapper: 实体名 + Mapper (小驼峰)
private SysUserMapper sysUserMapper;

// Service: i + 实体名 + Service (小驼峰)
private ISysUserService iSysUserService;
```

## 4. 数据库规范

### 4.1 表命名
- 格式: `模块前缀_表名`
- 示例: `sys_user`, `sys_role`, `sys_menu`
- 使用小写字母，单词间用下划线分隔

### 4.2 字段命名
- 使用小写字母，单词间用下划线分隔
- 主键: `{表名}_id` (如 `user_id`, `role_id`)
- 外键: `关联表名_id` (如 `dept_id`, `role_id`)
- 时间字段: `{描述}_time` (如 `create_time`, `update_time`)
- 状态字段: `status`
- 删除标志: `del_flag`

### 4.3 公共字段
每个表必须包含以下公共字段:
```sql
create_by varchar(64) comment '创建者',
create_time datetime comment '创建时间',
update_by varchar(64) comment '更新者',
update_time datetime comment '更新时间',
remark varchar(500) comment '备注',
del_flag char(1) default '0' comment '删除标志(0=存在,2=删除)'
```

### 4.4 删除操作
- **必须使用逻辑删除**: 设置 `del_flag = '2'`
- **严禁物理删除**: 不允许使用 `DELETE FROM` 语句
- 查询时必须添加 `del_flag = '0'` 条件

## 5. 异常处理规范

### 5.1 异常类型
- **业务异常**: 使用 `TWTException`
- **系统异常**: 使用 Spring 标准异常

### 5.2 异常处理原则
- **严禁捕获异常后不做任何处理**
- 捕获异常后必须记录日志或重新抛出
- 业务异常必须提供清晰的错误信息

### 5.3 异常示例
```java
// 正确示例
if (Objects.isNull(user)) {
    throw new TWTException("用户不存在");
}

// 错误示例 - 严禁这样做
try {
    // 业务逻辑
} catch (Exception e) {
    // 什么都不做 - 严禁！
}
```

## 6. 注解使用规范

### 6.1 必须使用的注解

| 注解 | 使用位置 | 说明 |
|------|----------|------|
| `@RestController` | Controller 类 | 标识 RESTful 控制器 |
| `@RequestMapping` | Controller 类/方法 | 映射请求路径 |
| `@Service` | Service 实现类 | 标识服务类 |
| `@Autowired` | 字段 | 依赖注入 |
| `@Transactional` | Service 方法 | 事务控制 |
| `@PreAuthorize` | Controller 方法 | 权限控制 |
| `@Log` | Controller 方法 | 操作日志 |
| `@Validated` | Controller 方法参数 | 参数校验 |
| `@Schema` | 实体类/字段 | API 文档 |
| `@Operation` | Controller 方法 | API 文档 |
| `@Tag` | Controller 类 | API 文档 |

### 6.2 参数校验注解
```java
@NotBlank(message = "用户名不能为空")
@Size(max = 30, message = "用户名长度不能超过30个字符")
private String username;

@Email(message = "邮箱格式不正确")
@Size(max = 50, message = "邮箱长度不能超过50个字符")
private String email;

@NotNull(message = "状态不能为空")
private String status;
```

## 7. 事务管理规范

### 7.1 事务注解
```java
@Override
@Transactional(rollbackFor = TWTException.class)
public int insertUser(SysUser user) {
    // 业务逻辑
    return userMapper.insertUser(user);
}
```

### 7.2 事务原则
- 所有涉及数据库写操作的 Service 方法必须添加 `@Transactional`
- 指定 `rollbackFor = TWTException.class`
- 事务方法内不要捕获异常，让异常向上抛出以触发回滚

## 8. 数据权限规范

### 8.1 数据权限注解
```java
@Override
@MicroDataScope(deptIdField = "d.dept_id", userIdField = "u.user_id")
public List<SysUser> selectUserList(SysUser user) {
    return userMapper.selectUserList(user);
}
```

### 8.2 数据权限原则
- 查询列表方法必须添加 `@MicroDataScope` 注解
- 指定部门字段和用户字段
- 系统会自动注入数据权限过滤条件

## 9. 日志规范

### 9.1 日志级别
- **ERROR**: 系统错误，需要立即处理
- **WARN**: 警告信息，可能存在问题
- **INFO**: 重要的业务流程信息
- **DEBUG**: 调试信息

### 9.2 日志示例
```java
private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

// 业务日志
log.info("新增用户: {}", user.getUsername());

// 错误日志
log.error("用户导入失败", e);
```

## 10. 缓存使用规范

### 10.1 缓存注解
```java
@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#user.username")
public int updateUser(SysUser user) {
    // 更新用户时清除缓存
    return userMapper.updateUser(user);
}
```

### 10.2 缓存原则
- 频繁查询且不常变化的数据使用缓存
- 更新数据时必须清除相关缓存
- 缓存 key 必须使用常量定义

## 11. 配置文件规范

### 11.1 application.yml 结构
```yaml
server:
  port: 8082  # 端口号

spring:
  application:
    name: dataself-server-system  # 应用名称
  profiles:
    active: dev  # 环境配置
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848  # Nacos 地址
      config:
        server-addr: ${spring.cloud.nacos.discovery.server-addr}
        file-extension: yml
```

### 11.2 配置原则
- 敏感信息不要写在配置文件中
- 使用 Nacos 配置中心管理配置
- 不同环境使用不同的配置文件

## 12. 代码注释规范

### 12.1 类注释
```java
/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 用户信息管理
 */
```

### 12.2 方法注释
```java
/**
 * 根据条件分页查询用户列表
 * @param user 用户信息
 * @return 用户信息集合
 */
```

### 12.3 字段注释
```java
/**
 * 用户ID
 */
@Schema(description = "用户ID")
private Long userId;
```

## 13. 代码质量要求

### 13.1 代码规范
- 遵循阿里巴巴 Java 开发手册
- 使用 IDEA 代码格式化工具
- 避免魔法值，使用常量定义
- 避免过长的方法，单个方法不超过 80 行

### 13.2 代码审查
- 提交代码前必须自测
- 确保没有编译错误和警告
- 确保没有 Lint 错误
- 代码必须经过 Code Review

## 14. 安全规范

### 14.1 SQL 注入防护
- 使用 MyBatis 的 `#{}` 而不是 `${}`
- 动态 SQL 必须做好参数校验

### 14.2 XSS 防护
- 前端输入必须做转义处理
- 富文本内容必须做 XSS 过滤

### 14.3 权限控制
- 所有接口必须添加权限控制
- 使用 `@PreAuthorize` 注解
- 权限标识格式: `模块:实体:操作`

## 15. 性能优化规范

### 15.1 数据库优化
- 避免 N+1 查询
- 合理使用索引
- 避免全表扫描
- 分页查询必须使用 `PageUtils.startPage()`

### 15.2 代码优化
- 避免在循环中查询数据库
- 使用批量操作代替单条操作
- 合理使用缓存
- 避免创建不必要的对象