package com.tweets.service.model;

import com.tweets.service.entity.cassandra.TweetCassandra;
import com.tweets.service.entity.cassandra.TweetKey;
import com.tweets.service.entity.ignite.TweetIgnite;
import com.tweets.service.entity.mongo.TweetMongo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

public class TweetConverter {

    public static Tweet fromTweetCassandraToTweetModel(TweetCassandra tweetCassandra) {
        Tweet tweetModel = new Tweet();

        tweetModel.setId(tweetCassandra.getId());
        tweetModel.setTitle(tweetCassandra.getTitle());
        tweetModel.setBody(tweetCassandra.getBody());
        tweetModel.setAuthor(tweetCassandra.getAuthor());
        tweetModel.setUsersWhoDisliked(tweetCassandra.getUsersWhoDisliked().stream().collect(Collectors.toSet()));
        tweetModel.setUsersWhoLiked(tweetCassandra.getUsersWhoLiked().stream().collect(Collectors.toSet()));
        tweetModel.setDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(tweetCassandra.getPk().getDate().getTime()), ZoneId.systemDefault()));

        return tweetModel;
    }

    public static Tweet fromTweetMongoToTweetModel(TweetMongo tweetMongo) {
        Tweet tweetModel = new Tweet();

        tweetModel.setId(tweetMongo.getId());
        tweetModel.setTitle(tweetMongo.getTitle());
        tweetModel.setBody(tweetMongo.getBody());
        tweetModel.setAuthor(tweetMongo.getAuthor());
        tweetModel.setComments(tweetMongo.getComments().stream().map(CommentConverter::fromCommentMongoToCommentModel).collect(Collectors.toList()));
        tweetModel.setUsersWhoDisliked(tweetMongo.getUsersWhoDisliked());
        tweetModel.setUsersWhoLiked(tweetMongo.getUsersWhoLiked());
        tweetModel.setDate(tweetMongo.getDate());

        return tweetModel;
    }

    public static TweetCassandra fromTweetModelToTweetCassandra(Tweet tweetModel) {
        TweetCassandra tweetCassandra = new TweetCassandra();

        tweetCassandra.setPk(new TweetKey());
        tweetCassandra.getPk().setTempKey(1);
        tweetCassandra.getPk().setDate(Date.from(tweetModel.getDate().atZone(ZoneId.systemDefault()).toInstant()));
        tweetCassandra.setId(tweetModel.getId());
        tweetCassandra.setTitle(tweetModel.getTitle());
        tweetCassandra.setBody(tweetModel.getBody());
        tweetCassandra.setAuthor(tweetModel.getAuthor());
        tweetCassandra.setUsersWhoDisliked(tweetModel.getUsersWhoDisliked());
        tweetCassandra.setUsersWhoLiked(tweetModel.getUsersWhoLiked());

        return tweetCassandra;
    }

    public static TweetMongo fromTweetModelToTweetMongo(Tweet tweetModel) {
        TweetMongo tweetMongo = new TweetMongo();

        tweetMongo.setId(tweetModel.getId());
        tweetMongo.setTitle(tweetModel.getTitle());
        tweetMongo.setBody(tweetModel.getBody());
        tweetMongo.setAuthor(tweetModel.getAuthor());
        tweetMongo.setComments(tweetModel.getComments().stream().map(CommentConverter::fromCommentModelToCommentMongo).collect(Collectors.toList()));
        tweetMongo.setUsersWhoDisliked(tweetModel.getUsersWhoDisliked());
        tweetMongo.setUsersWhoLiked(tweetModel.getUsersWhoLiked());
        tweetMongo.setDate(tweetModel.getDate());

        return tweetMongo;
    }

    public static TweetIgnite fromTweetModelToTweetIgnite(Tweet tweetModel) {
        TweetIgnite tweetIgnite = new TweetIgnite();

        tweetIgnite.setId(tweetModel.getId());
        tweetIgnite.setTitle(tweetModel.getTitle());
        tweetIgnite.setBody(tweetModel.getBody());
        tweetIgnite.setAuthor(tweetModel.getAuthor());
        tweetIgnite.setComments(tweetModel.getComments().stream().map(CommentConverter::fromCommentModelToCommentIgnite).collect(Collectors.toList()));
        tweetIgnite.setUsersWhoDisliked(tweetModel.getUsersWhoDisliked());
        tweetIgnite.setUsersWhoLiked(tweetModel.getUsersWhoLiked());
        tweetIgnite.setDate(tweetModel.getDate());

        return tweetIgnite;
    }

    public static Tweet fromTweetIgniteToTweetModel(TweetIgnite tweetIgnite) {
        Tweet tweetModel = new Tweet();

        tweetModel.setId(tweetIgnite.getId());
        tweetModel.setTitle(tweetIgnite.getTitle());
        tweetModel.setBody(tweetIgnite.getBody());
        tweetModel.setAuthor(tweetIgnite.getAuthor());
        tweetModel.setComments(tweetIgnite.getComments().stream().map(CommentConverter::fromCommentIgniteToCommentModel).collect(Collectors.toList()));
        tweetModel.setUsersWhoDisliked(tweetIgnite.getUsersWhoDisliked());
        tweetModel.setUsersWhoLiked(tweetIgnite.getUsersWhoLiked());
        tweetModel.setDate(tweetIgnite.getDate());

        return tweetModel;
    }
}
