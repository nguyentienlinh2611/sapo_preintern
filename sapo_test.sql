-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: sapo_test
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `attribute`
--

DROP TABLE IF EXISTS `attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribute` (
  `attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `attribute_name` varchar(50) NOT NULL,
  PRIMARY KEY (`attribute_id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES (124,'color'),(125,'size'),(126,'memory'),(127,'brand');
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute_value`
--

DROP TABLE IF EXISTS `attribute_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribute_value` (
  `attribute_value_id` int(11) NOT NULL AUTO_INCREMENT,
  `attribute_id` int(11) NOT NULL,
  `attribute_value` varchar(255) NOT NULL,
  PRIMARY KEY (`attribute_value_id`) USING BTREE,
  KEY `attribute_id` (`attribute_id`),
  CONSTRAINT `attribute_value_ibfk_1` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`attribute_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_value`
--

LOCK TABLES `attribute_value` WRITE;
/*!40000 ALTER TABLE `attribute_value` DISABLE KEYS */;
INSERT INTO `attribute_value` VALUES (112,124,'grey'),(113,125,'15,6 inch'),(114,126,'1TB HDD'),(115,125,'16 inch'),(116,127,'ASUS'),(117,124,'black'),(118,124,'black'),(119,127,'ACER'),(120,124,'white'),(121,127,'ACER'),(122,124,'white');
/*!40000 ALTER TABLE `attribute_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  `active_flg` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (28,'Laptop',1);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) DEFAULT NULL,
  `product_category_id` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `active_flg` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `product_catalog_id` (`product_category_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`product_category_id`) REFERENCES `categories` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (34,'Laptop ASUS X159UR',28,'2020-03-06 09:56:28',NULL,1),(35,'Laptop ASUS X440UR',28,'2020-03-07 05:03:04',NULL,1),(36,'Laptop ASUS X445UR',28,'2020-03-07 05:05:21',NULL,1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant`
--

DROP TABLE IF EXISTS `variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant` (
  `variant_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `active_flg` int(1) DEFAULT NULL,
  PRIMARY KEY (`variant_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `variant_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant`
--

LOCK TABLES `variant` WRITE;
/*!40000 ALTER TABLE `variant` DISABLE KEYS */;
INSERT INTO `variant` VALUES (33,34,1),(34,35,1),(35,36,1),(36,36,1),(37,36,1);
/*!40000 ALTER TABLE `variant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant_attribute`
--

DROP TABLE IF EXISTS `variant_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant_attribute` (
  `variant_id` int(11) NOT NULL,
  `attribute_value_id` int(11) NOT NULL,
  PRIMARY KEY (`variant_id`,`attribute_value_id`) USING BTREE,
  KEY `attribute_value_id` (`attribute_value_id`),
  CONSTRAINT `variant_attribute_ibfk_2` FOREIGN KEY (`attribute_value_id`) REFERENCES `attribute_value` (`attribute_value_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `variant_attribute_ibfk_3` FOREIGN KEY (`variant_id`) REFERENCES `variant` (`variant_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant_attribute`
--

LOCK TABLES `variant_attribute` WRITE;
/*!40000 ALTER TABLE `variant_attribute` DISABLE KEYS */;
INSERT INTO `variant_attribute` VALUES (33,112),(34,112),(35,112),(33,113),(33,114),(34,115),(35,115),(36,116),(36,117),(37,119),(37,120);
/*!40000 ALTER TABLE `variant_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'sapo_test'
--

--
-- Dumping routines for database 'sapo_test'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-07 12:10:52
