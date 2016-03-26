package com.github.szberes.netbank.backend.controllers;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RestTransactionRequest {

    @NotNull(message = "The source account Id cannot be null.")
    @Min(value = 0, message = "The source account id must be a positive integer.")
    private Long sourceAccountId;

    @NotNull(message = "The destination account Id cannot be null.")
    @Min(value = 0, message = "The destination account id must be a positive integer.")
    private Long destinationAccountId;

    @NotNull(message = "The amount cannot be null.")
    @Min(value = 1, message = "The amount must be greater or equal to 1. ")
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
