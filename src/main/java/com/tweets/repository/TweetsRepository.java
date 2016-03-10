package com.tweets.repository;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.service.model.Comment;
import com.tweets.service.model.Tweet;
import com.tweets.service.entity.mongo.CommentMongo;
import com.tweets.service.valueobject.PageParams;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TweetsRepository {

    Tweet insert(Tweet tweet);

    Comment insertComment(String tweetId, Comment comment);

    Tweet updateTweet(Tweet tweet);

    Tweet findTweetById(String tweetId);

    List<TweetTO> findAllByOrderByDateDesc(PageParams pageParams);

    List<Comment> findCommentsByTweet(String tweetId, PageParams pageParams);

    Boolean isTweetLiked(String tweetId, String username);

    Boolean isTweetDisliked(String tweetId, String username);
}
