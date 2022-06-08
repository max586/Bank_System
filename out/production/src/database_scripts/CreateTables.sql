drop table if exists Transactions;
drop table if exists Cards;
drop table if exists Credits;
drop table if exists OrdinaryAccounts;
drop table if exists SavingsAccounts;
DROP TABLE if exists UsersData;
DROP TABLE if exists Users;

create table Users(
username varchar(30) not NULL PRIMARY key,
`password` varchar(30) not null,
email varchar(30)
);

create table UsersData(
username varchar(30),
`First name` varchar(20),
`Last name` varchar(20),
`Sex` varchar(1),
City varchar(20),
Address varchar(30),
PESEL varchar(30),
foreign key(username) references Users(username) on update cascade on delete cascade
);

create table OrdinaryAccounts(
username varchar(30),
nr varchar(30) not null primary key,
balance float check(balance>=0),
foreign key(username) references Users(username) on update cascade on delete cascade
);

create table SavingsAccounts(
username varchar(30),
nr varchar(30) not null primary key,
balance float check(balance>=0),
rate float check(rate between 0 and 100),
foreign key(username) references Users(username) on update cascade on delete cascade
);

create table Credits(
id int not null auto_increment primary key,
username varchar(30) not null,
amount float check(amount>=0 and amount<=50000),
`start date` date,
`duration` int,#in months
rate float check(rate between 0 and 100),
foreign key(username) references Users(username) on update cascade on delete cascade
);

create table Cards(
username varchar(30),
`nr` varchar(16) not null primary key,
pin varchar(4),
foreign key(username) references Users(username) on update cascade on delete cascade
);

create table Transactions(
`account nr from` varchar(16) not null,
`account nr to` varchar(16) not null,
amount float check(amount>0),
`date` date,
`sender info` varchar(100),
`transaction info` varchar(100),
foreign key(`account nr from`) references OrdinaryAccounts(nr),
foreign key(`account nr to`) references OrdinaryAccounts(nr)
);






