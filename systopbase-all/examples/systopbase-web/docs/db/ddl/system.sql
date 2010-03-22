drop table Hibernate_unique_key cascade;


/*==============================================================*/
/* Table: hibernate_unique_key                                       */
/*==============================================================*/
create table hibernate_unique_key (
next_hi              int
);

insert into hibernate_unique_key values(1024);

commit;

drop table if exists T_CATEGORY;

drop table if exists T_CONFIGURATION;

drop table if exists T_PROPERTY;

drop table if exists T_VARIABLE;

/*==============================================================*/
/* Table: T_CATEGORY                                            */
/*==============================================================*/
create table if not exists T_CATEGORY
(
   OID                            int                            not null AUTO_INCREMENT,
   NAME                           varchar(255),
   CONFIGURATION_OID              int,
   primary key (OID)
);

/*==============================================================*/
/* Table: T_CONFIGURATION                                       */
/*==============================================================*/
create table if not exists T_CONFIGURATION
(
   OID                            int                            not null AUTO_INCREMENT,
   NAME                           varchar(255)                   not null,
   primary key (OID),
   unique (NAME)
);

/*==============================================================*/
/* Table: T_PROPERTY                                            */
/*==============================================================*/
create table if not exists T_PROPERTY
(
   OID                            int                            not null AUTO_INCREMENT,
   NAME                           varchar(255),
   VALUE                          varchar(255),
   CATEGORY_OID                   int                            not null,
   primary key (OID)
);

/*==============================================================*/
/* Table: T_VARIABLE                                            */
/*==============================================================*/
create table if not exists T_VARIABLE
(
   OID                            int                            not null AUTO_INCREMENT,
   NAME                           varchar(255),
   VALUE                          varchar(255),
   CONFIGURATION_OID              int,
   primary key (OID)
);
