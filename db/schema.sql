drop table if exists users;
drop table if exists account;
drop table if exists accountusers;
drop table if exists application;
drop table if exists joint;

create table users(
    username text primary key,
    password text,
    category text
);

create table account(
    id serial primary key,
    number integer,
    balance integer
);

create table accountusers(
    id serial primary key,
    username text,
    accountnumber integer
);

create table application(
    id serial primary key,
    username text
);

create table joint(
    id serial primary key,
    username text,
    account integer,
    adduser text
);

insert into users(username,password,category) values('job','password','employee');
insert into users(username,password,category) values('dbadmin','dbadmin','admin');
