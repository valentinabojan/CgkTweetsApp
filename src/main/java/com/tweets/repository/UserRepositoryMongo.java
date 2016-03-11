package com.tweets.repository;

import com.tweets.service.entity.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepositoryMongo extends MongoRepository<User, String> {

    public User findByUsername(String username);
}