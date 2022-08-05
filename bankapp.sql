drop database bank;
create database bank;
use bank;

create table customer(
    id integer,
    name varchar(30),
    email varchar(30),
    phone char(10),
    passwrd varchar(30),
    constraint pk_Customer PRIMARY KEY (id)
);

create table account(
    id integer,
    pin char(4),
    balance double(15,4),
    customer_id_fk integer,
    constraint pk_Account PRIMARY KEY (id)
);

create table transaction(
    id integer,
    account_num_fk integer,
    type varchar(30),
    amount double(15,4),
    txntimestamp DateTime,
    constraint pk_Transaction PRIMARY KEY (id)
);




alter table account
    add constraint fk_bank_acc FOREIGN KEY (customer_id_fk)
REFERENCES customer(id) ON DELETE CASCADE;;

alter table transaction
    add constraint fk_transc_bank_acc FOREIGN KEY (account_num_fk)
REFERENCES account(id) ON DELETE CASCADE;;





insert into customer values(1,"prasanna","prasanna@gmail.com","9888888888","1");
insert into customer values(2,"siva","siva@gmail.com","9777777777","2");
insert into customer values(3,"suresh","suresh@gmail.com","9666666666","3");

insert into account values(1,1234,10000,1);
insert into account values(4,1234,40000,1);
insert into account values(5,1234,50000,1);
insert into account values(2,2345,20000,2);
insert into account values(3,3456,30000,3);
