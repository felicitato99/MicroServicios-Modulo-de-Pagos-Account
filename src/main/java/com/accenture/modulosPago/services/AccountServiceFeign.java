package com.accenture.modulosPago.services;

import com.accenture.modulosPago.clients.UserClientRest;
import com.accenture.modulosPago.dtos.TransactionInfoDto;
import com.accenture.modulosPago.entities.Account;
import com.accenture.modulosPago.models.User;
import com.accenture.modulosPago.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service("serviceFeignAccount")
@Primary
public class AccountServiceFeign implements AccountServiceInter {
    @Autowired
    private UserClientRest userClientRestFeign;
    @Autowired
    private AccountRepository accountRepository;
    private Logger logger = LoggerFactory.getLogger(AccountServiceFeign.class);

    @Override
    public List<Account> findAll() {
        logger.info("MSAccounts Feign: List Account");
        return accountRepository.findAll();
    }
    @Override
    public List<Account> findByIdUser(Long userId) {
        logger.info("MSAccounts Feign: find account with userId");
        return accountRepository.findByUserIdAndIsActive(userId,Boolean.TRUE);
    }
    @Override
    public Account findById(Long id) {
        logger.info("MSAccounts Feign: find account with id");
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account createdAccount(User user) {
        Account accountNew;
        do {
            accountNew = new Account(user);
        } while (accountRepository.findByAccountNumber(accountNew.getAccountNumber()) != null
                || accountRepository.findByCbu(accountNew.getCbu()) != null);
        accountRepository.save(accountNew);
        logger.info("MSAccounts Feign: Account Created");
        return accountNew;
    }

    @Override
    public Account LastAccountCreated(Long userId) {
        logger.info("MSAccounts Feign: last Account created for userId");
        return accountRepository.findByUserIdOrderByIdDesc(userId)
                .stream().findFirst()
                .orElse(null);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        logger.info("MSAccounts Feign: find account with account number");
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Long findLastUserWithAccount(Long userId) {
        logger.info("MSAccounts Feign: find user exists to create account");
        User auxUser = userClientRestFeign.detail(userId);
        if(auxUser != null){
            return auxUser.getId();
        }else {
            return 0L;
        }
    }

    @Override
    public Account findByCbu(String cbu) {
        logger.info("MSAccounts Feign: find account with cbu");
        return accountRepository.findByCbu(cbu);
    }

    @Override
    public Boolean deleteById(Long accountId) {
        Optional<Account> accountCheck = accountRepository.findById(accountId);
        logger.info("MSAccounts Feign: deleted account by id");
        if(accountCheck.isPresent()) {
            if(Utils.verifyBalanceAccount(accountCheck.get())){
                accountCheck.get().setActive(false);
                accountRepository.save(accountCheck.get());
                return true;
            }
        }
        return false;
    }

    public void updateBalance(TransactionInfoDto transactionInfoDto) {
        Account account1 = accountRepository.findByAccountNumber(transactionInfoDto.getSendingAccount());
        Account account2 = accountRepository.findByAccountNumber(transactionInfoDto.getReceiverAccount());
        BigDecimal total1 = Utils.updateBalance("DEBIT",account1,transactionInfoDto.getAmount());
        BigDecimal total2 = Utils.updateBalance("CREDIT",account2,transactionInfoDto.getAmount());
        account1.setBalance(total1);
        account2.setBalance(total2);
        accountRepository.save(account1);
        accountRepository.save(account2);
        logger.info("MSAccounts Feign: update balance account");
    }


}
