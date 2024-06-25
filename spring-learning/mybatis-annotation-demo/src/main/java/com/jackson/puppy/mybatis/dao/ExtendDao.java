package com.jackson.puppy.mybatis.dao;

import com.jackson.puppy.mybatis.dao.sql.provider.ExtendProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

import static org.apache.ibatis.type.JdbcType.INTEGER;
import static org.apache.ibatis.type.JdbcType.VARCHAR;

/**
 * @author Kevin
 * @since 4/14/2018
 */
@Mapper
public interface ExtendDao {

	@Results({
			@Result(column = "teacherId", property = "teacherId", jdbcType = INTEGER),
			@Result(column = "teacherName", property = "teacherName", jdbcType = VARCHAR),
			@Result(column = "classId", property = "classId", jdbcType = INTEGER),
			@Result(column = "className", property = "className", jdbcType = VARCHAR),
			@Result(column = "studentId", property = "studentId", jdbcType = INTEGER),
			@Result(column = "studentName", property = "studentName", jdbcType = VARCHAR)
	})
	@Select("select t.id teacherId, t.name teacherName, c.id classId, c.name className, s.id studentId, s.name studentName " +
			"from teacher t " +
			"inner join class c on t.id = c.teacher_id " +
			"inner join student s on c.id = s.class_id")
	List<Map<String, Object>> joinAll();

	@Results({
			@Result(column = "teacherId", property = "teacherId", jdbcType = INTEGER),
			@Result(column = "teacherName", property = "teacherName", jdbcType = VARCHAR),
			@Result(column = "classId", property = "classId", jdbcType = INTEGER),
			@Result(column = "className", property = "className", jdbcType = VARCHAR),
			@Result(column = "studentId", property = "studentId", jdbcType = INTEGER),
			@Result(column = "studentName", property = "studentName", jdbcType = VARCHAR)
	})
	@SelectProvider(type = ExtendProvider.class, method = "joinAllByTeacherIdAndClassIdAndStudentId")
	List<Map<String, Object>> joinAllByTeacherIdAndClassIdAndStudentId(Map<String, Integer> map);
}
