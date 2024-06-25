package geektime.spring.data.mongodemo;

import geektime.spring.data.mongodemo.model.User;
import geektime.spring.data.mongodemo.service.CollectionService;
import geektime.spring.data.mongodemo.service.DocumentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTemplateTest {
    @Autowired
    CollectionService collectionService;
    @Autowired
    DocumentService documentService;

    @Test
    public void testA() {
        collectionService.createCollection("user");
        collectionService.collectionExists("user");
        User user1= User.builder().name("name1").age(20).sex("male").address("chengdu").build();
        documentService.saveOneUser(user1,"user");
        ArrayList<User> userList = new ArrayList<>();
        User user2=User.builder().name("name2").age(30).sex("female").address("beijing").build();
        User user3=User.builder().name("name3").age(40).sex("male").address("shenzhen").build();
        userList.add(user2);
        userList.add(user3);
        documentService.saveManyUser(userList,"user");
    }

    @Test
    public void testB() {
        documentService.findAll();
//        documentService.findById("2","userCollection");
        documentService.findBySex("male","user");
        documentService.findBySexAndAge("male",20,"user");
        documentService.findBySexOrderByAge("male","user");
    }
}
