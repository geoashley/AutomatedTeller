package com.atm.repositories;

import com.atm.entity.Account;
import com.atm.entity.Customer;
import com.atm.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface AccountRepository<P> extends CrudRepository<Account, Long> {

    Account findByAccountNumber(String accountNumber);
}
