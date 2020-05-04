create sequence hibernate_sequence start 1 increment 1;
create table archive
(
    id         int8 not null,
    rating     int4 not null,
    course_id  int8 not null,
    student_id int8 not null,
    teacher_id int8 not null,
    primary key (id)
);
create table course
(
    id          int8 not null,
    course_name varchar(255),
    teacher_id  int8,
    primary key (id)
);
create table course_students
(
    student_id int8 not null,
    course_id  int8 not null,
    primary key (student_id, course_id)
);
create table student
(
    id              int8    not null,
    activation_code varchar(255),
    active          boolean not null,
    email           varchar(255),
    filename        varchar(255),
    password        varchar(255),
    username        varchar(255),
    primary key (id)
);
create table student_role
(
    student_id int8 not null,
    roles      varchar(255)
);

create table teacher
(
    id              int8    not null,
    activation_code varchar(255),
    active          boolean not null,
    email           varchar(255),
    filename        varchar(255),
    password        varchar(255),
    username        varchar(255),
    primary key (id)
);

create table teacher_role
(
    teacher_id int8 not null,
    roles      varchar(255)
);

alter table if exists archive
    add constraint courses_archives foreign key (course_id) references course;

alter table if exists archive
    add constraint students_archives foreign key (student_id) references student;

alter table if exists archive
    add constraint teachers_archives foreign key (teacher_id) references teacher;

alter table if exists course
    add constraint teachers_courses foreign key (teacher_id) references teacher;

alter table if exists course_students
    add constraint FKgut5xj4l8sk6hg3l0t2su2pnc foreign key (course_id) references course;

alter table if exists course_students
    add constraint students_courses foreign key (student_id) references student;

alter table if exists student_role
    add constraint students_roles foreign key (student_id) references student;

alter table if exists teacher_role
    add constraint teachers_roles foreign key (teacher_id) references teacher;