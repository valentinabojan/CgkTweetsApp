package com.tweets.service;

import com.tweets.repository.TweetsRepository;
import com.tweets.service.entity.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TweetsService {

    @Autowired
    TweetsRepository repository;

//    public List<Customer> findAllCustomers() {
//        return repository.findAll();
//    }

    public void createNewTweet(Tweet tweet) {
        tweet.setDate(LocalDate.now());

        repository.save(tweet);
    }

}
