package com.tweets.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("com.tweets")
@EnableMongoRepositories("com.tweets.repository")
//@EnableCassandraRepositories("com.tweets.repository.cassandra")
public class AppConfig {

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);


//        // Start Ignite in client mode.
//        Ignite ignite = Ignition.start();
//
//        IgniteCache<String, TweetIgnite> cache = ignite.getOrCreateCache("tweetsCache");
//
//        TweetIgnite ti = new TweetIgnite();
//        ti.setTitle("abcd dcba");
//        ti.setAuthor("someone");
//        ti.setId("1");
//        cache.put(ti.getId(), ti);
//
//        System.out.println(cache.get("1").getTitle());
    }
}