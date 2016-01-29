package com.tweets.repository;

import com.tweets.service.entity.Session;
import com.tweets.service.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, String> {

    public Session findByUsername(String username);
}