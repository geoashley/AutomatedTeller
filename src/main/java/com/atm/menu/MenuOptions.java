package com.atm.menu;

import com.atm.entity.Account;
import com.atm.entity.Customer;
import com.atm.service.AccountService;
import com.atm.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.Console;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

@Component
public class MenuOptions {

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountService accountService;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    Scanner command= new Scanner(System.in);
    Console console = System.console();
    DecimalFormat moneyFormat=new DecimalFormat("'$'###,##0.00");

    private boolean gotConsole(){
        return console !=null? true : false;

    }

    public void screenFlush(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void displayMenu() {
        if(!gotConsole()) return;
        screenFlush();
        console.writer().println(ANSI_GREEN+ "-----------------------------------------------------");
        console.writer().println(ANSI_GREEN+ "                         Welcome                     ");
        console.writer().println(ANSI_GREEN+ "-----------------------------------------------------");
        Customer customer = null;

        while (customer == null)
            customer = promptLogin();
        boolean stayLoggedIn = true;
        while (stayLoggedIn)
            stayLoggedIn = manageMainMenu(customer);

    }
    public boolean manageMainMenu(Customer customer){
        Account account = mainMenuOptions(customer);

        if(account != null){
            int option = accountMenuOptions();
            switch (option){
                case 1:
                    withDrawMenu(account);
                    return true;
                case 2:
                    depositMenu(account);
                    return true;
                case 3:
                    displayBalance(account);
                    return true;
                case 9:
                    return false;
            }
        }
        return  false;
    }
    public Customer promptLogin(){

        Customer customer = null;
        boolean running = true;
        while(running){
            String input  = console.readLine(ANSI_GREEN+"Enter your customer Number : ");
            if(StringUtils.isNumeric(input)){
                Integer customerNumber = Integer.valueOf(input);
                customer = customerService.getByCustomerNumber(customerNumber);
                running = false;
            }else{
                console.writer().println(ANSI_RED+"Customer Number must be numeric");
            }
        }
        running = true;
        while(running){
            char[] password = console.readPassword(ANSI_GREEN+ "Enter your PIN : ");
            String pin = new String(password);
            if(StringUtils.isNumeric(pin)){
                Integer pinNumber = Integer.valueOf(pin);
                if(customer == null || customer.getPin() !=pinNumber){
                    console.writer().println(ANSI_RED+"Invalid Customer Number or Pin");
                    return null;
                }
                if(customer != null && customer.getPin() ==pinNumber) {
                    return customer;
                }
                running = false;
            }else{
                console.writer().println(ANSI_RED+"PIN Number must be numeric");
            }
        }
        return null;
    }
    public Account mainMenuOptions(Customer customer) {
        if (!gotConsole()) return null;
        int option = -1;
        screenFlush();
        console.writer().println(ANSI_GREEN+"-----------------------------------------------------");
        console.writer().println(ANSI_GREEN+"                         Main Menu                   ");
        console.writer().println(ANSI_GREEN+"-----------------------------------------------------");
        console.writer().println("Select the Account you Want to Access: ");
        console.writer().println(" 1 - Checking Account");
        console.writer().println(" 2 - Saving Account");
        console.writer().println(" 9 - Logout");
        boolean running = true;
        while(running){
            String input  = console.readLine(ANSI_GREEN+"Enter option : ");
            if(StringUtils.isNumeric(input)){
                option = Integer.valueOf(input);
                switch(option){
                    case 1:
                        Account checking = customerService.getCheckingAccount(customer);
                        if(checking==null){
                            System.out.println(ANSI_RED+"Account Not Found!");
                        }else{
                            return checking;
                        }
                        break;
                    case 2:
                        Account saving = customerService.getSavingAccount(customer);
                        if(saving==null){
                            System.out.println(ANSI_RED+"Account Not Found!");
                        }else{
                            return saving;
                        }
                        break;
                    case 9:
                        running = false;
                        break;
                    default:
                        System.out.println(ANSI_RED+"Command not recognized!");
                        break;
                }
            }else{
                console.writer().println(ANSI_RED+"Option must be numeric");
            }
        }
        return null;
    }

    public int accountMenuOptions() {
        if (!gotConsole()) return -1;
        int option = -1;

        screenFlush();
        console.writer().println(ANSI_GREEN+"-----------------------------------------------------");
        console.writer().println(ANSI_GREEN+"                         Account Menu                ");
        console.writer().println(ANSI_GREEN+"-----------------------------------------------------");
        console.writer().println("Select the Account you Want to Access: ");
        console.writer().println(" 1 - Withdraw Funds");
        console.writer().println(" 2 - Deposit");
        console.writer().println(" 3 - Display Account Balance");
        console.writer().println(" 9 - Logout");
        boolean running = true;
        while(running){
            String input  = console.readLine(ANSI_GREEN+"Enter option : ");
            if(StringUtils.isNumeric(input)){
                option = Integer.valueOf(input);
                switch(option){
                    case 1:
                    case 2:
                    case 3:
                    case 9:
                        running = false;
                        break;
                    default:
                        System.out.println(ANSI_RED+"Invalid selection!");
                        break;
                }
            }else {
                console.writer().println(ANSI_RED + "Option must be numeric");
            }
        }
        return option;
    }

    public void withDrawMenu(Account acc) {
        if (!gotConsole()) return;
        Double amount = null;
        screenFlush();
        console.writer().println(ANSI_GREEN + "-----------------------------------------------------");
        console.writer().println(ANSI_GREEN + "                         Withdrawal Menu             ");
        console.writer().println(ANSI_GREEN + "-----------------------------------------------------");
        console.writer().println("Current Balance - " + acc.getBalance());
        boolean running = true;
        while (running) {
            String input = console.readLine(ANSI_GREEN + "Enter Amount to withdraw : ");
            if (StringUtils.isNumeric(input)) {
                amount  = Double.valueOf(input);
                if(amount > acc.getBalance()){
                    console.writer().println(ANSI_RED + "Amount must be lesser than the balance");
                }else
                    running = false;
            } else {
                console.writer().println(ANSI_RED + "Amount must be numeric");
            }
        }
        if(amount !=null && amount>0){
            accountService.addTransaction(acc, -amount);
            console.writer().println();
            console.writer().println(ANSI_YELLOW + "Amount dispensed : " + amount);
            console.writer().println(ANSI_YELLOW + "New balance      : " + acc.getBalance());
            promptReturn();
        }

    }

    public void depositMenu(Account acc) {
        if (!gotConsole()) return;
        Double amount = null;
        screenFlush();
        console.writer().println(ANSI_GREEN + "-----------------------------------------------------");
        console.writer().println(ANSI_GREEN + "                         Deposit Menu                ");
        console.writer().println(ANSI_GREEN + "-----------------------------------------------------");
        console.writer().println("Current Balance - " + acc.getBalance());
        boolean running = true;
        while (running) {
            String input = console.readLine(ANSI_GREEN + "Enter Amount to deposit : ");
            if (StringUtils.isNumeric(input)) {
                amount  = Double.valueOf(input);
                if(amount < 0){
                    console.writer().println(ANSI_RED + "Amount must greater than 0");
                }else
                    running = false;
            } else {
                console.writer().println(ANSI_RED + "Amount must be numeric");
            }
        }
        if(amount !=null && amount>0){
            accountService.addTransaction(acc, amount);
            console.writer().println();
            console.writer().println(ANSI_YELLOW + "Amount deposited : " + amount);
            console.writer().println(ANSI_YELLOW + "New balance      : " + acc.getBalance());
            promptReturn();
        }

    }
    public void displayBalance(Account acc) {
        if (!gotConsole()) return;

        screenFlush();
        console.writer().println(ANSI_GREEN + "-----------------------------------------------------");
        console.writer().println(ANSI_GREEN + "                Account Balance                      ");
        console.writer().println(ANSI_GREEN + "-----------------------------------------------------");
        console.writer().println("Current Balance - " + acc.getBalance());
        promptReturn();
    }

    private void promptReturn() {
        try {
            console.writer().println();
            console.writer().println(ANSI_BLUE + "Enter enter to exit");
            char c = (char) System.in.read();
        } catch (IOException io) {
            return;

        }
    }

}
