package com.tweets.service.entity.cassandra;

import com.tweets.service.entity.Tweet;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Table(value = "tweet")
public class TweetCassandra {

    private String id;

    private String title;

    private String body;

    private String author;

//    private List<String> comments;

    @Column(value = "users_who_liked")
    private List<String> usersWhoLiked;

    @Column(value = "users_who_disliked")
    private List<String> usersWhoDisliked;

    @PrimaryKey
    private TweetKey pk;

    public TweetCassandra() {
        this.pk.setTempKey(1);
    }

    public TweetCassandra(Tweet tweet) {
        this.setPk(new TweetKey());
        this.pk.setTempKey(1);
        this.id = tweet.getId();
        this.title = tweet.getTitle();
        this.body = tweet.getBody();
        this.author = tweet.getAuthor();
//        this.comments = tweet.getComments();
        this.usersWhoDisliked = tweet.getUsersWhoDisliked();
        this.usersWhoLiked = tweet.getUsersWhoLiked();
        this.pk.setDate(Date.from(tweet.getDate().atZone(ZoneId.systemDefault()).toInstant()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

//    public List<String> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<String> comments) {
//        this.comments = comments;
//    }

    public List<String> getUsersWhoLiked() {
        return usersWhoLiked;
    }

    public void setUsersWhoLiked(List<String> usersWhoLiked) {
        this.usersWhoLiked = usersWhoLiked;
    }

    public List<String> getUsersWhoDisliked() {
        return usersWhoDisliked;
    }

    public void setUsersWhoDisliked(List<String> usersWhoDisliked) {
        this.usersWhoDisliked = usersWhoDisliked;
    }

    public TweetKey getPk() {
        return pk;
    }

    public void setPk(TweetKey pk) {
        this.pk = pk;
    }

    public static class TweetCassandraBuilder
    {
        private TweetCassandra tweetCassandra;

        private TweetCassandraBuilder()
        {
            tweetCassandra = new TweetCassandra();
        }

        public TweetCassandraBuilder withId(String id)
        {
            tweetCassandra.id = id;
            return this;
        }

        public TweetCassandraBuilder withTitle(String title)
        {
            tweetCassandra.title = title;
            return this;
        }

        public TweetCassandraBuilder withBody(String body)
        {
            tweetCassandra.body = body;
            return this;
        }

        public TweetCassandraBuilder withAuthor(String author)
        {
            tweetCassandra.author = author;
            return this;
        }

//        public TweetCassandraBuilder withComments(List<String> comments)
//        {
//            tweetCassandra.comments = comments;
//            return this;
//        }

        public TweetCassandraBuilder withUsersWhoLiked(List<String> usersWhoLiked)
        {
            tweetCassandra.usersWhoLiked = usersWhoLiked;
            return this;
        }

        public TweetCassandraBuilder withUsersWhoDisliked(List<String> usersWhoDisliked)
        {
            tweetCassandra.usersWhoDisliked = usersWhoDisliked;
            return this;
        }

        public static TweetCassandraBuilder tweetCassandra()
        {
            return new TweetCassandraBuilder();
        }

        public TweetCassandra build()
        {
            return tweetCassandra;
        }
    }
}
