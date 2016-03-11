package com.tweets.service;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.repository.TweetsRepository;
import com.tweets.service.exception.ValidationException;
import com.tweets.service.model.Comment;
import com.tweets.service.model.Tweet;
import com.tweets.service.valueobject.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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
        tweet.setUsersWhoDisliked(new HashSet<>());
        tweet.setUsersWhoLiked(new HashSet<>());
        tweet.setDate(LocalDateTime.now());
        tweet.setAuthor(userService.getPrincipalName());

        return new TweetTO(repository.insert(tweet));
    }

    public List<TweetTO> findTweets(PageParams pageParams) {
        List<TweetTO> tweets = repository.findAllByOrderByDateDesc(pageParams);

        tweets.stream().forEach(tweet -> {
            tweet.setLiked(repository.isTweetLiked(tweet.getId(), userService.getPrincipalName()));
            tweet.setDisliked(repository.isTweetDisliked(tweet.getId(), userService.getPrincipalName()));
        });

        return tweets;
    }

    public Comment createNewComment(String tweetId, Comment comment) {
        validateComment(comment);

        comment.setAuthor(userService.getPrincipalName());
        comment.setDate(LocalDateTime.now());

        return repository.insertComment(tweetId, comment);
    }

    public TweetTO likeTweet(String tweetId) {
        Tweet tweet = repository.findTweetById(tweetId);
        if (tweet == null)
            return null;

        String username = userService.getPrincipalName();

        if (tweet.getUsersWhoLiked().contains(username)){
            TweetTO likedTweet = new TweetTO(tweet);
            likedTweet.setLiked(true);
            return likedTweet;
        }

        if (tweet.getUsersWhoDisliked().contains(username))
            tweet.getUsersWhoDisliked().remove(username);

        tweet.getUsersWhoLiked().add(username);

        TweetTO likedTweet = new TweetTO(repository.updateTweet(tweet));
        likedTweet.setLiked(true);

        return likedTweet;
    }

    public TweetTO dislikeTweet(String tweetId) {
        Tweet tweet = repository.findTweetById(tweetId);
        if (tweet == null)
            return null;

        String username = userService.getPrincipalName();

        if (tweet.getUsersWhoDisliked().contains(username)){
            TweetTO likedTweet = new TweetTO(tweet);
            likedTweet.setDisliked(true);
            return likedTweet;
        }

        if (tweet.getUsersWhoLiked().contains(username))
            tweet.getUsersWhoLiked().remove(username);

        tweet.getUsersWhoDisliked().add(username);

        TweetTO dislikedTweet = new TweetTO(repository.updateTweet(tweet));
        dislikedTweet.setDisliked(true);

        return dislikedTweet;
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
