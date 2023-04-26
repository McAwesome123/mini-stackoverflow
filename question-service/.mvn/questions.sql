create database if not exists stackoverflow_questions;
use stackoverflow_questions;

create table questions
(
    id int unsigned primary key not null auto_increment,
    title varchar(120) not null,
    description text not null,
    closed boolean not null,
    userId int unsigned not null,

    foreign key (userId) references stackoverflowusers.users(id)
);

create table answers
(
    id int unsigned primary key not null auto_increment,
    description text not null,
    marked boolean not null,
    userId int unsigned not null,
    questionId int unsigned not null,

    foreign key (userId) references stackoverflowusers.users(id),
    foreign key (questionId) references  questions(id)
);
