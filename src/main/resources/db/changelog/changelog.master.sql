--liquibase formatted sql

--changeset tattoo_kot:1.0.1
create table users (
    id int generated always as identity,
    login varchar(100) not null unique,
    password varchar(100) not null,
    created date default current_timestamp,
    updated date default current_timestamp
);

--changeset tattoo_kot:1.0.2
drop table users;

create table users (
    id int generated always as identity,
    login varchar(100) not null unique,
    password varchar(100) not null,
    created date default current_timestamp,
    updated date default current_timestamp,
    primary key (id),
    constraint fk_user foreign key (id) references users(id)
);

--changeset tattoo_kot:1.0.3
create table roles (
    id int generated always as identity,
    name varchar(100) not null unique,
    primary key (id),
    constraint fk_role foreign key (id) references roles(id)
);

--changeset tattoo_kot:1.0.4
create table user_roles (
    user_id bigint,
    role_id bigint
);

--changeset tattoo_kot:1.0.5
create table files (
    id int generated always as identity,
    file_name varchar(255) not null,
    location text not null unique,
    created date default current_timestamp,
    updated date default current_timestamp,
    primary key (id),
    constraint fk_files foreign key (id) references files(id)
);

--changeset tattoo_kot:1.0.6
create table events (
    user_id bigint,
    file_id bigint
);

--changeset tattoo_kot:1.0.7
insert into roles(name) values('ROLE_ADMIN');
insert into roles(name) values('ROLE_USER');
insert into roles(name) values('ROLE_MODERATOR');

--changeset tattoo_kot:1.0.8
alter table user_roles rename to users_roles;

--changeset tattoo_kot:1.0.9
alter table users_roles rename column role_id to roles_id;

--changeset tattoo_kot:1.1.0
alter table events add id int generated always as identity;

--changeset tattoo_kot:1.1.1
alter table events drop column id;

--changeset tattoo_kot:1.1.2
alter table events add created date default current_timestamp;

--changeset tattoo_kot:1.1.3
alter table events add id int generated always as identity;

create table user_events (
    user_id bigint,
    events_id bigint
);

--changeset tattoo_kot:1.1.4
alter table user_events rename to users_events;

--changeset tattoo_kot:1.1.5
alter table files alter column location type varchar(255) using location::varchar(255);

--changeset tattoo_kot:1.1.6
create table users_files (
     user_id bigint,
     files_id bigint
);

--changeset tattoo_kot:1.1.7
drop table users_events;

--changeset tattoo_kot:1.1.8
drop table users_files;
alter table files drop column created;
alter table files drop column updated;

--changeset tattoo_kot:1.1.9
drop table users_roles;

--changeset tattoo_kot:1.2.0
create table users_roles (
    user_id bigint,
    roles_id bigint
);