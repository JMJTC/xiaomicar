CREATE DATABASE  IF NOT EXISTS `xiaomicar` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `xiaomicar`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: xiaomicar
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `battery_signal`
--

DROP TABLE IF EXISTS `battery_signal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `battery_signal` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `vehicle_id` bigint NOT NULL COMMENT '车辆ID',
  `signal_time` datetime NOT NULL COMMENT '信号时间',
  `mx` decimal(5,2) DEFAULT NULL COMMENT '最高电压',
  `mi` decimal(5,2) DEFAULT NULL COMMENT '最低电压',
  `ix` decimal(5,2) DEFAULT NULL COMMENT '最高电流',
  `ii` decimal(5,2) DEFAULT NULL COMMENT '最低电流',
  `soc` decimal(5,2) DEFAULT NULL COMMENT '电池荷电状态',
  `soh` decimal(5,2) DEFAULT NULL COMMENT '电池健康状态',
  `temperature` decimal(5,2) DEFAULT NULL COMMENT '电池温度',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_vehicle_time` (`vehicle_id`,`signal_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='电池信号记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `battery_signal`
--

LOCK TABLES `battery_signal` WRITE;
/*!40000 ALTER TABLE `battery_signal` DISABLE KEYS */;
/*!40000 ALTER TABLE `battery_signal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle_info`
--

DROP TABLE IF EXISTS `vehicle_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `vid` varchar(16) NOT NULL COMMENT '车辆唯一标识',
  `frame_number` varchar(32) NOT NULL COMMENT '车架编号',
  `battery_type` enum('ternary','lfp') NOT NULL COMMENT '电池类型：ternary(三元电池)、lfp(铁锂电池)',
  `total_mileage` decimal(10,2) DEFAULT '0.00' COMMENT '总里程(km)',
  `battery_health` tinyint DEFAULT '100' COMMENT '电池健康状态(%)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_vid` (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='车辆信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle_info`
--

LOCK TABLES `vehicle_info` WRITE;
/*!40000 ALTER TABLE `vehicle_info` DISABLE KEYS */;
INSERT INTO `vehicle_info` VALUES (1,'V1D16Char12345','1','ternary',100.00,100,'2025-05-18 01:25:23','2025-05-18 01:25:23'),(2,'V1DAnother16','2','lfp',600.00,95,'2025-05-18 01:25:23','2025-05-18 01:25:23'),(3,'V1DThird16Char','3','ternary',300.00,98,'2025-05-18 01:25:23','2025-05-18 01:25:23');
/*!40000 ALTER TABLE `vehicle_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warning_info`
--

DROP TABLE IF EXISTS `warning_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warning_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `vehicle_id` bigint NOT NULL COMMENT '车辆ID',
  `rule_id` bigint NOT NULL COMMENT '规则ID',
  `warning_level` tinyint NOT NULL COMMENT '预警等级',
  `warning_message` varchar(256) NOT NULL COMMENT '预警消息',
  `warning_time` datetime NOT NULL COMMENT '预警时间',
  `is_handled` tinyint(1) DEFAULT '0' COMMENT '是否处理',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `handle_result` varchar(256) DEFAULT NULL COMMENT '处理结果',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_vehicle_time` (`vehicle_id`,`warning_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='预警信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warning_info`
--

LOCK TABLES `warning_info` WRITE;
/*!40000 ALTER TABLE `warning_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `warning_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warning_rule`
--

DROP TABLE IF EXISTS `warning_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warning_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `rule_code` varchar(32) NOT NULL COMMENT '规则编号',
  `rule_name` varchar(64) NOT NULL COMMENT '规则名称',
  `battery_type` enum('ternary','lfp','all') NOT NULL COMMENT '适用电池类型',
  `rule_expression` text NOT NULL COMMENT '规则表达式',
  `warning_level` tinyint NOT NULL COMMENT '预警等级(0-5)',
  `warning_message` varchar(256) NOT NULL COMMENT '预警消息',
  `is_enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_rule_code_battery` (`rule_code`,`battery_type`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='预警规则表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warning_rule`
--

LOCK TABLES `warning_rule` WRITE;
/*!40000 ALTER TABLE `warning_rule` DISABLE KEYS */;
INSERT INTO `warning_rule` VALUES (4,'1','电压差报警','ternary','5<=(Mx - Mi),报警等级：0;3<=(Mx - Mi)<5,报警等级：1;1<=(Mx - Mi)<3,报警等级：2;0.6<=(Mx - Mi)<1,报警等级：3;0.2<=(Mx - Mi)<0.6,报警等级：4;(Mx - Mi)<0.2, 不报警',0,'电压差报警',1,'2025-05-18 01:32:45','2025-05-18 01:32:45'),(5,'1','电压差报警','lfp','2<=(Mx - Mi),报警等级：0;1<=(Mx - Mi)<2,报警等级：1;0.7<=(Mx - Mi)<1,报警等级：2;0.4<=(Mx - Mi)<0.7,报警等级：3;0.2<=(Mx - Mi)<0.4,报警等级：4;(Mx - Mi)<0.2, 不报警',0,'电压差报警',1,'2025-05-18 01:32:45','2025-05-18 01:32:45'),(6,'2','电流差报警','ternary','3<=(Ix - Ii),报警等级：0;1<=(Ix - Ii)<3,报警等级：1;0.2<=(Ix - Ii)<1,报警等级：2;(Ix - Ii)<0.2, 不报警',0,'电流差报警',1,'2025-05-18 01:32:45','2025-05-18 01:32:45'),(7,'2','电流差报警','lfp','1<=(Ix - Ii),报警等级：0;0.5<=(Ix - Ii)<1,报警等级：1;0.2<=(Ix - Ii)<0.5,报警等级：2;(Ix - Ii)<0.2, 不报警',0,'电流差报警',1,'2025-05-18 01:32:45','2025-05-18 01:32:45');
/*!40000 ALTER TABLE `warning_rule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-18  1:37:26
