package com.github.szberes.netbank.backend.controllers;

// TODO javax validation
public class RestTransactionRequest {

    private Long sourceAccountId;

    private Long destinationAccountId;

    private Long amount;

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

    public RestTransactionRequest() {
    }
}
