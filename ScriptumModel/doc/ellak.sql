-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ellak
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

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
INSERT INTO `company` VALUES (1,'Εταιρεία 1',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2),(2,'Εταιρεία 2',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(3,'Εταιρεία 3',NULL,NULL,0,NULL,NULL,NULL,NULL,'','','','','','','','','','','','','',2),(7,'Εταιρεία 8',NULL,NULL,0,NULL,NULL,NULL,'2011-03-16 10:34:11',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6);
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_type`
--

LOCK TABLES `company_type` WRITE;
/*!40000 ALTER TABLE `company_type` DISABLE KEYS */;
INSERT INTO `company_type` VALUES (1,'Δοκιμαστικός Τύπος Εταιρείας 1',NULL,NULL,NULL,NULL,NULL),(2,'Δοκιμαστικός Τύπος Εταιρείας 2','Δοκιμή 2',NULL,NULL,NULL,NULL),(3,'Δοκιμαστικός Τύπος Εταιρείας 3',NULL,NULL,NULL,NULL,NULL),(6,'Δοκιμαστικός Τύπος Εταιρείας 10',NULL,NULL,NULL,NULL,NULL),(7,'������������ ����� ��������� 2',NULL,NULL,NULL,NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (2,2,'John','Smith',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'angelos@uit.gr',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'George',NULL,NULL,NULL,NULL),(3,2,'Angelos','Anagn',NULL,NULL,NULL,NULL,NULL,'Αθήνα',NULL,'angelos@uit.gr',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'G',NULL,NULL,NULL,NULL),(5,2,'test4','tester4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL),(6,1,'tester5','test5',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL),(8,1,'tester7','test7',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL),(9,1,'tester8','test8',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos8',NULL,NULL,NULL,NULL),(10,2,'Tester9','Test9',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos9',NULL,NULL,NULL,NULL),(11,1,'tester10','test10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL),(12,2,'George','Bush',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'W',NULL,NULL,NULL,NULL),(13,3,'Γεωργία','Παπαδοπούλου',NULL,NULL,NULL,NULL,'','',NULL,'','','','',NULL,NULL,NULL,NULL,'Γεώργιος','','','',''),(14,7,'George','Bush',NULL,NULL,NULL,NULL,'','',NULL,'','','','',NULL,NULL,NULL,NULL,'Sr','','','',''),(16,2,'Jack','Smith',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'G',NULL,NULL,NULL,NULL),(18,7,'Dohn','George',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(19,7,'Bong','George',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,7,'Collins','Jack',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,2,'test1','﻿test4',NULL,NULL,NULL,NULL,'test','Αθήνα',NULL,'test','test','test','test',NULL,NULL,NULL,NULL,'testostest','test','tes','test','test');
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diavgeia_decision_type`
--

DROP TABLE IF EXISTS `diavgeia_decision_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diavgeia_decision_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `supported` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diavgeia_decision_type`
--

LOCK TABLES `diavgeia_decision_type` WRITE;
/*!40000 ALTER TABLE `diavgeia_decision_type` DISABLE KEYS */;
INSERT INTO `diavgeia_decision_type` VALUES (1,NULL,'Type1',NULL,NULL),(2,NULL,'Type2',NULL,NULL),(3,NULL,'Type3',NULL,NULL);
/*!40000 ALTER TABLE `diavgeia_decision_type` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `distribution_method`
--

LOCK TABLES `distribution_method` WRITE;
/*!40000 ALTER TABLE `distribution_method` DISABLE KEYS */;
INSERT INTO `distribution_method` VALUES (1,'ΕΛΤΑ Απλή Αποστολή',NULL,NULL,NULL,NULL),(2,'ΕΛΤΑ Συστημένη Αποστολή',NULL,NULL,NULL,NULL),(3,'Email',NULL,NULL,NULL,NULL),(4,'Fax',NULL,NULL,NULL,NULL),(5,'Courier',NULL,NULL,NULL,NULL),(6,'Προσωπική Παράδοση-Παραλαβή',NULL,NULL,NULL,NULL),(7,'Web Service',NULL,NULL,NULL,NULL),(8,'Μη διαθέσιμη',NULL,NULL,NULL,NULL);
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
  `relative_protocol` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `incoming_protocol_FKIndex1` (`distribution_method_id`),
  KEY `incoming_protocol_FKIndex2` (`contact_sender_id`),
  CONSTRAINT `fk_7cbbc300-215b-11e0-9059-080027b715d2` FOREIGN KEY (`distribution_method_id`) REFERENCES `distribution_method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cbdaecc-215b-11e0-9059-080027b715d2` FOREIGN KEY (`contact_sender_id`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incoming_protocol`
--

LOCK TABLES `incoming_protocol` WRITE;
/*!40000 ALTER TABLE `incoming_protocol` DISABLE KEYS */;
INSERT INTO `incoming_protocol` VALUES (31,3,8,2,'2011-05-26 11:48:09',NULL,NULL,'Dokimi',NULL,'123','2011-05-11','test',2,2,'2011-05-26 11:47:32','2011-05-26 08:48:09',NULL,NULL);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outgoing_protocol`
--

LOCK TABLES `outgoing_protocol` WRITE;
/*!40000 ALTER TABLE `outgoing_protocol` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parameter`
--

LOCK TABLES `parameter` WRITE;
/*!40000 ALTER TABLE `parameter` DISABLE KEYS */;
INSERT INTO `parameter` VALUES (1,NULL,NULL,'DISTRIBUTION_METHOD_EMAIL_ID',NULL,NULL,'3',NULL,NULL,NULL,NULL),(2,NULL,NULL,'DISTRIBUTION_METHOD_WEBSERVICE_ID',NULL,NULL,'7',NULL,NULL,NULL,NULL),(3,NULL,NULL,'SMTP_HOST',NULL,NULL,'mail.illumine.gr',NULL,NULL,NULL,NULL),(4,NULL,NULL,'SMTP_PASSWORD',NULL,NULL,'XINU666+1',NULL,NULL,NULL,NULL),(5,NULL,NULL,'SMTP_USER',NULL,NULL,'mountrakis@illumine.gr',NULL,NULL,NULL,NULL),(6,NULL,NULL,'EMAIL_FROM',NULL,NULL,'mountrakis@illumine.gr',NULL,NULL,NULL,NULL),(7,NULL,NULL,'OKM_NODE_PENDING_INCOMING',NULL,NULL,'/okm:root/PendingIncoming',NULL,NULL,NULL,NULL),(8,NULL,NULL,'OKM_NODE_INCOMING',NULL,NULL,'/okm:root/IncomingProtocol',NULL,NULL,NULL,NULL),(9,NULL,NULL,'OKM_NODE_PENDING_OUTGOING',NULL,NULL,'/okm:root/PendingOutgoing',NULL,NULL,NULL,NULL),(10,NULL,NULL,'OKM_NODE_OUTGOING',NULL,NULL,'/okm:root/OutgoingProtocol',NULL,NULL,NULL,NULL),(11,NULL,NULL,'PROTOCOL_BOOK_COMPANY',NULL,NULL,'ΕΛΛΑΚ',NULL,NULL,NULL,NULL),(12,NULL,NULL,'PROTOCOL_BOOK_AUTHOR',NULL,NULL,'ΕΛΛΑΚ',NULL,NULL,NULL,NULL),(13,NULL,NULL,'PROTOCOL_BOOK_CREATOR',NULL,NULL,'ΕΛΛΑΚ',NULL,NULL,NULL,NULL),(14,NULL,NULL,'PROTOCOL_BOOK_KEYWORDS',NULL,NULL,'Βιβλίο Πρωτοκόλλου',NULL,NULL,NULL,NULL),(15,NULL,NULL,'PROTOCOL_BOOK_SUBJECT',NULL,NULL,'Βιβλίο Πρωτοκόλλου',NULL,NULL,NULL,NULL),(16,NULL,NULL,'PROTOCOL_BOOK_TITLE',NULL,NULL,'Βιβλίο Πρωτοκόλλου',NULL,NULL,NULL,NULL),(17,NULL,NULL,'PROTOCOL_BOOK_FILE',NULL,NULL,'protocol_book.pdf',NULL,NULL,NULL,NULL),(18,NULL,NULL,'PROTOCOL_BOOK_FONT_FOLDER',NULL,NULL,'/home/aanagnostopoulos/workspace/eProtocol/WebContent/fonts',NULL,NULL,NULL,NULL),(19,NULL,NULL,'DISTRIBUTION_METHOD_NA_ID',NULL,NULL,'8',NULL,NULL,NULL,NULL),(20,NULL,NULL,'OKM_AUTH_PORT_ADDRESS',NULL,NULL,'http://localhost:8080/OpenKM/OKMAuth',NULL,NULL,NULL,NULL),(21,NULL,NULL,'OKM_DOCUMENT_PORT_ADDRESS',NULL,NULL,'http://localhost:8080/OpenKM/OKMDocument',NULL,NULL,NULL,NULL),(22,NULL,NULL,'OKM_FOLDER_PORT_ADDRESS',NULL,NULL,'http://localhost:8080/OpenKM/OKMFolder',NULL,NULL,NULL,NULL),(23,NULL,NULL,'OKM_SEARCH_PORT_ADDRESS',NULL,NULL,'http://localhost:8080/OpenKM/OKMSearch',NULL,NULL,NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `protocol_document`
--

LOCK TABLES `protocol_document` WRITE;
/*!40000 ALTER TABLE `protocol_document` DISABLE KEYS */;
INSERT INTO `protocol_document` VALUES (65,NULL,31,2,'casaparts.log.1',1048644,'application/octet-stream',1,'adsfadsf','/okm:root/IncomingProtocol/2/casaparts.log.1','96b8a57d-4692-4f3d-8eea-5bccd01030ce');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `protocol_number`
--

LOCK TABLES `protocol_number` WRITE;
/*!40000 ALTER TABLE `protocol_number` DISABLE KEYS */;
INSERT INTO `protocol_number` VALUES (1,2,2,1,2011,2,NULL,'2011-05-26 08:48:09');
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
INSERT INTO `user_to_role` VALUES (2,1),(3,2),(4,3),(5,2),(6,2),(8,3),(9,1),(10,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,NULL,'user1','user1','user','1',0,NULL,NULL,NULL,NULL),(3,NULL,'reader1','reader1','John','Doe',0,NULL,NULL,NULL,NULL),(4,NULL,'writer1','writer1','John','Writer',0,NULL,NULL,NULL,NULL),(5,NULL,'test1','test1','Test','Test',0,NULL,NULL,NULL,NULL),(6,NULL,'test2','test2','test2','test2',0,NULL,NULL,NULL,NULL),(8,NULL,'test4','test4','test4','test4',0,NULL,NULL,NULL,NULL),(9,NULL,'test5','test5','test5','test5',1,NULL,NULL,NULL,NULL),(10,NULL,'test7','test7','test7','test7',0,NULL,NULL,NULL,NULL);
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

-- Dump completed on 2011-05-27 12:17:48
