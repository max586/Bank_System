
insert into Users values
("test_user","password","maks.ovsienko2@gmail.com"),("user1","q[q]sqsmq0smlZX","abc@sth.com"),("user2","zapkzpakzq1A../","jojo@kok"),("user3",",za;x;xmaxmslxM;oihhJGJ1","poplp@qui"),("user4","31okspqswKHh8VV./","ihkkl@mnqk"),
("user5","QSQSQKSHKJXnbvnavxa7871..","ppp@zzz");
insert INTO UsersData values
("test_user","fn","ln","M","somewhere","ul x, y","666"),("user1","Max","Apol","M","Krakow","ul.1, 100","78022181674"),("user2","Alex","Bos","M","Zakopane","ul.10, 120","48022235482"),("user3","Morty","Bonon","M","Warszawa","ul. 7, 190","51062019893"),
("user4","Sam","Camel","M","Bydgoszcz","ul. 12, 11","57030885255"),("user5","Lila","Stich","F","Gdansk","ul. 20, 99","52082916496");

insert into OrdinaryAccounts values("test_user","PL666",1000),("user1","PL777",900);
insert into SavingsAccounts values("test_user","PL999",10000,4);

insert into Cards values("test_user","123456","1234");

#select * from Users u1 join UsersData u2 on u1.username = u2.username join OrdinaryAccountNumbers o on u1.username = o.username join SavingsAccountNumbers s on u1.username = s.username;