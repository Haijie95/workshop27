package com.haijie.workshop27.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static com.haijie.workshop27.Constants.*;

@Repository
public class GameRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Document> getGameById(int gid) {
            //query by game id
            Criteria c = Criteria.where(FIELD_GID).in(gid);
    
            Query query = Query.query(c);
    
            return mongoTemplate.find(query, Document.class, COLLECTION_GAME);
    }
    
}
