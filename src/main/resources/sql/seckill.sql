/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2018-10-24 23:44:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `name` varchar(255) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` datetime NOT NULL COMMENT '秒杀开启时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES ('1000', '1000元秒杀iPhone7', '97', '2018-10-22 00:00:00', '2018-10-24 01:00:00', '2018-10-23 23:49:53');
INSERT INTO `seckill` VALUES ('1001', '300元秒杀华为10', '200', '2018-10-22 00:00:00', '2018-10-22 01:00:00', '2018-10-22 23:49:08');
INSERT INTO `seckill` VALUES ('1002', '500元秒杀荣耀9', '500', '2018-10-22 00:00:00', '2018-10-22 01:00:00', '2018-10-22 23:49:11');

-- ----------------------------
-- Table structure for success_seckill
-- ----------------------------
DROP TABLE IF EXISTS `success_seckill`;
CREATE TABLE `success_seckill` (
  `seckill_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_phone` bigint(11) NOT NULL,
  `state` tinyint(2) NOT NULL DEFAULT '-1' COMMENT '-1:无效 0:成功 1:已付款 2:发货',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`,`user_phone`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of success_seckill
-- ----------------------------
INSERT INTO `success_seckill` VALUES ('1000', '13902121234', '-1', '2018-10-23 23:58:27');
INSERT INTO `success_seckill` VALUES ('1001', '13902121234', '0', '2018-10-24 00:11:56');
