package geektime.spring.data.mongodemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {
    @Autowired
    MongoTemplate mongoTemplate;

    public String createCollection(String collectionName) {
        if(mongoTemplate.collectionExists(collectionName)){
            mongoTemplate.getCollection(collectionName).drop();
        }
        mongoTemplate.createCollection(collectionName);
        return mongoTemplate.collectionExists(collectionName) ? "create collection success" : "create collection failed";
    }

    public String createCollectionWithSize(String collectionName, long size, long maxDocument) {
        CollectionOptions collectionOptions = CollectionOptions.empty().capped().size(size).maxDocuments(maxDocument);
        mongoTemplate.createCollection(collectionName, collectionOptions);
        return mongoTemplate.collectionExists(collectionName) ? "create collection success" : "create collection failed";
    }

    public Object getCollectionNames() {
        return mongoTemplate.getCollectionNames();
    }

    public boolean collectionExists(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }

    public String dropCollection(String collectionName) {
        mongoTemplate.getCollection(collectionName).drop();
        return !mongoTemplate.collectionExists(collectionName) ? "delete collection successfully" : "delete collection failed";
    }

}
