/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : babytun

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 21/05/2020 15:57:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '产品分类',
  `category_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级分类',
  `category_level` int(11) NOT NULL COMMENT '级别',
  `category_order` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `t_evaluate`;
CREATE TABLE `t_evaluate`  (
  `evaluate_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `stars` int(11) NULL DEFAULT 5 COMMENT '5',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `goods_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '0',
  PRIMARY KEY (`evaluate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37261 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods`  (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品编号',
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `sub_title` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子标题',
  `original_cost` float NOT NULL COMMENT '原价',
  `current_price` float NOT NULL COMMENT '折后价',
  `discount` float NOT NULL COMMENT '折扣(0~1)',
  `is_free_delivery` int(11) NOT NULL COMMENT '是否包邮',
  `category_id` int(11) NOT NULL DEFAULT 0,
  `last_update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2674 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_goods_cover
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_cover`;
CREATE TABLE `t_goods_cover`  (
  `gc_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `gc_pic_url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gc_thumb_url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gc_order` int(11) NOT NULL,
  PRIMARY KEY (`gc_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12993 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_goods_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_detail`;
CREATE TABLE `t_goods_detail`  (
  `gd_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `gd_pic_url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gd_order` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`gd_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33518 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_goods_param
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_param`;
CREATE TABLE `t_goods_param`  (
  `gp_id` int(11) NOT NULL AUTO_INCREMENT,
  `gp_param_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gp_param_value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `goods_id` int(11) NOT NULL,
  `gp_order` int(11) NOT NULL,
  PRIMARY KEY (`gp_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8387 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `order_status` int(1) NULL DEFAULT NULL,
  `userid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `recv_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `recv_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `recv_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `postage` float(255, 0) NULL DEFAULT NULL,
  `amout` float(255, 0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_promotion_seckill
-- ----------------------------
DROP TABLE IF EXISTS `t_promotion_seckill`;
CREATE TABLE `t_promotion_seckill`  (
  `ps_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `ps_count` int(255) NOT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `status` int(255) NULL DEFAULT NULL COMMENT '0-未开始 1-进行中  2-已结束',
  `current_price` float NOT NULL DEFAULT 0,
  PRIMARY KEY (`ps_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户信息ID',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `usable_balance` decimal(10, 2) NOT NULL COMMENT '可用余额单位：元',
  `frozen_balance` decimal(10, 2) NOT NULL COMMENT '冻结余额单位：元',
  `sum_income_amount` decimal(10, 2) NOT NULL COMMENT '累计收入总额单位：元',
  `sum_income_count` int(11) NOT NULL DEFAULT 0 COMMENT '累计收入次数',
  `sum_pay_amount` decimal(10, 2) NOT NULL COMMENT '累计支出总额单位：元',
  `sum_pay_count` int(11) NOT NULL DEFAULT 0 COMMENT '累计支出次数',
  `sum_withdraw_amount` decimal(10, 2) NOT NULL COMMENT '累计提现总额单位：元',
  `sum_withdraw_count` int(11) NOT NULL DEFAULT 0 COMMENT '累计提现次数',
  `sum_recharge_amount` decimal(10, 2) NOT NULL COMMENT '累计充值总额单位：元',
  `sum_recharge_count` int(11) NOT NULL DEFAULT 0 COMMENT '累计充值次数',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建日期',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
  `del_flg` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态 0:未删除；1:已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `userId`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 162 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户信息表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
