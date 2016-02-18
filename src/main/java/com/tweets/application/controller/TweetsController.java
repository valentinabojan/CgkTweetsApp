package com.tweets.application.controller;

import com.tweets.application.transferobject.StringTO;
import com.tweets.application.transferobject.TweetTO;
import com.tweets.service.TweetsService;
import com.tweets.service.model.Comment;
import com.tweets.service.model.Tweet;
import com.tweets.service.entity.mongo.CommentMongo;
import com.tweets.service.exception.ValidationException;
import com.tweets.service.valueobject.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/tweets")
public class TweetsController {

    @Autowired
    TweetsService tweetsService;

    @RequestMapping(method = POST)
    public ResponseEntity<TweetTO> postNewTweet(@RequestBody Tweet tweet) {
        try {
            tweetsService.createNewTweet(tweet);

            return new ResponseEntity(HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity(new StringTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/{tweetId}/comments", method = POST)
    public ResponseEntity<Comment> postNewComment(@PathVariable("tweetId") String tweetId,
                                                  @RequestBody Comment comment) {
        try {
            tweetsService.createNewComment(tweetId, comment);

            return new ResponseEntity(HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity(new StringTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = GET)
    public ResponseEntity getTweets(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<TweetTO> listOfTweets = tweetsService.findTweets(new PageParams(page, size));
        if (listOfTweets.isEmpty())
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(listOfTweets, HttpStatus.OK);
    }

    @RequestMapping(path = "/{tweetId}/comments", method = GET)
    public ResponseEntity getTweetComments(@PathVariable("tweetId") String tweetId,
                                           @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<Comment> listOfComments = tweetsService.findTweetComments(tweetId, new PageParams(page, size));
        if (listOfComments.isEmpty())
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(listOfComments, HttpStatus.OK);
    }

    @RequestMapping(path = "/{tweetId}/like", method = PUT)
    public ResponseEntity likeTweet(@PathVariable("tweetId") String tweetId) {
        TweetTO likedTweet = tweetsService.likeTweet(tweetId);
        if (likedTweet == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(likedTweet, HttpStatus.OK);
    }

    @RequestMapping(path = "/{tweetId}/dislike", method = PUT)
    public ResponseEntity dislikeTweet(@PathVariable("tweetId") String tweetId) {
        TweetTO dislikedTweet = tweetsService.dislikeTweet(tweetId);
        if (dislikedTweet == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(dislikedTweet, HttpStatus.OK);
    }
}