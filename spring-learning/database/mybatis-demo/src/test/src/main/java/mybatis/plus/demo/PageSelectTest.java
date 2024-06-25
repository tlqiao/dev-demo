package mybatis.plus.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mybatis.plus.demo.mapper.UserMapper;
import mybatis.plus.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PageSelectTest {
    @Autowired
    UserMapper userMapper;
    @Test
    public void addUser(){

        List<User> userList=new ArrayList<>();
        for(int i=0;i<10;i++){
            User user = new User();
            user.setName("testName"+i);
            user.setAge(100);
            user.setEmail("testEmail"+i+"@gmail.com");
            userList.add(user);
        }
        userList.forEach(element->userMapper.insert(element));
    }
    @Test
    public void selectUserByPage(){
        //通过SelectPage实现分页查询
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("id");
        Page<User> page = new Page<>(1, 4);
        Page<User> result = userMapper.selectPage(page, wrapper);
        System.out.println("page == result: " + (page == result));
        System.out.println("size: " + result.getSize());
        System.out.println("total: " + result.getTotal());
        for(User user : result.getRecords()) {
            System.out.println(user);
        }
        //通过SelectMapsPage实现分页查询
        Page<Map<String,Object>> pageMap = new Page<>(1, 4);
        userMapper.selectMapsPage(pageMap, null);
        System.out.println("size: " + pageMap.getSize());
        System.out.println("total: " + pageMap.getTotal());
        System.out.println("pages: " + pageMap.getPages());
        for(Map<String,Object> map : pageMap.getRecords()) {
            System.out.println(map);
        }
    }

}
