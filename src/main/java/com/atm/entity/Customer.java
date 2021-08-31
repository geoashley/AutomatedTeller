package com.atm.entity;

import com.atm.datatype.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "customer_number")
    private int customerNumber;

    @Column(name = "pin")
    private int pin;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    public Account getCheckingAccount() {
        var maybe = accounts.stream().filter(account -> account.getType() == AccountType.CHECKING).findFirst();
        return maybe.isPresent() ? maybe.get() : null;
    }

    public Account getSavingAccount() {
        var maybe = accounts.stream().filter(account -> account.getType() == AccountType.SAVING).findFirst();
        return maybe.isPresent() ? maybe.get() : null;
    }

}
