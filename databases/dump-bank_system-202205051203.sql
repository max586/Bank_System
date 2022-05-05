-- MySQL dump 10.13  Distrib 8.0.28, for Linux (x86_64)
--
-- Host: localhost    Database: bank_system
-- ------------------------------------------------------
-- Server version	8.0.28-0ubuntu0.21.10.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('user1','q[q]sqsmq0smlZX','abc@sth.com'),('user2','zapkzpakzq1A../','jojo@kok'),('user3',',za;x;xmaxmslxM;oihhJGJ1','poplp@qui'),('user4','31okspqswKHh8VV./','ihkkl@mnqk'),('user5','QSQSQKSHKJXnbvnavxa7871..','ppp@zzz'),('user6','pd5U}#ySX><n','maks.ovsienko2@gmail.com'),('username is saved','2Eh&];taL7Mx','maks.ovsienko2@gmail.com');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UsersData`
--

DROP TABLE IF EXISTS `UsersData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UsersData` (
  `username` varchar(30) NOT NULL,
  `First name` varchar(20) NOT NULL,
  `Last name` varchar(20) NOT NULL,
  `City` varchar(20) DEFAULT NULL,
  `Address` varchar(30) DEFAULT NULL,
  `PESEL` varchar(30) DEFAULT NULL,
  KEY `username` (`username`),
  CONSTRAINT `UsersData_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UsersData`
--

LOCK TABLES `UsersData` WRITE;
/*!40000 ALTER TABLE `UsersData` DISABLE KEYS */;
INSERT INTO `UsersData` VALUES ('user1','Max','Apol','Krakow','ul.1, 100','78022181674'),('user2','Alex','Bos','Zakopane','ul.10, 120','48022235482'),('user3','Morty','Bonon','Warszawa','ul. 7, 190','51062019893'),('user4','Sam','Camel','Bydgoszcz','ul. 12, 11','57030885255'),('user5','Lila','Stich','Gdansk','ul. 20, 99','52082916496'),('user6','max','klaa','kyiv','ul. 1, 12','128128012'),('username is saved','max','poio','Krakow','ul 1, 12','84091042652');
/*!40000 ALTER TABLE `UsersData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'bank_system'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-05 12:03:30
