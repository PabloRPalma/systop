-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.31-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema quake
--

CREATE DATABASE IF NOT EXISTS quake;
USE quake;

--
-- Definition of table `advisorys`
--

DROP TABLE IF EXISTS `advisorys`;
CREATE TABLE `advisorys` (
  `ID` int(11) NOT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `CONTENT` longtext,
  `CREAT_DATE` datetime DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  `POS_CODE` varchar(20) DEFAULT NULL,
  `RE_CONTENT` longtext,
  `RE_DATE` datetime DEFAULT NULL,
  `STATUS` varchar(1) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `advisorys`
--

/*!40000 ALTER TABLE `advisorys` DISABLE KEYS */;
/*!40000 ALTER TABLE `advisorys` ENABLE KEYS */;


--
-- Definition of table `announces`
--

DROP TABLE IF EXISTS `announces`;
CREATE TABLE `announces` (
  `ID` int(11) NOT NULL,
  `AUTHOR` varchar(50) DEFAULT NULL,
  `CONTENT` varchar(255) DEFAULT NULL,
  `CREAT_DATE` datetime DEFAULT NULL,
  `IS_NEW` varchar(1) DEFAULT NULL,
  `OUT_TIME` decimal(22,0) DEFAULT NULL,
  `SHOW_TYPE` varchar(1) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `announces`
--

/*!40000 ALTER TABLE `announces` DISABLE KEYS */;
/*!40000 ALTER TABLE `announces` ENABLE KEYS */;


--
-- Definition of table `articles`
--

DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles` (
  `ID` int(11) NOT NULL,
  `AUDITED` varchar(1) DEFAULT NULL,
  `AUTHOR` varchar(100) DEFAULT NULL,
  `AVAILABLE` varchar(1) DEFAULT NULL,
  `CONTENT` longtext,
  `CREATE_TIME` datetime DEFAULT NULL,
  `DESCN` varchar(255) DEFAULT NULL,
  `EXPIRE_DATE` datetime DEFAULT NULL,
  `HITS` int(11) DEFAULT NULL,
  `IS_DRAFT` varchar(1) DEFAULT NULL,
  `IS_ELITE` varchar(1) DEFAULT NULL,
  `KEY_WORD` varchar(255) DEFAULT NULL,
  `LINK_URL` varchar(255) DEFAULT NULL,
  `MAX_CHAR_PAGE` int(11) DEFAULT NULL,
  `ON_TOP` varchar(1) DEFAULT NULL,
  `PAGINATION_TYPE` varchar(1) DEFAULT NULL,
  `PATH` varchar(255) DEFAULT NULL,
  `SERIAL_NO` int(11) DEFAULT NULL,
  `SHORT_TITLE` varchar(255) DEFAULT NULL,
  `SUBTITLE` varchar(255) DEFAULT NULL,
  `SUMMARY` longtext,
  `TITLE` varchar(255) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `AUDITOR` int(11) DEFAULT NULL,
  `CATALOG` int(11) DEFAULT NULL,
  `INPUTER` int(11) DEFAULT NULL,
  `TEMPLATE` int(11) DEFAULT NULL,
  `UPDATER` int(11) DEFAULT NULL,
  `FLASH_IMG` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FKE566C23D2E3E8B5F` (`UPDATER`),
  KEY `FKE566C23DB1C38E36` (`CATALOG`),
  KEY `FKE566C23DB0BC448D` (`INPUTER`),
  KEY `FKE566C23D14CDC994` (`AUDITOR`),
  KEY `FKE566C23D54735970` (`TEMPLATE`),
  KEY `FKB6C0D23D2E3E8B5F` (`UPDATER`),
  KEY `FKB6C0D23DB1C38E36` (`CATALOG`),
  KEY `FKB6C0D23DB0BC448D` (`INPUTER`),
  KEY `FKB6C0D23D14CDC994` (`AUDITOR`),
  KEY `FKB6C0D23D54735970` (`TEMPLATE`),
  CONSTRAINT `FKB6C0D23D14CDC994` FOREIGN KEY (`AUDITOR`) REFERENCES `users` (`ID`),
  CONSTRAINT `FKB6C0D23D2E3E8B5F` FOREIGN KEY (`UPDATER`) REFERENCES `users` (`ID`),
  CONSTRAINT `FKB6C0D23D54735970` FOREIGN KEY (`TEMPLATE`) REFERENCES `templates` (`ID`),
  CONSTRAINT `FKB6C0D23DB0BC448D` FOREIGN KEY (`INPUTER`) REFERENCES `users` (`ID`),
  CONSTRAINT `FKB6C0D23DB1C38E36` FOREIGN KEY (`CATALOG`) REFERENCES `catalogs` (`ID`),
  CONSTRAINT `FKE566C23D14CDC994` FOREIGN KEY (`AUDITOR`) REFERENCES `users` (`ID`),
  CONSTRAINT `FKE566C23D2E3E8B5F` FOREIGN KEY (`UPDATER`) REFERENCES `users` (`ID`),
  CONSTRAINT `FKE566C23D54735970` FOREIGN KEY (`TEMPLATE`) REFERENCES `templates` (`ID`),
  CONSTRAINT `FKE566C23DB0BC448D` FOREIGN KEY (`INPUTER`) REFERENCES `users` (`ID`),
  CONSTRAINT `FKE566C23DB1C38E36` FOREIGN KEY (`CATALOG`) REFERENCES `catalogs` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `articles`
--

/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;


--
-- Definition of table `attachments`
--

DROP TABLE IF EXISTS `attachments`;
CREATE TABLE `attachments` (
  `ID` int(11) NOT NULL,
  `IS_DEL` varchar(1) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `PATH` varchar(255) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `ARTICLE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FKD3F3CBB018F129D6` (`ARTICLE`),
  CONSTRAINT `FKD3F3CBB018F129D6` FOREIGN KEY (`ARTICLE`) REFERENCES `articles` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `attachments`
--

/*!40000 ALTER TABLE `attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachments` ENABLE KEYS */;


--
-- Definition of table `c3p0_test_table`
--

DROP TABLE IF EXISTS `c3p0_test_table`;
CREATE TABLE `c3p0_test_table` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `c3p0_test_table`
--

/*!40000 ALTER TABLE `c3p0_test_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `c3p0_test_table` ENABLE KEYS */;


--
-- Definition of table `catalogs`
--

DROP TABLE IF EXISTS `catalogs`;
CREATE TABLE `catalogs` (
  `ID` int(11) NOT NULL,
  `DESCN` varchar(255) DEFAULT NULL,
  `GROUPID` int(11) DEFAULT NULL,
  `IS_ENABLE` varchar(2) DEFAULT NULL,
  `LINK_URL` varchar(255) DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `ORDER_ID` int(11) DEFAULT NULL,
  `PIC` varchar(255) DEFAULT NULL,
  `ROOT` varchar(25) DEFAULT NULL,
  `ROOTPATH` varchar(220) DEFAULT NULL,
  `SHOW_ON_INDEX` varchar(2) DEFAULT NULL,
  `SHOW_ON_PARLIST` varchar(2) DEFAULT NULL,
  `SHOW_ON_TOP` varchar(2) DEFAULT NULL,
  `TYPE` varchar(2) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `ART_TEMPLATE` int(11) DEFAULT NULL,
  `TEMPLATE` int(11) DEFAULT NULL,
  `PARENT` int(11) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK3172937A554F36C7` (`PARENT`),
  KEY `FK3172937A54735970` (`TEMPLATE`),
  KEY `FK3172937A43470FCC` (`ART_TEMPLATE`),
  KEY `FK2CCA37A554F36C7` (`PARENT`),
  KEY `FK2CCA37A54735970` (`TEMPLATE`),
  KEY `FK2CCA37A43470FCC` (`ART_TEMPLATE`),
  CONSTRAINT `FK2CCA37A43470FCC` FOREIGN KEY (`ART_TEMPLATE`) REFERENCES `templates` (`ID`),
  CONSTRAINT `FK2CCA37A54735970` FOREIGN KEY (`TEMPLATE`) REFERENCES `templates` (`ID`),
  CONSTRAINT `FK2CCA37A554F36C7` FOREIGN KEY (`PARENT`) REFERENCES `catalogs` (`ID`),
  CONSTRAINT `FK3172937A43470FCC` FOREIGN KEY (`ART_TEMPLATE`) REFERENCES `templates` (`ID`),
  CONSTRAINT `FK3172937A54735970` FOREIGN KEY (`TEMPLATE`) REFERENCES `templates` (`ID`),
  CONSTRAINT `FK3172937A554F36C7` FOREIGN KEY (`PARENT`) REFERENCES `catalogs` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `catalogs`
--

/*!40000 ALTER TABLE `catalogs` DISABLE KEYS */;
INSERT INTO `catalogs` (`ID`,`DESCN`,`GROUPID`,`IS_ENABLE`,`LINK_URL`,`NAME`,`ORDER_ID`,`PIC`,`ROOT`,`ROOTPATH`,`SHOW_ON_INDEX`,`SHOW_ON_PARLIST`,`SHOW_ON_TOP`,`TYPE`,`VERSION`,`ART_TEMPLATE`,`TEMPLATE`,`PARENT`,`target`) VALUES 
 (229377,'',2,'1',NULL,'工作信息',1,NULL,'dynamicwork','/dynamicwork','1','1','1','1',8,196610,36831232,NULL,'_self'),
 (229378,'',3,'1',NULL,'法规标准',2,NULL,'regulations','/regulations','1','1','1','1',9,196610,36831232,NULL,'_self'),
 (229383,'',8,'1',NULL,'软件下载',7,NULL,'download','/download','1','1','1','1',2,196610,34373634,NULL,NULL),
 (34996224,'',11,'1',NULL,'关于本站',9,NULL,'about','/about','1','1','1','1',4,196610,35782656,NULL,'_self'),
 (35323907,'',3,'1',NULL,'数据共享规章制度',11,NULL,'guizhang','/regulations/guizhang','0','1','0','1',4,196610,38076417,229378,'_self'),
 (35323908,'',3,'1',NULL,'数据共享标准规范',12,NULL,'standard','/regulations/standard','0','1','0','1',3,196610,38076417,229378,'_self'),
 (35323909,'',3,'1',NULL,'地震相关法规',13,NULL,'law','/regulations/law','0','1','0','1',3,196610,38076417,229378,'_self'),
 (36601856,'用于登录用户更改个人信息！',12,'1',NULL,'个人信息修改',14,NULL,'useredit','/useredit','0','0','0','1',2,196610,36569088,NULL,'_self');
/*!40000 ALTER TABLE `catalogs` ENABLE KEYS */;


--
-- Definition of table `datasource_info`
--

DROP TABLE IF EXISTS `datasource_info`;
CREATE TABLE `datasource_info` (
  `id` varchar(255) NOT NULL,
  `cz_host` varchar(255) DEFAULT NULL,
  `cz_instance` varchar(255) DEFAULT NULL,
  `cz_path` varchar(255) DEFAULT NULL,
  `cz_port` varchar(255) DEFAULT NULL,
  `cz_pwd` varchar(255) DEFAULT NULL,
  `cz_schema` varchar(255) DEFAULT NULL,
  `cz_type` varchar(255) DEFAULT NULL,
  `cz_user` varchar(255) DEFAULT NULL,
  `qz_host` varchar(255) DEFAULT NULL,
  `qz_instance` varchar(255) DEFAULT NULL,
  `qz_port` varchar(255) DEFAULT NULL,
  `qz_pwd` varchar(255) DEFAULT NULL,
  `qz_schema` varchar(255) DEFAULT NULL,
  `qz_user` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `datasource_info`
--

/*!40000 ALTER TABLE `datasource_info` DISABLE KEYS */;
INSERT INTO `datasource_info` (`id`,`cz_host`,`cz_instance`,`cz_path`,`cz_port`,`cz_pwd`,`cz_schema`,`cz_type`,`cz_user`,`qz_host`,`qz_instance`,`qz_port`,`qz_pwd`,`qz_schema`,`qz_user`,`version`) VALUES 
 ('datasource','127.0.0.1','seismic',NULL,'3306','root','seismic','cz_mysql','root','127.0.0.1','seispara','3306','root','seispara','root',2);
/*!40000 ALTER TABLE `datasource_info` ENABLE KEYS */;


--
-- Definition of table `depts`
--

DROP TABLE IF EXISTS `depts`;
CREATE TABLE `depts` (
  `ID` int(11) NOT NULL,
  `dept_sort` varchar(255) DEFAULT NULL,
  `descn` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `serial_no` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK5B0CACE59DF1B52` (`parent_id`),
  CONSTRAINT `FK5B0CACE59DF1B52` FOREIGN KEY (`parent_id`) REFERENCES `depts` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `depts`
--

/*!40000 ALTER TABLE `depts` DISABLE KEYS */;
/*!40000 ALTER TABLE `depts` ENABLE KEYS */;


--
-- Definition of table `email_def`
--

DROP TABLE IF EXISTS `email_def`;
CREATE TABLE `email_def` (
  `id` varchar(255) NOT NULL,
  `freq_seismic` varchar(255) DEFAULT NULL,
  `freq_sign` varchar(255) DEFAULT NULL,
  `max_items` int(11) DEFAULT NULL,
  `min_m` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `email_def`
--

/*!40000 ALTER TABLE `email_def` DISABLE KEYS */;
INSERT INTO `email_def` (`id`,`freq_seismic`,`freq_sign`,`max_items`,`min_m`,`version`) VALUES 
 ('emaildef','0 0 0 * * ?',NULL,NULL,1,4);
/*!40000 ALTER TABLE `email_def` ENABLE KEYS */;


--
-- Definition of table `google_map`
--

DROP TABLE IF EXISTS `google_map`;
CREATE TABLE `google_map` (
  `ID` varchar(255) NOT NULL,
  `googlemap_id` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `google_map`
--

/*!40000 ALTER TABLE `google_map` DISABLE KEYS */;
INSERT INTO `google_map` (`ID`,`googlemap_id`) VALUES 
 ('googlemapid','ABQIAAAAfcFqkl5W9As7abpVgumoyBTwM0brOpm-All5BF6PoaKBxRWWERTB7Uv5c56sUm_Pc-uytBf6NPBl8A');
/*!40000 ALTER TABLE `google_map` ENABLE KEYS */;


--
-- Definition of table `hibernate_unique_key`
--

DROP TABLE IF EXISTS `hibernate_unique_key`;
CREATE TABLE `hibernate_unique_key` (
  `next_hi` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hibernate_unique_key`
--

/*!40000 ALTER TABLE `hibernate_unique_key` DISABLE KEYS */;
INSERT INTO `hibernate_unique_key` (`next_hi`) VALUES 
 (35);
/*!40000 ALTER TABLE `hibernate_unique_key` ENABLE KEYS */;


--
-- Definition of table `link_catas`
--

DROP TABLE IF EXISTS `link_catas`;
CREATE TABLE `link_catas` (
  `ID` int(11) NOT NULL,
  `DESCN` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `link_catas`
--

/*!40000 ALTER TABLE `link_catas` DISABLE KEYS */;
INSERT INTO `link_catas` (`ID`,`DESCN`,`NAME`) VALUES 
 (720896,'','友情链接');
/*!40000 ALTER TABLE `link_catas` ENABLE KEYS */;


--
-- Definition of table `links`
--

DROP TABLE IF EXISTS `links`;
CREATE TABLE `links` (
  `ID` int(11) NOT NULL,
  `IS_ELITE` varchar(1) DEFAULT NULL,
  `IS_PASSED` varchar(1) DEFAULT NULL,
  `ORDER_ID` int(11) DEFAULT NULL,
  `SIT_MAIL` varchar(255) DEFAULT NULL,
  `SITE_INFO` longtext,
  `SITE_LOGO` varchar(255) DEFAULT NULL,
  `SITE_NAME` varchar(50) DEFAULT NULL,
  `SITE_URL` varchar(255) DEFAULT NULL,
  `LINK_CATA` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK6234FB969DD74DB` (`LINK_CATA`),
  CONSTRAINT `FK6234FB969DD74DB` FOREIGN KEY (`LINK_CATA`) REFERENCES `link_catas` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `links`
--

/*!40000 ALTER TABLE `links` DISABLE KEYS */;
INSERT INTO `links` (`ID`,`IS_ELITE`,`IS_PASSED`,`ORDER_ID`,`SIT_MAIL`,`SITE_INFO`,`SITE_LOGO`,`SITE_NAME`,`SITE_URL`,`LINK_CATA`) VALUES 
 (753664,'1',NULL,1,'','','','中国地震局(CEA,中文版)','http://www.cea.gov.cn/',720896),
 (753665,'0',NULL,2,'','','','中国地震局地球物理研究所(IGCEA)','http://www.cea-igp.ac.cn',720896);
/*!40000 ALTER TABLE `links` ENABLE KEYS */;


--
-- Definition of table `permis_resc`
--

DROP TABLE IF EXISTS `permis_resc`;
CREATE TABLE `permis_resc` (
  `permission_id` int(11) NOT NULL,
  `resc_id` int(11) NOT NULL,
  PRIMARY KEY (`permission_id`,`resc_id`),
  KEY `FK427D10C89A58A5E5` (`permission_id`),
  KEY `FK427D10C8E9AD0070` (`resc_id`),
  CONSTRAINT `FK427D10C89A58A5E5` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`ID`),
  CONSTRAINT `FK427D10C8E9AD0070` FOREIGN KEY (`resc_id`) REFERENCES `resources` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `permis_resc`
--

/*!40000 ALTER TABLE `permis_resc` DISABLE KEYS */;
INSERT INTO `permis_resc` (`permission_id`,`resc_id`) VALUES 
 (34897920,34865170),
 (34897920,34865171),
 (34897920,34865172),
 (34897920,34865173),
 (34897920,34865174),
 (34897920,34865175),
 (34897920,34865176),
 (34897920,34865177),
 (34897920,34865178),
 (34897920,34865179),
 (34897920,34865180),
 (34897920,34865181),
 (34897920,34865182),
 (34897920,34865190),
 (34897920,34865191),
 (34897920,34865192),
 (34897920,34865193),
 (34897920,34865194),
 (34897920,34865195),
 (34897920,34865196),
 (34897920,34865197),
 (34897920,34865198),
 (34897920,34865199),
 (34897920,34865200),
 (34897920,34865201),
 (34897920,34865202),
 (34897920,34865203),
 (34897920,34865204),
 (34897920,34865205),
 (34897920,34865206),
 (34897920,34865207),
 (34897920,34865208),
 (34897920,34865209),
 (34897920,34865210),
 (34897920,34865211),
 (34897920,34865212),
 (34897920,34865213),
 (34897920,34865214),
 (34897920,34865215),
 (34897920,34865216),
 (34897920,34865217),
 (34897920,34865218),
 (34897920,34865219),
 (34897920,34865220),
 (34897920,34865224),
 (34897920,34865225),
 (34897920,34865226),
 (34897920,34865227),
 (34897920,34865228),
 (34897920,34865229),
 (34897920,34865230),
 (34897920,34865231),
 (34897920,34865232),
 (34897920,34865233),
 (34897920,34865239),
 (34897920,34865240),
 (34897920,34865242),
 (34897920,34865243),
 (34897920,34865251),
 (34897920,35422213),
 (34897920,35422214),
 (34897920,35422215),
 (34897920,35422216),
 (34897920,35422217),
 (34897920,35618825),
 (34897920,35618826),
 (34897920,35618827),
 (34897920,35618828),
 (34897920,35618829),
 (34897920,35618830),
 (34897920,35618831),
 (34897920,35618832),
 (34897920,35684352),
 (34897920,35684380),
 (34897920,35684381),
 (34897920,35684382),
 (34897920,35684383),
 (34897920,35684384),
 (34897920,35684385),
 (34897920,35684386),
 (34897920,35684387),
 (34897920,35684388),
 (34897920,35684389),
 (34897920,35684390),
 (34897920,35684391),
 (34897920,35749888),
 (34897920,35749889),
 (34897920,35749890),
 (34897920,35749891),
 (34897920,35749893),
 (34897920,35749894),
 (34897920,35749895),
 (34897920,35749896),
 (34897920,35749897),
 (34897920,35749898),
 (34897920,35749899),
 (34897920,35749900),
 (34897920,35749901),
 (34897920,35749902),
 (34897920,35749903),
 (34897920,35749904),
 (34897920,35946496),
 (34897920,35946497),
 (34897920,39026721),
 (34897920,39026722),
 (35880960,35684380),
 (35880960,35684381),
 (35880960,35684382);
/*!40000 ALTER TABLE `permis_resc` ENABLE KEYS */;


--
-- Definition of table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `ID` int(11) NOT NULL,
  `descn` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `is_sys` char(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `permissions`
--

/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` (`ID`,`descn`,`name`,`operation`,`status`,`version`,`is_sys`) VALUES 
 (34897920,'Admin Authorization','AUTH_ADMIN',NULL,'1',NULL,'0'),
 (35880960,'普通用户权限','AUTH_NORMAL','target_url','1',NULL,NULL);
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;


--
-- Definition of table `quake_catalog`
--

DROP TABLE IF EXISTS `quake_catalog`;
CREATE TABLE `quake_catalog` (
  `ID` int(11) NOT NULL,
  `cl_descn` varchar(255) DEFAULT NULL,
  `cl_cname` varchar(255) DEFAULT NULL,
  `cl_tname` varchar(255) DEFAULT NULL,
  `dis_type` varchar(255) DEFAULT NULL,
  `mag_tname` varchar(255) DEFAULT NULL,
  `phase_tname` varchar(255) DEFAULT NULL,
  `seed_dis` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `quake_catalog`
--

/*!40000 ALTER TABLE `quake_catalog` DISABLE KEYS */;
/*!40000 ALTER TABLE `quake_catalog` ENABLE KEYS */;


--
-- Definition of table `reg_memo`
--

DROP TABLE IF EXISTS `reg_memo`;
CREATE TABLE `reg_memo` (
  `id` varchar(255) NOT NULL,
  `content` longtext,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `reg_memo`
--

/*!40000 ALTER TABLE `reg_memo` DISABLE KEYS */;
INSERT INTO `reg_memo` (`id`,`content`,`title`) VALUES 
 ('regmemoDef','<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 目前地震科学数据共享系统还未实现统一用户认证和授权，国家地震科学数据共享中心和各分中心自行管理各数据服务网站的用户注册、授权和登录，用户访问不同的数据时要进行多次注册和登录。将来我们将实现整个地震科学数据服务网站的一站式注册和登录。</p>\r\n<p>以下内容仅限于国家地震科学数据共享中心的用户注册服务。</p>\r\n<p>1、用户类别与权限</p>\r\n<p>国家地震科学数据共享中心网站用户分为两大类，非注册用户和注册用户。</p>\r\n<p>非注册用户可以在线浏览、查询和下载以下数据：<br />\r\n&nbsp;&nbsp;&nbsp; <br />\r\n&nbsp;国家台网地震速报目录<br />\r\n&nbsp;国家台网大地震快速数据<br />\r\n&nbsp;首都圈台网地震速报目录<br />\r\n&nbsp;&nbsp;地震台站基本数据<br />\r\n&nbsp;&nbsp;中国大地震全球波形记录</p>\r\n<p>注册用户又分为两类：</p>\r\n<p>(1)普通用户<br />\r\n可以在线浏览、查询和下载以下数据：</p>\r\n<p>&nbsp;&nbsp;中国台网地震目录<br />\r\n&nbsp;&nbsp;首都圈台网地震目录<br />\r\n&nbsp;&nbsp;中国历史地震目录<br />\r\n&nbsp;&nbsp;中国震例数据<br />\r\n&nbsp;国家台网震相数据<br />\r\n&nbsp;&nbsp;首都圈强震观测数据</p>\r\n<p>(2)高级用户<br />\r\n除享有普通用户的权利外，还可以线浏览、查询和下载以下数据：</p>\r\n<p>&nbsp;国家台网连续波形<br />\r\n&nbsp;&nbsp;国家台网事件波形<br />\r\n&nbsp;&nbsp;首都圈台网连续波形<br />\r\n&nbsp;&nbsp;首都圈台网事件波形<br />\r\n&nbsp;&nbsp;<br />\r\n中国公民都可申请成为普通用户。用户完成注册后立即成为普通用户。</p>\r\n<p>中国公务用户和公益用户可申请成为高级用户。如果申请高级用户，用户完成注册后立即成为普通用户，待审查批准后才能成为高级用户。</p>\r\n<p>2、注册用户信息<br />\r\n为了您更好地享用地震科学数据共享网提供的各种服务，您应该：<br />\r\n(1)提供详尽、准确的个人信息；<br />\r\n(2)个人信息发生变化时及时更新。</p>\r\n<p>国家地震科学数据共享中心尊重用户个人隐私，未经用户允许绝不泄露用户信息。</p>\r\n<p>3、用户的帐号和密码安全<br />\r\n(1)用户名注册成功后，必须保管好自己的帐号和密码，谨防被盗或泄露。每个用户都要对以其用户名进行的所有活动负责。<br />\r\n(2)用户在发现任何非法使用用户帐号或利用安全漏洞的情况，应立即报告国家地震科学数据共享中心。<br />\r\n(3)用户不得使用任何方式盗用、冒充他人用户名，否则后果自负。</p>\r\n<p>4、用户的权利和义务<br />\r\n（1）国家地震科学数据共享中心网站提供的所有内容，包括文字、图形、图表、视频声音、软件和其它各种有形产品受版权、商标、标签和其它财产所有权法律的保护。<br />\r\n（2）用户有权获取相应级别的地震科学数据。<br />\r\n（3）用户有权对地震科学数据共享服务质量进行监督，并向共享中心或其上级主管部门提出意见和建议。<br />\r\n（4）用户对获取的地震科学数据，只享有有限的、不排它的使用权。<br />\r\n（5）各类用户未经允许不得以任何方式将经审批获得的共享地震科学数据，以及由这些数据经单位换算、介质转换或者量度变换后形成的新资料有偿或无偿向他人提供。<br />\r\n（6）用户应在所发表的成果中注明所用数据资料的来源并寄送成果样本到共享中心或分中心存档。<br />\r\n（7）用户有义务配合共享中心和分中心进行改进数据共享服务质量的相关调查。</p>\r\n<p>5、国家地震科学数据共享中心对违反数据使用规定的用户一经发现立即停止服务。</p>','注册须知');
/*!40000 ALTER TABLE `reg_memo` ENABLE KEYS */;


--
-- Definition of table `resources`
--

DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `ID` int(11) NOT NULL,
  `descn` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `res_string` varchar(255) DEFAULT NULL,
  `res_type` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `RES_STRING` (`res_string`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `resources`
--

/*!40000 ALTER TABLE `resources` DISABLE KEYS */;
INSERT INTO `resources` (`ID`,`descn`,`name`,`res_string`,`res_type`,`version`) VALUES 
 (34865170,'文章管理_新建','文章管理_新建','/admin/article/newArticle.do*','url_resource',2),
 (34865171,'文章管理_编辑','文章管理_编辑','/admin/article/editArticle.do*','url_resource',2),
 (34865172,'文章管理_保存','文章管理_保存','/admin/article/saveArticle.do*','url_resource',2),
 (34865173,'文章管理_查看','文章管理_查看','/admin/article/lookArticle.do*','url_resource',2),
 (34865174,'文章管理_删除','文章管理_删除','/admin/article/removeArticle.do*','url_resource',2),
 (34865175,'文章管理_文章列表','文章管理_文章列表','/admin/article/listArticles.do*','url_resource',2),
 (34865176,'文章审核管理_文章审核列表','文章审核管理_文章审核列表','/admin/article/listAuditArticles.do*','url_resource',2),
 (34865177,'文章审核管理_取消审核','文章审核管理_取消审核','/admin/article/auditArticle.do*','url_resource',2),
 (34865178,'文章审核管理_批量取消审核','文章审核管理_批量取消审核','/admin/article/batchAuditArticle.do*','url_resource',2),
 (34865179,'文章审核管理_设为推荐','文章审核管理_设为推荐','/admin/article/eliteArticle.do*','url_resource',2),
 (34865180,'文章审核管理_设为固顶','文章审核管理_设为固顶','/admin/article/onTopArticle.do*','url_resource',2),
 (34865181,'文章管理_排序','文章管理_排序','/admin/article/orderArticle.do*','url_resource',2),
 (34865182,'文章管理_保存排序','文章管理_保存排序','/admin/article/saveOrderArticle.do*','url_resource',2),
 (34865190,'栏目管理_保存','栏目管理_保存','/admin/catalog/save.do*','url_resource',2),
 (34865191,'栏目管理_新建','栏目管理_新建','/admin/catalog/newCatalog.do*','url_resource',2),
 (34865192,'栏目管理_列表','栏目管理_列表','/admin/catalog/listCatalog.do*','url_resource',2),
 (34865193,'栏目管理_栏目详细信息','栏目管理_栏目详细信息','/admin/catalog/catalogDetails.do*','url_resource',2),
 (34865194,'栏目管理_编辑','栏目管理_编辑','/admin/catalog/editCatalog.do*','url_resource',2),
 (34865195,'栏目管理_添加子栏目','栏目管理_添加子栏目','/admin/catalog/addChildCatalog.do*','url_resource',2),
 (34865196,'栏目管理_栏目排序','栏目管理_栏目排序','/admin/catalog/listOrderCatalog.do*','url_resource',2),
 (34865197,'栏目管理_保存排序','栏目管理_保存排序','/admin/catalog/saveOrderCatalog.do*','url_resource',2),
 (34865198,'链接类别管理_列表','链接类别管理_列表','/admin/links/listCatas.do*','url_resource',2),
 (34865199,'链接类别管理_新建','链接类别管理_新建','/admin/links/newCatas.do*','url_resource',2),
 (34865200,'链接类别管理_编辑','链接类别管理_编辑','/admin/links/editCatas.do*','url_resource',2),
 (34865201,'链接类别管理_保存','链接类别管理_保存','/admin/links/saveCatas.do*','url_resource',2),
 (34865202,'链接管理_列表','链接管理_列表','/admin/links/listLink.do*','url_resource',2),
 (34865203,'链接管理_编辑','链接管理_编辑','/admin/links/editLink.do*','url_resource',2),
 (34865204,'链接管理_新建','链接管理_新建','/admin/links/newLink.do*','url_resource',2),
 (34865205,'链接管理_保存','链接管理_保存','/admin/links/saveLink.do*','url_resource',2),
 (34865206,'链接管理_删除','链接管理_删除','/admin/links/removeLink.do*','url_resource',2),
 (34865207,'链接管理_取消推荐','链接管理_取消推荐','/admin/links/eliteLink.do*','url_resource',2),
 (34865208,'链接管理_排序','链接管理_排序','/admin/links/orderLink.do*','url_resource',2),
 (34865209,'链接管理_保存排序','链接管理_保存排序','/admin/links/saveOrderLink.do*','url_resource',2),
 (34865210,'软件管理_新建','软件管理_新建','/admin/software/newSoft.do*','url_resource',2),
 (34865211,'软件管理_编辑','软件管理_编辑','/admin/software/edit.do*','url_resource',2),
 (34865212,'软件管理_保存','软件管理_保存','/admin/software/save.do*','url_resource',2),
 (34865213,'软件管理_列表','软件管理_列表','/admin/software/index.do*','url_resource',2),
 (34865214,'软件管理_删除','软件管理_删除','/admin/software/remove.do*','url_resource',2),
 (34865215,'软件类别管理_列表','软件类别管理_列表','/admin/software/listcatas.do*','url_resource',2),
 (34865216,'软件类别管理_保存','软件类别管理_保存','/admin/software/savecatas.do*','url_resource',2),
 (34865217,'软件类别管理_删除','软件类别管理_删除','/admin/software/delcatas.do*','url_resource',2),
 (34865218,'软件类别管理_编辑','软件类别管理_编辑','/admin/software/editcatas.do*','url_resource',2),
 (34865219,'软件类别管理_更新','软件类别管理_更新','/admin/software/upcatas.do*','url_resource',2),
 (34865220,'软件类别管理_新建','软件类别管理_新建','/admin/software/addcatas.do*','url_resource',2),
 (34865224,'模板管理_新建','模板管理_新建','/admin/template/newTemplate.do*','url_resource',2),
 (34865225,'模板管理_编辑','模板管理_编辑','/admin/template/editTemplate.do*','url_resource',2),
 (34865226,'模板管理_保存','模板管理_保存','/admin/template/saveTemplate.do*','url_resource',2),
 (34865227,'模板管理_列表','模板管理_列表','/admin/template/listTemplate.do*','url_resource',2),
 (34865228,'模板管理_资源文件列表','模板管理_资源文件列表','/admin/template/filesList.do*','url_resource',2),
 (34865229,'模板管理_保存资源文件','模板管理_保存资源文件','/admin/template/saveResFiles.do*','url_resource',2),
 (34865230,'SMTP配置_查看','SMTP配置_查看','/admin/mail/view.do*','url_resource',2),
 (34865231,'SMTP配置_编辑','SMTP配置_编辑','/admin/mail/edit.do*','url_resource',2),
 (34865232,'SMTP配置_保存','SMTP配置_保存','/admin/mail/save.do*','url_resource',2),
 (34865233,'SMTP配置_测试','SMTP配置_测试','/admin/mail/check.do*','url_resource',2),
 (34865239,'数据源配置_编辑','数据源配置_编辑','/quake/admin/ds/edit.do*','url_resource',3),
 (34865240,'数据源配置_保存','数据源配置_保存','/quake/admin/ds/save.do*','url_resource',3),
 (34865242,'网站基本信息设置_查看','网站基本信息设置_查看','/quake/admin/sitecfg/index.do*','url_resource',3),
 (34865243,'网站基本信息设置_保存','网站基本信息设置_保存','/quake/admin/sitecfg/save.do*','url_resource',3),
 (34865251,'数据服务首页','数据服务首页','/quake/index.do*','url_resource',2),
 (35422213,'注册用户管理_列表','注册用户管理_列表','/regist/index.do*','url_resource',6),
 (35422214,'注册用户管理_后台添加','注册用户管理_后台添加','/regist/editNew.do*','url_resource',6),
 (35422215,'注册用户管理_后台编辑','注册用户管理_后台编辑','/regist/edit.do*','url_resource',6),
 (35422216,'注册用户管理_后台审核','注册用户管理_后台审核','/regist/checkupUser.do*','url_resource',6),
 (35422217,'注册用户管理_后台保存','注册用户管理_后台保存','/regist/save.do*','url_resource',6),
 (35618825,'地震目录管理_列表','地震目录管理_列表','/quake/admin/catalog/index.do*','url_resource',4),
 (35618826,'地震目录管理_新建','地震目录管理_新建','/quake/admin/catalog/editNew.do*','url_resource',4),
 (35618827,'地震目录管理_编辑','地震目录管理_编辑','/quake/admin/catalog/edit.do*','url_resource',4),
 (35618828,'地震目录管理_保存','地震目录管理_保存','/quake/admin/catalog/save.do*','url_resource',4),
 (35618829,'编辑邮件订阅信息','编辑邮件订阅信息','/quake/email/admin/edit.do*','url_resource',3),
 (35618830,'保存邮件订阅信息','保存邮件订阅信息','/quake/email/admin/save.do*','url_resource',3),
 (35618831,'注册用户管理_后台删除','注册用户管理_后台删除','/regist/remove.do*','url_resource',4),
 (35618832,'地震目录管理_删除','地震目录管理_删除','/quake/admin/catalog/remove.do*','url_resource',4),
 (35684352,'测震邮件订阅信息列表','测震邮件订阅信息列表','/quake/email/admin/seisverify/list.do*','url_resource',3),
 (35684380,'前台修改个人密码','前台修改个人密码','/security/user/changePassword.do*','url_resource',4),
 (35684381,'编辑个人信息','编辑个人信息','/security/user/edit.do*','url_resource',5),
 (35684382,'前台个人信息保存','前台个人信息保存','/security/user/save.do*','url_resource',4),
 (35684383,'登录后台 ','登录后台 ','/adminIndex.jsp','url_resource',3),
 (35684384,'用户信息查询（所有用户）','用户信息查询（所有用户）','/security/user/index.do*','url_resource',3),
 (35684385,'删除用户','删除用户','/security/user/remove.do*','url_resource',3),
 (35684386,'启用用户','启用用户','/security/user/unsealUser.do*','url_resource',3),
 (35684387,'添加新用户','添加新用户','/security/user/editNew.do*','url_resource',3),
 (35684388,'返回用户类型','返回用户类型','/security/user/getRoles.do*','url_resource',3),
 (35684389,'角色管理','角色管理','/security/role/index.do*','url_resource',3),
 (35684390,'编辑角色','编辑角色','/security/role/edit.do*','url_resource',4),
 (35684391,'新建角色','新建角色','/security/role/editNew.do*','url_resource',3),
 (35749888,'删除角色','删除角色','/security/role/remove.do*','url_resource',3),
 (35749889,'保存角色','保存角色','/security/role/save.do*','url_resource',3),
 (35749890,'为用户分配角色','为用户分配角色','/security/role/saveUserRoles.do*','url_resource',3),
 (35749891,'取消保存用户角色信息','取消保存用户角色信息','/security/role/cancelSaveUserRoles.do*','url_resource',3),
 (35749893,'权限查询','权限查询','/security/permission/index.do*','url_resource',3),
 (35749894,'编辑权限','编辑权限','/security/permission/edit.do*','url_resource',3),
 (35749895,'新建权限','新建权限','/security/permission/editNew.do*','url_resource',3),
 (35749896,'删除权限','删除权限','/security/permission/remove.do*','url_resource',3),
 (35749897,'为角色分配权限','为角色分配权限','/security/permission/saveRolePermissions.do*','url_resource',3),
 (35749898,'取消保存为角色分配的权限','取消保存为角色分配的权限','/security/permission/cancelSaveRolePermissions.do*','url_resource',4),
 (35749899,'资源查询','资源查询','/security/resource/index.do*','url_resource',3),
 (35749900,'编辑资源信息','编辑资源信息','/security/resource/edit.do*','url_resource',3),
 (35749901,'新建资源','新建资源','/security/resource/editNew.do*','url_resource',3),
 (35749902,'删除资源','删除资源','/security/resource/remove.do*','url_resource',3),
 (35749903,'为权限分配资源','为权限分配资源','/security/resource/savePermissionResources.do*','url_resource',3),
 (35749904,'取消权限分配的资源','取消权限分配的资源','/security/resource/cancleSavePermissionResources.do*','url_resource',3),
 (35946496,'地震目录查询(SEED)','地震目录查询(SEED)','/quake/seismic/data/seed/index.do*','url_resource',3),
 (35946497,'地震目录列表信息(SEED)','地震目录列表信息(SEED)','/quake/seismic/data/seed/list.do*','url_resource',3),
 (37781510,'SEED存放路径配置','SEED存放路径配置','/quake/admin/seedpath/edit.do*','url_resource',2),
 (37781511,'SEED存放路径保存','SEED存放路径保存','/quake/admin/seedpath/save.do*','url_resource',2),
 (39026721,'Google Map Key配置','Google Map Key配置','/quake/admin/googlemap/edit.do*','url_resource',4),
 (39026722,'Google Map Key保存','Google Map Key保存','/quake/admin/googlemap/save.do*','url_resource',4);
/*!40000 ALTER TABLE `resources` ENABLE KEYS */;


--
-- Definition of table `role_permis`
--

DROP TABLE IF EXISTS `role_permis`;
CREATE TABLE `role_permis` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FK28B8B0038E47D4C5` (`role_id`),
  KEY `FK28B8B0039A58A5E5` (`permission_id`),
  CONSTRAINT `FK28B8B0038E47D4C5` FOREIGN KEY (`role_id`) REFERENCES `roles` (`ID`),
  CONSTRAINT `FK28B8B0039A58A5E5` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role_permis`
--

/*!40000 ALTER TABLE `role_permis` DISABLE KEYS */;
INSERT INTO `role_permis` (`role_id`,`permission_id`) VALUES 
 (1,34897920),
 (1,35880960);
/*!40000 ALTER TABLE `role_permis` ENABLE KEYS */;


--
-- Definition of table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `ID` int(11) NOT NULL,
  `descn` varchar(255) DEFAULT NULL,
  `is_sys` char(1) DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `parent` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK67A8EBDE147EB` (`parent`),
  CONSTRAINT `FK67A8EBDE147EB` FOREIGN KEY (`parent`) REFERENCES `roles` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roles`
--

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`ID`,`descn`,`is_sys`,`name`,`version`,`parent`) VALUES 
 (1,'系统管理员角色','1','ROLE_ADMIN',NULL,NULL),
 (3,'普通用户角色','1','ROLE_NORMAL',NULL,NULL);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;


--
-- Definition of table `sample_rates`
--

DROP TABLE IF EXISTS `sample_rates`;
CREATE TABLE `sample_rates` (
  `id` varchar(255) NOT NULL,
  `data_amount` int(11) DEFAULT NULL,
  `date_format` varchar(255) DEFAULT NULL,
  `enabled` char(1) DEFAULT '0',
  `for_mail` char(1) DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `sort` decimal(2,0) DEFAULT NULL,
  `stock_date_format` varchar(255) DEFAULT NULL,
  `stock_period` varchar(255) DEFAULT NULL,
  `stock_period_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sample_rates`
--

/*!40000 ALTER TABLE `sample_rates` DISABLE KEYS */;
/*!40000 ALTER TABLE `sample_rates` ENABLE KEYS */;


--
-- Definition of table `seed_plots`
--

DROP TABLE IF EXISTS `seed_plots`;
CREATE TABLE `seed_plots` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MapFile` varchar(255) DEFAULT NULL,
  `SeedFile` varchar(255) DEFAULT NULL,
  `Station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `seed_plots`
--

/*!40000 ALTER TABLE `seed_plots` DISABLE KEYS */;
/*!40000 ALTER TABLE `seed_plots` ENABLE KEYS */;


--
-- Definition of table `seedpath`
--

DROP TABLE IF EXISTS `seedpath`;
CREATE TABLE `seedpath` (
  `id` varchar(255) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `seedpath`
--

/*!40000 ALTER TABLE `seedpath` DISABLE KEYS */;
/*!40000 ALTER TABLE `seedpath` ENABLE KEYS */;


--
-- Definition of table `seismic_mails`
--

DROP TABLE IF EXISTS `seismic_mails`;
CREATE TABLE `seismic_mails` (
  `ID` int(11) NOT NULL,
  `catalog` varchar(255) DEFAULT NULL,
  `catalog_name` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `email_addr` varchar(255) DEFAULT NULL,
  `end_epi_lat` decimal(10,5) DEFAULT NULL,
  `end_epi_lon` decimal(10,5) DEFAULT NULL,
  `last_send_date` datetime DEFAULT NULL,
  `max_m` decimal(4,2) DEFAULT NULL,
  `min_m` decimal(4,2) DEFAULT NULL,
  `start_epi_lat` decimal(10,5) DEFAULT NULL,
  `start_epi_lon` decimal(10,5) DEFAULT NULL,
  `state` char(1) DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FKD68EF628337298A5` (`user_id`),
  CONSTRAINT `FKD68EF628337298A5` FOREIGN KEY (`user_id`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `seismic_mails`
--

/*!40000 ALTER TABLE `seismic_mails` DISABLE KEYS */;
/*!40000 ALTER TABLE `seismic_mails` ENABLE KEYS */;


--
-- Definition of table `site_cfg`
--

DROP TABLE IF EXISTS `site_cfg`;
CREATE TABLE `site_cfg` (
  `ID` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cms_code` varchar(255) DEFAULT NULL,
  `cms_name` varchar(255) DEFAULT NULL,
  `copyright` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `ICPCode` varchar(255) DEFAULT NULL,
  `min_m` double DEFAULT NULL,
  `province_name` varchar(255) DEFAULT NULL,
  `zipCode` varchar(255) DEFAULT NULL,
  `cz_catalog` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK2843936CA7ACF683` (`cz_catalog`),
  CONSTRAINT `FK2843936CA7ACF683` FOREIGN KEY (`cz_catalog`) REFERENCES `quake_catalog` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `site_cfg`
--

/*!40000 ALTER TABLE `site_cfg` DISABLE KEYS */;
INSERT INTO `site_cfg` (`ID`,`address`,`cms_code`,`cms_name`,`copyright`,`email`,`ICPCode`,`min_m`,`province_name`,`zipCode`,`cz_catalog`) VALUES 
 (163840,'','','测震台网数据服务系统','地震局','','',0,'','',NULL);
/*!40000 ALTER TABLE `site_cfg` ENABLE KEYS */;


--
-- Definition of table `smtp_config`
--

DROP TABLE IF EXISTS `smtp_config`;
CREATE TABLE `smtp_config` (
  `id` varchar(255) NOT NULL,
  `auth` varchar(255) DEFAULT NULL,
  `host` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `smtp_config`
--

/*!40000 ALTER TABLE `smtp_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `smtp_config` ENABLE KEYS */;


--
-- Definition of table `softcatas`
--

DROP TABLE IF EXISTS `softcatas`;
CREATE TABLE `softcatas` (
  `ID` int(11) NOT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `softcatas`
--

/*!40000 ALTER TABLE `softcatas` DISABLE KEYS */;
/*!40000 ALTER TABLE `softcatas` ENABLE KEYS */;


--
-- Definition of table `software`
--

DROP TABLE IF EXISTS `software`;
CREATE TABLE `software` (
  `ID` int(11) NOT NULL,
  `AUTHORIZATION` varchar(255) DEFAULT NULL,
  `DESCN` varchar(255) DEFAULT NULL,
  `DOWN_COUNT` int(11) DEFAULT NULL,
  `DOWN_URL` varchar(255) DEFAULT NULL,
  `INTRODUCTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `OS` varchar(255) DEFAULT NULL,
  `PUB_DATE` datetime DEFAULT NULL,
  `SIZE` bigint(20) DEFAULT NULL,
  `SOFT_VERSION` varchar(50) DEFAULT NULL,
  `SOFT_CATALOG` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK4EA361A7E5F10F3F` (`SOFT_CATALOG`),
  CONSTRAINT `FK4EA361A7E5F10F3F` FOREIGN KEY (`SOFT_CATALOG`) REFERENCES `softcatas` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `software`
--

/*!40000 ALTER TABLE `software` DISABLE KEYS */;
/*!40000 ALTER TABLE `software` ENABLE KEYS */;


--
-- Definition of table `specials`
--

DROP TABLE IF EXISTS `specials`;
CREATE TABLE `specials` (
  `ID` int(11) NOT NULL,
  `area` longtext,
  `CREATE_TIME` datetime DEFAULT NULL,
  `depth` varchar(255) DEFAULT NULL,
  `desn` longtext,
  `epifocus` longtext,
  `event_wave` longtext,
  `fracture` longtext,
  `front_pic` varchar(255) DEFAULT NULL,
  `history_pic` longtext,
  `intensity` longtext,
  `latitude` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `m_t` longtext,
  `magnitude` varchar(255) DEFAULT NULL,
  `mechanism` longtext,
  `qc_id` varchar(255) DEFAULT NULL,
  `quakeTime` varchar(255) DEFAULT NULL,
  `state` varchar(1) DEFAULT NULL,
  `station_wave` longtext,
  `TABLENAME` varchar(255) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `specials`
--

/*!40000 ALTER TABLE `specials` DISABLE KEYS */;
/*!40000 ALTER TABLE `specials` ENABLE KEYS */;


--
-- Definition of table `station_seeds`
--

DROP TABLE IF EXISTS `station_seeds`;
CREATE TABLE `station_seeds` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `cha` varchar(255) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `loc` varchar(255) DEFAULT NULL,
  `net` varchar(255) DEFAULT NULL,
  `sampleRate` varchar(255) DEFAULT NULL,
  `seed` varchar(255) DEFAULT NULL,
  `sta` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `totSamples` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `station_seeds`
--

/*!40000 ALTER TABLE `station_seeds` DISABLE KEYS */;
/*!40000 ALTER TABLE `station_seeds` ENABLE KEYS */;


--
-- Definition of table `templates`
--

DROP TABLE IF EXISTS `templates`;
CREATE TABLE `templates` (
  `ID` int(11) NOT NULL,
  `CONTENT` text,
  `DESCN` varchar(255) DEFAULT NULL,
  `IS_COMM` varchar(1) DEFAULT NULL,
  `IS_DEF` varchar(1) DEFAULT NULL,
  `IS_DEL` varchar(1) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `TYPE` varchar(1) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `templates`
--

/*!40000 ALTER TABLE `templates` DISABLE KEYS */;
INSERT INTO `templates` (`ID`,`CONTENT`,`DESCN`,`IS_COMM`,`IS_DEF`,`IS_DEL`,`NAME`,`TYPE`,`VERSION`) VALUES 
 (131072,'<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF8\" />\r\n<title></title>\r\n<style type=\"text/css\">\r\n<!--\r\n.arttitle{width:340px;\r\nwhite-space:nowrap;\r\nword-break:keep-all;\r\noverflow:hidden;\r\ntext-overflow:ellipsis;}　　\r\n.textbox{\r\n    border:1px solid #AADFFF;\r\n}\r\nbody {\r\n	margin-left: 0px;\r\n	margin-top: 0px;\r\n	margin-right: 0px;\r\n	margin-bottom: 0px;\r\n	font-family:\"宋体\";\r\n	font-size:12px;\r\n	color:#2d6fa9;\r\n}\r\n.menu {\r\n	font-size: 14px;\r\n	font-weight: bold;\r\n	color: #204c89;\r\n}\r\n.border4 {\r\n	border: 1px solid #A1B5CE;\r\n}\r\n.STYLE3 {\r\n	color: #474747\r\n}\r\n.address {\r\n	color: #0f0f0f\r\n}\r\na{text-decoration:none;color:#286e94;\r\n}\r\na:hover{\r\n	text-decoration:none;color:#fd0100;\r\n} \r\n\r\n-->\r\n</style>\r\n<script type=\"text/javascript\">\r\n     function queryMoreSpeical(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/admin/special/indexMore.do\"\r\n     }\r\n\r\n</script>\r\n</head>\r\n<body>\r\n<table width=\"1002\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">\r\n  <tr>\r\n    <td>&nbsp;</td>\r\n  </tr>\r\n</table>\r\n<table width=\"1002\" height=\"694\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">\r\n  <tr>\r\n    <td width=\"250\" height=\"694\" valign=\"top\"><table width=\"242\" height=\"222\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"border4\">\r\n      <tr>\r\n        <td width=\"240\" height=\"220\" valign=\"top\"><table width=\"240\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n          <tr>\r\n            <td height=\"25\" background=\"${ctx}/ResRoot/index/images/lanmu_tt.gif\"><table width=\"200\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n              <tr>\r\n                \r\n                <td width=\"118\" background=\"${ctx}/ResRoot/index/images/bgbfg.gif\"><div align=\"center\" class=\"whitefont\">用户信息</div></td>\r\n                <td width=\"31\">&nbsp;</td>\r\n                <td width=\"51\">&nbsp;</td>\r\n              </tr>\r\n            </table></td>\r\n          </tr>\r\n          <tr><td bgcolor=\"FAFAFA\"><#if req.getUserPrincipal()??>\r\n              <@FreeMarker_Template:loginsuccess/><#else><@FreeMarker_Template:login/></#if>\r\n          </td></tr>\r\n        </table>\r\n       </td>\r\n      </tr>\r\n    </table>    <br />\r\n    <table width=\"240\" height=\"222\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"border4\">\r\n      <tr>\r\n        <td height=\"220\" valign=\"top\"><table width=\"240\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr>\r\n              <td height=\"25\" background=\"${ctx}/ResRoot/index/images/lanmu_tt.gif\"><table width=\"200\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                  <tr>\r\n                    \r\n                    <td width=\"118\" background=\"${ctx}/ResRoot/index/images/bgbfg.gif\"><div align=\"center\" class=\"whitefont\">邮件订阅</div></td>\r\n                    <td width=\"31\">&nbsp;</td>\r\n                    <td width=\"51\">&nbsp;</td>\r\n                  </tr>\r\n              </table></td>\r\n            </tr>\r\n          </table>\r\n          <@FreeMarker_Template:setEmail/></td>\r\n      </tr>\r\n    </table>\r\n    <br />\r\n    <table width=\"242\" height=\"222\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"border4\">\r\n      <tr>\r\n        <td width=\"240\" height=\"218\" valign=\"top\"><table width=\"240\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr>\r\n              <td height=\"25\" background=\"${ctx}/ResRoot/index/images/lanmu_tt.gif\"><table width=\"240\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                  <tr>\r\n                    <td width=\"118\" background=\"${ctx}/ResRoot/index/images/bgbfg.gif\"><div align=\"center\" class=\"whitefont\">友情连接</div></td>\r\n                    <td width=\"60\">&nbsp;</td>\r\n                    <td width=\"62\">&nbsp;</td>\r\n                  </tr>\r\n              </table></td>\r\n            </tr>\r\n          </table>\r\n          <table width=\"100%\" height=\"180\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr><td height=\"8\" colspan=\"3\"></td></tr>\r\n			<tr>\r\n              <td width=\"2\">&nbsp;</td>\r\n              <td width=\"236\" valign=\"top\"><div style=\"overflow:auto;height:180px;\"><@FreeMarker_Template:link/></div></td>\r\n              <td width=\"2\">&nbsp;</td>\r\n            </tr>\r\n          </table></td>\r\n      </tr>\r\n    </table></td>\r\n    <td valign=\"top\"><table width=\"752\" height=\"222\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"border4\">\r\n      <tr>\r\n        <td height=\"220\" valign=\"top\"><table width=\"750\" height=\"29\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr>\r\n              <td width=\"38\" background=\"${ctx}/ResRoot/index/images/bgbgbg.gif\"  style=\"border-bottom:1px solid #d6d6d6\"><img src=\"${ctx}/ResRoot/index/images/t.gif\" width=\"38\" height=\"29\" /></td>\r\n              <td width=\"89\" bgcolor=\"#FCFCFC\"  style=\"border-bottom:1px solid #d6d6d6\"><strong>地 震 专 题</strong></td>\r\n              <td width=\"562\"  style=\"border-bottom:1px solid #d6d6d6\" background=\"${ctx}/ResRoot/index/images/bgbgbg2.gif\">&nbsp;</td>\r\n              <td width=\"48\"  style=\"border-bottom:1px solid #d6d6d6\" background=\"${ctx}/ResRoot/index/images/bgbgbg2.gif\">\r\n<a href=\"#\" onclick=\"queryMoreSpeical()\">更多</a>&nbsp;</td>\r\n            </tr>\r\n          </table>\r\n            <table width=\"750\" height=\"189\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr>\r\n              <td width=\"6\" height=\"189\">&nbsp;</td>\r\n              <td width=\"285\" align=\"center\" valign=\"middle\">\r\n                <@FreeMarker_Template:flash/>\r\n              </td>\r\n              <td width=\"12\" valign=\"middle\">&nbsp;</td>\r\n              <td width=\"447\" valign=\"top\"><@FreeMarker_Template:dynamicwork/></td>\r\n              </tr>\r\n          </table></td>\r\n      </tr>\r\n    </table>\r\n      <br />\r\n    <table width=\"752\" height=\"222\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"border4\">\r\n      <tr>\r\n        <td height=\"220\" valign=\"top\"><table width=\"750\" height=\"29\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr>\r\n              <td width=\"38\" background=\"${ctx}/ResRoot/index/images/bgbgbg.gif\"  style=\"border-bottom:1px solid #d6d6d6\"><img src=\"${ctx}/ResRoot/index/images/t.gif\" width=\"38\" height=\"29\" /></td>\r\n              <td width=\"90\" bgcolor=\"#FCFCFC\"  style=\"border-bottom:1px solid #d6d6d6\"><strong>近期地震目录</strong></td>\r\n              <td width=\"624\"  style=\"border-bottom:1px solid #d6d6d6\" background=\"${ctx}/ResRoot/index/images/bgbgbg2.gif\">&nbsp;</td>\r\n            </tr>\r\n          </table>\r\n          <table width=\"750\" height=\"189\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr>\r\n              <td width=\"24\" height=\"189\">&nbsp;</td>\r\n              <td width=\"344\" valign=\"middle\">\r\n			  	<table width=\"100%\" border=\"0\" \r\nalign=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n<#if CzCatBean.queryHomeCzCats(0,6)??>\r\n         <#list CzCatBean.queryHomeCzCats(0,6) as root>\r\n                  <tr>\r\n                    <td height=\"29\" align=\"left\" valign=\"middle\"><table width=\"100%\" height=\"29\" border=\"0\" \r\ncellpadding=\"0\" cellspacing=\"0\">\r\n                      <tr>\r\n                        <td width=\"3%\" align=\"center\" valign=\"middle\"><img src=\"${ctx}/ResRoot/index/images/2.gif\" \r\nwidth=\"3\" height=\"7\" /></td>\r\n                        <td width=\"97%\" align=\"left\" valign=\"middle\"><span class=\"STYLE3\">\r\n<font color=\"#286e94\">\r\n${root.O_TIME?string(\"yyyy年MM月dd日HH点mm分\")}<#if root.LOCATION_CNAME ??>\r\n${root.LOCATION_CNAME}</#if>发生${root.M?substring(0,3)}级地震\r\n</font>\r\n</span></td>\r\n                      </tr>\r\n                    </table></td>\r\n                  </tr>\r\n          </#list></#if>\r\n                </table>\r\n			  </td>\r\n              <td width=\"6\" valign=\"middle\"><img src=\"${ctx}/ResRoot/index/images/index_17.gif\" width=\"6\" \r\nheight=\"180\" alt=\"\" /></td>\r\n              <td width=\"25\" >\r\n			  </td>\r\n              <td width=\"344\" valign=\"middle\">\r\n			  	 <table width=\"100%\" border=\"0\" \r\nalign=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n<#if CzCatBean.queryHomeCzCats(6,6)??>\r\n<#list CzCatBean.queryHomeCzCats(6,6) as root>\r\n                  <tr>\r\n                    <td height=\"29\" align=\"left\" valign=\"middle\"><table width=\"100%\" height=\"29\" border=\"0\" \r\ncellpadding=\"0\" cellspacing=\"0\">\r\n                      <tr>\r\n                        <td width=\"3%\" align=\"center\" valign=\"middle\"><img src=\"${ctx}/ResRoot/index/images/2.gif\" \r\nwidth=\"3\" height=\"7\" /></td>\r\n                        <td width=\"97%\" align=\"left\" valign=\"middle\"><span class=\"STYLE3\">\r\n<font color=\"#286e94\">\r\n${root.O_TIME?string(\"yyyy年MM月dd日HH点mm分\")}<#if root.LOCATION_CNAME ??>\r\n${root.LOCATION_CNAME}</#if>发生${root.M?substring(0,3)}级地震\r\n</font>\r\n</span></td>\r\n                      </tr>\r\n                    </table></td>\r\n                  </tr>\r\n          </#list></#if>\r\n                </table>\r\n			  </td>\r\n            </tr>\r\n          </table></td>\r\n      </tr>\r\n    </table>\r\n    <br />\r\n    <table width=\"752\" height=\"222\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n      <tr>\r\n        <td width=\"382\" height=\"222\" valign=\"top\"><table width=\"370\" height=\"222\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"border4\">\r\n          <tr>\r\n            <td height=\"220\" valign=\"top\"><table width=\"368\" height=\"29\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n              <tr>\r\n                <td width=\"38\" background=\"${ctx}/ResRoot/index/images/bgbgbg.gif\"  style=\"border-bottom:1px solid #d6d6d6\"><img src=\"${ctx}/ResRoot/index/images/t.gif\" width=\"38\" height=\"29\" /></td>\r\n                <td width=\"73\" background=\"${ctx}/ResRoot/index/images/bgbgbg.gif\" bgcolor=\"#FCFCFC\"  style=\"border-bottom:1px solid #d6d6d6\"><strong>工 作 信 息</strong></td>\r\n                <td width=\"207\" background=\"${ctx}/ResRoot/index/images/bgbgbg2.gif\"  style=\"border-bottom:1px solid #d6d6d6\">&nbsp;</td>\r\n                <td width=\"48\" background=\"${ctx}/ResRoot/index/images/bgbgbg2.gif\"  style=\"border-bottom:1px solid #d6d6d6\"><a href=\"${ctx}/dynamicwork/index.shtml\">更多</a>&nbsp;</td>\r\n              </tr>\r\n              <tr><td height=\"15\" colspan=\"4\"></td></tr>\r\n              <#list ArticleBean.getArtsByCatName(\"工作信息\",6) as root> \r\n                     <tr>\r\n                       <td height=\"29\" colspan=\"4\" align = \"left\" valign=\"top\">\r\n                           <div class=\"arttitle\">\r\n                               &nbsp;<a href=\"${ctx}${root.path}\" target=\"_blank\">·${root.title}</a>\r\n                           </div>\r\n                       </td>\r\n                     </tr>    \r\n                  </#list>\r\n            </table></td>\r\n          </tr>\r\n        </table></td>\r\n        <td width=\"370\" valign=\"top\"><table width=\"370\" height=\"222\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"border4\">\r\n          <tr>\r\n            <td height=\"220\" valign=\"top\"><table width=\"367\" height=\"29\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                  <td width=\"38\" background=\"${ctx}/ResRoot/index/images/bgbgbg.gif\"  style=\"border-bottom:1px solid #d6d6d6\"><img src=\"${ctx}/ResRoot/index/images/t.gif\" width=\"38\" height=\"29\" /></td>\r\n                  <td width=\"75\" background=\"${ctx}/ResRoot/index/images/bgbgbg.gif\" bgcolor=\"#FAFBFD\"  style=\"border-bottom:1px solid #d6d6d6\"><strong>法 规 标 准</strong></td>\r\n                  <td width=\"207\" background=\"${ctx}/ResRoot/index/images/bgbgbg2.gif\"  style=\"border-bottom:1px solid #d6d6d6\">&nbsp;</td>\r\n                  <td width=\"48\" background=\"${ctx}/ResRoot/index/images/bgbgbg2.gif\"  style=\"border-bottom:1px solid #d6d6d6\"><a href=\"${ctx}/regulations/index.shtml\">更多</a>&nbsp;</td>\r\n                </tr>\r\n                <tr><td height=\"15\" colspan=\"4\"></td></tr>\r\n              <#list ArticleBean.getArtsByCatName(\"数据共享规章制度\",6) as root> \r\n                     <tr>\r\n                       <td height=\"29\" colspan=\"4\" align = \"left\" valign=\"top\">\r\n                           <div class=\"arttitle\">\r\n                               &nbsp;<a href=\"${ctx}${root.path}\" target=\"_blank\">·${root.title}</a>\r\n                           </div>\r\n                       </td>\r\n                     </tr>    \r\n                  </#list>\r\n\r\n            </table></td>\r\n          </tr>\r\n        </table></td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n</table>\r\n</body>','首页主体显示模板',NULL,'0',NULL,'main','3',133),
 (163841,'<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title><#if siteCfg.cmsName??>${siteCfg.cmsName}<#else>&nbsp;</#if></title>\r\n<style type=\"text/css\">\r\n<!--\r\n*{margin:0;padding:0;border:0;}\r\n#nav {\r\n  line-height: 24px;  list-style-type: none; background:#666;\r\n  font-size: 14px;\r\n  font-weight: bold;\r\n  color: #204c89;\r\n}\r\n#nav a {\r\n display: block; width: 80px; text-align:center;\r\n}\r\n#nav a:link  {\r\n text-decoration:none;\r\n}\r\n#nav a:visited  {\r\n text-decoration:none;\r\n}\r\n#nav a:hover  {\r\n text-decoration:none;font-weight:bold;\r\n}\r\n#nav li {\r\n float: left; width: 80px;\r\n}\r\n#nav li ul {\r\n line-height: 27px;  list-style-type: none;text-align:left;\r\n left: -999em; width: 180px; position: absolute; \r\n}\r\n#nav li ul li{\r\n float: left; width: 180px;\r\n background: #F6F6F6; \r\n margin-left: 8px;\r\n}\r\n#nav li ul a{\r\n display: block; width: 156px;text-align:left;padding-left:24px;\r\n}\r\n#nav li ul a:link  {\r\n color:#666; text-decoration:none;\r\n}\r\n#nav li ul a:visited  {\r\n color:#666;text-decoration:none;\r\n}\r\n#nav li ul a:hover  {\r\n color:#FF0000;text-decoration:none;font-weight:normal;\r\n background:#D3E8F8;\r\n}\r\n#nav li:hover ul {\r\n left: auto;\r\n}\r\n#nav li.sfhover ul {\r\n left: auto;\r\n}\r\n#content {\r\n clear: left; \r\n}\r\n-->\r\n</style>\r\n\r\n<script type=text/javascript><!--//--><![CDATA[//><!--\r\nfunction menuFix() {\r\n var sfEls = document.getElementById(\"nav\").getElementsByTagName(\"li\");\r\n //alert(sfEls.length);\r\n for (var i=0; i<sfEls.length; i++) {\r\n  sfEls[i].onmouseover=function() {\r\n  this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n  }\r\n  sfEls[i].onMouseDown=function() {\r\n  this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n  }\r\n  sfEls[i].onMouseUp=function() {\r\n  this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n  }\r\n  sfEls[i].onmouseout=function() {\r\n  this.className=this.className.replace(new RegExp(\"( ?|^)sfhover\\\\b\"), \"\");\r\n  }\r\n }\r\n}\r\nwindow.onload=menuFix;\r\n//--><!]]></script>\r\n\r\n\r\n<style type=\"text/css\">\r\n<!--\r\n.arttitle{width:340px;\r\nwhite-space:nowrap;\r\nword-break:keep-all;\r\noverflow:hidden;\r\ntext-overflow:ellipsis;}　　\r\n.textbox{\r\n    border:1px solid #AADFFF;\r\n}\r\nbody {\r\n	margin-left: 0px;\r\n	margin-top: 0px;\r\n	margin-right: 0px;\r\n	margin-bottom: 0px;\r\n	font-family:\"宋体\";\r\n	font-size:12px;\r\n	color:#2d6fa9;\r\n}\r\n.menu {\r\n	font-size: 14px;\r\n	font-weight: bold;\r\n	color: #204c89;\r\n}\r\n.STYLE3 {\r\n	color: #474747\r\n}\r\n.address {\r\n	color: #0f0f0f\r\n}\r\na{text-decoration:none;color:#286e94;\r\n}\r\na:hover{\r\n	text-decoration:none;color:#fd0100;\r\n} \r\n\r\n-->\r\n</style>\r\n  <script type=\"text/javascript\">\r\n     function addBookmark() {\r\n        if (window.sidebar) { \r\n            window.sidebar.addPanel(document.title, location.href,\"\"); \r\n        } else if( document.all ) {\r\n            window.external.AddFavorite( location.href, document.title);\r\n        } else if( window.opera && window.print ) {\r\n            return true;\r\n        }\r\n     }\r\n  </script>\r\n\r\n  <script type=\"text/javascript\">\r\n     function queryCzCats(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/index.do?model.tableName=\"+cltName;\r\n     }\r\n     function queryCzCatsByRound(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/indexRound.do?model.tableName=\"+cltName;\r\n     }\r\n\r\n     function queryCatPhases(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/indexPhase.do?model.tableName=\"+cltName;\r\n     }\r\n     function queryCatSeeds(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/indexSeed.do?model.tableName=\"+cltName;\r\n     }\r\n     function setMailsOfSeis(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/email/seismic/index.do\"\r\n     }\r\n     function stationSeeds(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/station/stationSeeds.do\"\r\n     }\r\n\r\n     function queryStations(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/station/list.do\"\r\n     }\r\n     function queryInstrument(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/instrument/index.do\"\r\n     }\r\n\r\n  </script>\r\n</head>\r\n\r\n<body>\r\n<table width=\"1002\" align=\"center\" style=\"margin:0 auto\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td width=\"1002\" height=\"126\" align=\"center\" valign=\"top\" background=\"${ctx}/ResRoot/index/images/index_01.jpg\"><div align=\"right\">\r\n<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\"1002\" height=\"126\">\r\n        <param name=\"movie\" value=\"${ctx}/ResRoot/index/flash/21.swf\" />\r\n        <param name=\"quality\" value=\"high\" />\r\n        <param name=\"wmode\" value=\"transparent\" />\r\n        <embed src=\"${ctx}/ResRoot/index/flash/21.swf\" width=\"1003\" height=\"126\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" wmode=\"transparent\"></embed>\r\n      </object></div>\r\n    </td>\r\n  </tr>\r\n  <tr>\r\n<td height=\"35\" align=\"right\" valign=\"top\" background=\"${ctx}/ResRoot/index/images/index_02.gif\">\r\n<#if req.getUserPrincipal()??>\r\n\r\n<@FreeMarker_Template:top_login/><#else><@FreeMarker_Template:top_unlogin/></#if>\r\n   </td>\r\n  </tr>\r\n  <tr>\r\n  	<td><div id=\"main_info\"><@FreeMarker_Template:main/></div></td>\r\n  </tr>\r\n  <tr>\r\n  	<td><div id=\"cta_info\" style=\"padding:0px;margin:0px; display: none;\">\r\n    <iframe src=\"\" id=\"main\" name=\"main\" style=\"width:100%; height:430px; border:0px;\" frameborder=\"0\"></iframe>\r\n</div></td>\r\n  </tr>\r\n  <tr>\r\n  	<td><@FreeMarker_Template:foot/></td>\r\n  </tr>\r\n</table>\r\n</body>','主页模板',NULL,'1',NULL,'index','2',417),
 (196609,'<@FreeMarker_Template:top/>\r\n<div id=\"main_info\">\r\n<table width=\"800\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td width=\"800\" height=\"46\" valign=\"middle\" background=\"${ctx}/ResRoot/index/images/0001.gif\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"45px\">&nbsp;</td><td style=\"padding-top:6px;\">\r\n您现在的位置：<a \r\nhref=\"${ctx}/index.shtml\">首页</a>&nbsp;-&gt;&nbsp;${catalog.name}</td></tr></table></td>\r\n</tr>\r\n<tr>\r\n<td height=\"391\" background=\"${ctx}/ResRoot/index/images/bg.gif\"><@FreeMarker_Template:artslist/>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"${ctx}/ResRoot/index/images/bottom.gif\" width=\"800\" height=\"24\"></td>\r\n</tr>\r\n</table>\r\n</div>\r\n<@FreeMarker_Template:foot/>  ','所有不含有子级的栏目通用模板',NULL,'1',NULL,'catalog','0',104),
 (196610,'<#if req.getUserPrincipal()??>\r\n<@FreeMarker_Template:top_catalog_login/><#else><@FreeMarker_Template:top_catalog_unlogin/></#if>\r\n\r\n<div id=\"main_info\" style=\"margin:0 auto\" align=\"center\">\r\n<table width=\"800\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td width=\"800\" height=\"46\" valign=\"middle\" background=\"${ctx}/ResRoot/index/images/0001.gif\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"45px\">&nbsp;</td><td style=\"padding-top:6px;\" align=\"left\">\r\n您现在的位置：<a href=\"${ctx}/index.shtml\">首页</a>&nbsp;-&gt;&nbsp;${path}${article.title}</td></tr></table></td>\r\n</tr>\r\n<tr>\r\n<td height=\"391\" background=\"${ctx}/ResRoot/index/images/bg.gif\"><table width=\"800\" border=\"0\" align=\"center\">\r\n  <tr>\r\n    <td>\r\n	  <table width=\"100%\" border=\"0\" align=\"center\">\r\n		<tr>\r\n			<td height=\"50px\" align=\"center\" valign=\"middle\">\r\n			  <font style=\"color:#0e3977; font-size:20px;\"><b>${article.title}</b></font>\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td height=\"36px\" align=\"center\" valign=\"middle\">\r\n			  <font color=\"#1c638b\">发布时间：<#if article.createTime??>\r\n${article.createTime?string(\"yyyy-MM-dd\")}</#if>&nbsp;&nbsp;&nbsp;录入人：${article.author}</font>\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td align=\"left\" valign=\"middle\" style=\"padding:5px 10px 5px 10px;\">\r\n			  <font size=\"4\" color=\"#4a4a4a\">${article.content}</font>\r\n			  <p><#if article.attachmentses??>\r\n			  	<#list article.attachmentses as attachments>\r\n					&nbsp;&nbsp;&nbsp;&nbsp;附件下载：<a href=\"${ctx}${attachments.path}\">${attachments.name}</a>\r\n				</#list>\r\n			  </#if></p></td>\r\n		</tr>\r\n      </table>\r\n	</td>	\r\n  </tr>\r\n</table>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"${ctx}/ResRoot/index/images/bottom.gif\" width=\"800\" height=\"24\"></td>\r\n</tr>\r\n</table>\r\n</div>\r\n<@FreeMarker_Template:foot/>','',NULL,'1',NULL,'article','1',75),
 (884736,'<table style=\"vertical-align: top;\">\r\n<tr>\r\n<td class=\"menu\"><a href=\"${ctx}/index.shtml\">首页</a>&nbsp;&nbsp;</td>\r\n<td>\r\n<ul id=\"nav\">\r\n<li><a href=\"#\">台站仪器</a>\r\n <ul>\r\n    <li><a href=\"#\" onclick=\"queryStations()\">台站查询</a></li>\r\n    <li><a href=\"#\" onclick=\"queryInstrument()\">仪器查询</a></li>\r\n </ul>\r\n</li>\r\n\r\n<li><a href=\"#\">地震目录</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuCzCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCzCats(\'${czcats.cltName}\')\">${czcats.clcName}(矩形)</a></li>\r\n </#list>\r\n<#list CzCatBean.queryMenuCzCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCzCatsByRound(\'${czcats.cltName}\')\">${czcats.clcName}(圆形)</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n\r\n<li><a href=\"#\">震相数据</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuPhaseCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCatPhases(\'${czcats.cltName}\')\">${czcats.clcName}</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n\r\n<li><a href=\"#\">事件波形</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuSeedCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCatSeeds(\'${czcats.cltName}\')\">${czcats.clcName}</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n\r\n</ul>\r\n</td>\r\n<td class=\"menu\">&nbsp;<a href=\"#\" onclick=\"stationSeeds()\">连续波形</a>&nbsp;</td>\r\n\r\n<td class=\"menu\">&nbsp;<a href=\"#\" onclick=\"setMailsOfSeis()\">邮件订阅</a>&nbsp;</td>\r\n\r\n<td>&nbsp;\r\n<span class=\"menu\">\r\n    \r\n     <#list CatalogBean.getNavigation(0,10) as catalog>\r\n       <#if catalog.type == \'1\'>\r\n        <a href=\"${ctx}${catalog.rootPath}/index.shtml\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n       <#else>\r\n          <#if CatalogBean.isStartHttp(\"${catalog.linkUrl}\")=true>\r\n              <a href=\"${catalog.linkUrl}\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n          <#else>\r\n              <a href=\"${ctx}/${catalog.linkUrl}\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n          </#if>    \r\n       </#if>\r\n     </#list>\r\n  </span>\r\n\r\n</td>\r\n</tr>\r\n</table>','登录后的top页',NULL,'0',NULL,'top_login','3',6),
 (884737,'<table style=\"vertical-align: top\">\r\n<tr>\r\n<td class=\"menu\"><a href=\"${ctx}/index.shtml\">首页</a>&nbsp;&nbsp;</td>\r\n<td>\r\n<ul id=\"nav\">\r\n<li><a href=\"#\">台站仪器</a>\r\n <ul>\r\n    <li><a href=\"#\" onclick=\"queryStations()\">台站查询</a></li>\r\n    <li><a href=\"#\" onclick=\"queryInstrument()\">仪器查询</a></li>\r\n </ul>\r\n</li>\r\n\r\n<li><a href=\"#\">地震目录</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuCzCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCzCats(\'${czcats.cltName}\')\">${czcats.clcName}(矩形)</a></li>\r\n </#list>\r\n<#list CzCatBean.queryMenuCzCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCzCatsByRound(\'${czcats.cltName}\')\">${czcats.clcName}(圆形)</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n<!--\r\n<li><a href=\"#\">震相数据</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuPhaseCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCatPhases(\'${czcats.cltName}\')\">${czcats.clcName}</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n\r\n<li><a href=\"#\">事件波形</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuSeedCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCatSeeds(\'${czcats.cltName}\')\">${czcats.clcName}</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n\r\n</ul>\r\n</td>\r\n<td class=\"menu\">&nbsp;<a href=\"#\" onclick=\"stationSeeds()\">连续波形</a>&nbsp;</td>\r\n\r\n<td class=\"menu\">&nbsp;<a href=\"#\" onclick=\"setMailsOfSeis()\">邮件订阅</a>&nbsp;</td>\r\n-->\r\n<td>&nbsp;\r\n<span class=\"menu\">\r\n    \r\n     <#list CatalogBean.getNavigation(0,10) as catalog>\r\n       <#if catalog.type == \'1\'>\r\n        <a href=\"${ctx}${catalog.rootPath}/index.shtml\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n       <#else>\r\n          <#if CatalogBean.isStartHttp(\"${catalog.linkUrl}\")=true>\r\n              <a href=\"${catalog.linkUrl}\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n          <#else>\r\n              <a href=\"${ctx}/${catalog.linkUrl}\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n          </#if>    \r\n       </#if>\r\n     </#list>\r\n  </span>\r\n\r\n</td>\r\n</tr>\r\n</table>','未登录的top页',NULL,'0',NULL,'top_unlogin','3',2),
 (884738,'<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html \r\n\r\nxmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title><#if siteCfg.cmsName??>${siteCfg.cmsName}<#else>&nbsp;</#if></title>\r\n<style type=\"text/css\">\r\n<!--\r\n*{margin:0;padding:0;border:0;}\r\n#nav {\r\n  line-height: 24px;  list-style-type: none; background:#666;\r\n  font-size: 14px;\r\n  font-weight: bold;\r\n  color: #204c89;\r\n}\r\n#nav a {\r\n display: block; width: 80px; text-align:center;\r\n}\r\n#nav a:link  {\r\n text-decoration:none;\r\n}\r\n#nav a:visited  {\r\n text-decoration:none;\r\n}\r\n#nav a:hover  {\r\n text-decoration:none;font-weight:bold;\r\n}\r\n#nav li {\r\n float: left; width: 80px;\r\n}\r\n#nav li ul {\r\n line-height: 27px;  list-style-type: none;text-align:left;\r\n left: -999em; width: 180px; position: absolute; \r\n}\r\n#nav li ul li{\r\n float: left; width: 180px;\r\n background: #F6F6F6; \r\n margin-left: 8px;\r\n}\r\n#nav li ul a{\r\n display: block; width: 156px;text-align:left;padding-left:24px;\r\n}\r\n#nav li ul a:link  {\r\n color:#666; text-decoration:none;\r\n}\r\n#nav li ul a:visited  {\r\n color:#666;text-decoration:none;\r\n}\r\n#nav li ul a:hover  {\r\n color:#FF0000;text-decoration:none;font-weight:normal;\r\n background:#D3E8F8;\r\n}\r\n#nav li:hover ul {\r\n left: auto;\r\n}\r\n#nav li.sfhover ul {\r\n left: auto;\r\n}\r\n#content {\r\n clear: left; \r\n}\r\n-->\r\n</style>\r\n\r\n<script type=text/javascript><!--//--><![CDATA[//><!--\r\nfunction menuFix() {\r\n var sfEls = document.getElementById(\"nav\").getElementsByTagName(\"li\");\r\n //alert(sfEls.length);\r\n for (var i=0; i<sfEls.length; i++) {\r\n  sfEls[i].onmouseover=function() {\r\n  this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n  }\r\n  sfEls[i].onMouseDown=function() {\r\n  this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n  }\r\n  sfEls[i].onMouseUp=function() {\r\n  this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n  }\r\n  sfEls[i].onmouseout=function() {\r\n  this.className=this.className.replace(new RegExp(\"( ?|^)sfhover\\\\b\"), \"\");\r\n  }\r\n }\r\n}\r\nwindow.onload=menuFix;\r\n//--><!]]></script>\r\n\r\n\r\n<style type=\"text/css\">\r\n<!--\r\n.arttitle{width:340px;\r\nwhite-space:nowrap;\r\nword-break:keep-all;\r\noverflow:hidden;\r\ntext-overflow:ellipsis;}　　\r\n.textbox{\r\n    border:1px solid #AADFFF;\r\n}\r\nbody {\r\n	margin-left: 0px;\r\n	margin-top: 0px;\r\n	margin-right: 0px;\r\n	margin-bottom: 0px;\r\n	font-family:\"宋体\";\r\n	font-size:12px;\r\n	color:#2d6fa9;\r\n}\r\n.menu {\r\n	font-size: 14px;\r\n	font-weight: bold;\r\n	color: #204c89;\r\n}\r\n.STYLE3 {\r\n	color: #474747\r\n}\r\n.address {\r\n	color: #0f0f0f\r\n}\r\na{text-decoration:none;color:#286e94;\r\n}\r\na:hover{\r\n	text-decoration:none;color:#fd0100;\r\n} \r\n\r\n-->\r\n</style>\r\n  <script type=\"text/javascript\">\r\n     function addBookmark() {\r\n        if (window.sidebar) { \r\n            window.sidebar.addPanel(document.title, location.href,\"\"); \r\n        } else if( document.all ) {\r\n            window.external.AddFavorite( location.href, document.title);\r\n        } else if( window.opera && window.print ) {\r\n            return true;\r\n        }\r\n     }\r\n  </script>\r\n\r\n  <script type=\"text/javascript\">\r\n     function queryCzCats(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/index.do?model.tableName=\"+cltName;\r\n     }\r\n     function queryCzCatsByRound(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/indexRound.do?model.tableName=\"+cltName;\r\n     }\r\n\r\n     function queryCatPhases(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/indexPhase.do?model.tableName=\"+cltName;\r\n     }\r\n     function queryCatSeeds(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/indexSeed.do?model.tableName=\"+cltName;\r\n     }\r\n     function setMailsOfSeis(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/email/seismic/index.do\"\r\n     }\r\n     function stationSeeds(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/station/stationSeeds.do\"\r\n     }\r\n\r\n     function queryStations(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/station/list.do\"\r\n     }\r\n     function queryInstrument(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/instrument/index.do\"\r\n     }\r\n\r\n  </script>\r\n</head>\r\n\r\n<body>\r\n<table border=\"0\" width=\"1002\" style=\"margin:0 auto\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td width=\"1002\" height=\"126\" align=\"center\" valign=\"top\"  background=\"${ctx}/ResRoot/index/images/index_01.jpg\"><div align=\"right\">\r\n     <object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\"1002\" height=\"126\">\r\n        <param name=\"movie\" value=\"${ctx}/ResRoot/index/flash/21.swf\" />\r\n        <param name=\"quality\" value=\"high\" />\r\n        <param name=\"wmode\" value=\"transparent\" />\r\n        <embed src=\"${ctx}/ResRoot/index/flash/21.swf\" width=\"1002\" height=\"126\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" wmode=\"transparent\"></embed>\r\n      </object></div>\r\n    </td>\r\n  </tr>\r\n  <tr>\r\n<td height=\"35\" align=\"right\" valign=\"top\" background=\"${ctx}/ResRoot/index/images/index_02.gif\">\r\n\r\n<table style=\"vertical-align: top;\">\r\n<tr>\r\n<td class=\"menu\"><a href=\"${ctx}/index.shtml\">首页</a>&nbsp;&nbsp;</td>\r\n<td>\r\n<ul id=\"nav\">\r\n<li><a href=\"#\">台站仪器</a>\r\n <ul>\r\n    <li><a href=\"#\" onclick=\"queryStations()\">台站查询</a></li>\r\n    <li><a href=\"#\" onclick=\"queryInstrument()\">仪器查询</a></li>\r\n </ul>\r\n</li>\r\n\r\n<li><a href=\"#\">地震目录</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuCzCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCzCats(\'${czcats.cltName}\')\">${czcats.clcName}(矩形)</a></li>\r\n </#list>\r\n<#list CzCatBean.queryMenuCzCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCzCatsByRound(\'${czcats.cltName}\')\">${czcats.clcName}(圆形)</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n\r\n<li><a href=\"#\">震相数据</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuPhaseCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCatPhases(\'${czcats.cltName}\')\">${czcats.clcName}</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n\r\n<li><a href=\"#\">事件波形</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuSeedCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCatSeeds(\'${czcats.cltName}\')\">${czcats.clcName}</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n\r\n</ul>\r\n</td>\r\n<td class=\"menu\">&nbsp;<a href=\"#\" onclick=\"stationSeeds()\">连续波形</a>&nbsp;</td>\r\n\r\n<td class=\"menu\">&nbsp;<a href=\"#\" onclick=\"setMailsOfSeis()\">邮件订阅</a>&nbsp;</td>\r\n\r\n<td>&nbsp;\r\n<span class=\"menu\">\r\n    \r\n     <#list CatalogBean.getNavigation(0,10) as catalog>\r\n       <#if catalog.type == \'1\'>\r\n        <a href=\"${ctx}${catalog.rootPath}/index.shtml\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n       <#else>\r\n          <#if CatalogBean.isStartHttp(\"${catalog.linkUrl}\")=true>\r\n              <a href=\"${catalog.linkUrl}\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n          <#else>\r\n              <a href=\"${ctx}/${catalog.linkUrl}\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n          </#if>    \r\n       </#if>\r\n     </#list>\r\n  </span>\r\n\r\n</td>\r\n</tr>\r\n</table>\r\n   </td>\r\n  </tr>\r\n</table>\r\n<table align=\"center\" style=\"margin:0 auto\"><tr><td width=\"1003\">\r\n<div id=\"cta_info\" style=\"padding:0px;margin:0px; display: none;\">\r\n    <iframe src=\"\" id=\"main\" name=\"main\" style=\"width:100%; height:430px; border:0px;\" frameborder=\"0\"></iframe>\r\n</div>\r\n</td></tr></table>\r\n</body>','登录后的栏目相关top页',NULL,'0',NULL,'top_catalog_login','3',4),
 (884739,'<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html \r\n\r\nxmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title><#if siteCfg.cmsName??>${siteCfg.cmsName}<#else>&nbsp;</#if></title>\r\n<style type=\"text/css\">\r\n<!--\r\n*{margin:0;padding:0;border:0;}\r\n#nav {\r\n  line-height: 24px;  list-style-type: none; background:#666;\r\n  font-size: 14px;\r\n  font-weight: bold;\r\n  color: #204c89;\r\n}\r\n#nav a {\r\n display: block; width: 80px; text-align:center;\r\n}\r\n#nav a:link  {\r\n text-decoration:none;\r\n}\r\n#nav a:visited  {\r\n text-decoration:none;\r\n}\r\n#nav a:hover  {\r\n text-decoration:none;font-weight:bold;\r\n}\r\n#nav li {\r\n float: left; width: 80px;\r\n}\r\n#nav li ul {\r\n line-height: 27px;  list-style-type: none;text-align:left;\r\n left: -999em; width: 180px; position: absolute; \r\n}\r\n#nav li ul li{\r\n float: left; width: 180px;\r\n background: #F6F6F6; \r\n margin-left: 8px;\r\n}\r\n#nav li ul a{\r\n display: block; width: 156px;text-align:left;padding-left:24px;\r\n}\r\n#nav li ul a:link  {\r\n color:#666; text-decoration:none;\r\n}\r\n#nav li ul a:visited  {\r\n color:#666;text-decoration:none;\r\n}\r\n#nav li ul a:hover  {\r\n color:#FF0000;text-decoration:none;font-weight:normal;\r\n background:#D3E8F8;\r\n}\r\n#nav li:hover ul {\r\n left: auto;\r\n}\r\n#nav li.sfhover ul {\r\n left: auto;\r\n}\r\n#content {\r\n clear: left; \r\n}\r\n-->\r\n</style>\r\n\r\n<script type=text/javascript><!--//--><![CDATA[//><!--\r\nfunction menuFix() {\r\n var sfEls = document.getElementById(\"nav\").getElementsByTagName(\"li\");\r\n //alert(sfEls.length);\r\n for (var i=0; i<sfEls.length; i++) {\r\n  sfEls[i].onmouseover=function() {\r\n  this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n  }\r\n  sfEls[i].onMouseDown=function() {\r\n  this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n  }\r\n  sfEls[i].onMouseUp=function() {\r\n  this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n  }\r\n  sfEls[i].onmouseout=function() {\r\n  this.className=this.className.replace(new RegExp(\"( ?|^)sfhover\\\\b\"), \"\");\r\n  }\r\n }\r\n}\r\nwindow.onload=menuFix;\r\n//--><!]]></script>\r\n\r\n\r\n<style type=\"text/css\">\r\n<!--\r\n.arttitle{width:340px;\r\nwhite-space:nowrap;\r\nword-break:keep-all;\r\noverflow:hidden;\r\ntext-overflow:ellipsis;}　　\r\n.textbox{\r\n    border:1px solid #AADFFF;\r\n}\r\nbody {\r\n	margin-left: 0px;\r\n	margin-top: 0px;\r\n	margin-right: 0px;\r\n	margin-bottom: 0px;\r\n	font-family:\"宋体\";\r\n	font-size:12px;\r\n	color:#2d6fa9;\r\n}\r\n.menu {\r\n	font-size: 14px;\r\n	font-weight: bold;\r\n	color: #204c89;\r\n}\r\n.STYLE3 {\r\n	color: #474747\r\n}\r\n.address {\r\n	color: #0f0f0f\r\n}\r\na{text-decoration:none;color:#286e94;\r\n}\r\na:hover{\r\n	text-decoration:none;color:#fd0100;\r\n} \r\n\r\n-->\r\n</style>\r\n  <script type=\"text/javascript\">\r\n     function addBookmark() {\r\n        if (window.sidebar) { \r\n            window.sidebar.addPanel(document.title, location.href,\"\"); \r\n        } else if( document.all ) {\r\n            window.external.AddFavorite( location.href, document.title);\r\n        } else if( window.opera && window.print ) {\r\n            return true;\r\n        }\r\n     }\r\n  </script>\r\n\r\n  <script type=\"text/javascript\">\r\n     function queryCzCats(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/index.do?model.tableName=\"+cltName;\r\n     }\r\n     function queryCzCatsByRound(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/indexRound.do?model.tableName=\"+cltName;\r\n     }\r\n\r\n     function queryCatPhases(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/indexPhase.do?model.tableName=\"+cltName;\r\n     }\r\n     function queryCatSeeds(cltName){\r\n         //alert(cltName);\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/data/catalog/indexSeed.do?model.tableName=\"+cltName;\r\n     }\r\n     function setMailsOfSeis(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/email/seismic/index.do\"\r\n     }\r\n     function stationSeeds(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/station/stationSeeds.do\"\r\n     }\r\n\r\n     function queryStations(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/station/list.do\"\r\n     }\r\n     function queryInstrument(){\r\n         document.getElementById(\"main_info\").style.display=\"none\";\r\n         document.getElementById(\"cta_info\").style.display=\"\";\r\n         window.parent.main.location.href=\"${ctx}/quake/seismic/instrument/index.do\"\r\n     }\r\n\r\n  </script>\r\n</head>\r\n\r\n<body>\r\n<table border=\"0\" width=\"1002\" style=\"margin:0 auto\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td width=\"1002\" height=\"126\" align=\"center\" valign=\"top\"  background=\"${ctx}/ResRoot/index/images/index_01.jpg\"><div align=\"right\">\r\n     <object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\"1002\" height=\"126\">\r\n        <param name=\"movie\" value=\"${ctx}/ResRoot/index/flash/21.swf\" />\r\n        <param name=\"quality\" value=\"high\" />\r\n        <param name=\"wmode\" value=\"transparent\" />\r\n        <embed src=\"${ctx}/ResRoot/index/flash/21.swf\" width=\"1002\" height=\"126\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" wmode=\"transparent\"></embed>\r\n      </object></div>\r\n    </td>\r\n  </tr>\r\n  <tr>\r\n<td height=\"35\" align=\"right\" valign=\"top\" background=\"${ctx}/ResRoot/index/images/index_02.gif\">\r\n\r\n<table style=\"vertical-align: top;\">\r\n<tr>\r\n<td class=\"menu\"><a href=\"${ctx}/index.shtml\">首页</a>&nbsp;&nbsp;</td>\r\n<td>\r\n<ul id=\"nav\">\r\n<li><a href=\"#\">台站仪器</a>\r\n <ul>\r\n    <li><a href=\"#\" onclick=\"queryStations()\">台站查询</a></li>\r\n    <li><a href=\"#\" onclick=\"queryInstrument()\">仪器查询</a></li>\r\n </ul>\r\n</li>\r\n\r\n<li><a href=\"#\">地震目录</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuCzCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCzCats(\'${czcats.cltName}\')\">${czcats.clcName}(矩形)</a></li>\r\n </#list>\r\n<#list CzCatBean.queryMenuCzCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCzCatsByRound(\'${czcats.cltName}\')\">${czcats.clcName}(圆形)</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n\r\n<!--\r\n<li><a href=\"#\">震相数据</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuPhaseCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCatPhases(\'${czcats.cltName}\')\">${czcats.clcName}</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n\r\n<li><a href=\"#\">事件波形</a>\r\n <ul>\r\n  <#list CzCatBean.queryMenuSeedCats() as czcats>\r\n    <li><a href=\"#\" onclick=\"queryCatSeeds(\'${czcats.cltName}\')\">${czcats.clcName}</a></li>\r\n </#list>\r\n </ul>\r\n</li>\r\n\r\n</ul>\r\n</td>\r\n<td class=\"menu\">&nbsp;<a href=\"#\" onclick=\"stationSeeds()\">连续波形</a>&nbsp;</td>\r\n\r\n<td class=\"menu\">&nbsp;<a href=\"#\" onclick=\"setMailsOfSeis()\">邮件订阅</a>&nbsp;</td>\r\n-->\r\n\r\n<td>&nbsp;\r\n<span class=\"menu\">\r\n    \r\n     <#list CatalogBean.getNavigation(0,10) as catalog>\r\n       <#if catalog.type == \'1\'>\r\n        <a href=\"${ctx}${catalog.rootPath}/index.shtml\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n       <#else>\r\n          <#if CatalogBean.isStartHttp(\"${catalog.linkUrl}\")=true>\r\n              <a href=\"${catalog.linkUrl}\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n          <#else>\r\n              <a href=\"${ctx}/${catalog.linkUrl}\" target=\"${catalog.target}\">${catalog.name}</a>&nbsp;\r\n          </#if>    \r\n       </#if>\r\n     </#list>\r\n  </span>\r\n\r\n</td>\r\n</tr>\r\n</table>\r\n   </td>\r\n  </tr>\r\n</table>\r\n<table align=\"center\" style=\"margin:0 auto\"><tr><td width=\"1003\">\r\n<div id=\"cta_info\" style=\"padding:0px;margin:0px; display: none;\">\r\n    <iframe src=\"\" id=\"main\" name=\"main\" style=\"width:100%; height:430px; border:0px;\" frameborder=\"0\"></iframe>\r\n</div>\r\n</td></tr></table>\r\n</body>','未登录时栏目相关的top页',NULL,'0',NULL,'top_catalog_unlogin','3',4),
 (1048576,'<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding-left:8px;\">\r\n<tr><td height=\"4px\" colspan=\"2\">&nbsp;</td></tr>\r\n      <tr>\r\n      <td width=\"98%\" height=\"29\" align = \"left\" valign=\"top\">\r\n        <div style=\"line-height:24px\">\r\n          <font color=\"#286e94\">\r\n            &nbsp;&nbsp;为系统用户提供地震目录邮件订阅服务，用户注册成为系统用户，登录系统后便可以进行邮件订阅\r\n            </font>\r\n         </div>\r\n      </td><td width=\"2%\"></td>\r\n    </tr>    \r\n</table>','左侧邮件订阅栏目',NULL,'0',NULL,'setEmail','3',6),
 (33554432,'<table width=\"100%\" border=\"0\">\r\n  <tr>\r\n    <td align=\"center\">\r\n<#if page.hasPreviousPage()>\r\n<a href=\"index.shtml?pageNo=1\">[首页]</a>\r\n<#else>\r\n[首页]\r\n</#if>\r\n<#if page.hasPreviousPage()>\r\n<a href=\"index.shtml?pageNo=${page.pageNo-1}\">[上页]</a>\r\n<#else>\r\n[上页]\r\n</#if>\r\n<#if page.getHasNextPage()>\r\n<a href=\"index.shtml?pageNo=${page.pageNo+1}\">[下页]</a>\r\n<#else>\r\n[下页]\r\n</#if>\r\n<#if page.getHasNextPage()>\r\n<a href=\"index.shtml?pageNo=${page.pages}\">[尾页]</a>\r\n<#else>\r\n[尾页]\r\n</#if>\r\n页次：<font color=\"#FF0000\">${page.pageNo}</font>/${page.pages}页\r\n\r\n共<font color=\"#FF0000\">${page.rows}</font>条记录\r\n    </td>\r\n  </tr>\r\n</table>','分页模板',NULL,'1',NULL,'page','3',1),
 (34275328,'<table width=\"210\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n    <#list LinkBean.getLinks() as root> \r\n        <tr><td height=\"5\"></td>\r\n        </tr>\r\n        <tr>\r\n            <td height=\"25\" align=\"left\" valign=\"middle\">\r\n               ·<a href=\"${root.siteUrl}\" target=\"_blank\">${root.siteName}</a>\r\n            </td>\r\n        </tr>\r\n   </#list>\r\n</table>','链接管理',NULL,'0',NULL,'link','3',16),
 (34308098,'<#assign pageNo= RequestParameters[\"pageNo\"]?default(1)?number>\r\n<#assign pageSize= RequestParameters[\"pageSize\"]?default(10)?number>\r\n<#assign page = ArticleBean.queryArticles(catalog.name, pageNo, pageSize)>\r\n<table width=\"100%\" border=\"0\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td height=\"315\" valign=\"top\">\r\n      <table>\r\n<#list page.data as article>\r\n    <tr>\r\n        <td width=\"11\" height=\"14px\" align=\"right\" valign=\"middle\">        </td>\r\n        <td width=\"14\" align=\"center\" valign=\"middle\">\r\n			<img src=\"${ctx}/ResRoot/index/images/2.gif\" width=\"3\" height=\"7\" /></td>\r\n        <td width=\"785\" height=\"14px\" align=\"left\" style=\"padding-bottom:8px\">\r\n　　　　　  <#if article.getLinkUrl()!=\"\">\r\n             <div style=\"width:450px;float:left;display:inline;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\">\r\n	      <a href=\"${article.linkUrl}\" class=\"gray\" target=\"_blank\" title=\"${article.title}\">${article.shortTitle}               </a>             </div>\r\n          <#else>\r\n             <div style=\"width:450px;float:left;display:inline;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\">\r\n                <a href=\"${ctx}${article.path}\" class=\"gray\"   target=\"_blank\" title=\"${article.title}\">${article.shortTitle}                </a>             </div>\r\n          </#if>       </td>\r\n     </tr>\r\n   </#list>\r\n      </table>\r\n    </td>\r\n  </tr>\r\n\r\n     <tr>\r\n     <td height=\"13\" >\r\n       <@FreeMarker_Template:page/>\r\n     </td>\r\n   </tr>\r\n</table>','文章题目列表',NULL,'0',NULL,'artslist','3',27),
 (34373632,'<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\">\r\n  <tr> \r\n    <td height=\"10\" valign=\"bottom\"><font color=\"#FFFFFF\">&nbsp;</font></td>\r\n  </tr>\r\n  <#list SoftwareBean.getSoftCatas() as root>  \r\n  <tr> \r\n    <td height=\"20\" align=\"left\" valign=\"top\">&nbsp;&nbsp;<a href=\"${ctx}/software/index.do?model.softCatalog.id=${root.id}\" target=\"myframe\">${root.name}</a></td>\r\n  </tr>\r\n</#list>\r\n</table>','软件下载左侧类别列表',NULL,'0',NULL,'softwareleft','3',15),
 (34373634,'<#if req.getUserPrincipal()??>\r\n<@FreeMarker_Template:top_catalog_login/><#else><@FreeMarker_Template:top_catalog_unlogin/></#if>\r\n<div id=\"main_info\" style=\"margin:0 auto;width:1003px;\">\r\n\r\n<table width=\"1003\" border=\"0\" height=\"417\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\r\n  <tr>\r\n    <td width=\"250px\" height=\"38\" valign=\"top\" background=\"${ctx}/ResRoot/index/images/index1_01_01.gif\"></td>\r\n    <td rowspan=\"2\" valign=\"top\"><table width=\"753\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n<tr>\r\n<td width=\"760\" height=\"46\" valign=\"middle\" background=\"${ctx}/ResRoot/index/images/0001shot.gif\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"45px\">&nbsp;</td><td style=\"padding-top:6px;\">\r\n您现在的位置：<a \r\nhref=\"${ctx}/index.shtml\">首页</a>&nbsp;-&gt;&nbsp;${catalog.name}</td></tr></table></td>\r\n</tr>\r\n            <tr>\r\n        <td height=\"355\" valign=\"top\"><table width=\"100%\" height=\"350\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr>\r\n              <td width=\"18\" valign=\"top\" background=\"${ctx}/ResRoot/index/images/index1_07.gif\">&nbsp;</td>\r\n              <td valign=\"top\" bgcolor=\"#FFFFFF\">\r\n<iframe width=\"747\" frameborder=\"5\" height=\"350\" name=\"myframe\" src=\"${ctx}/software/index.do\"></iframe></td>\r\n              <td width=\"19\" valign=\"top\" background=\"${ctx}/ResRoot/index/images/index1_09.gif\">&nbsp;</td>\r\n            </tr>\r\n        </table></td>\r\n      </tr>\r\n     </table>\r\n    </td>\r\n  </tr>\r\n  <tr>\r\n    <td valign=\"top\"><table width=\"248\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n      <tr>\r\n        <td width=\"10\" height=\"359\" background=\"${ctx}/ResRoot/index/images/index1_03.gif\">&nbsp;</td>\r\n        <td height=\"359\" background=\"${ctx}/ResRoot/index/images/index1_04.gif\" valign=\"top\"><@FreeMarker_Template:softwareleft/></td>\r\n        <td width=\"10\" background=\"${ctx}/ResRoot/index/images/index1_05.gif\">&nbsp;</td>\r\n      </tr>\r\n      <tr>\r\n        <td height=\"12\" colspan=\"3\"background=\"${ctx}/ResRoot/index/images/index1_12.gif\">&nbsp;</td>\r\n        </tr>\r\n    </table></td>\r\n  </tr>\r\n  <tr>\r\n    <td colspan=\"2\" height=\"12px\"></td>\r\n  </tr>\r\n</table>\r\n</div>\r\n<@FreeMarker_Template:foot/>','软件下载模板',NULL,'0',NULL,'software','0',75),
 (34537472,'<table width=\"1003\" height=\"97\" border=\"0\" style=\"margin:0 auto\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td align=\"center\" valign=\"middle\" background=\"${ctx}/ResRoot/index/images/index_34.gif\">\r\n<span class=\"address\">版权所有：<#if siteCfg.copyright??>${siteCfg.copyright} <#else>&nbsp;</#if>&nbsp;&nbsp;&nbsp;<a href=\"http://www.miibeian.gov.cn\" target=\"_blank\"><#if siteCfg.icpCode??>${siteCfg.icpCode}<#else>&nbsp;</#if></a></span><br/><br/>\r\n<span class=\"address\">地址：<#if siteCfg.address??>${siteCfg.address}<#else>&nbsp;</#if> &nbsp;&nbsp;&nbsp;邮编：<#if siteCfg.zipCode??>${siteCfg.zipCode}<#else>&nbsp;</#if>&nbsp;&nbsp;&nbsp;</span><br/><br/>\r\n<span class=\"address\"> 电子邮件：<a href=\"mailto:<#if siteCfg.email??>${siteCfg.email}<#else>&nbsp;</#if>\"><#if siteCfg.email??>${siteCfg.email}<#else>&nbsp;</#if></a>&nbsp;&nbsp;&nbsp;建议使用屏幕分辨率1024*768浏览</span></td>\r\n  </tr>\r\n</table>\r\n\r\n<map name=\"Map2\" id=\"Map2\">\r\n<area shape=\"rect\" coords=\"664,16,695,33\" href=\"#\" />\r\n</map>\r\n<map name=\"Map3\" id=\"Map3\">\r\n<area shape=\"rect\" coords=\"314,16,344,31\" href=\"#\" />\r\n</map>\r\n<map name=\"Map4\" id=\"Map4\"><area shape=\"rect\" coords=\"307,13,335,30\" href=\"#\" /></map></body>\r\n</html>','页面底部模板',NULL,'0',NULL,'foot','3',22),
 (34603008,'<div align=\"center\" style=\"margin:0 auto;width:280px;\">\r\n<script type=\"text/javascript\">\r\n  var focus_width=280;\r\n var focus_height=168;\r\n var text_height=18;\r\n var swf_height = focus_height+text_height;\r\n\r\nvar imag=new Array();\r\nvar link=new Array();\r\nvar text=new Array();\r\n<#list SpecialBean.getSpecialToIndex(5) as article>\r\n<#assign articleId=\"${article.id}\">	\r\n<#if article.front_pic??>						  \r\nimag[${article_index?number+1}]=\"${ctx}${article.front_pic}\";\r\nlink[${article_index?number+1}]=\"${ctx}/admin/special/frontView.do?model.id=\"+\"${article.id}\";\r\ntext[${article_index?number+1}]=\"${article.title}\";\r\n</#if>\r\n</#list>\r\nvar pics=\"\", links=\"\", texts=\"\";\r\nfor(var i=1; i<imag.length; i++){\r\n	pics=pics+(\"|\"+imag[i]);\r\n	links=links+(\"|\"+link[i]);\r\n	texts=texts+(\"|\"+text[i]);\r\n}\r\npics=pics.substring(1);\r\nlinks=links.substring(1);\r\ntexts=texts.substring(1);\r\n \r\n document.write(\'<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\" width=\"\'+ focus_width +\'\" height=\"\'+ swf_height +\'\">\');\r\n document.write(\'<param name=\"allowScriptAccess\" value=\"sameDomain\"><param name=\"movie\" value=\"${ctx}/ResRoot/index/flash/focus2.swf\"><param name=\"quality\" value=\"high\"><param name=\"bgcolor\" value=\"#F0F0F0\">\');\r\n document.write(\'<param name=\"menu\" value=\"false\"><param name=wmode value=\"opaque\">\');\r\n document.write(\'<param name=\"FlashVars\" value=\"pics=\'+pics+\'&links=\'+links+\'&texts=\'+texts+\'&borderwidth=\'+focus_width+\'&borderheight=\'+focus_height+\'&textheight=\'+text_height+\'\">\');\r\n document.write(\'</object>\');\r\n</script> </div>\r\n','首页地震专题图片显示',NULL,'0',NULL,'flash','3',24),
 (34603009,'<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding-left:8px;\">\r\n<tr><td height=\"4px\" colspan=\"2\">&nbsp;</td></tr>\r\n  <#list SpecialBean.getSpecialToIndex(1) as special> \r\n    <tr>\r\n      <td width=\"98%\" height=\"29\" align = \"left\" valign=\"top\">\r\n        <div style=\"line-height:24px\"><font color=\"#286e94\">\r\n           <#if 280 < special.desn?length>			\r\n              ${special.desn[0..279]}...[<a href=\"${ctx}/admin/special/frontView.do?model.id=${special.id}\" target=\"_blank\"><font color=\"red\">详情</font></a>]\r\n	  <#else>${special.desn}\r\n            </font>\r\n	  </#if>\r\n        </div>\r\n      </td><td width=\"2%\"></td>\r\n    </tr>    \r\n  </#list>\r\n</table>','地震专题列表',NULL,'0',NULL,'dynamicwork','3',29),
 (35094528,'<style type=\"text/css\">\r\n.username{\r\n  border:1px solid #D3D1D2;\r\n}\r\n\r\n</style>\r\n\r\n<script type=\"text/javascript\">\r\nfunction onLogin() {\r\n   var frm = document.getElementById(\'loginForm\');\r\n   if(frm) {\r\n       frm.submit();\r\n   }\r\n}\r\nString.prototype.trim = function()\r\n{\r\n    return this.replace(/(^[\\\\s]*)|([\\\\s]*$)/g, \"\");\r\n}\r\n\r\nfunction check(){\r\n   var loginId = document.getElementById(\'j_username\').value;\r\n var loginpwd= document.getElementById(\'j_password\').value;\r\nvar code = document.getElementById(\'j_captcha_response\').value;\r\nif(loginId.trim() == \'\' || loginId == null){\r\n   alert(\'请输入用户名!\');\r\n   return false;\r\n  }\r\nif(loginpwd.trim() == \'\' || loginpwd== null){\r\n   alert(\'请输入密码!\');\r\n   return false;\r\n  }\r\nif(code.trim() == \'\' || code== null){\r\n   alert(\'请输入验证码!\');\r\n   return false;\r\n  }\r\nonLogin();\r\n}\r\n</script>\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                     <tr>\r\n                         <td width=\"225\" align=\"center\" valign=\"middle\" bgcolor=\"FAFAFA\">\r\n                           <form id=\"loginForm\" action=\"${ctx}/j_security_check\" method=\"post\" >\r\n                              <table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"225\" height=\"125\">\r\n                               <tr>\r\n                                  <td align=\"right\" width=\"61\" height=\"35\">用户名：</td>\r\n                                 <td valign=\"middle\" align=\"left\" colspan=\"2\"><input name=\"j_username\" id=\"j_username\" type=\"text\" class=\"username\" style=\"width:124px\"/>							     </td>\r\n                               </tr>\r\n                               <tr>\r\n			       <td valign=\"middle\" align=\"right\" height=\"35\">密 &nbsp;码：</td>\r\n                                 <td valign=\"middle\" align=\"left\" colspan=\"2\">\r\n			       <input name=\"j_password\" id=\"j_password\" class=\"username\" type=\"password\" style=\"width:124px\"/>	 </td>\r\n                               </tr>\r\n                               <tr>\r\n			       <td valign=\"middle\" align=\"right\" height=\"35\">验证码：</td>\r\n			       <td width=\"44\" align=\"left\" valign=\"middle\">\r\n							     <input type=\"text\" class=\"username\" name=\"j_captcha_response\" id=\"j_captcha_response\" style=\"width:40px\" autocomplete=\"off\">        </td>\r\n                                 <td width=\"120\" align=\"left\"><span style=\"text-align:left;\">\r\n			                           <iframe id=\"captchaFrame\" name=\"cf\" src=\"${ctx}/captcha.jpg\" frameborder=\"0\" scrolling=\"no\" style=\"width:75px;height:26px;border:1px solid #AADFFF;\" marginheight=\"0\" marginwidth=\"0\"></iframe>\r\n	                                 <a href=\"#\" style=\"color:#425E8D;\" onclick=\"frames[\'cf\'].location=\'${ctx}/captcha.jpg\';\">[刷]</a>\r\n			                     </span>								 </td>\r\n                               </tr>\r\n                               <tr><td colspan=\"3\" height=\"10\"></td></tr>\r\n                               <tr>\r\n                                   <td colspan=\"3\"><table align=\"center\" width=\"75%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n								   <tr>\r\n								    <td align=\"center\"><a href=\"${ctx}/regist/registMemo.do\">用户注册</a></td>\r\n                                    <td align=\"center\">\r\n                                   <a href=\"${ctx}/restorePassword/edit.do\" target=\"_blank\">忘记密码？</a></td>\r\n                                    <td align=\"left\"> <img src=\"${ctx}/ResRoot/index/images/5.gif\" width=\"43\" height=\"20\" style=\"CURSOR:hand;\" onClick=\"return check();\"/> </td>\r\n								   </tr>\r\n								   </table></td>\r\n                               </tr>\r\n                            </table>\r\n						   </form>\r\n					   </td>\r\n                    </tr>\r\n</table>','登录模板',NULL,'0',NULL,'login','3',116),
 (35258368,'<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n        <tr>\r\n          <td width=\"234\" align=\"center\" valign=\"middle\" bgcolor=\"FAFAFA\"><iframe width=\"230\" height=\"180\" frameborder=\"0\" src=\"${ctx}/user/userInfo.do\"></iframe></td>\r\n        </tr>\r\n      </table>\r\n','登录成功，个人信息模板，替换原登录模板',NULL,'0',NULL,'loginsuccess','3',13),
 (35356672,'<#if req.getUserPrincipal()??>\r\n<@FreeMarker_Template:top_catalog_login/><#else><@FreeMarker_Template:top_catalog_unlogin/></#if>\r\n\r\n<div id=\"main_info\" style=\"margin:0 auto\" align=\"center\">\r\n\r\n<TABLE height=400 cellSpacing=0 cellPadding=0 width=1003 align=center \r\nborder=0><TBODY>\r\n<TR>\r\n<TD vAlign=top width=250 background=${ctx}/ResRoot/index/images/index1_01_02.gif \r\nheight=37>&nbsp;</TD>\r\n<TD rowSpan=2 valign=\"top\"><table width=\"743\" height=\"403\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td width=\"760\" height=\"46\" valign=\"middle\" background=\"${ctx}/ResRoot/index/images/0001shot.gif\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"45px\">&nbsp;</td><td style=\"padding-top:6px;\">\r\n您现在的位置：<a \r\nhref=\"${ctx}/index.shtml\">首页</a>&nbsp;-&gt;&nbsp;${catalog.name}</td></tr></table></td>\r\n</tr>\r\n<tr>\r\n<td height=\"345\" background=\"${ctx}/ResRoot/index/images/bgshot.gif\"><@FreeMarker_Template:artslist/>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"${ctx}/ResRoot/index/images/bottomshot.gif\" width=\"760\" height=\"24\">&nbsp;</td>\r\n</tr>\r\n</table></TD>\r\n</TR>\r\n<TR>\r\n<TD vAlign=top>\r\n<TABLE cellSpacing=0 cellPadding=0 width=248 border=0>\r\n<TBODY>\r\n<TR>\r\n<TD width=10 background=${ctx}/ResRoot/index/images/index1_03.gif \r\nheight=9>&nbsp;</TD>\r\n<TD vAlign=top background=${ctx}/ResRoot/index/images/index1_04.gif \r\nheight=360><TABLE style=\"PADDING-LEFT: 7px\" cellSpacing=0 \r\ncellPadding=0 width=210 align=center border=0>\r\n<TBODY>\r\n<TR>\r\n<TD height=12>&nbsp;</TD></TR>\r\n<#list CatalogBean.getSubCatsByName(\'法规标准\') as root> \r\n                     <tr>\r\n                       <td height=\"29\" align = \"left\" valign=\"middle\"><div><a href=\"${ctx}${root.rootPath}/index.shtml\">${root.name}</a></div></td>\r\n                     </tr>    \r\n                  </#list>\r\n<TR>\r\n<TD vAlign=center align=right \r\nheight=29>&nbsp;&nbsp;</TD></TR></TBODY></TABLE></TD>\r\n<TD width=10 background=${ctx}/ResRoot/index/images/index1_05.gif>&nbsp;</TD></TR>\r\n<TR>\r\n<TD background=${ctx}/ResRoot/index/images/index1_12.gif colSpan=3 \r\nheight=12>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>\r\n<TR>\r\n<TD colSpan=2 height=12>&nbsp;</TD></TR></TBODY></TABLE>\r\n</div>\r\n<@FreeMarker_Template:foot/>','法规标准模板。此模板用于带有二级栏目的页面',NULL,'0',NULL,'standard','0',66);
INSERT INTO `templates` (`ID`,`CONTENT`,`DESCN`,`IS_COMM`,`IS_DEF`,`IS_DEL`,`NAME`,`TYPE`,`VERSION`) VALUES 
 (35782656,'<#if req.getUserPrincipal()??>\r\n<@FreeMarker_Template:top_catalog_login/><#else><@FreeMarker_Template:top_catalog_unlogin/></#if>\r\n\r\n<div id=\"main_info\" style=\"margin:0 auto\" align=\"center\">\r\n\r\n<#if ArticleBean.getFistArticleByCatName(catalog.name)??>\r\n<#assign art = ArticleBean.getFistArticleByCatName(catalog.name)/>\r\n<#else>\r\n<#assign art = \"请添加相关文章!\"/>\r\n</#if>\r\n\r\n  <table width=\"800\" border=\"0\" align=\"center\" height=\"397\">\r\n         <#if  ArticleBean.getFistArticleByCatName(catalog.name)??>\r\n	   <tr>\r\n	       <td height=\"50px\" align=\"center\" valign=\"middle\">\r\n	          <font style=\"color:#0e3977; font-size:20px;\"><b>${art.title}</b></font>\r\n                </td>\r\n	   </tr>\r\n	   <tr>\r\n	       <td align=\"left\" valign=\"middle\" style=\"padding:5px 10px 5px 10px;\">\r\n	           <font size=\"4\" color=\"#4a4a4a\">${art.content}</font>\r\n	       </td>\r\n	   </tr>\r\n         <#else>\r\n            <tr>\r\n                <td height=\"300px\" align=\"center\" valign=\"top\" style=\"padding-top:15px;\">\r\n                    <font size=\"5\">${art}</font>\r\n                </td>\r\n            </tr>\r\n         </#if>\r\n      </table>\r\n</div>\r\n   <@FreeMarker_Template:foot/>','关于本站',NULL,'0',NULL,'about','0',43),
 (36569088,'<#if req.getUserPrincipal()??>\r\n<@FreeMarker_Template:top_catalog_login/><#else><@FreeMarker_Template:top_catalog_unlogin/></#if>\r\n\r\n<div id=\"main_info\" style=\"margin:0 auto\" align=\"center\">\r\n\r\n<table width=\"800\" align=\"center\" style=\"margin:0 auto\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td width=\"800\" height=\"46\" valign=\"middle\" \r\n\r\nbackground=\"${ctx}/ResRoot/index/images/0001.gif\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td \r\n\r\nwidth=\"45px\">&nbsp;</td><td style=\"padding-top:6px;\" align=\"left\">\r\n您现在的位置：<a \r\nhref=\"${ctx}/index.shtml\">首页</a>&nbsp;-&gt;&nbsp;${catalog.name}</td></tr></table></td>\r\n</tr>\r\n<tr>\r\n<td height=\"391\" background=\"${ctx}/ResRoot/index/images/bg.gif\">\r\n<table align=\"center\" style=\"margin:0 auto\" width=\"753\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  <tr>\r\n    <td style=\"padding:25px 5px 10px 20px;\" align=\"left\">\r\n      <img src=\"${ctx}/images/icons/go.gif\"><font style=\"font-size:15px\"><b>您的个人信息：</b></font>\r\n    </td>\r\n  </tr>\r\n  <tr>\r\n    <td>\r\n      <iframe width=\"100%\" height=\"540\" frameborder=\"0\" src=\'${ctx}/user/showSelf.do?selfEdit=1&model.id=${RequestParameters[\"uid\"]}\'></iframe>\r\n   </td>\r\n  </tr>\r\n</table>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"${ctx}/ResRoot/index/images/bottom.gif\" width=\"800\" height=\"24\"></td>\r\n</tr>\r\n</table>\r\n</div>\r\n<@FreeMarker_Template:foot/>\r\n','个人信息修改',NULL,'0',NULL,'useredit','0',14),
 (36831232,'<#if req.getUserPrincipal()??>\r\n<@FreeMarker_Template:top_catalog_login/><#else><@FreeMarker_Template:top_catalog_unlogin/></#if>\r\n\r\n<div id=\"main_info\" style=\"margin:0 auto\" align=\"center\">\r\n\r\n<TABLE height=400 cellSpacing=0 cellPadding=0 width=1003 align=center \r\nborder=0>\r\n<TR>\r\n<TD vAlign=middle width=250 background=${ctx}/ResRoot/index/images/index1_01_02.gif \r\nheight=37 align=\"left\">&nbsp;<br/>&nbsp;&nbsp;&nbsp;<font style=\"font-family:\'黑体\';font-size:16px;color:white;\"><strong>${catalog.name}</strong></font></TD>\r\n<TD rowSpan=2 valign=\"top\"><table width=\"743\" height=\"418\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td width=\"760\" height=\"46\" valign=\"middle\" background=\"${ctx}/ResRoot/index/images/0001shot.gif\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"45px\">&nbsp;</td><td style=\"padding-top:6px;\" align=\"left\">\r\n您现在的位置：<a \r\nhref=\"${ctx}/index.shtml\">首页</a>&nbsp;-&gt;&nbsp;<#if catalog.parentCatalog??>${catalog.parentCatalog.name}</#if>${catalog.name}</td></tr></table></td>\r\n</tr>\r\n<tr>\r\n<td height=\"340\" valign=\"top\" background=\"${ctx}/ResRoot/index/images/bgshot.gif\"><@FreeMarker_Template:artslist/>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"${ctx}/ResRoot/index/images/bottomshot.gif\" width=\"760\" height=\"24\">&nbsp;</td>\r\n</tr>\r\n</table></TD>\r\n</TR>\r\n<TR>\r\n<TD vAlign=top>\r\n<TABLE cellSpacing=0 cellPadding=0 width=248 border=0>\r\n\r\n<TR>\r\n<TD width=10 background=${ctx}/ResRoot/index/images/index1_03.gif \r\nheight=9>&nbsp;</TD>\r\n<TD vAlign=top background=${ctx}/ResRoot/index/images/index1_04.gif \r\nheight=360><TABLE style=\"PADDING-LEFT: 7px\" cellSpacing=0 \r\ncellPadding=0 width=210 align=center border=0>\r\n\r\n<TR>\r\n<TD height=12>&nbsp;</TD></TR>\r\n<#list CatalogBean.getSubCatsByName(\"${catalog.name}\") as root> \r\n                     <tr>\r\n                       <td height=\"29\" align = \"left\" valign=\"middle\"><div><#if root.type==\"1\"><a href=\"${ctx}${root.rootPath}/index.shtml\">${root.name}</a><#else><a href=\"${root.linkUrl}\" target=\"_blank\">${root.name}</a></#if></div></td>\r\n                     </tr>    \r\n                  </#list>\r\n<TR>\r\n<TD vAlign=center align=right \r\nheight=29>&nbsp;&nbsp;</TD></TR></TABLE></TD>\r\n<TD width=10 background=${ctx}/ResRoot/index/images/index1_05.gif>&nbsp;</TD></TR>\r\n<TR>\r\n<TD background=${ctx}/ResRoot/index/images/index1_12.gif colSpan=3 \r\nheight=12>&nbsp;</TD></TR></TABLE></TD></TR>\r\n<TR>\r\n<TD colSpan=2 height=12>&nbsp;</TD></TR></TABLE>\r\n</div>\r\n<@FreeMarker_Template:foot/>','通用模板，适用于所有带二级栏目的一级栏目(注意：此模板应用于带有分页数据的栏目，即文章列表栏目)',NULL,'0',NULL,'common','0',50),
 (38076416,'<#if req.getUserPrincipal()??>\r\n<@FreeMarker_Template:top_catalog_login/><#else><@FreeMarker_Template:top_catalog_unlogin/></#if>\r\n<div id=\"main_info\" style=\"margin:0 auto\" align=\"center\">\r\n\r\n<TABLE height=400 cellSpacing=0 cellPadding=0 width=1003 align=center \r\nborder=0><TBODY>\r\n<TR>\r\n<TD vAlign=middle width=250 background=${ctx}/ResRoot/index/images/index1_01_02.gif \r\nheight=37>&nbsp;<br/>&nbsp;&nbsp;&nbsp;<font style=\"font-family:\'黑体\';font-size:16px;color:white;\"><strong>${catalog.name}</strong></TD>\r\n<TD rowSpan=2 valign=\"top\"><table width=\"743\" height=\"403\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td width=\"760\" height=\"46\" valign=\"middle\" background=\"${ctx}/ResRoot/index/images/0001shot.gif\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"45px\">&nbsp;</td><td style=\"padding-top:6px;\">\r\n您现在的位置：<a \r\nhref=\"${ctx}/index.shtml\">首页</a>&nbsp;-&gt;&nbsp;${catalog.name}</td></tr></table></td>\r\n</tr>\r\n  <tr>\r\n    <td style=\"padding:25px 5px 10px 20px;\">\r\n      <img src=\"${ctx}/images/icons/go.gif\"><font style=\"font-size:15px\"><b>已有的元数据为：</b></font>\r\n    </td>\r\n  </tr>\r\n<tr>\r\n<td height=\"345\" background=\"${ctx}/ResRoot/index/images/bgshot.gif\"><iframe width=\"100%\" height=\"370\" frameborder=\"0\" name=\"mainframe\" src=\"${ctx}/datashare/metadata/index.do\"></iframe>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"${ctx}/ResRoot/index/images/bottomshot.gif\" width=\"760\" height=\"24\">&nbsp;</td>\r\n</tr>\r\n</table></TD>\r\n</TR>\r\n<TR>\r\n<TD vAlign=top>\r\n<TABLE cellSpacing=0 cellPadding=0 width=248 border=0>\r\n<TBODY>\r\n<TR>\r\n<TD width=10 background=${ctx}/ResRoot/index/images/index1_03.gif \r\nheight=9>&nbsp;</TD>\r\n<TD vAlign=top background=${ctx}/ResRoot/index/images/index1_04.gif \r\nheight=360><TABLE style=\"PADDING-LEFT: 7px\" cellSpacing=0 \r\ncellPadding=0 width=210 align=center border=0>\r\n<TBODY>\r\n<TR>\r\n<TD height=12>&nbsp;</TD></TR>\r\n<#list CatalogBean.getSubCatsByName(\"${catalog.name}\") as root> \r\n                     <tr>\r\n                       <td height=\"29\" align = \"left\" valign=\"middle\"><div><#if root.type==\"1\"><a href=\"${ctx}${root.rootPath}/index.shtml\">${root.name}</a><#else><a href=\"${ctx}/${root.linkUrl}\" target=\"mainframe\">${root.name}</a></#if></div></td>\r\n                     </tr>    \r\n                  </#list>\r\n<TR>\r\n<TD vAlign=center align=right \r\nheight=29>&nbsp;&nbsp;</TD></TR></TBODY></TABLE></TD>\r\n<TD width=10 background=${ctx}/ResRoot/index/images/index1_05.gif>&nbsp;</TD></TR>\r\n<TR>\r\n<TD background=${ctx}/ResRoot/index/images/index1_12.gif colSpan=3 \r\nheight=12>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>\r\n<TR>\r\n<TD colSpan=2 height=12>&nbsp;</TD></TR></TBODY></TABLE>\r\n</div>\r\n<@FreeMarker_Template:foot/>','带有二级栏目，且右侧为iframe的栏目模板，此模板为元数据模板',NULL,'0',NULL,'commonframe','0',19),
 (38076417,'<#if req.getUserPrincipal()??>\r\n<@FreeMarker_Template:top_catalog_login/><#else><@FreeMarker_Template:top_catalog_unlogin/></#if>\r\n<div id=\"main_info\" style=\"margin:0 auto\" align=\"center\">\r\n\r\n<TABLE height=400 cellSpacing=0 cellPadding=0 width=1003 align=center \r\nborder=0><TBODY>\r\n<TR>\r\n<TD vAlign=middle width=250 background=${ctx}/ResRoot/index/images/index1_01_02.gif \r\nheight=37 align=\"left\">&nbsp;<br/>&nbsp;&nbsp;&nbsp;<font style=\"font-family:\'黑体\';font-size:16px;color:white;\"><strong>${catalog.parentCatalog.name}</strong></TD>\r\n<TD rowSpan=2 valign=\"top\"><table width=\"743\" height=\"422\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td width=\"760\" height=\"46\" valign=\"middle\" background=\"${ctx}/ResRoot/index/images/0001shot.gif\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"45px\">&nbsp;</td><td style=\"padding-top:6px;\" align=\"left\">\r\n您现在的位置：<a \r\nhref=\"${ctx}/index.shtml\">首页</a>&nbsp;-&gt;&nbsp;<#if catalog.parentCatalog??><a href=\"${ctx}${catalog.parentCatalog.rootPath}/index.shtml\">${catalog.parentCatalog.name}</a>&nbsp;-&gt;&nbsp;</#if>${catalog.name}</td></tr></table></td>\r\n</tr>\r\n<tr>\r\n<td height=\"339\" background=\"${ctx}/ResRoot/index/images/bgshot.gif\"><@FreeMarker_Template:artslist/>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"${ctx}/ResRoot/index/images/bottomshot.gif\" width=\"760\" height=\"24\">&nbsp;</td>\r\n</tr>\r\n</table></TD>\r\n</TR>\r\n<TR>\r\n<TD vAlign=top>\r\n<TABLE cellSpacing=0 cellPadding=0 width=248 border=0>\r\n<TBODY>\r\n<TR>\r\n<TD width=10 background=${ctx}/ResRoot/index/images/index1_03.gif \r\nheight=9>&nbsp;</TD>\r\n<TD vAlign=top background=${ctx}/ResRoot/index/images/index1_04.gif \r\nheight=360><TABLE style=\"PADDING-LEFT: 7px\" cellSpacing=0 \r\ncellPadding=0 width=210 align=center border=0>\r\n<TBODY>\r\n<TR>\r\n<TD height=12>&nbsp;</TD></TR>\r\n<#list CatalogBean.getSubCatsByName(\"${catalog.parentCatalog.name}\") as root> \r\n                     <tr>\r\n                       <td height=\"29\" align = \"left\" valign=\"middle\"><div><#if root.type==\"1\"><a href=\"${ctx}${root.rootPath}/index.shtml\">${root.name}</a><#else><a href=\"${root.linkUrl}\" target=\"_blank\">${root.name}</a></#if></div></td>\r\n                     </tr>    \r\n                  </#list>\r\n<TR>\r\n<TD vAlign=center align=right \r\nheight=29>&nbsp;&nbsp;</TD></TR></TBODY></TABLE></TD>\r\n<TD width=10 background=${ctx}/ResRoot/index/images/index1_05.gif>&nbsp;</TD></TR>\r\n<TR>\r\n<TD background=${ctx}/ResRoot/index/images/index1_12.gif colSpan=3 \r\nheight=12>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>\r\n<TR>\r\n<TD colSpan=2 height=12>&nbsp;</TD></TR></TBODY></TABLE>\r\n</div>\r\n<@FreeMarker_Template:foot/>','二级栏目通用模板',NULL,'0',NULL,'secondarycommon','0',20),
 (38141952,'<#if req.getUserPrincipal()??>\r\n<@FreeMarker_Template:top_catalog_login/><#else><@FreeMarker_Template:top_catalog_unlogin/></#if>\r\n<div id=\"main_info\" style=\"margin:0 auto\" align=\"center\">\r\n\r\n<TABLE height=400 cellSpacing=0 cellPadding=0 width=1003 align=center \r\nborder=0><TBODY>\r\n<TR>\r\n<TD vAlign=middle width=250 background=${ctx}/ResRoot/index/images/index1_01_02.gif \r\nheight=37>&nbsp;<br/>&nbsp;&nbsp;&nbsp;<font style=\"font-family:\'黑体\';font-size:16px;color:white;\"><strong>${catalog.parentCatalog.name}</strong></TD>\r\n<TD rowSpan=2 valign=\"top\"><table width=\"743\" height=\"403\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td width=\"760\" height=\"46\" valign=\"middle\" background=\"${ctx}/ResRoot/index/images/0001shot.gif\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"45px\">&nbsp;</td><td style=\"padding-top:6px;\">\r\n您现在的位置：<a \r\nhref=\"${ctx}/index.shtml\">首页</a>&nbsp;-&gt;&nbsp;<#if catalog.parentCatalog??><a href=\"${ctx}${catalog.parentCatalog.rootPath}/index.shtml\">${catalog.parentCatalog.name}</a>&nbsp;-&gt;&nbsp;</#if>${catalog.name}</td></tr></table></td>\r\n</tr>\r\n  <tr>\r\n    <td style=\"padding:25px 5px 10px 20px;\">\r\n      <img src=\"${ctx}/images/icons/go.gif\"><font style=\"font-size:15px\"><b>已有的元数据为：</b></font>\r\n    </td>\r\n  </tr>\r\n<tr>\r\n<td height=\"345\" background=\"${ctx}/ResRoot/index/images/bgshot.gif\"><iframe width=\"100%\" height=\"370\" frameborder=\"0\" src=\"${ctx}/datashare/metadata/index.do\"></iframe>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"${ctx}/ResRoot/index/images/bottomshot.gif\" width=\"760\" height=\"24\">&nbsp;</td>\r\n</tr>\r\n</table></TD>\r\n</TR>\r\n<TR>\r\n<TD vAlign=top>\r\n<TABLE cellSpacing=0 cellPadding=0 width=248 border=0>\r\n<TBODY>\r\n<TR>\r\n<TD width=10 background=${ctx}/ResRoot/index/images/index1_03.gif \r\nheight=9>&nbsp;</TD>\r\n<TD vAlign=top background=${ctx}/ResRoot/index/images/index1_04.gif \r\nheight=360><TABLE style=\"PADDING-LEFT: 7px\" cellSpacing=0 \r\ncellPadding=0 width=210 align=center border=0>\r\n<TBODY>\r\n<TR>\r\n<TD height=12>&nbsp;</TD></TR>\r\n<#list CatalogBean.getSubCatsByName(\"${catalog.parentCatalog.name}\") as root> \r\n                     <tr>\r\n                       <td height=\"29\" align = \"left\" valign=\"middle\"><div><#if root.type==\"1\"><a href=\"${ctx}${root.rootPath}/index.shtml\">${root.name}</a><#else><a href=\"${ctx}/${root.linkUrl}\" target=\"mainframe\">${root.name}</a></#if></div></td>\r\n                     </tr>    \r\n                  </#list>\r\n<TR>\r\n<TD vAlign=center align=right \r\nheight=29>&nbsp;&nbsp;</TD></TR></TBODY></TABLE></TD>\r\n<TD width=10 background=${ctx}/ResRoot/index/images/index1_05.gif>&nbsp;</TD></TR>\r\n<TR>\r\n<TD background=${ctx}/ResRoot/index/images/index1_12.gif colSpan=3 \r\nheight=12>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>\r\n<TR>\r\n<TD colSpan=2 height=12>&nbsp;</TD></TR></TBODY></TABLE>\r\n</div>\r\n<@FreeMarker_Template:foot/>','右侧为iframe的二级模板，元数据使用',NULL,'0',NULL,'secondarycommonframe','0',11);
/*!40000 ALTER TABLE `templates` ENABLE KEYS */;


--
-- Definition of table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK143BF46A8E47D4C5` (`role_id`),
  KEY `FK143BF46A337298A5` (`user_id`),
  CONSTRAINT `FK143BF46A337298A5` FOREIGN KEY (`user_id`) REFERENCES `users` (`ID`),
  CONSTRAINT `FK143BF46A8E47D4C5` FOREIGN KEY (`role_id`) REFERENCES `roles` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role`
--

/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`,`role_id`) VALUES 
 (32768,1),
 (32768,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;


--
-- Definition of table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `h_tel` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `data_email` varchar(255) DEFAULT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `descn` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `folk` varchar(255) DEFAULT NULL,
  `industry` varchar(255) DEFAULT NULL,
  `is_sys` char(1) DEFAULT '0',
  `last_login_ip` varchar(255) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  `login_times` int(11) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `msn` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `post` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `regist_time` datetime DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `unit_kind` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK6A68E083B217BD7` (`dept_id`),
  CONSTRAINT `FK6A68E083B217BD7` FOREIGN KEY (`dept_id`) REFERENCES `depts` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`ID`,`h_tel`,`address`,`birthday`,`data_email`,`degree`,`descn`,`email`,`folk`,`industry`,`is_sys`,`last_login_ip`,`last_login_time`,`level`,`login_id`,`login_times`,`mobile`,`msn`,`name`,`password`,`photo`,`post`,`province`,`qq`,`regist_time`,`sex`,`status`,`unit`,`unit_kind`,`user_type`,`version`,`zip`,`dept_id`) VALUES 
 (32768,NULL,'sdfhsd',NULL,'dudu88656@sohu.com','3','系统管理员角色','dudu88656@sohu.com',NULL,'N','1','127.0.0.1','2011-02-25 09:02:57',NULL,'admin',93,'13933067201',NULL,'Administrator','1d0258c2440a8d19e716292b231e3190',NULL,'a','HE',NULL,NULL,'M','1','aaa','4','1',98,'000000',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
