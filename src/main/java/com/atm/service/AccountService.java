package com.atm.service;

import com.atm.entity.Account;
import com.atm.entity.Customer;
import com.atm.entity.Transaction;
import com.atm.repositories.AccountRepository;
import com.atm.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    TransactionRepository<Transaction> transactionRepository;

    @Autowired
    AccountRepository<Account> accountRepository;

    @Transactional
    public Account getByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
    @Transactional
    public boolean addTransaction(Account account, Double amount){

        Transaction newTransaction = new Transaction();
        newTransaction.setAccount(account);
        newTransaction.setAmount(amount);
        transactionRepository.save(newTransaction);

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        return  true;
    }


    @Transactional
    public List<Transaction> getTransactions(Account account) {
        var maybe = accountRepository.findById(account.getId());
        if(maybe.isPresent()){
            account = maybe.get();
            return account.getTransactions();
        }
        return null;
    }

}
