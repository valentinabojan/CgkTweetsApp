package com.tweets.service.entity.ignite;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

public class CommentIgniteKey {

    private String id;

    @AffinityKeyMapped
    private String tweetId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }
}
