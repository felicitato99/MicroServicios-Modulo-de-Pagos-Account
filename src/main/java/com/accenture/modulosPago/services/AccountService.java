package com.accenture.modulosPago.services;

import com.accenture.modulosPago.dtos.TransactionInfoDto;
import com.accenture.modulosPago.entities.Account;
import com.accenture.modulosPago.models.User;
import com.accenture.modulosPago.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import utils.Utils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("accountServiceRestTemplate")

public class AccountService implements AccountServiceInter {

    private Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private RestTemplate clientRest;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional(readOnly = true) //spring
    public List<Account> findAll(){
        logger.info("MSAccounts RestTemplated: list all account");
        return (List<Account>) accountRepository.findAll();
    }



    @Override
    public List<Account> findByIdUser(Long userId) {
        logger.info("MSAccounts RestTemplated: find account with idUser");
        return accountRepository.findByUserIdAndIsActive(userId,Boolean.TRUE);
    }



    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account createdAccount(User user){
        Account accountNew;
        do {
            accountNew = new Account(user);
        } while (accountRepository.findByAccountNumber(accountNew.getAccountNumber()) != null
                || accountRepository.findByCbu(accountNew.getCbu()) != null);
        logger.info("MSAccounts RestTemplated: created account ");
        accountRepository.save(accountNew);
        return accountNew;
    }

    @Transactional(readOnly = true)
    public Account LastAccountCreated(Long userId){
        logger.info("MSAccounts RestTemplated: find user exists to create account");
        return accountRepository.findByUserIdOrderByIdDesc(userId)
                .stream().findFirst()
                .orElse(null);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        logger.info("MSAccounts RestTemplated: find account with number account");
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Account findByCbu(String cbu) {
        return accountRepository.findByCbu(cbu);
    }


    @Override
    public Long findLastUserWithAccount(Long userId) {
        logger.info("MSAccounts RestTemplated: find user exits to create account");
        Map<String,String> pathVariables = new HashMap<String,String>();
        pathVariables.put("id",userId.toString());
        User auxUser = clientRest.getForObject("http://localhost:8001/list/{id}",User.class,pathVariables);
        if(auxUser!=null){
            return auxUser.getId();
        }else{
            return 0L;
        }

    }
    @Override
    public Boolean deleteById(Long accountId) {
        logger.info("MSAccounts RestTemplated: delete account with id");
        Optional<Account> accountCheck = accountRepository.findById(accountId);
        if(accountCheck.isPresent()) {
            if(Utils.verifyBalanceAccount(accountCheck.get())){
                accountRepository.delete(accountCheck.get());
                return true;
            }
        }
        return false;
    }
    @Override
    public void updateBalance(TransactionInfoDto transactionInfoDto) {
        Account account1 = accountRepository.findByAccountNumber(transactionInfoDto.getSendingAccount());
        Account account2 = accountRepository.findByAccountNumber(transactionInfoDto.getReceiverAccount());
        BigDecimal total1 = Utils.updateBalance("DEBIT",account1,transactionInfoDto.getAmount());
        BigDecimal total2 = Utils.updateBalance("CREDIT",account2,transactionInfoDto.getAmount());
        account1.setBalance(total1);
        account2.setBalance(total2);
        accountRepository.save(account1);
        accountRepository.save(account2);
        logger.info("MSAccounts RestTemplate: update balance account");
    }


}
