package com.jackson.puppy.mybatis.dao;

import com.jackson.puppy.mybatis.domain.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static org.apache.ibatis.mapping.FetchType.LAZY;

/**
 * @author Kevin
 * @since 4/14/2018
 */
@Mapper
public interface TeacherDao {

	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "id", property = "classes", many = @Many(select = "com.jackson.puppy.mybatis.dao.ClassDao.getById", fetchType = LAZY))
	})
	@Select("select id, name from teacher")
	List<Teacher> findAll();

	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "id", property = "classes", many = @Many(select = "com.jackson.puppy.mybatis.dao.ClassDao.getById", fetchType = LAZY))
	})
	@Select("select id, name from teacher where id = #{id}")
	Teacher getById(Integer id);

	@Insert("insert into teacher(name) values(#{name})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer insert(Teacher teacher);

	@Update("update teacher set name = #{name} where id = #{id}")
	Integer updateById(Teacher teacher);

	@Delete("delete from teacher where id = #{id}")
	Integer deleteById(Integer id);
}
