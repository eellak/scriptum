/*
SQLyog Community- MySQL GUI v8.22 
MySQL - 5.1.48-community : Database - okm_repo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`okm_repo` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `okm_repo`;

/*Table structure for table `version_binval` */

DROP TABLE IF EXISTS `version_binval`;

CREATE TABLE `version_binval` (
  `BINVAL_ID` varchar(64) COLLATE utf8_bin NOT NULL,
  `BINVAL_DATA` longblob NOT NULL,
  UNIQUE KEY `VERSION_BINVAL_IDX` (`BINVAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `version_binval` */

/*Table structure for table `version_bundle` */

DROP TABLE IF EXISTS `version_bundle`;

CREATE TABLE `version_bundle` (
  `NODE_ID` varbinary(16) NOT NULL,
  `BUNDLE_DATA` longblob NOT NULL,
  UNIQUE KEY `VERSION_BUNDLE_IDX` (`NODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `version_bundle` */

insert  into `version_bundle`(`NODE_ID`,`BUNDLE_DATA`) values ('U—ù/ïEb»õÙë—v”T','\0\0\0\0\0Þ­¾ïúÎº¾Êþº¾Êþº¾\0\0ÿÿÿÿÿÿÿÿ\0éÐ´\r0<Nw»Z•%ÝÄPþ\0\0\0\020\0\0\0\0'),('WÍ;\"Ï˜NM»Æ°Ñ™œ:','\0\0\0\0\0éÐ´\r0<Nw»Z•%ÝÄPþ\0\0ÿÿÿÿÿÿÿÿ\0\0\0\0'),('Þ­¾ïúÎº¾Êþº¾Êþº¾','\0\0\0\0\0Þ­¾ïÊþº¾Êþº¾Êþº¾\0\0ÿÿÿÿÿÿÿÿ\0U—ù/ïEb»õÙë—v”T\0\0\0\0ba\0\0\0'),('éÐ´\r0<Nw»Z•%ÝÄPþ','\0\0\0\0\0U—ù/ïEb»õÙë—v”T\0\0ÿÿÿÿÿÿÿÿ\0WÍ;\"Ï˜NM»Æ°Ñ™œ:\0\0\0\05e\0\0\0\0');

/*Table structure for table `version_names` */

DROP TABLE IF EXISTS `version_names`;

CREATE TABLE `version_names` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `version_names` */

insert  into `version_names`(`ID`,`NAME`) values (1,'versionStorage'),(2,'versionLabels'),(3,'version'),(4,'predecessors'),(5,'created'),(6,'successors'),(7,'frozenNode'),(8,'frozenUuid'),(9,'frozenPrimaryType'),(10,'versionHistory'),(11,'versionableUuid'),(12,'versionComment'),(13,'data'),(14,'author'),(15,'mimeType'),(16,'size');

/*Table structure for table `version_refs` */

DROP TABLE IF EXISTS `version_refs`;

CREATE TABLE `version_refs` (
  `NODE_ID` varbinary(16) NOT NULL,
  `REFS_DATA` longblob NOT NULL,
  UNIQUE KEY `VERSION_REFS_IDX` (`NODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `version_refs` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
