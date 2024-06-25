package mybatis.plus.demo;

import lombok.extern.slf4j.Slf4j;
import mybatis.plus.demo.mapper.UserMapper;
import mybatis.plus.demo.model.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@MapperScan("mybatis.plus.demo.mapper")
public class MybatisPlusDemoApplication implements ApplicationRunner {
	@Autowired
	UserMapper userMapper;

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusDemoApplication.class, args);
	}
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//添加操作
		User user = new User();
		user.setName("testName");
		user.setEmail("testEmail");
		user.setAge(100);
		userMapper.insert(user);
		//更新操作
		user.setId(1);
		user.setName("newTestName");
		userMapper.updateById(user);
		List<User> users = userMapper.selectList(null); //条件构造器先不用
		users.forEach(System.out::println);
	}
}

