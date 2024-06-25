package com.example.shardingdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shardingdemo.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
//    @Insert("insert into t_user (name, age)"
//            + "values (#{name}, #{age})")
//    @Options(useGeneratedKeys = true)
//    int save(User user);
//
//    @Select("select * from t_user where id = #{id}")
//    @Results({
//            @Result(id = true, column = "id", property = "id"),
//    })
//    User findById(@Param("id") Long id);
}
