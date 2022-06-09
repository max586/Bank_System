drop table if exists HistorySavings;
drop table if exists HistoryOrdinary;
drop table if exists Cards;
drop table if exists Credits;
drop table if exists OrdinaryAccounts;
drop table if exists SavingsAccounts;
DROP TABLE if exists UsersData;
DROP TABLE if exists Users;

create table Users(
username varchar(30) not NULL PRIMARY key,
`password` varchar(30) not null,
email varchar(30),
appCode varchar(4)
);

create table UsersData(
username varchar(30),
`First name` varchar(20),
`Last name` varchar(20),
sex varchar(7),
`Phone number` varchar(9),
Town varchar(30),
Postcode varchar(6),
Street varchar(30),
`Street number` varchar(10),
pesel varchar(11),
foreign key(username) references Users(username) on update cascade on delete cascade
);

create table OrdinaryAccounts(
username varchar(30),
`Account number` varchar(28) not null primary key,
Balance double check(Balance>=0),
foreign key(username) references Users(username) on update cascade on delete cascade
);

create table SavingsAccounts(
username varchar(30),
`Account number` varchar(28) not null primary key,
Balance double check(Balance>=0),
Rate float check(Rate between 0 and 100),
foreign key(username) references Users(username) on update cascade on delete cascade
);

create table Credits(
username varchar(30) not null,
amount float check(amount>=0),
`amount payed` float check(`amount payed`>=0),
`start date` date,
`duration` int,#in years
foreign key(username) references Users(username) on update cascade on delete cascade
);

create table Cards(
username varchar(30),
`nr` varchar(16) not null primary key,
pin varchar(4),
foreign key(username) references Users(username) on update cascade on delete cascade
);

create table HistoryOrdinary(
`Operation Date` varchar(30) not null,
`Transfer Type` varchar(50) not null,
`Account nr from` varchar(28) not null,
`Account nr to` varchar(28),
`Phone nr to` varchar(9),
`Transfer Amount` double check(`Transfer Amount`>0),
`Transfer Currency` varchar(3) not null,
`Total Transfer Cost` double check (`Total Transfer Cost`>0),
`Transfer Title` varchar(1000) not null,
`Start Date` varchar(30),
`End Date` varchar(30),
`Transfer Cycle` int,
`Transfer Cycle Units` varchar(10),
`Sender first name` varchar(20),
`Sender last name` varchar(20),
`Sender Town` varchar(30),
`Sender Postcode` varchar(6),
`Sender Street` varchar(30),
`Sender Street number` varchar(10),
foreign key(`Account nr from`) references OrdinaryAccounts(`Account number`)
);

create table HistorySavings(
`Operation Date` varchar(30) not null,
`Transfer Type` varchar(50) not null,
`Account nr from` varchar(28) not null,
`Account nr to` varchar(28),
`Phone nr to` varchar(9),
`Transfer Amount` double check(`Transfer Amount`>0),
`Transfer Currency` varchar(3) not null,
`Total Transfer Cost` double check (`Total Transfer Cost`>0),
`Transfer Title` varchar(1000) not null,
`Start Date` varchar(30),
`End Date` varchar(30),
`Transfer Cycle` int,
`Transfer Cycle Units` varchar(10),
`Sender first name` varchar(20),
`Sender last name` varchar(20),
`Sender Town` varchar(30),
`Sender Postcode` varchar(6),
`Sender Street` varchar(30),
`Sender Street number` varchar(10),
foreign key(`Account nr from`) references SavingsAccounts(`Account number`)
);
