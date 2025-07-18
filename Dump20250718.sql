-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: spring_warehouse
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `activity_logs`
--

DROP TABLE IF EXISTS `activity_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `action_type` enum('CREATE','UPDATE','DELETE','ADJUST','LOGIN','LOGOUT') NOT NULL,
  `entity_type` varchar(50) DEFAULT NULL,
  `entity_id` int DEFAULT NULL,
  `old_value` text,
  `new_value` text,
  `note` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `activity_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=578 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_logs`
--

LOCK TABLES `activity_logs` WRITE;
/*!40000 ALTER TABLE `activity_logs` DISABLE KEYS */;
INSERT INTO `activity_logs` VALUES (1,1,'LOGIN','USER_SESSION',1,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:05:45'),(2,1,'LOGIN','USER_SESSION',1,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:29:17'),(3,1,'LOGIN','USER_SESSION',1,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:31:52'),(4,1,'LOGOUT','USER_SESSION',1,'LOGGED_IN','LOGGED_OUT','User logged out from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:34:36'),(5,4,'LOGIN','USER_SESSION',4,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:34:40'),(6,4,'LOGOUT','USER_SESSION',4,'LOGGED_IN','LOGGED_OUT','User logged out from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:35:33'),(7,1,'LOGIN','USER_SESSION',1,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:35:36'),(8,1,'LOGOUT','USER_SESSION',1,'LOGGED_IN','LOGGED_OUT','User logged out from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:35:52'),(9,3,'LOGIN','USER_SESSION',3,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:35:55'),(10,3,'LOGOUT','USER_SESSION',3,'LOGGED_IN','LOGGED_OUT','User logged out from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:36:38'),(11,1,'LOGIN','USER_SESSION',1,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:36:41'),(12,1,'LOGIN','USER_SESSION',1,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 17:45:53'),(13,1,'LOGIN','USER_SESSION',1,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:15:48'),(14,1,'LOGOUT','USER_SESSION',1,'LOGGED_IN','LOGGED_OUT','User logged out from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:25:00'),(15,1,'LOGIN','USER_SESSION',1,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:25:03'),(16,1,'LOGOUT','USER_SESSION',1,'LOGGED_IN','LOGGED_OUT','User logged out from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:25:14'),(17,3,'LOGIN','USER_SESSION',3,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:25:16'),(18,3,'LOGOUT','USER_SESSION',3,'LOGGED_IN','LOGGED_OUT','User logged out from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:25:26'),(19,2,'LOGIN','USER_SESSION',2,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:25:31'),(20,1,'LOGIN','USER_SESSION',1,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:30:15'),(21,1,'LOGOUT','USER_SESSION',1,'LOGGED_IN','LOGGED_OUT','User logged out from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:30:28'),(22,2,'LOGIN','USER_SESSION',2,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:30:31'),(23,2,'LOGIN','USER_SESSION',2,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:36:24'),(24,2,'LOGIN','USER_SESSION',2,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:43:22'),(25,2,'LOGIN','USER_SESSION',2,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:48:24'),(26,2,'LOGIN','USER_SESSION',2,NULL,'LOGGED_IN','User logged in from IP: 0:0:0:0:0:0:0:1','2025-06-28 18:51:05'),(27,1,'LOGIN','User',1,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 14:21:34'),(28,1,'LOGIN','User',1,NULL,'LOGGED_IN','Đăng nhập hệ thống','2025-07-07 14:26:17'),(29,1,'LOGIN','User',1,'User(userId=1, username=admin, passwordHash=IMc5pWjzi8+pHNsZvfnRGw==:CjgAqxuR2PJHWToh0yP2psgUA0B3lbdBC1bhbTIF2L0=, fullName=admin, email=a@hunght1890.com, phone=null, role=Role(roleId=admin, roleName=Administrator), createdAt=2025-06-04 04:24:57.0, updatedAt=2025-07-07 21:26:17.0, isActive=true, lastLogin=2025-07-07T21:26:17, lastLogout=2025-06-29T01:30:28)','User(userId=1, username=admin, passwordHash=IMc5pWjzi8+pHNsZvfnRGw==:CjgAqxuR2PJHWToh0yP2psgUA0B3lbdBC1bhbTIF2L0=, fullName=admin, email=a@hunght1890.com, phone=null, role=Role(roleId=admin, roleName=Administrator), createdAt=2025-06-04 04:24:57.0, updatedAt=2025-07-07 21:26:17.0, isActive=true, lastLogin=2025-07-07T21:26:17, lastLogout=2025-06-29T01:30:28)','Đăng nhập hệ thống','2025-07-07 14:32:11'),(30,1,'LOGIN','User',1,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 14:42:48'),(31,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 14:51:44'),(32,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 15:02:27'),(33,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 15:02:37'),(34,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 15:03:55'),(35,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 15:04:16'),(36,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 15:04:30'),(37,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 15:05:15'),(38,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 15:10:08'),(39,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 15:10:21'),(40,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-07 15:10:33'),(41,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-08 19:35:46'),(42,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-08 19:35:57'),(43,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-08 19:38:55'),(44,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-08 19:53:37'),(45,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-08 19:53:43'),(46,7,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-08 19:54:12'),(47,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-08 19:54:29'),(48,8,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-08 20:22:32'),(49,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 15:26:59'),(50,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 18:58:48'),(51,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 19:02:16'),(52,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 19:16:57'),(53,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 19:20:11'),(54,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 19:21:20'),(55,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 19:22:14'),(56,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 19:23:36'),(57,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 19:47:44'),(58,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 20:01:52'),(59,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:19:46'),(60,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:24:17'),(61,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:24:22'),(62,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:26:04'),(63,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:26:07'),(64,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:30:23'),(65,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:32:04'),(66,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:32:09'),(67,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:33:22'),(68,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:34:11'),(69,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:34:15'),(70,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:37:47'),(71,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:38:19'),(72,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:47:50'),(73,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 22:49:09'),(74,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 23:09:49'),(75,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 23:38:57'),(76,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 23:40:56'),(77,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 23:44:13'),(78,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 23:45:41'),(79,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 23:45:43'),(80,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 23:48:31'),(81,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 23:49:54'),(82,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 23:50:21'),(83,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-09 23:59:48'),(84,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 00:00:07'),(85,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 18:38:32'),(86,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 19:10:10'),(87,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 19:44:33'),(88,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 19:45:17'),(89,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 20:14:55'),(90,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 20:36:39'),(91,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 20:36:51'),(92,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 20:37:22'),(93,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 20:37:26'),(94,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 20:38:36'),(95,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 21:23:41'),(96,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 21:24:16'),(97,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 21:30:19'),(98,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 21:41:30'),(99,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 21:44:14'),(100,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 21:45:33'),(101,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 21:48:54'),(102,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 21:51:00'),(103,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 21:55:34'),(104,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 21:57:18'),(105,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:07:02'),(106,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:08:18'),(107,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:09:31'),(108,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:12:23'),(109,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:12:26'),(110,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:17:07'),(111,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:17:11'),(112,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:24:28'),(113,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:24:33'),(114,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:30:48'),(115,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:32:12'),(116,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:36:47'),(117,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:36:51'),(118,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:37:46'),(119,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:37:50'),(120,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:40:44'),(121,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:40:47'),(122,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:43:33'),(123,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:43:36'),(124,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:50:49'),(125,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:53:20'),(126,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:57:31'),(127,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:57:37'),(128,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:58:53'),(129,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:58:57'),(130,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 22:59:38'),(131,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 23:01:19'),(132,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 23:06:21'),(133,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 23:29:57'),(134,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 23:34:34'),(135,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 23:47:26'),(136,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 23:49:34'),(137,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 23:50:07'),(138,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 23:50:11'),(139,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 23:56:23'),(140,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 23:57:01'),(141,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-10 23:57:39'),(142,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 00:00:32'),(143,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 00:00:38'),(144,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 00:00:51'),(145,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 00:00:53'),(146,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 00:04:03'),(147,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 18:55:51'),(148,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:09:54'),(149,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:10:56'),(150,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:13:50'),(151,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:17:12'),(152,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:20:33'),(153,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:22:03'),(154,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:24:36'),(155,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:37:39'),(156,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:45:05'),(157,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:45:53'),(158,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:45:56'),(159,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:47:13'),(160,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:55:40'),(161,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:55:42'),(162,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:56:44'),(163,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:57:30'),(164,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:58:07'),(165,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:58:10'),(166,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:59:39'),(167,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 19:59:43'),(168,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:01:53'),(169,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:05:59'),(170,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:07:08'),(171,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:08:52'),(172,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:09:53'),(173,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:10:28'),(174,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:10:55'),(175,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:11:47'),(176,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:13:11'),(177,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:14:26'),(178,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:14:30'),(179,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:15:09'),(180,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:16:29'),(181,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:17:50'),(182,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:19:01'),(183,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:20:46'),(184,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:21:52'),(185,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:23:26'),(186,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:24:35'),(187,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:26:30'),(188,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:27:34'),(189,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:28:51'),(190,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:30:01'),(191,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:31:23'),(192,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:33:00'),(193,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:33:43'),(194,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:35:28'),(195,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:35:56'),(196,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:36:16'),(197,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:36:19'),(198,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:36:56'),(199,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:38:54'),(200,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:42:34'),(201,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:44:56'),(202,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:45:46'),(203,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 20:47:25'),(204,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 21:10:04'),(205,8,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 21:35:47'),(206,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 21:35:59'),(207,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 21:41:43'),(208,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 21:42:39'),(209,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 21:44:24'),(210,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 21:44:29'),(211,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 21:47:24'),(212,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 21:52:21'),(213,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 21:56:40'),(214,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 21:57:59'),(215,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:12:33'),(216,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:14:47'),(217,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:14:52'),(218,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:20:08'),(219,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:20:11'),(220,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:21:12'),(221,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:24:00'),(222,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:24:04'),(223,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:25:45'),(224,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:26:22'),(225,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:32:08'),(226,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:32:12'),(227,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:33:39'),(228,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:34:08'),(229,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:35:42'),(230,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:36:19'),(231,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:36:30'),(232,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:38:41'),(233,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:38:50'),(234,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:40:37'),(235,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:40:40'),(236,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:43:30'),(237,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:43:32'),(238,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:45:38'),(239,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:47:22'),(240,7,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:56:02'),(241,8,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:56:08'),(242,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:57:23'),(243,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:58:25'),(244,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 22:58:43'),(245,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:05:07'),(246,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:05:33'),(247,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:05:56'),(248,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:07:36'),(249,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:07:52'),(250,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:10:25'),(251,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:11:33'),(252,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:16:22'),(253,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:18:11'),(254,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:39:42'),(255,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:41:03'),(256,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:41:22'),(257,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-11 23:41:33'),(258,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 13:06:46'),(259,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 15:35:20'),(260,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 15:59:06'),(261,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 15:59:38'),(262,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 16:11:14'),(263,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 16:16:02'),(264,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 16:23:10'),(265,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 16:23:14'),(266,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 16:25:35'),(267,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 16:34:47'),(268,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 17:03:39'),(269,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 17:14:26'),(270,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 17:20:34'),(271,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 17:20:37'),(272,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 17:22:39'),(273,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 17:29:24'),(274,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 17:31:13'),(275,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 17:32:39'),(276,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 17:36:58'),(277,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 17:38:40'),(278,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 17:38:44'),(279,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:02:35'),(280,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:12:27'),(281,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:12:45'),(282,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:16:08'),(283,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:20:42'),(284,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:23:43'),(285,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:23:59'),(286,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:33:02'),(287,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:33:25'),(288,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:33:43'),(289,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:36:35'),(290,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:37:39'),(291,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:40:56'),(292,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 18:45:16'),(293,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:00:03'),(294,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:06:08'),(295,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:07:21'),(296,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:11:30'),(297,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:11:41'),(298,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:14:31'),(299,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:16:38'),(300,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:19:03'),(301,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:23:58'),(302,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:24:01'),(303,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:25:51'),(304,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:25:54'),(305,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:27:04'),(306,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:28:23'),(307,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:28:46'),(308,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:30:18'),(309,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:36:22'),(310,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:37:31'),(311,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:39:03'),(312,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:53:59'),(313,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:55:46'),(314,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:58:50'),(315,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 19:58:58'),(316,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 20:03:05'),(317,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 20:04:40'),(318,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 20:11:58'),(319,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 20:17:34'),(320,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 20:19:49'),(321,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 20:31:00'),(322,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 20:43:58'),(323,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-12 20:50:32'),(324,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 03:43:57'),(325,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 03:50:53'),(326,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 03:56:26'),(327,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:07:57'),(328,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:09:22'),(329,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:11:06'),(330,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:18:26'),(331,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:24:16'),(332,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:30:09'),(333,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:34:35'),(334,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:38:01'),(335,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:38:43'),(336,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:38:45'),(337,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:39:42'),(338,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:43:31'),(339,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:50:37'),(340,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:54:49'),(341,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:56:15'),(342,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:57:24'),(343,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 04:59:19'),(344,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:02:31'),(345,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:12:08'),(346,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:12:54'),(347,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:17:02'),(348,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:21:47'),(349,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:23:15'),(350,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:24:08'),(351,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:28:47'),(352,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:29:44'),(353,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:31:03'),(354,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:32:16'),(355,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:41:26'),(356,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:49:14'),(357,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:50:15'),(358,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:51:52'),(359,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 05:57:40'),(360,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 06:00:30'),(361,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 06:00:50'),(362,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-13 06:01:00'),(363,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 02:45:24'),(364,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 02:50:54'),(365,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 02:56:03'),(366,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:04:18'),(367,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:08:43'),(368,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:20:25'),(369,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:20:43'),(370,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:21:05'),(371,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:23:19'),(372,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:23:51'),(373,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:24:17'),(374,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:24:26'),(375,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:25:14'),(376,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:28:23'),(377,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:29:06'),(378,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:29:20'),(379,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:29:29'),(380,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:36:44'),(381,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:43:13'),(382,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 03:43:28'),(383,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 07:11:42'),(384,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 07:11:51'),(385,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 07:12:03'),(386,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 22:29:31'),(387,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 22:39:42'),(388,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 22:39:54'),(389,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 22:42:54'),(390,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 22:43:27'),(391,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 22:44:46'),(392,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 22:51:35'),(393,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 22:58:32'),(394,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 22:58:51'),(395,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 22:59:14'),(396,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 23:02:11'),(397,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 23:08:14'),(398,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 23:08:17'),(399,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 23:11:48'),(400,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 23:13:32'),(401,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 23:14:32'),(402,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 23:15:06'),(403,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 23:20:17'),(404,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-14 23:22:38'),(405,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 02:14:20'),(406,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 02:20:47'),(407,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 02:22:30'),(408,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 02:23:58'),(409,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 02:24:02'),(410,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 02:25:17'),(411,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 02:26:42'),(412,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 02:28:28'),(413,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 02:28:54'),(414,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 02:30:55'),(415,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 22:53:54'),(416,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 22:59:56'),(417,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:07:09'),(418,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:11:07'),(419,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:12:56'),(420,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:17:05'),(421,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:17:28'),(422,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:18:04'),(423,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:18:07'),(424,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:26:13'),(425,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:30:48'),(426,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:32:23'),(427,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:42:01'),(428,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-15 23:47:57'),(429,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 00:45:04'),(430,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 00:45:19'),(431,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 00:46:13'),(432,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 00:46:49'),(433,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 00:51:08'),(434,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 00:51:23'),(435,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 00:57:29'),(436,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:03:10'),(437,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:03:18'),(438,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:05:57'),(439,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:09:55'),(440,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:17:51'),(441,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:25:06'),(442,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:35:15'),(443,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:38:54'),(444,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:39:14'),(445,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:39:31'),(446,3,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:39:48'),(447,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:40:51'),(448,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:50:53'),(449,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 01:59:26'),(450,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:05:28'),(451,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:06:27'),(452,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:06:47'),(453,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:22:14'),(454,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:23:06'),(455,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:25:40'),(456,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:26:57'),(457,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:27:13'),(458,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:27:27'),(459,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:27:36'),(460,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:30:14'),(461,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:30:16'),(462,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:30:29'),(463,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:30:52'),(464,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:30:57'),(465,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:31:10'),(466,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:34:13'),(467,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:34:23'),(468,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:36:56'),(469,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:37:11'),(470,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:37:46'),(471,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 02:44:44'),(472,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 03:04:05'),(473,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 03:37:54'),(474,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 03:39:26'),(475,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 03:40:04'),(476,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 03:40:08'),(477,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 03:40:57'),(478,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 03:41:00'),(479,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 03:57:35'),(480,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 04:32:36'),(481,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 04:38:47'),(482,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 04:40:32'),(483,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 04:49:47'),(484,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 04:50:50'),(485,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 04:54:52'),(486,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 04:56:26'),(487,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:05:52'),(488,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:12:51'),(489,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:19:11'),(490,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:20:54'),(491,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:21:00'),(492,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:22:36'),(493,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:26:24'),(494,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:29:59'),(495,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:31:08'),(496,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:32:36'),(497,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:34:47'),(498,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:37:27'),(499,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:47:27'),(500,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:47:30'),(501,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:49:42'),(502,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:51:17'),(503,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 22:56:19'),(504,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 23:02:14'),(505,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 23:35:06'),(506,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 23:37:02'),(507,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 23:37:06'),(508,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 23:42:07'),(509,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 23:42:10'),(510,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 23:46:49'),(511,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 23:46:52'),(512,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 23:53:29'),(513,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 23:54:42'),(514,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-16 23:55:32'),(515,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:00:06'),(516,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:09:26'),(517,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:29:58'),(518,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:30:50'),(519,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:30:55'),(520,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:33:53'),(521,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:34:01'),(522,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:47:59'),(523,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:56:16'),(524,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:57:15'),(525,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:58:03'),(526,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:58:06'),(527,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 00:59:05'),(528,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 01:02:55'),(529,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 01:04:09'),(530,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 03:13:27'),(531,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 03:49:08'),(532,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 05:05:25'),(533,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 05:08:53'),(534,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 06:04:49'),(535,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 06:07:54'),(536,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 22:51:45'),(537,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 22:53:34'),(538,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 22:55:00'),(539,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 22:55:13'),(540,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 22:55:50'),(541,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:07:47'),(542,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:12:08'),(543,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:18:32'),(544,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:19:29'),(545,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:19:48'),(546,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:30:58'),(547,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:31:01'),(548,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:34:54'),(549,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:35:14'),(550,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:36:51'),(551,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:42:49'),(552,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:42:57'),(553,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:49:30'),(554,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:56:58'),(555,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:58:30'),(556,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-17 23:58:45'),(557,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 00:05:28'),(558,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 00:53:48'),(559,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 00:54:14'),(560,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 00:55:07'),(561,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 00:55:15'),(562,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:02:20'),(563,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:03:30'),(564,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:12:59'),(565,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:13:45'),(566,4,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:26:59'),(567,5,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:27:49'),(568,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:29:14'),(569,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:29:48'),(570,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:30:04'),(571,2,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:31:55'),(572,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:32:23'),(573,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:50:19'),(574,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 01:58:20'),(575,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 02:04:29'),(576,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 02:05:01'),(577,1,'LOGIN',NULL,NULL,NULL,NULL,'Đăng nhập hệ thống','2025-07-18 02:06:29');
/*!40000 ALTER TABLE `activity_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adjustmentreasons`
--

DROP TABLE IF EXISTS `adjustmentreasons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adjustmentreasons` (
  `reason_id` int NOT NULL AUTO_INCREMENT,
  `reason_description` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reason_id`),
  UNIQUE KEY `reason_description` (`reason_description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adjustmentreasons`
--

LOCK TABLES `adjustmentreasons` WRITE;
/*!40000 ALTER TABLE `adjustmentreasons` DISABLE KEYS */;
/*!40000 ALTER TABLE `adjustmentreasons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Áo','Cái','2025-06-04 14:41:20'),(2,'Quần','Cái','2025-06-04 14:41:20'),(3,'Váy','Cái','2025-06-04 14:41:20'),(4,'Bộ đồ','Bộ','2025-06-04 14:41:20'),(5,'Phụ kiện','Chiếc','2025-06-04 14:41:20'),(7,'123123',NULL,'2025-07-07 08:10:40');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category-product`
--

DROP TABLE IF EXISTS `category-product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category-product` (
  `id` int NOT NULL,
  `product_id` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category-product`
--

LOCK TABLES `category-product` WRITE;
/*!40000 ALTER TABLE `category-product` DISABLE KEYS */;
INSERT INTO `category-product` VALUES (1,21,1),(2,22,1),(3,23,2),(4,24,3),(5,25,1),(6,26,2),(7,27,1),(8,28,1),(9,29,3),(10,30,1),(11,31,4),(12,32,1),(13,33,2),(14,34,1),(15,35,5),(16,36,5),(17,37,5),(18,38,1),(19,39,2),(20,40,1),(21,45,5),(22,46,1);
/*!40000 ALTER TABLE `category-product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `inventory_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `quantity_on_hand` int NOT NULL DEFAULT '0',
  `last_updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `warehouse_id` int DEFAULT NULL,
  PRIMARY KEY (`inventory_id`),
  UNIQUE KEY `product_id` (`product_id`,`warehouse_id`),
  KEY `fk_inventory_warehouse` (`warehouse_id`),
  CONSTRAINT `fk_inventory_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`warehouse_id`) ON DELETE CASCADE,
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,21,90,'2025-06-21 18:28:50',1),(2,22,97,'2025-06-21 18:28:50',1),(3,23,1,'2025-07-04 16:17:39',1),(4,24,96,'2025-06-21 18:28:50',1),(5,25,0,'2025-07-04 16:20:00',1),(6,26,95,'2025-06-21 18:28:50',1),(7,27,100,'2025-06-21 18:28:50',1),(8,28,100,'2025-06-21 18:28:50',1),(9,29,100,'2025-06-21 18:28:50',1),(10,30,100,'2025-06-21 18:28:50',1),(11,31,100,'2025-07-12 10:24:24',1),(12,32,99,'2025-07-04 16:14:26',2),(13,33,100,'2025-07-04 16:14:26',3),(14,34,100,'2025-06-21 18:28:50',1),(15,35,100,'2025-06-21 18:28:50',1),(16,36,102,'2025-07-17 18:32:05',1),(17,37,100,'2025-06-21 18:28:50',1),(18,38,100,'2025-06-21 18:28:50',1),(19,39,105,'2025-07-17 18:30:26',1),(20,40,100,'2025-07-12 10:23:04',1),(23,23,0,'2025-07-12 10:16:28',2),(24,24,0,'2025-07-12 10:23:58',3),(25,28,0,'2025-07-12 10:24:05',2);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_code` varchar(255) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `purchase_price` float DEFAULT '0',
  `sale_price` float DEFAULT '0',
  `supplier_id` int DEFAULT NULL,
  `low_stock_threshold` int DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_active` tinyint(1) DEFAULT '1',
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_code` (`product_code`),
  KEY `supplier_id` (`supplier_id`),
  KEY `FK1cf90etcu98x1e6n9aks3tel3` (`category_id`),
  CONSTRAINT `FK1cf90etcu98x1e6n9aks3tel3` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (21,'CL001','Áo sơ mi trắng nam','Áo sơ mi trắng tay dài, chất cotton',150000,250000,NULL,5,NULL,NULL,1,2),(22,'CL002','Áo thun nữ in họa tiết','Chất thun co giãn, thoáng mát',100000,180000,2,10,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(23,'CL003','Quần jeans nam','Quần jean xanh đậm, form slim-fit',200000,350000,3,15,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(24,'CL004','Chân váy xếp ly','Chân váy nữ dáng ngắn, chất voan',180000,290000,2,7,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(25,'CL005','Áo khoác bomber nam','Áo khoác thời trang, chất kaki lót dù',300000,450000,4,4,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(26,'CL006','Quần short nữ','Quần short lưng cao, phối túi',120000,210000,4,8,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(27,'CL007','Áo hoodie unisex','Áo nỉ chui đầu có nón, freesize',250000,400000,5,6,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(28,'CL008','Áo len cổ lọ nữ','Áo giữ ấm mùa đông, chất len dày',220000,370000,6,5,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(29,'CL009','Váy maxi dạo phố','Váy dáng dài, hoa nhí nhẹ nhàng',250000,380000,6,6,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(30,'CL010','Áo ba lỗ nam','Áo thể thao nam chất thun lạnh',80000,150000,7,12,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(31,'CL011','Bộ pijama nữ','Bộ ngủ nữ, chất lụa mềm mát',180000,280000,NULL,10,NULL,NULL,1,1),(32,'CL012','Áo vest nam công sở','Vest đen lịch sự, vải tuyết mưa',400000,600000,8,2,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(33,'CL013','Quần tây nam','Quần tây công sở, form chuẩn',250000,370000,5,6,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(34,'CL014','Áo thun croptop nữ','Áo croptop trẻ trung năng động',90000,160000,9,15,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(35,'CL015','Khăn quàng cổ len','Khăn choàng len giữ ấm',50000,120000,9,20,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(36,'CL016','Tất vớ cotton','Vớ ngắn thoáng khí, size free',20000,40000,10,40,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(37,'CL017','Mũ bucket unisex','Mũ vải chống nắng thời trang',60000,110000,9,25,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(38,'CL018','Áo gió 2 lớp nam','Áo chống nước, có mũ trùm đầu',270000,420000,NULL,10,NULL,NULL,0,3),(39,'CL019','Quần legging nữ','Quần tập gym, chất co giãn tốt',130000,200000,7,12,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(40,'CL020','Áo sơ mi caro','Áo sơ mi nam nữ unisex, tay dài',180000,300000,9,14,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(45,'CL999','Sản phẩm test','Test mô tả',100000,120000,2,1,'2025-06-04 14:32:17','2025-06-11 14:08:37',1,NULL),(46,'test','123',NULL,123,123,NULL,NULL,NULL,NULL,1,NULL),(49,'123','12312',NULL,123123,3123,NULL,656,'2025-07-02 12:07:24','2025-07-02 20:31:35',1,3),(55,'12355dfdff','3213123','12312',232,323,NULL,2323,NULL,NULL,0,1),(56,'123123dfddf','dsfsdf','ádaf',12355,5555,NULL,55,NULL,NULL,0,1),(58,'Cldffas','Sản pdfdfhẩm test','Test mô tả',555,120000,NULL,1,NULL,NULL,1,1),(59,'hehe','hehe','Test mô tả',555,120000,NULL,1,NULL,NULL,1,1),(60,'CL9bv','Sản phẩbvbvm test','Test mô tả',100000,120000,NULL,1,NULL,NULL,1,1);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_order_details`
--

DROP TABLE IF EXISTS `purchase_order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_order_details` (
  `request_detail_id` int NOT NULL AUTO_INCREMENT,
  `request_id` int NOT NULL,
  `product_id` int NOT NULL,
  `requested_quantity` int NOT NULL,
  `unit_price_estimated` decimal(38,2) DEFAULT NULL,
  `supplier_id_suggested` int DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`request_detail_id`),
  KEY `request_id` (`request_id`),
  KEY `product_id` (`product_id`),
  KEY `supplier_id_suggested` (`supplier_id_suggested`),
  CONSTRAINT `purchase_order_details_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `purchase_orders` (`request_id`) ON DELETE CASCADE,
  CONSTRAINT `purchase_order_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `purchase_order_details_ibfk_3` FOREIGN KEY (`supplier_id_suggested`) REFERENCES `suppliers` (`supplier_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_details`
--

LOCK TABLES `purchase_order_details` WRITE;
/*!40000 ALTER TABLE `purchase_order_details` DISABLE KEYS */;
INSERT INTO `purchase_order_details` VALUES (1,2,35,3,NULL,4,NULL),(2,3,36,10,NULL,5,NULL),(3,3,35,10,NULL,NULL,NULL),(4,4,25,3,NULL,NULL,NULL),(5,5,25,4,NULL,NULL,NULL),(6,6,23,3,NULL,NULL,NULL),(7,7,24,3,NULL,NULL,NULL),(8,8,23,2,NULL,NULL,NULL),(9,9,24,3,540000.00,NULL,NULL),(10,10,32,2,800000.00,NULL,NULL),(11,11,33,2,500000.00,NULL,NULL),(12,12,23,1,200000.00,NULL,NULL),(13,13,21,4,600000.00,NULL,NULL),(14,13,22,2,200000.00,NULL,NULL),(15,14,33,2,500000.00,NULL,NULL),(16,14,24,4,720000.00,NULL,NULL),(17,15,23,3,600000.00,NULL,NULL),(18,15,28,2,440000.00,NULL,NULL),(19,16,24,2,360000.00,NULL,NULL),(20,17,23,3,600000.00,NULL,NULL),(21,18,24,1,180000.00,NULL,NULL),(22,19,23,2,400000.00,NULL,NULL),(23,20,23,2,400000.00,NULL,NULL);
/*!40000 ALTER TABLE `purchase_order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_orders`
--

DROP TABLE IF EXISTS `purchase_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_orders` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `user_id_requester` int NOT NULL,
  `request_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` enum('approved','ordered','partially_received','pending_approval','received','rejected') NOT NULL,
  `notes` text,
  `approved_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `warehouse_id` int DEFAULT NULL,
  `request_code` varchar(50) DEFAULT NULL,
  `supplier_id` int DEFAULT NULL,
  `purchase_order_id` int DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  KEY `user_id_requester` (`user_id_requester`),
  KEY `fk_purchaserequests_warehouse` (`warehouse_id`),
  KEY `FK2f8bwwfspl22pof570p9gkiq6` (`purchase_order_id`),
  CONSTRAINT `FK2f8bwwfspl22pof570p9gkiq6` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_orders` (`request_id`),
  CONSTRAINT `fk_purchaserequests_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`warehouse_id`) ON DELETE CASCADE,
  CONSTRAINT `purchase_orders_ibfk_1` FOREIGN KEY (`user_id_requester`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_orders`
--

LOCK TABLES `purchase_orders` WRITE;
/*!40000 ALTER TABLE `purchase_orders` DISABLE KEYS */;
INSERT INTO `purchase_orders` VALUES (1,4,'2025-06-22 15:40:34','ordered','hết hàng rrr',NULL,'2025-06-22 08:40:33','2025-07-12 20:48:02',1,'	REQ-17',4,NULL),(2,4,'2025-06-22 16:10:15','ordered','t',NULL,'2025-06-22 09:10:15','2025-07-12 20:48:02',1,'52351125392',1,NULL),(3,4,'2025-06-22 20:56:38','approved','hết hàng',NULL,'2025-06-22 13:56:37','2025-07-12 20:48:02',1,'	REQ-1752351125317',2,NULL),(4,2,'2025-07-12 19:56:06','approved',NULL,'2025-07-12 21:33:31','2025-07-12 12:56:06','2025-07-16 22:13:48',1,'	REQ-17523511',5,NULL),(5,2,'2025-07-12 19:59:05','rejected',NULL,NULL,'2025-07-12 12:59:05','2025-07-16 22:13:48',1,'	REQ-17523',6,NULL),(6,2,'2025-07-12 20:12:05','rejected',NULL,'2025-07-14 15:39:47','2025-07-12 13:12:05','2025-07-16 22:13:48',2,'REQ-1752351125399',1,NULL),(7,2,'2025-07-12 20:14:11','rejected',NULL,'2025-07-12 21:34:47','2025-07-12 13:14:11','2025-07-16 22:13:48',3,'REQ-1752351250677',2,NULL),(8,2,'2025-07-12 20:17:42','approved','hết','2025-07-15 17:45:34','2025-07-12 13:17:42','2025-07-16 22:13:48',2,'REQ-1752351462421',3,NULL),(9,2,'2025-07-13 05:12:21','approved','hết rồi','2025-07-15 17:45:39','2025-07-12 22:12:21','2025-07-16 22:59:24',3,'REQ-1752383541379',2,NULL),(10,2,'2025-07-14 22:59:02','rejected','@@','2025-07-15 17:45:42','2025-07-14 15:59:02','2025-07-16 22:59:32',2,'REQ-1752533942428',5,NULL),(11,2,'2025-07-14 22:59:09','approved',NULL,'2025-07-15 19:06:34','2025-07-14 15:59:09','2025-07-16 22:13:48',3,'REQ-1752533949318',5,NULL),(12,2,'2025-07-16 00:46:25','approved',NULL,'2025-07-15 19:06:37','2025-07-15 17:46:25','2025-07-16 22:13:48',2,'REQ-1752626784507',4,NULL),(13,2,'2025-07-16 00:46:32','ordered','','2025-07-15 19:06:39','2025-07-15 17:46:32','2025-07-15 19:06:39',1,'REQ-1752626791766',1,NULL),(14,2,'2025-07-16 00:46:46','ordered','hết hàng','2025-07-15 19:06:41','2025-07-15 17:46:46','2025-07-15 19:06:41',3,'REQ-1752626805578',1,NULL),(15,2,'2025-07-16 02:27:05','approved',NULL,'2025-07-15 19:27:20','2025-07-15 19:27:05','2025-07-16 22:13:48',2,'REQ-1752632824949',6,NULL),(16,2,'2025-07-16 02:27:10','approved',NULL,'2025-07-15 19:27:23','2025-07-15 19:27:10','2025-07-16 22:13:48',3,'REQ-1752632829561',7,NULL),(17,2,'2025-07-16 02:30:48','approved',NULL,'2025-07-15 19:31:16','2025-07-15 19:30:48','2025-07-16 22:13:48',1,'REQ-1752633048253',2,NULL),(18,2,'2025-07-16 02:31:05','rejected',NULL,'2025-07-15 19:34:28','2025-07-15 19:31:05','2025-07-16 22:13:48',3,'REQ-1752633065321',1,NULL),(19,2,'2025-07-16 02:34:19','approved',NULL,'2025-07-15 19:34:32','2025-07-15 19:34:19','2025-07-16 22:13:48',2,'REQ-1752633258675',3,NULL),(20,2,'2025-07-16 02:37:06','approved',NULL,'2025-07-15 19:37:16','2025-07-15 19:37:06','2025-07-16 22:13:48',1,'REQ-1752633426387',4,NULL);
/*!40000 ALTER TABLE `purchase_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` varchar(255) NOT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('admin','Administrator'),('purchasing_staff','Purchasing Staff'),('sales_staff','Sales Staff'),('warehouse_manager','Warehouse Manager'),('warehouse_staff','Warehouse Staff');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_order_details`
--

DROP TABLE IF EXISTS `sales_order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_order_details` (
  `order_detail_id` int NOT NULL AUTO_INCREMENT,
  `sales_order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity_ordered` int NOT NULL,
  `unit_sale_price` float(12,2) DEFAULT NULL,
  `warehouse_id` int DEFAULT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `sales_order_id` (`sales_order_id`),
  KEY `product_id` (`product_id`),
  KEY `fk_warehouse` (`warehouse_id`),
  CONSTRAINT `fk_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`warehouse_id`),
  CONSTRAINT `sales_order_details_ibfk_1` FOREIGN KEY (`sales_order_id`) REFERENCES `sales_orders` (`sales_order_id`) ON DELETE CASCADE,
  CONSTRAINT `sales_order_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_order_details`
--

LOCK TABLES `sales_order_details` WRITE;
/*!40000 ALTER TABLE `sales_order_details` DISABLE KEYS */;
INSERT INTO `sales_order_details` VALUES (11,7,28,19,370000.00,1),(12,8,28,10,370000.00,1),(13,9,38,3,420000.00,1),(14,32,21,4,250000.00,1),(15,32,22,3,180000.00,1),(16,32,23,4,350000.00,1),(17,32,24,4,290000.00,1),(18,32,25,2,450000.00,1),(19,32,26,1,210000.00,1),(20,32,27,1,400000.00,1),(21,32,28,1,370000.00,1),(22,32,29,1,380000.00,1),(23,32,30,1,150000.00,1),(24,32,31,1,280000.00,1),(25,32,34,1,160000.00,1),(26,32,35,1,120000.00,1),(27,32,36,1,40000.00,1),(28,32,37,1,110000.00,1),(29,32,38,1,420000.00,1),(30,32,39,1,200000.00,1),(31,32,40,1,300000.00,1),(33,33,21,2,250000.00,1),(34,33,22,0,180000.00,1),(35,33,23,0,350000.00,1),(36,33,24,0,290000.00,1),(37,33,26,0,210000.00,1),(38,34,22,4,180000.00,1),(39,35,23,2,350000.00,1),(40,35,24,2,290000.00,1),(42,36,22,1,180000.00,1),(43,36,26,1,210000.00,1),(44,37,21,3,250000.00,1),(79,2,22,3,180000.00,1),(82,2,21,3,250000.00,1),(83,38,21,2,250000.00,1),(84,38,22,2,180000.00,1),(85,38,23,4,350000.00,1),(87,39,21,3,250000.00,1),(88,39,22,4,180000.00,1),(89,40,32,3,600000.00,2),(90,41,26,5,210000.00,1),(91,42,21,3,250000.00,1),(92,43,21,1,250000.00,1),(93,44,32,1,600000.00,2),(94,45,21,2,250000.00,1),(95,46,23,5,350000.00,1),(96,47,21,5,250000.00,1),(97,47,22,2,180000.00,1);
/*!40000 ALTER TABLE `sales_order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_orders`
--

DROP TABLE IF EXISTS `sales_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_orders` (
  `sales_order_id` int NOT NULL AUTO_INCREMENT,
  `order_code` varchar(50) DEFAULT NULL,
  `customer_name` varchar(100) DEFAULT NULL,
  `user_id` int NOT NULL,
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` enum('awaiting_shipment','cancelled','completed','pending_stock_check','shipped') DEFAULT NULL,
  `notes` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `customer_email` varchar(100) DEFAULT NULL,
  `customer_phone` varchar(20) DEFAULT NULL,
  `shipping_address` text,
  PRIMARY KEY (`sales_order_id`),
  UNIQUE KEY `order_code` (`order_code`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `sales_orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_orders`
--

LOCK TABLES `sales_orders` WRITE;
/*!40000 ALTER TABLE `sales_orders` DISABLE KEYS */;
INSERT INTO `sales_orders` VALUES (2,'SO000002','b',3,'2025-06-12 17:00:00','shipped','','2025-06-13 03:04:24','',NULL,NULL),(7,'SO000003','v',3,'2025-06-22 00:00:00','completed','','2025-06-21 18:55:44',NULL,NULL,NULL),(8,'SO000004','qqq',3,'2025-06-22 00:00:00','completed','','2025-06-21 19:10:56',NULL,NULL,NULL),(9,'SO000005','qqq',3,'2025-06-28 00:00:00','shipped','123','2025-06-28 10:36:35',NULL,NULL,NULL),(10,'SO000006','zzz',3,'2025-06-29 00:00:00','shipped','','2025-06-29 01:15:00',NULL,NULL,NULL),(11,'SO000007','test1',3,'2025-07-01 00:00:00','shipped','','2025-07-01 02:00:00',NULL,NULL,NULL),(12,'SO000008','customerX',3,'2025-07-02 00:00:00','cancelled','Khách hủy đơn','2025-07-02 03:00:00',NULL,NULL,NULL),(13,'SO000009','alpha',3,'2025-07-03 00:00:00','cancelled','','2025-07-03 04:30:00',NULL,NULL,NULL),(14,'SO000010','beta',3,'2025-07-04 00:00:00','completed','Giao thành công','2025-07-04 06:00:00',NULL,NULL,NULL),(15,'SO000011','gamma',8,'2025-07-05 00:00:00','shipped','Đã giao nhanh','2025-07-05 02:45:00',NULL,NULL,NULL),(16,'SO000012','delta',8,'2025-07-06 00:00:00','completed','Khách VIP','2025-07-06 03:15:00',NULL,NULL,NULL),(17,'SO000013','omega',8,'2025-07-06 00:00:00','cancelled','Hết hàng','2025-07-06 04:20:00',NULL,NULL,NULL),(18,'SO000014','epsilon',8,'2025-07-07 00:00:00','shipped','Chờ kiểm kho','2025-07-07 06:30:00',NULL,NULL,NULL),(19,'SO000015','theta',8,'2025-07-07 00:00:00','shipped','Chờ vận chuyển','2025-07-07 07:00:00',NULL,NULL,NULL),(20,'SO000020','',3,'2025-07-10 20:36:56','shipped','','2025-07-10 13:36:56',NULL,NULL,NULL),(23,'SO000023','',3,'2025-07-10 20:37:29','shipped','','2025-07-10 13:37:29',NULL,NULL,NULL),(24,'SO000024','',3,'2025-07-10 20:37:31','cancelled','','2025-07-10 13:37:31',NULL,NULL,NULL),(25,'SO000025','',3,'2025-07-10 20:37:32','cancelled','','2025-07-10 13:37:32',NULL,NULL,NULL),(27,'SO000027','',3,'2025-07-10 20:52:15','cancelled','','2025-07-10 13:52:15',NULL,NULL,NULL),(28,'SO000028','',3,'2025-07-10 20:52:21','cancelled','','2025-07-10 13:52:21',NULL,NULL,NULL),(29,'SO000029','',3,'2025-07-10 20:52:26','cancelled','','2025-07-10 13:52:26',NULL,NULL,NULL),(31,'SO000031','',3,'2025-07-10 20:53:24','cancelled','','2025-07-10 13:53:24',NULL,NULL,NULL),(32,'SO000032','ádas',3,'2025-07-10 17:00:00','shipped','','2025-07-10 14:39:59','',NULL,NULL),(33,'SO000033','ádas',3,'2025-07-10 22:09:44','cancelled','','2025-07-10 15:09:44',NULL,NULL,NULL),(34,'SO000034','ádas',3,'2025-07-10 17:00:00','cancelled','','2025-07-10 15:12:43','',NULL,NULL),(35,'SO000035','ádas',3,'2025-07-10 17:00:00','cancelled','','2025-07-10 15:24:46','',NULL,NULL),(36,'SO000036','ádas',3,'2025-07-10 17:00:00','cancelled','ádasdasd','2025-07-10 16:30:17','',NULL,NULL),(37,'SO000037','ádas',3,'2025-07-10 17:00:00','shipped','ádasd2222','2025-07-10 16:34:45','dangcapprolaanhday@gmail.com',NULL,NULL),(38,'SO000038','adidas',3,'2025-07-11 17:00:00','shipped','alo','2025-07-11 15:57:57','dangcapprolaanhday@gmail.com',NULL,NULL),(39,'SO000039','ádas',3,'2025-07-11 23:05:21','shipped','','2025-07-11 16:05:21','dangcapprolaanhday@gmail.com',NULL,NULL),(40,'SO000040','ádas',3,'2025-07-11 23:05:29','shipped','','2025-07-11 16:05:29','dangcapprolaanhday@gmail.com',NULL,NULL),(41,'SO000041','ádas',3,'2025-07-11 23:41:17','shipped','','2025-07-11 16:41:17','dangcapprolaanhday@gmail.com',NULL,NULL),(42,'SO000042','ádas',3,'2025-07-14 03:20:37','shipped','','2025-07-13 20:20:37','dangcapprolaanhday@gmail.com',NULL,NULL),(43,'SO000043','ádas',3,'2025-07-14 03:24:04','shipped','','2025-07-13 20:24:04','dangcapprolaanhday@gmail.com',NULL,NULL),(44,'SO000044','ádas',3,'2025-07-14 03:24:13','shipped','','2025-07-13 20:24:13','',NULL,NULL),(45,'SO000045','ádas',3,'2025-07-14 03:29:16','shipped','','2025-07-13 20:29:16','dangcapprolaanhday@gmail.com',NULL,NULL),(46,'SO000046','ádas',3,'2025-07-15 17:00:00','shipped','','2025-07-15 15:54:20','dangcapprolaanhday@gmail.com',NULL,NULL),(47,'SO000047','ádas',3,'2025-07-16 01:39:27','awaiting_shipment','wasdf','2025-07-15 18:39:27','dangcapprolaanhday@gmail.com',NULL,NULL);
/*!40000 ALTER TABLE `sales_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setting` (
  `id` int NOT NULL AUTO_INCREMENT,
  `key` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_UNIQUE` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` VALUES (1,'SETTING_KEY_EMAIL_APP_USERNAME','123'),(2,'SETTING_KEY_EMAIL_APP_PASSWORD','12'),(4,'test','12');
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_take_details`
--

DROP TABLE IF EXISTS `stock_take_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_take_details` (
  `stock_take_detail_id` int NOT NULL AUTO_INCREMENT,
  `counted_quantity` int NOT NULL,
  `discrepancy` int NOT NULL,
  `product_id` int NOT NULL,
  `stock_take_id` int NOT NULL,
  `system_quantity` int NOT NULL,
  PRIMARY KEY (`stock_take_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_take_details`
--

LOCK TABLES `stock_take_details` WRITE;
/*!40000 ALTER TABLE `stock_take_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_take_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_takes`
--

DROP TABLE IF EXISTS `stock_takes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_takes` (
  `stock_take_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `stock_take_code` varchar(255) NOT NULL,
  `stock_take_date` date NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`stock_take_id`),
  UNIQUE KEY `UKkytaau1l5t3l33a8ltqba6bdv` (`stock_take_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_takes`
--

LOCK TABLES `stock_takes` WRITE;
/*!40000 ALTER TABLE `stock_takes` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_takes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stockinwarddetails`
--

DROP TABLE IF EXISTS `stockinwarddetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stockinwarddetails` (
  `inward_detail_id` int NOT NULL AUTO_INCREMENT,
  `stock_inward_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity_received` int NOT NULL,
  `unit_price_negotiated` decimal(38,2) DEFAULT NULL,
  `unit_purchase_price` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`inward_detail_id`),
  KEY `stock_inward_id` (`stock_inward_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `stockinwarddetails_ibfk_1` FOREIGN KEY (`stock_inward_id`) REFERENCES `stockinwards` (`stock_inward_id`) ON DELETE CASCADE,
  CONSTRAINT `stockinwarddetails_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stockinwarddetails`
--

LOCK TABLES `stockinwarddetails` WRITE;
/*!40000 ALTER TABLE `stockinwarddetails` DISABLE KEYS */;
INSERT INTO `stockinwarddetails` VALUES (9,13,36,2,3.00,4.00),(10,14,39,5,6.00,8.00);
/*!40000 ALTER TABLE `stockinwarddetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stockinwards`
--

DROP TABLE IF EXISTS `stockinwards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stockinwards` (
  `stock_inward_id` int NOT NULL AUTO_INCREMENT,
  `inward_code` varchar(255) DEFAULT NULL,
  `supplier_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `warehouse_id` int DEFAULT NULL,
  `purchase_request_id` int DEFAULT NULL,
  `inward_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `notes` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(30) DEFAULT 'DRAFT',
  PRIMARY KEY (`stock_inward_id`),
  UNIQUE KEY `inward_code` (`inward_code`),
  KEY `supplier_id` (`supplier_id`),
  KEY `user_id` (`user_id`),
  KEY `fk_stockinwards_warehouse` (`warehouse_id`),
  KEY `fk_stockinwards_purchase_request` (`purchase_request_id`),
  CONSTRAINT `fk_stockinwards_purchase_request` FOREIGN KEY (`purchase_request_id`) REFERENCES `purchase_orders` (`request_id`) ON DELETE SET NULL,
  CONSTRAINT `fk_stockinwards_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`warehouse_id`) ON DELETE CASCADE,
  CONSTRAINT `stockinwards_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`) ON DELETE SET NULL,
  CONSTRAINT `stockinwards_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stockinwards`
--

LOCK TABLES `stockinwards` WRITE;
/*!40000 ALTER TABLE `stockinwards` DISABLE KEYS */;
INSERT INTO `stockinwards` VALUES (13,'INW20250718082718',2,4,1,3,'2025-07-18 01:32:05','ádasd','2025-07-17 18:27:29','COMPLETED'),(14,'INW20250718082730',5,4,1,4,'2025-07-18 01:30:26','45','2025-07-17 18:27:38','COMPLETED');
/*!40000 ALTER TABLE `stockinwards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stockoutwarddetails`
--

DROP TABLE IF EXISTS `stockoutwarddetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stockoutwarddetails` (
  `outward_detail_id` int NOT NULL AUTO_INCREMENT,
  `stock_outward_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity_shipped` int NOT NULL,
  PRIMARY KEY (`outward_detail_id`),
  KEY `stock_outward_id` (`stock_outward_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `stockoutwarddetails_ibfk_1` FOREIGN KEY (`stock_outward_id`) REFERENCES `stockoutwards` (`stock_outward_id`) ON DELETE CASCADE,
  CONSTRAINT `stockoutwarddetails_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stockoutwarddetails`
--

LOCK TABLES `stockoutwarddetails` WRITE;
/*!40000 ALTER TABLE `stockoutwarddetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `stockoutwarddetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stockoutwards`
--

DROP TABLE IF EXISTS `stockoutwards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stockoutwards` (
  `stock_outward_id` int NOT NULL AUTO_INCREMENT,
  `outward_code` varchar(50) DEFAULT NULL,
  `sales_order_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `warehouse_id` int DEFAULT NULL,
  `outward_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `reason` enum('sale','internal_transfer','damage','loss','other') NOT NULL,
  `notes` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`stock_outward_id`),
  UNIQUE KEY `outward_code` (`outward_code`),
  KEY `sales_order_id` (`sales_order_id`),
  KEY `user_id` (`user_id`),
  KEY `fk_stockoutwards_warehouse` (`warehouse_id`),
  CONSTRAINT `fk_stockoutwards_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`warehouse_id`) ON DELETE CASCADE,
  CONSTRAINT `stockoutwards_ibfk_1` FOREIGN KEY (`sales_order_id`) REFERENCES `sales_orders` (`sales_order_id`) ON DELETE SET NULL,
  CONSTRAINT `stockoutwards_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stockoutwards`
--

LOCK TABLES `stockoutwards` WRITE;
/*!40000 ALTER TABLE `stockoutwards` DISABLE KEYS */;
/*!40000 ALTER TABLE `stockoutwards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stocktakedetails`
--

DROP TABLE IF EXISTS `stocktakedetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stocktakedetails` (
  `stock_take_detail_id` int NOT NULL AUTO_INCREMENT,
  `stock_take_id` int NOT NULL,
  `product_id` int NOT NULL,
  `system_quantity` int NOT NULL,
  `counted_quantity` int DEFAULT NULL,
  `discrepancy` int GENERATED ALWAYS AS ((`counted_quantity` - `system_quantity`)) STORED,
  PRIMARY KEY (`stock_take_detail_id`),
  KEY `stock_take_id` (`stock_take_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `stocktakedetails_ibfk_1` FOREIGN KEY (`stock_take_id`) REFERENCES `stocktakes` (`stock_take_id`) ON DELETE CASCADE,
  CONSTRAINT `stocktakedetails_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=222 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stocktakedetails`
--

LOCK TABLES `stocktakedetails` WRITE;
/*!40000 ALTER TABLE `stocktakedetails` DISABLE KEYS */;
INSERT INTO `stocktakedetails` (`stock_take_detail_id`, `stock_take_id`, `product_id`, `system_quantity`, `counted_quantity`) VALUES (1,1,21,0,NULL),(2,1,22,0,NULL),(3,1,23,0,NULL),(4,1,24,0,NULL),(5,1,25,0,NULL),(6,1,26,0,NULL),(7,1,27,0,NULL),(8,1,28,0,NULL),(9,1,29,0,NULL),(10,1,30,0,NULL),(11,1,31,0,NULL),(12,1,32,0,NULL),(13,1,33,0,NULL),(14,1,34,0,NULL),(15,1,35,0,NULL),(16,1,36,0,NULL),(17,1,37,0,NULL),(18,1,38,0,NULL),(19,1,39,0,NULL),(20,1,40,0,NULL),(21,1,45,0,NULL),(22,1,46,0,NULL),(32,2,21,130,100),(33,2,22,100,100),(34,2,23,0,NULL),(35,2,24,0,NULL),(36,2,25,0,NULL),(37,2,26,0,NULL),(38,2,27,0,NULL),(39,2,28,0,NULL),(40,2,29,0,NULL),(41,2,30,0,NULL),(42,2,31,0,NULL),(43,2,32,0,NULL),(44,2,33,0,NULL),(45,2,34,0,NULL),(46,2,35,0,NULL),(47,2,36,0,NULL),(48,2,37,0,NULL),(49,2,38,0,NULL),(50,2,39,0,NULL),(51,2,40,0,NULL),(52,2,45,0,NULL),(53,2,46,0,NULL),(63,3,21,130,130),(64,3,22,100,100),(65,3,23,0,NULL),(66,3,24,0,NULL),(67,3,25,0,NULL),(68,3,26,0,NULL),(69,3,27,0,NULL),(70,3,28,0,NULL),(71,3,29,0,NULL),(72,3,30,0,NULL),(73,3,31,0,NULL),(74,3,32,0,NULL),(75,3,33,0,NULL),(76,3,34,0,NULL),(77,3,35,0,NULL),(78,3,36,0,NULL),(79,3,37,0,NULL),(80,3,38,0,NULL),(81,3,39,0,NULL),(82,3,40,0,NULL),(83,3,45,0,NULL),(84,3,46,0,NULL),(85,4,21,130,100),(86,4,22,100,100),(87,4,23,150,133),(88,4,24,100,100),(89,4,25,170,100),(90,4,26,100,100),(91,4,27,100,100),(92,4,28,100,100),(93,4,29,100,100),(94,4,30,100,100),(95,4,31,100,133),(96,4,32,100,113),(97,4,33,100,100),(98,4,34,100,100),(99,4,35,100,100),(100,4,36,100,100),(101,4,37,114,100),(102,4,38,83,100),(103,4,39,72,100),(104,4,40,108,100),(105,4,45,126,100),(106,4,46,107,100),(116,5,21,130,100),(117,5,22,100,100),(118,5,23,150,150),(119,5,24,100,150),(120,5,25,170,150),(121,5,26,100,150),(122,5,27,100,150),(123,5,28,100,150),(124,5,29,100,150),(125,5,30,100,150),(126,5,31,100,150),(127,5,32,100,150),(128,5,33,100,150),(129,5,34,100,150),(130,5,35,100,150),(131,5,36,100,150),(132,5,37,114,150),(133,5,38,83,150),(134,5,39,72,150),(135,5,40,108,150),(136,5,45,126,150),(137,5,46,107,150),(147,6,21,100,10),(148,6,22,100,10),(149,6,23,150,10),(150,6,24,150,10),(151,6,25,150,10),(152,6,26,150,10),(153,6,27,150,150),(154,6,28,150,150),(155,6,29,150,150),(156,6,30,150,150),(157,6,31,150,150),(158,6,32,150,150),(159,6,33,150,150),(160,6,34,150,150),(161,6,35,150,150),(162,6,36,150,150),(163,6,37,150,150),(164,6,38,150,150),(165,6,39,150,150),(166,6,40,150,150),(167,6,45,150,150),(168,6,46,150,0),(169,7,21,10,100),(170,7,22,10,100),(171,7,23,10,1000),(172,7,24,10,100),(173,7,25,10,100),(174,7,26,10,100),(175,7,27,150,100),(176,7,28,150,100),(177,7,29,150,100),(178,7,30,150,100),(179,7,31,150,100),(180,7,32,150,100),(181,7,33,150,100),(182,7,34,150,100),(183,7,35,150,100),(184,7,36,150,100),(185,7,37,150,100),(186,7,38,150,100),(187,7,39,150,100),(188,7,40,150,100),(189,7,45,150,150),(190,7,46,0,100);
/*!40000 ALTER TABLE `stocktakedetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stocktakes`
--

DROP TABLE IF EXISTS `stocktakes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stocktakes` (
  `stock_take_id` int NOT NULL AUTO_INCREMENT,
  `stock_take_code` varchar(50) DEFAULT NULL,
  `user_id` int NOT NULL,
  `warehouse_id` int DEFAULT NULL,
  `stock_take_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` enum('pending','in_progress','completed','reconciled') DEFAULT 'pending',
  `notes` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`stock_take_id`),
  UNIQUE KEY `stock_take_code` (`stock_take_code`),
  KEY `user_id` (`user_id`),
  KEY `fk_stocktakes_warehouse` (`warehouse_id`),
  CONSTRAINT `fk_stocktakes_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`warehouse_id`) ON DELETE CASCADE,
  CONSTRAINT `stocktakes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stocktakes`
--

LOCK TABLES `stocktakes` WRITE;
/*!40000 ALTER TABLE `stocktakes` DISABLE KEYS */;
INSERT INTO `stocktakes` VALUES (1,'ST000001',2,NULL,'2025-06-11 00:00:00','pending','kiểm kê ngày 11/6','2025-06-11 15:27:08'),(2,'ST000002',2,NULL,'2025-06-12 00:00:00','in_progress','test','2025-06-11 17:02:15'),(3,'ST000003',2,NULL,'2025-06-12 00:00:00','in_progress','231ads','2025-06-12 13:39:23'),(4,'ST000004',2,NULL,'2025-06-13 00:00:00','completed','a','2025-06-13 07:34:53'),(5,'ST000005',2,NULL,'2025-06-13 00:00:00','reconciled','1','2025-06-13 08:28:49'),(6,'ST000006',2,NULL,'2025-06-13 00:00:00','reconciled','test','2025-06-13 09:10:32'),(7,'ST000007',2,1,'2025-06-22 00:00:00','reconciled','testtt','2025-06-21 18:24:15');
/*!40000 ALTER TABLE `stocktakes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `supplier_id` int NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(255) NOT NULL,
  `contact_person` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`supplier_id`),
  UNIQUE KEY `supplier_name` (`supplier_name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (1,'Công ty TNHH Thời Trang Ánh Sao','Nguyễn Thị Lan','0901234567','dangcapprolaanhday@gmail.com','123 Đường Lê Lợi, Quận 1, TP.HCM',NULL,'2025-07-16 01:35:06'),(2,'Công ty Cổ phần Dệt May Sài Gòn','Trần Văn Hùng','0912345678','dangcapprolaanhday@gmail.com','456 Đường Trần Hưng Đạo, Quận 5, TP.HCM',NULL,'2025-07-16 01:35:06'),(3,'Công ty TNHH Phụ Kiện Thời Trang','Lê Thị Mai','0923456789','dangcapprolaanhday@gmail.com','789 Đường Nguyễn Huệ, Quận 3, TP.HCM','2025-05-25 18:28:45','2025-07-16 01:35:06'),(4,'Công ty TNHH Quần Áo Thể Thao','Phạm Văn Nam','0934567890','dangcapprolaanhday@gmail.com','321 Đường Hai Bà Trưng, Quận 10, TP.HCM','2025-05-25 18:28:45','2025-07-16 01:35:06'),(5,'Công ty TNHH Thời Trang Nhật Bản','Nguyễn Thị Hồng','0945678901','contact@japanfashion.jp','654 Đường Phạm Ngũ Lão, Quận 1, TP.HCM',NULL,NULL),(6,'Công ty TNHH Dệt May Cao Cấp','Trần Thị Ngọc','0956789012','info78@premiumtextile.vn','987 Đường Lý Thường Kiệt, Quận Tân Bình, TP.HCM',NULL,NULL),(7,'Công ty TNHH Phụ Kiện Da','Lê Văn Tâm','0967890123','support@leatheraccessories.vn','123 Đường Cách Mạng Tháng 8, Quận 10, TP.HCM','2025-05-25 18:28:45','2025-05-25 18:28:45'),(8,'Công ty TNHH Thời Trang Trẻ Em','Phạm Thị Hà','0978901234','sales@kidsfashion.vn','456 Đường Nguyễn Trãi, Quận 5, TP.HCM','2025-05-25 18:28:45','2025-05-25 18:28:45'),(9,'Công ty TNHH Vải May Mặc','Nguyễn Văn Long','0989012345','contact@fabricvn.com','789 Đường Điện Biên Phủ, Quận Bình Thạnh, TP.HCM','2025-05-25 18:28:45','2025-05-25 18:28:45'),(10,'Công ty TNHH Thời Trang Bền Vững','Trần Thị Thanh','0990123456','info@sustainablefashion.vn','321 Đường Võ Thị Sáu, Quận 3, TP.HCM','2025-05-25 18:28:45','2025-05-25 18:28:45'),(11,'test','12','0325910819','a@gmail.com','123',NULL,NULL),(12,'tessttt1233asd','sadasd','56456478786','dasd@gmai.com','123',NULL,NULL);
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notifications`
--

DROP TABLE IF EXISTS `user_notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_notifications` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int NOT NULL,
  `receiver_id` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `message` text,
  `is_read` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notification_id`),
  KEY `sender_id` (`sender_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `user_notifications_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `user_notifications_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notifications`
--

LOCK TABLES `user_notifications` WRITE;
/*!40000 ALTER TABLE `user_notifications` DISABLE KEYS */;
INSERT INTO `user_notifications` VALUES (1,1,2,'test','test',0,'2025-06-22 12:10:59'),(2,1,3,'test','test',1,'2025-06-22 12:10:59'),(3,1,4,'test','test',0,'2025-06-22 12:10:59'),(4,1,2,'ttt','ttt',0,'2025-06-22 12:11:13'),(5,1,3,'ttt','ttt',1,'2025-06-22 12:11:13'),(6,1,4,'ttt','ttt',0,'2025-06-22 12:11:13');
/*!40000 ALTER TABLE `user_notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_active` tinyint(1) DEFAULT '1',
  `last_login` datetime DEFAULT NULL,
  `last_logout` datetime DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','IMc5pWjzi8+pHNsZvfnRGw==:CjgAqxuR2PJHWToh0yP2psgUA0B3lbdBC1bhbTIF2L0=','admin','a@hunght1890.com',NULL,'2025-06-03 14:24:57','2025-07-17 19:06:29',1,'2025-07-18 02:06:29','2025-06-28 18:30:28','admin'),(2,'ware','IMc5pWjzi8+pHNsZvfnRGw==:CjgAqxuR2PJHWToh0yP2psgUA0B3lbdBC1bhbTIF2L0=','avc','a@hunght1891.com','0325910821','2025-06-03 14:24:57','2025-07-17 18:31:55',1,'2025-07-18 01:31:55','2025-06-22 19:11:43','warehouse_staff'),(3,'sale','IMc5pWjzi8+pHNsZvfnRGw==:CjgAqxuR2PJHWToh0yP2psgUA0B3lbdBC1bhbTIF2L0=','aa','a@hunght1892.com','0325910811','2025-06-03 14:24:57','2025-07-15 18:39:48',1,'2025-07-16 01:39:48','2025-06-28 18:25:26','sales_staff'),(4,'pur','IMc5pWjzi8+pHNsZvfnRGw==:CjgAqxuR2PJHWToh0yP2psgUA0B3lbdBC1bhbTIF2L0=','qq','a@hunght1893.com','0325910811','2025-06-03 14:24:57','2025-07-17 18:26:59',1,'2025-07-18 01:26:59','2025-06-28 17:35:33','purchasing_staff'),(5,'hanh.hiep.777@gmail.com','sXCEai/XsamT2x6VTV1HzQ==:kIqt2dccGUnM+SgNOYO2rQsjfH0BpB3kN+KtUXbRoyI=','manager','hiepbqhe171337@fpt.edu.vn','0904034321','2025-07-03 05:10:59','2025-07-17 18:27:49',1,'2025-07-18 01:27:49',NULL,'warehouse_manager'),(7,'hanh.hiep@gmail.com','ErhXwqZh4z4KsvKZydS0Kw==:p3RitMLGfA+8e4uGLiNnEDOe6l7vhHE7jGfsdZa6Ng4=','asd','dangcapprolaanhday1@gmail.com',NULL,'2025-07-03 05:14:40','2025-07-11 15:56:02',1,'2025-07-11 22:56:02',NULL,'warehouse_manager'),(8,'hanh.hieasdp@gmail.com','Ryjr+sfDaRAyDjW9G2TaYw==:yxAVePKe6nNrZ2EW9vpBCAU55hKkJZldBwWBE2J7u3w=','asd','dangcapprolaanhday@gmail.com',NULL,'2025-07-03 05:15:12','2025-07-11 15:56:08',1,'2025-07-11 22:56:08',NULL,'purchasing_staff'),(9,'admin2','28kHg/caCR9RO+Q1WywgLg==:FF34ZTaPwmQmvWnU0nhkXGYXfVFR32pmNkh+Y9tGuHo=','admin','dangcapprolaanhday@gmail.com',NULL,'2025-07-06 11:25:19','2025-07-06 11:39:07',1,'2025-07-06 18:37:36',NULL,'admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouses`
--

DROP TABLE IF EXISTS `warehouses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouses` (
  `warehouse_id` int NOT NULL AUTO_INCREMENT,
  `warehouse_name` varchar(100) NOT NULL,
  `address` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`warehouse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouses`
--

LOCK TABLES `warehouses` WRITE;
/*!40000 ALTER TABLE `warehouses` DISABLE KEYS */;
INSERT INTO `warehouses` VALUES (1,'Kho FPT','Thạch Thất , Hà Nội',NULL),(2,'Kho Hồ Chí Minh','456 Lê Lợi, Quận 1, TP.HCM',NULL),(3,'Kho Sơn La','Sơn La',NULL),(4,'Kho Test','FPT',NULL);
/*!40000 ALTER TABLE `warehouses` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-18  9:27:11
