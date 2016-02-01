package com.tweets.service;

import com.tweets.repository.TweetsRepository;
import com.tweets.service.entity.Tweet;
import com.tweets.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;
import java.util.Set;

@Service
public class TweetsService {

    @Autowired
    TweetsRepository repository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

//    public List<Customer> findAllCustomers() {
//        return repository.findAll();
//    }

    public Tweet createNewTweet(Tweet tweet, String author) {
        validateTweet(tweet);

        tweet.setDate(LocalDate.now());
        tweet.setAuthor(author);

        return repository.insert(tweet);
    }

    private void validateTweet(Tweet tweet) {
        Set<ConstraintViolation<Tweet>> tweetConstraintViolations = validator.validate(tweet);
        if (!tweetConstraintViolations.isEmpty())
            throw new ValidationException(tweetConstraintViolations.iterator().next().getMessage());
    }

}
