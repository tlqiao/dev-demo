package com.example.shardingdemo;

import com.example.shardingdemo.model.User;
import com.example.shardingdemo.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testReadWriteSplitting() {
        // 插入一条用户记录
        User user = new User();
        user.setName("张三");
        user.setAge(100);
        userService.insert(user);

        // 查询用户记录，此时应从从库中读取
        User user1 = userService.selectById(user.getId());
        Assert.assertEquals("张三", user1.getName());
        // 删除用户记录，此时应写入主库

    }
}
