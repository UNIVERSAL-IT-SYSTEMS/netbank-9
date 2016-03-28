package com.github.szberes.netbank.backend.account.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.github.szberes.netbank.backend.account.management.jpa.AccountEntity;
import com.github.szberes.netbank.backend.account.management.jpa.AccountRepository;
import com.github.szberes.netbank.backend.account.management.jpa.TransactionRepository;
import com.github.szberes.netbank.backend.account.management.jpa.TransactionEntity;

@Component
@Transactional
public class AccountTransactionManager {


    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Long createNewTransaction(String name, Long sourceAccountId, Long destinationAccountId, Long amount) {
        Assert.isTrue(amount != null && amount > 0, "Amount should be greater than zero!");
        Assert.isTrue(!sourceAccountId.equals(destinationAccountId), "Source and destination accounts cannot be the same");

        AccountEntity sourceAccountEntity = accountRepository.findOne(sourceAccountId);
        assertUserOwnsAccount(name, sourceAccountEntity);

        AccountEntity destinationAccountEntity = accountRepository.findOne(destinationAccountId);
        Assert.notNull(destinationAccountEntity, "The destination account does not exist");

        Assert.isTrue(sourceAccountEntity.getCurrency().equals(destinationAccountEntity.getCurrency()),
                "Currencies of the accounts should be equal!");

        if (sourceAccountEntity.getBalance() < amount) {
            throw new IllegalArgumentException("User does not have enough credit ");
        }

        return executeTransaction(amount, sourceAccountEntity, destinationAccountEntity);
    }

    private Long executeTransaction(Long amount, AccountEntity sourceAccountEntity, AccountEntity destinationAccountEntity) {
        TransactionEntity transactionEntity = new TransactionEntity(sourceAccountEntity, destinationAccountEntity, amount);
        transactionRepository.save(transactionEntity);

        sourceAccountEntity.setBalance(sourceAccountEntity.getBalance() - amount);
        destinationAccountEntity.setBalance(destinationAccountEntity.getBalance() + amount);
        accountRepository.save(Arrays.asList(sourceAccountEntity, destinationAccountEntity));
        return transactionEntity.getId();
    }

    public List<TransactionEntity> getAllTransactions(String ownerId, Long accountId) {
        Assert.notNull(accountId, "The given account id must not be null!");
        AccountEntity accountEntity = accountRepository.findOne(accountId);
        assertUserOwnsAccount(ownerId, accountEntity);

        ArrayList<TransactionEntity> result = new ArrayList<>(accountEntity.getIncomingTransfers());
        result.addAll(accountEntity.getOutgoingTransfers());
        return result;
    }

    public AccountHeader getAccountHeader(String ownerId, Long accountId) {
        Assert.notNull(accountId, "The given account id must not be null!");
        AccountEntity accountEntity = accountRepository.findOne(accountId);

        Assert.notNull(accountEntity, "Cannot find account");
        assertUserOwnsAccount(ownerId, accountEntity);

        return AccountHeader.fromEntity(accountEntity);
    }

    private void assertUserOwnsAccount(String ownerId, AccountEntity accountEntity) {
        Assert.isTrue(accountEntity != null && accountEntity.getOwnerId().equals(ownerId), "User does not own this account");
    }
}
