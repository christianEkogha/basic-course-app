create table course (
id bigint not null primary key,
nom varchar(255) not null,
jour date not null
);

create table partant (
id bigint not null primary key,
nom varchar(255) not null
);

create table course_partant (
id bigint AUTO_INCREMENT primary key,
course_id bigint not null,
partant_id bigint not null,
foreign key (course_id) references course(id),
foreign key (partant_id) references partant(id)
);

