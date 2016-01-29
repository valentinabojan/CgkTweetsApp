package com.tweets.repository;

import java.util.List;

import com.tweets.service.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {


    public Customer findByFirstName(String firstName);

    public List<Customer> findByLastName(String lastName);

}