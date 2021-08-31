package com.atm.repositories;

import com.atm.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository<P> extends CrudRepository<Transaction, Long> {
}
