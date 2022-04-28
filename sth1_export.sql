-- MySQL dump 10.13  Distrib 8.0.28, for Linux (x86_64)
--
-- Host: localhost    Database: sth1
-- ------------------------------------------------------
-- Server version	8.0.28-0ubuntu0.21.10.3

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
-- Table structure for table `Credentials`
--

DROP TABLE IF EXISTS `Credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Credentials` (
  `username` varchar(30) NOT NULL,
  `login` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  KEY `username` (`username`),
  CONSTRAINT `Credentials_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Credentials`
--

LOCK TABLES `Credentials` WRITE;
/*!40000 ALTER TABLE `Credentials` DISABLE KEYS */;
INSERT INTO `Credentials` VALUES ('user2','cptBlack','567zbnma9alla'),('user3','morty','mnw777'),('user4','oko','zmnmla189'),('user5','lil','plplp111'),('user1','ababagalamaga','oqod2901');
/*!40000 ALTER TABLE `Credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `username` varchar(20) NOT NULL,
  `phone_number` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('user1','0908796','ahjg@cos'),('user2','0979796','jojo@kok'),('user3','5554322','poplp@qui'),('user4','318713','ihkkl@mnqk'),('user5','91919','ppp@zzz');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faktury`
--

DROP TABLE IF EXISTS `faktury`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faktury` (
  `faktura_id` smallint unsigned NOT NULL AUTO_INCREMENT,
  `klient_id` smallint unsigned DEFAULT NULL,
  `data_faktury` date NOT NULL,
  `wartosc_faktury` decimal(10,2) unsigned NOT NULL,
  `opis_faktury` tinytext,
  PRIMARY KEY (`faktura_id`),
  KEY `data_faktury` (`data_faktury`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faktury`
--

LOCK TABLES `faktury` WRITE;
/*!40000 ALTER TABLE `faktury` DISABLE KEYS */;
/*!40000 ALTER TABLE `faktury` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorie_wydatkow`
--

DROP TABLE IF EXISTS `kategorie_wydatkow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorie_wydatkow` (
  `kategoria_wydatku_id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `kategoria_wydatku` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`kategoria_wydatku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorie_wydatkow`
--

LOCK TABLES `kategorie_wydatkow` WRITE;
/*!40000 ALTER TABLE `kategorie_wydatkow` DISABLE KEYS */;
INSERT INTO `kategorie_wydatkow` VALUES (1,'podroz-hotel'),(2,'soft'),(3,'ksiazki');
/*!40000 ALTER TABLE `kategorie_wydatkow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `klienci`
--

DROP TABLE IF EXISTS `klienci`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `klienci` (
  `klient_id` smallint unsigned NOT NULL AUTO_INCREMENT,
  `nazwa_klienta` varchar(40) NOT NULL,
  `ulica_klienta` varchar(80) DEFAULT NULL,
  `miasto_klienta` varchar(30) DEFAULT NULL,
  `stan_klienta` varchar(2) DEFAULT NULL,
  `kod_pocztowy_klienta` mediumint unsigned DEFAULT NULL,
  `telefon_klienta` varchar(14) DEFAULT NULL,
  `osoba_kontaktowa` varchar(40) DEFAULT NULL,
  `email_kontaktowy` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`klient_id`),
  KEY `nazwa_klienta` (`nazwa_klienta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `klienci`
--

LOCK TABLES `klienci` WRITE;
/*!40000 ALTER TABLE `klienci` DISABLE KEYS */;
/*!40000 ALTER TABLE `klienci` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wydatki`
--

DROP TABLE IF EXISTS `wydatki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wydatki` (
  `wydatek_id` smallint unsigned NOT NULL AUTO_INCREMENT,
  `kategoria_wydatku_id` tinyint unsigned DEFAULT NULL,
  `wartosc_wydatku` decimal(10,2) DEFAULT NULL,
  `opis_wydatku` tinytext,
  `data_zaplaty` date DEFAULT NULL,
  PRIMARY KEY (`wydatek_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wydatki`
--

LOCK TABLES `wydatki` WRITE;
/*!40000 ALTER TABLE `wydatki` DISABLE KEYS */;
/*!40000 ALTER TABLE `wydatki` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-28 12:28:27
