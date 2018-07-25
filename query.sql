SET MODE MYSQL;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(70) NOT NULL,
  `password` varchar(64) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(1000) NOT NULL,
  `test_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_question_1_idx` (`test_id`),
  CONSTRAINT `fk_question_1` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `option`;
CREATE TABLE `option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `option_text` varchar(255) NOT NULL,
  `question_id` int(11) NOT NULL,
  `correct` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id_idx` (`question_id`),
  CONSTRAINT `question_id` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `test_user`;
CREATE TABLE `test_user` (
  `user_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `mark` int(11) NOT NULL,
  PRIMARY KEY (`test_id`,`user_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `test_id` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_answer`;
CREATE TABLE `user_answer` (
  `question_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `option_id` int(11) NOT NULL,
  PRIMARY KEY (`question_id`,`user_id`,`option_id`),
  KEY `u_id_idx` (`user_id`),
  KEY `o_id_idx` (`option_id`),
  CONSTRAINT `o_id` FOREIGN KEY (`option_id`) REFERENCES `option` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `q_id` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `u_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- INSERT INTO `role` VALUES (1,'admin'),(3,'student');
-- INSERT INTO `user` VALUES (1,'Виталий Гайнулин','uaanvi@gmail.com','529450F530E3725F80973896FF1E198F19562FBA00A2332571C5B4C78746012F',1),(2,'Alona Kaptur','alona@gmail.com','newpassword',3),(3,'Артем Бойко','artem@i.ua','doafwfwfeiwifebybwufbu2',3),(4,'Петр Петров','petro@mail.ru','fewgegergeregrger',3);
-- INSERT INTO `test` VALUES (1,'HTML - Основы'),(2,'Spring Core');
-- INSERT INTO `question` VALUES (1,'Что определяет параметр colspan тега <td>?',1),(2,'Как объединить по горизонтали несколько ячеек таблицы?',1),(3,'Для чего используется тег <TITLE>',1),(4,'Какие атрибуты допустимы в TEXTAREA?',1);
-- INSERT INTO `option` VALUES (1,'задает выравнивание внутри ячейки',1,'TRUE'),(2,'объединяет ячейки по горизонтали',1,'FALSE'),(3,'объединяет ячейки по вертикали',1,'FALSE'),(4,'задает отступ для колонок',1,'FALSE'),(5,'определяет количество колонок в строке',1,'FALSE'),(6,'С помощью атрибута ROWSPAN',2,'FALSE'),(7,'С помощью атрибута COLSPAN',2,'TRUE'),(8,'С помощью атрибута CELLSPACING',2,'FALSE'),(9,'С помощью атрибута HALIGN',2,'FALSE'),(10,'Определяет заголовок в тексте.',3,'FALSE'),(11,'Определяет заголовок таблицы.',3,'FALSE'),(12,'Определяет красную строку в тексте.',3,'FALSE'),(13,'Определяет заголовок документа.',3,'TRUE'),(14,'name',4,'TRUE'),(15,'cols',4,'FALSE'),(16,'title',4,'FALSE'),(17,'rows',4,'FALSE'),(18,'value',4,'TRUE');
-- INSERT INTO `test_user` VALUES (1,1,2);
-- INSERT INTO `user_answer` VALUES (1,1,1),(2,1,7),(3,1,11),(4,1,14),(4,1,15);
