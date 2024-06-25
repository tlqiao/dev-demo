package geektime.spring.data.mongodemo.service;

import geektime.spring.data.mongodemo.model.User;
import geektime.spring.data.mongodemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DocumentService {
    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    public void saveOneUser(User user,String collectionName) {
        mongoTemplate.insert(user,collectionName);
    }

    public void saveManyUser(ArrayList<User> userArrayList, String collectionName){
        mongoTemplate.insert(userArrayList,collectionName);
    }

    public Object findAll() {
        List<User> documentList = mongoTemplate.findAll(User.class);
        for (User user: documentList){
            System.out.println("user info is" + user.toString());
        }
        return documentList;
    }

    public Object findById(String id,String collectionName) {
       User user = mongoTemplate.findById(id,User.class,collectionName);
        System.out.println("user info is" + user.toString());
       return user;
    }

    public Object findBySex(String sex,String collectionName){
        Criteria criteria = Criteria.where("sex").is(sex);
        Query query= new Query(criteria);
        List<User> documentList = mongoTemplate.find(query,User.class,collectionName);
        for (User user: documentList){
            System.out.println("user info is" + user.toString());
        }
        return documentList;
    }

    public Object findBySexOrderByAge(String sex,String collectionName) {
        Criteria criteria = Criteria.where("sex").is(sex);
        Query query= new Query(criteria).with(Sort.by("age"));
        List<User> documentList = mongoTemplate.find(query,User.class,collectionName);
        for (User user: documentList){
            System.out.println("uer info is" + user.toString());
        }
        return documentList;
    }

    public Object findBySexAndAge(String sex,int age,String collectionName) {
        Criteria criteriaA = Criteria.where("sex").is(sex);
        Criteria criteriaB = Criteria.where("sex").gte(sex);
        Criteria multipleCriteria = new Criteria().andOperator(criteriaA,criteriaB);
        Query query= new Query(multipleCriteria);
        List<User> documentList = mongoTemplate.find(query,User.class,collectionName);
        for (User user: documentList){
            System.out.println("user info is" + user.toString());
        }
        return documentList;
    }
}
