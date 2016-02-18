package com.tweets.service.entity.cassandra;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "comment")
public class CommentCassandra {

    private String id;

    private String body;

    private String author;

    @PrimaryKey
    private CommentKey pk;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public CommentKey getPk() {
        return pk;
    }

    public void setPk(CommentKey pk) {
        this.pk = pk;
    }
}
