/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : drools

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-08-13 09:07:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `drools_rule`
-- ----------------------------
DROP TABLE IF EXISTS `drools_rule`;
CREATE TABLE `drools_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL,
  `rule` mediumtext CHARACTER SET utf8mb4,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `visible` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of drools_rule
-- ----------------------------
INSERT INTO `drools_rule` VALUES ('3', 'rule_005', 'package com.xu.drools;\r\nimport com.xu.drools.bean.Person;\r\nrule \"2\"\r\n	when\r\n        $p : Person(age < 30);\r\n    then\r\n		System.out.println(\"hello, young xu2!\");\r\n		$p.setDesc(\"young \"+$p.getName());\r\n		retract($p)\r\nend', '2017-01-17 10:43:14', '2017-08-29 20:15:37', '1');

-- ----------------------------
-- Table structure for `rule`
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `rule` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rule
-- ----------------------------
INSERT INTO `rule` VALUES ('1', 'point', 'package com.liuzhe.drools\r\n\r\nimport com.liuzhe.drools.entity.Point;\r\n\r\nrule birthdayPoint\r\n    // 过生日，则加10分，并且将当月交易比数翻倍后再计算积分\r\n    salience 100\r\n    lock-on-active true\r\n    when\r\n        $point : Point(birthDay == true)\r\n    then\r\n        $point.setPoint($point.getPoint()+10);\r\n        $point.setBuyNums($point.getBuyNums()*2);\r\n        $point.setBuyMoney($point.getBuyMoney()*2);\r\n        $point.setBillThisMonth($point.getBillThisMonth()*2);\r\n//        $point.recordPointLog($point.getUserName(),\"birthdayPoint\");\r\nend\r\n\r\nrule billThisMonthPoint\r\n    // 2018-01-01 - 2018-12-31 信用卡还款3次以上，每满3笔赠送30分\r\n    salience 99\r\n    lock-on-active true\r\n//    date-effective \"01-01-2018\"\r\n//    date-expires \"31-12-2018\"\r\n    when\r\n        $point : Point(billThisMonth >= 3)\r\n    then\r\n        $point.setPoint($point.getPoint()+$point.getBillThisMonth()/3*30);\r\n//        $point.recordPointLog($point.getUserName(),\"billThisMonthPoint\");\r\nend\r\n\r\nrule buyMoneyPoint\r\n    // 当月购物总金额100以上，每100元赠送10分\r\n    salience 98\r\n    lock-on-active true\r\n    when\r\n        $point : Point(buyMoney >= 100)\r\n    then\r\n        $point.setPoint($point.getPoint()+ (int)$point.getBuyMoney()/100 * 10);\r\n//        $point.recordPointLog($point.getUserName(),\"buyMoneyPoint\");\r\nend\r\n\r\nrule buyNumsPoint\r\n    // 当月购物次数5次以上，每五次赠送50分\r\n    salience 97\r\n    lock-on-active true\r\n    when\r\n        $point : Point(buyNums >= 5)\r\n    then\r\n        $point.setPoint($point.getPoint()+$point.getBuyNums()/5 * 50);\r\n//        $point.recordPointLog($point.getUserName(),\"buyNumsPoint\");\r\nend\r\n\r\nrule allFitPoint\r\n    // 特别的，如果全部满足了要求，则额外奖励100分\r\n    salience 96\r\n    lock-on-active true\r\n    when\r\n        $point:Point(buyNums >= 5 && billThisMonth >= 3 && buyMoney >= 100)\r\n    then\r\n        $point.setPoint($point.getPoint()+ 100);\r\n//        $point.recordPointLog($point.getUserName(),\"allFitPoint\");\r\nend\r\n\r\nrule subBackNumsPoint\r\n    // 发生退货，扣减10分\r\n    salience 10\r\n    lock-on-active true\r\n    when\r\n        $point : Point(backNums >= 1)\r\n    then\r\n        $point.setPoint($point.getPoint()-10);\r\n//        $point.recordPointLog($point.getUserName(),\"subBackNumsPoint\");\r\nend\r\n\r\nrule subBackMondyPoint\r\n    // 退货金额大于100，扣减100分\r\n    salience 9\r\n    lock-on-active true\r\n    when\r\n        $point : Point(backMondy >= 100)\r\n    then\r\n        $point.setPoint($point.getPoint()-10);\r\n//        $point.recordPointLog($point.getUserName(),\"subBackMondyPoint\");\r\nend');
INSERT INTO `rule` VALUES ('2', 'rule_name', 'this is rule , hhhhhhh');
