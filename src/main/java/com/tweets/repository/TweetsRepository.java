package com.tweets.repository;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.service.model.Tweet;
import com.tweets.service.entity.mongo.CommentMongo;
import com.tweets.service.valueobject.PageParams;

import java.util.List;


public interface TweetsRepository {

    Tweet insert(Tweet tweet);

    Tweet insertComment(String tweetId, CommentMongo comment);

    Tweet updateTweet(Tweet tweet);

    Tweet findTweetById(String tweetId);

    List<TweetTO> findAllByOrderByDateDesc(PageParams pageParams);

    List<CommentMongo> findCommentsByTweet(String tweetId, PageParams pageParams);

    Boolean isTweetLiked(String tweetId, String username);

    Boolean isTweetDisliked(String tweetId, String username);
}
