package com.tweets.service.entity.ignite;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CommentIgnite implements Serializable {

    @QuerySqlField
    private String id;

    @QuerySqlField
    private String author;

    @QuerySqlField
    private LocalDateTime date;

    @QuerySqlField
    private String body;

    public CommentIgnite() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
