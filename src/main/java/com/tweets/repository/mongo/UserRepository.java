package com.tweets.repository.mongo;

import com.tweets.service.entity.mongo.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

//@Profile("cassandra")
public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);
}