DROP TABLE if exists UsersData;
drop table if exists UsersAccounts;
DROP TABLE if exists Users;

create table Users(
username varchar(30) not NULL PRIMARY key,
`password` varchar(30) not null,
email varchar(30)
);

create table UsersData(
username varchar(30) NOT NUll,
`First name` varchar(20),
`Last name` varchar(20),
`Sex` varchar(1),
City varchar(20),
Address varchar(30),
PESEL varchar(30),
foreign KEY(username) REFERENCES Users(username)
);

create table UsersAccounts(
username varchar(30) not null,
`Ordinary account number` varchar(30),
`Savings account number` varchar(30),
foreign key(username) references Users(username)
);

/*create table UsersCards(
username varchar(30) not null,
);*/




