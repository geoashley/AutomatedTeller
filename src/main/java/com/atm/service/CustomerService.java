package com.atm.service;

import com.atm.entity.Account;
import com.atm.entity.Customer;
import com.atm.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository<Customer> customerRepository;

    @Transactional
    public Customer getByCustomerNumber(Integer customerNumber) {
        return customerRepository.findByCustomerNumber(customerNumber);
    }

    @Transactional
    public Account getCheckingAccount(Customer customer) {
        var maybe = customerRepository.findById(customer.getId());
        if(maybe.isPresent()){
            customer = maybe.get();
            return customer.getCheckingAccount();
        }
        return null;
    }
    @Transactional
    public Account getSavingAccount(Customer customer) {
        var maybe = customerRepository.findById(customer.getId());
        if(maybe.isPresent()){
            customer = maybe.get();
            return customer.getSavingAccount();
        }
        return null;
    }
}
