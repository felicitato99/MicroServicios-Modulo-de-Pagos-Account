package com.accenture.modulosPago.dtos;

import com.accenture.modulosPago.entities.Account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccountDto {
    private String accountNumber;
    private String cbu;
    private BigDecimal balance;
    private LocalDate creationDate;

    public AccountDto(Account account) {
        this.accountNumber = account.getAccountNumber();
        this.cbu = account.getCbu();
        this.balance = account.getBalance();
        this.creationDate = account.getCreationDate();
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
