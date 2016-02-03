package com.tweets.service;

import com.tweets.repository.TweetsRepository;
import com.tweets.service.entity.Tweet;
import com.tweets.service.exception.ValidationException;
import com.tweets.service.valueobject.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class TweetsService {

    @Autowired
    TweetsRepository repository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public Tweet createNewTweet(Tweet tweet, String author) {
        validateTweet(tweet);

        tweet.setDate(LocalDateTime.now());
        tweet.setAuthor(author);

        return repository.insert(tweet);
    }

    public List<Tweet> findTweets(PageParams pageParams) {
        return repository.findAllByOrderByDateDesc(new PageRequest(pageParams.getPage(), pageParams.getSize()));
    }

    private void validateTweet(Tweet tweet) {
        Set<ConstraintViolation<Tweet>> tweetConstraintViolations = validator.validate(tweet);
        if (!tweetConstraintViolations.isEmpty())
            throw new ValidationException(tweetConstraintViolations.iterator().next().getMessage());
    }

}
