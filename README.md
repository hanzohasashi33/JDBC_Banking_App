# JDBC_Banking_App

Banking application using JDBC and mySQL

The application has 2 types of logins: one as a user and one as an
admin. The admin user can add a new customer to the bank, as well as
delete and see all users. The admin user is a static one as compared to
having a SQL table with all admins. The username and password of the
admin is hardcoded as ‘admin’ and ‘admin’ respectively.
The customers/ normal end users have many functionalities such as
creating a new account, updating customer details, deleting an existing
account, and viewing all accounts. Each customer has a 1:n relationship
with accounts, i.e, one customer can have many accounts, whereas each
account is linked with only one customer. Each account has a balance, a
pin and an account id linked to it. Also a customer can withdraw, deposit
and transfer funds from one of his accounts provided he grants the correct
PIN. Whenever a fund transaction takes place, it’s logged in the transaction
table and the user can also see what all transactions took place from the
account.
