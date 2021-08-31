package com.atm.repositories;

import com.atm.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository<P> extends CrudRepository<Customer, Long> {
    Customer findByCustomerNumber(Integer accountNo);


}

