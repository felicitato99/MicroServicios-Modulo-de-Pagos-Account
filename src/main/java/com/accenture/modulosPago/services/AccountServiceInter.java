package com.accenture.modulosPago.services;

import com.accenture.modulosPago.dtos.TransactionInfoDto;
import com.accenture.modulosPago.entities.Account;
import com.accenture.modulosPago.models.User;


import java.util.List;

public interface AccountServiceInter {
    public Account createdAccount(User user);

    public Account LastAccountCreated(Long userId);

    public List<Account> findAll();

    public Account findById(Long id);
    public List<Account> findByIdUser (Long userId);
    public Account findByAccountNumber(String accountNumber);
    public Long findLastUserWithAccount(Long userId);

    public Account findByCbu(String cbu);

    Boolean deleteById(Long accountId);
    public void updateBalance(TransactionInfoDto transactionInfoDTO);
}
