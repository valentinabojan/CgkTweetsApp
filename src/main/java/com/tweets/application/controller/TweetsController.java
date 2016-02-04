package com.tweets.application.controller;

import com.tweets.application.transferobject.StringTO;
import com.tweets.service.TweetsService;
import com.tweets.service.entity.Tweet;
import com.tweets.service.exception.ValidationException;
import com.tweets.service.valueobject.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/tweets")
public class TweetsController {

    @Autowired
    TweetsService tweetsService;

    @RequestMapping(method = POST)
    public ResponseEntity<Tweet> postNewTweet(@RequestBody Tweet tweet, @CookieValue(name = "username", defaultValue = "") String username) {
        try {
            Tweet newTweet = tweetsService.createNewTweet(tweet, username);

            return new ResponseEntity(newTweet, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity(new StringTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = GET)
    public ResponseEntity getTweets(@CookieValue(name = "username", defaultValue = "") String username,
                                    @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<Tweet> listOfTweets = tweetsService.findTweets(new PageParams(page, size));
        if (listOfTweets.size() == 0){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(listOfTweets, HttpStatus.OK);
    }
}