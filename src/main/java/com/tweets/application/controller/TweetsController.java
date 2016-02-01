package com.tweets.application.controller;

import com.tweets.application.transferobject.StringTO;
import com.tweets.service.TweetsService;
import com.tweets.service.entity.Tweet;
import com.tweets.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/tweets")
public class TweetsController {

    @Autowired
    TweetsService tweetsService;

    @RequestMapping(method = POST)
    public ResponseEntity<Tweet> postNewTweet(@RequestBody Tweet tweet, @CookieValue(name = "username", defaultValue = "") String username) {
        if (username.isEmpty())
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        try {
            Tweet newTweet = tweetsService.createNewTweet(tweet, username);

            return new ResponseEntity(newTweet, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity(new StringTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}