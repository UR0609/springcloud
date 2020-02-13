SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for children
-- ----------------------------
DROP TABLE IF EXISTS `children`;
CREATE TABLE `children`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '上级ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of children
-- ----------------------------
INSERT INTO `children` VALUES (1, 'Jone', 1);
INSERT INTO `children` VALUES (2, 'Jack', 1);
INSERT INTO `children` VALUES (3, 'Jack2', 1);
INSERT INTO `children` VALUES (4, 'Jack', 15);
INSERT INTO `children` VALUES (5, 'Billie', 15);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Jone', 18, 'test1@baomidou.com');
INSERT INTO `user` VALUES (2, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (3, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (4, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (5, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (6, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (7, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (8, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (9, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (10, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (11, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (12, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (13, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (14, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (15, 'Tom', 28, 'test3@baomidou.com');
INSERT INTO `user` VALUES (16, 'Sandy', 21, 'test4@baomidou.com');
INSERT INTO `user` VALUES (17, 'Billie', 24, 'test5@baomidou.com');

SET FOREIGN_KEY_CHECKS = 1;
