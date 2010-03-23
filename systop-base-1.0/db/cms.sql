drop table Hibernate_unique_key cascade;

drop table Contents cascade;

drop table Attachments cascade;

drop table user_role cascade;

drop table role_permis cascade;

drop table permis_resc cascade;

drop table Users cascade;

drop table Roles cascade;

drop table Resources cascade;

drop table Permissions cascade;

drop table Catalogs cascade;

drop table depts cascade;

drop table acl_object_identity cascade;

drop table acl_permission cascade;

drop table Maps cascade;

drop table Entries cascade;
--==============================================================


--==============================================================
--Table hibernate_unique_key
--==============================================================
create table hibernate_unique_key (
next_hi              int
);

insert into hibernate_unique_key values(1024);

--==============================================================
-- Table: Permissions
--==============================================================
create table Permissions (
id                   int                  not null,
name                 varchar(255),
descn                varchar(255),
operation            varchar(255),
status               varchar(2)            default '1',
version              int                   default 1,
primary key (id)
);

--==============================================================
-- Table: Resources
--==============================================================
create table Resources (
id                   int                  not null,
name                 varchar(255),
descn                varchar(255),
res_type             varchar(25),
res_string           varchar(255),
version              int                   default 1,
primary key (id)
);

--==============================================================
-- Table: Roles
--==============================================================
create table Roles (
id                   int                  not null,
name                 varchar(255),
descn                varchar(255),
version              int                   default 1,
primary key (id)
);

--==============================================================
-- Table: depts
--==============================================================
create table depts (
id                   INT                  not null,
parent_id            INT,
name                 VARCHAR(255),
descn                VARCHAR(255),
primary key (id),
foreign key (parent_id)
      references depts (id)
);


--==============================================================
-- Table: Users
--==============================================================
create table Users (
id                   int                  not null,
dept_id              INT,
login_id             varchar(255),
password             varchar(255),
name                 varchar(255),
region               varchar(255),
status               varchar(2)            default '1',
email                varchar(255),
descn                varchar(255),
version              int                   default 1,
primary key (id),
foreign key (dept_id)
      references depts (id)
);

--==============================================================
-- Table: permis_resc
--==============================================================
create table permis_resc (
permission_id        int                  not null,
resc_id              int                  not null,
primary key (permission_id, resc_id),
foreign key (permission_id)
      references Permissions (id),
foreign key (resc_id)
      references Resources (id)
);

--==============================================================
-- Table: role_permis
--==============================================================
create table role_permis (
role_id              int                  not null,
permission_id        int                  not null,
primary key (role_id, permission_id),
foreign key (role_id)
      references Roles (id),
foreign key (permission_id)
      references Permissions (id)
);

--==============================================================
-- Table: user_role
--==============================================================
create table user_role (
user_id              int                  not null,
role_id              int                  not null,
primary key (user_id, role_id),
foreign key (user_id)
      references Users (id),
foreign key (role_id)
      references Roles (id)
);


--==============================================================
-- Table: Catalogs
--类别是指应用程序中常用的各种参考值，例如，学历(小学、大学、博士)；
--目录类别(频道、栏目...)等等这些数据，统一放在这个表里面：
--==============================================================
create table Catalogs (
catalog              VARCHAR(255)         not null,
reference_value      VARCHAR(10)          not null,
int_value            INT,
str_value            VARCHAR(255),
float_value          FLOAT,
double_value         DOUBLE PRECISION,
date_value           DATE,
cat_descn            VARCHAR(255),
ref_descn            VARCHAR(255),
is_sys_variable      CHAR(1)              not null,
version              INT,
primary key (catalog, reference_value)
);

--==============================================================
-- Table: Contents
--==============================================================
create table Contents (
id                   INT                  not null,
type                 VARCHAR(2),
title                VARCHAR(255),
descn                VARCHAR(255),
subtitle             VARCHAR(255),
serial_no            INT,
create_time          DATETIME,
expire_date          DATETIME,
summary              VARCHAR(1000),
body                 VARCHAR,
update_time          DATETIME,
audited              VARCHAR(1),
visible              VARCHAR(1),
available            VARCHAR(1),
is_draft             VARCHAR(1),
shortcut_id          INT,
author               int,
updater              int,
auditor              int,
parent_id            INT,
version              INT,
primary key (id),
foreign key (shortcut_id)
      references Contents (id),
foreign key (author)
      references Users (id),
foreign key (updater)
      references Users (id),
foreign key (auditor)
      references Users (id),
foreign key (parent_id)
      references Contents (id)
);


--==============================================================
-- Table: Attachments
--==============================================================
create table Attachments (
id                   INT                  not null,
content_id           INT,
path                 VARCHAR(255),
is_del               VARCHAR(1),
version              INT,
primary key (id),
foreign key (content_id)
      references Contents (id)
);

--==============================================================
-- Table: acl_object_identity
--==============================================================
create table acl_object_identity (
id                   INT            not null identity,
object_identity      VARCHAR(255),
parent_object        int,
acl_class            VARCHAR(255),
primary key (id),
foreign key (parent_object)
      references acl_object_identity (id)
);

--==============================================================
-- Table: acl_permission
--==============================================================
create table acl_permission (
id                   INT          not null identity,
acl_object_identity  INT,
recipient            VARCHAR(255),
mask                 INT,
primary key (id),
foreign key (acl_object_identity)
      references acl_object_identity (id)
);

--==============================================================
-- Table: Maps、Entries 
-- Catalog表的替换，将Catalog拆分成两个表Maps和Entries 
--==============================================================
create table Maps (
map_id               INTEGER              not null,
map_title            VARCHAR(255),
map_sign             VARCHAR(255),
map_descn            VARCHAR(255),
version              int,
primary key (map_id)
);

create table Entries (
entry_id             integer              not null,
map_id               INTEGER,
ref_value            VARCHAR(10),
view_text            VARCHAR(255),
entry_descn          VARCHAR(255),
version              int,
primary key (entry_id),
foreign key (map_id)
      references Maps (map_id)
);

--==============================================================

--==============================================================
-- init Catalogs Table
--==============================================================
insert into Catalogs (catalog,reference_value,str_value,is_sys_variable,version) values('catalog_value_type','0','字符型','1','1');
insert into Catalogs (catalog,reference_value,str_value,is_sys_variable,version) values('catalog_value_type','1','整数型','1','1');
insert into Catalogs (catalog,reference_value,str_value,is_sys_variable,version) values('catalog_value_type','2','单精度','1','1');
insert into Catalogs (catalog,reference_value,str_value,is_sys_variable,version) values('catalog_value_type','3','双精度','1','1');
insert into Catalogs (catalog,reference_value,str_value,is_sys_variable,version) values('catalog_value_type','4','日期','1','1');

insert into Catalogs (catalog,reference_value,str_value,is_sys_variable,version) values('catalog_type','1','系统类别','1','1');
insert into Catalogs (catalog,reference_value,str_value,is_sys_variable,version) values('catalog_type','0','普通类别','1','1');

