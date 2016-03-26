package com.github.szberes.netbank.backend.controllers;

import java.security.Principal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

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
    public Long newTransaction(@RequestBody RestTransactionRequest restTransactionRequest, Principal principal) {

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

        AccountEntity eurAccountOfAdmin = new AccountEntity("admin", "My first account", eur);
        eurAccountOfAdmin.setBalance(1000L);
        accountRepository.save(eurAccountOfAdmin);
        AccountEntity hufAccountOfAdmin = new AccountEntity("admin", "My second account", huf);
        hufAccountOfAdmin.setBalance(5000L);
        accountRepository.save(hufAccountOfAdmin);
        accountRepository.save(new AccountEntity("admin", "My third account", eur));
        accountRepository.save(new AccountEntity("admin", "My fourth account", huf));

        AccountEntity eurAccountOfUser = new AccountEntity("user", "First", eur);
        eurAccountOfUser.setBalance(500L);
        accountRepository.save(eurAccountOfUser);
        accountRepository.save(new AccountEntity("user", "Second", eur));
    }
}
