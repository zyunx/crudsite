create table users (
	user_name varchar(20),
	password varchar(50)
);
alter table users add constraint users_pk primary key (user_name);

create table groups (
	group_name varchar(20)
);
alter table groups add constraint groups_pk primary key (group_name);

create table permissions (
	permission_name varchar(20)
);
alter table permissions add constraint permissions_pk primary key (permission_name);

create table user_groups (
	user_name varchar(20),
	group_name varchar(20)
);
alter table user_groups add constraint user_groups_pk primary key (user_name, group_name);

create table user_permissions (
	user_name varchar(20),
	permission_name varchar(20)
);
alter table user_permissions add constraint user_permissions_pk primary key (user_name, permission_name);

create table group_permissions (
	group_name varchar(20),
	permission_name varchar(20)
);
alter table group_permissions add constraint group_permissions_pk primary key (group_name, permission_name);