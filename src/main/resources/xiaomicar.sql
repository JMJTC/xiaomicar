-- 创建模式 xiaomicar，若不存在则创建，指定字符集为 utf8mb4（支持更广泛字符，如 emoji），排序规则为 utf8mb4_unicode_ci
CREATE SCHEMA IF NOT EXISTS xiaomicar
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 使用 xiaomicar 模式，后续表结构将在此模式下创建
USE
xiaomicar;

-- 车辆信息表
CREATE TABLE vehicle_info
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
    vid            VARCHAR(16) NOT NULL COMMENT '车辆唯一标识',
    frame_number   VARCHAR(32) NOT NULL COMMENT '车架编号',
    battery_type   ENUM('ternary', 'lfp') NOT NULL COMMENT '电池类型：ternary(三元电池)、lfp(铁锂电池)',
    total_mileage  DECIMAL(10, 2) DEFAULT 0 COMMENT '总里程(km)',
    battery_health TINYINT        DEFAULT 100 COMMENT '电池健康状态(%)',
    create_time    DATETIME       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time    DATETIME       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_vid (vid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆信息表';

-- 电池信号记录表
CREATE TABLE battery_signal
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
    vehicle_id  BIGINT   NOT NULL COMMENT '车辆ID',
    signal_time DATETIME NOT NULL COMMENT '信号时间',
    mx          DECIMAL(5, 2) COMMENT '最高电压',
    mi          DECIMAL(5, 2) COMMENT '最低电压',
    ix          DECIMAL(5, 2) COMMENT '最高电流',
    ii          DECIMAL(5, 2) COMMENT '最低电流',
    soc         DECIMAL(5, 2) COMMENT '电池荷电状态',
    soh         DECIMAL(5, 2) COMMENT '电池健康状态',
    temperature DECIMAL(5, 2) COMMENT '电池温度',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX       idx_vehicle_time (vehicle_id, signal_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电池信号记录表';

-- 预警规则表
CREATE TABLE warning_rule
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
    rule_code       VARCHAR(32)  NOT NULL COMMENT '规则编号',
    rule_name       VARCHAR(64)  NOT NULL COMMENT '规则名称',
    battery_type    ENUM('ternary', 'lfp', 'all') NOT NULL COMMENT '适用电池类型',
    rule_expression TEXT         NOT NULL COMMENT '规则表达式',
    warning_level   TINYINT      NOT NULL COMMENT '预警等级(0-5)',
    warning_message VARCHAR(256) NOT NULL COMMENT '预警消息',
    is_enabled      TINYINT(1) DEFAULT 1 COMMENT '是否启用',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_rule_code (rule_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预警规则表';

-- 预警信息表
CREATE TABLE warning_info
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
    vehicle_id      BIGINT       NOT NULL COMMENT '车辆ID',
    rule_id         BIGINT       NOT NULL COMMENT '规则ID',
    warning_level   TINYINT      NOT NULL COMMENT '预警等级',
    warning_message VARCHAR(256) NOT NULL COMMENT '预警消息',
    warning_time    DATETIME     NOT NULL COMMENT '预警时间',
    is_handled      TINYINT(1) DEFAULT 0 COMMENT '是否处理',
    handle_time     DATETIME COMMENT '处理时间',
    handle_result   VARCHAR(256) COMMENT '处理结果',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX           idx_vehicle_time (vehicle_id, warning_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预警信息表';