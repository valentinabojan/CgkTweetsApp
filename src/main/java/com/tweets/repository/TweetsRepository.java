package com.tweets.repository;

import com.tweets.service.entity.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetsRepository extends MongoRepository<Tweet, String> {

//
//    public Customer findByFirstName(String firstName);
//
//    public List<Customer> findByLastName(String lastName);

}