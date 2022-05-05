select u1.username, u1.password, u1.email, u2.`First name`, u2.`Last name`, u2.City, u2.Address, u2.PESEL  from Users u1 join UsersData u2 on u1.username=u2.username;
