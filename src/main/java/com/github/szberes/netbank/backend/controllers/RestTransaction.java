package com.github.szberes.netbank.backend.controllers;

import java.util.Date;

public class RestTransaction {

    private Long id;
    private Date date;
    private Long sourceAccountId;
    private String sourceAccountOwner;
    private Long destinationAccountId;
    private String destinationAccountOwner;
    private Long amount;

    public RestTransaction() {
        // For JSON
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(Long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
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

    public String getDestinationAccountOwner() {
        return destinationAccountOwner;
    }

    public void setDestinationAccountOwner(String destinationAccountOwner) {
        this.destinationAccountOwner = destinationAccountOwner;
    }

    public String getSourceAccountOwner() {
        return sourceAccountOwner;
    }

    public void setSourceAccountOwner(String sourceAccountOwner) {
        this.sourceAccountOwner = sourceAccountOwner;
    }
}
