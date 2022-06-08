-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (arm64)
--
-- Host: localhost    Database: BankManager
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `Cards`
--

DROP TABLE IF EXISTS `Cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cards` (
  `username` varchar(30) DEFAULT NULL,
  `nr` varchar(16) NOT NULL,
  `pin` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`nr`),
  KEY `username` (`username`),
  CONSTRAINT `cards_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cards`
--

LOCK TABLES `Cards` WRITE;
/*!40000 ALTER TABLE `Cards` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Credits`
--

DROP TABLE IF EXISTS `Credits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Credits` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `amount` float DEFAULT NULL,
  `start date` date DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `rate` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  CONSTRAINT `credits_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `credits_chk_1` CHECK (((`amount` >= 0) and (`amount` <= 50000))),
  CONSTRAINT `credits_chk_2` CHECK ((`rate` between 0 and 100))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Credits`
--

LOCK TABLES `Credits` WRITE;
/*!40000 ALTER TABLE `Credits` DISABLE KEYS */;
/*!40000 ALTER TABLE `Credits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IncomingHistoryOrdinary`
--

DROP TABLE IF EXISTS `IncomingHistoryOrdinary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `IncomingHistoryOrdinary` (
  `Operation Date` varchar(30) NOT NULL,
  `Transfer Type` varchar(50) NOT NULL,
  `Account nr from` varchar(28) NOT NULL,
  `Account nr to` varchar(28) DEFAULT NULL,
  `Phone nr to` varchar(9) DEFAULT NULL,
  `Transfer Amount` double DEFAULT NULL,
  `Transfer Currency` varchar(3) NOT NULL,
  `Total Transfer Cost` double DEFAULT NULL,
  `Transfer Title` varchar(1000) NOT NULL,
  `Start Date` varchar(30) DEFAULT NULL,
  `End Date` varchar(30) DEFAULT NULL,
  `Transfer Cycle` int DEFAULT NULL,
  `Transfer Cycle Units` varchar(10) DEFAULT NULL,
  `Sender first name` varchar(20) DEFAULT NULL,
  `Sender last name` varchar(20) DEFAULT NULL,
  `Sender Town` varchar(30) DEFAULT NULL,
  `Sender Postcode` varchar(6) DEFAULT NULL,
  `Sender Street` varchar(30) DEFAULT NULL,
  `Sender Street number` varchar(10) DEFAULT NULL,
  KEY `Account nr to` (`Account nr to`),
  CONSTRAINT `incominghistoryordinary_ibfk_1` FOREIGN KEY (`Account nr to`) REFERENCES `OrdinaryAccounts` (`Account number`),
  CONSTRAINT `incominghistoryordinary_chk_1` CHECK ((`Transfer Amount` > 0)),
  CONSTRAINT `incominghistoryordinary_chk_2` CHECK ((`Total Transfer Cost` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IncomingHistoryOrdinary`
--

LOCK TABLES `IncomingHistoryOrdinary` WRITE;
/*!40000 ALTER TABLE `IncomingHistoryOrdinary` DISABLE KEYS */;
/*!40000 ALTER TABLE `IncomingHistoryOrdinary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IncomingHistorySavings`
--

DROP TABLE IF EXISTS `IncomingHistorySavings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `IncomingHistorySavings` (
  `Operation Date` varchar(30) NOT NULL,
  `Transfer Type` varchar(50) NOT NULL,
  `Account nr from` varchar(28) NOT NULL,
  `Account nr to` varchar(28) DEFAULT NULL,
  `Phone nr to` varchar(9) DEFAULT NULL,
  `Transfer Amount` double DEFAULT NULL,
  `Transfer Currency` varchar(3) NOT NULL,
  `Total Transfer Cost` double DEFAULT NULL,
  `Transfer Title` varchar(1000) NOT NULL,
  `Start Date` varchar(30) DEFAULT NULL,
  `End Date` varchar(30) DEFAULT NULL,
  `Transfer Cycle` int DEFAULT NULL,
  `Transfer Cycle Units` varchar(10) DEFAULT NULL,
  `Sender first name` varchar(20) DEFAULT NULL,
  `Sender last name` varchar(20) DEFAULT NULL,
  `Sender Town` varchar(30) DEFAULT NULL,
  `Sender Postcode` varchar(6) DEFAULT NULL,
  `Sender Street` varchar(30) DEFAULT NULL,
  `Sender Street number` varchar(10) DEFAULT NULL,
  KEY `Account nr to` (`Account nr to`),
  CONSTRAINT `incominghistorysavings_ibfk_1` FOREIGN KEY (`Account nr to`) REFERENCES `OrdinaryAccounts` (`Account number`),
  CONSTRAINT `incominghistorysavings_chk_1` CHECK ((`Transfer Amount` > 0)),
  CONSTRAINT `incominghistorysavings_chk_2` CHECK ((`Total Transfer Cost` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IncomingHistorySavings`
--

LOCK TABLES `IncomingHistorySavings` WRITE;
/*!40000 ALTER TABLE `IncomingHistorySavings` DISABLE KEYS */;
/*!40000 ALTER TABLE `IncomingHistorySavings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrdinaryAccounts`
--

DROP TABLE IF EXISTS `OrdinaryAccounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrdinaryAccounts` (
  `username` varchar(30) DEFAULT NULL,
  `Account number` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Balance` double DEFAULT NULL,
  PRIMARY KEY (`Account number`),
  KEY `username` (`username`),
  CONSTRAINT `ordinaryaccounts_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ordinaryaccounts_chk_1` CHECK ((`Balance` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrdinaryAccounts`
--

LOCK TABLES `OrdinaryAccounts` WRITE;
/*!40000 ALTER TABLE `OrdinaryAccounts` DISABLE KEYS */;
INSERT INTO `OrdinaryAccounts` VALUES ('Adix1999','PL96116022020000000366300252',0);
/*!40000 ALTER TABLE `OrdinaryAccounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OutgoingHistoryOrdinary`
--

DROP TABLE IF EXISTS `OutgoingHistoryOrdinary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OutgoingHistoryOrdinary` (
  `Operation Date` varchar(30) NOT NULL,
  `Transfer Type` varchar(50) NOT NULL,
  `Account nr from` varchar(28) NOT NULL,
  `Account nr to` varchar(28) DEFAULT NULL,
  `Phone nr to` varchar(9) DEFAULT NULL,
  `Transfer Amount` double DEFAULT NULL,
  `Transfer Currency` varchar(3) NOT NULL,
  `Total Transfer Cost` double DEFAULT NULL,
  `Transfer Title` varchar(1000) NOT NULL,
  `Start Date` varchar(30) DEFAULT NULL,
  `End Date` varchar(30) DEFAULT NULL,
  `Transfer Cycle` int DEFAULT NULL,
  `Transfer Cycle Units` varchar(10) DEFAULT NULL,
  `Receiver first name` varchar(20) DEFAULT NULL,
  `Receiver last name` varchar(20) DEFAULT NULL,
  `Receiver Town` varchar(30) DEFAULT NULL,
  `Receiver Postcode` varchar(6) DEFAULT NULL,
  `Receiver Street` varchar(30) DEFAULT NULL,
  `Receiver Street number` varchar(10) DEFAULT NULL,
  KEY `Account nr from` (`Account nr from`),
  CONSTRAINT `outgoinghistoryordinary_ibfk_1` FOREIGN KEY (`Account nr from`) REFERENCES `OrdinaryAccounts` (`Account number`),
  CONSTRAINT `outgoinghistoryordinary_chk_1` CHECK ((`Transfer Amount` > 0)),
  CONSTRAINT `outgoinghistoryordinary_chk_2` CHECK ((`Total Transfer Cost` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OutgoingHistoryOrdinary`
--

LOCK TABLES `OutgoingHistoryOrdinary` WRITE;
/*!40000 ALTER TABLE `OutgoingHistoryOrdinary` DISABLE KEYS */;
INSERT INTO `OutgoingHistoryOrdinary` VALUES ('a','b','PL96116022020000000366300252','d','e',1,'a',1,'b','','',0,'','a','b','','','','');
/*!40000 ALTER TABLE `OutgoingHistoryOrdinary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OutgoingHistorySavings`
--

DROP TABLE IF EXISTS `OutgoingHistorySavings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OutgoingHistorySavings` (
  `Operation Date` varchar(30) NOT NULL,
  `Transfer Type` varchar(50) NOT NULL,
  `Account nr from` varchar(28) NOT NULL,
  `Account nr to` varchar(28) DEFAULT NULL,
  `Phone nr to` varchar(9) DEFAULT NULL,
  `Transfer Amount` double DEFAULT NULL,
  `Transfer Currency` varchar(3) NOT NULL,
  `Total Transfer Cost` double DEFAULT NULL,
  `Transfer Title` varchar(1000) NOT NULL,
  `Start Date` varchar(30) DEFAULT NULL,
  `End Date` varchar(30) DEFAULT NULL,
  `Transfer Cycle` int DEFAULT NULL,
  `Transfer Cycle Units` varchar(10) DEFAULT NULL,
  `Receiver first name` varchar(20) DEFAULT NULL,
  `Receiver last name` varchar(20) DEFAULT NULL,
  `Receiver Town` varchar(30) DEFAULT NULL,
  `Receiver Postcode` varchar(6) DEFAULT NULL,
  `Receiver Street` varchar(30) DEFAULT NULL,
  `Receiver Street number` varchar(10) DEFAULT NULL,
  KEY `Account nr from` (`Account nr from`),
  CONSTRAINT `outgoinghistorysavings_ibfk_1` FOREIGN KEY (`Account nr from`) REFERENCES `SavingsAccounts` (`Account number`),
  CONSTRAINT `outgoinghistorysavings_chk_1` CHECK ((`Transfer Amount` > 0)),
  CONSTRAINT `outgoinghistorysavings_chk_2` CHECK ((`Total Transfer Cost` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OutgoingHistorySavings`
--

LOCK TABLES `OutgoingHistorySavings` WRITE;
/*!40000 ALTER TABLE `OutgoingHistorySavings` DISABLE KEYS */;
INSERT INTO `OutgoingHistorySavings` VALUES ('a','b','PL66726372637632768909000967','d','e',1,'a',1,'b','','',0,'','a','b','','','','');
/*!40000 ALTER TABLE `OutgoingHistorySavings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SavingsAccounts`
--

DROP TABLE IF EXISTS `SavingsAccounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SavingsAccounts` (
  `username` varchar(30) DEFAULT NULL,
  `Account number` varchar(28) NOT NULL,
  `Balance` double DEFAULT NULL,
  `Rate` float DEFAULT NULL,
  PRIMARY KEY (`Account number`),
  KEY `username` (`username`),
  CONSTRAINT `savingsaccounts_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `savingsaccounts_chk_1` CHECK ((`Balance` >= 0)),
  CONSTRAINT `savingsaccounts_chk_2` CHECK ((`Rate` between 0 and 100))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SavingsAccounts`
--

LOCK TABLES `SavingsAccounts` WRITE;
/*!40000 ALTER TABLE `SavingsAccounts` DISABLE KEYS */;
INSERT INTO `SavingsAccounts` VALUES ('Adix1999','PL66726372637632768909000967',0,1);
/*!40000 ALTER TABLE `SavingsAccounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `AppCode` varchar(4) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('Adix1999','Adix','adriandajakaj9@gmail.com',''),('Adr','passwd','ad@gmail.com','1234'),('user2','user2pass','user9@gmail.com','');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UsersData`
--

DROP TABLE IF EXISTS `UsersData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UsersData` (
  `username` varchar(30) DEFAULT NULL,
  `First name` varchar(20) DEFAULT NULL,
  `Last name` varchar(20) DEFAULT NULL,
  `PESEL` varchar(30) DEFAULT NULL,
  `Phone number` varchar(9) DEFAULT NULL,
  `Town` varchar(30) DEFAULT NULL,
  `Postcode` varchar(6) DEFAULT NULL,
  `Street` varchar(30) DEFAULT NULL,
  `Street number` varchar(10) DEFAULT NULL,
  KEY `username` (`username`),
  CONSTRAINT `usersdata_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UsersData`
--

LOCK TABLES `UsersData` WRITE;
/*!40000 ALTER TABLE `UsersData` DISABLE KEYS */;
INSERT INTO `UsersData` VALUES ('Adix1999','Adrian','Dajakaj','12345','794974148','odrzykon','38-406','łęgowa','4/17');
/*!40000 ALTER TABLE `UsersData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'BankManager'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-08 11:36:45
