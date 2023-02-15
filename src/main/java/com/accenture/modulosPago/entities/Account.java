package com.accenture.modulosPago.entities;

import com.accenture.modulosPago.dtos.AccountDto;
import com.accenture.modulosPago.models.User;
import org.hibernate.annotations.GenericGenerator;
import utils.Utils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    @Column (unique = true)
    private String accountNumber;
    @Column (unique = true)
    private String cbu;
    private BigDecimal balance;
    private LocalDate creationDate;
    @Transient
    private User user;
    private Long userId;
    private Boolean isActive;

    public Account() {
    }

    public Account(User user) {
        this.accountNumber = Utils.generateRandomDigits(10);
        this.cbu = Utils.generateRandomDigits(22);
        this.balance = new BigDecimal(0.00);
        this.creationDate = LocalDate.now();
        this.user = user;
        this.userId = user.getId();
        this.isActive = true;
    }


    public Account(AccountDto accountDto) {
        this.accountNumber = accountDto.getAccountNumber();
        this.cbu = accountDto.getCbu();
        this.balance = accountDto.getBalance();
        this.creationDate = accountDto.getCreationDate();
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
