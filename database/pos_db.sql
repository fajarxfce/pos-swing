-- MySQL dump 10.13  Distrib 9.2.0, for Linux (x86_64)
--
-- Host: localhost    Database: pos_db
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Electronics',NULL),(2,'Groceries',NULL),(3,'Clothing',NULL),(4,'Home Appliances',NULL),(5,'Furniture',NULL),(6,'Books',NULL),(7,'Sports Equipment',NULL),(8,'Toys',NULL),(9,'Stationery',NULL),(10,'Beauty Products',NULL),(11,'Kitchen Utensils',NULL),(12,'Gardening Tools',NULL),(13,'Office Supplies',NULL),(14,'Automotive',NULL),(15,'Baby Products',NULL),(16,'Health Care',NULL),(17,'Shoes',NULL),(18,'Jewelry',NULL),(19,'Pet Supplies',NULL),(20,'Music Instruments',NULL);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `price` decimal(10,2) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `category_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'EL001','Samsung Smart TV 55\"',NULL,10499850.00,15,1,'2025-04-14 02:53:39'),(2,'EL002','Apple iPhone 13',NULL,13499850.00,25,1,'2025-04-14 02:53:39'),(3,'EL003','Dell XPS 15 Laptop',NULL,19499850.00,10,1,'2025-04-14 02:53:39'),(4,'EL004','Sony Wireless Headphones',NULL,2249850.00,30,1,'2025-04-14 02:53:39'),(5,'EL005','Logitech Wireless Mouse',NULL,449850.00,50,1,'2025-04-14 02:53:39'),(6,'GR001','Organic Bananas (Bunch)',NULL,44850.00,100,2,'2025-04-14 02:53:39'),(7,'GR002','Whole Milk (1 Gallon)',NULL,52350.00,40,2,'2025-04-14 02:53:39'),(8,'GR003','White Bread',NULL,29850.00,44,2,'2025-04-14 02:53:39'),(9,'GR004','Free Range Eggs (Dozen)',NULL,74850.00,30,2,'2025-04-14 02:53:39'),(10,'GR005','Ground Coffee (12oz)',NULL,134850.00,24,2,'2025-04-14 02:53:39'),(11,'CL001','Men\'s Cotton T-shirt',NULL,299850.00,40,3,'2025-04-14 02:53:39'),(12,'CL002','Women\'s Jeans',NULL,599850.00,35,3,'2025-04-14 02:53:39'),(13,'CL003','Kids Winter Jacket',NULL,749850.00,20,3,'2025-04-14 02:53:39'),(14,'CL004','Cotton Socks Pack',NULL,149850.00,60,3,'2025-04-14 02:53:39'),(15,'CL005','Summer Hat',NULL,224850.00,25,3,'2025-04-14 02:53:39'),(16,'HA001','Microwave Oven',NULL,1349850.00,14,4,'2025-04-14 02:53:39'),(17,'HA002','Coffee Maker',NULL,749850.00,20,4,'2025-04-14 02:53:39'),(18,'HA003','Toaster',NULL,449850.00,25,4,'2025-04-14 02:53:39'),(19,'HA004','Vacuum Cleaner',NULL,2249850.00,10,4,'2025-04-14 02:53:39'),(20,'HA005','Electric Kettle',NULL,524850.00,30,4,'2025-04-14 02:53:39'),(21,'FU001','Dining Table',NULL,4499850.00,5,5,'2025-04-14 02:53:39'),(22,'FU002','Office Chair',NULL,1949850.00,15,5,'2025-04-14 02:53:39'),(23,'FU003','Sofa Set',NULL,8999850.00,8,5,'2025-04-14 02:53:39'),(24,'FU004','Bookshelf',NULL,2249850.00,12,5,'2025-04-14 02:53:39'),(25,'FU005','Queen Size Bed',NULL,5249850.00,7,5,'2025-04-14 02:53:39'),(26,'BO001','Python Programming Guide',NULL,524850.00,20,6,'2025-04-14 02:53:39'),(27,'BO002','Harry Potter Complete Set',NULL,1349850.00,15,6,'2025-04-14 02:53:39'),(28,'BO003','Java For Beginners',NULL,449850.00,25,6,'2025-04-14 02:53:39'),(29,'BO004','Game of Thrones Book 1',NULL,299850.00,30,6,'2025-04-14 02:53:39'),(30,'BO005','The Art of War',NULL,224850.00,35,6,'2025-04-14 02:53:39'),(31,'SP001','Tennis Racket',NULL,899850.00,18,7,'2025-04-14 02:53:39'),(32,'SP002','Basketball',NULL,374850.00,22,7,'2025-04-14 02:53:39'),(33,'SP003','Yoga Mat',NULL,299850.00,40,7,'2025-04-14 02:53:39'),(34,'SP004','Dumbbells Set',NULL,1199850.00,14,7,'2025-04-14 02:53:39'),(35,'SP005','Running Shoes',NULL,1349850.00,25,7,'2025-04-14 02:53:39'),(36,'TO001','LEGO Star Wars Set',NULL,749850.00,20,8,'2025-04-14 02:53:39'),(37,'TO002','Barbie Doll',NULL,299850.00,35,8,'2025-04-14 02:53:39'),(38,'TO003','Remote Control Car',NULL,599850.00,18,8,'2025-04-14 02:53:39'),(39,'TO004','Board Game - Monopoly',NULL,374850.00,22,8,'2025-04-14 02:53:39'),(40,'TO005','Stuffed Teddy Bear',NULL,224850.00,40,8,'2025-04-14 02:53:39'),(41,'ST001','Ballpoint Pen Pack',NULL,89850.00,100,9,'2025-04-14 02:53:39'),(42,'ST002','Notebook Set',NULL,149850.00,57,9,'2025-04-14 02:53:39'),(43,'ST003','Desk Organizer',NULL,299850.00,30,9,'2025-04-14 02:53:39'),(44,'ST004','Scissors Pack',NULL,119850.00,45,9,'2025-04-14 02:53:39'),(45,'ST005','Whiteboard Marker Set',NULL,134850.00,50,9,'2025-04-14 02:53:39'),(46,'BP001','Face Moisturizer',NULL,374850.00,35,10,'2025-04-14 02:53:39'),(47,'BP002','Lipstick Set',NULL,299850.00,40,10,'2025-04-14 02:53:39'),(48,'BP003','Shampoo (16oz)',NULL,149850.00,50,10,'2025-04-14 02:53:39'),(49,'BP004','Perfume',NULL,749850.00,25,10,'2025-04-14 02:53:39'),(50,'BP005','Makeup Brush Set',NULL,449850.00,20,10,'2025-04-14 02:53:39'),(51,'KU001','Knife Set',NULL,599850.00,25,11,'2025-04-14 02:53:39'),(52,'KU002','Cooking Pot',NULL,449850.00,30,11,'2025-04-14 02:53:39'),(53,'KU003','Measuring Cups',NULL,194850.00,40,11,'2025-04-14 02:53:39'),(54,'KU004','Silicone Spatula Set',NULL,224850.00,34,11,'2025-04-14 02:53:39'),(55,'KU005','Cutting Board',NULL,299850.00,29,11,'2025-04-14 02:53:39'),(56,'GT001','Garden Shovel',NULL,374850.00,20,12,'2025-04-14 02:53:39'),(57,'GT002','Pruning Shears',NULL,299850.00,25,12,'2025-04-14 02:53:39'),(58,'GT003','Watering Can',NULL,224850.00,30,12,'2025-04-14 02:53:39'),(59,'GT004','Plant Pot Set',NULL,449850.00,40,12,'2025-04-14 02:53:39'),(60,'GT005','Garden Gloves',NULL,149850.00,50,12,'2025-04-14 02:53:39'),(61,'OS001','Stapler',NULL,134850.00,39,13,'2025-04-14 02:53:39'),(62,'OS002','File Folders (Pack of 50)',NULL,194850.00,35,13,'2025-04-14 02:53:39'),(63,'OS003','Printer Paper (Ream)',NULL,104850.00,60,13,'2025-04-14 02:53:39'),(64,'OS004','Desk Lamp',NULL,374850.00,20,13,'2025-04-14 02:53:39'),(65,'OS005','Business Card Holder',NULL,224850.00,25,13,'2025-04-14 02:53:39'),(66,'AU001','Car Wax',NULL,299850.00,30,14,'2025-04-14 02:53:39'),(67,'AU002','Windshield Wipers',NULL,374850.00,40,14,'2025-04-14 02:53:39'),(68,'AU003','Air Freshener',NULL,89850.00,80,14,'2025-04-14 02:53:39'),(69,'AU004','Motor Oil',NULL,344850.00,50,14,'2025-04-14 02:53:39'),(70,'AU005','Tire Pressure Gauge',NULL,149850.00,35,14,'2025-04-14 02:53:39'),(71,'BB001','Baby Formula',NULL,374850.00,29,15,'2025-04-14 02:53:39'),(72,'BB002','Diapers (Pack of 50)',NULL,299850.00,40,15,'2025-04-14 02:53:39'),(73,'BB003','Baby Wipes',NULL,89850.00,70,15,'2025-04-14 02:53:39'),(74,'BB004','Baby Bottle Set',NULL,239850.00,25,15,'2025-04-14 02:53:39'),(75,'BB005','Baby Monitor',NULL,1349850.00,15,15,'2025-04-14 02:53:39'),(76,'HC001','First Aid Kit',NULL,449850.00,40,16,'2025-04-14 02:53:39'),(77,'HC002','Digital Thermometer',NULL,224850.00,35,16,'2025-04-14 02:53:39'),(78,'HC003','Pain Reliever Tablets',NULL,119850.00,60,16,'2025-04-14 02:53:39'),(79,'HC004','Vitamin C Supplements',NULL,194850.00,45,16,'2025-04-14 02:53:39'),(80,'HC005','Hand Sanitizer',NULL,74850.00,98,16,'2025-04-14 02:53:39'),(81,'SH001','Men\'s Casual Shoes',NULL,899850.00,20,17,'2025-04-14 02:53:39'),(82,'SH002','Women\'s Sneakers',NULL,974850.00,25,17,'2025-04-14 02:53:39'),(83,'SH003','Kids School Shoes',NULL,599850.00,30,17,'2025-04-14 02:53:39'),(84,'SH004','Hiking Boots',NULL,1349850.00,15,17,'2025-04-14 02:53:39'),(85,'SH005','Slippers',NULL,299850.00,40,17,'2025-04-14 02:53:39'),(86,'JW001','Silver Necklace',NULL,1499850.00,10,18,'2025-04-14 02:53:39'),(87,'JW002','Gold Earrings',NULL,2249850.00,8,18,'2025-04-14 02:53:39'),(88,'JW003','Diamond Ring',NULL,7499850.00,5,18,'2025-04-14 02:53:39'),(89,'JW004','Bracelet',NULL,1199850.00,15,18,'2025-04-14 02:53:39'),(90,'JW005','Wristwatch',NULL,1949850.00,12,18,'2025-04-14 02:53:39'),(91,'PS001','Dog Food (15lb)',NULL,449850.00,25,19,'2025-04-14 02:53:39'),(92,'PS002','Cat Litter (20lb)',NULL,224850.00,30,19,'2025-04-14 02:53:39'),(93,'PS003','Pet Bed',NULL,599850.00,20,19,'2025-04-14 02:53:39'),(94,'PS004','Pet Toys Set',NULL,299850.00,35,19,'2025-04-14 02:53:39'),(95,'PS005','Pet Grooming Brush',NULL,149850.00,40,19,'2025-04-14 02:53:39'),(96,'MI001','Acoustic Guitar',NULL,2999850.00,9,20,'2025-04-14 02:53:39'),(97,'MI002','Digital Keyboard',NULL,4499850.00,8,20,'2025-04-14 02:53:39'),(98,'MI003','Drum Set',NULL,5999850.00,5,20,'2025-04-14 02:53:39'),(99,'MI004','Violin',NULL,3749850.00,7,20,'2025-04-14 02:53:39'),(100,'MI005','Harmonica',NULL,299850.00,30,20,'2025-04-14 02:53:39');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_items`
--

