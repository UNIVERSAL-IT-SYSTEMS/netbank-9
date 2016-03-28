package com.github.szberes.netbank.backend.account.management.jpa;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TransactionEntity {

    @Id
    @SequenceGenerator(name="transactionIdGenerator", sequenceName="transaction_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactionIdGenerator")
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

    public TransactionEntity() {
        // For JPA
    }

    public TransactionEntity(AccountEntity sourceAccount, AccountEntity destinationAccount, Long amount) {
        date = new Date();
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
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
