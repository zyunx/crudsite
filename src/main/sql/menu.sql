create table menu_item (
	item_name varchar(50),
	item_text varchar(40) not null,
	item_url varchar(40),
	item_parent varchar(50));
	
alter table menu_item add constraint menu_item_pk primary key (item_name);
alter table menu_item add constraint item_parent_fk foreign key (item_parent) references menu_item(item_name);
