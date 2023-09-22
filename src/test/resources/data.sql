insert into partant (id_partant, nom_partant) 
values	(123, 'partant 1'),
		(124, 'partant 2'),
		(125, 'partant 3'),
		(126, 'partant 4'),
		(127, 'partant 5');

insert into course (id_course, nom_course, jour_course) 
values	(978, 'course 1', '2023-10-05');

insert into position (id_course, id_partant, numero) 
values	(978, 123, 1),
		(978, 124, 2),
		(978, 125, 3),
		(978, 126, 4);
