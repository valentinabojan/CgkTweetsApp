package com.tweets.repository.cassandra;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.repository.TweetsRepository;
import com.tweets.service.entity.Tweet;
import com.tweets.service.entity.cassandra.TweetCassandra;
import com.tweets.service.entity.mongo.CommentMongo;
import com.tweets.service.valueobject.PageParams;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile(value = "cassandra")
@Repository
public class TweetsRepositoryCassandra implements TweetsRepository {

    @Autowired
    private CassandraOperations cassandraOperations;

    public Tweet insert(Tweet tweet) {
        tweet.setId(new ObjectId().toString());
        cassandraOperations.insert(new TweetCassandra(tweet));

        return tweet;
    }

    @Override
    public Tweet insertComment(String tweetId, CommentMongo comment) {
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
    public List<CommentMongo> findCommentsByTweet(String tweetId, PageParams pageParams) {
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
