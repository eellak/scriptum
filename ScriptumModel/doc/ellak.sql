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
  `street_no` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vat_no` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_company_company_type1` (`company_type_id`),
  KEY `FK38A73C7D6DEC3633` (`company_type_id`),
  CONSTRAINT `FK38A73C7D6DEC3633` FOREIGN KEY (`company_type_id`) REFERENCES `company_type` (`id`),
  CONSTRAINT `fk_company_company_type1` FOREIGN KEY (`company_type_id`) REFERENCES `company_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'???????? 1','','',0,'',NULL,'2011-06-06 00:00:00','2011-06-05 21:00:00','',NULL,'','','',NULL,'','','','','','','',2,'',''),(2,'???????? 2','','',1,'',NULL,'2011-06-06 00:00:00','2011-06-05 21:00:00','',NULL,'','','',NULL,'','','','','','','',1,'',''),(3,'Εταιρεία 3',NULL,NULL,0,NULL,NULL,NULL,NULL,'','','','','','','','','','','','','',2,NULL,NULL),(7,'Εταιρεία 8',NULL,NULL,0,NULL,NULL,NULL,'2011-03-16 10:34:11',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL),(8,'','','',0,'',NULL,'2011-06-06 00:00:00','2011-06-05 21:00:00','',NULL,'','','',NULL,'','','','','','','',1,'',''),(9,'','','',0,'',NULL,'2011-06-06 00:00:00','2011-06-05 21:00:00','',NULL,'','','',NULL,'','','','','','','',1,'',''),(12,'???????? 17','','',0,'',NULL,'2011-06-06 00:00:00','2011-06-05 21:00:00','',NULL,'','','',NULL,'','','','','','','',1,'','');
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
  `street_no` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `contact_FKIndex1` (`company_id`),
  KEY `FK38B72420489C29F8` (`company_id`),
  CONSTRAINT `FK38B72420489C29F8` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_7cc2855a-215b-11e0-9059-080027b715d2` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (2,2,'John','Smith',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'angelos@uit.gr',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'George',NULL,NULL,NULL,NULL,NULL),(3,2,'Angelos','Anagn',NULL,NULL,NULL,NULL,NULL,'Αθήνα',NULL,'angelos@uit.gr',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'G',NULL,NULL,NULL,NULL,NULL),(5,2,'test4','tester4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL,NULL),(6,1,'tester5','test5',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL,NULL),(8,1,'tester7','test7',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL,NULL),(9,1,'tester8','test8',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos8',NULL,NULL,NULL,NULL,NULL),(10,2,'Tester9','Test9',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos9',NULL,NULL,NULL,NULL,NULL),(11,1,'tester10','test10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'testos',NULL,NULL,NULL,NULL,NULL),(12,2,'George','Bush',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'W',NULL,NULL,NULL,NULL,NULL),(13,3,'Γεωργία','Παπαδοπούλου',NULL,NULL,NULL,NULL,'','',NULL,'','','','',NULL,NULL,NULL,NULL,'Γεώργιος','','','','',NULL),(14,7,'George','Bush',NULL,NULL,NULL,NULL,'','',NULL,'','','','',NULL,NULL,NULL,NULL,'Sr','','','','',NULL),(16,2,'Jack','Smith',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'G',NULL,NULL,NULL,NULL,NULL),(18,7,'Dohn','George',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(19,7,'Bong','George',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,7,'Collins','Jack',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,2,'test1','﻿test4',NULL,NULL,NULL,NULL,'test','Αθήνα',NULL,'test','test','test','test',NULL,NULL,NULL,NULL,'testostest','test','tes','test','test',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diavgeia_decision_type`
--

LOCK TABLES `diavgeia_decision_type` WRITE;
/*!40000 ALTER TABLE `diavgeia_decision_type` DISABLE KEYS */;
INSERT INTO `diavgeia_decision_type` VALUES (4,'1','ΝΟΜΟΣ',NULL,NULL),(5,'2','ΠΡΑΞΗ ΝΟΜΟΘΕΤΙΚΟΥ ΠΕΡΙΕΧΟΜΕΝΟΥ',NULL,NULL),(6,'3','ΠΡΟΕΔΡΙΚΟ ΔΙΑΤΑΓΜΑ',NULL,NULL),(7,'4','ΕΓΚΥΚΛΙΟΣ',NULL,NULL),(8,'5','ΔΙΟΡΙΣΜΟΣ',NULL,NULL),(9,'6','ΚΑΘΟΡΙΣΜΟΣ ΑΜΟΙΒΩΝ/ΑΠΟΖΗΜΙΩΣΕΩΝ',NULL,NULL),(10,'7','ΠΡΟΚΗΡΥΞΗ ΠΛΗΡΩΣΗΣ ΘΕΣΕΩΝ',NULL,NULL),(11,'8','ΠΙΝΑΚΑΣ ΕΠΙΤΥΧΟΝΤΩΝ, ΔΙΟΡΙΣΤΕΩΝ, ΕΠΙΛΑΧΟΝΤΩΝ',NULL,NULL),(12,'9','ΠΡΑΞΗ ΥΠΟΥΡΓΙΚΟΥ ΣΥΜΒΟΥΛΙΟΥ',NULL,NULL),(13,'10','ΜΕΤΑΤΑΞΗ',NULL,NULL),(14,'11','ΔΙΑΘΕΣΙΜΟΤΗΤΑ',NULL,NULL),(15,'12','ΑΠΟΔΟΧΗ ΠΑΡΑΙΤΗΣΗΣ',NULL,NULL),(16,'13','ΛΥΣΗ ΣΧΕΣΗΣ',NULL,NULL),(17,'14','ΑΝΤΙΚΑΤΑΣΤΑΣΗ – ΠΑΥΣΗ Γ.Γ. - ΜΕΛΩΝ ΣΥΛΛΟΓΙΚΩΝ ΟΡΓΑΝΩΝ',NULL,NULL),(18,'15','Η, ΑΝΑΘΕΣΗ ΔΗΜΟΣΙΑΣ ΣΥΜΒΑΣΗΣ',NULL,NULL),(19,'16','ΑΝΑΘΕΣΗ ΠΡΟΜΗΘΕΙΑΣ / ΥΠΗΡΕΣΙΩΝ',NULL,NULL),(20,'17','AΠΟΔΟΧΗ ΔΩΡΕΑΣ – ΣΥΜΒΑΣΗ ΠΟΛΙΤΙΣΤΙΚΗΣ ΧΟΡΗΓΙΑΣ',NULL,NULL),(21,'18','ΛΟΙΠΕΣ ΑΤΟΜΙΚΕΣ ΔΙΟΙΚΗΤΙΚΕΣ ΠΡΑΞΕΙΣ',NULL,NULL),(22,'19','ΠΡΑΞΗ ΑΝΑΠΤΥΞΙΑΚΗΣ ΝΟΜΟΘΕΣΙΑΣ',NULL,NULL),(23,'20','ΚΑΤΗΓΟΡΙΑ ΠΡΟΥΠΟΛΟΓΙΣΜΟΥ',NULL,NULL),(24,'21','ΙΣΟΛΟΓΙΣΜΟΣ',NULL,NULL),(25,'22','ΣΥΓΚΡΟΤΗΣΗ ΣΥΛΛΟΓΙΚΟΥ ΟΡΓΑΝΟΥ',NULL,NULL),(26,'23','ΓΝΩΜΟΔΟΤΗΣΕΙΣ - ΠΡΑΚΤΙΚΑ ΝΟΜΙΚΟΥ ΣΥΜΒΟΥΛΙΟΥ ΤΟΥ ΚΡΑΤΟΥΣ',NULL,NULL),(27,'24','ΣΥΜΒΑΣΗ ΕΡΓΟΥ',NULL,NULL),(28,'25','ΠΑΡΑΧΩΡΗΣΗΣ ΧΡΗΣΗΣ ΔΗΜΟΣΙΑΣ ΠΕΡΙΟΥΣΙΑΣ',NULL,NULL),(29,'26','ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 2861/2010',NULL,NULL),(30,'27','ΚΑΤΗΓΟΡΙΑ ΔΑΠΑΝΗΣ',NULL,NULL),(31,'28','ΥΠΟΥΡΓΙΚΗ ΑΠΟΦΑΣΗ',NULL,NULL),(32,'29','ΚΥΑ (ΚΟΙΝΗ ΥΠΟΥΡΓΙΚΗ ΑΠΟΦΑΣΗ )',NULL,NULL),(33,'30','ΑΠΟΛΟΓΙΣΜΟΣ',NULL,NULL),(34,'31','ΛΟΙΠΕΣ ΚΑΝΟΝΙΣΤΙΚΕΣ ΠΡΑΞΕΙΣ',NULL,NULL),(35,'32','ΛΥΣΗ ΣΧΕΣΗΣ',NULL,NULL),(36,'33','ΠΡΑΞΗ – ΓΝΩΜΟΔΟΤΗΣΗ ΑΝΕΞΑΡΤΗΤΗΣ ΑΡΧΗΣ',NULL,NULL),(37,'34','ΑΦΜ ΦΟΡΕΑ ΠΟΥ ΕΠΙΧΟΡΗΓΕΙ Η ΥΛΟΠΟΙΕΙ ΤΗ ΔΩΡΕΑ',NULL,NULL),(38,'35','ΠΡΑΞΗ ΥΠΟΥΡΓΙΚΟΥ ΣΥΜΒΟΥΛΙΟΥ',NULL,NULL),(39,'36','ΠΡΑΞΗ ΥΠΟΒΙΒΑΣΜΟΥ',NULL,NULL),(40,'37','Παραχώρηση δημόσιων και δημοτικών κτημάτων',NULL,NULL),(41,'38','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )-Καθορισμός χρήσης γης παραχωρούμενου δημόσιου κτήματος',NULL,NULL),(42,'39','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )- Καθορισμός αιγιαλού, παραλίας, λιμνών, ποταμών, ρεμάτων και χειμάρρων,',NULL,NULL),(43,'40','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )- Καθορισμός εθνικών δρυμών, δασών και δασικών εκτάσεων',NULL,NULL),(44,'41','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )- Χαρακτηρισμός εκτάσεων ως αναδασωτέων',NULL,NULL),(45,'42','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )-Καθορισμός βιομηχανικών ζωνών',NULL,NULL),(46,'43','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )-Καθορισμός λατομικών ζωνών',NULL,NULL),(47,'44','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )- Σύνταξη και έγκριση πολεοδομικών μελετών και Γενικού Πολεοδομικού σχεδίου',NULL,NULL),(48,'45','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )-Έγκριση και τροποποίηση χωροταξικών και ρυμοτομικών σχεδίων',NULL,NULL),(49,'46','μεταφοράς αυτού',NULL,NULL),(50,'47','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )-Σύνταξη και έγκριση Ζωνών Οικιστικού Ελέγχου (ΖΟΕ)',NULL,NULL),(51,'48','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )-Χορήγηση, αναστολή χορήγησης, τροποποίηση οικοδομικών αδειών',NULL,NULL),(52,'49','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )-Καθορισμός ή τροποποίηση όρων δόμησης',NULL,NULL),(53,'50','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )- Χωροθέτηση',NULL,NULL),(54,'51','(ΠΡΑΞΗ ΤΗΣ ΠΕΡ. 19, ΠΑΡ. 4 ΑΡΘΡΟΥ 2 Ν. 3861/2010 )-Καθορισμός αρχαιολογικών χώρων',NULL,NULL),(55,'52','Χαρακτηρισμός κτιρίων ως διατηρητέων και αποχαρακτηρισμών αυτών.',NULL,NULL),(56,'53','ΣΥΜΒΑΣΗ ΕΡΓΑΣΙΑΣ ΙΔΙΩΤΙΚΟΥ ΔΙΚΑΙΟΥ',NULL,NULL),(57,'54','ΣΥΜΒΑΣΕΙΣ : ΔΙΑΚΗΡΥΞΗ ΔΗΜΟΣΙΑΣ ΣΥΜΒΑΣΗΣ',NULL,NULL),(58,'55','ΑΦΜ ΠΡΟΣΩΠΟΥ ΣΤΟ ΟΠΟΙΟ ΚΑΤΑΚΥΡΩΝΕΤΑΙ',NULL,NULL),(59,'56','AΦΜ ΠΡΟΣΩΠΟΥ ΣΤΟ ΟΠΟΙΟ ΓΙΝΕΤΑΙ ΑΝΑΘΕΣΗ',NULL,NULL),(60,'57','ΔΙΥΠΟΥΡΓΙΚΕΣ ΑΠΟΦΑΣΕΙΣ',NULL,NULL);
/*!40000 ALTER TABLE `diavgeia_decision_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diavgeia_subject_group`
--

DROP TABLE IF EXISTS `diavgeia_subject_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diavgeia_subject_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `create_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=579 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diavgeia_subject_group`
--

LOCK TABLES `diavgeia_subject_group` WRITE;
/*!40000 ALTER TABLE `diavgeia_subject_group` DISABLE KEYS */;
INSERT INTO `diavgeia_subject_group` VALUES (3,'1','Μέσα Μεταφοράς, Ταξίδια και Τουρισμός',NULL),(4,'2','Οικονομία και Οικονομικά',NULL),(5,'3','Περιβάλλον και Φυσικοί Πόροι',NULL),(6,'4','Πληροφορία και Επικοινωνία',NULL),(7,'5','Πολιτισμός και Ελεύθερος Χρόνος',NULL),(8,'6','Υγεία και Κοινωνική Μέριμνα',NULL),(9,'7','Πολεοδομία και Κτηματολόγιο',NULL),(10,'8','Άνθρωποι, Κοινότητες και Διαβίωση',NULL),(11,'9','Δημόσια Τάξη και Άμυνα',NULL),(12,'10','Δικαιοσύνη, Πολιτεία και Δημόσια Διοίκηση',NULL),(13,'11','Εκπαίδευση και Έρευνα',NULL),(14,'12','Εργασία, Ασφάλιση και Σύνταξη',NULL),(15,'13','Διεθνείς Υποθέσεις και Ευρωπαϊκή Ένωση',NULL),(16,'14','Επιχειρηματικότητα και Ανταγωνιστικότητα',NULL),(17,'15','Διοικητικά επαγγέλματα',NULL),(18,'16','Περιβαλλοντικά επαγγέλματα',NULL),(19,'17','Λοιπά επαγγέλματα',NULL),(20,'18','Επαγγέλματα εκπαίδευσης',NULL),(21,'19','Επαγγέλματα πληροφορικής',NULL),(22,'20','Επαγγέλματα υγείας',NULL),(23,'21','Τεχνικά επαγγέλματα',NULL),(24,'22','Καλλιτεχνικά επαγγέλματα',NULL),(25,'23','Κοινωνικά επαγγέλματα',NULL),(26,'24','Οικονομικά επαγγέλματα',NULL),(27,'25','Ναυτικά επαγγέλματα',NULL),(28,'26','Επαγγέλματα θετικών επιστημών',NULL),(29,'27','Επαγγέλματα εφαρμοσμένης μηχανικής',NULL),(30,'28','Νομικά επαγγέλματα',NULL),(31,'29','Πτηνοτροφία',NULL),(32,'30','Υλοτομία',NULL),(33,'31','Μελισσοκομία',NULL),(34,'32','Λατομεία',NULL),(35,'33','Δασοκομία',NULL),(36,'34','Γεωργία',NULL),(37,'35','Μεταλλεία',NULL),(38,'36','Ορυχεία',NULL),(39,'37','Κτηνοτροφία',NULL),(40,'38','Αλιεία',NULL),(41,'39','Προσωπικά στοιχεία',NULL),(42,'40','Ανθρώπινα δικαιώματα',NULL),(43,'41','Ιθαγένεια, μετανάστευση και κοινωνική ένταξη',NULL),(44,'42','Οικογένεια',NULL),(45,'43','Μητρώα και δημοτολόγιο',NULL),(46,'44','Κατοικία',NULL),(47,'45','Κύκλος ζωής',NULL),(48,'46','Βεβαιώσεις',NULL),(49,'47','Πτυχία',NULL),(50,'48','Μετεγγραφές',NULL),(51,'49','Μεταπτυχιακές σπουδές',NULL),(52,'50','Εκπαιδευτικά Ιδρύματα',NULL),(53,'51','Αναγνώριση τίτλων',NULL),(54,'52','Διδακτορικές σπουδές',NULL),(55,'53','Εργασία στην Ανώτατη εκπαίδευση',NULL),(56,'54','Υποτροφίες',NULL),(57,'55','Φοιτητικές παροχές',NULL),(58,'56','Άθληση',NULL),(59,'57','Αθλητική νομοθεσία',NULL),(60,'58','Οφειλές',NULL),(61,'59','Συνάλλαγμα',NULL),(62,'60','Τελωνεία',NULL),(63,'61','Δάνεια',NULL),(64,'62','Εγκαταστάσεις ψυχαγωγίας',NULL),(65,'63','Διασκέδαση',NULL),(66,'64','Τυχερά παιχνίδια',NULL),(67,'65','Τρόφιμα και ποτά',NULL),(68,'66','Κώδικας Οδικής Κυκλοφορίας',NULL),(69,'67','Στάθμευση',NULL),(70,'68','Δακτύλιος',NULL),(71,'69','Έλεγχος συμπεριφοράς οδηγών',NULL),(72,'70','Προξενικές υποθέσεις',NULL),(73,'71','Διεθνείς σχέσεις',NULL),(74,'72','Ευρωπαϊκή Ένωση',NULL),(75,'73','Δημόσιες συμβάσεις',NULL),(76,'74','Διπλωματική προστασία',NULL),(77,'75','Διεθνείς σχέσεις (γενικά)',NULL),(78,'76','Ποινική δίωξη',NULL),(79,'77','Ποινικό δίκαιο',NULL),(80,'78','Νομική εκπροσώπηση',NULL),(81,'79','Νομική βοήθεια',NULL),(82,'80','Δικαστήρια',NULL),(83,'81','Κληρονομικό δίκαιο',NULL),(84,'82','Έγκλημα',NULL),(85,'83','Δίκες και δικάσιμοι',NULL),(86,'84','Αστικό Δίκαιο',NULL),(87,'85','Δικαιοσύνη',NULL),(88,'86','Ποινικό μητρώο',NULL),(89,'87','Οικογενειακό δίκαιο',NULL),(90,'88','Δικαστικές αποφάσεις',NULL),(91,'89','Ένδικα μέσα',NULL),(92,'90','ηση',NULL),(93,'91','Εκλογές',NULL),(94,'92','Δικαιοσύνη και δίκαιο',NULL),(95,'93','Πολιτεία και δημοκρατικοί θεσμοί',NULL),(96,'94','Διοικητικές διαδικασίες',NULL),(97,'95','Κώδικας διοικητικής διαδικασίας',NULL),(98,'96','Επικύρωση εγγράφων',NULL),(99,'97','Οργάνωση δημοσίων φορέων',NULL),(100,'98','Αναζήτηση πιστοποιητικών',NULL),(101,'99','Λιμενικό σώμα',NULL),(102,'100','Πυροσβεστική',NULL),(103,'101','Αστυνομία',NULL),(104,'102','Όπλα και εκρηκτικές ύλες',NULL),(105,'103','Ένοπλες δυνάμεις και στρατολογία',NULL),(106,'104','Δημόσια τάξη',NULL),(107,'105','Εκλογικοί κατάλογοι',NULL),(108,'106','Εκλογές (γενικά)',NULL),(109,'107','Εκλογική νομοθεσία',NULL),(110,'108','Προσχολική αγωγή',NULL),(111,'109','Εκπαίδευση (γενικά)',NULL),(112,'110','Πρωτοβάθμια και δευτεροβάθμια εκπαίδευση',NULL),(113,'111','Τεχνική και επαγγελματική εκπαίδευση',NULL),(114,'112','Κατηγορίες εκπαίδευσης',NULL),(115,'113','Ξένες γλώσσες',NULL),(116,'114','Έρευνα (γενικά)',NULL),(117,'115','Ανώτατη εκπαίδευση',NULL),(118,'116','Καύσιμα',NULL),(119,'117','Ανανεώσιμες πηγές ενέργειας',NULL),(120,'118','Ηλεκτρική ενέργεια',NULL),(121,'119','Ένοπλες δυνάμεις',NULL),(122,'120','Στρατιωτικές σχολές',NULL),(123,'121','Στρατιωτική θητεία',NULL),(124,'122','μοθεσία',NULL),(125,'123','Επιδόματα αναπηρίας',NULL),(126,'124','Επιδόματα ασθένειας',NULL),(127,'125','Επιδόματα ενοικίου',NULL),(128,'126','Εποχιακά βοηθήματα',NULL),(129,'127','Οικογενειακά επιδόματα',NULL),(130,'128','Λοιπά επιδόματα',NULL),(131,'129','Ηλεκτρονική επικοινωνία',NULL),(132,'130','Μέσα και τύπος',NULL),(133,'131','Όροι και συνθήκες',NULL),(134,'132','Εργασιακά έγγραφα',NULL),(135,'133','Εργασιακά δικαιώματα',NULL),(136,'134','Άδειες',NULL),(137,'135','Απασχόληση στο δημόσιο',NULL),(138,'136','Συλλογικές συμβάσεις εργασίας',NULL),(139,'137','Αργίες',NULL),(140,'138','Εργασιακά',NULL),(141,'139','η εργασίας',NULL),(142,'140','Κοινωνική ασφάλιση',NULL),(143,'141','Άσκηση επαγγέλματος',NULL),(144,'142','Σύνταξη',NULL),(145,'143','Ανεργία',NULL),(146,'144','Προκηρύξεις',NULL),(147,'145','Προσλήψεις',NULL),(148,'146','Εργασιακή εμπειρία',NULL),(149,'147','Επαγγελματική κατάρτιση',NULL),(150,'148','Αναζήτηση εργασίας',NULL),(151,'149','Εκπαίδευση στην Ευρωπαϊκή Ένωση',NULL),(152,'150','Ευρωπαϊκή ιθαγένεια',NULL),(153,'151','Συνθήκη Σένγκεν',NULL),(154,'152','Εργασία στην Ευρωπαϊκή Ένωση',NULL),(155,'153','Ευρωπαϊκή Ένωση (γενικά)',NULL),(156,'154','Επιχειρηματική δραστηριότητα στην Ευρωπαϊκή Ένωση',NULL),(157,'155','Χάγης',NULL),(158,'156','Κώδικας Βιβλίων και Στοιχείων',NULL),(159,'157','Φορολογία κεφαλαίου',NULL),(160,'158','Φόροι',NULL),(161,'159','Φορολογία επιχειρήσεων',NULL),(162,'160','Ηλεκτρονική υποβολή',NULL),(163,'161','Φόρος Προστιθέμενης Αξίας',NULL),(164,'162','Φορολογία εισοδήματος',NULL),(165,'163','Φορολογία',NULL),(166,'164','Άρδευση',NULL),(167,'165','Λίμνες',NULL),(168,'166','Υπόγεια ύδατα',NULL),(169,'167','Ποτάμια',NULL),(170,'168','Ιθαγένεια',NULL),(171,'169','Κοινωνική ένταξη',NULL),(172,'170','Ομογένεια',NULL),(173,'171','Μετανάστευση',NULL),(174,'172','Ειδική αγωγή',NULL),(175,'173','ίδευση',NULL),(176,'174','Διαπολιτισμική εκπαίδευση',NULL),(177,'175','Εκκλησιαστική εκπαίδευση',NULL),(178,'176','Εκπαίδευση ενηλίκων',NULL),(179,'177','Επιμόρφωση εκπαιδευτικών',NULL),(180,'178','Ιδιωτική εκπαίδευση',NULL),(181,'179','Ναυτική εκπαίδευση',NULL),(182,'180','Ύδρευση και αποχέτευση',NULL),(183,'181','Απόκτηση κατοικίας',NULL),(184,'182','Εργατική κατοικία',NULL),(185,'183','Ανέγερση κατοικίας',NULL),(186,'184','Τηλεφωνική σύνδεση',NULL),(187,'185','Ενοικίαση κατοικίας',NULL),(188,'186','Επισκευή κατοικίας',NULL),(189,'187','Ηλεκτροδότηση',NULL),(190,'188','Αλλαγή διεύθυνσης',NULL),(191,'189','Θέρμανση',NULL),(192,'190','Γάμος',NULL),(193,'191','Θάνατος',NULL),(194,'192','Γέννηση',NULL),(195,'193','Βάφτιση',NULL),(196,'194','Ναυτιλία',NULL),(197,'195','Ξενοδοχεία και εστιατόρια',NULL),(198,'196','Εμπόριο',NULL),(199,'197','Παροχή υπηρεσιών',NULL),(200,'198','Χρηματοπιστωτικοί οργανισμοί',NULL),(201,'199','Βιομηχανία',NULL),(202,'200','Κατασκευές',NULL),(203,'201','Ασφάλιση μετοχικών ταμείων',NULL),(204,'202','Βιβλιάρια υγείας',NULL),(205,'203','Ασφάλιση ΤΣΜΕΔΕ',NULL),(206,'204','Κοινωνική ασφάλιση (γενικά)',NULL),(207,'205','Ασφαλιστικές παροχές',NULL),(208,'206','Ασφάλιση ΟΑΕΕ',NULL),(209,'207','Ασφάλιση ΤΕΑΥΕΚ',NULL),(210,'208','Ειδικές κατηγορίες ασφάλισης',NULL),(211,'209','Ασφάλιση ΙΚΑ',NULL),(212,'210','Ασφάλιση δημοσίου',NULL),(213,'211','Υγειονομική περίθαλψη',NULL),(214,'212','Ασφάλιση ΟΑΠ - ΔΕΗ',NULL),(215,'213','Ασφάλιση ΟΓΑ',NULL),(216,'214','Ασφάλιση Ταμείο Νομικών και ΚΕΑΔ',NULL),(217,'215','Ασφάλιση ΤΑΞΥ',NULL),(218,'216','Ασφάλιση ΤΑΠΟΤΕ',NULL),(219,'217','Ασφάλιση ΤΣΑΥ',NULL),(220,'218','Φιλανθρωπία',NULL),(221,'219','Αναπηρία',NULL),(222,'220','Κοινωνική πολιτική',NULL),(223,'221','Διδασκαλία ξένων γλωσσών',NULL),(224,'222','Πιστοποιητικά',NULL),(225,'223','Ξένες γλώσσες (γενικά)',NULL),(226,'224','ών',NULL),(227,'225','Κτηματολόγιο',NULL),(228,'226','Οικόπεδα',NULL),(229,'227','Εφημερίδα της κυβέρνησης',NULL),(230,'228','Σύνταγμα της Ελλάδας',NULL),(231,'229','Κυβέρνηση',NULL),(232,'230','Ηλεκτρονική διακυβέρνηση',NULL),(233,'231','Σιδηροδρομικές συγκοινωνίες',NULL),(234,'232','Εισιτήρια',NULL),(235,'233','Αστικές συγκοινωνίες',NULL),(236,'234','Αεροπορικές συγκοινωνίες',NULL),(237,'235','Υπεραστικές συγκοινωνίες',NULL),(238,'236','Ταξί',NULL),(239,'237','Ταξίδια και τουρισμός',NULL),(240,'238','Μεταφορές γενικά',NULL),(241,'239','Οχήματα',NULL),(242,'240','Οδική ασφάλεια',NULL),(243,'241','Ναυσιπλοΐα',NULL),(244,'242','Μέσα μαζικής μεταφοράς',NULL),(245,'243','Εμπορευματικές μεταφορές',NULL),(246,'244','Οδήγηση',NULL),(247,'245','Διαχείριση κυκλοφορίας',NULL),(248,'246','Δημοτολόγια',NULL),(249,'247','Μεταδημότευση',NULL),(250,'248','Μητρώο αρρένων',NULL),(251,'249','Σκάφη',NULL),(252,'250','Πλοία',NULL),(253,'251','Επαγγελματικές άδειες',NULL),(254,'252','Άδειες οδήγησης',NULL),(255,'253','Άδειες μηχανημάτων',NULL),(256,'254','Αυθαίρετα',NULL),(257,'255','Διατηρητέα',NULL),(258,'256','Κατεδάφιση κτιρίων',NULL),(259,'257','Οικοδομικές άδειες',NULL),(260,'258','Κτiριακές εγκαταστάσεις',NULL),(261,'259','ρίων',NULL),(262,'260','Απροστάτευτα παιδιά',NULL),(263,'261','Φροντίδα παιδιών',NULL),(264,'262','Διαζύγιο',NULL),(265,'263','Μονογονεϊκή οικογένεια',NULL),(266,'264','Υιοθεσία',NULL),(267,'265','Πολυτεκνία',NULL),(268,'266','Οικογενειακή κατάσταση',NULL),(269,'267','Οικογενειακές παροχές',NULL),(270,'268','Επιδόματα και χρηματικά βοηθήματα',NULL),(271,'269','Φορολογία και φόροι',NULL),(272,'270','Κλάδοι οικονομικής δραστηριότητας',NULL),(273,'271','Δάνεια, οφειλές και συνάλλαγμα',NULL),(274,'272','Όπλα',NULL),(275,'273','Εκρηκτικές ύλες',NULL),(276,'274','Τάφροι',NULL),(277,'275','Αιγιαλός',NULL),(278,'276','Χείμαρροι',NULL),(279,'277','Ρυάκια',NULL),(280,'278','Άδειες κυκλοφορίας',NULL),(281,'279','Διαγραφή οχημάτων',NULL),(282,'280','Μηχανήματα',NULL),(283,'281','Πινακίδες',NULL),(284,'282','Λεωφορεία και φορτηγά',NULL),(285,'283','Μεταβίβαση οχημάτων',NULL),(286,'284','Οχήματα (γενικά)',NULL),(287,'285','Τέλη κυκλοφορίας',NULL),(288,'286','Τεχνικός έλεγχος οχημάτων',NULL),(289,'287','Χλωρίδα και πανίδα',NULL),(290,'288','Προστασία περιβάλλοντος',NULL),(291,'289','Οριοθετήσεις',NULL),(292,'290','Ενέργεια',NULL),(293,'291','Υδατικοί πόροι',NULL),(294,'292','Αξιοποίηση φυσικών πόρων',NULL),(295,'293','Προστασία δεδομένων',NULL),(296,'294','Τηλεπικοινωνίες',NULL),(297,'295','Τεχνολογίες πληροφορικής και επικοινωνίας',NULL),(298,'296','Επικοινωνία',NULL),(299,'297','Πολεοδομία και σχέδιο πόλης',NULL),(300,'298','Κτηματολόγιο και οικόπεδα',NULL),(301,'299','Οικοδομές και κτίρια',NULL),(302,'300','Πολεοδομικές μελέτες',NULL),(303,'301','Πολεοδομία',NULL),(304,'302','Ρυμοτομία',NULL),(305,'303','Οικισμοί',NULL),(306,'304','Αρχαιότητες',NULL),(307,'305','Πολιτιστικοί χώροι (γενικά)',NULL),(308,'306','Αρχαιολογικοί χώροι',NULL),(309,'307','Πολιτισμός και δημιουργία',NULL),(310,'308','Πινακοθήκες',NULL),(311,'309','Μουσεία',NULL),(312,'310','Βιβλιοθήκες',NULL),(313,'311','Θέατρα',NULL),(314,'312','Διασκέδαση και ψυχαγωγία',NULL),(315,'313','Πολιτισμός',NULL),(316,'314','Εθελοντισμός',NULL),(317,'315','Αθλητισμός',NULL),(318,'316','Προξενικές πράξεις',NULL),(319,'317','Αλλοδαποί στην Ελλάδα',NULL),(320,'318','Έλληνες στο εξωτερικό',NULL),(321,'319','Διαχείριση αποβλήτων',NULL),(322,'320','Προστασία περιβάλλοντος (γενικά)',NULL),(323,'321','Απαγόρευση κυνηγιού',NULL),(324,'322','Ρύπανση',NULL),(325,'324','Ηλικία',NULL),(326,'325','Γνήσιο υπογραφής',NULL),(327,'326','Αριθμός Φορολογικού Μητρώου',NULL),(328,'327','Αστυνομική ταυτότητα',NULL),(329,'328','Βεβαιώσεις σπουδών',NULL),(330,'329','Εισαγωγή στην ανώτατη εκπαίδευση',NULL),(331,'330','Επαγγελματική εκπαίδευση',NULL),(332,'331','Σχολεία',NULL),(333,'332','Εργασία στην Πρωτοβάθμια και Δευτεροβάθμια εκπαίδευση',NULL),(334,'333','Συνταξιοδότηση',NULL),(335,'334','Συντάξεις ΤΣΑΥ',NULL),(336,'335','Συντάξεις γήρατος',NULL),(337,'336','Συντάξεις θανάτου',NULL),(338,'337','Συντάξεις πολυτέκνων',NULL),(339,'338','Συντάξεις ειδικών κατηγοριών',NULL),(340,'339','Συντάξεις ΙΚΑ',NULL),(341,'340','Συντάξεις ΝΑΤ',NULL),(342,'341','Συντάξεις ΟΑΕΕ',NULL),(343,'342','Συντάξεις ΟΑΠ - ΔΕΗ',NULL),(344,'343','Συντάξεις Ταμείο Νομικών - ΚΕΑΔ',NULL),(345,'344','τάξεις ΤΕΑΥΕΚ',NULL),(346,'345','Συντάξεις ΤΣΜΕΔΕ',NULL),(347,'346','Συντάξεις μετοχικών ταμείων',NULL),(348,'347','Συντάξεις ΤΑΠΟΤΕ',NULL),(349,'348','Συντάξεις δημοσίου',NULL),(350,'349','Συντάξεις ΟΓΑ',NULL),(351,'350','Συντάξεις αναπηρίας',NULL),(352,'351','Visas',NULL),(353,'352','Τουρισμός',NULL),(354,'353','Κοινωνικός τουρισμός',NULL),(355,'354','Ναυτικά φυλλάδια',NULL),(356,'355','Διαβατήρια',NULL),(357,'356','Ταξίδια',NULL),(358,'357','Ινστιτούτα Επαγγελματικής Κατάρτισης',NULL),(359,'358','Σχολές μαθητείας ΟΑΕΔ',NULL),(360,'359','Τεχνικές σχολές',NULL),(361,'360','Τεχνολογίες πληροφορικής',NULL),(362,'361','εσίες πιστοποίησης',NULL),(363,'362','Τεχνολογίες επικοινωνίας',NULL),(364,'363','Τηλεπικοινωνιακά τέλη',NULL),(365,'364','Λειτουργία τηλεπικοινωνιών',NULL),(366,'365','Τηλεπικοινωνιακές συνδέσεις',NULL),(367,'366','Δάση',NULL),(368,'367','Ζώα',NULL),(369,'368','Φυτά',NULL),(370,'369','Υγεία και υγιεινή',NULL),(371,'370','Προστασία καταναλωτών',NULL),(372,'371','Κοινωνική Πρόνοια',NULL),(373,'372','Διατροφή',NULL),(374,'373','Πιστοποιητικά υγείας',NULL),(375,'374','Υγεία γενικά',NULL),(376,'375','Φάρμακα',NULL),(377,'376','Σύστημα υγείας',NULL),(378,'377','Νοσηλεία',NULL),(379,'378','Δωρεά οργάνων',NULL),(380,'379','Κανόνες υγιεινής',NULL),(381,'380','Εμβόλια',NULL),(382,'381','Επιχειρηματικότητα',NULL),(383,'382','Έλεγχοι και κυρώσεις',NULL),(384,'383','Ανθρώπινο δυναμικό',NULL),(385,'384','Επαγγελματική στέγη',NULL),(386,'385','Σύσταση και λειτουργία εταιρίας',NULL),(387,'386','Επιχορηγήσεις επιχειρήσεων',NULL),(388,'387','Μετατροπή επιχείρησης',NULL),(389,'388','Κλείσιμο επιχείρησης',NULL),(390,'389','Ίδρυση και λειτουργία επιχείρησης',NULL),(391,'390','Επιχειρησιακά προγράμματα',NULL),(392,'391','Συγχώνευση επιχείρησης',NULL),(393,'392','Άδειες Προσωπικού Δήμων και Περιφερειών',NULL),(394,'393','Αναγνώριση εκτός γάμου γεννηθέντων τέκνων',NULL),(395,'395','Αποσπάσεις',NULL),(396,'396','Απώλεια - Ανάκτηση Ελληνικής Ιθαγένειας',NULL),(397,'397','Γενικός Γραμματέας Περιφέρειας',NULL),(398,'398','Δημόσιες Συμβάσεις Έργων',NULL),(399,'399','Δημόσιες Συμβάσεις Μελετών',NULL),(400,'400','Δημόσιες Συμβάσεις Προμηθειών',NULL),(401,'401','Δημόσιες Συμβάσεις Υπηρεσιών',NULL),(402,'402','Διαπίστωση Ελληνικής Ιθαγένειας από γέννηση',NULL),(403,'403','Διαχείριση περιουσίας ΟΤΑ',NULL),(404,'404','Δίκτυα Δήμων και Περιφερειών',NULL),(405,'405','Διορισμοί υπαλλήλων',NULL),(406,'406','Εκλογικές αποζημιώσεις',NULL),(407,'407','Εκλογικές προμήθειες',NULL),(408,'408','Ενσωμάτωση δικαίου της ΕΕ',NULL),(409,'409','Εξοδα Παράστασης - Κίνησης ΟΤΑ',NULL),(410,'410','Επιτροπές',NULL),(411,'411','Επιχειρήσεις Δήμων και Περιφερειών',NULL),(412,'412','Ευρωπαικά και Διεθνή Αναπτυξιακά  Προγράμματα',NULL),(413,'413','Ευρωπαική Εβδομάδα Τοπικής Δημοκρατίας',NULL),(414,'414','Ευρωπαικός Όμιλος εδαφικής συνεργασίας - ΕΟΕΣ',NULL),(415,'415','Ευρώπη για τους πολίτες',NULL),(416,'416','Μετανάστευση - Θέματα για στερούμενους διαβατηρίου',NULL),(417,'418','Καθορισμός αμοιβής Επιτροπών',NULL),(418,'419','Κοινωνικές δομές από ΝΠΔΔ και επιχειρήσεις ΟΤΑ',NULL),(419,'420','Κτήση Ελληνικής Ιθαγένειας από γέννηση',NULL),(420,'421','Ληξιαρχεία',NULL),(421,'422','ξεις γεγονότων εξωτερικού ελλήνων υπηκόων',NULL),(422,'423','Λύση υπαλληλικής σχέσης',NULL),(423,'424','Μετανάστευση-Ανακοινώσεις CION',NULL),(424,'425','Μετανάστευση-Αποφάσεις Συμβουλίου Ε.Ε. για τη μετανάστευση',NULL),(425,'426','Μετανάστευση-Βασικά έγγραφα πολιτικής Ε.Ε.',NULL),(426,'427','Μετανάστευση-Δικαιώματα και Υποχρεώσεις υπηκόων τρίτων χωρών',NULL),(427,'428','Μετανάστευση-Ειδική Βεβαίωση νόμιμης διαμονής για υπηκόους τρίτων χωρών',NULL),(428,'429','Μετανάστευση-Εκπροσώπηση υπηκόων τρίτων χωρών',NULL),(429,'430','Μετανάστευση-Εξωτερικές Σχέσεις Ε.Ε.',NULL),(430,'431','Μετανάστευση-Θέματα αδειών διαμονής για εργασία',NULL),(431,'433','τητης οικονομικής δραστηριότητας και υψηλών επενδύσεων',NULL),(432,'434','Μετανάστευση-Θέματα εισδοχής υπηκόων τρίτων χωρών για επιστημονική έρευνα',NULL),(433,'435','Μετανάστευση-Θέματα εξαρτημένης εργασίας – Διαδικασία μετάκλησης – Εποχιακή απασχόληση – Αλιεργάτες',NULL),(434,'436','Μετανάστευση-Θέματα επί μακρόν διαμενόντων για υπηκόους τρίτων χωρών και δεκαετείς άδειες',NULL),(435,'437','Μετανάστευση-Θέματα οικογενειακής επανένωσης για υπηκόους τρίτων χωρών',NULL),(436,'439','Μετανάστευση-Θέματα σπουδών για υπηκόους τρίτων χωρών',NULL),(437,'440','Μετανάστευση-Κανονισμοί Ε.Ε. για τη μετανάστευση',NULL),(438,'441','Μετανάστευση-Κοινοτικές Οδηγίες για τη μετανάστευση',NULL),(439,'442','ανάστευση-Νομοθεσία για είσοδο και διαμονή υπηκόων τρίτων χωρών στην Ελληνική Επικράτεια',NULL),(440,'443','Μετανάστευση-Συμπεράσματα Ευρωπαϊκών Συμβουλίων για τη μετανάστευση',NULL),(441,'444','Μετατάξεις',NULL),(442,'445','Μητρώα Αρρένων',NULL),(443,'446','Νομικά Πρόσωπα Δημοσίου Δικαίου Δήμων',NULL),(444,'447','ΟΗΕ, ΟΑΣΕ, ΟΟΣΑ',NULL),(445,'448','απλούστευση διαδικασιών',NULL),(446,'449','Πολιτικά Γραφεία',NULL),(447,'450','Πολιτογράφηση αλλογενών αλλοδαπών',NULL),(448,'451','Πολιτογράφηση ομογενών αλλοδαπών',NULL),(449,'452','Πράξεις επιχορηγήσεων εισφορών',NULL),(450,'453','Πράξεις καθορισμού αμοιβών μελών επιτροπής',NULL),(451,'454','υγκρότησης επιτροπών',NULL),(452,'455','\"',NULL),(453,'456','\"',NULL),(454,'457','Πρόγραμμα Δημοσίων Επενδύσεων',NULL),(455,'458','Πρόγραμμα Διοικητική Μεταρρύθμιση',NULL),(456,'459','Πρόγραμμα ΕΛΛΑΔΑ',NULL),(457,'460','Πρόγραμμα ΣΑΕ 355/8',NULL),(458,'461','Πρόγραμμα ΣΑΤΑ',NULL),(459,'462','Πρόγραμμα στεγαστικής συνδρομής Ελλήνων Τσιγγάνων / ROMA',NULL),(460,'463','Προκηρύξεις θέσεων Προϊσταμένων',NULL),(461,'464','Προμήθειες',NULL),(462,'465','Ένταξης',NULL),(463,'466','Προσλήψεις προσωπικού',NULL),(464,'467','στυνομίας',NULL),(465,'468','Προσωπικό επιχειρήσεων',NULL),(466,'469','Προσωπικό ιδρυμάτων',NULL),(467,'470','Προσωπικό κοινωνικών δομών',NULL),(468,'471','Προσωπικό νομικών προσώπων δημοσίου δικαίου',NULL),(469,'472','Προσωπικό Συνδέσμων ΟΤΑ',NULL),(470,'473','Προσωπικό Φορέων Διαχείρισης Στερεών Αποβλήτων',NULL),(471,'474','Προυπολογισμός',NULL),(472,'475','Προϋπολογισμός ΟΤΑ',NULL),(473,'476','Ρυθμίσεις κυκλοφορίας',NULL),(474,'477','Ρυθμίσεις οφειλών-πιστοληπτική πολιτική ΟΤΑ',NULL),(475,'478','ΣΔΙΤ',NULL),(476,'479','Συλλογικές Συμβάσεις Εργασίας',NULL),(477,'480','Συμβούλιο Ιθαγένειας',NULL),(478,'481','Συμβούλιο της Ευρώπης',NULL),(479,'482','Σύμφωνο Συμβίωσης',NULL),(480,'483','Σύνδεσμοι ΟΤΑ',NULL),(481,'484','Ταυτότητες',NULL),(482,'485','Υιοθεσία',NULL),(483,'486','Ασφάλεια και υγεία των εργαζομένων',NULL),(484,'487','Πρόγραμμα Θησέας',NULL),(485,'488','Πρόγραμμα ΣΑΕ 055',NULL),(486,'489','Πρόγραμμα κρατικής αναπτυξιακής βοήθεια-ΚΑΒ',NULL),(487,'490','Τοπική Αυτοδιοίκηση α΄ βαθμού',NULL),(488,'491','Τοπική Αυτοδιοίκηση β΄ βαθμού',NULL),(489,'492','Εκλογές-Θέματα Προβολής Συνδυασμών',NULL),(490,'493','Καταστήματα Υγειονομικού Ενδιαφέροντος',NULL),(491,'494','Επιχορηγήσεις των ΟΤΑ α\' και β΄ βαθμού από Κεντρικούς Αυτοτελείς Πόρους (ΚΑΠ)',NULL),(492,'496','των ΟΤΑ α\' και β΄ βαθμού',NULL),(493,'497','Υπερωρίες υπαλλήλων',NULL),(494,'498','Ιδρύματα Δήμων και Περιφερειών',NULL),(495,'499','Κατασκευές Δημοσίων Έργων',NULL),(496,'500','Μελέτες Δημοσίων Έργων',NULL),(497,'501','Μελέτες Ιδιωτικών Έργων',NULL),(498,'502','ΣΑΕ -  Συλλογική Απόφαση  Έργου',NULL),(499,'503','ΣΑΜ – Συλλογική Απόφαση Μελετών.',NULL),(500,'504','ΣΑΕΠ- Συλλογική Απόφαση Περιφερειών',NULL),(501,'505','ΣΑΜΠ – Συλλογική Απόφαση Μελετών Περιφερειών',NULL),(502,'506','ΣΑΝΑ – Συλλογική Απόφαση Νομαρχιακής Αυτοδιοίκησης',NULL),(503,'507','ΣΑΤΑ – Συλλογική Απόφαση Τοπικής Αυτοδιοίκησης.',NULL),(504,'508','Προέγκριση ΣΑΕ -  Συλλογικής Απόφασης  Έργου',NULL),(505,'509','Προέγκριση ΣΑΜ – Συλλογικής Απόφασης Μελετών.',NULL),(506,'510','Προέγκριση ΣΑΕΠ- Συλλογικής Απόφασης Περιφερειών',NULL),(507,'511','Προέγκριση ΣΑΜΠ – Συλλογικής Απόφασης Μελετών Περιφέρειών',NULL),(508,'512','Προέγκριση ΣΑΝΑ – Συλλογικής Απόφασης Νομαρχιακής Αυτοδιοίκησης',NULL),(509,'513','Προέγκριση ΣΑΤΑ – Συλλογικής Απόφασης Τοπικής Αυτοδιοίκησης.',NULL),(510,'514','ΚΑΝΟΝΙΣΤΙΚΗ ΑΝΑΠΤΥΞΙΑΚΟΥ ΝΟΜΟΥ',NULL),(511,'515','ΥΠΑΓΩΓΗ ΕΠΕΝΔΥΣΗΣ ΣΕ ΑΝΑΠΤΥΞΙΑΚΟ ΝΟΜΟ',NULL),(512,'516','ΤΡΟΠΟΠΟΙΗΣΗ ΕΠΕΝΔΥΣΗΣ ΥΠΑΧΘΕΙΣΑΣ ΣΕ ΑΝΑΠΤΥΞΙΑΚΟ ΝΟΜΟ',NULL),(513,'517','ΟΛΟΚΛΗΡΩΣΗ ΕΠΕΝΔΥΣΗΣ ΥΠΑΧΘΕΙΣΑΣ ΣΕ ΑΝΑΠΤΥΞΙΑΚΟ ΝΟΜΟ',NULL),(514,'518','ΞΙΑΚΟ ΝΟΜΟ',NULL),(515,'519','ΕΓΚΥΚΛΙΟΣ (Α.Ν.)  ΑΝΑΠΤΥΞΙΑΚΟΥ ΝΟΜΟΥ',NULL),(516,'520','ΚΑΝΟΝΙΣΤΙΚΗ Ν.3427/2005 Κεφάλαιο ΣΤ΄',NULL),(517,'521','ΥΠΟΥΡΓΙΚΗ ΑΠΟΦΑΣΗ Ν.3427/2005 – ΥΠΑΓΩΓΗΣ ΣΕ ΑΝΑΠΤΥΞΙΑΚΟ ΝΟΜΟ',NULL),(518,'522','Υπηρεσίες',NULL),(519,'523','Δήμαρχος',NULL),(520,'524','Δημοτικό Συμβούλιο',NULL),(521,'525','Δημοτικοί Φόροι, τέλη, εισφορές και άλλα ίδια έσοδα ΟΤΑ',NULL),(522,'526','Υπαίθρια διαφήμιση',NULL),(523,'527','Πρόστιμα ΚΟΚ και τέλη ελεγχόμενης στάθμευσης',NULL),(524,'528','Βεβαίωση και είσπραξη εσόδων των ΟΤΑ-Διευκόλυνση οφειλετών',NULL),(525,'529','Παρακρατήσεις υπέρ Συνδέσμων των ΟΤΑ',NULL),(526,'530','ογενών από χώρες της τέως Σοβιετικής Ένωσης',NULL),(527,'531','Απαλλοτριώσεις',NULL),(528,'532','Χρηματοδότηση Προγράμματος Δημοσίων Επενδύσεων',NULL),(529,'533','Δημαρχιακή Επιτροπή',NULL),(530,'534','Ταχυδρομικές Υπηρεσίες',NULL),(531,'535','Ενέργειες Διαφήμισης',NULL),(532,'536','Απόκτηση ελληνικής ιθαγένειας ομογενών από χώρες της πρώην ΕΣΣΔ βάσει του Ν.2790/2000',NULL),(533,'537','Ανώνυμες Εταιρείες',NULL),(534,'538','Οικονομική ενίσχυση Συνδικαλιστικών Οργανώσεων',NULL),(535,'539','Οργανόγραμμα Υπουργείων',NULL),(536,'540','Τεχνική Βοήθεια',NULL),(537,'541','Λιμενικές υποδομές',NULL),(538,'542','Αγορά ή διαθεση αυτοκινήτων',NULL),(539,'543','αυτοκ/των προς υπηρεσίες',NULL),(540,'544','Θέση σε κυκλοφορία αυτ/των',NULL),(541,'545','Άρση κυκλοφορίας Αυτ/των',NULL),(542,'546','Εθνικά Κληροδοτήματα',NULL),(543,'547','Περιβάλλον-Οριοθέτηση δασικών εκτάσεων',NULL),(544,'548','ΑΠΟΦΑΣΗ ΜΑΤΑΙΩΣΗΣ ΔΙΑΓΩΝΙΣΜΟΥ',NULL),(545,'549','Σύσταση Θέσεων',NULL),(546,'550','Ελληνική Αναπτυξιακή Συνεργασία',NULL),(547,'551','Μετακινήσεις προσωπικού',NULL),(548,'552','Νομισματικό Πρόγραμμα',NULL),(549,'553','Παροχή Εγγύησης Ελληνικού Δημοσίου',NULL),(550,'554','Περιβαλλοντική Αδειοδότηση',NULL),(551,'555','Υπερωρίες Προσωπικού Ιδιωτικού Δικαίου στο Δημόσιο',NULL),(552,'556','Αντικατάσταση μελών Δ.Σ',NULL),(553,'557','Χορήγηση προκαταβολής',NULL),(554,'558','Διενέργεια διαγωνισμού',NULL),(555,'559','Εσωτερική Μετακίνηση',NULL),(556,'560','Εσωτερική Μετακίνηση',NULL),(557,'561','Διαθεσιμότητα Υπαλλήλου',NULL),(558,'562','Ερευνητικά προγράμματα ή άλλα έργα',NULL),(559,'563','οβολή δικαιολογητικών για έκδοση εντάλματος πληρωμής',NULL),(560,'564','Ένταλμα πληρωμής',NULL),(561,'565','Γενικό Πρόγραμμα «Αλληλεγγύη και Διαχείριση Μεταναστευτικών Ροών» Περιόδου 2007-2013',NULL),(562,'567','\"',NULL),(563,'568','Τροποποίηση προυπολογισμού',NULL),(564,'569','Εθνικά Κληροδοτήματα',NULL),(565,'570','Ασφαλιστικές εισφορές στο ΤΕΑΔΥ',NULL),(566,'571','Τεχνικά θέματα',NULL),(567,'572','Εκδόσεις - Εκτυπώσεις',NULL),(568,'573','Γ Πράξη οικονομικού ενδιαφέροντος',NULL),(569,'574','ΕΣΠΑ 2007-2013',NULL),(570,'575','Εισηγητής',NULL),(571,'576','Συνδρομή έντυπων και ηλεκτρονικών περιοδικών',NULL),(572,'577','Δικαστικά έξοδα',NULL),(573,'578','Χρηματοδοτήσεις Έργων Προγράμματος Δημοσίων Επενδύσεων',NULL),(574,'579','Μέλη Συμβουλίων',NULL),(575,'580','Οικονομικοί πόροι',NULL),(576,'581','Λογαριασμός Εσόδων Δημοσίων Επενδύσεων',NULL),(577,'582','ζημιώσεις πληγέντων από φυσικές καταστροφές',NULL),(578,'583','Ορισμός Εκκαθαριστή',NULL);
/*!40000 ALTER TABLE `diavgeia_subject_group` ENABLE KEYS */;
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
  KEY `FK75459291EE5129A5` (`distribution_method_id`),
  KEY `FK75459291EA0F2F84` (`contact_sender_id`),
  CONSTRAINT `FK75459291EA0F2F84` FOREIGN KEY (`contact_sender_id`) REFERENCES `contact` (`id`),
  CONSTRAINT `FK75459291EE5129A5` FOREIGN KEY (`distribution_method_id`) REFERENCES `distribution_method` (`id`),
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
  KEY `FK49E7A60BEE5129A5` (`distribution_method_id`),
  CONSTRAINT `FK49E7A60BEE5129A5` FOREIGN KEY (`distribution_method_id`) REFERENCES `distribution_method` (`id`),
  CONSTRAINT `fk_7cbca1e4-215b-11e0-9059-080027b715d2` FOREIGN KEY (`distribution_method_id`) REFERENCES `distribution_method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outgoing_protocol`
--

LOCK TABLES `outgoing_protocol` WRITE;
/*!40000 ALTER TABLE `outgoing_protocol` DISABLE KEYS */;
INSERT INTO `outgoing_protocol` VALUES (1,8,3,'2011-05-31 14:24:48',NULL,NULL,'Test outgoing',NULL,'2011-05-12 14:24:15',NULL,NULL,NULL,3,2,2,'2011-05-31 14:23:49','2011-06-02 06:36:14',NULL),(2,8,4,'2011-06-02 09:39:02',NULL,NULL,'Dokimi',NULL,'2011-06-01 09:39:00',NULL,'John Doe',NULL,2,2,2,'2011-06-02 09:39:02','2011-06-02 06:40:00',NULL);
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
  KEY `FK47A749068388E318` (`contact_id`),
  KEY `FK47A749066740CA55` (`outgoing_protocol_id`),
  CONSTRAINT `FK47A749066740CA55` FOREIGN KEY (`outgoing_protocol_id`) REFERENCES `outgoing_protocol` (`id`),
  CONSTRAINT `FK47A749068388E318` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`),
  CONSTRAINT `fk_7cadf144-215b-11e0-9059-080027b715d2` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7caeb0d4-215b-11e0-9059-080027b715d2` FOREIGN KEY (`outgoing_protocol_id`) REFERENCES `outgoing_protocol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outgoing_recipient`
--

LOCK TABLES `outgoing_recipient` WRITE;
/*!40000 ALTER TABLE `outgoing_recipient` DISABLE KEYS */;
INSERT INTO `outgoing_recipient` VALUES (6,1,0),(8,2,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parameter`
--

LOCK TABLES `parameter` WRITE;
/*!40000 ALTER TABLE `parameter` DISABLE KEYS */;
INSERT INTO `parameter` VALUES (1,NULL,NULL,'DISTRIBUTION_METHOD_EMAIL_ID',NULL,NULL,'3',NULL,NULL,NULL,NULL),(2,NULL,NULL,'DISTRIBUTION_METHOD_WEBSERVICE_ID',NULL,NULL,'7',NULL,NULL,NULL,NULL),(3,NULL,NULL,'SMTP_HOST',NULL,NULL,'mail.illumine.gr',NULL,NULL,NULL,NULL),(4,NULL,NULL,'SMTP_PASSWORD',NULL,NULL,'XINU666+1',NULL,NULL,NULL,NULL),(5,NULL,NULL,'SMTP_USER',NULL,NULL,'mountrakis@illumine.gr',NULL,NULL,NULL,NULL),(6,NULL,NULL,'EMAIL_FROM',NULL,NULL,'mountrakis@illumine.gr',NULL,NULL,NULL,NULL),(7,NULL,NULL,'OKM_NODE_PENDING_INCOMING',NULL,NULL,'/okm:root/PendingIncoming',NULL,NULL,NULL,NULL),(8,NULL,NULL,'OKM_NODE_INCOMING',NULL,NULL,'/okm:root/IncomingProtocol',NULL,NULL,NULL,NULL),(9,NULL,NULL,'OKM_NODE_PENDING_OUTGOING',NULL,NULL,'/okm:root/PendingOutgoing',NULL,NULL,NULL,NULL),(10,NULL,NULL,'OKM_NODE_OUTGOING',NULL,NULL,'/okm:root/OutgoingProtocol',NULL,NULL,NULL,NULL),(11,NULL,NULL,'PROTOCOL_BOOK_COMPANY',NULL,NULL,'ΕΛΛΑΚ',NULL,NULL,NULL,NULL),(12,NULL,NULL,'PROTOCOL_BOOK_AUTHOR',NULL,NULL,'ΕΛΛΑΚ',NULL,NULL,NULL,NULL),(13,NULL,NULL,'PROTOCOL_BOOK_CREATOR',NULL,NULL,'ΕΛΛΑΚ',NULL,NULL,NULL,NULL),(14,NULL,NULL,'PROTOCOL_BOOK_KEYWORDS',NULL,NULL,'Βιβλίο Πρωτοκόλλου',NULL,NULL,NULL,NULL),(15,NULL,NULL,'PROTOCOL_BOOK_SUBJECT',NULL,NULL,'Βιβλίο Πρωτοκόλλου',NULL,NULL,NULL,NULL),(16,NULL,NULL,'PROTOCOL_BOOK_TITLE',NULL,NULL,'Βιβλίο Πρωτοκόλλου',NULL,NULL,NULL,NULL),(17,NULL,NULL,'PROTOCOL_BOOK_FILE',NULL,NULL,'protocol_book.pdf',NULL,NULL,NULL,NULL),(18,NULL,NULL,'PROTOCOL_BOOK_FONT_FOLDER',NULL,NULL,'/home/aanagnostopoulos/workspace/eProtocol/WebContent/fonts',NULL,NULL,NULL,NULL),(19,NULL,NULL,'DISTRIBUTION_METHOD_NA_ID',NULL,NULL,'8',NULL,NULL,NULL,NULL),(20,NULL,NULL,'OKM_AUTH_PORT_ADDRESS',NULL,NULL,'http://localhost:8080/OpenKM/OKMAuth',NULL,NULL,NULL,NULL),(21,NULL,NULL,'OKM_DOCUMENT_PORT_ADDRESS',NULL,NULL,'http://localhost:8080/OpenKM/OKMDocument',NULL,NULL,NULL,NULL),(22,NULL,NULL,'OKM_FOLDER_PORT_ADDRESS',NULL,NULL,'http://localhost:8080/OpenKM/OKMFolder',NULL,NULL,NULL,NULL),(23,NULL,NULL,'OKM_SEARCH_PORT_ADDRESS',NULL,NULL,'http://localhost:8080/OpenKM/OKMSearch',NULL,NULL,NULL,NULL),(24,NULL,NULL,'DIAVGEIA_BASE_URL',NULL,NULL,'https://193.105.109.110/apofaseis-dokimes',NULL,NULL,NULL,NULL),(25,NULL,NULL,'DIAVGEIA_TIMEOUT',NULL,NULL,'6000',NULL,NULL,NULL,NULL),(26,NULL,NULL,'DIAVGEIA_TRUSTSTORE_FILE',NULL,NULL,'/home/aanagnostopoulos/.keystore',NULL,NULL,NULL,'2011-06-02 06:30:14'),(27,NULL,NULL,'DIAVGEIA_TRUSTSTORE_PASS',NULL,NULL,'changeit',NULL,NULL,NULL,'2011-06-02 06:30:14'),(28,NULL,NULL,'DIAVGEIA_USER',NULL,NULL,'366_admin',NULL,NULL,NULL,'2011-06-02 06:30:14'),(29,NULL,NULL,'DIAVGEIA_PASSWORD',NULL,NULL,'366',NULL,NULL,NULL,'2011-06-02 06:30:14'),(30,NULL,NULL,'DIAVGEIA_EMAIL',NULL,NULL,'angelosanagnostopoulos@gmail.com',NULL,NULL,NULL,'2011-06-02 06:30:14'),(31,NULL,NULL,'DIAVGEIA_ORGANIZATION_ID',NULL,NULL,'366',NULL,NULL,NULL,'2011-06-02 06:30:14'),(32,NULL,NULL,'DIAVGEIA_UNIT_ID',NULL,NULL,'2111',NULL,NULL,NULL,'2011-06-02 06:30:14'),(33,NULL,NULL,'DIAVGEIA_SIGNS',NULL,NULL,'16',NULL,NULL,NULL,'2011-06-02 06:30:14'),(34,NULL,NULL,'DIAVGEIA_TMP_FILES',NULL,NULL,'/tmp',NULL,NULL,NULL,'2011-06-02 06:30:14'),(35,NULL,NULL,'DIAVGEIA_URL_EIDOS_APOFASIS',NULL,NULL,'http://193.105.109.185/api/types.xml',NULL,NULL,NULL,NULL),(36,NULL,NULL,'DIAVGEIA_URL_EIDOS_THEMATIK',NULL,NULL,'http://193.105.109.185/api/tags.xml',NULL,NULL,NULL,NULL);
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
  KEY `FKED904B19B2A351E8` (`user_creator_id`),
  CONSTRAINT `FKED904B19B2A351E8` FOREIGN KEY (`user_creator_id`) REFERENCES `users` (`id`),
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
  KEY `FK3800AAEB42FA1627` (`task_document_id`),
  KEY `FK3800AAEBEAF49C47` (`task_type_id`),
  KEY `FK3800AAEB79877325` (`user_dispatcher_id`),
  KEY `FK3800AAEB9CEC3E1C` (`parent_task_id`),
  KEY `FK3800AAEBEA6B48ED` (`task_state_id`),
  KEY `FK3800AAEBBAA05478` (`project_id`),
  KEY `FK3800AAEBBBB75D87` (`task_priority_id`),
  KEY `FK3800AAEB8388E318` (`contact_id`),
  KEY `FK3800AAEBB2A351E8` (`user_creator_id`),
  KEY `FK3800AAEB7058CE27` (`task_result_id`),
  CONSTRAINT `FK3800AAEB42FA1627` FOREIGN KEY (`task_document_id`) REFERENCES `task_document` (`id`),
  CONSTRAINT `FK3800AAEB7058CE27` FOREIGN KEY (`task_result_id`) REFERENCES `task_result` (`id`),
  CONSTRAINT `FK3800AAEB79877325` FOREIGN KEY (`user_dispatcher_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK3800AAEB8388E318` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`),
  CONSTRAINT `FK3800AAEB9CEC3E1C` FOREIGN KEY (`parent_task_id`) REFERENCES `project_task` (`id`),
  CONSTRAINT `FK3800AAEBB2A351E8` FOREIGN KEY (`user_creator_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK3800AAEBBAA05478` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FK3800AAEBBBB75D87` FOREIGN KEY (`task_priority_id`) REFERENCES `task_priority` (`id`),
  CONSTRAINT `FK3800AAEBEA6B48ED` FOREIGN KEY (`task_state_id`) REFERENCES `task_state` (`id`),
  CONSTRAINT `FK3800AAEBEAF49C47` FOREIGN KEY (`task_type_id`) REFERENCES `task_type` (`id`),
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
  KEY `FK38016131BAA05478` (`project_id`),
  KEY `FK380161311037998` (`users_id`),
  CONSTRAINT `FK380161311037998` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK38016131BAA05478` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
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
  `diavgeia_ada_code` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `protocol_document_FKIndex1` (`incoming_protocol_id`),
  KEY `protocol_document_FKIndex2` (`outgoing_protocol_id`),
  KEY `fk_protocol_document_document_types1` (`document_types_id`),
  KEY `FK47525EE21ED49D9C` (`document_types_id`),
  KEY `FK47525EE2B4D7B2C9` (`incoming_protocol_id`),
  KEY `FK47525EE26740CA55` (`outgoing_protocol_id`),
  CONSTRAINT `FK47525EE21ED49D9C` FOREIGN KEY (`document_types_id`) REFERENCES `document_type` (`id`),
  CONSTRAINT `FK47525EE26740CA55` FOREIGN KEY (`outgoing_protocol_id`) REFERENCES `outgoing_protocol` (`id`),
  CONSTRAINT `FK47525EE2B4D7B2C9` FOREIGN KEY (`incoming_protocol_id`) REFERENCES `incoming_protocol` (`id`),
  CONSTRAINT `fk_7cbf98ea-215b-11e0-9059-080027b715d2` FOREIGN KEY (`incoming_protocol_id`) REFERENCES `incoming_protocol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_7cc08868-215b-11e0-9059-080027b715d2` FOREIGN KEY (`outgoing_protocol_id`) REFERENCES `outgoing_protocol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_protocol_document_document_types1` FOREIGN KEY (`document_types_id`) REFERENCES `document_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `protocol_document`
--

LOCK TABLES `protocol_document` WRITE;
/*!40000 ALTER TABLE `protocol_document` DISABLE KEYS */;
INSERT INTO `protocol_document` VALUES (65,NULL,31,2,'casaparts.log.1',1048644,'application/octet-stream',1,'adsfadsf','/okm:root/IncomingProtocol/2/casaparts.log.1','96b8a57d-4692-4f3d-8eea-5bccd01030ce',NULL),(66,1,NULL,2,'casaparts.log.1',1048644,'application/octet-stream',1,'key1','/okm:root/OutgoingProtocol/3/casaparts.log.1','286eb00c-5d1b-4e06-a27e-b070b9b3dec3',NULL),(67,2,NULL,1,'christoforouz_incident.pdf',1830544,'application/pdf',1,'test','/okm:root/OutgoingProtocol/4/christoforouz_incident.pdf','502284b1-4119-4037-9e18-0e94df4f2c17',NULL),(68,2,NULL,3,'eProtocol_DiavgeiaV1.0.pdf',164281,'application/pdf',2,'key','/okm:root/OutgoingProtocol/4/eProtocol_DiavgeiaV1.0.pdf','7ecee509-7838-4988-aeeb-60ef1b8a5671',NULL);
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
INSERT INTO `protocol_number` VALUES (1,4,4,1,2011,2,NULL,'2011-06-02 06:39:02');
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
  KEY `FKFBD0DF09210EDD5C` (`role_id`),
  KEY `FKFBD0DF094232955C` (`resource_id`),
  CONSTRAINT `FKFBD0DF09210EDD5C` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKFBD0DF094232955C` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`),
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
  KEY `FK33DF3295A75E2173` (`document_type_id`),
  CONSTRAINT `FK33DF3295A75E2173` FOREIGN KEY (`document_type_id`) REFERENCES `document_type` (`id`),
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
  KEY `FK6239874D702AC6D7` (`user_sender_id`),
  KEY `FK6239874DFF1BA18B` (`project_task_id`),
  KEY `FK6239874D368631DD` (`user_receiver_id`),
  CONSTRAINT `FK6239874D368631DD` FOREIGN KEY (`user_receiver_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK6239874D702AC6D7` FOREIGN KEY (`user_sender_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK6239874DFF1BA18B` FOREIGN KEY (`project_task_id`) REFERENCES `project_task` (`id`),
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
  KEY `FK161C9746210EDD5C` (`role_id`),
  KEY `FK161C97461037998` (`users_id`),
  CONSTRAINT `FK161C97461037998` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK161C9746210EDD5C` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
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
  KEY `FK6A68E0823508C6A` (`users_manager_id`),
  CONSTRAINT `FK6A68E0823508C6A` FOREIGN KEY (`users_manager_id`) REFERENCES `users` (`id`),
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

-- Dump completed on 2011-06-10 13:31:28
