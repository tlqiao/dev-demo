create table college
(
    collegeId bigint auto_increment primary key,
    collegeName varchar(32) null,
    collegeLocation varchar(32)  null
);

create table student
(
    studentId bigint auto_increment primary key,
    studentName varchar(32) null,
    studentAge varchar(32)  null
);
