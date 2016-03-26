package com.github.szberes.netbank.backend.controllers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.github.szberes.netbank.backend.account.management.jpa.TransactionEntity;

@Component
public class TransactionEntityToRestTransactionConverter implements Converter<TransactionEntity, RestTransaction> {
    @Override
    public RestTransaction convert(TransactionEntity transactionEntity) {
        RestTransaction restTransaction = new RestTransaction();
        restTransaction.setId(transactionEntity.getId());
        restTransaction.setDate(transactionEntity.getDate());
        restTransaction.setAmount(transactionEntity.getAmount());
        restTransaction.setDestinationAccountId(transactionEntity.getDestinationAccount().getId());
        restTransaction.setDestinationAccountOwner(transactionEntity.getDestinationAccount().getOwnerId());
        restTransaction.setSourceAccountId(transactionEntity.getSourceAccount().getId());
        restTransaction.setSourceAccountOwner(transactionEntity.getSourceAccount().getOwnerId());
        return restTransaction;
    }
}
