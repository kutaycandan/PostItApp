SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;



DROP TABLE IF EXISTS `Member`;
CREATE TABLE `Member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `isDeleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `Team`;
CREATE TABLE `Team` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `teamname` varchar(255) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `isDeleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `TeamMember`;
CREATE TABLE `TeamMember` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `teamID` int(10) unsigned DEFAULT NULL,
  `memberID` int(10) unsigned DEFAULT NULL,
  `isMaster` tinyint(1) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `isDeleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `PostIt`;
CREATE TABLE `PostIt` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `postname` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `teamID` int(10) unsigned DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `isDeleted` tinyint(1) DEFAULT NULL,
  `isUpdatedNow` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
