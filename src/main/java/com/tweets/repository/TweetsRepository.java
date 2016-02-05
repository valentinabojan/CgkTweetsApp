package com.tweets.repository;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.service.entity.Comment;
import com.tweets.service.entity.Tweet;
import com.tweets.service.valueobject.PageParams;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class TweetsRepository {

    @Autowired
    private MongoOperations mongoOperations;

    public TweetTO insert(Tweet tweet) {
        mongoOperations.insert(tweet);

        return new TweetTO(tweet);
    }

    public Tweet insertComment(Tweet tweet, Comment comment) {
        Tweet t = mongoOperations.findOne(new Query(Criteria.where("id").is(tweet.getId())), Tweet.class);

        //Save – It should rename to saveOrUpdate(), it performs insert() if “_id” is NOT exist or update() if “_id” is existed”
        //Insert – Only insert, if “_id” is existed, an error is generated
        if(t != null) {
            comment.setId((new ObjectId()).toString());
            t.getComments().add(comment);
            mongoOperations.save(t);
        }
        return t;
    }
    
    

    public List<TweetTO> findAllByOrderByDateDesc(PageParams pageParams){
        AggregationOperation sortByDate = sort(Sort.Direction.DESC, "date");
        AggregationOperation project = project("title", "body", "author", "date")
                                            .and("comments").size().as("commentsCount")
                                            .and("usersWhoLiked").size().as("usersWhoLikedCount")
                                            .and("usersWhoDisliked").size().as("usersWhoDislikedCount");
        AggregationOperation skip = skip(pageParams.getPage() * pageParams.getSize());
        AggregationOperation limit = limit(pageParams.getSize());

        return mongoOperations.aggregate(newAggregation(sortByDate, project, skip, limit), Tweet.class, TweetTO.class).getMappedResults();
    }

    public List<Comment> findCommentsByTweet(String tweetId, PageParams pageParams){
        AggregationOperation match = match(Criteria.where("_id").is(tweetId));
        AggregationOperation project = project().andInclude("comments").andExclude("_id");
        AggregationOperation unwind = unwind("comments");
        AggregationOperation sortByDate = sort(Sort.Direction.DESC, "comments.date");
        AggregationOperation skip = skip(pageParams.getPage() * pageParams.getSize());
        AggregationOperation limit = limit(pageParams.getSize());
        AggregationOperation group = group().push("comments").as("comments");

        Tweet tweet = mongoOperations.aggregate(newAggregation(match, project, unwind, sortByDate, skip, limit, group), Tweet.class, Tweet.class).getMappedResults().get(0);

        return tweet.getComments();
    }

    
}