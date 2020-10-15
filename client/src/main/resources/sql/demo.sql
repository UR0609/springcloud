/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : 127.0.0.1:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : 65001

 Date: 14/08/2020 09:17:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for children
-- ----------------------------
DROP TABLE IF EXISTS `children`;
CREATE TABLE `children`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '上级ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of children
-- ----------------------------
INSERT INTO `children` VALUES (1, 'Jone', 1);
INSERT INTO `children` VALUES (2, 'Jack', 1);
INSERT INTO `children` VALUES (3, 'Jack2', 1);
INSERT INTO `children` VALUES (4, 'Jack', 15);
INSERT INTO `children` VALUES (5, 'Billie', 15);

-- ----------------------------
-- Table structure for interface_info
-- ----------------------------
DROP TABLE IF EXISTS `interface_info`;
CREATE TABLE `interface_info`  (
  `api_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `api_method` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'HttpMethod：GET、PUT、POST',
  `api_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拦截路径',
  `api_status` int(2) NOT NULL COMMENT '状态：0草稿，1发布，2有变更，3禁用',
  `api_comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注释',
  `api_type` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '脚本类型：SQL、DataQL',
  `api_script` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '查询脚本：xxxxxxx',
  `api_schema` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接口的请求/响应数据结构',
  `api_sample` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求/响应/请求头样本数据',
  `api_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `api_gmt_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`api_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Dataway 中的API' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of interface_info
-- ----------------------------
INSERT INTO `interface_info` VALUES (2, 'GET', '/api/demos', 1, '', 'DataQL', '// a new DataQL Query.\nvar query = @@sql()<%\n    select * from s_user\n%>\nreturn query()', '{}', '{\"requestBody\":\"{\\\"message\\\":\\\"Hello DataQL.\\\"}\",\"headerData\":[]}', '2020-05-12 20:37:04', '2020-05-12 20:37:08');
INSERT INTO `interface_info` VALUES (3, 'POST', '/api/getData', 1, '', 'DataQL', '// a new DataQL Query.\n// 声明一个 SQL\nvar dataSet = @@sql(username) <%\n    select * from s_user where username = #{username};\n%>\n// 执行这个 SQL，并返回结果\nreturn dataSet(${username});', '{}', '{\"requestBody\":\"{\\\"username\\\":\\\"admin\\\"}\",\"headerData\":[]}', '2020-05-14 19:02:42', '2020-05-14 19:02:45');
INSERT INTO `interface_info` VALUES (5, 'POST', '/api/add', 1, '', 'DataQL', '// insert语句模版\nvar insertSQL = @@sql(username,password) <%\n    insert into s_user (\n        username,\n        password\n    ) values (\n        #{username},\n        #{password}\n    )\n%>\n// 插入数据\nreturn insertSQL(${username},${password});', '{}', '{\"requestBody\":\"{\\\"id\\\":3,\\\"username\\\":\\\"addtest\\\",\\\"password\\\":\\\"addtest\\\"}\",\"headerData\":[]}', '2020-05-14 19:15:26', '2020-05-14 19:15:29');
INSERT INTO `interface_info` VALUES (6, 'POST', '/api/rule/add.do', 1, '', 'DataQL', '// insert语句模版\nvar insertSQL = @@sql(name,code,desc,creater) <%\n    insert into s_role (\n        name,\n        code,\n        `desc`,\n        creater,\n        create_time\n    ) value (\n        #{name},\n        #{code},\n        #{desc},\n        #{creater},\n        now()\n    )\n%>\n// 插入数据\nreturn insertSQL(${name},${code},${desc},${creater});', '{}', '{\"requestBody\":\"{\\\"name\\\":\\\"admin\\\",\\\"code\\\":\\\"admin\\\",\\\"desc\\\":\\\"超级管理员\\\",\\\"creater\\\":\\\"1\\\"}\",\"headerData\":[]}', '2020-05-14 19:33:57', '2020-05-14 19:40:01');

-- ----------------------------
-- Table structure for interface_release
-- ----------------------------
DROP TABLE IF EXISTS `interface_release`;
CREATE TABLE `interface_release`  (
  `pub_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Publish ID',
  `pub_api_id` int(11) NOT NULL COMMENT '所属API ID',
  `pub_method` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'HttpMethod：GET、PUT、POST',
  `pub_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拦截路径',
  `pub_status` int(2) NOT NULL COMMENT '状态：0有效，1无效（可能被下线）',
  `pub_type` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '脚本类型：SQL、DataQL',
  `pub_script` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '查询脚本：xxxxxxx',
  `pub_script_ori` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原始查询脚本，仅当类型为SQL时不同',
  `pub_schema` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接口的请求/响应数据结构',
  `pub_sample` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求/响应/请求头样本数据',
  `pub_release_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间（下线不更新）',
  PRIMARY KEY (`pub_id`) USING BTREE,
  INDEX `idx_interface_release`(`pub_api_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Dataway API 发布历史。' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of interface_release
-- ----------------------------
INSERT INTO `interface_release` VALUES (1, 1, 'POST', '/api/demos', 1, 'DataQL', '\n\nvar query = @@sql()<%\n    SELECT * FROM s_user\n%>\nreturn query()', '\n\nvar query = @@sql()<%\n    SELECT * FROM s_user\n%>\nreturn query()', '{}', '{\"requestBody\":\"{\\\"message\\\":\\\"Hello DataQL.\\\"}\",\"headerData\":[]}', '2020-05-12 20:30:05');
INSERT INTO `interface_release` VALUES (2, 1, 'POST', '/api/demos', 1, 'DataQL', '\n\nvar query = @@sql()<%\n    SELECT * FROM s_user\n%>\nreturn query()', '\n\nvar query = @@sql()<%\n    SELECT * FROM s_user\n%>\nreturn query()', '{}', '{\"requestBody\":\"{\\\"message\\\":\\\"Hello DataQL.\\\"}\",\"headerData\":[]}', '2020-05-12 20:33:10');
INSERT INTO `interface_release` VALUES (3, 2, 'GET', '/api/demos', 0, 'DataQL', '// a new DataQL Query.\nvar query = @@sql()<%\n    select * from s_user\n%>\nreturn query()', '// a new DataQL Query.\nvar query = @@sql()<%\n    select * from s_user\n%>\nreturn query()', '{}', '{\"requestBody\":\"{\\\"message\\\":\\\"Hello DataQL.\\\"}\",\"headerData\":[]}', '2020-05-12 20:37:08');
INSERT INTO `interface_release` VALUES (4, 3, 'POST', '/api/getData', 0, 'DataQL', '// a new DataQL Query.\n// 声明一个 SQL\nvar dataSet = @@sql(username) <%\n    select * from s_user where username = #{username};\n%>\n// 执行这个 SQL，并返回结果\nreturn dataSet(${username});', '// a new DataQL Query.\n// 声明一个 SQL\nvar dataSet = @@sql(username) <%\n    select * from s_user where username = #{username};\n%>\n// 执行这个 SQL，并返回结果\nreturn dataSet(${username});', '{}', '{\"requestBody\":\"{\\\"username\\\":\\\"admin\\\"}\",\"headerData\":[]}', '2020-05-14 19:02:45');
INSERT INTO `interface_release` VALUES (5, 4, 'POST', '/api/', 1, 'DataQL', '// insert语句模版\nvar insertSQL = @@sql(username,password) <%\n    insert into s_user (\n        username,\n        password\n    ) values (\n        #{username},\n        #{password}\n    )\n%>\n// 插入数据\nreturn insertSQL(${username},${password});', '// insert语句模版\nvar insertSQL = @@sql(username,password) <%\n    insert into s_user (\n        username,\n        password\n    ) values (\n        #{username},\n        #{password}\n    )\n%>\n// 插入数据\nreturn insertSQL(${username},${password});', '{}', '{\"requestBody\":\"{\\\"username\\\":\\\"addtest\\\",\\\"password\\\":\\\"addtest\\\"}\",\"headerData\":[]}', '2020-05-14 19:14:26');
INSERT INTO `interface_release` VALUES (6, 5, 'POST', '/api/add', 0, 'DataQL', '// insert语句模版\nvar insertSQL = @@sql(username,password) <%\n    insert into s_user (\n        username,\n        password\n    ) values (\n        #{username},\n        #{password}\n    )\n%>\n// 插入数据\nreturn insertSQL(${username},${password});', '// insert语句模版\nvar insertSQL = @@sql(username,password) <%\n    insert into s_user (\n        username,\n        password\n    ) values (\n        #{username},\n        #{password}\n    )\n%>\n// 插入数据\nreturn insertSQL(${username},${password});', '{}', '{\"requestBody\":\"{\\\"id\\\":3,\\\"username\\\":\\\"addtest\\\",\\\"password\\\":\\\"addtest\\\"}\",\"headerData\":[]}', '2020-05-14 19:15:29');
INSERT INTO `interface_release` VALUES (7, 6, 'POST', '/api/rule/add.do', 0, 'DataQL', '// insert语句模版\nvar insertSQL = @@sql(name,code,desc,creater) <%\n    insert into s_role (\n        name,\n        code,\n        `desc`,\n        creater,\n        create_time\n    ) value (\n        #{name},\n        #{code},\n        #{desc},\n        #{creater},\n        now()\n    )\n%>\n// 插入数据\nreturn insertSQL(${name},${code},${desc},${creater});', '// insert语句模版\nvar insertSQL = @@sql(name,code,desc,creater) <%\n    insert into s_role (\n        name,\n        code,\n        `desc`,\n        creater,\n        create_time\n    ) value (\n        #{name},\n        #{code},\n        #{desc},\n        #{creater},\n        now()\n    )\n%>\n// 插入数据\nreturn insertSQL(${name},${code},${desc},${creater});', '{}', '{\"requestBody\":\"{\\\"name\\\":\\\"admin\\\",\\\"code\\\":\\\"admin\\\",\\\"desc\\\":\\\"超级管理员\\\",\\\"creater\\\":\\\"1\\\"}\",\"headerData\":[]}', '2020-05-14 19:40:01');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` bigint(12) NOT NULL,
  `permission_id` bigint(12) NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `s_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `s_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (2, 1);
INSERT INTO `role_permission` VALUES (3, 1);
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (2, 2);
INSERT INTO `role_permission` VALUES (1, 3);
INSERT INTO `role_permission` VALUES (1, 4);
INSERT INTO `role_permission` VALUES (2, 4);

-- ----------------------------
-- Table structure for s_permission
-- ----------------------------
DROP TABLE IF EXISTS `s_permission`;
CREATE TABLE `s_permission`  (
  `id` bigint(12) NOT NULL,
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of s_permission
-- ----------------------------
INSERT INTO `s_permission` VALUES (1, 'select', '查看');
INSERT INTO `s_permission` VALUES (2, 'update', '更新');
INSERT INTO `s_permission` VALUES (3, 'delete', '删除');
INSERT INTO `s_permission` VALUES (4, 'save', '新增');

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role`  (
  `id` bigint(12) NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of s_role
-- ----------------------------
INSERT INTO `s_role` VALUES (1, 'svip');
INSERT INTO `s_role` VALUES (2, 'vip');
INSERT INTO `s_role` VALUES (3, 'p');

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user`  (
  `id` bigint(12) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(6) NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES (1, 'Jack', '123', NULL, NULL, NULL);
INSERT INTO `s_user` VALUES (2, 'Rose', '123', 'ljryh', 24, '124732719@qq.com');
INSERT INTO `s_user` VALUES (3, 'Paul', '123', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_token`;
CREATE TABLE `sys_token`  (
  `user_id` int(11) NOT NULL,
  `expire_time` datetime(0) NULL DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_token
-- ----------------------------
INSERT INTO `sys_token` VALUES (1, '2020-08-05 02:31:12', '5547774c0f40d752946f84dedf8e0aaf', '2020-08-04 14:31:12');
INSERT INTO `sys_token` VALUES (2, '2020-08-11 05:44:51', '3ab2c8ab06e0d5786f23a9b22e831089', '2020-08-10 17:44:51');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` bigint(12) NOT NULL,
  `role_id` bigint(12) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `s_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `s_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (3, 3);

SET FOREIGN_KEY_CHECKS = 1;
