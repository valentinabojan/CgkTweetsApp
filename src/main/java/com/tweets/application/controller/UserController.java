package com.tweets.application.controller;

import com.tweets.service.TweetsService;
import com.tweets.service.UserService;
import com.tweets.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TweetsService customerService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Boolean> login(@RequestBody User user, HttpServletResponse response) {
        String username = userService.loginUser(user);

        if (username == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Cookie cookie = new Cookie("username", username.toString());
        response.addCookie(cookie);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}