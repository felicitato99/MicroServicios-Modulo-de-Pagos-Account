package com.accenture.modulosPago.services;

import com.accenture.modulosPago.clients.UserClientRest;
import com.accenture.modulosPago.dtos.AccountDto;
import com.accenture.modulosPago.entities.Account;
import com.accenture.modulosPago.models.User;
import com.accenture.modulosPago.repositorys.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service ("accountServiceRestTemplate")
@Primary
public class AccountService implements AccountServiceInter {
    @Autowired
    private RestTemplate clientRest;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public List<Account> findAll(){
        return (List<Account>)accountRepository.findAll();
    }

    @Override
    public List<Account> findByIdUser(Long userId) {
        return accountRepository.findAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Account findById(Long id){
        return accountRepository.findById(id).orElse(null);
    }

    public Account createdAccount (User user){
        Account newAccount;
        do {
            newAccount = new Account(user);
        } while (accountRepository.findByAccountNumber(newAccount.getAccountNumber()) != null
                || accountRepository.findByCbu(newAccount.getCbu()) != null);
        accountRepository.save(newAccount);

        clientRest.postForEntity("http://localhost:8001/api/user/addAccountToUser/", newAccount,Account.class);

        return newAccount;
    }
    @Transactional(readOnly = true)
    public Account LastAccountCreated(Long userId){
        return accountRepository.findAllByUserIdOrderByIdDesc(userId)
                .stream().findFirst()
                .orElse(null);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Account findByCbu(String cbu) {
        return accountRepository.findByCbu(cbu);
    }

}
