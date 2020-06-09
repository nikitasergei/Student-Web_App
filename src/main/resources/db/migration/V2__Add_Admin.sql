insert into teacher (id, password, username, active)
values (1, '123', 'Admin', true);

insert into teacher_role (teacher_id, roles)
values (1, 'ADMIN');

insert into teacher_role (teacher_id, roles)
values (1, 'TEACHER');
