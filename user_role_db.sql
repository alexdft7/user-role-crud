DROP DATABASE IF EXISTS `user_tracker`;
CREATE DATABASE `user_tracker`;
USE `user_tracker`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

LOCK TABLES `user` WRITE;

INSERT INTO `user` VALUES 
	(1,'Саша','alex','123321'),
	(2,'Вася','vas_ya','ВаСя'),
	(3,'Петя','PETYA','qwerty'),
	(4,'Маша','Mas','Пароль'),
	(5,'Ваня','Ivan','HelloWorld');

UNLOCK TABLES;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
`id` int NOT NULL AUTO_INCREMENT,
`name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `role` WRITE;

INSERT INTO `role` VALUES 
	(1,'Пользователь'),
	(2,'Модератор'),
	(3,'Оператор'),
	(4,'Администратор'),
	(5,'Директор');

UNLOCK TABLES;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
`user_id` int,
`role_id` int,
CONSTRAINT fk_user FOREIGN KEY (user_id)
REFERENCES user_tracker.user(id),
CONSTRAINT fk_role FOREIGN KEY (role_id)
REFERENCES user_tracker.role(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `user_role` WRITE;

INSERT INTO `user_role` VALUES 
	(1,1),
	(1,2),
	(1,3),
	(2,5),
	(3,1);

UNLOCK TABLES;