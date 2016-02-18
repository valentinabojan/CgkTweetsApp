package com.tweets.service.model;

import com.tweets.service.entity.cassandra.CommentCassandra;
import com.tweets.service.entity.cassandra.CommentKey;
import com.tweets.service.entity.mongo.CommentMongo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CommentConverter {

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
