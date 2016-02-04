package com.tweets.application.controller;

import com.tweets.service.UserSecurityDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/principal")
@Component()
public class UserController {

    @Autowired
    UserSecurityDetailsService service;

    @RequestMapping(method = GET)
    public ResponseEntity<String> getPrincipalDetails() {
        String principalName = service.getPrincipalName();

        if (principalName == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return new ResponseEntity(principalName, HttpStatus.OK);
    }
}