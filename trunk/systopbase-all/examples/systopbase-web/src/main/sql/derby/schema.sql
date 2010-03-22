drop table ROLES_AUTHORITIES;
drop table USERS_ROLES;
drop table USERS;
drop table ROLES;
drop table AUTHORITIES;


create table USERS (
ID integer primary key GENERATED ALWAYS AS IDENTITY,
LOGIN_NAME varchar(20) not null unique,
PASSWORD varchar(20),
NAME varchar(20),
EMAIL varchar(30)
);

create table ROLES (
ID integer primary key GENERATED ALWAYS AS IDENTITY,
NAME varchar(20) not null unique
);

create table USERS_ROLES (
USER_ID integer not null,
ROLE_ID integer not null,
FOREIGN KEY (ROLE_ID) references ROLES(ID),
FOREIGN KEY (USER_ID) references USERS(ID)
);

CREATE TABLE AUTHORITIES (
ID integer primary key GENERATED ALWAYS AS IDENTITY,
NAME varchar(20) not null,
DISPLAY_NAME varchar(20) not null
);

create table ROLES_AUTHORITIES (
ROLE_ID integer not null,
AUTHORITY_ID integer not null,
FOREIGN KEY (ROLE_ID) references ROLES(ID),
FOREIGN KEY (AUTHORITY_ID) references AUTHORITIES(ID)
);