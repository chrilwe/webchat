create table tb_user_account(
	account_no varchar(20) not null,
	user_id int not null,
	status int not null,
	money int not null,
	create_time datetime not null,
	pay_password varchar(80) not null,
	error_password_times_limit int not null,
	first_input_password_time datetime,
	primary key(user_id)
)engine=innodb;

create table tb_transaction_details(
	id int auto_increment not null,
	account_no varchar(20) not null,
	status int not null,
	transaction_startTime datetime not null,
	transaction_endTime datetime not null,
	transaction_type int not null,
	cost_to_account_no varchar(20),
	receive_from_account_no varchar(20),
	transaction_desc varchar(150),
	transaction_money int not null,
	primary key(id)
)engine=innodb;