Github: [JMJTC/xiaomicar](https://github.com/JMJTC/xiaomicar)

文档链接：[‌‬﻿‬‍﻿⁠‍‍﻿﻿‌‌‬﻿⁠‬‬‌‬﻿‌⁠‍‬‬‌README - 飞书云文档](https://wayawbott0.f.mioffice.cn/docx/doxk4t0aDwTAmOgj6LtLx0bqk2L)

### 系统设计

#### 系统概述

该系统为小米汽车相关的管理系统，主要负责处理车辆信息、电池信号记录、预警规则以及预警信息等业务。系统包含数据存储、业务逻辑处理和前端交互等部分，通过 Spring Boot 框架搭建，结合 MyBatis-Plus 进行数据库操作，Redis 用于缓存，RocketMQ  用于消息队列。

#### 系统架构

- **表现层（Controller）**：负责接收前端请求，处理请求参数，调用服务层的方法进行业务处理，并将处理结果返回给前端。
- **服务层（Service）**：实现具体的业务逻辑，调用数据访问层的方法进行数据操作，同时处理缓存和消息队列相关的业务。
- **数据访问层（Mapper）**：负责与数据库进行交互，使用 MyBatis-Plus 提供的基础方法或自定义 SQL 语句进行数据的增删改查操作。
- **数据库（Database）**：使用 MySQL 存储车辆信息、电池信号记录、预警规则和预警信息等数据。
- **缓存（Redis）**：用于缓存预警信息，提高系统的响应速度。
- **消息队列（RocketMQ/RabbitMQ）**：用于发送预警消息，实现系统的异步处理。

#### 系统模块划分

- **车辆信息管理模块**：负责车辆信息的增删改查操作。
- **电池信号管理模块**：负责电池信号记录的保存和查询操作。
- **预警规则管理模块**：负责预警规则的配置和管理。
- **预警信息管理模块**：负责预警信息的生成、处理和查询操作。

### 数据库表设计

#### 车辆信息表（vehicle_info）

| 字段名         | 类型                  | 描述                                         |
| -------------- | --------------------- | -------------------------------------------- |
| id             | bigint                | 自增主键                                     |
| vid            | varchar(16)           | 车辆唯一标识                                 |
| frame_number   | varchar(32)           | 车架编号                                     |
| battery_type   | enum('ternary','lfp') | 电池类型：ternary (三元电池)、lfp (铁锂电池) |
| total_mileage  | decimal(10,2)         | 总里程 (km)                                  |
| battery_health | tinyint               | 电池健康状态 (%)                             |
| create_time    | datetime              | 创建时间                                     |
| update_time    | datetime              | 更新时间                                     |

#### 电池信号记录表（battery_signal）

| 字段名      | 类型         | 描述         |
| ----------- | ------------ | ------------ |
| id          | bigint       | 自增主键     |
| vehicle_id  | bigint       | 车辆 ID      |
| signal_time | datetime     | 信号时间     |
| mx          | decimal(5,2) | 最高电压     |
| mi          | decimal(5,2) | 最低电压     |
| ix          | decimal(5,2) | 最高电流     |
| ii          | decimal(5,2) | 最低电流     |
| soc         | decimal(5,2) | 电池荷电状态 |
| soh         | decimal(5,2) | 电池健康状态 |
| temperature | decimal(5,2) | 电池温度     |
| create_time | datetime     | 创建时间     |

#### 预警规则表（warning_rule）

| 字段名          | 类型                        | 描述             |
| --------------- | --------------------------- | ---------------- |
| id              | bigint                      | 自增主键         |
| rule_code       | varchar(32)                 | 规则编号         |
| rule_name       | varchar(64)                 | 规则名称         |
| battery_type    | enum('ternary','lfp','all') | 适用电池类型     |
| rule_expression | text                        | 规则表达式       |
| warning_level   | tinyint                     | 预警等级 (0 - 5) |
| warning_message | varchar(256)                | 预警消息         |
| is_enabled      | tinyint(1)                  | 是否启用         |
| create_time     | datetime                    | 创建时间         |
| update_time     | datetime                    | 更新时间         |

#### 预警信息表（warning_info）

| 字段名          | 类型         | 描述     |
| --------------- | ------------ | -------- |
| id              | bigint       | 自增主键 |
| vehicle_id      | bigint       | 车辆 ID  |
| rule_id         | bigint       | 规则 ID  |
| warning_level   | tinyint      | 预警等级 |
| warning_message | varchar(256) | 预警消息 |
| warning_time    | datetime     | 预警时间 |
| is_handled      | tinyint(1)   | 是否处理 |
| handle_time     | datetime     | 处理时间 |
| handle_result   | varchar(256) | 处理结果 |
| create_time     | datetime     | 创建时间 |

### 接口设计

程序运行后可在http://localhost:8090/swagger-ui.html查看接口

![image-20250519134537892](https://raw.githubusercontent.com/JMJTC/cloudimg/main/picgoAndGitHub/image-20250519134537892.png)

![image-20250519134706244](https://raw.githubusercontent.com/JMJTC/cloudimg/main/picgoAndGitHub/image-20250519134706244.png)

![image-20250519134742862](https://raw.githubusercontent.com/JMJTC/cloudimg/main/picgoAndGitHub/image-20250519134742862.png)

![image-20250519134758657](https://raw.githubusercontent.com/JMJTC/cloudimg/main/picgoAndGitHub/image-20250519134758657.png)

![image-20250519134814081](https://raw.githubusercontent.com/JMJTC/cloudimg/main/picgoAndGitHub/image-20250519134814081.png)





![image-20250519134842282](https://raw.githubusercontent.com/JMJTC/cloudimg/main/picgoAndGitHub/image-20250519134842282.png)

![image-20250519134906582](https://raw.githubusercontent.com/JMJTC/cloudimg/main/picgoAndGitHub/image-20250519134906582.png)

![image-20250519134935610](https://raw.githubusercontent.com/JMJTC/cloudimg/main/picgoAndGitHub/image-20250519134935610.png)

![image-20250519135010193](https://raw.githubusercontent.com/JMJTC/cloudimg/main/picgoAndGitHub/image-20250519135010193.png)

![image-20250519135034542](https://raw.githubusercontent.com/JMJTC/cloudimg/main/picgoAndGitHub/image-20250519135034542.png)

![image-20250519135309535](https://raw.githubusercontent.com/JMJTC/cloudimg/main/picgoAndGitHub/image-20250519135309535.png)

