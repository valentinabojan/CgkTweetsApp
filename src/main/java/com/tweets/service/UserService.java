package com.tweets.service;

import com.tweets.repository.UserRepository;
import com.tweets.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public User loginUser(User user) {
        User userFromDB = repository.findByUsername(user.getUsername());

        if (!user.getPassword().equals(userFromDB.getPassword()))
            return null;

        return user;
    }
}