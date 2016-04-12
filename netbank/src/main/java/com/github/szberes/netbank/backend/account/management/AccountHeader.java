package com.github.szberes.netbank.backend.account.management;

import java.util.Currency;

import com.github.szberes.netbank.backend.account.management.jpa.AccountEntity;

public class AccountHeader {

    private Long id;
    private String name;
    private Long balance;
    private Currency currency;

    public AccountHeader(Long id, String name, Long balance, Currency currency) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public static AccountHeader fromEntity(AccountEntity entity) {
        return new AccountHeader(entity.getId(), entity.getAccountName(), entity.getBalance(), entity.getCurrency());
    }
}
