package com.tweets.service;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.repository.TweetsRepository;
import com.tweets.service.entity.Comment;
import com.tweets.service.entity.Tweet;
import com.tweets.service.exception.ValidationException;
import com.tweets.service.valueobject.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TweetsService {

    @Autowired
    TweetsRepository repository;

    @Autowired
    UserSecurityDetailsService userService;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public TweetTO createNewTweet(Tweet tweet) {
        validateTweet(tweet);

        tweet.setComments(new ArrayList<>());
        tweet.setUsersWhoDisliked(new ArrayList<>());
        tweet.setUsersWhoLiked(new ArrayList<>());
        tweet.setDate(LocalDateTime.now());
        tweet.setAuthor(userService.getPrincipalName());

        return new TweetTO(repository.insert(tweet));
    }

    public List<TweetTO> findTweets(PageParams pageParams) {
        return repository.findAllByOrderByDateDesc(pageParams);
    }

    public Tweet createNewComment(String tweetId, Comment comment) {
        validateComment(comment);

        comment.setAuthor(userService.getPrincipalName());
        comment.setDate(LocalDateTime.now());

        return repository.insertComment(tweetId, comment);
    }

    public List<Comment> findTweetComments(String tweetId, PageParams pageParams) {
        return repository.findCommentsByTweet(tweetId, pageParams);
    }

    private void validateComment(Comment comment) {
        Set<ConstraintViolation<Comment>> tweetConstraintViolations = validator.validate(comment);
        if (!tweetConstraintViolations.isEmpty())
            throw new ValidationException(tweetConstraintViolations.iterator().next().getMessage());
    }

    private void validateTweet(Tweet tweet) {
        Set<ConstraintViolation<Tweet>> tweetConstraintViolations = validator.validate(tweet);
        if (!tweetConstraintViolations.isEmpty())
            throw new ValidationException(tweetConstraintViolations.iterator().next().getMessage());
    }

}