DROP TABLE IF EXISTS `sale_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sale_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sale_id` (`sale_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `sale_items_ibfk_1` FOREIGN KEY (`sale_id`) REFERENCES `sales` (`id`),
  CONSTRAINT `sale_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_items`
--

LOCK TABLES `sale_items` WRITE;
/*!40000 ALTER TABLE `sale_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `sale_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `id` int NOT NULL AUTO_INCREMENT,
  `invoice_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `customer_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `paid_amount` decimal(10,2) NOT NULL,
  `change_amount` decimal(10,2) NOT NULL,
  `sale_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `invoice_number` (`invoice_number`),
  KEY `customer_id` (`customer_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_items`
--

DROP TABLE IF EXISTS `transaction_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `transaction_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `transaction_id` (`transaction_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `transaction_items_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`id`),
  CONSTRAINT `transaction_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_items`
--

LOCK TABLES `transaction_items` WRITE;
/*!40000 ALTER TABLE `transaction_items` DISABLE KEYS */;
INSERT INTO `transaction_items` VALUES (1,1,71,1,374850),(2,1,10,1,134850),(3,1,16,1,1349850),(4,1,96,1,2999850),(5,2,80,1,74850),(6,2,54,1,224850),(7,2,8,1,29850),(8,2,61,1,134850),(9,3,55,1,299850),(10,3,34,1,1199850),(11,3,80,1,74850),(12,3,42,3,149850);
/*!40000 ALTER TABLE `transaction_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `transaction_date` timestamp NOT NULL,
  `total_amount` double NOT NULL,
  `customer_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,'INV-20250414-095526','2025-04-14 02:53:48',4859400,'Fajar'),(2,'INV-20250414-095546','2025-04-14 02:55:29',464400,'Rizky'),(3,'INV-20250414-185205','2025-04-14 11:51:35',2024100,'Ridho');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `full_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin123','Administrator','admin','2025-04-14 02:53:37');
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

-- Dump completed on 2025-04-14 18:57:31
