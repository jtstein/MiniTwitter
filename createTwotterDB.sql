CREATE DATABASE  IF NOT EXISTS `twitterdb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `twitterdb`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: twitterdb
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follow` (
  `followid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `followeduserid` int(11) NOT NULL,
  `followdate` datetime NOT NULL,
  PRIMARY KEY (`followid`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (15,4,1,'2017-11-26 03:59:30'),(42,1,4,'2017-11-26 07:43:22'),(43,1,3,'2017-11-26 07:54:51'),(45,12,1,'2017-11-26 08:21:27');
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtag`
--

DROP TABLE IF EXISTS `hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hashtag` (
  `hashtagid` int(11) NOT NULL AUTO_INCREMENT,
  `hashtagtext` varchar(45) NOT NULL,
  `hashtagcount` int(11) NOT NULL,
  PRIMARY KEY (`hashtagid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtag`
--

LOCK TABLES `hashtag` WRITE;
/*!40000 ALTER TABLE `hashtag` DISABLE KEYS */;
INSERT INTO `hashtag` VALUES (1,'#coffee',2),(5,'#Bitcoin',1);
/*!40000 ALTER TABLE `hashtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `twit`
--

DROP TABLE IF EXISTS `twit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `twit` (
  `twitid` int(11) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(45) NOT NULL,
  `useremail` varchar(45) NOT NULL,
  `text` varchar(140) NOT NULL,
  `twitdate` datetime NOT NULL,
  `mentionedUserID` varchar(45) NOT NULL,
  `userid` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`twitid`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `twit`
--

LOCK TABLES `twit` WRITE;
/*!40000 ALTER TABLE `twit` DISABLE KEYS */;
INSERT INTO `twit` VALUES (1,'Jordan Stein','jor.stein@gmail.com','coding all night','2017-10-19 00:00:00','-1',1),(3,'Thomas Danforth','jestertommy@yahoo.com','It\'s pronounced tweeeeeet','2017-10-19 00:00:00','-1',2),(5,'Deodat Lawson','lawson@gmail.com','CSS is a pain in the ASS','2017-10-19 00:00:00','-1',6),(6,'Abagail Hobbs','hobby@aol.com','studying all night #coffee','2017-10-19 00:00:00','-1',3),(7,'Deodat Lawson','lawson@gmail.com','@Jordan Stein It\'s time to go to bed.','2017-10-19 00:00:00','1',6),(8,'Giles Corey','gcorey@salem.com','Anyone doing anything tonight??','2017-11-05 00:00:00','-1',4),(15,'Jordan Stein','jor.stein@gmail.com','@Giles Corey whats up?','2017-11-16 02:59:53','4',1),(28,'Giles Corey','gcorey@salem.com','I need #coffee so much','2017-11-26 03:30:20','-1',4),(44,'Giles Corey','gcorey@salem.com','Whats going on?','2017-11-26 07:30:48','-1',4),(47,'Jordan Stein','jor.stein@gmail.com','#Bitcoin just hit a new ATH!','2017-11-26 07:55:43','-1',1),(48,'Giles Corey','gcorey@salem.com','@Thomas Danforth did you get my email?','2017-11-26 07:57:02','2',4),(50,'Fred Hodwick','fhodwick7@gmail.com','Hanging out with @Jordan Stein','2017-11-26 08:21:25','1',12);
/*!40000 ALTER TABLE `twit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `twithashtag`
--

DROP TABLE IF EXISTS `twithashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `twithashtag` (
  `twitid` int(11) NOT NULL,
  `hashtagid` int(11) NOT NULL,
  PRIMARY KEY (`twitid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `twithashtag`
--

LOCK TABLES `twithashtag` WRITE;
/*!40000 ALTER TABLE `twithashtag` DISABLE KEYS */;
INSERT INTO `twithashtag` VALUES (27,1),(28,1),(47,5);
/*!40000 ALTER TABLE `twithashtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `fullname` varchar(45) NOT NULL,
  `birthdate` date NOT NULL,
  `password` varchar(64) NOT NULL,
  `salt` varchar(64) NOT NULL DEFAULT 'NA',
  `securityquestion` varchar(45) NOT NULL,
  `securityanswer` varchar(45) NOT NULL,
  `numtwits` int(11) NOT NULL,
  `lastlogin` datetime NOT NULL DEFAULT '1970-01-02 00:00:00',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'jor.stein@gmail.com','Jordan Stein','1993-02-17','11ba6a2915f389e044da6b179702d2d24b302c0cd1acaa317573903daea57483','qvsteVR5f/dMke6dd9E9xkwSs//ag2AAFyLRQaRIqQg=','pet','puppy',3,'2017-11-26 08:26:59'),(2,'jestertommy@yahoo.com','Thomas Danforth','1992-03-18','oK123','NA','school','Baxton',1,'1970-01-02 00:00:00'),(3,'hobby@aol.com','Abagail Hobbs','1987-08-21','iAmInnocent7','NA','car','what is a car',1,'1970-01-02 00:00:00'),(4,'gcorey@salem.com','Giles Corey','1972-02-02','2872d3eb049c3b4699ecb332ceb511a7b108e6252016074b05c5260e72bcf592','SlawGRpOZF479l+Fu1rYeaqdgKl8UCiNVjddKYQymzY=','car','fun car',4,'2017-11-26 07:57:08'),(5,'cottonm@gmail.com','Cotten Mather','2000-06-19','theBest22','NA','pet','mr bigglesworth',0,'1970-01-02 00:00:00'),(6,'lawson@gmail.com','Deodat Lawson','1967-09-23','Lawson8','NA','car','horse',2,'1970-01-02 00:00:00'),(7,'samsew@yahoo.com','Samuel Sewall','1981-02-23','Sammy3','NA','pet','douglas',0,'1970-01-02 00:00:00'),(8,'crispy22@gmail.com','Pretzel Crisp','1962-09-18','gooD7','NA','car','focus',0,'1970-01-02 00:00:00'),(9,'gcarl@gmail.com','Garret Carlsen','1212-12-12','gCarl9','NA','school','bear elementary',0,'1970-01-02 00:00:00'),(12,'fhodwick7@gmail.com','Fred Hodwick','1982-08-19','8206bebaf9a1b0d864477f2aac7ec617f220162ea72c1e188cc8ce5f2e7c8659','SZE2nCL/YnfYdxRS/VHHtH44FePlRl6KIKOcfppa0gY=','school','dropped out',1,'2017-11-26 08:21:30');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-26 20:27:57
