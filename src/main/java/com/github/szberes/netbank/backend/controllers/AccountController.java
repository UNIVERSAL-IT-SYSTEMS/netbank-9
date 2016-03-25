package com.github.szberes.netbank.backend.controllers;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import com.github.szberes.netbank.backend.account.management.AccountHeader;
import com.github.szberes.netbank.backend.account.management.AccountTransactionManager;
import com.github.szberes.netbank.backend.account.management.jpa.AccountEntity;
import com.github.szberes.netbank.backend.account.management.jpa.AccountRepository;
import com.github.szberes.netbank.backend.account.management.jpa.TransferEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void newTransaction(@RequestBody RestTransactionRequest restTransactionRequest, Principal principal) {

        accountTransactionManager.createNewTransaction(principal.getName(), restTransactionRequest.getSourceAccountId(),
                restTransactionRequest.getDestinationAccountId(), restTransactionRequest.getAmount());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{accountId}/transactions")
    public List<RestTransaction> getHistory(@PathVariable Long accountId, Principal principal) {
        List<TransferEntity> transactions = accountTransactionManager.getAllTransactions(principal.getName(), accountId);
        return transactions.stream().map(transferEntityRestTransactionConverter::convert).collect(Collectors.toList());
    }

    @PostConstruct
    public void initData() {
        AccountEntity admin = new AccountEntity("admin", "My first account", Currency.getInstance("EUR"));
        admin.setBalance(1000L);
        accountRepository.save(admin);
        accountRepository.save(new AccountEntity("admin", "My second account", Currency.getInstance("HUF")));
        accountRepository.save(new AccountEntity("admin", "My third account", Currency.getInstance("EUR")));
    }
}
