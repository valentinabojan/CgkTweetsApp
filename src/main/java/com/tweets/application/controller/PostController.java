package com.tweets.application.controller;

import com.tweets.service.CustomerService;
import com.tweets.service.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/tweets")
public class PostController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(method = GET)
    public List<Customer> greeting(@CookieValue(name = "username", defaultValue = "") String username, HttpServletResponse response) {
        if (username.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        } else
            return customerService.findAllCustomers();
    }
}