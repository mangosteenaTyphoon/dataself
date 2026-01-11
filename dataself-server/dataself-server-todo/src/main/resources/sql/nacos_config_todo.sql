-- --------------------------------------------------------
-- Nacos 配置中心 - OKR-Todo 模块配置
-- --------------------------------------------------------
-- 说明：
-- 1. 此 SQL 用于在 Nacos 配置中心添加 dataself-server-todo 模块的配置
-- 2. 执行前请确保 Nacos 数据库已创建
-- 3. 配置的 namespace 为 dev（开发环境）
-- 4. 配置的 group 为 DEFAULT_GROUP
-- --------------------------------------------------------

USE nacos;

-- 插入 OKR-Todo 模块配置
INSERT INTO config_info (
    data_id, 
    group_id, 
    content, 
    md5, 
    gmt_create, 
    gmt_modified, 
    src_user, 
    src_ip, 
    app_name, 
    tenant_id, 
    c_desc, 
    c_use, 
    effect, 
    type, 
    c_schema
) VALUES (
    'dataself-server-todo-dev.yml',
    'DEFAULT_GROUP',
    '# Spring
spring: 
  datasource:
    dynamic:
      hikari:
        # 连接测试查询
        connection-test-query: SELECT 1 FROM DUAL
        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短
        max-lifetime: 540000
        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放
        idle-timeout: 500000
        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size
        minimum-idle: 10
        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值
        maximum-pool-size: 12
        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒
        connection-timeout: 60000
      # 设置默认的数据源或者数据源组,默认值即为master
      primary: master 
      datasource:
        # 主库数据源
        master:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://127.0.0.1:3306/dataself?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8
          username: root
          password: password
        # 从库数据源（可选）
        # slave:
        #   driver-class-name: com.mysql.cj.jdbc.Driver
        #   url: jdbc:mysql://127.0.0.1:3306/dataself?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8
        #   username: root
        #   password: password
      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭
      seata: false

# Swagger 配置
swagger:
  title: DataSelf Swagger API
  version: 1.0.0
  description: OKR-Todo 目标与任务管理服务
  license: Powered By DataSelf
  licenseUrl: https://dataself.cn
  terms-of-service-url: https://dataself.cn
  contact:
    name: DataSelf
    email: 2471835953@qq.com
    url: https://dataself.cn
  authorization:
    name: password
    token-url-list:
      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:8080}/auth/oauth/token

# 开启Swagger增强模式
knife4j:
  enable: true

# OKR-Todo 业务配置
todo:
  # 目标配置
  goal:
    # 目标得分计算权重配置
    score:
      # 任务质量得分权重（默认70%）
      quality-weight: 0.7
      # 时间完成得分权重（默认30%）
      time-weight: 0.3
    # 时间完成得分规则
    time-score:
      # 提前完成
      advance: 100
      # 按时完成
      on-time: 90
      # 逾期1-3天
      overdue-1-3: 80
      # 逾期4-7天
      overdue-4-7: 70
      # 逾期8-14天
      overdue-8-14: 60
      # 逾期15天以上
      overdue-15-plus: 50
  # 任务配置
  task:
    # 任务评分映射
    score:
      # 优秀
      excellent: 95
      # 良好
      good: 85
      # 合格
      qualified: 75
      # 差
      poor: 60',
    MD5('# Spring
spring: 
  datasource:
    dynamic:
      hikari:
        # 连接测试查询
        connection-test-query: SELECT 1 FROM DUAL
        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短
        max-lifetime: 540000
        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放
        idle-timeout: 500000
        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size
        minimum-idle: 10
        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值
        maximum-pool-size: 12
        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒
        connection-timeout: 60000
      # 设置默认的数据源或者数据源组,默认值即为master
      primary: master 
      datasource:
        # 主库数据源
        master:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://127.0.0.1:3306/dataself?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8
          username: root
          password: password
        # 从库数据源（可选）
        # slave:
        #   driver-class-name: com.mysql.cj.jdbc.Driver
        #   url: jdbc:mysql://127.0.0.1:3306/dataself?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8
        #   username: root
        #   password: password
      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭
      seata: false

# Swagger 配置
swagger:
  title: DataSelf Swagger API
  version: 1.0.0
  description: OKR-Todo 目标与任务管理服务
  license: Powered By DataSelf
  licenseUrl: https://dataself.cn
  terms-of-service-url: https://dataself.cn
  contact:
    name: DataSelf
    email: 2471835953@qq.com
    url: https://dataself.cn
  authorization:
    name: password
    token-url-list:
      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:8080}/auth/oauth/token

# 开启Swagger增强模式
knife4j:
  enable: true

# OKR-Todo 业务配置
todo:
  # 目标配置
  goal:
    # 目标得分计算权重配置
    score:
      # 任务质量得分权重（默认70%）
      quality-weight: 0.7
      # 时间完成得分权重（默认30%）
      time-weight: 0.3
    # 时间完成得分规则
    time-score:
      # 提前完成
      advance: 100
      # 按时完成
      on-time: 90
      # 逾期1-3天
      overdue-1-3: 80
      # 逾期4-7天
      overdue-4-7: 70
      # 逾期8-14天
      overdue-8-14: 60
      # 逾期15天以上
      overdue-15-plus: 50
  # 任务配置
  task:
    # 任务评分映射
    score:
      # 优秀
      excellent: 95
      # 良好
      good: 85
      # 合格
      qualified: 75
      # 差
      poor: 60'),
    NOW(),
    NOW(),
    'nacos',
    '127.0.0.1',
    '',
    'dev',
    'OKR-Todo 目标与任务管理服务配置',
    '',
    '',
    'yaml',
    ''
);

-- 更新网关路由配置（添加 todo 模块路由）
-- 注意：此部分需要手动更新 dataself-gateway-dev.yml 配置，在 routes 中添加以下内容：
-- 
-- # OKR-Todo 模块
-- - id: dataself-server-todo
--   uri: lb://dataself-server-todo
--   predicates:
--     - Path=/todo/**
--   filters:
--     - StripPrefix=1

-- --------------------------------------------------------
-- 使用说明
-- --------------------------------------------------------
-- 1. 执行此 SQL 后，需要在 Nacos 控制台验证配置是否添加成功
-- 2. 访问 Nacos 控制台：http://localhost:8848/nacos
-- 3. 登录后进入"配置管理" -> "配置列表"
-- 4. 在 dev 命名空间下查看 dataself-server-todo-dev.yml 配置
-- 5. 根据实际环境修改数据库连接信息
-- 6. 手动更新网关配置，添加 todo 模块的路由规则
-- --------------------------------------------------------
