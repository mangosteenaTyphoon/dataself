# DataSelf - 数据驱动自我系统

<div align="center">

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-green.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

**一个基于数据驱动的自我管理与分析系统**

[特性](#特性) • [技术栈](#技术栈) • [快速开始](#快速开始) • [项目结构](#项目结构) • [开发指南](#开发指南)

</div>

---

## 📖 项目简介

DataSelf 是一个现代化的数据驱动自我管理系统，旨在通过数据采集、分析和可视化，帮助用户更好地了解和管理自己的生活、工作和学习。系统采用微服务架构，提供高可用、高性能、易扩展的解决方案。

### 🎯 核心理念

- **数据驱动决策**：通过数据分析，为个人成长提供科学依据
- **智能化分析**：集成 AI 能力，提供智能化的数据洞察
- **可视化呈现**：直观的数据可视化，让数据说话
- **隐私保护**：本地化部署，数据完全由用户掌控

### ✨ 特性

- 🚀 **微服务架构**：基于 Spring Cloud Alibaba 的分布式微服务架构
- 🤖 **AI 集成**：集成 Spring AI 和阿里云 AI，提供智能化数据分析
- 🔐 **安全可靠**：完善的认证授权机制，保障数据安全
- 📊 **数据可视化**：丰富的数据展示和分析能力
- 🔄 **高可用**：支持服务注册发现、负载均衡、熔断降级
- 📝 **代码生成**：内置代码生成器，提高开发效率
- 📁 **文件管理**：分布式文件存储服务
- ⏰ **任务调度**：集成 XXL-Job 分布式任务调度
- 📱 **实时通信**：WebSocket 支持实时数据推送

## 🛠 技术栈

### 后端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.2.0 | 基础框架 |
| Spring Cloud | 2023.0.0 | 微服务框架 |
| Spring Cloud Alibaba | 2023.0.1.2 | 微服务组件 |
| Spring AI | 1.0.1 | AI 集成框架 |
| Nacos | 2.3.2 | 服务注册与配置中心 |
| Sentinel | 1.8.8 | 流量控制与熔断降级 |
| Gateway | 4.1.0 | 微服务网关 |
| MyBatis | 3.0.3 | ORM 框架 |
| Redis | - | 分布式缓存 |
| MySQL | - | 关系型数据库 |
| XXL-Job | 2.4.1 | 分布式任务调度 |
| Knife4j | 4.5.0 | API 文档 |
| Hutool | 5.8.31 | Java 工具类库 |

### 核心组件

- **认证授权**：Spring Security + JWT
- **分布式锁**：Redisson
- **数据源管理**：Dynamic DataSource
- **分库分表**：ShardingSphere
- **对象存储**：OSS 统一封装
- **Excel 处理**：EasyExcel
- **敏感词过滤**：Sensitive Word
- **ID 生成器**：雪花算法

## 📁 项目结构

```
dataself/
├── dataself-api/              # API 模块
│   ├── dataself-api-ai/       # AI 服务 API
│   ├── dataself-api-auth/     # 认证服务 API
│   ├── dataself-api-dfs/      # 文件服务 API
│   ├── dataself-api-gen/      # 代码生成 API
│   ├── dataself-api-job/      # 任务调度 API
│   └── dataself-api-system/   # 系统服务 API
├── dataself-auth/             # 认证服务
├── dataself-framework/        # 框架核心模块
│   ├── dataself-framework-core/        # 核心工具
│   ├── dataself-framework-datasource/  # 数据源管理
│   ├── dataself-framework-datascope/   # 数据权限
│   ├── dataself-framework-jdbc/        # JDBC 封装
│   ├── dataself-framework-job/         # 任务调度
│   ├── dataself-framework-log/         # 日志管理
│   ├── dataself-framework-openfeign/   # 远程调用
│   ├── dataself-framework-redis/       # Redis 缓存
│   ├── dataself-framework-security/    # 安全配置
│   ├── dataself-framework-snowflake/   # ID 生成器
│   ├── dataself-framework-swagger/     # API 文档
│   ├── dataself-framework-utils/       # 工具类
│   └── dataself-framework-websocket/   # WebSocket
├── dataself-gateway/          # 网关服务
├── dataself-server/           # 业务服务模块
│   ├── dataself-server-ai/    # AI 服务
│   ├── dataself-server-dfs/   # 文件服务
│   ├── dataself-server-gen/   # 代码生成服务
│   ├── dataself-server-job/   # 任务调度服务
│   └── dataself-server-system/# 系统服务
└── dataself-visual/           # 可视化监控模块
    ├── dataself-visual-monitor/   # 服务监控
    └── dataself-visual-sentinel/  # 流量监控
```

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+
- Nacos 2.3.2+

### 安装步骤

1. **克隆项目**

```bash
git clone https://github.com/yourusername/dataself.git
cd dataself
```

2. **配置 Nacos**

启动 Nacos 服务器，并在 Nacos 中创建相应的配置文件。

3. **配置数据库**

创建数据库并执行初始化脚本（待添加）。

4. **修改配置**

根据实际环境修改 `application.yml` 中的配置：
- Nacos 地址
- 数据库连接
- Redis 连接

5. **编译项目**

```bash
mvn clean install -DskipTests
```

6. **启动服务**

按以下顺序启动服务：
```bash
# 1. 启动认证服务
cd dataself-auth
mvn spring-boot:run

# 2. 启动网关服务
cd dataself-gateway
mvn spring-boot:run

# 3. 启动业务服务
cd dataself-server/dataself-server-system
mvn spring-boot:run

# 4. 启动其他服务...
```

7. **访问系统**

- 网关地址：http://localhost:8080
- API 文档：http://localhost:8080/doc.html
- 监控中心：http://localhost:9090

## 📝 开发指南

### 环境配置

项目支持多环境配置：

- **dev**：开发环境（默认）
- **test**：测试环境
- **staging**：预发布环境
- **product**：生产环境

切换环境：
```bash
mvn clean package -P test
```

### 代码规范

项目使用 Spring Java Format 进行代码格式化：

```bash
# 格式化代码
mvn spring-javaformat:apply

# 检查代码格式
mvn spring-javaformat:validate
```

### API 文档

启动服务后访问 Knife4j 文档：
```
http://localhost:{port}/doc.html
```

## 🔧 配置说明

### Nacos 配置

在 Nacos 中需要创建以下配置文件：

- `dataself-app-{profile}.yml`：应用公共配置
- `dataself-{service}-{profile}.yml`：各服务独立配置

### 日志配置

日志文件存放在 `logs/{应用名称}/` 目录下：
- `debug.log`：调试日志
- `error.log`：错误日志

日志保留 30 天，单文件最大 50MB。

## 📊 监控

### Spring Boot Admin

访问 http://localhost:9090 查看服务监控信息。

### Sentinel Dashboard

访问 http://localhost:8858 查看流量控制和熔断降级配置。

## 🤝 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 📄 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。

## 📮 联系方式

- 项目地址：[https://github.com/yourusername/dataself](https://github.com/yourusername/dataself)
- 问题反馈：[Issues](https://github.com/yourusername/dataself/issues)

## 🙏 致谢

感谢以下开源项目：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Cloud Alibaba](https://github.com/alibaba/spring-cloud-alibaba)
- [MyBatis](https://mybatis.org/)
- [Hutool](https://hutool.cn/)

---

<div align="center">

**⭐ 如果这个项目对你有帮助，请给一个 Star！⭐**

Made with ❤️ by DataSelf Team

</div>
