package com.tweets.application.controller;

import com.tweets.service.TweetsService;
import com.tweets.service.entity.Tweet;
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
    public ResponseEntity postNewTweet(@CookieValue(name = "username", defaultValue = "") String username, @RequestBody Tweet tweet) {
        if (username.isEmpty())
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        tweetsService.createNewTweet(tweet);

        return new ResponseEntity(HttpStatus.OK);
    }
}