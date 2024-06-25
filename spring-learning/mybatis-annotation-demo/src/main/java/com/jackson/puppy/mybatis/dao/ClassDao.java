package com.jackson.puppy.mybatis.dao;

import com.jackson.puppy.mybatis.dao.sql.provider.ClassProvider;
import com.jackson.puppy.mybatis.domain.Class;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static org.apache.ibatis.mapping.FetchType.LAZY;

/**
 * @author Kevin
 * @since 4/14/2018
 */
@Mapper
public interface ClassDao {

	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "teacher_id", property = "teacherId"),
			@Result(column = "teacher_id", property = "teacher", one = @One(select = "com.jackson.puppy.mybatis.dao.TeacherDao.getById", fetchType = LAZY)),
			@Result(column = "id", property = "students", many = @Many(select = "com.jackson.puppy.mybatis.dao.StudentDao.getByClassId", fetchType = LAZY))
	})
	@Select("select id, name, teacher_id from class")
	List<Class> findAll();

	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "teacher_id", property = "teacherId"),
			@Result(column = "teacher_id", property = "teacher", one = @One(select = "com.jackson.puppy.mybatis.dao.TeacherDao.getById", fetchType = LAZY)),
			@Result(column = "id", property = "students", many = @Many(select = "com.jackson.puppy.mybatis.dao.StudentDao.getByClassId", fetchType = LAZY))
	})
	@Select("select id, name, teacher_id from class where id = #{id}")
	Class getById(Integer id);

	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "teacher_id", property = "teacherId"),
			@Result(column = "teacher_id", property = "teacher", one = @One(select = "com.jackson.puppy.mybatis.dao.TeacherDao.getById", fetchType = LAZY)),
			@Result(column = "id", property = "students", many = @Many(select = "com.jackson.puppy.mybatis.dao.StudentDao.getByClassId", fetchType = LAZY))
	})
	@Select("select id, name, teacher_id from class where teacher_id = #{teacherId}")
	Class getByTeacherId(Integer teacherId);

	@InsertProvider(type = ClassProvider.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer insert(Class aClass);

	@InsertProvider(type = ClassProvider.class, method = "updateById")
	Integer updateById(Class aClass);

	@Delete("delete from class where id = #{id}")
	Integer deleteById(Integer id);


}
