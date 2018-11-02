/*
Navicat MySQL Data Transfer

Source Server         : 127本机
Source Server Version : 50720
Source Host           : 127.0.0.1:3306
Source Database       : myspringboot

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-11-02 15:06:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` int(11) NOT NULL COMMENT '父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单icon',
  `sort` int(10) NOT NULL COMMENT '排序',
  `href` varchar(2000) DEFAULT NULL COMMENT '链接',
  `is_show` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否显示（0：隐藏， 1：显示）',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '功能菜单', null, '0', null, '0', null);
INSERT INTO `sys_menu` VALUES ('2', '1', '系统管理', 'am-icon-home', '5', null, '1', null);
INSERT INTO `sys_menu` VALUES ('3', '2', '用户管理', 'am-icon-angle-right', '5', 'sys/user/list', '1', 'sys:list:user');
INSERT INTO `sys_menu` VALUES ('4', '2', '角色管理', 'am-icon-angle-right', '10', 'sys/role/list', '1', 'sys:list:role');
INSERT INTO `sys_menu` VALUES ('5', '2', '菜单管理', 'am-icon-angle-right', '15', 'sys/menu/list', '1', 'sys:list:menu');
INSERT INTO `sys_menu` VALUES ('6', '3', '添加', null, '5', null, '0', 'sys:add:user');
INSERT INTO `sys_menu` VALUES ('7', '3', '查看', null, '10', null, '0', 'sys:view:user');
INSERT INTO `sys_menu` VALUES ('9', '3', '编辑', null, '15', null, '0', 'sys:edit:user');
INSERT INTO `sys_menu` VALUES ('10', '3', '删除', null, '20', null, '0', 'sys:delete:user');
INSERT INTO `sys_menu` VALUES ('11', '4', '添加', null, '5', null, '0', 'sys:add:role');
INSERT INTO `sys_menu` VALUES ('12', '4', '查看', null, '10', null, '0', 'sys:view:role');
INSERT INTO `sys_menu` VALUES ('13', '4', '编辑', null, '15', null, '0', 'sys:edit:role');
INSERT INTO `sys_menu` VALUES ('14', '4', '权限设置', null, '20', null, '0', 'sys:allocation:role');
INSERT INTO `sys_menu` VALUES ('15', '4', '删除', null, '25', null, '0', 'sys:delete:role');
INSERT INTO `sys_menu` VALUES ('16', '5', '添加', null, '10', null, '0', 'sys:add:menu');
INSERT INTO `sys_menu` VALUES ('17', '5', '查看', null, '15', null, '0', 'sys:view:menu');
INSERT INTO `sys_menu` VALUES ('18', '5', '编辑', null, '20', null, '0', 'sys:edit:menu');
INSERT INTO `sys_menu` VALUES ('19', '5', '删除', null, '15', null, '0', 'sys:delete:menu');
INSERT INTO `sys_menu` VALUES ('44', '1', '系统监控', 'am-icon-bar-chart', '10', '', '1', '');
INSERT INTO `sys_menu` VALUES ('45', '44', '连接池监控', 'am-icon-angle-right', '5', 'druid', '1', '');
INSERT INTO `sys_menu` VALUES ('46', '3', '导出', '', '25', '', '0', 'sys:export:user');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `is_admin` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是超级管理员',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态（0：不可用， 1：可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'Role_Admin', '1', '1');
INSERT INTO `sys_role` VALUES ('2', 'Role_User', '0', '1');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `menu_id` int(11) NOT NULL COMMENT '菜单编号',
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '6');
INSERT INTO `sys_role_menu` VALUES ('1', '7');
INSERT INTO `sys_role_menu` VALUES ('1', '9');
INSERT INTO `sys_role_menu` VALUES ('1', '10');
INSERT INTO `sys_role_menu` VALUES ('1', '46');
INSERT INTO `sys_role_menu` VALUES ('1', '4');
INSERT INTO `sys_role_menu` VALUES ('1', '11');
INSERT INTO `sys_role_menu` VALUES ('1', '12');
INSERT INTO `sys_role_menu` VALUES ('1', '13');
INSERT INTO `sys_role_menu` VALUES ('1', '14');
INSERT INTO `sys_role_menu` VALUES ('1', '15');
INSERT INTO `sys_role_menu` VALUES ('1', '5');
INSERT INTO `sys_role_menu` VALUES ('1', '16');
INSERT INTO `sys_role_menu` VALUES ('1', '17');
INSERT INTO `sys_role_menu` VALUES ('1', '19');
INSERT INTO `sys_role_menu` VALUES ('1', '18');
INSERT INTO `sys_role_menu` VALUES ('1', '44');
INSERT INTO `sys_role_menu` VALUES ('1', '45');
INSERT INTO `sys_role_menu` VALUES ('2', '2');
INSERT INTO `sys_role_menu` VALUES ('2', '3');
INSERT INTO `sys_role_menu` VALUES ('2', '6');
INSERT INTO `sys_role_menu` VALUES ('2', '7');
INSERT INTO `sys_role_menu` VALUES ('2', '9');
INSERT INTO `sys_role_menu` VALUES ('2', '10');
INSERT INTO `sys_role_menu` VALUES ('2', '46');
INSERT INTO `sys_role_menu` VALUES ('2', '44');
INSERT INTO `sys_role_menu` VALUES ('2', '45');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `phone` varchar(200) NOT NULL COMMENT '电话',
  `headImg` varchar(255) DEFAULT NULL COMMENT '头像',
  `startTime` datetime NOT NULL COMMENT '开始时间',
  `endTime` datetime NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '038bdaf98f2037b31f1e75b5b4c9b26e', '15150806930', '', '2018-01-01 00:00:00', '2018-10-31 11:02:40');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('2', '2');
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('1', '2');
