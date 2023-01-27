package com.accenture.modulosPago.services;

import com.accenture.modulosPago.entities.Account;
import com.accenture.modulosPago.models.User;


import java.util.List;

public interface AccountServiceInter {

    public List<Account> findAll();
    public List<Account> findByIdUser (Long userId);

    public Account findById(Long id);

    public Account createdAccount(User user);

    public Account LastAccountCreated(Long userId);

    public Account findByAccountNumber(String accountNumber);

    public Account findByCbu(String cbu);

}
