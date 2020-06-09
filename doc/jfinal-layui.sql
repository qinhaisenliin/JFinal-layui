/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : jfinal-layui

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 03/01/2019 18:14:10
*/


-- ----------------------------
-- Table structure for data_dictionary
-- ----------------------------
DROP TABLE IF EXISTS data_dictionary;
CREATE TABLE data_dictionary  (
  id varchar(32)   COMMENT '主键',
  code varchar(255)  COMMENT '字典编号',
  name varchar(255)   COMMENT '字典名称',
  remark varchar(255) COMMENT '备注',
  order_num int(11)  DEFAULT 1 COMMENT '排序',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8  COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_dictionary
-- ----------------------------
INSERT INTO data_dictionary VALUES ('40288ae76812ae55016812bc5be9002c', 'logType', '日志类型', '系统日志，用于演示快速引用业务字典', 1);

-- ----------------------------
-- Table structure for data_dictionary_value
-- ----------------------------
DROP TABLE IF EXISTS data_dictionary_value;
CREATE TABLE data_dictionary_value  (
  id varchar(32)    COMMENT '主键',
  value varchar(255)  COMMENT '数据值',
  name varchar(255)   COMMENT '数据名称',
  order_num int(11) COMMENT '排序',
  remark varchar(255) COMMENT '备注',
  dictionary_code varchar(32)  COMMENT '字典编号',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8   COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_dictionary_value
-- ----------------------------
INSERT INTO data_dictionary_value VALUES ('40288ae76812ae55016812bcc5060037', '操作日志', '操作日志', 1, '字典编号：logType', 'logType');
INSERT INTO data_dictionary_value VALUES ('40288ae76812ae55016812bcfc740039', '数据日志', '数据日志', 2, '字典编号：logType', 'logType');
INSERT INTO data_dictionary_value VALUES ('40288ae76812ae55016812bd288b003b', '登录日志', '登录日志', 3, '字典编号：logType', 'logType');

-- ----------------------------
-- Table structure for file_uploaded
-- ----------------------------
DROP TABLE IF EXISTS file_uploaded;
CREATE TABLE file_uploaded  (
  id bigint(20),
  create_time datetime ,
  file_name varchar(200) ,
  file_size bigint(20) COMMENT 'KB',
  save_path varchar(300),
  object_id varchar(200),
  url varchar(255),
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8  COMMENT = '附件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_function
-- ----------------------------
DROP TABLE IF EXISTS sys_function;
CREATE TABLE sys_function  (
  id varchar(32)   COMMENT '主键',
  func_name varchar(80)   COMMENT '功能名称',
  is_stop int(11) DEFAULT 0 COMMENT '是否启用(0:是,1:否)',
  link_page text  COMMENT '功能url',
  parent_code varchar(40)  COMMENT '上级编号',
  parent_name varchar(100)   COMMENT '上级名称',
  func_type int(11)  DEFAULT 0 COMMENT '功能类型(0:菜单,1:按钮)',
  icon varchar(50)    COMMENT '图标',
  order_no int(11)  COMMENT '排序',
  descript varchar(255)  COMMENT '注释',
  spread int(11) DEFAULT 1 COMMENT '是否展开菜单(0:展开,1:不展开)',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8  COMMENT = '系统功能表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_function
-- ----------------------------
INSERT INTO sys_function VALUES ('data_dictionary', '业务字典', 0, '/portal/core/dictionary', 'sys_manager', '系统管理', 0, NULL, 5, NULL, 1);
INSERT INTO sys_function VALUES ('data_dictionary_add', '添加', 0, '/portal/core/dictionary/save', 'data_dictionary', '数据字典', 1, NULL, 1, NULL, 1);
INSERT INTO sys_function VALUES ('data_dictionary_delete', '删除', 0, '/portal/core/dictionary/delete', 'data_dictionary', '数据字典', 1, NULL, 3, NULL, 1);
INSERT INTO sys_function VALUES ('data_dictionary_update', '修改', 0, '/portal/core/dictionary/update', 'data_dictionary', '数据字典', 1, NULL, 2, NULL, 1);
INSERT INTO sys_function VALUES ('data_dictionary_value', '字典数据值', 0, NULL, 'data_dictionary', '数据字典', 1, NULL, 4, NULL, 1);
INSERT INTO sys_function VALUES ('data_dictionary_value_add', '添加', 0, '/portal/core/dictionary/value/add', 'data_dictionary_value', '字典数据值', 1, NULL, 5, NULL, 1);
INSERT INTO sys_function VALUES ('data_dictionary_value_delete', '删除', 0, '/portal/core/dictionary/value/delete', 'data_dictionary_value', '字典数据值', 1, NULL, 7, NULL, 1);
INSERT INTO sys_function VALUES ('data_dictionary_value_update', '修改', 0, '/portal/core/dictionary/update', 'data_dictionary_value', '字典数据值', 1, NULL, 6, NULL, 1);
INSERT INTO sys_function VALUES ('frame_main_view', '主页', 0, 'https://www.qinhaisenlin.com/user/reg/2', 'frame', 'JFinal极速开发企业应用管理系统', 0, 'layui-icon-home', 2, NULL, 1);
INSERT INTO sys_function VALUES ('pub_fileList', '附件列表', 0, '/portal/getFileList', 'sys', '网站后台管理', 0, 'layui-icon-read', 5, NULL, 1);
INSERT INTO sys_function VALUES ('sys', '网站后台管理', 0, NULL, 'frame', 'JFinal极速开发企业应用管理系统', 0, NULL, 1, NULL, 1);
INSERT INTO sys_function VALUES ('sys_druid', '性能监控', 0, '/assets/druid', 'sys', '网站后台管理', 0, 'layui-icon-engine', 3, NULL, 1);
INSERT INTO sys_function VALUES ('sys_echart', 'Echart图表', 0, '/portal/echart', 'sys', '网站后台管理', 0, 'layui-icon-chart', 2, NULL, 1);
INSERT INTO sys_function VALUES ('sys_func_add', '添加', 0, '/portal/core/sysFunc/save', 'sys_func_manager', '功能管理', 1, NULL, 1, NULL, 1);
INSERT INTO sys_function VALUES ('sys_func_delete', '删除', 0, '/portal/core/sysFunc/delete', 'sys_func_manager', '功能管理', 1, NULL, 3, NULL, 1);
INSERT INTO sys_function VALUES ('sys_func_manager', '功能管理', 0, '/portal/core/sysFunc', 'sys_manager', '系统管理', 0, 'layui-icon-wu', 1, NULL, 1);
INSERT INTO sys_function VALUES ('sys_func_update', '修改', 0, '/portal/core/sysFunc/update', 'sys_func_manager', '功能管理', 1, NULL, 2, NULL, 1);
INSERT INTO sys_function VALUES ('sys_log', '系统日志', 0, '/portal/core/sysLog', 'sys_manager', '系统管理', 0, NULL, 6, NULL, 1);
INSERT INTO sys_function VALUES ('sys_manager', '系统管理', 0, NULL, 'sys', '网站后台管理', 0, 'layui-icon-set', 1, NULL, 1);
INSERT INTO sys_function VALUES ('sys_org_add', '添加', 0, '/portal/core/sysOrg/add', 'sys_org_manager', '部门管理', 1, NULL, 1, NULL, 1);
INSERT INTO sys_function VALUES ('sys_org_delete', '删除', 0, '/portal/core/sysOrg/delete', 'sys_org_manager', '部门管理', 1, NULL, 3, NULL, 1);
INSERT INTO sys_function VALUES ('sys_org_manager', '部门管理', 0, '/portal/core/sysOrg', 'sys_manager', '系统管理', 0, 'layui-icon-wu', 4, NULL, 1);
INSERT INTO sys_function VALUES ('sys_org_update', '修改 ', 0, '/portal/core/sysOrg/update', 'sys_org_manager', '部门管理', 1, NULL, 2, NULL, 1);
INSERT INTO sys_function VALUES ('sys_pub_upload', '上传页面', 0, '/portal/toUpload', 'sys', '网站后台管理', 0, 'layui-icon-upload-drag', 4, NULL, 1);
INSERT INTO sys_function VALUES ('sys_role_add', '添加', 0, '/portal/core/sysRole/save', 'sys_role_manager', '角色管理', 1, NULL, 1, NULL, 1);
INSERT INTO sys_function VALUES ('sys_role_delete', '删除', 0, '/portal/core/sysRole/delete', 'sys_role_manager', '角色管理', 1, NULL, 3, NULL, 1);
INSERT INTO sys_function VALUES ('sys_role_manager', '角色管理', 0, '/portal/core/sysRole', 'sys_manager', '系统管理', 0, 'layui-icon-wu', 2, NULL, 1);
INSERT INTO sys_function VALUES ('sys_role_update', '修改', 0, '/portal/core/sysRole/update', 'sys_role_manager', '角色管理', 1, NULL, 2, NULL, 1);
INSERT INTO sys_function VALUES ('sys_user_add', '添加', 0, '/portal/core/sysUser/save', 'sys_user_manager', '用户管理', 1, NULL, 1, NULL, 1);
INSERT INTO sys_function VALUES ('sys_user_delete', '删除', 0, '/portal/core/sysUser/delete', 'sys_user_manager', '用户管理', 1, NULL, 3, NULL, 1);
INSERT INTO sys_function VALUES ('sys_user_manager', '用户管理', 0, '/portal/core/sysUser', 'sys_manager', '系统管理', 0, 'layui-icon-wu', 3, NULL, 1);
INSERT INTO sys_function VALUES ('sys_user_reset', '重置密码', 0, '/portal/core/sysUser/reset', 'sys_user_manager', '用户管理', 1, NULL, 5, NULL, 1);
INSERT INTO sys_function VALUES ('sys_user_role', '角色', 0, '/portal/core/sysUser/userRole', 'sys_user_manager', '用户管理', 1, NULL, 6, NULL, 1);
INSERT INTO sys_function VALUES ('sys_user_update', '修改', 0, '/portal/core/sysUser/update', 'sys_user_manager', '用户管理', 1, NULL, 2, NULL, 1);
INSERT INTO sys_function VALUES ('form_business_table', '测试表单', 0, '/portal/form/business/view_table', 'sys', '网站后台管理', 0, 'layui-icon-table', 7, NULL, 1);
INSERT INTO sys_function VALUES ('form_sys_tree', '表单分类树', 0, NULL, 'form_view_manager', '在线表单', 1, NULL, 6, NULL, 1);
INSERT INTO sys_function VALUES ('form_sys_tree_add', '添加', 0, '/portal/form/sysTree/save', 'form_sys_tree', '表单分类树', 1, NULL, 7, NULL, 1);
INSERT INTO sys_function VALUES ('form_sys_tree_delete', '删除', 0, '/portal/form/sysTree/delete', 'form_sys_tree', '表单分类树', 1, NULL, 9, NULL, 1);
INSERT INTO sys_function VALUES ('form_sys_tree_update', '修改', 0, '/portal/form/sysTree/update', 'form_sys_tree', '表单分类树', 1, NULL, 8, NULL, 1);
INSERT INTO sys_function VALUES ('form_view_add', '添加', 0, '/portal/form/view/save', 'form_view_manager', '在线表单', 1, NULL, 3, NULL, 1);
INSERT INTO sys_function VALUES ('form_view_delete', '删除', 0, '/portal/form/view/delete', 'form_view_manager', '在线表单', 1, NULL, 5, NULL, 1);
INSERT INTO sys_function VALUES ('form_view_manager', '在线表单', 0, '/portal/form/view', 'sys', '网站后台管理', 0, 'layui-icon-templeate-1', 6, NULL, 1);
INSERT INTO sys_function VALUES ('form_view_update', '修改', 0, '/portal/form/view/update', 'form_view_manager', '在线表单', 1, NULL, 4, NULL, 1);
-- 新增自定义sql菜单
INSERT INTO sys_function VALUES ('form_sql_add', '添加', 0, '/portal/form/sql/save', 'sys_cus_sql', '自定义SQL', 1, NULL, 1, NULL, 1);
INSERT INTO sys_function VALUES ('form_sql_delete', '删除', 0, '/portal/form/sql/delete', 'sys_cus_sql', '自定义SQL', 1, NULL, 3, NULL, 1);
INSERT INTO sys_function VALUES ('form_sql_update', '修改', 0, '/portal/form/sql/update', 'sys_cus_sql', '自定义SQL', 1, NULL, 2, NULL, 1);
INSERT INTO sys_function VALUES ('sql_sys_tree', 'sql分类树', 0, NULL, 'sys_cus_sql', '自定义SQL', 1, NULL, 4, NULL, 1);
INSERT INTO sys_function VALUES ('sql_sys_tree_add', '添加', 0, '/portal/form/sysTree/save', 'sql_sys_tree', 'sql分类树', 1, NULL, 5, NULL, 1);
INSERT INTO sys_function VALUES ('sql_sys_tree_delete', '删除', 0, '/portal/form/sysTree/delete', 'sql_sys_tree', 'sql分类树', 1, NULL, 7, NULL, 1);
INSERT INTO sys_function VALUES ('sql_sys_tree_update', '修改', 0, '/portal/form/sysTree/update', 'sql_sys_tree', 'sql分类树', 1, NULL, 6, NULL, 1);
INSERT INTO sys_function VALUES ('sys_cus_sql', '自定义SQL', 0, '/portal/form/sql', 'sys_manager', '系统管理', 0, NULL, 6, NULL, 1);
-- 代码器
INSERT INTO sys_function VALUES ('generator_manager', '代码生成器', 0, '/portal/generator/code', 'sys', '网站后台管理', 0, 'layui-icon-fonts-code', 2, NULL, 1);
-- 联级多选
INSERT INTO sys_function VALUES ('select_more_demo', '联级多选', 0, '/portal/go/common/demo/select', 'sys', '网站后台管理', 0, 'layui-icon-down', 60, NULL, 1);
-- 角色管理中权限分配的保存按钮
INSERT INTO sys_function VALUES ('sys_role_save_function', '权限分配按钮', 0, '/portal/core/sysRole/saveRoleFunction', 'sys_role_manager', '角色管理', 1, NULL, 4, NULL, 1);
-- 报表管理菜单
INSERT INTO sys_function VALUES ('ureport_course', '报表教程', 0, 'https://www.w3cschool.cn/ureport/ureport-dysp2ha7.html', 'ureport_manager', '报表管理', 0, NULL, 4, NULL, 1);
INSERT INTO sys_function VALUES ('ureport_designer', '报表设计', 0, '/ureport/designer', 'ureport_manager', '报表管理', 0, NULL, 3, NULL, 1);
INSERT INTO sys_function VALUES ('ureport_manager', '报表管理', 0, '/ureport/designer', 'sys', '网站后台管理', 0, 'layui-icon-app', 2, NULL, 1);
INSERT INTO sys_function VALUES ('ureport_preview', '报表预览', 0, NULL, 'ureport_manager', '报表管理', 0, NULL, 5, NULL, 1);
INSERT INTO sys_function VALUES ('ureport_preview_sys_log', '日志报表', 0, '/ureport/preview?_u=file:sysLog.ureport.xml', 'ureport_preview', '报表预览', 0, NULL, 4, NULL, 1);
INSERT INTO sys_function VALUES ('ureport_preview_user', '用户报表', 0, '/ureport/preview?_u=file:user.ureport.xml', 'ureport_preview', '报表预览', 0, NULL, 5, NULL, 1);
-- 可编辑表格
INSERT INTO sys_function VALUES ('table_edit', '可编辑表格', 0, '/portal/go/common/demo/layuiTableEdit', 'sys', '网站后台管理', 0, 'layui-icon-table', 50, NULL, 1);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS sys_log;
CREATE TABLE sys_log  (
  id varchar(32) ,
  url varchar(255) ,
  method_name varchar(255) ,
  data text ,
  create_time datetime ,
  user_code varchar(255)  ,
  remark varchar(500) ,
  ip varchar(255)  ,
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8   COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO sys_log VALUES ('40288ae768132a540168132f5aa50003', '/pub/login/submit', '登录', '{password=[6e5ee632b5020f7ec3b7f1007a8ab4a09f1c6a64410ffbdffc0198d7cd1e025220ffac783ac7884e20124fbc3d48fcb76d17be558605cf9fe4bddf6f7558927d0b3a63f330fd476836459ee0ce921456c97d95cb3789b2490fa1e4779f647180fd237a0085cb75a66aaf79378b31226562f2998b239b1608112cf1aca2c49424],verifyCode=[],returnUrl=[/],userCode=[admin]}', '2019-01-03 18:08:17', 'admin(管理员)', '登录日志', '192.168.10.103');
INSERT INTO sys_log VALUES ('40288ae768132a540168132f649b0005', '/portal/core/sysLog', '系统日志', '{}', '2019-01-03 18:08:20', 'admin(管理员)', '操作日志(系统管理)', '192.168.10.103');
INSERT INTO sys_log VALUES ('40288ae768132a5401681332f6360008', '/portal/getFileList', '附件列表', '{}', '2019-01-03 18:12:13', 'admin(管理员)', '操作日志(网站后台管理)', '192.168.10.103');
INSERT INTO sys_log VALUES ('40288ae768132a540168133305ca0009', '/portal/delete', '删除', '{}', '2019-01-03 18:12:17', 'admin(管理员)', '数据日志', '192.168.10.103');
INSERT INTO sys_log VALUES ('40288ae768132a5401681333080d000a', '/portal/getFileList', '附件列表', '{}', '2019-01-03 18:12:18', 'admin(管理员)', '操作日志(网站后台管理)', '192.168.10.103');
INSERT INTO sys_log VALUES ('40288ae768132a5401681333bd70000b', '/portal/core/sysUser', '用户管理', '{}', '2019-01-03 18:13:04', 'admin(管理员)', '操作日志(系统管理)', '192.168.10.103');

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS sys_org;
CREATE TABLE sys_org  (
  id varchar(32)  COMMENT '主键',
  org_code varchar(50)  COMMENT '部门编号和主键默认',
  parentid varchar(40)   COMMENT '上级部门编号',
  parentid_name varchar(50)  COMMENT '上级部门名称',
  org_name varchar(50)   COMMENT '部门名称',
  chief varchar(30)  COMMENT '部门负责人姓名',
  phone varchar(20)  COMMENT '部门负责人电话',
  mobile varchar(20)  COMMENT '部门负责人手机号',
  email varchar(40)  COMMENT '部门负责人邮件',
  descript text  COMMENT '描述',
  lev int(11)   COMMENT '级别',
  isstop int(11)   COMMENT '是否停用\r\n1：停用；\r\n0：启用；',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8  COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO sys_org VALUES ('40288ae767ee75f70167ee7bae0a0001', '40288ae767ee75f70167ee7bae0a0001', 'ff808081616e1efd01616e2c89af0000', '绘宇智能', '人事部', NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO sys_org VALUES ('40288ae767ee75f70167ee7c06ba0002', '40288ae767ee75f70167ee7c06ba0002', 'ff808081616e1efd01616e2c89af0000', '绘宇智能', '财务部', NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO sys_org VALUES ('40288ae767ee75f70167ee7c94220003', '40288ae767ee75f70167ee7c94220003', 'ff80808161e4629f0161e49ac1d30001', '系统集成部', '研发部', NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO sys_org VALUES ('40288ae767ee75f70167ee7cb5120004', '40288ae767ee75f70167ee7cb5120004', 'ff80808161e4629f0161e49ac1d30001', '系统集成部', '项目部', NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO sys_org VALUES ('ff808081616e1efd01616e2c89af0000', 'ff808081616e1efd01616e2c89af0000', 'sys', '组织机构', '绘宇智能', '一木森', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO sys_org VALUES ('ff80808161e4629f0161e49ac1d30001', 'ff80808161e4629f0161e49ac1d30001', 'ff808081616e1efd01616e2c89af0000', '绘宇智能', '系统集成部', NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO sys_org VALUES ('sys', 'sys', '', '', '组织机构', '系统管理员', NULL, NULL, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role  (
  id varchar(32),
  descript varchar(200) ,
  is_stop int(11) ,
  orgid varchar(100) ,
  parent_id varchar(100) ,
  role_code varchar(100),
  role_name varchar(100),
  user_code varchar(100) ,
  visit_view varchar(100) ,
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO sys_role VALUES ('40288ae7677c6ab801677c6c30ac0001', NULL, 1, NULL, 'superadmin', 'admin', '管理员', NULL, NULL);
INSERT INTO sys_role VALUES ('40288ae767ab41450167ab8d9a1f0001', NULL, 1, NULL, 'admin', 'zjl', '总经理', NULL, NULL);
INSERT INTO sys_role VALUES ('40288ae767afdfee0167afe17c9c0001', NULL, 1, NULL, 'superadmin', 'xzb', '行政部', 'superadmin', NULL);
INSERT INTO sys_role VALUES ('40288ae767afdfee0167afe1c8c00002', NULL, 1, NULL, 'superadmin', 'jsb', '技术部', 'superadmin', NULL);
INSERT INTO sys_role VALUES ('40288ae767afdfee0167afe225390003', NULL, 1, NULL, 'jsb', 'yf', '研发部', 'superadmin', NULL);
INSERT INTO sys_role VALUES ('40288ae767afdfee0167afe269e80004', NULL, 1, NULL, 'jsb', 'xmb', '项目部', 'superadmin', NULL);
INSERT INTO sys_role VALUES ('40288ae767afdfee0167afe2e1410005', NULL, 1, NULL, 'yf', 'pt', '平台研发', 'superadmin', NULL);
INSERT INTO sys_role VALUES ('40288ae767afdfee0167afe33a480006', NULL, 1, NULL, 'yf', 'gis', 'gis研发', 'superadmin', NULL);
INSERT INTO sys_role VALUES ('40288ae767ed898c0167ed8c1e110001', NULL, 1, NULL, 'admin', 'admin3', '人事部', 'admin', NULL);
INSERT INTO sys_role VALUES ('40288ae767ee75f70167ee7fe5720005', NULL, 1, NULL, 'admin', 'xtjcb', '系统集成部', 'admin', NULL);
INSERT INTO sys_role VALUES ('40288ae767eeb06b0167eeb185410001', NULL, 1, NULL, 'xtjcb', 'yfb', '研发部', 'admin', NULL);
INSERT INTO sys_role VALUES ('ff80808161f5012f0161f53666ef0000', '管理系统所有角色', 1, 'ff808081616e1efd01616e2c89af0000', 'sys', 'superadmin', '超级管理员', 'admin', NULL);

-- ----------------------------
-- Table structure for sys_role_function
-- ----------------------------
DROP TABLE IF EXISTS sys_role_function;
CREATE TABLE sys_role_function  (
  id varchar(200) ,
  function_id varchar(100),
  role_code varchar(50),
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8  COMMENT = '角色功能关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_function
-- ----------------------------
INSERT INTO sys_role_function VALUES ('admin-data_dictionary', 'data_dictionary', 'admin');
INSERT INTO sys_role_function VALUES ('admin-data_dictionary_add', 'data_dictionary_add', 'admin');
INSERT INTO sys_role_function VALUES ('admin-data_dictionary_delete', 'data_dictionary_delete', 'admin');
INSERT INTO sys_role_function VALUES ('admin-data_dictionary_update', 'data_dictionary_update', 'admin');
INSERT INTO sys_role_function VALUES ('admin-data_dictionary_value', 'data_dictionary_value', 'admin');
INSERT INTO sys_role_function VALUES ('admin-data_dictionary_value_add', 'data_dictionary_value_add', 'admin');
INSERT INTO sys_role_function VALUES ('admin-data_dictionary_value_delete', 'data_dictionary_value_delete', 'admin');
INSERT INTO sys_role_function VALUES ('admin-data_dictionary_value_update', 'data_dictionary_value_update', 'admin');
INSERT INTO sys_role_function VALUES ('admin-frame', 'frame', 'admin');
INSERT INTO sys_role_function VALUES ('admin-pub_fileList', 'pub_fileList', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys', 'sys', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_echart', 'sys_echart', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_func_add', 'sys_func_add', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_func_delete', 'sys_func_delete', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_func_manager', 'sys_func_manager', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_func_update', 'sys_func_update', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_log', 'sys_log', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_manager', 'sys_manager', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_org_add', 'sys_org_add', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_org_delete', 'sys_org_delete', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_org_manager', 'sys_org_manager', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_org_update', 'sys_org_update', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_pub_upload', 'sys_pub_upload', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_role_add', 'sys_role_add', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_role_delete', 'sys_role_delete', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_role_manager', 'sys_role_manager', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_role_update', 'sys_role_update', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_user_add', 'sys_user_add', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_user_delete', 'sys_user_delete', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_user_manager', 'sys_user_manager', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_user_reset', 'sys_user_reset', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_user_role', 'sys_user_role', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_user_update', 'sys_user_update', 'admin');
INSERT INTO sys_role_function VALUES ('admin3-frame', 'frame', 'admin3');
INSERT INTO sys_role_function VALUES ('admin3-sys', 'sys', 'admin3');
INSERT INTO sys_role_function VALUES ('admin3-sys_func_add', 'sys_func_add', 'admin3');
INSERT INTO sys_role_function VALUES ('admin3-sys_func_delete', 'sys_func_delete', 'admin3');
INSERT INTO sys_role_function VALUES ('admin3-sys_func_manager', 'sys_func_manager', 'admin3');
INSERT INTO sys_role_function VALUES ('admin3-sys_func_update', 'sys_func_update', 'admin3');
INSERT INTO sys_role_function VALUES ('admin3-sys_manager', 'sys_manager', 'admin3');
INSERT INTO sys_role_function VALUES ('jsb_frame', 'frame', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_frame_main_view', 'frame_main_view', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys', 'sys', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_druid', 'sys_druid', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_echart', 'sys_echart', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_func_add', 'sys_func_add', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_func_delete', 'sys_func_delete', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_func_manager', 'sys_func_manager', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_func_update', 'sys_func_update', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_manager', 'sys_manager', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_org_add', 'sys_org_add', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_org_delete', 'sys_org_delete', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_org_manager', 'sys_org_manager', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_org_update', 'sys_org_update', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_role_add', 'sys_role_add', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_role_delete', 'sys_role_delete', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_role_manager', 'sys_role_manager', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_role_update', 'sys_role_update', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_user_add', 'sys_user_add', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_user_delete', 'sys_user_delete', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_user_manager', 'sys_user_manager', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_user_reset', 'sys_user_reset', 'jsb');
INSERT INTO sys_role_function VALUES ('jsb_sys_user_update', 'sys_user_update', 'jsb');
INSERT INTO sys_role_function VALUES ('pt-frame', 'frame', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys', 'sys', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_func_add', 'sys_func_add', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_func_delete', 'sys_func_delete', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_func_manager', 'sys_func_manager', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_func_update', 'sys_func_update', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_manager', 'sys_manager', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_role_add', 'sys_role_add', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_role_delete', 'sys_role_delete', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_role_manager', 'sys_role_manager', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_role_update', 'sys_role_update', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_user_add', 'sys_user_add', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_user_delete', 'sys_user_delete', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_user_manager', 'sys_user_manager', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_user_reset', 'sys_user_reset', 'pt');
INSERT INTO sys_role_function VALUES ('pt-sys_user_update', 'sys_user_update', 'pt');
INSERT INTO sys_role_function VALUES ('superadmin-frame', 'frame', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-frame_main_view', 'frame_main_view', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys', 'sys', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_druid', 'sys_druid', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_echart', 'sys_echart', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_func_add', 'sys_func_add', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_func_delete', 'sys_func_delete', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_func_manager', 'sys_func_manager', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_func_update', 'sys_func_update', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_manager', 'sys_manager', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_org_add', 'sys_org_add', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_org_delete', 'sys_org_delete', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_org_manager', 'sys_org_manager', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_org_update', 'sys_org_update', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_role_add', 'sys_role_add', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_role_delete', 'sys_role_delete', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_role_manager', 'sys_role_manager', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_role_update', 'sys_role_update', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_user_add', 'sys_user_add', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_user_delete', 'sys_user_delete', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_user_manager', 'sys_user_manager', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_user_reset', 'sys_user_reset', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin-sys_user_update', 'sys_user_update', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_data_dictionary', 'data_dictionary', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_data_dictionary_add', 'data_dictionary_add', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_data_dictionary_delete', 'data_dictionary_delete', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_data_dictionary_update', 'data_dictionary_update', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_data_dictionary_value', 'data_dictionary_value', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_data_dictionary_value_add', 'data_dictionary_value_add', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_data_dictionary_value_delete', 'data_dictionary_value_delete', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_data_dictionary_value_update', 'data_dictionary_value_update', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_pub_fileList', 'pub_fileList', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_sys_log', 'sys_log', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_sys_pub_upload', 'sys_pub_upload', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_sys_user_role', 'sys_user_role', 'superadmin');
INSERT INTO sys_role_function VALUES ('sys_data_dictionary', 'data_dictionary', 'sys');
INSERT INTO sys_role_function VALUES ('sys_data_dictionary_add', 'data_dictionary_add', 'sys');
INSERT INTO sys_role_function VALUES ('sys_data_dictionary_delete', 'data_dictionary_delete', 'sys');
INSERT INTO sys_role_function VALUES ('sys_data_dictionary_update', 'data_dictionary_update', 'sys');
INSERT INTO sys_role_function VALUES ('sys_data_dictionary_value', 'data_dictionary_value', 'sys');
INSERT INTO sys_role_function VALUES ('sys_data_dictionary_value_add', 'data_dictionary_value_add', 'sys');
INSERT INTO sys_role_function VALUES ('sys_data_dictionary_value_delete', 'data_dictionary_value_delete', 'sys');
INSERT INTO sys_role_function VALUES ('sys_data_dictionary_value_update', 'data_dictionary_value_update', 'sys');
INSERT INTO sys_role_function VALUES ('sys_frame', 'frame', 'sys');
INSERT INTO sys_role_function VALUES ('sys_frame_main_view', 'frame_main_view', 'sys');
INSERT INTO sys_role_function VALUES ('sys_pub_fileList', 'pub_fileList', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys', 'sys', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_druid', 'sys_druid', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_echart', 'sys_echart', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_func_add', 'sys_func_add', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_func_delete', 'sys_func_delete', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_func_manager', 'sys_func_manager', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_func_update', 'sys_func_update', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_log', 'sys_log', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_manager', 'sys_manager', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_org_add', 'sys_org_add', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_org_delete', 'sys_org_delete', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_org_manager', 'sys_org_manager', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_org_update', 'sys_org_update', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_pub_upload', 'sys_pub_upload', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_role_add', 'sys_role_add', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_role_delete', 'sys_role_delete', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_role_manager', 'sys_role_manager', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_role_update', 'sys_role_update', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_user_add', 'sys_user_add', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_user_delete', 'sys_user_delete', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_user_manager', 'sys_user_manager', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_user_reset', 'sys_user_reset', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_user_role', 'sys_user_role', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_user_update', 'sys_user_update', 'sys');
INSERT INTO sys_role_function VALUES ('xmb_frame', 'frame', 'xmb');
INSERT INTO sys_role_function VALUES ('xmb_sys', 'sys', 'xmb');
INSERT INTO sys_role_function VALUES ('xmb_sys_druid', 'sys_druid', 'xmb');
INSERT INTO sys_role_function VALUES ('xmb_sys_echart', 'sys_echart', 'xmb');
INSERT INTO sys_role_function VALUES ('xtjcb-frame', 'frame', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys', 'sys', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_func_add', 'sys_func_add', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_func_delete', 'sys_func_delete', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_func_manager', 'sys_func_manager', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_func_update', 'sys_func_update', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_manager', 'sys_manager', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_org_add', 'sys_org_add', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_org_delete', 'sys_org_delete', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_org_manager', 'sys_org_manager', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_org_update', 'sys_org_update', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_role_add', 'sys_role_add', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_role_delete', 'sys_role_delete', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_role_manager', 'sys_role_manager', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_role_update', 'sys_role_update', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_user_add', 'sys_user_add', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_user_delete', 'sys_user_delete', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_user_manager', 'sys_user_manager', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_user_reset', 'sys_user_reset', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_user_role', 'sys_user_role', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xtjcb-sys_user_update', 'sys_user_update', 'xtjcb');
INSERT INTO sys_role_function VALUES ('xzb_frame', 'frame', 'xzb');
INSERT INTO sys_role_function VALUES ('xzb_sys', 'sys', 'xzb');
INSERT INTO sys_role_function VALUES ('xzb_sys_manager', 'sys_manager', 'xzb');
INSERT INTO sys_role_function VALUES ('xzb_sys_user_add', 'sys_user_add', 'xzb');
INSERT INTO sys_role_function VALUES ('xzb_sys_user_delete', 'sys_user_delete', 'xzb');
INSERT INTO sys_role_function VALUES ('xzb_sys_user_manager', 'sys_user_manager', 'xzb');
INSERT INTO sys_role_function VALUES ('xzb_sys_user_reset', 'sys_user_reset', 'xzb');
INSERT INTO sys_role_function VALUES ('xzb_sys_user_update', 'sys_user_update', 'xzb');
INSERT INTO sys_role_function VALUES ('yf_frame', 'frame', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys', 'sys', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_func_add', 'sys_func_add', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_func_delete', 'sys_func_delete', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_func_manager', 'sys_func_manager', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_func_update', 'sys_func_update', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_manager', 'sys_manager', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_org_add', 'sys_org_add', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_org_delete', 'sys_org_delete', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_org_manager', 'sys_org_manager', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_org_update', 'sys_org_update', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_role_add', 'sys_role_add', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_role_delete', 'sys_role_delete', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_role_manager', 'sys_role_manager', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_role_update', 'sys_role_update', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_user_add', 'sys_user_add', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_user_delete', 'sys_user_delete', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_user_manager', 'sys_user_manager', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_user_reset', 'sys_user_reset', 'yf');
INSERT INTO sys_role_function VALUES ('yf_sys_user_update', 'sys_user_update', 'yf');
INSERT INTO sys_role_function VALUES ('zjl_frame', 'frame', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys', 'sys', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_func_add', 'sys_func_add', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_func_delete', 'sys_func_delete', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_func_manager', 'sys_func_manager', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_func_update', 'sys_func_update', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_manager', 'sys_manager', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_org_add', 'sys_org_add', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_org_delete', 'sys_org_delete', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_org_manager', 'sys_org_manager', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_org_update', 'sys_org_update', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_role_add', 'sys_role_add', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_role_delete', 'sys_role_delete', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_role_manager', 'sys_role_manager', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_role_update', 'sys_role_update', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_user_add', 'sys_user_add', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_user_delete', 'sys_user_delete', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_user_manager', 'sys_user_manager', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_user_reset', 'sys_user_reset', 'zjl');
INSERT INTO sys_role_function VALUES ('zjl_sys_user_update', 'sys_user_update', 'zjl');
INSERT INTO sys_role_function VALUES ('admin-form_business_table', 'form_business_table', 'admin');
INSERT INTO sys_role_function VALUES ('admin-form_sys_tree', 'form_sys_tree', 'admin');
INSERT INTO sys_role_function VALUES ('admin-form_sys_tree_add', 'form_sys_tree_add', 'admin');
INSERT INTO sys_role_function VALUES ('admin-form_sys_tree_delete', 'form_sys_tree_delete', 'admin');
INSERT INTO sys_role_function VALUES ('admin-form_sys_tree_update', 'form_sys_tree_update', 'admin');
INSERT INTO sys_role_function VALUES ('admin-form_view_add', 'form_view_add', 'admin');
INSERT INTO sys_role_function VALUES ('admin-form_view_delete', 'form_view_delete', 'admin');
INSERT INTO sys_role_function VALUES ('admin-form_view_manager', 'form_view_manager', 'admin');
INSERT INTO sys_role_function VALUES ('admin-form_view_update', 'form_view_update', 'admin');
INSERT INTO sys_role_function VALUES ('superadmin_form_business_table', 'form_business_table', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_form_sys_tree', 'form_sys_tree', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_form_sys_tree_add', 'form_sys_tree_add', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_form_sys_tree_delete', 'form_sys_tree_delete', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_form_sys_tree_update', 'form_sys_tree_update', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_form_view_add', 'form_view_add', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_form_view_delete', 'form_view_delete', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_form_view_manager', 'form_view_manager', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_form_view_update', 'form_view_update', 'superadmin');
INSERT INTO sys_role_function VALUES ('sys_form_business_table', 'form_business_table', 'sys');
INSERT INTO sys_role_function VALUES ('sys_form_sys_tree', 'form_sys_tree', 'sys');
INSERT INTO sys_role_function VALUES ('sys_form_sys_tree_add', 'form_sys_tree_add', 'sys');
INSERT INTO sys_role_function VALUES ('sys_form_sys_tree_delete', 'form_sys_tree_delete', 'sys');
INSERT INTO sys_role_function VALUES ('sys_form_sys_tree_update', 'form_sys_tree_update', 'sys');
INSERT INTO sys_role_function VALUES ('sys_form_view_add', 'form_view_add', 'sys');
INSERT INTO sys_role_function VALUES ('sys_form_view_delete', 'form_view_delete', 'sys');
INSERT INTO sys_role_function VALUES ('sys_form_view_manager', 'form_view_manager', 'sys');
INSERT INTO sys_role_function VALUES ('sys_form_view_update', 'form_view_update', 'sys');
-- 自定义SQL权限
INSERT INTO sys_role_function VALUES ('admin-form_sql_add', 'form_sql_add', 'admin');
INSERT INTO sys_role_function VALUES ('admin-form_sql_delete', 'form_sql_delete', 'admin');
INSERT INTO sys_role_function VALUES ('admin-form_sql_update', 'form_sql_update', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sql_sys_tree', 'sql_sys_tree', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sql_sys_tree_add', 'sql_sys_tree_add', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sql_sys_tree_delete', 'sql_sys_tree_delete', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sql_sys_tree_update', 'sql_sys_tree_update', 'admin');
INSERT INTO sys_role_function VALUES ('admin-sys_cus_sql', 'sys_cus_sql', 'admin');
INSERT INTO sys_role_function VALUES ('superadmin_form_sql_add', 'form_sql_add', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_form_sql_delete', 'form_sql_delete', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_form_sql_update', 'form_sql_update', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_sql_sys_tree', 'sql_sys_tree', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_sql_sys_tree_add', 'sql_sys_tree_add', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_sql_sys_tree_delete', 'sql_sys_tree_delete', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_sql_sys_tree_update', 'sql_sys_tree_update', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_sys_cus_sql', 'sys_cus_sql', 'superadmin');
INSERT INTO sys_role_function VALUES ('sys_form_sql_add', 'form_sql_add', 'sys');
INSERT INTO sys_role_function VALUES ('sys_form_sql_delete', 'form_sql_delete', 'sys');
INSERT INTO sys_role_function VALUES ('sys_form_sql_update', 'form_sql_update', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sql_sys_tree', 'sql_sys_tree', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sql_sys_tree_add', 'sql_sys_tree_add', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sql_sys_tree_delete', 'sql_sys_tree_delete', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sql_sys_tree_update', 'sql_sys_tree_update', 'sys');
INSERT INTO sys_role_function VALUES ('sys_sys_cus_sql', 'sys_cus_sql', 'sys');
-- 代码器
INSERT INTO sys_role_function VALUES ('admin-generator_manager', 'generator_manager', 'admin');
INSERT INTO sys_role_function VALUES ('superadmin_generator_manager', 'generator_manager', 'superadmin');
INSERT INTO sys_role_function VALUES ('sys_generator_manager', 'generator_manager', 'sys');
-- 联级多选
INSERT INTO sys_role_function VALUES ('admin-select_more_demo', 'select_more_demo', 'admin');
INSERT INTO sys_role_function VALUES ('superadmin_select_more_demo', 'select_more_demo', 'superadmin');
INSERT INTO sys_role_function VALUES ('sys_select_more_demo', 'select_more_demo', 'sys');
-- 报表管理
INSERT INTO sys_role_function VALUES ('admin-table_edit', 'table_edit', 'admin');
INSERT INTO sys_role_function VALUES ('admin-ureport_course', 'ureport_course', 'admin');
INSERT INTO sys_role_function VALUES ('admin-ureport_designer', 'ureport_designer', 'admin');
INSERT INTO sys_role_function VALUES ('admin-ureport_manager', 'ureport_manager', 'admin');
INSERT INTO sys_role_function VALUES ('admin-ureport_preview', 'ureport_preview', 'admin');
INSERT INTO sys_role_function VALUES ('admin-ureport_preview_sys_log', 'ureport_preview_sys_log', 'admin');
INSERT INTO sys_role_function VALUES ('admin-ureport_preview_user', 'ureport_preview_user', 'admin');
INSERT INTO sys_role_function VALUES ('superadmin_table_edit', 'table_edit', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_ureport_course', 'ureport_course', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_ureport_designer', 'ureport_designer', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_ureport_manager', 'ureport_manager', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_ureport_preview', 'ureport_preview', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_ureport_preview_sys_log', 'ureport_preview_sys_log', 'superadmin');
INSERT INTO sys_role_function VALUES ('superadmin_ureport_preview_user', 'ureport_preview_user', 'superadmin');
INSERT INTO sys_role_function VALUES ('sys_table_edit', 'table_edit', 'sys');
INSERT INTO sys_role_function VALUES ('sys_ureport_course', 'ureport_course', 'sys');
INSERT INTO sys_role_function VALUES ('sys_ureport_designer', 'ureport_designer', 'sys');
INSERT INTO sys_role_function VALUES ('sys_ureport_manager', 'ureport_manager', 'sys');
INSERT INTO sys_role_function VALUES ('sys_ureport_preview', 'ureport_preview', 'sys');
INSERT INTO sys_role_function VALUES ('sys_ureport_preview_sys_log', 'ureport_preview_sys_log', 'sys');
INSERT INTO sys_role_function VALUES ('sys_ureport_preview_user', 'ureport_preview_user', 'sys');
-- 分配按钮权限
INSERT INTO sys_role_function VALUES ('superadmin_sys_role_save_function', 'sys_role_save_function', 'superadmin');
INSERT INTO sys_role_function VALUES ('sys_sys_role_save_function', 'sys_role_save_function', 'sys');
INSERT INTO sys_role_function VALUES ('admin_sys_role_save_function', 'sys_role_save_function', 'admin');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user  (
  id varchar(20) COMMENT '主键',
  user_code varchar(20)  COMMENT '用户编号默认跟ID一样',
  user_name varchar(20)  COMMENT '用户名称',
  passwd varchar(100)  COMMENT '密码',
  org_id varchar(50)  COMMENT '部门编号',
  post varchar(50)  COMMENT '职务',
  sex int(11)   COMMENT '1;男,0女',
  tel varchar(40)   COMMENT '电话',
  mobile varchar(30)  COMMENT '手机号码',
  email varchar(40)   COMMENT 'EMAIL',
  address varchar(100)  COMMENT '家庭地址',
  allow_login int(11)  COMMENT '允许登录',
  deleted int(11)  DEFAULT 0 COMMENT '删除操作（1：删除未审核）',
  allow_login_time datetime   COMMENT '允许登录时间或最后登录时间',
  failure_number int(11)   COMMENT '登录错误次数',
  PRIMARY KEY (id) USING BTREE,
  INDEX index_1_sys_user_orgid(org_id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8  COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO sys_user VALUES ('admin', 'admin', '管理员', 'E10ADC3949BA59ABBE56E057F20F883E', 'ff808081616e1efd01616e2c89af0000', NULL, 1, NULL, NULL, NULL, NULL, 0, 0, '2019-01-03 18:08:17', 0);
INSERT INTO sys_user VALUES ('superadmin', 'superadmin', '超级管理员', 'E10ADC3949BA59ABBE56E057F20F883E', 'ff808081616e1efd01616e2c89af0000', NULL, 1, NULL, NULL, NULL, NULL, 0, 0, '2019-01-02 18:12:29', 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role  (
  id varchar(200),
  role_code varchar(50) ,
  user_code varchar(100),
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '用户角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO sys_user_role VALUES ('13-superadmin', '13', 'superadmin');
INSERT INTO sys_user_role VALUES ('admin-admin', 'admin', 'admin');
INSERT INTO sys_user_role VALUES ('admin3-admin', 'admin3', 'admin');
INSERT INTO sys_user_role VALUES ('jsb-123', 'jsb', '123');
INSERT INTO sys_user_role VALUES ('qinhaisenlin-admin', 'admin', 'qinhaisenlin');
INSERT INTO sys_user_role VALUES ('superadmin-superadmin', 'superadmin', 'superadmin');
INSERT INTO sys_user_role VALUES ('xmb-linwei', 'xmb', 'linwei');
INSERT INTO sys_user_role VALUES ('xtjcb-admin', 'xtjcb', 'admin');
INSERT INTO sys_user_role VALUES ('xzb-123', 'xzb', '123');
INSERT INTO sys_user_role VALUES ('xzb-linwei', 'xzb', 'linwei');
INSERT INTO sys_user_role VALUES ('xzb-qweq', 'xzb', 'qweq');
INSERT INTO sys_user_role VALUES ('yfb-admin', 'yfb', 'admin');
INSERT INTO sys_user_role VALUES ('zjl-linwei', 'zjl', 'linwei');
INSERT INTO sys_user_role VALUES ('zjl-qweq', 'zjl', 'qweq');   

DROP TABLE IF EXISTS form_view;
CREATE TABLE form_view  (
  id varchar(64) CHARACTER SET utf8  NOT NULL,
  tree_id varchar(64) CHARACTER SET utf8  NOT NULL,
  name varchar(255) CHARACTER SET utf8  NOT NULL,
  code varchar(255) CHARACTER SET utf8  NOT NULL,
  status varchar(50) CHARACTER SET utf8  NOT NULL,
  template_view text CHARACTER SET utf8  NOT NULL,
  create_time datetime  DEFAULT NULL,
  update_time datetime  DEFAULT NULL,
  descp varchar(255) CHARACTER SET utf8  NULL DEFAULT NULL,
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8  COMMENT = '在线表单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of form_view
-- ----------------------------
INSERT INTO form_view VALUES ('402880fa6a61eb68016a620b0dba0079', '107400000000002215', '添加页', 'view_add', 'DEPLOYED', '\n#@layoutT(\"添加页\")\n#define main()\n	<form action=\"javascript:void(0)\" class=\"layui-form layui-form-pane f-form\" method=\"post\" lay-filter=\'saveForm\'>	\n	         <input type=\"hidden\" class=\"layui-input\" name=\"object\" value=\"sys_tree\"/>\n		<input type=\"hidden\" class=\"layui-input\" name=\"id\" value=\"\"/>\n		<input type=\"hidden\" class=\"layui-input\" name=\"parent_id\" value=\"\"/>\n		\n		<div class=\"layui-row layui-col-space1 task-row\">\n		#@colStart(\"所属分类\",12)\n			<input type=\"text\" name=\"type\" value=\"form\" class=\"layui-input layui-disabled\" lay-verType=\'tips\'lay-verify=\"required|\" required maxlength=\"50\" placeHolder=\"必填\" readonly=\"readonly\" />\n		#@colEnd()\n		</div>\n		\n		<div class=\"layui-row layui-col-space1 task-row\">\n		#@colStart(\"名称\",12)\n			<input type=\"text\" name=\"name\"  value=\"\" class=\"layui-input\"  lay-verType=\'tips\' lay-verify=\"required|\" maxlength=\"50\" placeHolder=\"必填\" required />\n		#@colEnd()\n	 	</div>	\n		\n		<div class=\"layui-row layui-col-space1 task-row\">\n		#@colStart(\"序号\",12)\n			<input type=\"number\" name=\"order_no\" value=\"1\" class=\"layui-input\"/>\n		#@colEnd()\n		</div>\n	\n		</div>\n		\n		#@submitButton()\n	</form>\n	\n#end\n\n<!-- 截取url参数 -->\n#define js()\n<script type=\"text/javascript\">\n	$(function(){\n		var searchURL=window.location.search;\n		searchURL = searchURL.substring(1, searchURL.length);\n		var params=searchURL.split(\"&\");\n		var p1 = params[0].split(\"=\")[1];\n		var p2 = params[1].split(\"=\")[1];\n		$(\'input[name=\"parent_id\"]\').val(p1);\n		$(\'input[name=\"type\"]\').val(p2);\n		\n	});     \n</script>\n#end\n\n<!-- 保存数据 -->\n#define layuiFunc()	\n	var saveUrl=\"#(path)/portal/form/business/save\";\n#end\n', '2019-04-28 11:44:13', '2019-04-28 17:59:47', '测试添加页面');
INSERT INTO form_view VALUES ('402880fa6a62f77b016a62fd9dad005d', '107400000000002215', '修改页', 'view_update', 'DEPLOYED', '\n#@layoutT(\"修改页\")\n#define main()\n\n	<form action=\"javascript:void(0)\" class=\"layui-form layui-form-pane f-form\" method=\"post\" lay-filter=\'saveForm\'>\n	        <input type=\"hidden\" class=\"layui-input\" name=\"object\" value=\"sys_tree\"/>\n		<input type=\"hidden\" class=\"layui-input\" name=\"id\" value=\"\"/>\n		<input type=\"hidden\" class=\"layui-input\" name=\"parent_id\" value=\"\"/>\n		\n		<div class=\"layui-row layui-col-space1 task-row\">\n		#@colStart(\"所属分类\",12)\n			<input type=\"text\" name=\"type\" value=\"\" class=\"layui-input layui-disabled\" lay-verType=\'tips\'lay-verify=\"required|\" required maxlength=\"50\" placeHolder=\"必填\" readonly=\"readonly\" />\n		#@colEnd()\n		</div>\n		\n		<div class=\"layui-row layui-col-space1 task-row\">\n		#@colStart(\"名称\",12)\n			<input type=\"text\" name=\"name\"  value=\"\" class=\"layui-input\"  lay-verType=\'tips\' lay-verify=\"required|\" maxlength=\"50\" placeHolder=\"必填\" required />\n		#@colEnd()\n	 	</div>	\n		\n		<div class=\"layui-row layui-col-space1 task-row\">\n		#@colStart(\"序号\",12)\n			<input type=\"number\" name=\"order_no\" value=\"1\" class=\"layui-input\"/>\n		#@colEnd()\n		</div>\n	\n		</div>\n		\n		#@submitButton()\n	</form>\n	\n#end\n\n<!-- 获取对象数据 -->\n#define js()\n<script type=\"text/javascript\">\n	$(function(){\n		var searchURL=window.location.search;\n		searchURL=searchURL+\"&object=sys_tree&primaryKey=id\";\n		var editUrl=\"#(path)/portal/form/business/getModel\"+searchURL;\n		//发送请求\n		$.post(editUrl, {}, function(ret) {\n			if (ret.state==\"ok\") {\n				var data=ret.data;\n				//页面赋值\n				$.each(data,function(key,val){\n					$(\'input[name=\"\'+key+\'\"]\').val(val);\n				});\n			} else {\n				warn(ret.msg);\n			}\n		});\n	});\n	\n</script>\n#end\n\n<!-- 保存数据 -->\n#define layuiFunc()\n	var saveUrl=\"#(path)/portal/form/business/update\";\n#end\n', '2019-04-28 16:09:09', '2019-04-28 17:59:38', '修改数据页面');
INSERT INTO form_view VALUES ('402880fa6a62f77b016a62fe58b60066', '107400000000002215', '列表页', 'view_table', 'DEPLOYED', '\n\n#@layoutT(\"列表首页\")\n#define main()\n	#@formStart()\n		#@queryStart(\'名称\')\n		   <input type=\"search\" name=\"name\" autocomplete=\"off\" class=\"layui-input\" placeholder=\"名称\"/>\n		#@queryEnd()\n		#@queryStart(\'分类\')\n		   <input type=\"search\" name=\"type\" autocomplete=\"off\" class=\"layui-input\" placeholder=\"分类\"/>\n		#@queryEnd()		\n	#@formEnd()\n	\n	<!-- 表头按钮 -->\n	<div class=\'layui-row f-index-toolbar\'>\n		<div class=\'layui-col-xs12\'>\n			<div class=\"layui-btn-group\">\n				<button id=\'addBtn_\' class=\"layui-btn layui-btn-normal layui-btn-sm\">\n				  <i class=\"layui-icon\">&#xe608;</i> 新增\n				</button>\n				<button id=\'refreshBtn_\' class=\"layui-btn layui-btn-normal layui-btn-sm\">\n				  <i class=\"layui-icon\">&#xe669;</i> 刷新\n				</button>				\n				<button id=\'deleteBtn_\' class=\"layui-btn  layui-btn-normal layui-btn-sm\">\n				  <i class=\"layui-icon\">&#xe640;</i> 删除\n				</button>			\n			</div>\n		</div>\n	</div>\n	\n	<!-- 表格列表 -->\n	<div class=\"layui-row  f-index-toolbar\">\n		<div class=\"layui-col-xs12\">\n			<table id=\"maingrid\" lay-filter=\"maingrid\"></table>\n		</div>\n   	</div>\n   	\n   	<!-- 	每行的操作按钮 -->\n	<script type=\"text/html\" id=\"bar_maingrid\">\n  		<a class=\"layui-btn layui-btn-xs\" lay-event=\"edit\">编辑</a>\n  		<a class=\"layui-btn layui-btn-xs layui-btn-danger\" lay-event=\"del\">删除</a>\n	</script>\n#end\n\n#define js()\n<!-- 分页表格 -->\n<script>\n	gridArgs.title=\'系统树\';\n	gridArgs.dataId=\'id\';\n	gridArgs.deleteUrl=\'#(path)/portal/form/business/delete?object=sys_tree&primaryKey=\'+gridArgs.dataId;\n	gridArgs.updateUrl=\'#(path)/portal/form/business/edit?viewCode=view_update&id=\';\n	gridArgs.addUrl=\'#(path)/portal/form/business/add?viewCode=view_add\';\n	gridArgs.gridDivId =\'maingrid\';\n	initGrid({id : \'maingrid\'\n			,elem : \'#maingrid\'\n			,cellMinWidth: 80\n			,cols : [ [\n					{title: \'主键\',field : \'id\',width : 35,checkbox : true},						\n					{title:\'序号\',type:\'numbers\',width:35},\n        			{title: \'分类\', field: \'type\'},\n					{title: \'名称\', field: \'name\' },\n	        		{title: \'排序\', field: \'order_no\'},																\n					{title: \'操作\',fixed:\'right\',width : 180,align : \'left\',toolbar : \'#bar_maingrid\'} // 这里的toolbar值是模板元素的选择器\n			] ]\n			,url:\"#(path)/portal/form/business/list\"\n            ,where:{\"object\":\"sys_tree\",\"primaryKey\":gridArgs.dataId}\n			,searchForm : \'searchForm\'\n		});\n</script>\n\n#end\n ', '2019-04-28 16:09:57', '2019-04-28 18:04:14', '列表页');

-- ----------------------------
-- Table structure for sys_tree
-- ----------------------------
DROP TABLE IF EXISTS sys_tree;
CREATE TABLE sys_tree  (
  id varchar(64) CHARACTER SET utf8  NOT NULL COMMENT '主键',
  parent_id varchar(64) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '上级Id',
  name varchar(50) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '名称',
  type varchar(50) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '分类',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  order_no int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '系统tree' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tree
-- ----------------------------
INSERT INTO sys_tree VALUES ('107400000000002215', NULL, '流程表单', 'form', '2019-04-25 14:00:52', 1);
INSERT INTO sys_tree VALUES ('4028830c6b5fcc89016b5fd976fc005a', NULL, '用户部门查询', 'sql', '2019-09-04 09:00:52', 1);

-- ----------------------------
-- Table structure for w_sys_tree
-- ----------------------------
DROP TABLE IF EXISTS w_sys_tree;
CREATE TABLE w_sys_tree  (
  id varchar(64) CHARACTER SET utf8  NOT NULL COMMENT '主键',
  parent_id varchar(64) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '上级Id',
  name varchar(50) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '名称',
  type varchar(50) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '分类',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  order_no int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '测试在线表单用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for form_sql
-- ----------------------------
DROP TABLE IF EXISTS form_sql;
CREATE TABLE form_sql  (
  id varchar(64) CHARACTER SET utf8  NOT NULL COMMENT '主键',
  tree_id varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '树id',
  code varchar(50) CHARACTER SET utf8  NOT NULL COMMENT 'sql编号',
  name varchar(50) CHARACTER SET utf8  NOT NULL COMMENT 'sql名称',
  content varchar(500) CHARACTER SET utf8  NOT NULL COMMENT 'sql内容',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  descp varchar(255) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT 'sql说明',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8  COMMENT = '表单自定义查询sql' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of form_sql
-- ----------------------------
INSERT INTO form_sql VALUES ('4028830c6b5feeb4016b5ff3bee9000c', '4028830c6b5fcc89016b5fd976fc005a', 'query_user', '查询用户', 'select a.*,b.org_name from sys_user a,sys_org b where a.org_id=b.id group by a.id order by a.id desc ', '2019-06-16 19:02:18', 'test');
INSERT INTO form_sql VALUES ('4028832c6cf20578016cf209c0300013', '4028830c6b5fcc89016b5fd976fc005a', 'query_org', '部门下拉sql', 'select id value,org_name name from sys_org', '2019-09-02 20:53:41', NULL);

