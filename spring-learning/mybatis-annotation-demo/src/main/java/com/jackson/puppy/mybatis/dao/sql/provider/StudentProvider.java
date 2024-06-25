package com.jackson.puppy.mybatis.dao.sql.provider;

import com.jackson.puppy.mybatis.domain.Class;
import com.jackson.puppy.mybatis.domain.Student;

import java.util.Map;

/**
 * @author Kevin
 * @since 4/14/2018
 */
public class StudentProvider extends AbstractSqlProvider {

	public String findByIds(Map<String, Object> map) {
		final StringBuilder sb = new StringBuilder("select id, name, class_id from student where id in ");
		final String foreachClause = forEachClause(map, "ids", L_BRACKET, COMMA, R_BRACKET);
		sb.append(foreachClause);
		return sb.toString();
	}

	public String findByNames(Map<String, Object> map) {
		final StringBuilder sb = new StringBuilder("select id, name, class_id from student where name in ");
		final String foreachClause = forEachClause(map, "names", L_BRACKET, COMMA, R_BRACKET);
		sb.append(foreachClause);
		return sb.toString();
	}

	public String insert(Student student) {

		final Class aClass = student.getaClass();
		if (aClass == null || aClass.getId() == null) {
			return "insert into student (name, class_id) values(#{name}, #{classId})";
		} else {
			return "insert into student (name, class_id) values(#{name}, #{aClass.id})";
		}
	}

	public String updateById(Student student) {

		final Class aClass = student.getaClass();
		if (aClass == null || aClass.getId() == null) {
			return "update student set name = #{name}, class_id = #{classId} where id = #{id}";
		} else {
			return "update student set name = #{name}, class_id = #{aClass.id} where id = #{id}";
		}
	}
}
