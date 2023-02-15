package com.accenture.modulosPago.dtos;

public class TransactionInfoDto {
    private Double amount;
    private String sendingAccount;
    private  String receiverAccount;
    private String transactionType;


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSendingAccount() {
        return sendingAccount;
    }

    public void setSendingAccount(String sendingAccount) {
        this.sendingAccount = sendingAccount;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
