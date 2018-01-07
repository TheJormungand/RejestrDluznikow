/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : rejestrdlugow

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-01-06 19:50:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `debt`
-- ----------------------------
DROP TABLE IF EXISTS `debt`;
CREATE TABLE `debt` (
  `IDdebt` int(11) NOT NULL AUTO_INCREMENT,
  `Owner` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDdebt`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of debt
-- ----------------------------

-- ----------------------------
-- Table structure for `group`
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `IDgroup` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) DEFAULT NULL,
  `Admin` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDgroup`),
  KEY `AdminGroup_FK` (`Admin`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES ('1', 'kamilg', '1');

-- ----------------------------
-- Table structure for `paiddebt`
-- ----------------------------
DROP TABLE IF EXISTS `paiddebt`;
CREATE TABLE `paiddebt` (
  `IDpaiddept` int(11) NOT NULL AUTO_INCREMENT,
  `IDuserdept` int(11) DEFAULT NULL,
  `Amount` int(11) DEFAULT NULL,
  `Accepted` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDpaiddept`),
  KEY `IDuserdept` (`IDuserdept`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of paiddebt
-- ----------------------------

-- ----------------------------
-- Table structure for `state`
-- ----------------------------
DROP TABLE IF EXISTS `state`;
CREATE TABLE `state` (
  `IDstate` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IDstate`),
  KEY `Name` (`Name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of state
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `IDuser` int(11) NOT NULL AUTO_INCREMENT,
  `Login` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Authorised` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDuser`),
  KEY `AuthorisedUser_FK` (`Authorised`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'kamil', 'kamil', 'k@k.com', '1');

-- ----------------------------
-- Table structure for `userdebt`
-- ----------------------------
DROP TABLE IF EXISTS `userdebt`;
CREATE TABLE `userdebt` (
  `IDuserdebt` int(11) NOT NULL AUTO_INCREMENT,
  `IDdebt` int(11) DEFAULT NULL,
  `IDuser` int(11) DEFAULT NULL,
  `Amount` int(11) DEFAULT NULL,
  `Accepted` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDuserdebt`),
  KEY `IDdebt` (`IDdebt`),
  KEY `IDuser` (`IDuser`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userdebt
-- ----------------------------

-- ----------------------------
-- Table structure for `usergroup`
-- ----------------------------
DROP TABLE IF EXISTS `usergroup`;
CREATE TABLE `usergroup` (
  `IDusergroup` int(11) NOT NULL AUTO_INCREMENT,
  `IDuser` int(11) DEFAULT NULL,
  `IDgroup` int(11) DEFAULT NULL,
  `Accepted` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDusergroup`),
  KEY `IDgroup` (`IDgroup`),
  KEY `IDuser` (`IDuser`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usergroup
-- ----------------------------
