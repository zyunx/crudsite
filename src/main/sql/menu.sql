create table menu_items (
	item_name varchar(50),
	item_text varchar(40) not null,
	item_url varchar(40),
	item_order int default 0,
	item_parent varchar(50));
	
alter table menu_items add constraint menu_item_pk primary key (item_name);
alter table menu_items add constraint menu_item_parent_fk foreign key (item_parent) references menu_items (item_name);

-- 根菜单
insert into menu_items (item_name, item_text, item_order) values ('root', '所有菜单', 0);