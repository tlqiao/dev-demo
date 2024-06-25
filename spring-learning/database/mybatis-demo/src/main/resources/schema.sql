create table t_coffee (
    id bigint not null auto_increment,
    name varchar(255),
    price bigint not null,
    create_time timestamp,
    update_time timestamp,
    primary key (id)
);
create table t_user (
    id bigint not null auto_increment,
    name varchar(255),
    age integer,
    email varchar(255),
    create_time timestamp,
    update_time timestamp,
    primary key(id)
);

create table t_blog
(
    id          bigint not null auto_increment,
    user_id     bigint not null,
    content     varchar(255),
    create_time timestamp,
    update_time timestamp,
    primary key (id)
)
