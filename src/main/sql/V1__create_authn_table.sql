create table T_AUTHN (
	user_name varchar(50) not null,
	password varchar(50) not null
);

alter table T_AUTHN add constraint authn_pk primary key(user_name);
 