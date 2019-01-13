/*
 Navicat Premium Data Transfer

 Source Server         : 王-阿里云
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 47.100.2.37:3306
 Source Schema         : shopping_mall

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 13/01/2019 15:36:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for adress
-- ----------------------------
DROP TABLE IF EXISTS `adress`;
CREATE TABLE `adress` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收货地址id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `name` varchar(11) NOT NULL DEFAULT '' COMMENT '姓名',
  `tel` varchar(11) NOT NULL DEFAULT '' COMMENT '电话',
  `post_code` varchar(10) NOT NULL DEFAULT '' COMMENT '邮编',
  `adress` varchar(100) NOT NULL DEFAULT '' COMMENT '详细地址',
  `province` varchar(8) NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(8) NOT NULL DEFAULT '' COMMENT '市',
  `region` varchar(8) NOT NULL DEFAULT '' COMMENT '区',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT ' 0已删除 1存在',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='收货地址表';

-- ----------------------------
-- Records of adress
-- ----------------------------
BEGIN;
INSERT INTO `adress` VALUES (5, 29, '姓名5', '55555555555', '102102102', '江苏省常州市新北区锦湖创新中心A座2楼', '江苏省', '常州市', '新北区', 1, '2019-01-03 14:43:36', '2019-01-05 09:47:12');
INSERT INTO `adress` VALUES (7, 31, '姓名3', '33333333333', '102102102', '江苏省常州市新北区锦湖创新中心A座2楼', '江苏省', '常州市', '新北区', 1, '2019-01-02 17:28:16', '2019-01-05 09:47:15');
INSERT INTO `adress` VALUES (8, 32, '姓名5', '55555555555', '102102102', '江苏省常州市新北区锦湖创新中心A座2楼', '江苏省', '常州市', '新北区', 1, '2019-01-03 14:43:36', '2019-01-05 09:47:17');
INSERT INTO `adress` VALUES (27, 29, '1', '41231', '4123', '4123124', '', '', '', 1, '2019-01-05 09:48:20', '2019-01-05 09:48:20');
INSERT INTO `adress` VALUES (28, 29, '124123', '4123', '4123', '4123124213', '', '', '', 1, '2019-01-05 15:25:51', '2019-01-05 15:25:51');
INSERT INTO `adress` VALUES (30, 33, '4123', '4213', '4123', '4123123', '', '', '', 1, '2019-01-11 11:29:53', '2019-01-11 11:29:53');
INSERT INTO `adress` VALUES (31, 33, '124', '4123', '4123', '41232', '', '', '', 1, '2019-01-11 12:56:59', '2019-01-11 12:56:59');
INSERT INTO `adress` VALUES (32, 34, 'admin', 'admin', '000000', '124123124', '', '', '', 1, '2019-01-11 13:43:01', '2019-01-11 13:43:01');
INSERT INTO `adress` VALUES (33, 34, 'admin2', 'admin2', '121212', '121212', '', '', '', 1, '2019-01-11 13:44:08', '2019-01-11 13:44:08');
COMMIT;

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '商品名',
  `title` varchar(15) NOT NULL DEFAULT '' COMMENT '商品标题',
  `detail` varchar(100) NOT NULL DEFAULT '' COMMENT '商品详情',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '商品图片(多张图,分割)',
  `inventory` int(10) NOT NULL DEFAULT '1' COMMENT '商品库存',
  `is_shelves` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否上架 1是0否',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT ' 0已删除 1存在',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of good
-- ----------------------------
BEGIN;
INSERT INTO `good` VALUES (1, 'iPhone X', '苹果手机：iphoneX', 'Apple iPhone X (A1865) 64GB 深空灰色 移动联通电信4G手机', 8888.00, 'https://pic.qqtn.com/up/2017-9/2017091314280414443.jpg', 100, 1, 0, '2019-01-02 20:43:04', '2019-01-05 16:00:16');
INSERT INTO `good` VALUES (2, 'iPhone 8', '苹果手机：iphone8', 'Apple iPhone 8 (A1863) 64GB 深空灰色 移动联通电信4G手机', 6666.00, 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547083843&di=bb79f7e51d167cf545d56314b83bb2c7&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fproduct%2F575351%2F201610%2F12%2F147625574992659322_800.jpg', 100, 1, 0, '2019-01-02 20:44:56', '2019-01-05 16:00:16');
INSERT INTO `good` VALUES (3, 'iPhone 7', '苹果手机：iphoneX', 'Apple iPhone X (A1865) 64GB 深空灰色 移动联通电信4G手机', 8888.00, 'https://pic.qqtn.com/up/2017-9/2017091314280414443.jpg', 100, 1, 0, '2019-01-02 20:43:04', '2019-01-05 16:00:17');
INSERT INTO `good` VALUES (4, 'iPhone 6', '苹果手机：iphoneX', 'Apple iPhone X (A1865) 64GB 深空灰色 移动联通电信4G手机', 8888.00, 'https://pic.qqtn.com/up/2017-9/2017091314280414443.jpg', 100, 1, 0, '2019-01-02 20:43:04', '2019-01-05 16:00:17');
INSERT INTO `good` VALUES (5, 'iPhone 5', '苹果手机：iphone8', 'Apple iPhone 8 (A1863) 64GB 深空灰色 移动联通电信4G手机', 6666.00, 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547083843&di=bb79f7e51d167cf545d56314b83bb2c7&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fproduct%2F575351%2F201610%2F12%2F147625574992659322_800.jpg', 100, 1, 0, '2019-01-02 20:44:56', '2019-01-05 16:00:18');
INSERT INTO `good` VALUES (6, 'iPhone 4', '苹果手机：iphoneX', 'Apple iPhone X (A1865) 64GB 深空灰色 移动联通电信4G手机', 8888.00, 'https://pic.qqtn.com/up/2017-9/2017091314280414443.jpg', 100, 1, 0, '2019-01-02 20:43:04', '2019-01-05 16:00:19');
COMMIT;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `order_id` varchar(32) NOT NULL COMMENT '订单号',
  `subject` varchar(256) DEFAULT '' COMMENT '订单名称',
  `body` varchar(128) DEFAULT '' COMMENT '订单描述',
  `money` float DEFAULT '0' COMMENT '付款金额',
  `seller_id` varchar(32) DEFAULT '' COMMENT '商户UID',
  `alipay_no` varchar(64) DEFAULT '' COMMENT '支付宝订单号',
  `status` varchar(32) DEFAULT '' COMMENT '订单状态（与官方统一）【WAIT_BUYER_PAY：交易创建，等待买家付款；TRADE_CLOSED：未付款交易超时关闭，或支付完成后全额退款；TRADE_SUCCESS：交易支付成功；TRADE_FINISHED：交易结束，不可退款】',
  `refund_money` float DEFAULT '0' COMMENT '总计退款金额',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_info
-- ----------------------------
BEGIN;
INSERT INTO `order_info` VALUES ('154729890565223', '[测试商品]', '[测试商品]', 0, '2088131827729981', '', 'WAIT_BUYER_PAY', 0, '2019-01-12 21:15:06', NULL);
INSERT INTO `order_info` VALUES ('154729913152044', '[测试商品]', '[测试商品]', 0, '2088131827729981', '', 'WAIT_BUYER_PAY', 0, '2019-01-12 21:18:52', NULL);
INSERT INTO `order_info` VALUES ('154729916193767', '[测试商品]', '[测试商品]', 1, '2088131827729981', '', 'WAIT_BUYER_PAY', 0, '2019-01-12 21:19:22', NULL);
INSERT INTO `order_info` VALUES ('154729920273034', '[测试商品]', '[测试商品]', 0, '2088131827729981', '', 'WAIT_BUYER_PAY', 0, '2019-01-12 21:20:03', NULL);
INSERT INTO `order_info` VALUES ('154729936260284', '[测试商品]', '[测试商品]', 1, '2088131827729981', '', 'WAIT_BUYER_PAY', 0, '2019-01-12 21:22:43', NULL);
COMMIT;

-- ----------------------------
-- Table structure for order_local
-- ----------------------------
DROP TABLE IF EXISTS `order_local`;
CREATE TABLE `order_local` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `alipay_ord_num` varchar(50) NOT NULL DEFAULT '' COMMENT '支付宝订单号',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `good_name` varchar(30) NOT NULL DEFAULT '' COMMENT '商品id',
  `good_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `good_num` int(4) NOT NULL DEFAULT '1' COMMENT '购买商品数量',
  `good_detail` varchar(125) NOT NULL DEFAULT '' COMMENT '购买数量',
  `adress_info` varchar(100) NOT NULL DEFAULT '' COMMENT '用户收货地址id',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT ' 0已删除 1存在',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_local
-- ----------------------------
BEGIN;
INSERT INTO `order_local` VALUES (51, '154729890565223', 33, '测试商品', 0.01, 1, '测试商品', '4123123', 1, '2019-01-12 13:15:05', '2019-01-12 13:15:05');
INSERT INTO `order_local` VALUES (52, '154729913152044', 33, '测试商品', 0.10, 1, '测试商品', '4123123', 1, '2019-01-12 13:18:51', '2019-01-12 13:18:51');
INSERT INTO `order_local` VALUES (53, '154729916193767', 33, '测试商品', 1.00, 1, '测试商品', '41232', 1, '2019-01-12 13:19:21', '2019-01-12 13:19:21');
INSERT INTO `order_local` VALUES (54, '154729920273034', 33, '测试商品', 0.20, 1, '测试商品', '4123123', 1, '2019-01-12 13:20:02', '2019-01-12 13:20:02');
INSERT INTO `order_local` VALUES (55, '154729936260284', 33, '测试商品', 1.00, 1, '测试商品', '4123123', 1, '2019-01-12 13:22:42', '2019-01-12 13:22:42');
COMMIT;

-- ----------------------------
-- Table structure for order_refund
-- ----------------------------
DROP TABLE IF EXISTS `order_refund`;
CREATE TABLE `order_refund` (
  `refund_id` varchar(32) NOT NULL COMMENT '退款号',
  `order_id` varchar(32) DEFAULT NULL COMMENT '订单号',
  `money` float DEFAULT NULL COMMENT '退款金额',
  `account` varchar(64) DEFAULT NULL COMMENT '退款账户',
  `reason` varchar(255) DEFAULT NULL COMMENT '退款原因',
  `refund_date` varchar(32) DEFAULT NULL COMMENT '退款时间',
  PRIMARY KEY (`refund_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单退款';

-- ----------------------------
-- Records of order_refund
-- ----------------------------
BEGIN;
INSERT INTO `order_refund` VALUES ('154701343052705', '154701331557142', 123, 'ywe***@sandbox.com', '123', '2019-01-09 13:57:11');
COMMIT;

-- ----------------------------
-- Table structure for shop_cart
-- ----------------------------
DROP TABLE IF EXISTS `shop_cart`;
CREATE TABLE `shop_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `good_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品id',
  `num` int(11) NOT NULL DEFAULT '1' COMMENT '购买数量',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT ' 0已删除 1存在',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='购物车表';

-- ----------------------------
-- Records of shop_cart
-- ----------------------------
BEGIN;
INSERT INTO `shop_cart` VALUES (19, 29, 2, 12, 1, '2019-01-05 15:42:05', '2019-01-05 15:42:05');
INSERT INTO `shop_cart` VALUES (22, 33, 3, 3, 1, '2019-01-09 22:45:55', '2019-01-09 22:45:55');
INSERT INTO `shop_cart` VALUES (24, 33, 5, 3, 1, '2019-01-10 10:24:06', '2019-01-10 10:24:06');
INSERT INTO `shop_cart` VALUES (27, 34, 5, 2, 1, '2019-01-11 14:10:44', '2019-01-11 14:10:44');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(15) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `password` varchar(21) NOT NULL DEFAULT '' COMMENT '密码',
  `tel` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `email` varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT ' 0已删除 1存在',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `role_id` int(4) NOT NULL DEFAULT '1' COMMENT '1普通用户2管理员',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (29, '1', '123', '', '', 1, '2019-01-05 08:52:26', '2019-01-05 09:47:47', 1);
INSERT INTO `user` VALUES (31, '2', '123', '', '', 1, '2019-01-05 09:43:40', '2019-01-05 09:47:47', 1);
INSERT INTO `user` VALUES (32, '3', '123', '', '', 1, '2019-01-05 09:45:42', '2019-01-05 09:47:49', 1);
INSERT INTO `user` VALUES (33, '123', '123', '', '', 1, '2019-01-09 14:10:48', '2019-01-09 14:10:48', 1);
INSERT INTO `user` VALUES (34, 'admin', 'admin', '', '', 1, '2019-01-11 12:41:16', '2019-01-11 12:41:16', 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
