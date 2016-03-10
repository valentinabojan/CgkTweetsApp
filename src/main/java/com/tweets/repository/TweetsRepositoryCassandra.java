package com.tweets.repository;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.tweets.application.transferobject.TweetTO;
import com.tweets.repository.TweetsRepository;
import com.tweets.service.entity.cassandra.CommentCassandra;
import com.tweets.service.model.Comment;
import com.tweets.service.model.CommentConverter;
import com.tweets.service.model.Tweet;
import com.tweets.service.entity.cassandra.TweetCassandra;
import com.tweets.service.entity.mongo.CommentMongo;
import com.tweets.service.model.TweetConverter;
import com.tweets.service.valueobject.PageParams;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Profile(value = "cassandra")
@Repository
public class TweetsRepositoryCassandra implements TweetsRepository {

    @Autowired
    private CassandraOperations cassandraOperations;

    public Tweet insert(Tweet tweet) {
        tweet.setId(new ObjectId().toString());
        cassandraOperations.insert(TweetConverter.fromTweetModelToTweetCassandra(tweet));

        return tweet;
    }

    @Override
    public Comment insertComment(String tweetId, Comment comment) {
        CommentCassandra commentCassandra = CommentConverter.fromCommentModelToCommentCassandra(comment);
        commentCassandra.getPk().setTweetId(tweetId);
        commentCassandra.setId(new ObjectId().toString());

        cassandraOperations.insert(commentCassandra);

        return CommentConverter.fromCommentCassandraToCommentModel(commentCassandra);
    }

    @Override
    public Tweet updateTweet(Tweet tweet) {
        TweetCassandra tweetCassandra = cassandraOperations.update(TweetConverter.fromTweetModelToTweetCassandra(tweet));

        return TweetConverter.fromTweetCassandraToTweetModel(tweetCassandra);
    }

    @Override
    public Tweet findTweetById(String tweetId) {
        Select select = QueryBuilder.select().all().from("tweets", "tweet");
        select.where(QueryBuilder.eq("id", tweetId));
        select.limit(1);

        List<Tweet> tweets = cassandraOperations.query(select, (row, rowNum) -> {
            return Tweet.TweetBuilder.tweet()
                    .withId(row.getString("id"))
                    .withTitle(row.getString("title"))
                    .withBody(row.getString("body"))
                    .withAuthor(row.getString("author"))
                    .withDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(row.getDate("date").getTime()), ZoneId.systemDefault()))
                    .withUsersWhoLiked(row.getSet("users_who_liked", String.class))
                    .withUsersWhoDisliked(row.getSet("users_who_disliked", String.class))
                    .build();
        });

        Tweet tweet = tweets.get(0);
        tweet.setUsersWhoLiked(tweet.getUsersWhoLiked().stream().collect(Collectors.toSet()));
        tweet.setUsersWhoDisliked(tweet.getUsersWhoDisliked().stream().collect(Collectors.toSet()));

        return tweet;
    }

    @Override
    public List<TweetTO> findAllByOrderByDateDesc(PageParams pageParams) {
        Integer maximumEndIndexForTweetsInterval = pageParams.getSize() * (pageParams.getPage() + 1);
        Integer maximumStartIndexForTweetsInterval = pageParams.getPage() * pageParams.getSize();

        Select select = QueryBuilder.select().all().from("tweets", "tweet");
        select.where(QueryBuilder.eq("temp_key", 1));
        select.orderBy(QueryBuilder.desc("date"));
        select.limit(maximumEndIndexForTweetsInterval);

        List<TweetTO> tweets = cassandraOperations.query(select, (row, rowNum) -> {
            return TweetTO.TweetTOBuilder.tweetTO()
                    .withId(row.getString("id"))
                    .withTitle(row.getString("title"))
                    .withBody(row.getString("body"))
                    .withAuthor(row.getString("author"))
                    .withDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(row.getDate("date").getTime()), ZoneId.systemDefault()))
                    .withUsersWhoLikedCount(row.getSet("users_who_liked", String.class).size())
                    .withUsersWhoDislikedCount(row.getSet("users_who_disliked", String.class).size())
                    .withCommentsCount(countCommentsByTweet(row.getString("id")))
                    .build();
        });

        return tweets.subList(Math.min(tweets.size(), maximumStartIndexForTweetsInterval),
                              Math.min(tweets.size(), maximumEndIndexForTweetsInterval));
    }

    public Integer countCommentsByTweet(String tweetId) {
        Select select = QueryBuilder.select().countAll().from("tweets", "comment");
        select.where(QueryBuilder.eq("post_id", tweetId));

        return cassandraOperations.queryForObject(select, Long.class).intValue();
    }

    @Override
    public List<Comment> findCommentsByTweet(String tweetId, PageParams pageParams) {
        Integer maximumEndIndexForCommentsInterval = pageParams.getSize() * (pageParams.getPage() + 1);
        Integer maximumStartIndexForCommentsInterval = pageParams.getPage() * pageParams.getSize();

        Select select = QueryBuilder.select().all().from("tweets", "comment");
        select.where(QueryBuilder.eq("post_id", tweetId));
        select.orderBy(QueryBuilder.desc("date"));
        select.limit(maximumEndIndexForCommentsInterval);

        List<Comment> comments = cassandraOperations.query(select, (row, rowNum) -> {
            return Comment.CommentBuilder.comment()
                    .withId(row.getString("id"))
                    .withBody(row.getString("body"))
                    .withAuthor(row.getString("author"))
                    .withDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(row.getDate("date").getTime()), ZoneId.systemDefault()))
                    .build();
        });

        return comments.subList(Math.min(comments.size(), maximumStartIndexForCommentsInterval),
                                Math.min(comments.size(), maximumEndIndexForCommentsInterval));
    }

    @Override
    public Boolean isTweetLiked(String tweetId, String username) {
        Tweet tweet = findTweetById(tweetId);

        return tweet.getUsersWhoLiked().contains(username);
    }

    @Override
    public Boolean isTweetDisliked(String tweetId, String username) {
        Tweet tweet = findTweetById(tweetId);

        return tweet.getUsersWhoDisliked().contains(username);
    }
}
