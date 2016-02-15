package com.tweets.service.entity.mongo;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document(collection = "tweet")
public class CommentMongo {

    @Id
    @Field(value = "_id")
    private String id;

    private String author;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    @NotNull
    @NotEmpty(message = "Body is missing.")
    private String body;

    public CommentMongo() {
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

    public static class CommentBuilder
    {
        private CommentMongo comment;

        private CommentBuilder()
        {
            comment = new CommentMongo();
        }

        public CommentBuilder withId(String id)
        {
            comment.id = id;
            return this;
        }

        public CommentBuilder withAuthor(String author)
        {
            comment.author = author;
            return this;
        }

        public CommentBuilder withDate(LocalDateTime date)
        {
            comment.date = date;
            return this;
        }

        public CommentBuilder withBody(String body)
        {
            comment.body = body;
            return this;
        }

        public static CommentBuilder comment()
        {
            return new CommentBuilder();
        }

        public CommentMongo build()
        {
            return comment;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentMongo comment = (CommentMongo) o;

        if (author != null ? !author.equals(comment.author) : comment.author != null) return false;
        if (body != null ? !body.equals(comment.body) : comment.body != null) return false;
        if (date != null ? !date.equals(comment.date) : comment.date != null) return false;
        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }
}
