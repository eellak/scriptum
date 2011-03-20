-- MySQL dump 10.13  Distrib 5.1.49, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ellak
-- ------------------------------------------------------
-- Server version	5.1.49-1ubuntu8.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `application_log`
--

DROP TABLE IF EXISTS `application_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `appuser` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `system` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `class` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `level` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `message` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `msg_data` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `application_log_index1752` (`created`),
  KEY `application_log_index1753` (`appuser`),
  KEY `application_log_index1754` (`system`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_log`
--

LOCK TABLES `application_log` WRITE;
/*!40000 ALTER TABLE `application_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `application_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_parameter`
--

DROP TABLE IF EXISTS `application_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_parameter` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `domain` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `application_parameter_type` int(11) unsigned DEFAULT NULL,
  `parameter_value` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `default_value` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_value` int(11) unsigned DEFAULT NULL,
  `create_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_parameter`
--

LOCK TABLES `application_parameter` WRITE;
/*!40000 ALTER TABLE `application_parameter` DISABLE KEYS */;
/*!40000 ALTER TABLE `application_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_goverment` tinyint(1) DEFAULT NULL,
  `create_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` int(11) unsigned DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `street` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `streetNo` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prefecture` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `postcode` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vatNo` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL,
  `irs` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telephone` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mobile` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fax` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `web` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `web_service` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_type_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_company_company_type1` (`company_type_id`),
  CONSTRAINT `fk_company_company_type1` FOREIGN KEY (`company_type_id`) REFERENCES `company_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'Εταιρεία 1',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2),(2,'Εταιρεία 2',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(3,'Εταιρεία 3',NULL,NULL,0,NULL,NULL,NULL,NULL,'','','','','','','','','','','','','',2),(4,'Εταιρεία 4',NULL,NULL,1,NULL,NULL,NULL,NULL,'','','','','','','','','','','','','',3),(7,'Εταιρεία 8',NULL,NULL,0,NULL,NULL,NULL,'2011-03-16 10:34:11',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_type`
--

DROP TABLE IF EXISTS `company_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `crate_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_type`
--

LOCK TABLES `company_type` WRITE;
/*!40000 ALTER TABLE `company_type` DISABLE KEYS */;
INSERT INTO `company_type` VALUES (1,'Δοκιμαστικός Τύπος Εταιρείας 1',NULL,NULL,NULL,NULL,NULL),(2,'Δοκιμαστικός Τύπος Εταιρείας 2','Δοκιμή 2',NULL,NULL,NULL,NULL),(3,'Δοκιμαστικός Τύπος Εταιρείας 3',NULL,NULL,NULL,NULL,NULL),(6,'Δοκιμαστικός Τύπος Εταιρείας 10',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `company_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `company_id` int(11) unsigned NOT NULL,
  `name` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `surname` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `organization` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `department` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `job` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `postcode` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telephone` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fax` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mobile` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `middlename` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `street` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `streetNo` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prefecture` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `web` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `contact_FKIndex1` (`company_id`),
  CONSTRAINT `fk_7cc2855a-215b-11e0-9059-080027b715d2` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,1,'Γιάννης','Παπαδόπουλος',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Γεώργιος',NULL,NULL,NULL,NULL),(2,2,'John','Smith',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'angelos@uit.gr',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'George',NULL,NULL,NULL,NULL),(3,2,'Angelos','Anagn',NULL,NULL,NULL,NULL,NULL,'Αθήνα',NULL,'angelos@uit.gr',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'G',NULL,NULL,NULL,NULL),(5,2,'test4','tester4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL),(6,1,'tester5','test5',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL),(8,1,'tester7','test7',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL),(9,1,'tester8','test8',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos8',NULL,NULL,NULL,NULL),(10,2,'Tester9','Test9',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos9',NULL,NULL,NULL,NULL),(11,1,'tester10','test10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL),(12,2,'George','Bush',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'W',NULL,NULL,NULL,NULL),(13,3,'Γεωργία','Παπαδοπούλου',NULL,NULL,NULL,NULL,'','',NULL,'','','','',NULL,NULL,NULL,NULL,'Γεώργιος','','','',''),(14,7,'George','Bush',NULL,NULL,NULL,NULL,'','',NULL,'','','','',NULL,NULL,NULL,NULL,'Sr','','','','');
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `distribution_method`
--

DROP TABLE IF EXISTS `distribution_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `distribution_method` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `distribution_method`
--

LOCK TABLES `distribution_method` WRITE;
/*!40000 ALTER TABLE `distribution_method` DISABLE KEYS */;
INSERT INTO `distribution_method` VALUES (1,'ΕΛΤΑ Απλή Αποστολή',NULL,NULL,NULL,NULL),(2,'ΕΛΤΑ Συστημένη Αποστολή',NULL,NULL,NULL,NULL),(3,'Email',NULL,NULL,NULL,NULL),(4,'Fax',NULL,NULL,NULL,NULL),(5,'Courier',NULL,NULL,NULL,NULL),(6,'Προσωπική Παράδοση-Παραλαβή',NULL,NULL,NULL,NULL),(7,'Web Service',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `distribution_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `document_type`
--

DROP TABLE IF EXISTS `document_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document_type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_periodic` int(11) unsigned DEFAULT '0',
  `days` int(11) unsigned DEFAULT NULL,
  `create_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document_type`
--

LOCK TABLES `document_type` WRITE;
/*!40000 ALTER TABLE `document_type` DISABLE KEYS */;
INSERT INTO `document_type` VALUES (1,'Δοκιμαστικός Τύπος 1','Δοκιμή 1',NULL,NULL,NULL,NULL,NULL,NULL),(2,'Δοκιμαστικός Τύπος 2','Δοκιμή 2',NULL,1,NULL,NULL,NULL,NULL),(3,'Δοκιμαστικός Τύπος 3','Δοκιμή 3',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `document_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incoming_protocol`
--

DROP TABLE IF EXISTS `incoming_protocol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incoming_protocol` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `contact_sender_id` int(11) unsigned NOT NULL,
  `distribution_method_id` int(11) unsigned NOT NULL,
  `protocol_number` int(11) unsigned DEFAULT NULL,
  `protocol_date` datetime DEFAULT NULL,
  `protocol_series` int(11) unsigned DEFAULT NULL,
  `protocol_year` int(11) unsigned DEFAULT NULL,
  `subject` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `comments` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `incoming_protocol_number` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'ar. prot. eggrafou',
  `incoming_date` date DEFAULT NULL COMMENT 'hmerominia eggrafou',
  `incoming_place` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `update_user_id` int(11) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `incoming_protocol_FKIndex1` (`distribution_method_id`),
  KEY `incoming_protocol_FKIndex2` (`contact_sender_id`),
  CONSTRAINT `fk_7cbbc300-215b-11e0-9059-080027b715d2` FOREIGN KEY (`distribution_method_id`) REFERENCES `distribution_method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cbdaecc-215b-11e0-9059-080027b715d2` FOREIGN KEY (`contact_sender_id`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incoming_protocol`
--

LOCK TABLES `incoming_protocol` WRITE;
/*!40000 ALTER TABLE `incoming_protocol` DISABLE KEYS */;
INSERT INTO `incoming_protocol` VALUES (12,6,5,12,'2011-02-04 10:23:52',1,2011,'Test','adadfad','aadsfadsf','2011-02-04','eee',NULL,NULL,'2011-02-16 13:49:21','2011-02-23 08:24:11',NULL),(15,12,5,1,'2011-02-10 11:34:07',1,2011,'blabla','2dddccc','435','2011-02-19','thes/ki',NULL,NULL,'2011-02-22 11:34:40','2011-02-22 09:34:40',NULL),(16,5,4,2,'2011-02-12 11:39:10',1,2011,'test4','2adfadf','11111','2011-02-10','adsfadf',NULL,NULL,'2011-02-22 11:39:39','2011-02-22 09:39:39',NULL),(17,6,4,3,'2011-02-17 15:30:19',1,2011,'bla bla bla','asdfasdfa','222','2011-02-03','aaasss',NULL,NULL,'2011-02-22 14:55:17','2011-02-22 13:30:56',NULL),(19,3,3,6,'2011-02-09 15:48:59',1,2011,'Auto Number error test','asdfasdf','asdfadsf','2011-02-26','aaaa',NULL,NULL,'2011-02-22 15:50:49','2011-02-22 13:50:49',NULL),(20,3,6,8,'2011-02-10 15:54:22',1,2011,'another error test','3333','111','2011-02-18','adsfasdf',NULL,NULL,'2011-02-22 15:57:13','2011-02-22 13:57:13',NULL),(21,11,6,13,'2011-02-08 10:33:44',1,2011,'pending test','adsfadf','adsfadf','2011-02-04','333',NULL,NULL,'2011-02-23 10:32:43','2011-02-23 08:33:53',NULL),(22,3,2,15,'2011-02-09 10:41:45',1,2011,'incoming test','sdfadfadf','adsfadf','2011-02-10','test',NULL,NULL,'2011-02-25 10:42:25','2011-02-25 08:42:25',NULL),(23,3,4,16,'2011-03-03 11:41:25',1,2011,'Test incoming','asdfadsf','1231111','2011-03-12','test',NULL,NULL,'2011-03-08 11:41:48','2011-03-08 09:41:48',NULL),(24,3,3,17,'2011-03-02 12:13:43',1,2011,'testtest','adsfasdf','333444','2011-03-03','asdf333',NULL,NULL,'2011-03-08 12:14:18','2011-03-08 10:14:18',NULL),(25,3,3,18,'2011-03-04 12:17:28',1,2011,'test2323','dafdsfadf','33377gg','2011-03-04','adsfadsfe333',NULL,NULL,'2011-03-08 12:17:53','2011-03-08 10:17:53',NULL),(28,5,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2011-03-17 11:07:50','2011-03-17 09:09:11',NULL);
/*!40000 ALTER TABLE `incoming_protocol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outgoing_protocol`
--

DROP TABLE IF EXISTS `outgoing_protocol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `outgoing_protocol` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `distribution_method_id` int(11) unsigned NOT NULL,
  `protocol_number` int(11) unsigned DEFAULT NULL,
  `protocol_date` datetime DEFAULT NULL,
  `protocol_series` int(11) unsigned DEFAULT NULL,
  `protocol_year` int(11) unsigned DEFAULT NULL,
  `subject` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `comments` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `outgoing_date` datetime DEFAULT NULL COMMENT 'imerominia diekperaiwsis',
  `relative_protocol` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'to sxetiko - otidipote',
  `author` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `outgoing_place` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sent_diaygeia` int(1) unsigned DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `update_user_id` int(11) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `outgoing_protocol_FKIndex1` (`distribution_method_id`),
  CONSTRAINT `fk_7cbca1e4-215b-11e0-9059-080027b715d2` FOREIGN KEY (`distribution_method_id`) REFERENCES `distribution_method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outgoing_protocol`
--

LOCK TABLES `outgoing_protocol` WRITE;
/*!40000 ALTER TABLE `outgoing_protocol` DISABLE KEYS */;
INSERT INTO `outgoing_protocol` VALUES (2,2,957200018,'2011-02-03 12:46:00',NULL,NULL,'test',NULL,'2011-02-09 12:46:04',NULL,NULL,NULL,NULL,NULL,NULL,'2011-02-16 10:50:44','2011-02-16 10:46:06',NULL),(3,5,884390021,'2011-02-03 12:48:43',NULL,NULL,'Test 2',NULL,'2011-02-03 12:48:39',NULL,NULL,NULL,NULL,NULL,NULL,'2011-02-16 12:48:05','2011-02-16 10:48:47',NULL),(4,3,13,'2011-03-03 12:22:45',1,2011,'Outgoing pending test1','ffff','2011-03-10 12:22:53',NULL,'adfadf',NULL,NULL,NULL,NULL,'2011-02-16 13:50:03','2011-03-08 10:22:55',NULL),(7,3,11,'2011-02-17 16:21:11',1,2011,'asdfasdf','fasfadsf','2011-02-10 16:21:26',NULL,'asdfasdf',NULL,NULL,NULL,NULL,'2011-02-22 16:21:38','2011-02-22 14:21:38',NULL),(8,3,1,'2011-03-17 10:44:37',1,2011,'Test Outgoing','adfadf','2011-03-11 10:44:51',NULL,'Test writer',NULL,NULL,NULL,NULL,'2011-03-06 10:45:14','2011-03-06 08:45:14',NULL),(9,3,2,'2011-03-02 11:04:29',1,2011,'Email Test2','24234adsfadsf','2011-03-11 11:04:32',NULL,'Test',NULL,NULL,NULL,NULL,'2011-03-06 11:04:45','2011-03-06 09:04:45',NULL),(10,3,3,'2011-03-10 11:35:20',1,2011,'asdfadf',NULL,'2011-03-10 11:35:44',NULL,'32adfaf',NULL,NULL,NULL,NULL,'2011-03-06 11:35:57','2011-03-06 09:35:57',NULL),(11,3,4,'2011-03-02 11:59:22',1,2011,'Test 3','dsfadsf','2011-03-06 11:59:33',NULL,'asdfadsfadf',NULL,NULL,NULL,NULL,'2011-03-06 11:59:54','2011-03-06 09:59:54',NULL),(12,3,5,'2011-03-03 12:25:30',1,2011,'test test','adsfadsf','2011-03-12 12:26:12',NULL,'John SMith',NULL,NULL,NULL,NULL,'2011-03-06 12:26:14','2011-03-06 10:26:14',NULL),(13,3,6,'2011-03-04 12:34:25',1,2011,'test test test',NULL,'2011-03-17 12:34:36',NULL,'bla bla bla',NULL,NULL,NULL,NULL,'2011-03-06 12:35:07','2011-03-06 10:35:07',NULL),(14,3,7,'2011-03-02 12:48:11',1,2011,'email test','adsfadsf','2011-03-11 12:48:22',NULL,'ccccc',NULL,NULL,NULL,NULL,'2011-03-06 12:48:36','2011-03-06 10:48:36',NULL),(15,3,8,'2011-03-04 12:54:18',1,2011,'bla bla bla','adsfasdf','2011-03-05 12:53:59',NULL,'adsfa3333',NULL,NULL,NULL,NULL,'2011-03-06 12:54:22','2011-03-06 10:54:22',NULL),(16,3,9,'2011-03-04 12:56:08',1,2011,'asdfadsf',NULL,'2011-03-04 12:56:30',NULL,'adcccc',NULL,NULL,NULL,NULL,'2011-03-06 12:56:32','2011-03-06 10:56:32',NULL),(17,7,10,'2011-03-03 11:26:46',1,2011,'yat','asdf','2011-03-18 11:27:19',NULL,'test',NULL,NULL,NULL,NULL,'2011-03-07 11:27:21','2011-03-07 09:27:21',NULL),(18,7,11,'2011-03-04 11:31:25',1,2011,'test test','asdfadsf','2011-03-10 11:31:07',NULL,'adsfadf',NULL,NULL,NULL,NULL,'2011-03-07 11:31:29','2011-03-07 09:31:29',NULL),(19,3,12,'2011-03-01 11:14:32',1,2011,'Test out','adsfadf','2011-03-05 11:15:07',NULL,'test test',NULL,NULL,NULL,NULL,'2011-03-08 11:15:11','2011-03-08 09:15:11',NULL),(20,3,14,'2011-03-02 12:25:34',1,2011,'outgoing test','asdadf','2011-03-05 12:25:44',NULL,'aaaaaa',NULL,NULL,NULL,NULL,'2011-03-08 12:26:00','2011-03-08 10:26:00',NULL),(21,3,15,'2011-03-03 12:34:10',1,2011,'test test test','asdfasdfasfd','2011-03-02 12:34:19',NULL,'sdfasdfasdf',NULL,NULL,NULL,NULL,'2011-03-08 12:34:40','2011-03-08 10:34:40',NULL),(22,3,16,'2011-03-02 12:42:26',1,2011,'bla bla bla','adsfadf','2011-03-05 12:42:49',NULL,'dddddd',NULL,NULL,NULL,NULL,'2011-03-08 12:42:53','2011-03-08 10:42:53',NULL),(23,3,17,'2011-03-04 12:50:26',1,2011,'test test test test','adfadf','2011-03-03 12:50:34',NULL,'ddddd',NULL,NULL,NULL,NULL,'2011-03-08 12:50:47','2011-03-08 10:50:47',NULL),(24,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2011-03-17 11:10:50','2011-03-17 09:10:50',NULL);
/*!40000 ALTER TABLE `outgoing_protocol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outgoing_recipient`
--

DROP TABLE IF EXISTS `outgoing_recipient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `outgoing_recipient` (
  `contact_id` int(11) unsigned NOT NULL,
  `outgoing_protocol_id` int(11) unsigned NOT NULL,
  `is_cc` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`contact_id`,`outgoing_protocol_id`),
  KEY `outgoing_recipients_FKIndex1` (`contact_id`),
  KEY `outgoing_recipients_FKIndex2` (`outgoing_protocol_id`),
  CONSTRAINT `fk_7cadf144-215b-11e0-9059-080027b715d2` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7caeb0d4-215b-11e0-9059-080027b715d2` FOREIGN KEY (`outgoing_protocol_id`) REFERENCES `outgoing_protocol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outgoing_recipient`
--

LOCK TABLES `outgoing_recipient` WRITE;
/*!40000 ALTER TABLE `outgoing_recipient` DISABLE KEYS */;
INSERT INTO `outgoing_recipient` VALUES (2,8,0),(2,9,0),(2,10,0),(2,11,0),(2,12,0),(2,13,0),(2,14,0),(2,15,0),(2,16,0),(2,17,0),(2,18,0),(2,19,0),(3,2,0),(3,4,0),(3,7,0),(3,8,1),(3,9,1),(3,10,1),(3,11,1),(3,12,1),(3,13,1),(3,14,1),(3,15,1),(3,16,1),(3,18,1),(3,19,1),(3,20,0),(3,21,0),(3,22,0),(3,23,0),(5,3,0),(5,17,1),(9,7,1);
/*!40000 ALTER TABLE `outgoing_recipient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parameter`
--

DROP TABLE IF EXISTS `parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `initial_value` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `value` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parameter`
--

LOCK TABLES `parameter` WRITE;
/*!40000 ALTER TABLE `parameter` DISABLE KEYS */;
INSERT INTO `parameter` VALUES (1,NULL,NULL,'DISTRIBUTION_METHOD_EMAIL_ID',NULL,NULL,'3',NULL,NULL,NULL,NULL),(2,NULL,NULL,'DISTRIBUTION_METHOD_WEBSERVICE_ID',NULL,NULL,'7',NULL,NULL,NULL,NULL),(3,NULL,NULL,'SMTP_HOST',NULL,NULL,'mail.illumine.gr',NULL,NULL,NULL,NULL),(4,NULL,NULL,'SMTP_PASSWORD',NULL,NULL,'XINU666+1',NULL,NULL,NULL,NULL),(5,NULL,NULL,'SMTP_USER',NULL,NULL,'mountrakis@illumine.gr',NULL,NULL,NULL,NULL),(6,NULL,NULL,'EMAIL_FROM',NULL,NULL,'mountrakis@illumine.gr',NULL,NULL,NULL,NULL),(7,NULL,NULL,'OKM_NODE_PENDING_INCOMING',NULL,NULL,'/okm:root/PendingIncoming',NULL,NULL,NULL,NULL),(8,NULL,NULL,'OKM_NODE_INCOMING',NULL,NULL,'/okm:root/IncomingProtocol',NULL,NULL,NULL,NULL),(9,NULL,NULL,'OKM_NODE_PENDING_OUTGOING',NULL,NULL,'/okm:root/PendingOutgoing',NULL,NULL,NULL,NULL),(10,NULL,NULL,'OKM_NODE_OUTGOING',NULL,NULL,'/okm:root/OutgoingProtocol',NULL,NULL,NULL,NULL),(11,NULL,NULL,'PROTOCOL_BOOK_COMPANY',NULL,NULL,'ΕΛΛΑΚ',NULL,NULL,NULL,NULL),(12,NULL,NULL,'PROTOCOL_BOOK_AUTHOR',NULL,NULL,'ΕΛΛΑΚ',NULL,NULL,NULL,NULL),(13,NULL,NULL,'PROTOCOL_BOOK_CREATOR',NULL,NULL,'ΕΛΛΑΚ',NULL,NULL,NULL,NULL),(14,NULL,NULL,'PROTOCOL_BOOK_KEYWORDS',NULL,NULL,'Βιβλίο Πρωτοκόλλου',NULL,NULL,NULL,NULL),(15,NULL,NULL,'PROTOCOL_BOOK_SUBJECT',NULL,NULL,'Βιβλίο Πρωτοκόλλου',NULL,NULL,NULL,NULL),(16,NULL,NULL,'PROTOCOL_BOOK_TITLE',NULL,NULL,'Βιβλίο Πρωτοκόλλου',NULL,NULL,NULL,NULL),(17,NULL,NULL,'PROTOCOL_BOOK_FILE',NULL,NULL,'protocol_book.pdf',NULL,NULL,NULL,NULL),(18,NULL,NULL,'PROTOCOL_BOOK_FONT_FOLDER',NULL,NULL,'/home/aanagnostopoulos/workspace/eProtocol/WebContent/fonts',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_creator_id` int(11) unsigned NOT NULL,
  `name` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_dt` datetime DEFAULT NULL,
  `expected_dt` datetime DEFAULT NULL,
  `end_dt` datetime DEFAULT NULL,
  `create_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `project_FKIndex1` (`user_creator_id`),
  CONSTRAINT `fk_7cb85cba-215b-11e0-9059-080027b715d2` FOREIGN KEY (`user_creator_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_task`
--

DROP TABLE IF EXISTS `project_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_task` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `contact_id` int(11) unsigned NOT NULL,
  `parent_task_id` int(11) unsigned NOT NULL,
  `user_creator_id` int(11) unsigned NOT NULL,
  `user_dispatcher_id` int(11) unsigned NOT NULL,
  `project_id` int(11) unsigned NOT NULL,
  `task_document_id` int(11) unsigned NOT NULL,
  `task_result_id` int(11) unsigned NOT NULL,
  `task_priority_id` int(11) unsigned NOT NULL,
  `task_type_id` int(11) unsigned NOT NULL,
  `task_state_id` int(11) unsigned NOT NULL,
  `name` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `task_no` int(11) unsigned DEFAULT NULL,
  `start_dt` datetime DEFAULT NULL,
  `expected_dt` datetime DEFAULT NULL,
  `closed_dt` datetime DEFAULT NULL,
  `comments` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `times_revised` int(11) unsigned DEFAULT '0',
  `times_contact_external` int(11) unsigned DEFAULT '0',
  `reviewer_score` int(11) unsigned DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `project_tasks_FKIndex1` (`project_id`),
  KEY `project_tasks_FKIndex2` (`task_state_id`),
  KEY `project_tasks_FKIndex3` (`task_type_id`),
  KEY `project_tasks_FKIndex4` (`task_priority_id`),
  KEY `project_tasks_FKIndex5` (`task_result_id`),
  KEY `project_tasks_FKIndex6` (`task_document_id`),
  KEY `project_tasks_FKIndex7` (`user_creator_id`),
  KEY `project_tasks_FKIndex8` (`user_dispatcher_id`),
  KEY `project_task_FKIndex9` (`parent_task_id`),
  KEY `project_task_FKIndex10` (`contact_id`),
  CONSTRAINT `fk_7caf6d6c-215b-11e0-9059-080027b715d2` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cb02aa4-215b-11e0-9059-080027b715d2` FOREIGN KEY (`task_state_id`) REFERENCES `task_state` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cb0faba-215b-11e0-9059-080027b715d2` FOREIGN KEY (`task_type_id`) REFERENCES `task_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cb1bb12-215b-11e0-9059-080027b715d2` FOREIGN KEY (`task_priority_id`) REFERENCES `task_priority` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cb42b18-215b-11e0-9059-080027b715d2` FOREIGN KEY (`task_result_id`) REFERENCES `task_result` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cb5d6b6-215b-11e0-9059-080027b715d2` FOREIGN KEY (`task_document_id`) REFERENCES `task_document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cb6adf2-215b-11e0-9059-080027b715d2` FOREIGN KEY (`user_creator_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cb7739a-215b-11e0-9059-080027b715d2` FOREIGN KEY (`user_dispatcher_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cb931e4-215b-11e0-9059-080027b715d2` FOREIGN KEY (`parent_task_id`) REFERENCES `project_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cc393e6-215b-11e0-9059-080027b715d2` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_task`
--

LOCK TABLES `project_task` WRITE;
/*!40000 ALTER TABLE `project_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_user`
--

DROP TABLE IF EXISTS `project_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_user` (
  `project_id` int(11) unsigned NOT NULL,
  `users_id` int(11) unsigned NOT NULL,
  `create_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`project_id`,`users_id`),
  KEY `project_users_FKIndex1` (`project_id`),
  KEY `project_users_FKIndex2` (`users_id`),
  CONSTRAINT `fk_7cb27d0e-215b-11e0-9059-080027b715d2` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cb369ee-215b-11e0-9059-080027b715d2` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_user`
--

LOCK TABLES `project_user` WRITE;
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `protocol_book`
--

DROP TABLE IF EXISTS `protocol_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `protocol_book` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `protocol_series` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_incoming` int(1) DEFAULT NULL,
  `protocol_number` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '''o teleytaios arithmos protokolou''',
  `protocol_year` int(4) DEFAULT NULL,
  `protocol_path` varchar(640) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_active` int(1) NOT NULL DEFAULT '1' COMMENT 'shows if this protocol book is active',
  `create_user` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `protocol_book`
--

LOCK TABLES `protocol_book` WRITE;
/*!40000 ALTER TABLE `protocol_book` DISABLE KEYS */;
/*!40000 ALTER TABLE `protocol_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `protocol_document`
--

DROP TABLE IF EXISTS `protocol_document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `protocol_document` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `outgoing_protocol_id` int(11) unsigned DEFAULT NULL,
  `incoming_protocol_id` int(11) unsigned DEFAULT NULL,
  `document_types_id` int(11) unsigned NOT NULL,
  `document_name` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT 'the filename',
  `document_size` bigint(20) NOT NULL COMMENT 'the filesize OPEN KM ASSIGNS THIS!',
  `document_mime` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `document_number` int(11) NOT NULL COMMENT 'the index in protocol node',
  `document_keywords` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'GUI Assigns',
  `okm_path` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'OpenKM path for document OPEN KM ASSIGNS THIS!',
  `okm_uuid` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'OpenKM internal id. OPEN KM ASSIGNS THIS!',
  PRIMARY KEY (`id`),
  KEY `protocol_document_FKIndex1` (`incoming_protocol_id`),
  KEY `protocol_document_FKIndex2` (`outgoing_protocol_id`),
  KEY `fk_protocol_document_document_types1` (`document_types_id`),
  CONSTRAINT `fk_7cbf98ea-215b-11e0-9059-080027b715d2` FOREIGN KEY (`incoming_protocol_id`) REFERENCES `incoming_protocol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cc08868-215b-11e0-9059-080027b715d2` FOREIGN KEY (`outgoing_protocol_id`) REFERENCES `outgoing_protocol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_protocol_document_document_types1` FOREIGN KEY (`document_types_id`) REFERENCES `document_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `protocol_document`
--

LOCK TABLES `protocol_document` WRITE;
/*!40000 ALTER TABLE `protocol_document` DISABLE KEYS */;
INSERT INTO `protocol_document` VALUES (24,2,NULL,2,'ellak.sql',43052,NULL,1,'test2','/okm:root/OutgoingProtocol/2/ellak.sql','02fadc19-6a95-4cd7-9dc2-46d68c34b3dc'),(26,3,NULL,2,'velocity.log',645,NULL,1,'test1','/okm:root/OutgoingProtocol/3/velocity.log','f93c1069-6f1a-4541-8053-0c756af6e88f'),(30,NULL,15,2,'velocity.log',645,NULL,1,'adfadsf','/okm:root/IncomingProtocol/15/velocity.log','46fcc74a-d138-4cf3-b76d-f8146a7cde52'),(31,NULL,16,2,'velocity.log',645,NULL,1,'2233dd','/okm:root/IncomingProtocol/16/velocity.log','736c25e9-4220-4d33-98a3-9a36dffd2035'),(32,NULL,17,1,'resources_en.xls',24064,NULL,1,'asdfad','/okm:root/IncomingProtocol/17/resources_en.xls','d4e84786-d1fd-49e8-b9c9-82b05e8be65e'),(34,NULL,19,2,'resources_en.xls',24064,NULL,1,'asdfaf','/okm:root/IncomingProtocol/19/resources_en.xls','5a3906ba-15c4-47ea-bce1-c30263bcf389'),(35,NULL,20,2,'velocity.log',645,NULL,1,'asdf','/okm:root/IncomingProtocol/20/velocity.log','b94dcd9a-9e0c-49b0-8c3c-75e5d5c9b34e'),(38,7,NULL,1,'dapple.zip',234336,NULL,1,'adsfasd','/okm:root/OutgoingProtocol/7/dapple.zip','0a8bc8e0-4e0d-4904-bda3-81ce49777b75'),(39,NULL,12,2,'ellak.sql',43052,NULL,1,'test1','/okm:root/IncomingProtocol/12-1-2011/ellak.sql','7654a7f7-4e95-4794-be10-caa5f4d88b70'),(40,NULL,21,2,'resources_en.xls',24064,NULL,1,'adsfadf','/okm:root/IncomingProtocol/13-1-2011/resources_en.xls','bf5473b8-62e4-447d-8e70-8a5355d6bc67'),(41,NULL,22,1,'help_en.zip',1405,NULL,1,'asdadsf','/okm:root/IncomingProtocol/15-1-2011/help_en.zip','8e43541a-f1f0-42a9-a17f-8394ffea07fb'),(42,8,NULL,1,'Anagnostopoulos_Symbasi_V1.1.doc',29184,'application/msword',1,'test','/okm:root/OutgoingProtocol/1-1-2011/Anagnostopoulos_Symbasi_V1.1.doc','13019e4a-a3d0-4147-9f5a-985c15c26480'),(43,9,NULL,1,'Anagnostopoulos_Symbasi_V1.1.doc',29184,'application/msword',1,'asdadf','/okm:root/OutgoingProtocol/2-1-2011/Anagnostopoulos_Symbasi_V1.1.doc','5c7089a9-dc7d-4a45-9f2e-7a1cc7a325a5'),(44,10,NULL,1,'Anagnostopoulos_Symbasi_V1.1.doc',29184,'application/msword',1,'asdfa','/okm:root/OutgoingProtocol/3-1-2011/Anagnostopoulos_Symbasi_V1.1.doc','4f2024ac-0201-4816-ada7-69fc272c88b3'),(45,11,NULL,1,'Anagnostopoulos_Symbasi_V1.1.doc',29184,'application/msword',1,'dfadsfadsf','/okm:root/OutgoingProtocol/4-1-2011/Anagnostopoulos_Symbasi_V1.1.doc','775002b1-e8d3-4a4b-aef1-1efc46d95a81'),(46,12,NULL,1,'Anagnostopoulos_Symbasi_V1.1.pdf',56142,'application/pdf',1,'sdfadfaf','/okm:root/OutgoingProtocol/5-1-2011/Anagnostopoulos_Symbasi_V1.1.pdf','909388f1-7475-430d-a736-248ae5575c4d'),(47,13,NULL,1,'Anagnostopoulos_Symbasi_V1.1.pdf',56142,'application/pdf',1,'adsfasdfadsf','/okm:root/OutgoingProtocol/6-1-2011/Anagnostopoulos_Symbasi_V1.1.pdf','f6737ef7-76fc-4694-9cda-15f0ee5df6b4'),(48,14,NULL,1,'Anagnostopoulos_Symbasi_V1.1.doc',29184,'application/msword',1,'asdf','/okm:root/OutgoingProtocol/7-1-2011/Anagnostopoulos_Symbasi_V1.1.doc','126af06f-0aa3-4050-b5de-fcbbf11f1ba4'),(49,15,NULL,1,'Anagnostopoulos_Symbasi_V1.1.pdf',56142,'application/pdf',1,'afadsf','/okm:root/OutgoingProtocol/8-1-2011/Anagnostopoulos_Symbasi_V1.1.pdf','714ee2f9-5179-4dde-be09-26463c9b907c'),(50,16,NULL,2,'Anagnostopoulos_Symbasi_V1.1.doc',29184,'application/msword',1,'fdasdfadsf','/okm:root/OutgoingProtocol/9-1-2011/Anagnostopoulos_Symbasi_V1.1.doc','2d6c0ddc-5023-4918-a4ba-a37c66dc929f'),(51,17,NULL,1,'Anagnostopoulos_Symbasi_V1.1.pdf',56142,'application/pdf',1,'2222','/okm:root/OutgoingProtocol/10-1-2011/Anagnostopoulos_Symbasi_V1.1.pdf','f10bff87-3fd5-40ff-aee3-82bdc3dacd72'),(52,18,NULL,1,'Anagnostopoulos_Symbasi_V1.1.pdf',56142,'application/pdf',1,'ads333','/okm:root/OutgoingProtocol/11-1-2011/Anagnostopoulos_Symbasi_V1.1.pdf','486952c9-743e-4ded-9f5a-008e8fae558f'),(53,19,NULL,1,'help_en.zip',1405,'application/zip',1,'adsfadf','/okm:root/OutgoingProtocol/12-1-2011/help_en.zip','7f5a2727-800e-49b9-a64f-2ba97a34fede'),(54,NULL,23,1,'dapple.zip',234336,'application/zip',1,'23434','/okm:root/IncomingProtocol/16-1-2011/dapple.zip','960c3be1-13a4-4a16-b03f-38426a8ca6d9'),(55,NULL,24,1,'dapple.zip',234336,'application/zip',1,'3333','/okm:root/IncomingProtocol/17-1-2011/dapple.zip','4a62476e-7492-479d-af48-50c65187242c'),(56,NULL,25,1,'Copy of resources_en.xls',32768,'application/vnd.ms-excel',1,'dfff','/okm:root/IncomingProtocol/18-1-2011/Copy of resources_en.xls','9b9226fa-a52d-4786-9a10-0fe3b00b8bc4'),(57,4,NULL,1,'Copy of resources_en.xls',32768,'application/vnd.ms-excel',1,'dsfadsf','/okm:root/OutgoingProtocol/13-1-2011/Copy of resources_en.xls','301abafc-ea8e-46c2-97cf-25e00bac896c'),(58,20,NULL,1,'Copy of resources_en.xls',32768,'application/vnd.ms-excel',1,'23434','/okm:root/OutgoingProtocol/14-1-2011/Copy of resources_en.xls','ea98c2d8-394f-4208-aa27-5ced55175cab'),(59,21,NULL,1,'Anagnostopoulos_Symbasi_V1.1.doc',29184,'application/msword',1,'asdfasdf','/okm:root/OutgoingProtocol/15-1-2011/Anagnostopoulos_Symbasi_V1.1.doc','bd1866a2-986d-4703-881f-65b61b922cd3'),(60,22,NULL,1,'Anagnostopoulos_Symbasi_V1.1.doc',29184,'application/msword',1,'asdfadf','/okm:root/OutgoingProtocol/16-1-2011/Anagnostopoulos_Symbasi_V1.1.doc','71bd6dfd-ba68-4f2b-a4b1-7069d66575cd'),(61,23,NULL,1,'Anagnostopoulos_Symbasi_V1.1.doc',29184,'application/msword',1,'asdfadsf','/okm:root/OutgoingProtocol/17-1-2011/Anagnostopoulos_Symbasi_V1.1.doc','ee276fd5-91e9-4e57-aa78-9dc16b7214a3'),(62,NULL,28,1,'export_Contact.csv',1378,'text/csv',1,'export','/okm:root/PendingIncoming/28/export_Contact.csv','4873ec4e-6eb5-4f94-8465-6206dc34d501');
/*!40000 ALTER TABLE `protocol_document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `protocol_number`
--

DROP TABLE IF EXISTS `protocol_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `protocol_number` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `number` bigint(20) DEFAULT NULL,
  `series` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `protocol_number`
--

LOCK TABLES `protocol_number` WRITE;
/*!40000 ALTER TABLE `protocol_number` DISABLE KEYS */;
INSERT INTO `protocol_number` VALUES (1,18,18,1,2011,0,NULL,'2011-02-25 08:40:19'),(2,17,17,1,2011,1,NULL,NULL);
/*!40000 ALTER TABLE `protocol_number` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `resource` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_protocol` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN',NULL,1),(2,'READER',NULL,1),(3,'WRITER',NULL,1);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_to_resource`
--

DROP TABLE IF EXISTS `role_to_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_to_resource` (
  `role_id` int(11) unsigned NOT NULL,
  `resource_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`),
  KEY `roles_to_resource_FKIndex1` (`role_id`),
  KEY `roles_to_resource_FKIndex2` (`resource_id`),
  CONSTRAINT `fk_7cac7e54-215b-11e0-9059-080027b715d2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_7cad2692-215b-11e0-9059-080027b715d2` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_to_resource`
--

LOCK TABLES `role_to_resource` WRITE;
/*!40000 ALTER TABLE `role_to_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_to_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_document`
--

DROP TABLE IF EXISTS `task_document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_document` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `document_name` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '''the filename''',
  `document_size` varchar(11) COLLATE utf8_unicode_ci NOT NULL COMMENT '''the filesize''',
  `document_number` int(11) NOT NULL COMMENT 'the index of doc in task',
  `document_keywords` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '''comma sep keywirds''',
  `document_type_id` int(11) unsigned NOT NULL,
  `okm_path` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `okm_uuid` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_task_document_document_type1` (`document_type_id`),
  CONSTRAINT `fk_task_document_document_type1` FOREIGN KEY (`document_type_id`) REFERENCES `document_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_document`
--

LOCK TABLES `task_document` WRITE;
/*!40000 ALTER TABLE `task_document` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_message`
--

DROP TABLE IF EXISTS `task_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_receiver_id` int(11) unsigned NOT NULL,
  `user_sender_id` int(11) unsigned NOT NULL,
  `project_task_id` int(11) unsigned NOT NULL,
  `subject` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `message` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `task_messages_FKIndex1` (`project_task_id`),
  KEY `task_message_FKIndex2` (`user_sender_id`),
  KEY `task_message_FKIndex3` (`user_receiver_id`),
  CONSTRAINT `fk_7cb4f340-215b-11e0-9059-080027b715d2` FOREIGN KEY (`project_task_id`) REFERENCES `project_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cba0a42-215b-11e0-9059-080027b715d2` FOREIGN KEY (`user_sender_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cbad8e6-215b-11e0-9059-080027b715d2` FOREIGN KEY (`user_receiver_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_message`
--

LOCK TABLES `task_message` WRITE;
/*!40000 ALTER TABLE `task_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_priority`
--

DROP TABLE IF EXISTS `task_priority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_priority` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_priority`
--

LOCK TABLES `task_priority` WRITE;
/*!40000 ALTER TABLE `task_priority` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_priority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_result`
--

DROP TABLE IF EXISTS `task_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_result` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_result`
--

LOCK TABLES `task_result` WRITE;
/*!40000 ALTER TABLE `task_result` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_state`
--

DROP TABLE IF EXISTS `task_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_state` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_state`
--

LOCK TABLES `task_state` WRITE;
/*!40000 ALTER TABLE `task_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_type`
--

DROP TABLE IF EXISTS `task_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_periodic` int(11) unsigned DEFAULT '0',
  `day_period` int(11) unsigned DEFAULT NULL,
  `create_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_type`
--

LOCK TABLES `task_type` WRITE;
/*!40000 ALTER TABLE `task_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_to_role`
--

DROP TABLE IF EXISTS `user_to_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_to_role` (
  `users_id` int(11) unsigned NOT NULL,
  `role_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`users_id`,`role_id`),
  KEY `users_to_roles_FKIndex1` (`users_id`),
  KEY `users_to_roles_FKIndex2` (`role_id`),
  CONSTRAINT `fk_7cab0434-215b-11e0-9059-080027b715d2` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_7cabb168-215b-11e0-9059-080027b715d2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_to_role`
--

LOCK TABLES `user_to_role` WRITE;
/*!40000 ALTER TABLE `user_to_role` DISABLE KEYS */;
INSERT INTO `user_to_role` VALUES (2,1),(3,2),(4,3);
/*!40000 ALTER TABLE `user_to_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `users_manager_id` int(11) unsigned DEFAULT NULL,
  `username` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `surname` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_disabled` tinyint(1) DEFAULT NULL,
  `create_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_user` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `users_FKIndex1` (`users_manager_id`),
  CONSTRAINT `fk_7cc17c3c-215b-11e0-9059-080027b715d2` FOREIGN KEY (`users_manager_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,NULL,'user1','user1','user','1',0,NULL,NULL,NULL,NULL),(3,NULL,'reader1','reader1','John','Doe',0,NULL,NULL,NULL,NULL),(4,NULL,'writer1','writer1','John','Writer',0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-03-18 10:21:27
