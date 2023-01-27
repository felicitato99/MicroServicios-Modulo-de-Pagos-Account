package com.accenture.modulosPago.services;

import com.accenture.modulosPago.clients.UserClientRest;
import com.accenture.modulosPago.dtos.AccountDto;
import com.accenture.modulosPago.entities.Account;
import com.accenture.modulosPago.models.User;
import com.accenture.modulosPago.repositorys.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("accountServiceFeign")
@Primary
public class AccountServiceFeign implements AccountServiceInter {
    @Autowired
    private UserClientRest userClientRestFeign;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> findAll() {
        //List<Account> accountList = new ArrayList<>();
        //List<User> userList = userClientRestFeign.findAll().stream().collect(Collectors.toList());
        //for (int i = 0; i < userList.size(); i++) {
        //    accountList.addAll(accountRepository.findAllByUserId(userList.get(i).getAccountId()));
        //}
        return accountRepository.findAll();
    }

    @Override
    public List<Account> findByIdUser(Long userId) {
        return accountRepository.findAllByUserId(userId);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account createdAccount(User user) {
        Account newAccount;
        do {
            newAccount = new Account(user);
        } while (accountRepository.findByAccountNumber(newAccount.getAccountNumber()) != null
                || accountRepository.findByCbu(newAccount.getCbu()) != null);
        accountRepository.save(newAccount);
        userClientRestFeign.addAccountToUser(newAccount);
        return accountRepository.save(newAccount);
    }

    @Override
    public Account LastAccountCreated(Long userId) {
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
