package com.tweets.repository;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.service.model.Comment;
import com.tweets.service.model.Tweet;
import com.tweets.service.valueobject.PageParams;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Profile(value = "ignite")
@Repository
public class TweetsRepositoryIgnite implements TweetsRepository {

    @IgniteInstanceResource
    Ignite ignite;

    @Override
    public Tweet insert(Tweet tweet) {
        IgniteCache<Tweet, String> cache = ignite.getOrCreateCache("tweetsCache");

        tweet.setId(UUID.randomUUID().toString());
        cache.put(tweet, tweet.getId());

        return tweet;
    }

    @Override
    public Comment insertComment(String tweetId, Comment comment) {
        return null;
    }

    @Override
    public Tweet updateTweet(Tweet tweet) {
        return null;
    }

    @Override
    public Tweet findTweetById(String tweetId) {
        return null;
    }

    @Override
    public List<TweetTO> findAllByOrderByDateDesc(PageParams pageParams) {
        return null;
    }

    @Override
    public List<Comment> findCommentsByTweet(String tweetId, PageParams pageParams) {
        return null;
    }

    @Override
    public Boolean isTweetLiked(String tweetId, String username) {
        return null;
    }

    @Override
    public Boolean isTweetDisliked(String tweetId, String username) {
        return null;
    }
}
