package com.atm.service;

import com.atm.config.AppConfig;
import com.atm.entity.Account;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AccountServiceTest extends TestCase {

    @Autowired
    AccountService accountService;

    @Test
    public void testWithDraw(){
        Account account = accountService.getByAccountNumber("C12345671");
        accountService.addTransaction(account, -100.0);
        assertEquals(29900.0, account.getBalance());
    }

    @Test
    public void testDeposit(){
        Account account = accountService.getByAccountNumber("S23456781");
        accountService.addTransaction(account, 100.0);
        assertEquals(40100.0, account.getBalance());
    }

}