DROP TABLE if exists UsersData;
DROP TABLE if exists Users;

create table Users(
username varchar(30) not NULL PRIMARY key,
`password` varchar(30) not null,
email varchar(30) not NULL
);

create table UsersData(
username varchar(30) NOT NULL,
`First name` varchar(20) NOT NULL,
`Last name` varchar(20) NOT NULL,
City varchar(20),
Address varchar(30),
PESEL varchar(30),
foreign KEY(username) REFERENCES Users(username)
);

insert into Users values
("user1","q[q]sqsmq0smlZX","abc@sth.com"),("user2","zapkzpakzq1A../","jojo@kok"),("user3",",za;x;xmaxmslxM;oihhJGJ1","poplp@qui"),("user4","31okspqswKHh8VV./","ihkkl@mnqk"),
("user5","QSQSQKSHKJXnbvnavxa7871..","ppp@zzz");
insert INTO UsersData values
("user1","Max","Apol","Krakow","ul.1, 100","78022181674"),("user2","Alex","Bos","Zakopane","ul.10, 120","48022235482"),("user3","Morty","Bonon","Warszawa","ul. 7, 190","51062019893"),
("user4","Sam","Camel","Bydgoszcz","ul. 12, 11","57030885255"),("user5","Lila","Stich","Gdansk","ul. 20, 99","52082916496");

select * from Users;
select * from UsersData;



