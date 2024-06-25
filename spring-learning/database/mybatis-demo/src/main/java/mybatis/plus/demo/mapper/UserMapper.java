package mybatis.plus.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mybatis.plus.demo.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
    // 在对应的Mapper上面继承基本的类 BaseMapper
    // 所有的CRUD操作都已经编写完成了
}


