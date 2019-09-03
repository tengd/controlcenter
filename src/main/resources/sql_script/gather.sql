/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 80011
Source Host           : 127.0.0.1:3306
Source Database       : gather

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2019-08-31 09:19:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for s_dic_date
-- ----------------------------
DROP TABLE IF EXISTS `s_dic_date`;
CREATE TABLE `s_dic_date` (
  `uuid` varchar(32) NOT NULL COMMENT 'UUID',
  `typecode` varchar(50) DEFAULT NULL COMMENT '类型编码',
  `d_name` varchar(50) DEFAULT NULL COMMENT '类型名称',
  `d_value` varchar(70) DEFAULT NULL,
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `createdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `updatedate` timestamp NULL DEFAULT NULL COMMENT '更新日志',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='初使化字典数据';

-- ----------------------------
-- Records of s_dic_date
-- ----------------------------
INSERT INTO `s_dic_date` VALUES ('332bfbb05a184d9fa6e283c02c83a6b2', 'FUN', '树', 'tree', 'admin', '2016-04-19 09:03:30', null, null);
INSERT INTO `s_dic_date` VALUES ('436384c69956401ab3a5d8d4ac54adb7', 'P', '锁(lock)', 'lock', 'admin', '2018-01-12 15:38:10', null, null);
INSERT INTO `s_dic_date` VALUES ('6e0f18f977f348ffb9cde66a7dc23315', 'P', '获取数据(fetch)', 'fetch', 'admin', '2018-01-26 11:24:16', 'admin', '2018-01-26 11:24:29');
INSERT INTO `s_dic_date` VALUES ('72c765e953df40c59d996156b8f05161', 'FUN', '菜单', 'menu', 'admin', '2016-04-19 09:04:59', null, null);
INSERT INTO `s_dic_date` VALUES ('842b0726234f429c98dc295c1e9f150a', 'U004', '系统管理员', '', 'admin', '2017-04-01 14:11:56', 'admin', '2017-04-01 14:11:56');
INSERT INTO `s_dic_date` VALUES ('87076cb7f4f242b6b7c8357a6e9a88ac', 'P', '解析(resolve)', 'resolve', 'admin', '2018-01-12 15:36:24', null, null);
INSERT INTO `s_dic_date` VALUES ('8dae34077b16455983ebe89506ba3d5d', 'FUN', '系统', 'system', 'admin', '2018-07-13 11:51:21', null, null);
INSERT INTO `s_dic_date` VALUES ('915f35a89bf84ba2af81670e950b48b5', 'U002', '客户', '', 'admin', '2017-04-01 14:11:34', 'admin', '2017-04-01 14:11:34');
INSERT INTO `s_dic_date` VALUES ('a09f9bd0565a4b78a7f87d187e8f48d6', 'P', '所有(*)', '*', 'admin', '2016-04-19 09:05:45', null, null);
INSERT INTO `s_dic_date` VALUES ('bf9ba982cb7942d189321d1e5f4c7791', 'P', '清空(clear)', 'clear', 'admin', '2018-01-12 15:35:10', null, null);
INSERT INTO `s_dic_date` VALUES ('c1824e66c6d54f53ab135d2fd6e4a1d1', 'P', '定时配置(quartz)', 'quartz', 'admin', '2018-01-12 15:37:30', null, null);
INSERT INTO `s_dic_date` VALUES ('c2cfa738983b4f39b04dc4800ba5176f', 'U003', '一般权限用户', '', 'admin', '2017-04-01 14:11:43', 'admin', '2017-04-01 14:11:43');
INSERT INTO `s_dic_date` VALUES ('dd8bc0d95a114452bd7ce3867e95b2a9', 'P', '提交(submit)', 'submit', 'admin', '2018-01-12 15:33:55', null, null);
INSERT INTO `s_dic_date` VALUES ('dfcf0d3124a04bc0bb1d0e3f03ca24d7', 'P', '查看(view)', 'view', 'admin', '2016-04-19 09:05:55', null, null);
INSERT INTO `s_dic_date` VALUES ('ecb404bc9e1e42a99ccc537e5b76dfc4', 'P', '编辑(update)', 'update', 'admin', '2016-04-19 09:06:00', null, null);
INSERT INTO `s_dic_date` VALUES ('fa9d186adc9d495d85e7a6a0343784b8', 'P', '添加(add)', 'add', 'admin', '2016-04-18 10:11:51', null, null);

-- ----------------------------
-- Table structure for s_fun
-- ----------------------------
DROP TABLE IF EXISTS `s_fun`;
CREATE TABLE `s_fun` (
  `uuid` varchar(32) NOT NULL COMMENT 'UUID',
  `f_name` varchar(200) DEFAULT NULL COMMENT '功能名称',
  `f_name_jx` varchar(200) DEFAULT NULL COMMENT '功能名称简写',
  `fun_type` varchar(20) DEFAULT NULL COMMENT '功能类型 菜单:menu ,树:tree,web导航:navigation',
  `sort_index` int(11) DEFAULT NULL COMMENT '排序号',
  `pater_id` varchar(32) DEFAULT NULL COMMENT '父功能ID',
  `url` varchar(1000) DEFAULT NULL COMMENT '功能对应界面的URL',
  `click_num` int(11) DEFAULT NULL COMMENT '点击数',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `createdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能表';

-- ----------------------------
-- Records of s_fun
-- ----------------------------
INSERT INTO `s_fun` VALUES ('0710004db6bf49ca8cc1c0dc9142d10d', '流程配置', '流程配置', 'tree', '20', '', '', null, 'admin', '2018-07-23 20:19:29', null, null);
INSERT INTO `s_fun` VALUES ('1406e81a85fc4bc59f8731c332f40737', '角色管理', '角色管理', 'tree', '12', 'b04f72da41a0494aa93be8619031c9ca', 'system/role', null, '腾冬', '2018-05-21 17:54:32', 'admin', '2018-05-18 09:55:10');
INSERT INTO `s_fun` VALUES ('2c4da2a58cad4fdeb3cbd9797da84226', '用户管理', '用户管理', 'tree', '11', 'b04f72da41a0494aa93be8619031c9ca', 'system/user', null, '腾冬', '2016-04-21 14:26:40', null, null);
INSERT INTO `s_fun` VALUES ('44d39ecf58d54c01a86d84ff8c55af1b', '流程创建', '流程创建', 'tree', '21', '0710004db6bf49ca8cc1c0dc9142d10d', 'snaker/processDesigner', null, 'admin', '2018-07-23 20:20:55', null, null);
INSERT INTO `s_fun` VALUES ('7e6354c0de6049469317de53d7c89267', '权限管理', '权限管理', 'tree', '13', 'b04f72da41a0494aa93be8619031c9ca', 'system/permission', null, '腾冬', '2016-04-21 14:26:46', null, null);
INSERT INTO `s_fun` VALUES ('8b66ab20fd6542efa012f068bd4b70d4', '流程列表', '流程列表', 'tree', '22', '0710004db6bf49ca8cc1c0dc9142d10d', 'snaker/processList', null, 'admin', '2018-07-23 20:21:50', null, null);
INSERT INTO `s_fun` VALUES ('99fda79b1c294fa1bb5372533d0556f5', '业务组织', '业务组织', 'tree', '16', 'b04f72da41a0494aa93be8619031c9ca', 'system/org', null, 'admin', '2018-07-17 14:29:10', 'admin', '2018-07-17 14:29:10');
INSERT INTO `s_fun` VALUES ('b04f72da41a0494aa93be8619031c9ca', '系统组织', '组织管理', 'tree', '10', '', '', null, '腾冬', '2017-12-25 15:23:46', 'admin', '2017-12-25 15:23:46');
INSERT INTO `s_fun` VALUES ('d05f6751a1e148be8958ea7b4fd30309', '安全日志', '安全日志', 'tree', '17', 'b04f72da41a0494aa93be8619031c9ca', 'system/syslog', null, 'admin', '2018-07-17 14:30:08', 'admin', '2018-07-17 14:30:08');
INSERT INTO `s_fun` VALUES ('d2cb1e5bb13240f9a7c47819b029da3a', '字典管理', '数据字典', 'tree', '15', 'b04f72da41a0494aa93be8619031c9ca', 'system/dicdate', null, 'admin', '2016-04-25 14:18:59', null, null);
INSERT INTO `s_fun` VALUES ('d3cd097e824644d6b9f96b3f532710a1', '功能管理', '功能管理', 'tree', '14', 'b04f72da41a0494aa93be8619031c9ca', 'system/fun', null, '腾冬', '2016-04-21 14:26:49', null, null);

-- ----------------------------
-- Table structure for s_org
-- ----------------------------
DROP TABLE IF EXISTS `s_org`;
CREATE TABLE `s_org` (
  `uuid` varchar(32) NOT NULL COMMENT 'ID',
  `org_code` varchar(50) DEFAULT NULL COMMENT '组织码',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `parent_code` varchar(50) DEFAULT NULL COMMENT '父组织码',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父组织ID',
  `sort_index` int(11) DEFAULT NULL COMMENT '排序号',
  `locked` bigint(20) DEFAULT NULL COMMENT '是否锁定',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `updatedate` date DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织表';

-- ----------------------------
-- Records of s_org
-- ----------------------------
INSERT INTO `s_org` VALUES ('86a69d5908154f07b630798af490d6bb', '5325', '云内动力ABS', null, null, '30', null, 'admin', '2018-06-05 08:35:52', null, null, '云内动力ABS');
INSERT INTO `s_org` VALUES ('942fc862825d4d24abb6808861333d89', '5323', '融创ABS项目', null, null, '10', null, 'admin', '2018-06-05 08:35:45', null, null, '融创-项目描述');
INSERT INTO `s_org` VALUES ('bd50852cc56346fdb4e25e6c9557cb1a', '5254', '恒大地产ABS项目', '', '', '20', null, 'admin', '2018-05-24 11:31:35', null, null, '恒大-项目描述');

-- ----------------------------
-- Table structure for s_org_user
-- ----------------------------
DROP TABLE IF EXISTS `s_org_user`;
CREATE TABLE `s_org_user` (
  `uuid` varchar(32) NOT NULL,
  `u_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `org_id` varchar(32) DEFAULT NULL COMMENT '组织ID',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(32) DEFAULT NULL COMMENT '更新者',
  `updatedate` date DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织与用户关系';

-- ----------------------------
-- Records of s_org_user
-- ----------------------------
INSERT INTO `s_org_user` VALUES ('0acacb4f990343dea2997341a0bc93e2', 'd09fa7ec51a84b278b8ce10c0830e811', '942fc862825d4d24abb6808861333d89', 'admin', '2018-06-05 11:27:20', null, null);
INSERT INTO `s_org_user` VALUES ('2131ca41a85148c9a9a3a8caef980aac', '14eb20cf168e4dd7b1b3ddf37d8b3a31', 'bd50852cc56346fdb4e25e6c9557cb1a', 'admin', '2018-05-17 18:03:48', null, null);
INSERT INTO `s_org_user` VALUES ('419375c7689d4d309525690ebf2a910b', '14eb20cf168e4dd7b1b3ddf37d8b3a31', '942fc862825d4d24abb6808861333d89', 'admin', '2018-05-17 18:03:48', null, null);
INSERT INTO `s_org_user` VALUES ('4603ea952c834fc796da94b5349ca645', 'd09fa7ec51a84b278b8ce10c0830e811', '86a69d5908154f07b630798af490d6bb', 'admin', '2018-06-05 11:27:20', null, null);
INSERT INTO `s_org_user` VALUES ('f2ec20fa9c6f4a4bac5c589cedad754c', 'd09fa7ec51a84b278b8ce10c0830e811', 'bd50852cc56346fdb4e25e6c9557cb1a', 'admin', '2018-06-05 11:27:20', null, null);

-- ----------------------------
-- Table structure for s_permission
-- ----------------------------
DROP TABLE IF EXISTS `s_permission`;
CREATE TABLE `s_permission` (
  `uuid` varchar(32) NOT NULL,
  `fun_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `p_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限:权限\r\n功能:user:create\r\n功能:user:update\r\n功能:user:insert\r\n功能:user:delete\r\n功能:user:view\r\n\r\n用户拥有资源“system:user”的“update”和“delete”权限。如上可以简写成\r\nrole42="system:user:update,delete"\r\n单个资源全部权限\r\nrole51="system:user:create,update,delete,view"\r\nrole51=system:user:*\r\nrole51=system:user\r\nrole51=*:view\r\n用户拥有所有资源的“view”所有权限\r\nrole5=*:*:view',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `updatedate` date DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of s_permission
-- ----------------------------
INSERT INTO `s_permission` VALUES ('147e855baa7a4794bfc958525fd7114a', '1406e81a85fc4bc59f8731c332f40737', 'd76dcb3c64934c35bd68e09342bfe67b', '角色管理功能-组织管理员(角色)-拥有', '角色管理:组织管理员:*', 'admin', '2016-04-22 15:12:54', null, null);
INSERT INTO `s_permission` VALUES ('296f421a8af34128845b515320ee1c79', '99fda79b1c294fa1bb5372533d0556f5', 'd76dcb3c64934c35bd68e09342bfe67b', '组织管理功能-组织管理员(角色)-拥有', '组织管理:组织管理员:*', 'admin', '2018-07-14 13:48:08', null, null);
INSERT INTO `s_permission` VALUES ('6c60a56845df48ecb730cf43bea74868', 'd2cb1e5bb13240f9a7c47819b029da3a', 'd76dcb3c64934c35bd68e09342bfe67b', '字典管理功能-组织管理员(角色)-拥有', '字典管理:组织管理员:*', 'admin', '2016-04-23 16:04:39', null, null);
INSERT INTO `s_permission` VALUES ('7c8275d5348343c39e2494f65d167d3f', 'd05f6751a1e148be8958ea7b4fd30309', 'd76dcb3c64934c35bd68e09342bfe67b', '日志管理功能-组织管理员(角色)-拥有', '日志管理:组织管理员:*', 'admin', '2017-04-12 12:11:04', null, null);
INSERT INTO `s_permission` VALUES ('83645979fa404fe7b92c989537bea6b1', 'd3cd097e824644d6b9f96b3f532710a1', 'd76dcb3c64934c35bd68e09342bfe67b', '功能管理功能-组织管理员(角色)-拥有', '功能管理:组织管理员:*', 'admin', '2016-04-22 15:14:00', null, null);
INSERT INTO `s_permission` VALUES ('a9007a3f292a4672b5ec913f90d3545e', '7e6354c0de6049469317de53d7c89267', 'd76dcb3c64934c35bd68e09342bfe67b', '权限管理功能-组织管理员(角色)-拥有', '权限管理:组织管理员:*', 'admin', '2016-04-22 15:13:49', null, null);
INSERT INTO `s_permission` VALUES ('d2ebb510844146d0b1206f3270c90d91', '44d39ecf58d54c01a86d84ff8c55af1b', 'd76dcb3c64934c35bd68e09342bfe67b', '流程创建功能-组织管理员(角色)-拥有', '流程创建:组织管理员:*', 'admin', '2018-07-23 20:22:09', null, null);
INSERT INTO `s_permission` VALUES ('e1098481455a4ae899781745fd9c84a5', '2c4da2a58cad4fdeb3cbd9797da84226', 'd76dcb3c64934c35bd68e09342bfe67b', '用户管理功能-组织管理员(角色)-拥有', '用户管理:组织管理员:*', 'admin', '2016-04-22 15:12:32', null, null);
INSERT INTO `s_permission` VALUES ('e65b48080249429885bcb24461ba1c97', '8b66ab20fd6542efa012f068bd4b70d4', 'd76dcb3c64934c35bd68e09342bfe67b', '流程列表功能-组织管理员(角色)-拥有', '流程列表:组织管理员:*', 'admin', '2018-07-23 20:22:26', null, null);

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `uuid` varchar(32) NOT NULL COMMENT 'UUID',
  `r_name` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `createdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `updatedate` date DEFAULT NULL COMMENT '更新时间',
  `role_type` tinyint(8) DEFAULT '1',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of s_role
-- ----------------------------
INSERT INTO `s_role` VALUES ('23af4db311b340bcadf5a04cb795b4c4', 'daw', 'awd', 'admin', '2018-05-17 14:22:20', null, null, '1');
INSERT INTO `s_role` VALUES ('2c83715bb0144990a742bdb53383c1a5', '采集数据', '采集数据', 'admin', '2018-05-17 14:22:20', 'admin', '2018-02-06', '1');
INSERT INTO `s_role` VALUES ('d76dcb3c64934c35bd68e09342bfe67b', '组织管理员', '组织管理员', 'admin', '2018-05-17 14:22:21', 'admin', '2016-04-11', '1');

-- ----------------------------
-- Table structure for s_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `s_sys_log`;
CREATE TABLE `s_sys_log` (
  `uuid` varchar(32) NOT NULL COMMENT 'ID',
  `visit_ip` varchar(20) DEFAULT NULL COMMENT '访问IP',
  `u_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `u_name` varchar(20) DEFAULT NULL COMMENT '用户名称',
  `fun_id` varchar(32) DEFAULT NULL COMMENT '访问功能',
  `fun_name` varchar(200) DEFAULT NULL COMMENT '功能名称',
  `visit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '访问时间',
  `visit_os` varchar(50) DEFAULT NULL COMMENT '操作系统',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户日志表';



-- ----------------------------
-- Table structure for s_user_role
-- ----------------------------
DROP TABLE IF EXISTS `s_user_role`;
CREATE TABLE `s_user_role` (
  `uuid` varchar(32) NOT NULL,
  `userid` varchar(32) DEFAULT NULL,
  `roleid` varchar(32) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `creator` varchar(20) DEFAULT NULL,
  `createdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatetor` varchar(20) DEFAULT NULL,
  `updatedate` date DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色成员表';

-- ----------------------------
-- Records of s_user_role
-- ----------------------------
INSERT INTO `s_user_role` VALUES ('053f72caaff64147ab65dc1e52373389', 'bf35d564b1b54058ba530523a0782f9a', '2c83715bb0144990a742bdb53383c1a5', null, 'admin', '2018-02-06 11:02:23', null, null);
INSERT INTO `s_user_role` VALUES ('1a929dc19ea442d1843d8493b056efb4', 'b95ae3fdbc1844019abf5164c05371b1', 'd76dcb3c64934c35bd68e09342bfe67b', null, 'admin', '2018-05-07 16:40:15', null, null);
INSERT INTO `s_user_role` VALUES ('3439d757b8404a599d31416565afbf09', '14eb20cf168e4dd7b1b3ddf37d8b3a31', 'd76dcb3c64934c35bd68e09342bfe67b', null, 'admin', '2018-05-17 18:03:59', null, null);
INSERT INTO `s_user_role` VALUES ('3bfd808cdc1549efbb7f44edcc36762a', '42ae09dc35174f1c886639ab10a13542', 'd76dcb3c64934c35bd68e09342bfe67b', null, 'admin', '2018-01-19 10:36:41', null, null);
INSERT INTO `s_user_role` VALUES ('46bb60faa5cb4714a2057e092a2bf98f', '02cfff8ea56c4170ab6fdcc556c32b68', '2c83715bb0144990a742bdb53383c1a5', null, 'admin', '2018-02-06 11:02:10', null, null);
INSERT INTO `s_user_role` VALUES ('6baf3c4c7bf54304bde0844b3905d04f', 'cd61481e32954d009d3b22f84d7b6711', 'd76dcb3c64934c35bd68e09342bfe67b', null, 'admin', '2017-12-28 09:37:45', null, null);
INSERT INTO `s_user_role` VALUES ('ac39f596ef0f449b887bad112d42d14f', 'd09fa7ec51a84b278b8ce10c0830e811', 'd76dcb3c64934c35bd68e09342bfe67b', null, 'admin', '2018-05-17 09:39:01', null, null);
INSERT INTO `s_user_role` VALUES ('f734afaf1e264ab5b9f61d8f7e6b69f8', '238d4eb6b8cd4bcf88eae45201653fc0', 'd76dcb3c64934c35bd68e09342bfe67b', null, 'admin', '2016-04-27 14:35:24', null, null);

-- ----------------------------
-- Table structure for t_base_data
-- ----------------------------
DROP TABLE IF EXISTS `t_base_data`;
CREATE TABLE `t_base_data` (
  `uuid` varchar(32) NOT NULL COMMENT 'uuid',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `datasource_config_id` varchar(32) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL COMMENT '令牌',
  `table_name` varchar(20) DEFAULT NULL COMMENT '解析表',
  `company_code` varchar(20) DEFAULT NULL COMMENT '公司码',
  `company_name` varchar(50) DEFAULT NULL COMMENT '公司名称',
  `xml_text` longtext COMMENT '解析文本',
  `analysis_time` timestamp NULL DEFAULT NULL COMMENT '解析时间',
  `push_author` varchar(20) DEFAULT NULL COMMENT '推送者',
  `push_time` timestamp NULL DEFAULT NULL COMMENT '推送时间',
  `is_succ` bit(1) DEFAULT NULL COMMENT '是否解析成功',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础数据表';

-- ----------------------------
-- Records of t_base_data
-- ----------------------------

-- ----------------------------
-- Table structure for t_datasource_config
-- ----------------------------
DROP TABLE IF EXISTS `t_datasource_config`;
CREATE TABLE `t_datasource_config` (
  `uuid` varchar(32) NOT NULL,
  `t_t_uuid` varchar(32) DEFAULT NULL COMMENT 'ID',
  `t_f_uuid` varchar(32) DEFAULT NULL COMMENT 'ID',
  `ds_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `ds_name` varchar(50) DEFAULT NULL COMMENT '公司名称',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `driver` varchar(50) DEFAULT NULL COMMENT '驱动包',
  `url` varchar(200) DEFAULT NULL COMMENT 'url',
  `ds_username` varchar(50) DEFAULT NULL COMMENT '连接用户名称',
  `ds_password` varchar(20) DEFAULT NULL COMMENT '连接用户密码',
  `server_add` varchar(100) DEFAULT NULL COMMENT '连接地址',
  `port` int(11) DEFAULT NULL COMMENT '端口号',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建者ID',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `createdate` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `updatedate` date DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据源配置表';

-- ----------------------------
-- Records of t_datasource_config
-- ----------------------------

-- ----------------------------
-- Table structure for t_ftpfile
-- ----------------------------
DROP TABLE IF EXISTS `t_ftpfile`;
CREATE TABLE `t_ftpfile` (
  `uuid` varchar(32) NOT NULL COMMENT 'ID',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `filename` varchar(200) DEFAULT NULL COMMENT '文件名',
  `storepath` varchar(400) DEFAULT NULL COMMENT '存储路径',
  `storedate` timestamp NULL DEFAULT NULL COMMENT '存储日期',
  `lock_sign` bit(1) DEFAULT NULL COMMENT '锁标志',
  `datasource_id` varchar(32) DEFAULT NULL COMMENT '数据源',
  `datasource_name` varchar(50) DEFAULT NULL COMMENT '数据源名称',
  `resolve_sign` bit(1) DEFAULT NULL COMMENT '解析标志',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `createdate` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `updatedate` date DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FTP文件信息表';

-- ----------------------------
-- Records of t_ftpfile
-- ----------------------------

-- ----------------------------
-- Table structure for t_trigger_config
-- ----------------------------
DROP TABLE IF EXISTS `t_trigger_config`;
CREATE TABLE `t_trigger_config` (
  `uuid` varchar(32) NOT NULL COMMENT 'ID',
  `datasource_id` varchar(32) DEFAULT NULL COMMENT '触发源ID',
  `datasource_name` varchar(50) DEFAULT NULL COMMENT '触发源名称',
  `quartz_corn` varchar(10) DEFAULT NULL COMMENT '触发时间表达式',
  `Clazz` varchar(50) DEFAULT NULL COMMENT '触发java_class',
  `is_available` tinyint(1) DEFAULT NULL COMMENT '是否有效',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建者ID',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `createdate` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `updatedate` date DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='触发配置表';

-- ----------------------------
-- Records of t_trigger_config
-- ----------------------------

-- ----------------------------
-- Table structure for u_user
-- ----------------------------
DROP TABLE IF EXISTS `u_user`;
CREATE TABLE `u_user` (
  `uuid` varchar(32) NOT NULL COMMENT '用户ID',
  `s_dic_date_id` varchar(32) DEFAULT NULL COMMENT '用户类型(初使化字典数据ID)',
  `u_name` varchar(20) DEFAULT NULL COMMENT '用户名称',
  `account` varchar(50) DEFAULT NULL COMMENT '登录帐户',
  `pas` varchar(32) DEFAULT NULL COMMENT '登录密码',
  `salt` varchar(50) DEFAULT NULL COMMENT '盐',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `address` varchar(200) DEFAULT NULL COMMENT '注册地址',
  `email` varchar(200) DEFAULT NULL COMMENT '邮件地址',
  `wechar_no` varchar(50) DEFAULT NULL COMMENT '微信号',
  `qq_no` varchar(50) DEFAULT NULL COMMENT 'QQ号',
  `headshot` blob COMMENT '头像',
  `sign` int(14) DEFAULT '0' COMMENT '标记(值为0正常,1为删除)',
  `integral` float(14,2) DEFAULT NULL COMMENT '积分',
  `integral_s` int(14) DEFAULT NULL COMMENT '积分上线',
  `integral_e` int(14) DEFAULT NULL COMMENT '积分下线',
  `locked` int(11) DEFAULT '0' COMMENT '用户是否锁定(0未锁定,1为锁定)',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `QRcode` blob COMMENT '用户二维码图片',
  `medal_number` int(14) DEFAULT NULL COMMENT '勋章数',
  `u_describe` varchar(1000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `account_uq` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of u_user
-- ----------------------------
INSERT INTO `u_user` VALUES ('14eb20cf168e4dd7b1b3ddf37d8b3a31', null, 'jwd', '0016238', '21232F297A57A5A743894A0E4A801FC3', '99A446A4AEE8F225840B3AEA3EAEFB64', '18314496694', '', '', '', '', null, '0', null, null, null, '0', '2018-01-22 08:45:39', null, null, null);
INSERT INTO `u_user` VALUES ('b95ae3fdbc1844019abf5164c05371b1', null, 'test2', '0016231', 'E10ADC3949BA59ABBE56E057F20F883E', '80660E29103D525B694F45E34E23F498', '18311111111', '', '', '', '', null, '0', null, null, null, '1', '2018-05-07 16:39:00', null, null, null);
INSERT INTO `u_user` VALUES ('cd61481e32954d009d3b22f84d7b6711', null, 'test', 'test', '098F6BCD4621D373CADE4E832627B4F6', '05A671C66AEFEA124CC08B76EA6D30BB', '123456789', 'aa', 'aa@163.com', 'aa', '123', null, '0', null, null, null, '0', '2017-02-24 15:57:13', null, null, null);
INSERT INTO `u_user` VALUES ('d09fa7ec51a84b278b8ce10c0830e811', '842b0726234f429c98dc295c1e9f150a', 'admin', 'admin', '21232F297A57A5A743894A0E4A801FC3', 'F6FDFFE48C908DEB0F4C3BD36C032E72', '15974818487', '云南昆明', 'td2005hyyd@163.com', 'tengd123', '372458522', null, '0', null, null, null, '0', '2016-04-06 16:54:11', null, null, null);

-- ----------------------------
-- Table structure for wf_cc_order
-- ----------------------------
DROP TABLE IF EXISTS `wf_cc_order`;
CREATE TABLE `wf_cc_order` (
  `order_Id` varchar(100) DEFAULT NULL COMMENT '流程实例ID',
  `actor_Id` varchar(100) DEFAULT NULL COMMENT '参与者ID',
  `create_Time` varchar(50) DEFAULT NULL COMMENT '抄送时间',
  `finish_Time` varchar(50) DEFAULT NULL COMMENT '完成时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  KEY `IDX_CCORDER_ORDER` (`order_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抄送实例表';

-- ----------------------------
-- Records of wf_cc_order
-- ----------------------------

-- ----------------------------
-- Table structure for wf_hist_order
-- ----------------------------
DROP TABLE IF EXISTS `wf_hist_order`;
CREATE TABLE `wf_hist_order` (
  `id` varchar(100) NOT NULL COMMENT '主键ID',
  `process_Id` varchar(100) NOT NULL COMMENT '流程定义ID',
  `order_State` tinyint(1) NOT NULL COMMENT '状态',
  `creator` varchar(100) DEFAULT NULL COMMENT '发起人',
  `create_Time` varchar(50) NOT NULL COMMENT '发起时间',
  `end_Time` varchar(50) DEFAULT NULL COMMENT '完成时间',
  `expire_Time` varchar(50) DEFAULT NULL COMMENT '期望完成时间',
  `priority` tinyint(1) DEFAULT NULL COMMENT '优先级',
  `parent_Id` varchar(100) DEFAULT NULL COMMENT '父流程ID',
  `order_No` varchar(100) DEFAULT NULL COMMENT '流程实例编号',
  `variable` varchar(2000) DEFAULT NULL COMMENT '附属变量json存储',
  PRIMARY KEY (`id`),
  KEY `IDX_HIST_ORDER_PROCESSID` (`process_Id`),
  KEY `IDX_HIST_ORDER_NO` (`order_No`),
  KEY `FK_HIST_ORDER_PARENTID` (`parent_Id`),
  CONSTRAINT `FK_HIST_ORDER_PARENTID` FOREIGN KEY (`parent_Id`) REFERENCES `wf_hist_order` (`id`),
  CONSTRAINT `FK_HIST_ORDER_PROCESSID` FOREIGN KEY (`process_Id`) REFERENCES `wf_process` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史流程实例表';

-- ----------------------------
-- Records of wf_hist_order
-- ----------------------------

-- ----------------------------
-- Table structure for wf_hist_task
-- ----------------------------
DROP TABLE IF EXISTS `wf_hist_task`;
CREATE TABLE `wf_hist_task` (
  `id` varchar(100) NOT NULL COMMENT '主键ID',
  `order_Id` varchar(100) NOT NULL COMMENT '流程实例ID',
  `task_Name` varchar(100) NOT NULL COMMENT '任务名称',
  `display_Name` varchar(200) NOT NULL COMMENT '任务显示名称',
  `task_Type` tinyint(1) NOT NULL COMMENT '任务类型',
  `perform_Type` tinyint(1) DEFAULT NULL COMMENT '参与类型',
  `task_State` tinyint(1) NOT NULL COMMENT '任务状态',
  `operator` varchar(100) DEFAULT NULL COMMENT '任务处理人',
  `create_Time` varchar(50) NOT NULL COMMENT '任务创建时间',
  `finish_Time` varchar(50) DEFAULT NULL COMMENT '任务完成时间',
  `expire_Time` varchar(50) DEFAULT NULL COMMENT '任务期望完成时间',
  `action_Url` varchar(200) DEFAULT NULL COMMENT '任务处理url',
  `parent_Task_Id` varchar(100) DEFAULT NULL COMMENT '父任务ID',
  `variable` varchar(2000) DEFAULT NULL COMMENT '附属变量json存储',
  PRIMARY KEY (`id`),
  KEY `IDX_HIST_TASK_ORDER` (`order_Id`),
  KEY `IDX_HIST_TASK_TASKNAME` (`task_Name`),
  KEY `IDX_HIST_TASK_PARENTTASK` (`parent_Task_Id`),
  CONSTRAINT `FK_HIST_TASK_ORDERID` FOREIGN KEY (`order_Id`) REFERENCES `wf_hist_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史任务表';

-- ----------------------------
-- Records of wf_hist_task
-- ----------------------------

-- ----------------------------
-- Table structure for wf_hist_task_actor
-- ----------------------------
DROP TABLE IF EXISTS `wf_hist_task_actor`;
CREATE TABLE `wf_hist_task_actor` (
  `task_Id` varchar(100) NOT NULL COMMENT '任务ID',
  `actor_Id` varchar(100) NOT NULL COMMENT '参与者ID',
  KEY `IDX_HIST_TASKACTOR_TASK` (`task_Id`),
  CONSTRAINT `FK_HIST_TASKACTOR` FOREIGN KEY (`task_Id`) REFERENCES `wf_hist_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史任务参与者表';

-- ----------------------------
-- Records of wf_hist_task_actor
-- ----------------------------

-- ----------------------------
-- Table structure for wf_order
-- ----------------------------
DROP TABLE IF EXISTS `wf_order`;
CREATE TABLE `wf_order` (
  `id` varchar(100) NOT NULL COMMENT '主键ID',
  `parent_Id` varchar(100) DEFAULT NULL COMMENT '父流程ID',
  `process_Id` varchar(100) NOT NULL COMMENT '流程定义ID',
  `creator` varchar(100) DEFAULT NULL COMMENT '发起人',
  `create_Time` varchar(50) NOT NULL COMMENT '发起时间',
  `expire_Time` varchar(50) DEFAULT NULL COMMENT '期望完成时间',
  `last_Update_Time` varchar(50) DEFAULT NULL COMMENT '上次更新时间',
  `last_Updator` varchar(100) DEFAULT NULL COMMENT '上次更新人',
  `priority` tinyint(1) DEFAULT NULL COMMENT '优先级',
  `parent_Node_Name` varchar(100) DEFAULT NULL COMMENT '父流程依赖的节点名称',
  `order_No` varchar(100) DEFAULT NULL COMMENT '流程实例编号',
  `variable` varchar(2000) DEFAULT NULL COMMENT '附属变量json存储',
  `version` int(3) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`),
  KEY `IDX_ORDER_PROCESSID` (`process_Id`),
  KEY `IDX_ORDER_NO` (`order_No`),
  KEY `FK_ORDER_PARENTID` (`parent_Id`),
  CONSTRAINT `FK_ORDER_PARENTID` FOREIGN KEY (`parent_Id`) REFERENCES `wf_order` (`id`),
  CONSTRAINT `FK_ORDER_PROCESSID` FOREIGN KEY (`process_Id`) REFERENCES `wf_process` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程实例表';

-- ----------------------------
-- Records of wf_order
-- ----------------------------

-- ----------------------------
-- Table structure for wf_process
-- ----------------------------
DROP TABLE IF EXISTS `wf_process`;
CREATE TABLE `wf_process` (
  `id` varchar(100) NOT NULL COMMENT '主键ID',
  `name` varchar(100) DEFAULT NULL COMMENT '流程名称',
  `display_Name` varchar(200) DEFAULT NULL COMMENT '流程显示名称',
  `type` varchar(100) DEFAULT NULL COMMENT '流程类型',
  `instance_Url` varchar(200) DEFAULT NULL COMMENT '实例url',
  `state` tinyint(1) DEFAULT NULL COMMENT '流程是否可用',
  `content` longblob COMMENT '流程模型定义',
  `version` int(2) DEFAULT NULL COMMENT '版本',
  `create_Time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  KEY `IDX_PROCESS_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程定义表';

-- ----------------------------
-- Records of wf_process
-- ----------------------------
INSERT INTO `wf_process` VALUES ('4c49ab6ea89243a5b6ee244929d1743a', 'test_process', '测试流程', null, '', '1', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D226E6F223F3E0A3C70726F63657373206E616D653D22746573745F70726F636573732220646973706C61794E616D653D22E6B58BE8AF95E6B581E7A88B22203E0A3C7374617274206C61796F75743D2238392C3132352C35302C353022206E616D653D22737461727422203E0A3C7472616E736974696F6E206F66667365743D22302C2D31302220746F3D22726563743222206E616D653D22706174683622202F3E0A3C2F73746172743E0A3C7461736B206C61796F75743D223138302C3134332C3130302C353022206E616D653D2272656374322220646973706C61794E616D653D22E7BB8FE79086E5AEA1E6A0B822207461736B547970653D224D616A6F722220706572666F726D547970653D22414E5922203E0A3C7472616E736974696F6E206F66667365743D22302C2D31302220746F3D22726563743322206E616D653D22706174683722202F3E0A3C2F7461736B3E0A3C7461736B206C61796F75743D223336372C3135372C3130302C353022206E616D653D2272656374332220646973706C61794E616D653D22E680BBE7BB8FE79086E5AEA1E6A0B822207461736B547970653D224D616A6F722220706572666F726D547970653D22414E5922203E0A3C7472616E736974696F6E206F66667365743D22302C2D31302220746F3D22656E6422206E616D653D22706174683822202F3E0A3C7472616E736974696F6E206F66667365743D22302C2D31302220746F3D227265637432222020673D223333352C33353922206E616D653D2270617468392220646973706C61794E616D653D22E9A9B3E59B9E22202F3E0A3C2F7461736B3E0A3C656E64206C61796F75743D223531332C3136362C35302C353022206E616D653D22656E6422203E0A3C2F656E643E0A3C2F70726F636573733E, '0', '2018-07-23 20:24:40', null);

-- ----------------------------
-- Table structure for wf_surrogate
-- ----------------------------
DROP TABLE IF EXISTS `wf_surrogate`;
CREATE TABLE `wf_surrogate` (
  `id` varchar(100) NOT NULL COMMENT '主键ID',
  `process_Name` varchar(100) DEFAULT NULL COMMENT '流程名称',
  `operator` varchar(100) DEFAULT NULL COMMENT '授权人',
  `surrogate` varchar(100) DEFAULT NULL COMMENT '代理人',
  `odate` varchar(64) DEFAULT NULL COMMENT '操作时间',
  `sdate` varchar(64) DEFAULT NULL COMMENT '开始时间',
  `edate` varchar(64) DEFAULT NULL COMMENT '结束时间',
  `state` tinyint(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `IDX_SURROGATE_OPERATOR` (`operator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='委托代理表';

-- ----------------------------
-- Records of wf_surrogate
-- ----------------------------

-- ----------------------------
-- Table structure for wf_task
-- ----------------------------
DROP TABLE IF EXISTS `wf_task`;
CREATE TABLE `wf_task` (
  `id` varchar(100) NOT NULL COMMENT '主键ID',
  `order_Id` varchar(100) NOT NULL COMMENT '流程实例ID',
  `task_Name` varchar(100) NOT NULL COMMENT '任务名称',
  `display_Name` varchar(200) NOT NULL COMMENT '任务显示名称',
  `task_Type` tinyint(1) NOT NULL COMMENT '任务类型',
  `perform_Type` tinyint(1) DEFAULT NULL COMMENT '参与类型',
  `operator` varchar(100) DEFAULT NULL COMMENT '任务处理人',
  `create_Time` varchar(50) DEFAULT NULL COMMENT '任务创建时间',
  `finish_Time` varchar(50) DEFAULT NULL COMMENT '任务完成时间',
  `expire_Time` varchar(50) DEFAULT NULL COMMENT '任务期望完成时间',
  `action_Url` varchar(200) DEFAULT NULL COMMENT '任务处理的url',
  `parent_Task_Id` varchar(100) DEFAULT NULL COMMENT '父任务ID',
  `variable` varchar(2000) DEFAULT NULL COMMENT '附属变量json存储',
  `version` tinyint(1) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`),
  KEY `IDX_TASK_ORDER` (`order_Id`),
  KEY `IDX_TASK_TASKNAME` (`task_Name`),
  KEY `IDX_TASK_PARENTTASK` (`parent_Task_Id`),
  CONSTRAINT `FK_TASK_ORDERID` FOREIGN KEY (`order_Id`) REFERENCES `wf_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

-- ----------------------------
-- Records of wf_task
-- ----------------------------

-- ----------------------------
-- Table structure for wf_task_actor
-- ----------------------------
DROP TABLE IF EXISTS `wf_task_actor`;
CREATE TABLE `wf_task_actor` (
  `task_Id` varchar(100) NOT NULL COMMENT '任务ID',
  `actor_Id` varchar(100) NOT NULL COMMENT '参与者ID',
  KEY `IDX_TASKACTOR_TASK` (`task_Id`),
  CONSTRAINT `FK_TASK_ACTOR_TASKID` FOREIGN KEY (`task_Id`) REFERENCES `wf_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务参与者表';

-- ----------------------------
-- Records of wf_task_actor
-- ----------------------------
