
-- ----------------------------
-- Table structure for app_application_info
-- ----------------------------
DROP TABLE IF EXISTS app_application_info;
CREATE TABLE app_application_info (
  property_id int(11) NOT NULL AUTO_INCREMENT,
  system_type int(11) NOT NULL,
  property_name varchar(255) NOT NULL,
  property_flag varchar(255) NOT NULL,
  property_value varchar(255) NOT NULL,
  description varchar(255) DEFAULT NULL,
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (property_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_attribute_info
-- ----------------------------
DROP TABLE IF EXISTS app_attribute_info;
CREATE TABLE app_attribute_info (
  uid int(11) NOT NULL AUTO_INCREMENT,
  system_type int(11) NOT NULL,
  attribute_flag varchar(255) NOT NULL,
  attribute_name varchar(255) NOT NULL,
  attribute_value int(11) NOT NULL,
  description varchar(255) DEFAULT NULL,
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_region_info
-- ----------------------------
DROP TABLE IF EXISTS app_region_info;
CREATE TABLE app_region_info (
  region_id int(11) NOT NULL AUTO_INCREMENT,
  region_name varchar(255) NOT NULL,
  region_code varchar(255) NOT NULL,
  parent_id int(11) DEFAULT NULL,
  sequence int(11) NOT NULL,
  map_name varchar(255) DEFAULT NULL,
  use_flag int(11) DEFAULT '1',
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (region_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_version_info
-- ----------------------------
DROP TABLE IF EXISTS app_version_info;
CREATE TABLE app_version_info (
  version_id int(11) NOT NULL AUTO_INCREMENT,
  system_type int(11) NOT NULL,
  version_type int(11) NOT NULL,
  version_name varchar(255) NOT NULL,
  version_code int(11) NOT NULL,
  device_type int(11) NOT NULL,
  description varchar(255) DEFAULT NULL,
  upload_time datetime NOT NULL,
  http_path varchar(255) NOT NULL,
  save_path varchar(255) NOT NULL,
  save_name varchar(255) NOT NULL,
  file_name varchar(255) NOT NULL,
  file_md5 varchar(255) NOT NULL,
  file_type varchar(255) NOT NULL,
  file_size bigint(20) NOT NULL,
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (version_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_weather_info
-- ----------------------------
DROP TABLE IF EXISTS app_weather_info;
CREATE TABLE app_weather_info (
  weather_id int(11) NOT NULL AUTO_INCREMENT,
  region_code varchar(255) NOT NULL,
  description varchar(255) default '',
  temperature varchar(255) default '',
  image_type_1 int(11) NOT NULL,
  image_type_2 int(11) NOT NULL,
  pm_2_5 int(11) default '0',
  humidity varchar(255) default '',
  wind_direction varchar(255) default '',
  wind_speed varchar(255) default '',
  forecast datetime NOT NULL,
  week varchar(255) default '',
  chinese varchar(255) default '',
  save_date datetime NOT NULL,
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (weather_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- 用户相关 - Table structure for app_user_info
-- ----------------------------
DROP TABLE IF EXISTS app_user_info;
CREATE TABLE app_user_info (
  user_id int(11) NOT NULL AUTO_INCREMENT,
  user_name varchar(255) NOT NULL,
  nick_name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  create_time datetime NOT NULL,
  last_time datetime DEFAULT NULL,
  user_type int(11) DEFAULT '0',
  online int(11) default '0',
  use_flag int(11) DEFAULT '1',
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (user_id),
  KEY user_name (user_name) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_group_info
-- ----------------------------
DROP TABLE IF EXISTS app_group_info;
CREATE TABLE app_group_info (
  group_id int(11) NOT NULL AUTO_INCREMENT,
  group_name varchar(255) NOT NULL,
  group_type int(11) DEFAULT '0',
  parent_id int(11) DEFAULT NULL,
  region_id int(11) NOT NULL,
  description varchar(255) DEFAULT NULL,
  sequence int(11) DEFAULT '0',
  use_flag int(11) DEFAULT '1',
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_role_info
-- ----------------------------
DROP TABLE IF EXISTS app_role_info;
CREATE TABLE app_role_info (
  role_id int(11) NOT NULL AUTO_INCREMENT,
  role_name varchar(255) NOT NULL,
  system_type int(11) DEFAULT NULL,
  use_flag int(11) DEFAULT '1',
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_authority_info
-- ----------------------------
DROP TABLE IF EXISTS app_authority_info;
CREATE TABLE app_authority_info (
  authority_id int(11) NOT NULL AUTO_INCREMENT,
  system_type int(11) NOT NULL,
  authority_name varchar(255) NOT NULL,
  authority_flag varchar(255) NOT NULL,
  use_flag int(11) DEFAULT '1',
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (authority_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_resource_info
-- ----------------------------
DROP TABLE IF EXISTS app_resource_info;
CREATE TABLE app_resource_info (
  resource_id int(11) NOT NULL AUTO_INCREMENT,
  system_type int(11) NOT NULL,
  resource_name varchar(255) NOT NULL,
  resource_type int(11) NOT NULL,
  resource_url varchar(255) NOT NULL,
  parent_id int(11) DEFAULT NULL,
  sequence int(11) NOT NULL,
  use_flag int(11) DEFAULT '1',
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (resource_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS app_user_role_rel;
CREATE TABLE app_user_role_rel (
  user_id int(11) NOT NULL,
  role_id int(11) NOT NULL,
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_role_authority_rel
-- ----------------------------
DROP TABLE IF EXISTS app_role_authority_rel;
CREATE TABLE app_role_authority_rel (
  role_id int(11) NOT NULL,
  authority_id int(11) NOT NULL,
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (role_id, authority_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_authority_resource_rel
-- ----------------------------
DROP TABLE IF EXISTS app_authority_resource_rel;
CREATE TABLE app_authority_resource_rel (
  authority_id int(11) NOT NULL,
  resource_id int(11) NOT NULL,
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (authority_id,resource_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for app_user_group_rel
-- ----------------------------
DROP TABLE IF EXISTS app_user_group_rel;
CREATE TABLE app_user_group_rel (
  user_id int(11) NOT NULL,
  group_id int(11) NOT NULL,
  role_id int(11) NOT NULL,
  h_version int(11) DEFAULT '1',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (user_id,group_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS app_user_detail_info;
CREATE TABLE app_user_detail_info (
  uid int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  name varchar(255) NOT NULL,
  value varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  h_version int(11) DEFAULT '0',
  h_deleted int(11) DEFAULT '0',
  PRIMARY KEY (uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 区域
-- ----------------------------
DELETE FROM app_region_info;
INSERT INTO app_region_info (region_id, region_name, region_code, parent_id, sequence, use_flag) VALUES (1, 	'总部', 	    '', NULL, 1, 1);
INSERT INTO app_region_info (region_id, region_name, region_code, parent_id, sequence, use_flag) VALUES (2, 	'北京市', 	'', NULL, 2, 1);
INSERT INTO app_region_info (region_id, region_name, region_code, parent_id, sequence, use_flag) VALUES (201, 	'朝阳区', 	'', 2, 1, 1);
INSERT INTO app_region_info (region_id, region_name, region_code, parent_id, sequence, use_flag) VALUES (202, 	'海淀区',	'', 2, 2, 1);
INSERT INTO app_region_info (region_id, region_name, region_code, parent_id, sequence, use_flag) VALUES (3, 	'湖北省',	'', NULL, 3, 1);
INSERT INTO app_region_info (region_id, region_name, region_code, parent_id, sequence, use_flag) VALUES (301, 	'武汉市', 	'', 3, 3, 1);
INSERT INTO app_region_info (region_id, region_name, region_code, parent_id, sequence, use_flag) VALUES (4, 	'陕西省', 	'', NULL, 4, 1);

-- ----------------------------
-- Records of 组织结构
-- ----------------------------
DELETE FROM app_group_info where group_id=1;
INSERT INTO app_group_info (group_id, group_name, parent_id, description, sequence, region_id, use_flag) VALUES (1, '总部',   NULL, '', 1, 1, 1);
INSERT INTO app_group_info (group_id, group_name, parent_id, description, sequence, region_id, use_flag) VALUES (2, '北京部', NULL, '', 2, 2, 1);
INSERT INTO app_group_info (group_id, group_name, parent_id, description, sequence, region_id, use_flag) VALUES (3, '朝阳部', NULL, '', 3, 201, 1);
INSERT INTO app_group_info (group_id, group_name, parent_id, description, sequence, region_id, use_flag) VALUES (4, '海淀部', NULL, '', 4, 202, 1);
INSERT INTO app_group_info (group_id, group_name, parent_id, description, sequence, region_id, use_flag) VALUES (5, '武汉部', NULL, '', 5, 301, 1);

-- ----------------------------
-- Records of 超级管理员用户
-- ----------------------------
DELETE FROM app_user_info where user_id=1 or user_id=2;
INSERT INTO app_user_info (user_id, user_name, nick_name, password, create_time, last_time, user_type, use_flag) VALUES ('1', 'administrator', '超级管理员', 'E67A63B9F434013D119040DEE91727B3', now(), NULL, 1, 1);
INSERT INTO app_user_info (user_id, user_name, nick_name, password, create_time, last_time, user_type, use_flag) VALUES ('2', 'admin', '普通管理员', 'E67A63B9F434013D119040DEE91727B3', now(), NULL, 1, 1);

DELETE FROM app_user_role_rel where user_id=1 or user_id=2;
INSERT INTO app_user_role_rel(user_id, role_id) VALUES(1, 1);
INSERT INTO app_user_role_rel(user_id, role_id) VALUES(2, 2);

DELETE FROM app_user_group_rel where user_id=1 or user_id=2;
INSERT INTO app_user_group_rel(user_id, group_id, role_id) VALUES(1, 1, 1);
INSERT INTO app_user_group_rel(user_id, group_id, role_id) VALUES(2, 1, 2);

