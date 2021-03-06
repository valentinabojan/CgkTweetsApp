package com.tweets.repository;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.service.entity.mongo.TweetMongo;
import com.tweets.service.model.Comment;
import com.tweets.service.model.CommentConverter;
import com.tweets.service.model.Tweet;
import com.tweets.service.model.TweetConverter;
import com.tweets.service.valueobject.PageParams;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Profile("mongo")
@Repository
public class TweetsRepositoryMongo implements TweetsRepository{

    @Autowired
    private MongoOperations mongoOperations;

    public Tweet insert(Tweet tweet) {
        TweetMongo tweetMongo = TweetConverter.fromTweetModelToTweetMongo(tweet);

        mongoOperations.insert(tweetMongo);

        return TweetConverter.fromTweetMongoToTweetModel(tweetMongo);
    }

    public Comment insertComment(String tweetId, Comment comment) {
        TweetMongo t = mongoOperations.findOne(new Query(Criteria.where("id").is(tweetId)), TweetMongo.class);

        //Save – It should rename to saveOrUpdate(), it performs insert() if “_id” is NOT exist or update() if “_id” is existed”
        //Insert – Only insert, if “_id” is existed, an error is generated
        if(t != null) {
            comment.setId((new ObjectId()).toString());
            t.getComments().add(CommentConverter.fromCommentModelToCommentMongo(comment));
            mongoOperations.save(t);
        }
        return comment;
    }

    public Tweet updateTweet(Tweet tweet) {
        TweetMongo tweetMongo = TweetConverter.fromTweetModelToTweetMongo(tweet);

        mongoOperations.save(tweet);

        return TweetConverter.fromTweetMongoToTweetModel(tweetMongo);
    }

    public Tweet findTweetById(String tweetId) {
        TweetMongo tweetMongo = mongoOperations.findOne(new Query(Criteria.where("id").is(tweetId)), TweetMongo.class);

        return TweetConverter.fromTweetMongoToTweetModel(tweetMongo);
    }

    public List<TweetTO> findAllByOrderByDateDesc(PageParams pageParams){
        AggregationOperation sortByDate = sort(Sort.Direction.DESC, "date");
        AggregationOperation project = project("title", "body", "author", "date")
                                            .and("comments").size().as("commentsCount")
                                            .and("usersWhoLiked").size().as("usersWhoLikedCount")
                                            .and("usersWhoDisliked").size().as("usersWhoDislikedCount");
        AggregationOperation skip = skip(pageParams.getPage() * pageParams.getSize());
        AggregationOperation limit = limit(pageParams.getSize());

        return mongoOperations.aggregate(newAggregation(sortByDate, project, skip, limit), TweetMongo.class, TweetTO.class).getMappedResults();
    }

    public List<Comment> findCommentsByTweet(String tweetId, PageParams pageParams){
        AggregationOperation match = match(Criteria.where("_id").is(tweetId));
        AggregationOperation project = project().andInclude("comments").andExclude("_id");
        AggregationOperation unwind = unwind("comments");
        AggregationOperation sortByDate = sort(Sort.Direction.DESC, "comments.date");
        AggregationOperation skip = skip(pageParams.getPage() * pageParams.getSize());
        AggregationOperation limit = limit(pageParams.getSize());
        AggregationOperation group = group().push("comments").as("comments");

        List<TweetMongo> tweets = mongoOperations.aggregate(newAggregation(match, project, unwind, sortByDate, skip, limit, group), TweetMongo.class, TweetMongo.class).getMappedResults();

        if (tweets.isEmpty())
            return new ArrayList<>();
        else
            return TweetConverter.fromTweetMongoToTweetModel(tweets.get(0)).getComments();
    }

    public Boolean isTweetLiked(String tweetId, String username) {
        return mongoOperations.findOne(new Query(Criteria.where("_id").is(tweetId).and("usersWhoLiked").in(username)), TweetMongo.class) != null;
    }

    public Boolean isTweetDisliked(String tweetId, String username) {
        return mongoOperations.findOne(new Query(Criteria.where("_id").is(tweetId).and("usersWhoDisliked").in(username)), TweetMongo.class) != null;
    }
}