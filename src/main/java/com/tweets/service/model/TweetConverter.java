package com.tweets.service.model;

import com.tweets.service.entity.cassandra.CommentCassandra;
import com.tweets.service.entity.cassandra.CommentKey;
import com.tweets.service.entity.cassandra.TweetCassandra;
import com.tweets.service.entity.cassandra.TweetKey;
import com.tweets.service.entity.mongo.CommentMongo;
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
        tweetModel.setUsersWhoDisliked(tweetCassandra.getUsersWhoDisliked().stream().collect(Collectors.toList()));
        tweetModel.setUsersWhoLiked(tweetCassandra.getUsersWhoLiked().stream().collect(Collectors.toList()));
        tweetModel.setDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(tweetCassandra.getPk().getDate().getTime()), ZoneId.systemDefault()));

        return tweetModel;
    }

    public static Tweet fromTweetMongoToTweetModel(TweetMongo tweetMongo) {
        Tweet tweetModel = new Tweet();

        tweetModel.setId(tweetMongo.getId());
        tweetModel.setTitle(tweetMongo.getTitle());
        tweetModel.setBody(tweetMongo.getBody());
        tweetModel.setAuthor(tweetMongo.getAuthor());
        tweetModel.setComments(tweetMongo.getComments().stream().map(TweetConverter::fromCommentMongoToCommentModel).collect(Collectors.toList()));
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
        tweetMongo.setComments(tweetModel.getComments().stream().map(TweetConverter::fromCommentModelToCommentMongo).collect(Collectors.toList()));
        tweetMongo.setUsersWhoDisliked(tweetModel.getUsersWhoDisliked());
        tweetMongo.setUsersWhoLiked(tweetModel.getUsersWhoLiked());
        tweetMongo.setDate(tweetModel.getDate());

        return tweetMongo;
    }

    public static CommentMongo fromCommentModelToCommentMongo(Comment commentModel) {
        CommentMongo commentMongo = new CommentMongo();

        commentMongo.setId(commentModel.getId());
        commentMongo.setBody(commentModel.getBody());
        commentMongo.setAuthor(commentModel.getAuthor());
        commentMongo.setDate(commentModel.getDate());

        return commentMongo;
    }

    public static Comment fromCommentMongoToCommentModel(CommentMongo commentMongo) {
        Comment commentModel = new Comment();

        commentModel.setId(commentMongo.getId());
        commentModel.setBody(commentMongo.getBody());
        commentModel.setAuthor(commentMongo.getAuthor());
        commentModel.setDate(commentMongo.getDate());

        return commentModel;
    }

    public static CommentCassandra fromCommentModelToCommentCassandra(Comment commentModel) {
        CommentCassandra commentCassandra = new CommentCassandra();

        commentCassandra.setPk(new CommentKey());
        commentCassandra.getPk().setDate(Date.from(commentModel.getDate().atZone(ZoneId.systemDefault()).toInstant()));
        commentCassandra.setId(commentModel.getId());
        commentCassandra.setBody(commentModel.getBody());
        commentCassandra.setAuthor(commentModel.getAuthor());

        return commentCassandra;
    }

    public static Comment fromCommentCassandraToCommentModel(CommentCassandra commentCassandra) {
        Comment commentModel = new Comment();

        commentModel.setId(commentCassandra.getId());
        commentModel.setBody(commentCassandra.getBody());
        commentModel.setAuthor(commentCassandra.getAuthor());
        commentModel.setDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(commentCassandra.getPk().getDate().getTime()), ZoneId.systemDefault()));

        return commentModel;
    }

}
