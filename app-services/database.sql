create database walmart;
use walmart;

create table employee(
Id int NOT NULL AUTO_INCREMENT primary key,
email varchar(255) not null unique,
first_name varchar(255) not null,
last_name varchar(255) not null,
password varchar(255) not null,
gender varchar(255) not null,
role varchar(255) not null,
manager_id int
);


create table skill(
	Id int NOT NULL AUTO_INCREMENT primary key,
    name varchar(255) not null,
    family varchar(255) not null,
    proficiency varchar(255) not null,
    role_type varchar(255) not null,
    experience int,
    current_historic varchar(255) not null,
    approver_comments varchar(255),
    experience_exposure_text varchar(255) ,
    status varchar(255) not null,
    employee_id int not null,
    foreign key (employee_id) references employee(id) on delete cascade
);

create table template(
	Id int NOT NULL AUTO_INCREMENT primary key,
	from_address varchar(255) not null,
    to_address varchar(255) not null,
    cc varchar(255) not null,
    subject varchar(255) not null,
    body_content varchar(255) not null,
    created_by int not null,
    status varchar(255) not null,
    foreign key (created_by) references employee(id) on delete cascade
)