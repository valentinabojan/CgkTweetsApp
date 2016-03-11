package com.tweets.repository;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.service.entity.ignite.CommentIgnite;
import com.tweets.service.entity.ignite.TweetIgnite;
import com.tweets.service.model.Comment;
import com.tweets.service.model.CommentConverter;
import com.tweets.service.model.Tweet;
import com.tweets.service.model.TweetConverter;
import com.tweets.service.valueobject.PageParams;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.cache.Cache;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Profile(value = "ignite")
@Repository
public class TweetsRepositoryIgnite implements TweetsRepository {

    private Ignite ignite;
    private IgniteCache<String, TweetIgnite> tweetsCache;
    private IgniteCache<String, CommentIgnite> commentsCache;

    public TweetsRepositoryIgnite() {
        ignite = Ignition.start();
        CacheConfiguration tweetCFG = new CacheConfiguration("tweetsCache");
        tweetCFG.setIndexedTypes(String.class, TweetIgnite.class);
        tweetsCache = ignite.getOrCreateCache(tweetCFG);

        CacheConfiguration commentCFG = new CacheConfiguration("commentsCache");
        commentCFG.setIndexedTypes(String.class, CommentIgnite.class);
        commentsCache = ignite.getOrCreateCache(commentCFG);
    }

    @Override
    public Tweet insert(Tweet tweet) {
        tweet.setId(UUID.randomUUID().toString());

        tweetsCache.put(tweet.getId(), TweetConverter.fromTweetModelToTweetIgnite(tweet));

        return tweet;
    }

    @Override
    public Comment insertComment(String tweetId, Comment comment) {
        comment.setId(UUID.randomUUID().toString());

        CommentIgnite commentIgnite = CommentConverter.fromCommentModelToCommentIgnite(comment);
        commentIgnite.setTweetId(tweetId);

        commentsCache.put(commentIgnite.getId(), commentIgnite);

        return comment;
    }

    @Override
    public Tweet updateTweet(Tweet tweet) {
        tweetsCache.put(tweet.getId(), TweetConverter.fromTweetModelToTweetIgnite(tweet));

        return tweet;
    }

    @Override
    public Tweet findTweetById(String tweetId) {
        TweetIgnite tweetIgnite = tweetsCache.get(tweetId);

        return TweetConverter.fromTweetIgniteToTweetModel(tweetIgnite);
    }

    @Override
    public List<TweetTO> findAllByOrderByDateDesc(PageParams pageParams) {
        SqlQuery sql = new SqlQuery(TweetIgnite.class, "order by date desc limit ? offset ?");

        List<TweetTO> tweets = new ArrayList<>();
        QueryCursor<Cache.Entry<String, TweetIgnite>> cursor = tweetsCache.query(sql.setArgs(pageParams.getSize(), pageParams.getPage()));

        for (Cache.Entry<String, TweetIgnite> e : cursor) {
            TweetTO tweetTO = new TweetTO(TweetConverter.fromTweetIgniteToTweetModel(e.getValue()));
            tweetTO.setCommentsCount(countCommentsByTweet(tweetTO.getId()));

            tweets.add(tweetTO);
        }

        return tweets;
    }

    public Integer countCommentsByTweet(String tweetId) {
        SqlFieldsQuery sql = new SqlFieldsQuery("select count(*) from CommentIgnite where CommentIgnite.tweetId = ?");

        QueryCursor<List<?>> cursor = commentsCache.query(sql.setArgs(tweetId));
        for (List<?> row : cursor)
            return Integer.parseInt(row.get(0).toString());

        return 0;
    }

    @Override
    public List<Comment> findCommentsByTweet(String tweetId, PageParams pageParams) {
        //SqlFieldsQuery sql = new SqlFieldsQuery("select count(*) from CommentIgnite where CommentIgnite.tweetId = ?");

        //QueryCursor<List<?>> cursor = commentsCache.query(sql.setArgs(tweetId));

        //CommentIgnite commentIgnite = commentsCache.get(tweetId);
        CommentIgnite commentIgnite = new CommentIgnite();
        SqlFieldsQuery sql = new SqlFieldsQuery(
                "select CommentIgnite.author, CommentIgnite.date, CommentIgnite.body from CommentIgnite where CommentIgnite.tweetId = ?" +
                        "order by date desc limit ? offset ?");
        // "select CommentIgnite.author, CommentIgnite.date, CommentIgnite.body from CommentIgnite where CommentIgnite.tweetId = ?")
        QueryCursor<List<?>> cursor = commentsCache.query(sql.setArgs(tweetId,pageParams.getSize(), pageParams.getPage()));
        List<Comment> comments = new ArrayList<>();
        for (List<?> e : cursor) {
            commentIgnite.setAuthor(e.get(0).toString());
            commentIgnite.setDate(LocalDateTime.parse(e.get(1).toString()));
            commentIgnite.setBody(e.get(2).toString());
            comments.add(CommentConverter.fromCommentIgniteToCommentModel(commentIgnite));
        }

        return comments;
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
