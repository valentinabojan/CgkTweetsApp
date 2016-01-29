package com.tweets.application.controller;

import com.tweets.service.CustomerService;
import com.tweets.service.UserService;
import com.tweets.service.entity.Customer;
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
    CustomerService customerService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Customer> login(@RequestBody User user, HttpServletResponse response) {
        String sessionID = userService.loginUser(user);

        if (sessionID == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Cookie cookie = new Cookie("sessionID", sessionID.toString());
        response.addCookie(cookie);

        return new ResponseEntity<>(customerService.findAllCustomers().get(0), HttpStatus.OK);
    }
}