
insert into Users values
("test_user","password","maks.ovsienko2@gmail.com","1234"),("user1","q[q]sqsmq0smlZX","abc@sth.com","3567"),("user2","zapkzpakzq1A../","jojo@kok","9999"),("user3",",za;x;xmaxmslxM;oihhJGJ1","poplp@qui","8281"),("user4","31okspqswKHh8VV./","ihkkl@mnqk","0000"),
("user5","QSQSQKSHKJXnbvnavxa7871..","ppp@zzz","1111");
insert INTO UsersData values
("test_user","fn","ln","M","27983719","somewhere","30-178","ul abc",125,"6381763182"),("user1","Max","Apol","M","3627318","Krakow","21-212","ul.ajk",90,"78022184"),("user2","Alex","Bos","M","9019201","Zakopane","10-010","ul.apoa",120,"480222354"),
("user3","Morty","Bonon","M","1209102","Warszawa","129-02","ul. pop",88,"51062893"),("user4","Sam","Camel","M","81323213","Bydgoszcz","89-908","ul. nmn", 11,"570308852"),("user5","Lila","Stich","F","13131321","Gdansk","21-210","ul. lasas", 99,"520829164");

insert into OrdinaryAccounts values("test_user","PL80113720220610261845683381",100000),("user1","PL86113720220610812999702392",999),("user2","PL12122666666666666666666666",100000),("user3","PL77113720220610210804864298",2000),("user4","PL80113720220610240271948198",10000),("user5","PL67113720220610370294021356",9090);
insert into SavingsAccounts values("test_user","PL80113720220610338657127571",10000,4),("user1","PL12122666666666666666666666",5000,4),("user2","PL58113720220610243802004109",300,4),("user3","PL88113720220610009598965480",3000,4),("user4","PL91113720220610781987477062",9000,4),("user5","PL83113720220610811316782759",11000,4);

INSERT INTO Cards values("test_user","1298109280119","1234"),("user1","1919919191","1234");

INSERT INTO Credits values("user1",9000,900,"2022-06-08",1);

INSERT INTO HistoryOrdinary(`Operation Date`,`Transfer Type`,`Account nr from`,`Account nr to`,`Phone nr to`,`Transfer Amount`,`Transfer Currency`,`Total Transfer Cost`,`Transfer Title`,`Start Date`,`End Date`,`Transfer Cycle`,`Transfer Cycle Units`,`First name`,`Last name`,`Town`,`Postcode`,`Street`,`Street number`) VALUES("2022-06-08","country transfer","PL80113720220610261845683381","PL9987","192219219",1000,"PLN",1000,"abc",null,null,null,null,"fn","ln","Gui","30-056","Ahj","12/2"),
("2022-06-07","country transfer","PL86113720220610812999702392","PL80113720220610261845683381","111999233",999,"PLN",1004,"hello",null,null,null,null,"fnm","lnm","Gpo","30-096","Aoj","23/2"),
("2022-06-01","transfer abroad","PL80113720220610261845683381","PL12122666666666666666666666","556277111",100,"EUR",1000,"osipq",null,null,null,null,"pol","lop","Mon","30-126","Aplk","122"),
("2022-06-05","own transfer","PL80113720220610261845683381","PL80113720220610338657127571","100888199",120,"PLN",120,"aoiosas",null,null,null,null,"fnio","lpl","Gui","32-890","Mkoi","100"),
("2022-06-02","standing order","PL12122666666666666666666666","PL80113720220610261845683381","101010191",10000,"PLN",10000,"edjeded","2022-04-02","2022-08-02",2,"month","vbop","bibop","Mnlop","10-056","Aplo","1092/1");

