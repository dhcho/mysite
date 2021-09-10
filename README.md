mysite Implementation(로그인, 회원가입, 게시판, 방명록, 갤러리, 관리자 페이지)

   | --- mysite02: Model2 MVC(Servlet, JSP)

   | --- mysite03: Spring MVC(xml config)

   | --- mysite04: Spring MVC(xml config -> java config) - applicationContext, spring-servlet.xml

   | --- mysite05: Spring MVC(xml config -> java config) - web.xml

   | --- mysite06: Spring Framework -> Spring Boot


* DB Schema
```
-- webdb.author definition

CREATE TABLE `author` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- webdb.emaillist definition

CREATE TABLE `emaillist` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(200) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


-- webdb.gallery definition

CREATE TABLE `gallery` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(200) NOT NULL,
  `url` varchar(200) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- webdb.guestbook definition

CREATE TABLE `guestbook` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `message` text NOT NULL,
  `reg_date` date NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;


-- webdb.pet definition

CREATE TABLE `pet` (
  `name` varchar(20) DEFAULT NULL,
  `owner` varchar(20) DEFAULT NULL,
  `species` varchar(20) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `death` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- webdb.site definition

CREATE TABLE `site` (
  `title` varchar(50) DEFAULT NULL,
  `welcome` varchar(200) DEFAULT NULL,
  `profile` varchar(200) DEFAULT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- webdb.`user` definition

CREATE TABLE `user` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(200) NOT NULL,
  `password` varchar(45) NOT NULL,
  `gender` enum('female','male') NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- webdb.board definition

CREATE TABLE `board` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `contents` text NOT NULL,
  `reg_date` datetime NOT NULL,
  `hit` int(11) NOT NULL,
  `group_no` int(11) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  `depth` int(11) DEFAULT NULL,
  `user_no` int(11) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `fk_board_user1` (`user_no`),
  CONSTRAINT `fk_board_user1` FOREIGN KEY (`user_no`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


-- webdb.book definition

CREATE TABLE `book` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `status` enum('대여중','대여가능') NOT NULL DEFAULT '대여가능',
  `author_no` int(11) NOT NULL,
  PRIMARY KEY (`no`),
  KEY `fk_book_author` (`author_no`),
  CONSTRAINT `fk_book_author` FOREIGN KEY (`author_no`) REFERENCES `author` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
