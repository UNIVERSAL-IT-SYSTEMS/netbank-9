package com.github.szberes.netbank.backend.account.management.jpa;

import java.util.Currency;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "accountNamesAreUniquePerUser", columnNames = {"ownerId", "accountName"}))
public class AccountEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long balance;

    private String ownerId;

    private String accountName;

    private Currency currency;

    @OneToMany(mappedBy = "sourceAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> outgoingTransfers;

    @OneToMany(mappedBy = "destinationAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> incomingTransfers;

    public AccountEntity() {
        // For JPA
    }

    public AccountEntity(String ownerId, String accountName, Currency currency) {
        this.ownerId = ownerId;
        this.accountName = accountName;
        this.currency = currency;
        this.balance = 0L;
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

    public List<TransactionEntity> getOutgoingTransfers() {
        return outgoingTransfers;
    }

    public void setOutgoingTransfers(List<TransactionEntity> outgoingTransfers) {
        this.outgoingTransfers = outgoingTransfers;
    }

    public List<TransactionEntity> getIncomingTransfers() {
        return incomingTransfers;
    }

    public void setIncomingTransfers(List<TransactionEntity> incomingTransfers) {
        this.incomingTransfers = incomingTransfers;
    }

    public void addIncomingTransfer(TransactionEntity transactionEntity) {
        incomingTransfers.add(transactionEntity);
    }

    public void addOutgoingTransfer(TransactionEntity transactionEntity) {
        outgoingTransfers.add(transactionEntity);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
