-- MySQL dump 10.13  Distrib 5.1.58, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: okm_repo
-- ------------------------------------------------------
-- Server version	5.1.58-1ubuntu1

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


CREATE DATABASE `okm_repo` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_bin;

use `okm_repo`;


--
-- Table structure for table `VERSION_BINVAL`
--
DROP TABLE IF EXISTS `VERSION_BINVAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VERSION_BINVAL` (
  `BINVAL_ID` varchar(64) COLLATE utf8_bin NOT NULL,
  `BINVAL_DATA` longblob NOT NULL,
  UNIQUE KEY `VERSION_BINVAL_IDX` (`BINVAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VERSION_BINVAL`
--

LOCK TABLES `VERSION_BINVAL` WRITE;
/*!40000 ALTER TABLE `VERSION_BINVAL` DISABLE KEYS */;
/*!40000 ALTER TABLE `VERSION_BINVAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VERSION_BUNDLE`
--

DROP TABLE IF EXISTS `VERSION_BUNDLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VERSION_BUNDLE` (
  `NODE_ID` varbinary(16) NOT NULL,
  `BUNDLE_DATA` longblob NOT NULL,
  UNIQUE KEY `VERSION_BUNDLE_IDX` (`NODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VERSION_BUNDLE`
--

LOCK TABLES `VERSION_BUNDLE` WRITE;
/*!40000 ALTER TABLE `VERSION_BUNDLE` DISABLE KEYS */;
INSERT INTO `VERSION_BUNDLE` VALUES ('U��/�Eb����v�T','\0\0\0\0\0ޭ���κ���������\0\0��������\0�д\r0<Nw�Z�%��P�\0\0\0\020\0\0\0\0'),('W�;\"ϘNM�ưљ�:','\0\0\0\0\0�д\r0<Nw�Z�%��P�\0\0��������\0\0\0\0'),('ޭ���κ���������','\0\0\0\0\0ޭ��������������\0\0��������\0U��/�Eb����v�T\0\0\0\0ba\0\0\0'),('�д\r0<Nw�Z�%��P�','\0\0\0\0\0U��/�Eb����v�T\0\0��������\0W�;\"ϘNM�ưљ�:\0\0\0\05e\0\0\0\0');
/*!40000 ALTER TABLE `VERSION_BUNDLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VERSION_NAMES`
--

DROP TABLE IF EXISTS `VERSION_NAMES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VERSION_NAMES` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VERSION_NAMES`
--

LOCK TABLES `VERSION_NAMES` WRITE;
/*!40000 ALTER TABLE `VERSION_NAMES` DISABLE KEYS */;
INSERT INTO `VERSION_NAMES` VALUES (1,'versionStorage'),(2,'versionLabels'),(3,'version'),(4,'predecessors'),(5,'created'),(6,'successors'),(7,'frozenNode'),(8,'frozenUuid'),(9,'frozenPrimaryType'),(10,'versionHistory'),(11,'versionableUuid'),(12,'versionComment'),(13,'data'),(14,'author'),(15,'mimeType'),(16,'size');
/*!40000 ALTER TABLE `VERSION_NAMES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VERSION_REFS`
--

DROP TABLE IF EXISTS `VERSION_REFS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VERSION_REFS` (
  `NODE_ID` varbinary(16) NOT NULL,
  `REFS_DATA` longblob NOT NULL,
  UNIQUE KEY `VERSION_REFS_IDX` (`NODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VERSION_REFS`
--

LOCK TABLES `VERSION_REFS` WRITE;
/*!40000 ALTER TABLE `VERSION_REFS` DISABLE KEYS */;
/*!40000 ALTER TABLE `VERSION_REFS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-12-13 13:18:38
