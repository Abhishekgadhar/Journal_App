package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Queue;

public class UserRepositoryImpl  {


    @Autowired
    private MongoTemplate mongoTemplate;


    public List<User> getUserForSA(){
        Query query = new Query();

//        query.addCriteria(Criteria.where("username").is("ram"));
//        query.addCriteria(Criteria.where("field").lte("value"));
//        query.addCriteria(criteria.orOperator(Criteria.where("email").exists(true),Criteria.where("sentimentAnalysis").is(true))); // we can use or and and
        query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> users = mongoTemplate.find(query,User.class);

        return users;
    }
}
