package com.tweets.service;

import com.tweets.repository.CustomerRepository;
import com.tweets.service.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository repository;

    public List<Customer> findAllCustomers() {
        return repository.findAll();
    }

}
