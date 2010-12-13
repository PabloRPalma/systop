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

USE quake;

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
 (35880960,'系统配置管理','AUTH_SYSCONF','target_url','1',NULL,NULL),
 (35880961,'许可管理','AUTH_LICENSE','target_url','1',NULL,NULL),
 (35880962,'链接管理','AUTH_NLA','target_url','1',NULL,NULL),
 (35880963,'模板管理','AUTH_TEMPLATE','target_url','1',NULL,NULL),
 (35913728,'软件管理','AUTH_SOFTWARE','target_url','1',NULL,NULL),
 (35913729,'文章管理','AUTH_ARTICLE','target_url','1',NULL,NULL),
 (35913730,'栏目管理','AUTH_CATA','target_url','1',NULL,NULL);
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;


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
 (35684380,'前台修改个人密码','前台修改个人密码','/security/user/changePassword.do*','url_resource',3),
 (35684381,'编辑个人信息','编辑个人信息','/security/user/edit.do*','url_resource',4),
 (35684382,'前台个人信息保存','前台个人信息保存','/security/user/save.do*','url_resource',3),
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




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
