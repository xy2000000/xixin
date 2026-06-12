mysqldump: [Warning] Using a password on the command line interface can be insecure.
-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: xixin_user
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `xixin_user`
--

/*!40000 DROP DATABASE IF EXISTS `xixin_user`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `xixin_user` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `xixin_user`;

--
-- Table structure for table `t_doctor`
--

DROP TABLE IF EXISTS `t_doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_doctor` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '鍏宠仈鐢ㄦ埛ID',
  `package_id` bigint(20) unsigned DEFAULT NULL COMMENT '负责的体检套餐ID',
  `department` varchar(64) DEFAULT NULL COMMENT '绉戝?',
  `title` varchar(32) DEFAULT NULL COMMENT '鑱岀О',
  `specialty` varchar(256) DEFAULT NULL COMMENT '涓撻暱',
  `introduction` text COMMENT '绠?粙',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='鍖荤敓妗ｆ?琛';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_doctor`
--

LOCK TABLES `t_doctor` WRITE;
/*!40000 ALTER TABLE `t_doctor` DISABLE KEYS */;
INSERT INTO `t_doctor` VALUES (1,1,1,'全科医学科','主任医师','心血管疾病','从事内科临床工作逾25年，擅长冠心病、高血压、心力衰竭的诊治','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(2,3,2,'呼吸内科','副主任医师','呼吸系统疾病','专长：慢性阻塞性肺疾病、支气管哮喘、肺部感染性疾病','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(3,9,3,'放射影像科','主治医师','医学影像诊断','CT/磁共振影像诊断专家，擅长肿瘤影像学评估','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0);
/*!40000 ALTER TABLE `t_doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_patient`
--

DROP TABLE IF EXISTS `t_patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_patient` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '鍏宠仈鐢ㄦ埛ID',
  `name` varchar(64) DEFAULT NULL COMMENT '濮撳悕',
  `gender` tinyint(1) DEFAULT NULL COMMENT '鎬у埆: 0濂?1鐢',
  `birthday` date DEFAULT NULL COMMENT '鍑虹敓鏃ユ湡',
  `id_card` varchar(18) DEFAULT NULL COMMENT '韬?唤璇佸彿',
  `address` varchar(256) DEFAULT NULL COMMENT '鍦板潃',
  `emergency_contact` varchar(64) DEFAULT NULL COMMENT '绱ф?鑱旂郴浜',
  `emergency_phone` varchar(20) DEFAULT NULL COMMENT '绱ф?鑱旂郴鐢佃瘽',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='鎮ｈ?妗ｆ?琛';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_patient`
--

LOCK TABLES `t_patient` WRITE;
/*!40000 ALTER TABLE `t_patient` DISABLE KEYS */;
INSERT INTO `t_patient` VALUES (1,2,'测试患者甲',1,'1990-03-15','110101199003150011','北京市海淀区中关村大街1号','张父','13900001001','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(2,5,'王患者乙',1,'1985-07-22','310101198507220012','上海市浦东新区世纪大道100号','王母','13900001002','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(3,6,'李患者丙',0,'1992-11-08','440101199211080013','广州市天河区天河路200号','李丈夫','13900001003','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0);
/*!40000 ALTER TABLE `t_patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '鐢ㄦ埛鍚',
  `password` varchar(128) NOT NULL COMMENT '瀵嗙爜(MD5)',
  `real_name` varchar(64) DEFAULT NULL COMMENT '鐪熷疄濮撳悕',
  `phone` varchar(20) DEFAULT NULL COMMENT '鎵嬫満鍙',
  `email` varchar(128) DEFAULT NULL COMMENT '閭??',
  `avatar` varchar(256) DEFAULT NULL COMMENT '澶村儚URL',
  `role` varchar(32) NOT NULL DEFAULT 'PATIENT' COMMENT '瑙掕壊: ADMIN/DOCTOR/PATIENT',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '鐘舵?: 0绂佺敤 1鍚?敤',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '鏇存柊鏃堕棿',
  `create_by` bigint(20) unsigned DEFAULT NULL COMMENT '鍒涘缓浜',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '鏇存柊浜',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '閫昏緫鍒犻櫎: 0鏈?垹闄?1宸插垹闄',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='鐢ㄦ埛琛';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'admin','123456','系统管理员',NULL,NULL,NULL,'ADMIN',1,'2026-06-02 19:59:01','2026-06-04 10:35:23',NULL,NULL,0),(2,'testuser01','123456','测试患者甲','13911111111','test01@test.com',NULL,'PATIENT',1,'2026-06-04 08:08:37','2026-06-04 10:35:24',NULL,NULL,0),(3,'testdoc01','123456','张医生','13900139001','doc01@test.com',NULL,'DOCTOR',1,'2026-06-04 08:08:37','2026-06-04 10:35:24',NULL,NULL,0),(5,'patient001','123456','王患者乙','13800001001','zhangwei@test.com',NULL,'PATIENT',1,'2026-06-04 08:39:36','2026-06-04 10:35:24',NULL,NULL,0),(6,'patient002','123456','李患者丙','13800001002','lijuan@test.com',NULL,'PATIENT',1,'2026-06-04 08:39:36','2026-06-04 10:35:24',NULL,NULL,0),(7,'newpatient99','123456','赵六','13900001111','zhaoliu@test.com',NULL,'PATIENT',1,'2026-06-04 10:32:15','2026-06-04 10:32:15',NULL,NULL,0);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `xixin_exam`
--

/*!40000 DROP DATABASE IF EXISTS `xixin_exam`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `xixin_exam` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `xixin_exam`;

--
-- Table structure for table `t_appointment`
--

DROP TABLE IF EXISTS `t_appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_appointment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `package_id` bigint(20) unsigned NOT NULL COMMENT '套餐ID',
  `institution_id` bigint(20) unsigned DEFAULT NULL COMMENT '体检机构ID',
  `doctor_id` bigint(20) unsigned DEFAULT NULL COMMENT '分配的医生ID',
  `appointment_date` date NOT NULL COMMENT '预约日期',
  `time_slot` varchar(32) DEFAULT NULL COMMENT '时间段',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_package_id` (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='预约记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_appointment`
--

LOCK TABLES `t_appointment` WRITE;
/*!40000 ALTER TABLE `t_appointment` DISABLE KEYS */;
INSERT INTO `t_appointment` VALUES (1,2,1,1,1,'2026-06-10','上午',1,'需空腹前来','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(2,5,2,2,3,'2026-06-11','上午',0,'首次体检','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(3,6,3,3,9,'2026-06-12','下午',1,NULL,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(4,5,5,1,1,'2026-07-01','上午',0,'糖尿病风险评估','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(5,6,4,3,1,'2026-07-02','下午',3,'甲状腺结节复查','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0);
/*!40000 ALTER TABLE `t_appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_appt_type_institution`
--

DROP TABLE IF EXISTS `t_appt_type_institution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_appt_type_institution` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `appt_type` varchar(64) NOT NULL COMMENT '预约类型(对应 dict_item.value)',
  `institution_id` bigint(20) unsigned NOT NULL COMMENT '机构ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_inst` (`appt_type`,`institution_id`),
  KEY `idx_type` (`appt_type`),
  KEY `idx_inst` (`institution_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='预约类型-机构关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_appt_type_institution`
--

LOCK TABLES `t_appt_type_institution` WRITE;
/*!40000 ALTER TABLE `t_appt_type_institution` DISABLE KEYS */;
INSERT INTO `t_appt_type_institution` VALUES (1,'individual',1,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(2,'individual',2,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(3,'individual',3,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(4,'individual',4,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(5,'individual',5,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(6,'group',1,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(7,'group',2,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(8,'onboarding',1,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(9,'onboarding',2,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(10,'onboarding',3,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(11,'travel',1,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(12,'travel',3,'2026-06-04 10:05:09','2026-06-04 10:05:09');
/*!40000 ALTER TABLE `t_appt_type_institution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_exam_item`
--

DROP TABLE IF EXISTS `t_exam_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_exam_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `package_id` bigint(20) unsigned NOT NULL COMMENT '所属套餐ID',
  `name` varchar(128) NOT NULL COMMENT '项目名称',
  `type` varchar(32) DEFAULT NULL COMMENT '项目类型',
  `description` text COMMENT '项目描述',
  `sort_order` int(11) DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_package_id` (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='体检项目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_exam_item`
--

LOCK TABLES `t_exam_item` WRITE;
/*!40000 ALTER TABLE `t_exam_item` DISABLE KEYS */;
INSERT INTO `t_exam_item` VALUES (1,1,'血常规','检验','白细胞、红细胞、血红蛋白、血小板等全血细胞计数',1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(2,1,'尿常规','检验','尿液化学分析及显微镜检查',2,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(3,1,'十二导联心电图','心电','静息十二导联心电图检查',3,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(4,2,'肝功能全项','检验','谷丙转氨酶、谷草转氨酶、谷氨酰转肽酶、总胆红素等',1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(5,2,'肾功能全项','检验','肌酐、尿素氮、尿酸测定',2,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(6,2,'胸部X光片','影像','后前位胸部正位摄片',3,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(7,3,'肿瘤标志物七项','检验','甲胎蛋白、癌胚抗原、CA19-9、CA125等七项肿瘤标志物',1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(8,3,'甲状腺彩超','影像','B型超声甲状腺及颈部淋巴结扫描',2,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(9,3,'低剂量螺旋CT','影像','胸部低剂量螺旋CT肺癌筛查',3,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(10,4,'妇科常规检查','体格','盆腔检查及宫颈液基薄层细胞学检查(TCT)',1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(11,4,'乳腺彩超','影像','双侧乳腺B型超声检查',2,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(12,4,'HPV-DNA分型','检验','高危型人乳头瘤病毒(HPV)基因分型检测',3,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(13,5,'骨密度检测','影像','双能X线骨密度仪(DEXA)检测骨质疏松',1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(14,5,'颈动脉彩超','影像','颈动脉内膜中层厚度及斑块评估',2,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(15,5,'心脏彩超','心电','经胸超声心动图检查',3,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0);
/*!40000 ALTER TABLE `t_exam_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_exam_package`
--

DROP TABLE IF EXISTS `t_exam_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_exam_package` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '套餐名称',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `description` text COMMENT '套餐描述',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `sort_order` int(11) DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='体检套餐表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_exam_package`
--

LOCK TABLES `t_exam_package` WRITE;
/*!40000 ALTER TABLE `t_exam_package` DISABLE KEYS */;
INSERT INTO `t_exam_package` VALUES (1,'基础健康体检',199.00,'血常规、尿常规、心电图三项基础检查',1,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(2,'标准健康体检',599.00,'基础项目+肝功能全项+肾功能全项+胸部X光片',1,2,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(3,'高端全面体检',1299.00,'标准项目+肿瘤标志物筛查+甲状腺彩超+低剂量螺旋CT',1,3,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(4,'女性专项体检',899.00,'妇科检查+乳腺彩超+HPV-DNA分型检测',1,4,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(5,'银发关怀体检',1699.00,'高端项目+骨密度检测+颈动脉彩超+心脏彩超',1,5,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0);
/*!40000 ALTER TABLE `t_exam_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_institution`
--

DROP TABLE IF EXISTS `t_institution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_institution` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '机构名称',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `business_hours` varchar(128) DEFAULT NULL COMMENT '营业时间',
  `description` text COMMENT '描述',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态: 0停用 1启用',
  `sort_order` int(11) DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='体检机构表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_institution`
--

LOCK TABLES `t_institution` WRITE;
/*!40000 ALTER TABLE `t_institution` DISABLE KEYS */;
INSERT INTO `t_institution` VALUES (1,'北京旗舰体检中心','北京市东城区长安街100号东方广场3层','010-88881001','周一至周五 08:00-17:00, 周六 08:00-12:00','旗舰级体检中心，配备全套进口设备，支持所有体检套餐',1,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(2,'上海浦东体检分院','上海市浦东新区世纪大道200号金融大厦','021-66662002','周一至周日 07:30-16:30','新近装修，配备最新CT和磁共振设备',1,2,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(3,'广州天河体检分院','广州市天河区天河路300号正佳广场旁','020-55553003','周一至周六 08:30-17:30','地铁直达交通便利，女性健康检查专区',1,3,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(4,'成都锦江社区体检站','成都市锦江区红星路400号社区中心','028-44444004','周一至周五 09:00-18:00','社区便民体检站，提供基础检查和常规体检',0,4,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(5,'南京鼓楼体检中心','南京市鼓楼区中山路500号大学附属楼','025-33335005','周一至周日 08:00-16:00','大学附属体检中心，老年保健特色，提供健康管理服务',1,5,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0);
/*!40000 ALTER TABLE `t_institution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_institution_package`
--

DROP TABLE IF EXISTS `t_institution_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_institution_package` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `institution_id` bigint(20) unsigned NOT NULL COMMENT '机构ID',
  `package_id` bigint(20) unsigned NOT NULL COMMENT '套餐ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_inst_pkg` (`institution_id`,`package_id`),
  KEY `idx_inst` (`institution_id`),
  KEY `idx_pkg` (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='机构-套餐关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_institution_package`
--

LOCK TABLES `t_institution_package` WRITE;
/*!40000 ALTER TABLE `t_institution_package` DISABLE KEYS */;
INSERT INTO `t_institution_package` VALUES (1,1,1,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(2,1,2,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(3,1,3,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(4,1,4,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(5,1,5,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(6,2,1,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(7,2,2,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(8,2,3,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(9,2,4,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(10,2,5,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(11,3,1,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(12,3,2,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(13,3,3,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(14,3,4,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(15,4,1,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(16,4,2,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(17,5,1,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(18,5,2,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(19,5,3,'2026-06-04 10:05:09','2026-06-04 10:05:09'),(20,5,5,'2026-06-04 10:05:09','2026-06-04 10:05:09');
/*!40000 ALTER TABLE `t_institution_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `xixin_report`
--

/*!40000 DROP DATABASE IF EXISTS `xixin_report`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `xixin_report` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `xixin_report`;

--
-- Table structure for table `t_report`
--

DROP TABLE IF EXISTS `t_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `appointment_id` bigint(20) unsigned DEFAULT NULL COMMENT '预约ID',
  `doctor_id` bigint(20) unsigned DEFAULT NULL COMMENT '医生ID',
  `summary` text COMMENT '检查摘要',
  `conclusion` text COMMENT '总检结论',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `generate_time` datetime DEFAULT NULL COMMENT '生成时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_appointment_id` (`appointment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='体检报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_report`
--

LOCK TABLES `t_report` WRITE;
/*!40000 ALTER TABLE `t_report` DISABLE KEYS */;
INSERT INTO `t_report` VALUES (1,2,1,1,'生命体征平稳，血常规各项指标在正常范围，心电图示窦性心律','身体健康，建议继续保持良好生活习惯',2,'2026-06-10 14:00:00','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(2,5,2,3,'肝功能谷丙转氨酶轻度升高(65 U/L)，其余指标正常','提示轻度脂肪肝可能，建议减少饮酒、控制饮食，3个月后复查',1,'2026-06-12 10:30:00','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(3,6,3,9,'肿瘤标志物均为阴性，CT示右肺上叶微小结节(3mm, 良性表现)','肺微小结节倾向良性，建议6个月后CT随访',2,'2026-06-13 16:00:00','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(4,6,5,1,'宫颈TCT阴性，乳腺彩超示左乳纤维腺瘤(1.5cm, BI-RADS 3类)','纤维腺瘤倾向良性，建议6个月短期随访复查',1,'2026-07-03 11:00:00','2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0);
/*!40000 ALTER TABLE `t_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_report_item`
--

DROP TABLE IF EXISTS `t_report_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_report_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `report_id` bigint(20) unsigned NOT NULL COMMENT '报告ID',
  `exam_item_id` bigint(20) unsigned DEFAULT NULL COMMENT '体检项目ID',
  `exam_item_name` varchar(128) DEFAULT NULL COMMENT '项目名称',
  `result` varchar(256) DEFAULT NULL COMMENT '检查结果',
  `reference_range` varchar(128) DEFAULT NULL COMMENT '参考范围',
  `abnormal_flag` tinyint(1) DEFAULT '0' COMMENT '异常标志',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_report_id` (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='报告项目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_report_item`
--

LOCK TABLES `t_report_item` WRITE;
/*!40000 ALTER TABLE `t_report_item` DISABLE KEYS */;
INSERT INTO `t_report_item` VALUES (1,1,1,'血常规','白细胞6.8, 红细胞5.2, 血红蛋白148g/L','白细胞4-10, 红细胞4.5-5.8, 血红蛋白130-175',0,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(2,1,3,'心电图','窦性心律，心率72次/分，电轴正常','正常窦性心律',0,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(3,2,4,'肝功能全项','谷丙转氨酶65 U/L(↑)，谷草转氨酶38 U/L','谷丙转氨酶10-40, 谷草转氨酶10-35',1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(4,2,5,'肾功能全项','肌酐82 umol/L，尿素氮4.5 mmol/L','肌酐60-110, 尿素氮3.2-7.1',0,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(5,3,7,'肿瘤标志物七项','甲胎蛋白3.2 ng/mL，癌胚抗原1.5 ng/mL','甲胎蛋白<7, 癌胚抗原<5',0,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(6,4,10,'妇科常规检查','未见上皮内病变或恶性细胞(NILM)','未见异常',0,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0);
/*!40000 ALTER TABLE `t_report_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `xixin_system`
--

/*!40000 DROP DATABASE IF EXISTS `xixin_system`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `xixin_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `xixin_system`;

--
-- Table structure for table `t_dict_item`
--

DROP TABLE IF EXISTS `t_dict_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_dict_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dict_type_id` bigint(20) unsigned NOT NULL COMMENT '字典类型ID',
  `label` varchar(128) NOT NULL COMMENT '标签',
  `value` varchar(128) NOT NULL COMMENT '值',
  `sort_order` int(11) DEFAULT '0' COMMENT '排序',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_dict_type_id` (`dict_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典项表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dict_item`
--

LOCK TABLES `t_dict_item` WRITE;
/*!40000 ALTER TABLE `t_dict_item` DISABLE KEYS */;
INSERT INTO `t_dict_item` VALUES (1,1,'男','1',1,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(2,1,'女','0',2,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(3,2,'待确认','0',1,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(4,2,'已确认','1',2,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(5,2,'已取消','2',3,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(6,2,'已完成','3',4,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(7,3,'草稿','0',1,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(8,3,'已生成','1',2,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(9,3,'已发布','2',3,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(10,4,'个人体检','individual',1,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(11,4,'团体体检','group',2,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(12,4,'入职体检','onboarding',3,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(13,4,'出入境体检','travel',4,1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0);
/*!40000 ALTER TABLE `t_dict_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_dict_type`
--

DROP TABLE IF EXISTS `t_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_dict_type` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(128) NOT NULL COMMENT '字典名称',
  `dict_type` varchar(64) NOT NULL COMMENT '字典类型',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dict_type`
--

LOCK TABLES `t_dict_type` WRITE;
/*!40000 ALTER TABLE `t_dict_type` DISABLE KEYS */;
INSERT INTO `t_dict_type` VALUES (1,'性别','sys_gender',1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(2,'预约状态','exam_appt_status',1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(3,'报告状态','report_status',1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0),(4,'预约类型','exam_appt_type',1,'2026-06-04 10:35:24','2026-06-04 10:35:24',NULL,NULL,0);
/*!40000 ALTER TABLE `t_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) NOT NULL COMMENT '角色名称',
  `role_code` varchar(64) NOT NULL COMMENT '角色编码',
  `description` varchar(256) DEFAULT NULL COMMENT '角色描述',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` VALUES (1,'超级管理员','ADMIN','更新后的描述',1,'2026-06-02 20:00:09','2026-06-04 08:54:05',NULL,NULL,1),(2,'医生','DOCTOR','体检医生',1,'2026-06-02 20:00:09','2026-06-02 20:00:09',NULL,NULL,0),(3,'患者','PATIENT','体检用户',1,'2026-06-02 20:00:09','2026-06-02 20:00:09',NULL,NULL,0);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-04 10:37:31
