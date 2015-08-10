create table USER_DATA (
	ID int not null auto_increment,
	NAME varchar(255) not null,
	SURNAME varchar(255) not null,
	primary key (ID)
) ENGINE=MyISAM;

create table ACCOUNT_DATA (
	ID int not null auto_increment,
	VERSION int not null,
	BALANCE int not null,
	primary key (ID)
) ENGINE=MyISAM;

create table ACCOUNT(
	ID int not null auto_increment,
	USER_DATA int not null,
	ACCOUNT_DATA int not null,
	primary key (ID),
	foreign key (USER_DATA) references USER_DATA(ID),
	foreign key (ACCOUNT_DATA) references ACCOUNT_DATA(ID)
) ENGINE=MyISAM;

create table TRANSACTION (
	ID int not null auto_increment,
	ACCOUNT int not null,
	DATE date not null,
	AMOUNT int not null,
	primary key (ID)
) ENGINE=MyISAM;

