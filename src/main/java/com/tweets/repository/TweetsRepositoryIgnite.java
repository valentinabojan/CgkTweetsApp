package com.tweets.repository;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.service.entity.ignite.TweetIgnite;
import com.tweets.service.model.Comment;
import com.tweets.service.model.Tweet;
import com.tweets.service.model.TweetConverter;
import com.tweets.service.valueobject.PageParams;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Profile(value = "ignite")
@Repository
public class TweetsRepositoryIgnite implements TweetsRepository {

    private Ignite ignite = Ignition.start();
    private IgniteCache<String, TweetIgnite> cache = ignite.getOrCreateCache("tweetsCache");

    @Override
    public Tweet insert(Tweet tweet) {
        tweet.setId(UUID.randomUUID().toString());
        
        cache.put(tweet.getId(), TweetConverter.fromTweetModelToTweetIgnite(tweet));

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
        TweetIgnite tweetIgnite = cache.get(tweetId);

        return TweetConverter.fromTweetIgniteToTweetModel(tweetIgnite);
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
