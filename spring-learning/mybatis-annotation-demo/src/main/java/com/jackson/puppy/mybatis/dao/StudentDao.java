package com.jackson.puppy.mybatis.dao;

import com.jackson.puppy.mybatis.dao.sql.provider.StudentProvider;
import com.jackson.puppy.mybatis.domain.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static org.apache.ibatis.mapping.FetchType.LAZY;

/**
 * @author Kevin
 * @since 4/14/2018
 */
@Mapper
public interface StudentDao {

	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "class_id", property = "classId"),
			@Result(column = "class_id", property = "aClass", one = @One(select = "com.jackson.puppy.mybatis.dao.ClassDao.getById", fetchType = LAZY)),
	})
	@Select("select id, name, class_id from student")
	List<Student> findAll();

	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "class_id", property = "classId"),
			@Result(column = "class_id", property = "aClass", one = @One(select = "com.jackson.puppy.mybatis.dao.ClassDao.getById", fetchType = LAZY)),
	})
	@Select("select id, name, class_id from student where id = #{id}")
	Student getById(Integer id);

	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "class_id", property = "classId"),
	})
	@SelectProvider(type = StudentProvider.class, method = "findByIds")
	List<Student> findByIds(@Param("ids") List<Integer> ids);

	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "class_id", property = "classId"),
	})
	@SelectProvider(type = StudentProvider.class, method = "findByNames")
	List<Student> findByNames(@Param("names") List<String> names);

	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "class_id", property = "classId"),
			@Result(column = "class_id", property = "aClass", one = @One(select = "com.jackson.puppy.mybatis.dao.ClassDao.getById", fetchType = LAZY)),
	})
	@Select("select id, name, class_id from student where class_id = #{classId}")
	List<Student> getByClassId(Integer classId);

	@InsertProvider(type = StudentProvider.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer insert(Student student);

	@UpdateProvider(type = StudentProvider.class, method = "updateById")
	Integer updateById(Student student);

	@Delete("delete from student where id = #{id}")
	Integer deleteById(Integer id);
}
