package com.github.szberes.netbank.backend.account.management.jpa;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TransferEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    @ManyToOne(targetEntity = AccountEntity.class, optional = false)
    private AccountEntity sourceAccount;

    @ManyToOne(targetEntity = AccountEntity.class, optional = false)
    private AccountEntity destinationAccount;

    @Column(nullable = false)
    private Long amount;

    public TransferEntity(AccountEntity sourceAccount, AccountEntity destinationAccount, Long amount) {
        date = new Date();
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public TransferEntity() {
    }

    public AccountEntity getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(AccountEntity sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public AccountEntity getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(AccountEntity destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
