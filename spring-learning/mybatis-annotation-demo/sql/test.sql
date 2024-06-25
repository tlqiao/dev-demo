CREATE DATABASE IF NOT EXISTS test_db DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use test_db

CREATE TABLE IF NOT EXISTS teacher(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS class(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    teacher_id INT NOT NULL,
    CONSTRAINT fk_class_teacher FOREIGN KEY(teacher_id) REFERENCES teacher(id)
);

CREATE TABLE IF NOT EXISTS student(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    class_id INT NOT NULL,
    CONSTRAINT fk_student_class FOREIGN KEY(class_id) REFERENCES class(id)
);

INSERT INTO teacher(id, name) value(1, 'Teacher A');
INSERT INTO teacher(id, name) value(2, 'Teacher B');
INSERT INTO teacher(id, name) value(3, 'Teacher C');
INSERT INTO teacher(id, name) value(4, 'Teacher D');
INSERT INTO teacher(id, name) value(5, 'Teacher E');

INSERT INTO class(id, name, teacher_id) value(1, 'English A', 1);
INSERT INTO class(id, name, teacher_id) value(2, 'English B', 1);
INSERT INTO class(id, name, teacher_id) value(3, 'Chinese A', 2);
INSERT INTO class(id, name, teacher_id) value(4, 'Chinese B', 3);
INSERT INTO class(id, name, teacher_id) value(5, 'Math A', 4);
INSERT INTO class(id, name, teacher_id) value(6, 'Math B', 5);


INSERT INTO student(id, name, class_id) value(1, 'Student A', 1);
INSERT INTO student(id, name, class_id) value(2, 'Student B', 1);
INSERT INTO student(id, name, class_id) value(3, 'Student C', 1);
INSERT INTO student(id, name, class_id) value(4, 'Student D', 2);
INSERT INTO student(id, name, class_id) value(5, 'Student E', 2);
INSERT INTO student(id, name, class_id) value(6, 'Student F', 3);
INSERT INTO student(id, name, class_id) value(7, 'Student G', 4);
INSERT INTO student(id, name, class_id) value(8, 'Student H', 4);
INSERT INTO student(id, name, class_id) value(9, 'Student I', 5);
INSERT INTO student(id, name, class_id) value(10, 'Student J', 5);
INSERT INTO student(id, name, class_id) value(11, 'Student K', 5);
INSERT INTO student(id, name, class_id) value(12, 'Student L', 6);