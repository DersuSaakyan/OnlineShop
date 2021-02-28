/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.20 : Database - itnext
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`itnext` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `itnext`;

/*Table structure for table `cart` */

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `quantity` int DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `cart` */

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `category` */

insert  into `category`(`id`,`name`) values 
(1,'Samsung'),
(2,'Iphone'),
(3,'Nokia'),
(4,'Honor'),
(5,'Huawei'),
(6,'ASUS'),
(7,'Lenovo'),
(8,'Sony'),
(9,'Xiaomi'),
(10,'LG'),
(11,'Motorola'),
(12,'Meizu');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `feedback` */

insert  into `feedback`(`id`,`user_id`,`product_id`,`comment`,`date`) values 
(49,22,112,'ddd','20-10-2020 11:26:42'),
(50,18,112,'aaa','20-10-2020 11:26:47'),
(51,22,112,'s','20-10-2020 11:26:53');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  `pr_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `orders` */

insert  into `orders`(`id`,`product_id`,`user_id`,`date`,`total`,`quantity`,`pic_url`,`pr_title`) values 
(16,61,15,'07-10-2020 12:34:01',40,1,'1600769045511_img14.jpg','Nokia'),
(17,62,15,'07-10-2020 12:34:01',80,1,'1600769073519_img13.jpg','Asus'),
(18,65,15,'07-10-2020 16:50:32',170000,1,'1600969879741_img9.jpg','Honor Prime'),
(19,65,15,'07-10-2020 16:53:53',340000,2,'1600969879741_img9.jpg','Honor Prime'),
(20,65,15,'07-10-2020 16:56:39',340000,2,'1600969879741_img9.jpg','Honor Prime'),
(21,58,18,'08-10-2020 13:07:01',110,2,'1600768861385_img1.jpg','Asus'),
(22,59,18,'08-10-2020 13:07:01',500,1,'1600768892253_img7.jpg','Iphone Xs'),
(23,73,19,'13-10-2020 18:05:18',50,1,'1602479521694_img14.jpg','Nokia'),
(24,68,19,'13-10-2020 18:05:18',130,1,'1602162197044_img8.jpg','Honor 8c'),
(25,68,19,'13-10-2020 18:23:32',130,1,'1602162197044_img8.jpg','Honor 8c'),
(26,73,19,'13-10-2020 18:23:32',50,1,'1602479521694_img14.jpg','Nokia'),
(27,69,19,'13-10-2020 18:27:37',1200,2,'1602163888109_img3.jpg','Iphone 8'),
(36,70,2,'14-10-2020 21:26:43',1000,2,'1602345487940_img9.jpg','ASUS '),
(37,71,2,'14-10-2020 21:26:43',1000,1,'1602345526989_img6.jpg','Iphone Xs'),
(38,70,2,'15-10-2020 19:30:22',500,1,'1602345487940_img9.jpg','ASUS '),
(39,72,2,'15-10-2020 19:30:22',200,1,'1602345834995_1200px-LG_V20_Titan.jpg','LG'),
(40,70,2,'15-10-2020 19:37:22',500,1,'1602345487940_img9.jpg','ASUS '),
(41,70,2,'15-10-2020 19:43:04',500,1,'1602345487940_img9.jpg','ASUS '),
(42,110,22,'20-10-2020 11:20:44',1300,1,'1602836543657_img1.jpg','Samsung Galaxy S11'),
(43,107,22,'20-10-2020 11:20:44',910,2,'1602836071269_img9.jpg','Lenovo A6'),
(44,109,22,'20-10-2020 11:20:44',900,3,'1602836466093_x.jpg','Xiaomi');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `date` varchar(255) DEFAULT NULL,
  `count` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `active` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `product` */

insert  into `product`(`id`,`title`,`description`,`date`,`count`,`price`,`user_id`,`category_id`,`active`) values 
(91,'Samasun galaxy S10','Vivamus magna justo, lacinia eget consectetur sed, convallis at tellus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula. Vivamus suscipit tortor eget felis porttitor volutpat.\r\n\r\nCurabitur aliquet quam id dui posuere blandit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec rutrum congue leo eget malesuada.','16-10-2020 11:07:50',5,900,19,1,1),
(92,'Nokia Fonarik','Vivamus magna justo, lacinia eget consectetur sed, convallis at tellus. Curabitur aliquet quam id dui posuere blandit. Vivamus magna justo, lacinia eget consectetur sed, convallis at tellus.\r\n\r\nCras ultricies ligula sed magna dictum porta. Nulla porttitor accumsan tincidunt. Proin eget tortor risus.','16-10-2020 11:25:50',4,100,19,3,1),
(93,'Lenovo K10','Nulla porttitor accumsan tincidunt. Nulla quis lorem ut libero malesuada feugiat. Donec sollicitudin molestie malesuada.\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ultricies ligula sed magna dictum porta. Vivamus magna justo, lacinia eget consectetur sed, convallis at tellus.','16-10-2020 11:28:11',400,3,19,7,1),
(94,'Iphone Xs','Curabitur arcu erat, accumsan id imperdiet et, porttitor at sem. Vivamus magna justo, lacinia eget consectetur sed, convallis at tellus. Curabitur arcu erat, accumsan id imperdiet et, porttitor at sem.\r\n\r\nCurabitur arcu erat, accumsan id imperdiet et, porttitor at sem. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula. Vivamus suscipit tortor eget felis porttitor volutpat.','16-10-2020 11:28:43',6,1000,19,2,1),
(95,'Iphone 8','Quisque velit nisi, pretium ut lacinia in, elementum id enim. Donec rutrum congue leo eget malesuada. Cras ultricies ligula sed magna dictum porta.\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla porttitor accumsan tincidunt. Donec sollicitudin molestie malesuada.','16-10-2020 11:29:11',4,800,19,2,1),
(96,'Xiaomi Redmi','Quisque velit nisi, pretium ut lacinia in, elementum id enim. Nulla porttitor accumsan tincidunt. Cras ultricies ligula sed magna dictum porta.\r\n\r\nCurabitur aliquet quam id dui posuere blandit. Curabitur aliquet quam id dui posuere blandit. Vivamus magna justo, lacinia eget consectetur sed, convallis at tellus.','16-10-2020 11:29:45',7,600,19,9,1),
(97,'LG','Nulla quis lorem ut libero malesuada feugiat. Pellentesque in ipsum id orci porta dapibus. Vivamus suscipit tortor eget felis porttitor volutpat.\r\n\r\nVivamus suscipit tortor eget felis porttitor volutpat. Curabitur arcu erat, accumsan id imperdiet et, porttitor at sem. Vestibulum ac diam sit amet quam vehicula elementum sed sit amet dui.','16-10-2020 11:31:45',6,500.9,2,10,1),
(98,'Samasung galaxy Note 10','Nulla porttitor accumsan tincidunt. Cras ultricies ligula sed magna dictum porta. Vestibulum ac diam sit amet quam vehicula elementum sed sit amet dui.\r\n\r\nCras ultricies ligula sed magna dictum porta. Vivamus magna justo, lacinia eget consectetur sed, convallis at tellus. Quisque velit nisi, pretium ut lacinia in, elementum id enim.','16-10-2020 11:34:52',4,1500,2,1,1),
(100,'Honor 8c','Praesent sapien massa, convallis a pellentesque nec, egestas non nisi. Curabitur arcu erat, accumsan id imperdiet et, porttitor at sem. Quisque velit nisi, pretium ut lacinia in, elementum id enim.\r\n\r\nDonec rutrum congue leo eget malesuada. Curabitur arcu erat, accumsan id imperdiet et, porttitor at sem. Curabitur aliquet quam id dui posuere blandit.','16-10-2020 11:37:38',3,300,2,4,1),
(101,'ASUS','Curabitur arcu erat, accumsan id imperdiet et, porttitor at sem. Curabitur aliquet quam id dui posuere blandit. Nulla quis lorem ut libero malesuada feugiat.\r\n\r\nDonec rutrum congue leo eget malesuada. Curabitur aliquet quam id dui posuere blandit. Praesent sapien massa, convallis a pellentesque nec, egestas non nisi.','16-10-2020 11:43:23',4,400,2,6,1),
(102,'Iphone ProMax','Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula. Curabitur aliquet quam id dui posuere blandit. Vivamus suscipit tortor eget felis porttitor volutpat.\r\n\r\nDonec sollicitudin molestie malesuada. Vestibulum ac diam sit amet quam vehicula elementum sed sit amet dui. Proin eget tortor risus.','16-10-2020 11:47:15',7,2000,19,2,1),
(103,'Nokia','Sed porttitor lectus nibh. Vivamus suscipit tortor eget felis porttitor volutpat. Sed porttitor lectus nibh.\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla porttitor accumsan tincidunt. Praesent sapien massa, convallis a pellentesque nec, egestas non nisi.','16-10-2020 11:48:46',3,200,19,7,1),
(105,'Sony Xperia','Curabitur non nulla sit amet nisl tempus convallis quis ac lectus. Praesent sapien massa, convallis a pellentesque nec, egestas non nisi. Pellentesque in ipsum id orci porta dapibus.\r\n\r\nVestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula. Curabitur arcu erat, accumsan id imperdiet et, porttitor at sem. Sed porttitor lectus nibh.','16-10-2020 11:54:28',3,570,2,8,1),
(106,'Honor Prime','Vestibulum ac diam sit amet quam vehicula elementum sed sit amet dui. Curabitur non nulla sit amet nisl tempus convallis quis ac lectus. Quisque velit nisi, pretium ut lacinia in, elementum id enim.\r\n\r\nNulla quis lorem ut libero malesuada feugiat. Vivamus suscipit tortor eget felis porttitor volutpat. Lorem ipsum dolor sit amet, consectetur adipiscing elit.','16-10-2020 12:08:11',4,300,2,4,1),
(107,'Lenovo A6','Vivamus magna justo, lacinia eget consectetur sed, convallis at tellus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula. Sed porttitor lectus nibh.\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula.','16-10-2020 12:14:31',2,455,2,7,1),
(108,'Lg&100','Curabitur non nulla sit amet nisl tempus convallis quis ac lectus. Vivamus suscipit tortor eget felis porttitor volutpat. Praesent sapien massa, convallis a pellentesque nec, egestas non nisi.\r\n\r\nSed porttitor lectus nibh. Nulla porttitor accumsan tincidunt. Lorem ipsum dolor sit amet, consectetur adipiscing elit.','16-10-2020 12:15:44',4,233,2,10,1),
(109,'Xiaomi','Vestibulum ac diam sit amet quam vehicula elementum sed sit amet dui. Sed porttitor lectus nibh. Nulla porttitor accumsan tincidunt.\r\n\r\nCurabitur arcu erat, accumsan id imperdiet et, porttitor at sem. Donec rutrum congue leo eget malesuada. Cras ultricies ligula sed magna dictum porta.','16-10-2020 12:21:06',3,300,19,9,1),
(110,'Samsung Galaxy S11','Curabitur aliquet quam id dui posuere blandit. Vivamus suscipit tortor eget felis porttitor volutpat. Quisque velit nisi, pretium ut lacinia in, elementum id enim.\r\n\r\nVestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula. Curabitur non nulla sit amet nisl tempus convallis quis ac lectus. Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.','16-10-2020 12:22:23',2,1300,19,1,1),
(111,'Motorola','Donec rutrum congue leo eget malesuada. Donec rutrum congue leo eget malesuada. Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.\r\n\r\nPraesent sapien massa, convallis a pellentesque nec, egestas non nisi. Curabitur aliquet quam id dui posuere blandit. Curabitur arcu erat, accumsan id imperdiet et, porttitor at sem.','16-10-2020 12:25:00',4,600,19,11,0),
(112,'Iphone 5s','Donec sollicitudin molestie malesuada. Pellentesque in ipsum id orci porta dapibus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.\r\n\r\nMauris blandit aliquet elit, eget tincidunt nibh pulvinar a. Donec sollicitudin molestie malesuada. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula.','20-10-2020 11:15:37',4,500,22,2,1),
(113,'Samasun galaxy S4','Quisque velit nisi, pretium ut lacinia in, elementum id enim. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula. Quisque velit nisi, pretium ut lacinia in, elementum id enim.\r\n\r\nVivamus magna justo, lacinia eget consectetur sed, convallis at tellus. Quisque velit nisi, pretium ut lacinia in, elementum id enim. Donec rutrum congue leo eget malesuada.','20-10-2020 11:16:46',4,400,22,1,-1);

/*Table structure for table `product_img` */

DROP TABLE IF EXISTS `product_img`;

CREATE TABLE `product_img` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_img_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `product_img` */

insert  into `product_img`(`id`,`product_id`,`pic_url`) values 
(134,91,'1602832069994_Samsung_Galaxy_S10_Plus_Black.jpg'),
(135,92,'1602833150906_nokia_fonarik.jpg'),
(136,93,'1602833291603_lenovo K10.jpg'),
(137,94,'1602833323228_img6.jpg'),
(138,94,'1602833323233_img7.jpg'),
(139,95,'1602833351097_img3.jpg'),
(140,96,'1602833385878_Redmi.jpg'),
(141,97,'1602833505550_1200px-LG_V20_Titan.jpg'),
(142,98,'1602833692583_samsung-galaxy-note10.jpg'),
(144,100,'1602833858697_honor 8x.jpg'),
(145,101,'1602834203841_img13.jpg'),
(146,101,'1602834203845_img16.jpg'),
(147,102,'1602834435159_img5.jpg'),
(148,102,'1602834435164_img6.jpg'),
(149,103,'1602834526379_img14.jpg'),
(151,105,'1602834868186_img11.jpg'),
(152,105,'1602834868190_Sony-Xperia.jpg'),
(153,106,'1602835691247_img8.jpg'),
(154,107,'1602836071269_img9.jpg'),
(155,107,'1602836071277_img10.jpg'),
(156,107,'1602836071284_lenovo K10.jpg'),
(157,108,'1602836144103_lg.jpg'),
(158,109,'1602836466093_x.jpg'),
(159,110,'1602836543657_img1.jpg'),
(160,111,'1602836700952_motorola.jpg'),
(161,112,'1603178137741_1200px-LG_V20_Titan.jpg'),
(162,112,'1603178137745_honor 8x.jpg'),
(163,112,'1603178137748_honor-note10.jpg'),
(164,113,'1603178206723_img16.jpg');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(31) DEFAULT NULL,
  `type` enum('ADMIN','USER') DEFAULT 'USER',
  `verify` tinyint(1) DEFAULT '0',
  `token` varchar(255) DEFAULT NULL,
  `active` int DEFAULT '0',
  `pic_url` varchar(255) DEFAULT 'default.png',
  `gender` enum('MALE','FEMALE') DEFAULT NULL,
  `code` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`surname`,`age`,`email`,`password`,`phone`,`type`,`verify`,`token`,`active`,`pic_url`,`gender`,`code`) values 
(2,'Der','Saakyan',20,'der.sahakyan@gmail.com','$2a$10$wYSMXdG3R2LpUq.Wv1WB5e085SIiY2iJgogGZeXKMJ5aH6kbwB.Z2','+37491252925','USER',1,NULL,0,'1600442294815_Me.jpg','MALE',0),
(18,'Anna','Anyan',20,'an@mail.ru','$2a$10$F2HsDtEjlKgcSHprtfSW8.RrjuZRtNWjnyGot.TCFk8Oh4qaajVxa','+37491345689','ADMIN',1,NULL,0,'default.png','MALE',0),
(19,'Gevork','Iricyan',19,'gev@mail.ru','$2a$10$MvAiC8BIT48VDPVjwb/1AevIbTtGbe6qgMaU.xEjAlj5OjoGqPMki','+37456345655','USER',1,NULL,0,'default.png','MALE',0),
(22,'Anul','Anyan',14,'derensahakyan7@gmail.com','$2a$10$O5oQBlYiqayWF53rVpmDx.Wz3RzX/HbxGdvhUGfQgBPqS2n3Ep1EG','+37492132345','USER',1,NULL,0,'1603177942200_honor 8x.jpg','MALE',0);

/*Table structure for table `wishlist` */

DROP TABLE IF EXISTS `wishlist`;

CREATE TABLE `wishlist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `wishlist` */

/* Procedure structure for procedure `addLike` */

/*!50003 DROP PROCEDURE IF EXISTS  `addLike` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `addLike`(a INT(11) , s INT(11))
BEGIN
DECLARE X INT(11);
SET X=(SELECT COUNT(*) FROM `wishlist` WHERE user_id=a AND product_id=s);
IF X=0 THEN	
	INSERT INTO `wishlist`(user_id,product_id) VALUES (a,s);
ELSE
	DELETE FROM `wishlist` WHERE user_id = a AND product_id=s;
END IF;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
