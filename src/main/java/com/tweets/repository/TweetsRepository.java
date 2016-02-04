package com.tweets.repository;

import com.tweets.service.entity.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TweetsRepository extends MongoRepository<Tweet, String> {

    //@Query(fields = "{comments: 0}")
    public List<Tweet> findAllByOrderByDateDesc(Pageable pageable);

}