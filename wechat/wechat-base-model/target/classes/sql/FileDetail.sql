create table tb_file_detail(
	id int not null auto_increment,
	original_file_name varchar(50) not null,
	file_type varchar(10) not null,
	file_size int,
	create_time datetime not null,
	update_time datetime,
	user_id int not null,
	is_floder int not null,
	order_no int,
	parent_id int not null,
	grade int not null,
	file_address varchar(100),
	primary key(id)
)engine=innodb;