insert into partant (id, nom) 
values	(123, 'partant 1'),
		(124, 'partant 2'),
		(125, 'partant 3'),
		(126, 'partant 4'),
		(127, 'partant 5');

insert into course (id, nom, jour) 
values	(978, 'course 1', '2023-10-05');

insert into course_partant (course_id, partant_id) 
values	(978, 123),
		(978, 124),
		(978, 125),
		(978, 126);
