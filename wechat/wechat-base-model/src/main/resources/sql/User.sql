#tb_user表
create table tb_user(
	id int not null auto_increment,
	account_no varchar(11) not null,
	password varchar(70) not null,
	nick_name varchar(10) not null,
	status int(1) not null,
	grade int not null,
	create_time datetime not null,
	update_time datetime not null,
	email varchar(50) not null,
	phone_no varchar(15) not null,
	max_friends int not null,
	primary key(id)
)engine=innodb;

alter table tb_user add index index_account_no(account_no);
alter table tb_user add index index_nick_name(nick_name);
alter table tb_user add index index_email(email);
alter table tb_user add index index_phone(phone_no);

#tb_role表
create table tb_role(
	id int not null auto_increment,
	name varchar(15) not null,
	role_code varchar(15) not null,
	create_time datetime not null,
	update_time datetime not null,
	status int(1) not null,
	primary key(id)
)engine=innodb;

alter table tb_role add index index_role_name(name);


#tb_role_user表
create table tb_role_user(
	id int not null auto_increment,
	role_id int not null,
	user_id int not null,
	primary key(id)
)engine=innodb;


#tb_permission表
create table tb_permission(
	id int not null auto_increment,
	name varchar(15) not null,
	grade int not null,
	pid int not null,
	create_time datetime not null,
	status int(1) not null,
	is_leaf int(1) not null,
	primary key(id)
)engine=innodb;

#tb_role_permission
create table tb_role_permission(
	id int not null auto_increment,
	role_id int not null,
	permission_id int not null,
	primary key(id)
)engine=innodb;

#tb_friendgroup
create table tb_friendgroup(
	id int not null auto_increment,
	group_name varchar(15) not null,
	create_time datetime not null,
	update_time datetime not null,
	user_id int not null,
	primary key(id)
);

#tb_friends
create table tb_friends(
	id int not null auto_increment,
	group_id int not null,
	friend_id int not null,
	primary key(id)
);