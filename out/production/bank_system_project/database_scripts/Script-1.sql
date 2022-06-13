#delete from Credits where username="test_user";
#update OrdinaryAccounts set `Account number` = 'PL12122666666666666666666666' where username='user2';
#select `Operation Date`,`Transfer Type` ,`Account nr to` ,`Phone nr to` ,`Transfer Amount` ,`Transfer Currency` ,`Total Transfer Cost` ,`Transfer Title` ,`Start Date` ,`End Date` ,`Transfer Cycle` ,`Transfer Cycle Units` ,`First name` ,`Last name` ,Town ,Postcode ,Street ,`Street number` from HistoryOrdinary;
delete from Credits;
select * from Users;
select * from UsersData;
select * from OrdinaryAccounts;
select * from SavingsAccounts;
select * from Credits;
select * from Cards;
select * from HistoryOrdinary;
select * from HistorySavings;
select h.`Start Date`,h.`Transfer Cycle` ,h.`Transfer Cycle Units` ,h.`Account nr from` ,h.`Account nr to` ,h.`Transfer Amount`  from HistoryOrdinary h where `Transfer Type` = "standing order" and  (SELECT CURDATE())  <`End Date`;

#select h.* from HistoryOrdinary h join OrdinaryAccounts o on h.`Account nr from` = o.`Account nucmber`where h.`Operation Date` >= '2022-06-05'and o.username='test_user';