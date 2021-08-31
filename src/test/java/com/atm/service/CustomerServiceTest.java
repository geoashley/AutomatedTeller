package com.atm.service;

import com.atm.config.AppConfig;
import com.atm.entity.Account;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})

public class CustomerServiceTest extends TestCase {
    @Autowired
    CustomerService customerService;

    @Test
    public void testFindByCustomerNumber(){
        var customer = customerService.getByCustomerNumber(1234567);
        assertNotNull(customer);
        assertEquals(1234567, customer.getCustomerNumber());
        assertEquals(3566, customer.getPin());
    }

    @Test
    public void testAccounts(){
        var customer = customerService.getByCustomerNumber(1234567);
        assertNotNull(customer);
        assertEquals(1234567, customer.getCustomerNumber());
        Account checking = customerService.getCheckingAccount(customer);
        assertEquals("C12345671",checking.getAccountNumber());

        Account saving = customerService.getSavingAccount(customer);
        assertEquals("S23456781", saving.getAccountNumber());
    }

    @Test
    public void testOneAccount(){
        var customer = customerService.getByCustomerNumber(3456789);
        assertNotNull(customer);
        assertEquals(3456789, customer.getCustomerNumber());
        Account checking = customerService.getCheckingAccount(customer);
        assertNull(checking);

        Account saving = customerService.getSavingAccount(customer);
        assertEquals("S23456783", saving.getAccountNumber());
    }

}