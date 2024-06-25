package shardingsphere.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import shardingsphere.demo.mapper.TestStanderMapper;
import shardingsphere.demo.model.TestStander;

import javax.annotation.Resource;

@SpringBootTest
public class TestShardingSphereDemo {
    @Resource
    public TestStanderMapper testStandardMapper;

    @Test
    void insert() {
        for (int i = 0; i < 10; i++) {
            TestStander testStandard = new TestStander();
            testStandard.setName("张三" + i);
            testStandardMapper.insert(testStandard);
        }
    }
}
