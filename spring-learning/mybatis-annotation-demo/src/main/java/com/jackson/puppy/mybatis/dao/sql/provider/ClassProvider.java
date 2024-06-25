package com.jackson.puppy.mybatis.dao.sql.provider;

import com.jackson.puppy.mybatis.domain.Class;
import com.jackson.puppy.mybatis.domain.Teacher;

/**
 * @author Kevin
 * @since 4/14/2018
 */
public class ClassProvider extends AbstractSqlProvider {

	public String insert(Class aClass) {

		final Teacher teacher = aClass.getTeacher();
		if (teacher == null || teacher.getId() == null) {
			return "insert into class (name, teacher_id) values(#{name}, #{teacherId})";
		} else {
			return "insert into class (name, teacher_id) values(#{name}, #{teacher.id})";
		}
	}

	public String updateById(Class aClass) {

		final Teacher teacher = aClass.getTeacher();
		if (teacher == null || teacher.getId() == null) {
			return "update class set name = #{name}, teacher_id = #{teacherId} where id = #{id}";
		} else {
			return "update class set name = #{name}, teacher_id = #{teacher.id} where id = #{id}";
		}
	}
}
