create table customer
(
    id bigint auto_increment primary key,
    name varchar(32) null,
    contactName varchar(32)  null,
    address varchar(255) null,
    city varchar(128) null,
    postCode varchar(32) null,
    country varchar(32) null
);
