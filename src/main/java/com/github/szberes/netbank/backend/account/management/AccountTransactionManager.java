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
import com.github.szberes.netbank.backend.account.management.jpa.TransferEntity;

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
        Assert.notNull(sourceAccountEntity, "The source account does not exist");
        if (!sourceAccountEntity.getOwnerId().equals(name)) {
            throw new IllegalArgumentException("User does not own this account"); // TODO forbidden
        }

        AccountEntity destinationAccountEntity = accountRepository.findOne(destinationAccountId);
        Assert.notNull(destinationAccountEntity, "The destination account does not exist");

        Assert.isTrue(sourceAccountEntity.getCurrency().equals(destinationAccountEntity.getCurrency()),
                "Currencies of the accounts should be equal!");

        if (sourceAccountEntity.getBalance() < amount) {
            throw new IllegalArgumentException("User does not have enough credit "); // TODO bad request, precondition failed etc
        }

        TransferEntity transferEntity = new TransferEntity(sourceAccountEntity, destinationAccountEntity, amount);
        transactionRepository.save(transferEntity);

        sourceAccountEntity.setBalance(sourceAccountEntity.getBalance() - amount);
        destinationAccountEntity.setBalance(destinationAccountEntity.getBalance() + amount);
        accountRepository.save(Arrays.asList(sourceAccountEntity, destinationAccountEntity));
        return transferEntity.getId();
    }

    public List<TransferEntity> getAllTransactions(String ownerId, Long accountId) {
        Assert.notNull(accountId, "The given account id must not be null!");
        AccountEntity accountEntity = accountRepository.findOne(accountId);
        assertUserOwnsAccount(ownerId, accountEntity);

        ArrayList<TransferEntity> result = new ArrayList<>(accountEntity.getIncomingTransfers());
        result.addAll(accountEntity.getOutgoingTransfers());
        return result;
    }

    public AccountHeader getAccountHeader(String ownerId, Long accountId) {
        AccountEntity accountEntity = accountRepository.findOne(accountId);

        Assert.notNull(accountEntity, "Cannot find account");
        assertUserOwnsAccount(ownerId, accountEntity);

        return AccountHeader.fromEntity(accountEntity);
    }

    private void assertUserOwnsAccount(String ownerId, AccountEntity accountEntity) {
        Assert.isTrue(accountEntity.getOwnerId().equals(ownerId), "Account belongs to another user.");
    }
}
