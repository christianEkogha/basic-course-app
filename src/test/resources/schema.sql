create table course (
id_course bigint not null primary key,
nom_course varchar(255) not null,
jour_course date not null
);

create table partant (
id_partant bigint not null primary key,
nom_partant varchar(255) not null
);

create table position (
id_course bigint not null,
id_partant bigint not null,
numero int not null,
foreign key (id_course) references course(id_course),
foreign key (id_partant) references partant(id_partant)
);

