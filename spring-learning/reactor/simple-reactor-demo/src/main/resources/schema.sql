drop table t_user if exists;
create table t_user (
                          id bigint auto_increment
                          name varchar(255),
                          price bigint,
                          address varchar(255),
                          primary key (id)
);
