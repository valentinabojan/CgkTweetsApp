package com.tweets.service;

import com.tweets.repository.SessionRepository;
import com.tweets.repository.UserRepository;
import com.tweets.service.entity.Session;
import com.tweets.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import java.security.SecureRandom;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    SessionRepository sessionRepository;

    public String loginUser(User user) {
        User userFromDB = repository.findByUsername(user.getUsername());

        if (!user.getPassword().equals(userFromDB.getPassword()))
            return null;

        SecureRandom generator = new SecureRandom();
        byte randomBytes[] = new byte[32];
        generator.nextBytes(randomBytes);

        BASE64Encoder encoder = new BASE64Encoder();

        String sessionID = encoder.encode(randomBytes);
        sessionRepository.insert(new Session(sessionID, user.getUsername()));

        return sessionID;
    }

    public void logoutUser(User user) {
        Session session = sessionRepository.findByUsername(user.getUsername());
        sessionRepository.delete(session.getSessionID());
    }
}