package com.github.szberes.netbank.backend.controllers;

import com.github.szberes.netbank.backend.account.management.jpa.TransferEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionEntityToRestTransactionConverter implements Converter<TransferEntity, RestTransaction> {
    @Override
    public RestTransaction convert(TransferEntity transferEntity) {
        RestTransaction restTransaction = new RestTransaction();
        restTransaction.setId(transferEntity.getId());
        restTransaction.setDate(transferEntity.getDate());
        restTransaction.setAmount(transferEntity.getAmount());
        restTransaction.setDestinationAccountId(transferEntity.getDestinationAccount().getId());
        restTransaction.setDestinationAccountOwner(transferEntity.getDestinationAccount().getOwnerId());
        restTransaction.setSourceAccountId(transferEntity.getSourceAccount().getId());
        restTransaction.setSourceAccountOwner(transferEntity.getSourceAccount().getOwnerId());
        return restTransaction;
    }
}
