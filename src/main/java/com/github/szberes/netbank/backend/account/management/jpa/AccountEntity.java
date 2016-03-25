package com.github.szberes.netbank.backend.account.management.jpa;

import javax.persistence.*;
import java.util.Currency;
import java.util.List;

@Entity
public class AccountEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long balance;

    private String ownerId;

    private String accountName;

    private Currency currency;

    @OneToMany(mappedBy = "sourceAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransferEntity> outgoingTransfers;

    @OneToMany(mappedBy = "destinationAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransferEntity> incomingTransfers;

    public AccountEntity(String ownerId, String accountName, Currency currency) {
        this.ownerId = ownerId;
        this.accountName = accountName;
        this.currency = currency;
        this.balance = 0L;
    }

    public AccountEntity() {
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public List<TransferEntity> getOutgoingTransfers() {
        return outgoingTransfers;
    }

    public void setOutgoingTransfers(List<TransferEntity> outgoingTransfers) {
        this.outgoingTransfers = outgoingTransfers;
    }

    public List<TransferEntity> getIncomingTransfers() {
        return incomingTransfers;
    }

    public void setIncomingTransfers(List<TransferEntity> incomingTransfers) {
        this.incomingTransfers = incomingTransfers;
    }

    public void addIncomingTransfer(TransferEntity transferEntity) {
        incomingTransfers.add(transferEntity);
    }

    public void addOutgoingTransfer(TransferEntity transferEntity) {
        outgoingTransfers.add(transferEntity);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
