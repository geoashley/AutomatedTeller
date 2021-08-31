insert into customer (id, first_name, last_name, customer_number, pin ) values(1, 'Chandler','Bing','1234567',3566);
insert into customer (id, first_name, last_name, customer_number, pin ) values(2, 'Joseph','Tribiani','2345678',3577);
insert into customer (id, first_name, last_name, customer_number, pin ) values(3, 'Monica','Geller','3456789',3588);

insert into account (id, customer_id, account_number, balance, type ) values(1, 1, 'C12345671',30000.00, 0);
insert into account (id, customer_id, account_number, balance, type ) values(2, 1, 'S23456781',40000.00, 1);

insert into account (id, customer_id, account_number, balance, type ) values(3, 2, 'C12345672',500.00, 0);
insert into account (id, customer_id, account_number, balance, type ) values(4, 2, 'S23456782',0.00, 1);

insert into account (id, customer_id, account_number, balance, type ) values(5, 3, 'S23456783',0.00, 1);