package com.github.szberes.netbank.backend.controllers;

import java.security.Principal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.szberes.netbank.backend.account.management.AccountHeader;
import com.github.szberes.netbank.backend.account.management.AccountTransactionManager;
import com.github.szberes.netbank.backend.account.management.jpa.AccountEntity;
import com.github.szberes.netbank.backend.account.management.jpa.AccountRepository;
import com.github.szberes.netbank.backend.account.management.jpa.TransactionEntity;

@RestController
@RequestMapping("/backend/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTransactionManager accountTransactionManager;

    @Autowired
    private TransactionEntityToRestTransactionConverter transferEntityRestTransactionConverter;

    @RequestMapping(method = RequestMethod.GET)
    public List<AccountHeader> listMyAccounts(Principal principal) {
        return accountRepository.findAccountByOwnerId(principal.getName()).stream()
                .map(AccountHeader::fromEntity).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{accountId}/transactions")
    public Long newTransaction(@RequestBody @Valid RestTransactionRequest restTransactionRequest, Principal principal) {

        return accountTransactionManager.createNewTransaction(principal.getName(), restTransactionRequest.getSourceAccountId(),
                restTransactionRequest.getDestinationAccountId(), restTransactionRequest.getAmount());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{accountId}/transactions")
    public List<RestTransaction> getHistory(@PathVariable Long accountId, Principal principal) {
        List<TransactionEntity> transactions = accountTransactionManager.getAllTransactions(principal.getName(), accountId);
        return transactions.stream().map(transferEntityRestTransactionConverter::convert).collect(Collectors.toList());
    }

    @PostConstruct
    public void initData() {
        Currency eur = Currency.getInstance("EUR");
        Currency huf = Currency.getInstance("HUF");

        String adminId = "admin";
        addAccountIfNotExists(adminId, "My first account", 1000L, eur);
        addAccountIfNotExists(adminId, "My second account", 5000L, huf);
        addAccountIfNotExists(adminId, "My third account", eur);
        addAccountIfNotExists(adminId, "My fourth account", huf);

        addAccountIfNotExists("user", "First", 500L, eur);
        addAccountIfNotExists("user", "Second", eur);
    }

    private void addAccountIfNotExists(String ownerId, String accountName, Currency currency) {
        addAccountIfNotExists(ownerId, accountName, 0L, currency);
    }

    private void addAccountIfNotExists(String ownerId, String accountName, long balance, Currency eur) {
        AccountEntity account = accountRepository.findAccountByOwnerIdAndAccountName(ownerId, accountName);
        if (account != null) {
            return;
        }
        AccountEntity eurAccountOfAdmin = new AccountEntity(ownerId, accountName, eur);
        eurAccountOfAdmin.setBalance(balance);
        accountRepository.save(eurAccountOfAdmin);
    }
}
