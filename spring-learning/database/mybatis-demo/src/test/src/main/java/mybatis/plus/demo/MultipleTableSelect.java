package mybatis.plus.demo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mybatis.plus.demo.mapper.BlogMapper;
import mybatis.plus.demo.mapper.UserMapper;
import mybatis.plus.demo.model.Blog;
import mybatis.plus.demo.model.BlogUserVo;
import mybatis.plus.demo.model.User;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MultipleTableSelect {
    @Autowired
    UserMapper userMapper;
    @Autowired
    BlogMapper blogMapper;

//添加测试数据
    @Test
    public void addUserAndBlog() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("testName" + i);
            user.setAge(100);
            user.setEmail("testEmail" + i + "@gmail.com");
            userList.add(user);
        }
        userList.forEach(element -> userMapper.insert(element));

        for (int j = 0; j < 7; j++) {
            Blog blog = new Blog();
            blog.setUser_id(j);
            blog.setContent("this is user's blog " + j);
            blogMapper.insert(blog);
        }
    }
//多表查询验证
    @Test
    public void selectDataFromMultipleTable() {
        List<BlogUserVo> blogUserVoList = blogMapper.getAllUserBlogs();
        blogUserVoList.forEach(item -> {
            System.out.println("this is from blogUserVoList" + item.getName());
            System.out.println("this is from blogUserVoList" + item.getContent());
            System.out.println("this is from blogUserVoList" + item.getCreateTime());
            System.out.println("this is from blogUserVoList" + item.getUpdateTime());
        });
    }
    //多表分页查询验证
    @Test
    public void selectDataByPage() {
        getDataFromMultipleTableByPage(2,3);
    }
    public void getDataFromMultipleTableByPage(int current, int size) {
        Page<BlogUserVo> page = new Page<>(current,size);
        blogMapper.getAllUserBlogsByPage(page);
        page.getRecords().forEach(System.out::println);
    }
}
