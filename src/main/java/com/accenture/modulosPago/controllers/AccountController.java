package com.accenture.modulosPago.controllers;

import com.accenture.modulosPago.dtos.AccountDto;
import com.accenture.modulosPago.entities.Account;
import com.accenture.modulosPago.models.User;
import com.accenture.modulosPago.repositorys.AccountRepository;
import com.accenture.modulosPago.services.AccountService;
import com.accenture.modulosPago.services.AccountServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.Utils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    @Qualifier("accountServiceRestTemplate")
    private AccountServiceInter accountServiceInter;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/list")
    public List<Account> getListAccounts(){
        return accountServiceInter.findAll();
    }

    @GetMapping("/list/{id}")
    public Account getById(@PathVariable Long id){
        return accountServiceInter.findById(id);
    }


    @PostMapping("/createdAccount")
    public ResponseEntity<Object> createdAccount(@RequestBody User user){
        try{
            Account accountNew = accountServiceInter.createdAccount(user);
            return new ResponseEntity<>(accountNew, HttpStatus.CREATED);
        }catch (Exception ex){
            ex.printStackTrace();
            ex.getMessage();
            return new ResponseEntity<>("Unexpected Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lastAccountCreated/{userId}")
    public Account lastAccountCreated(@PathVariable Long userId){
        return accountServiceInter.LastAccountCreated(userId);
    }
    @GetMapping("/listAccountCreated/{userId}")
    public List<Account> listAccountIdCreated(@PathVariable Long userId){
        return accountServiceInter.findByIdUser(userId);
    }

    @GetMapping("/list/cbu/{cbu}")
    public ResponseEntity<Object> getByCbu(@PathVariable String cbu) {
        if (cbu.length() != 22) {
            return new ResponseEntity<>("CBU must have 22 digits", HttpStatus.NOT_FOUND);
        }
        if (Utils.verifyNumber(cbu) == false) {
            return new ResponseEntity<>("CBU only accept digits numbers", HttpStatus.NOT_FOUND);
        }
        Account account = accountServiceInter.findByCbu(cbu);
        return new ResponseEntity<>(account, HttpStatus.ACCEPTED);
    }
    @GetMapping("/list/accountNumber/{accountNumber}")
    public ResponseEntity<Object> getByAccountNumber(@PathVariable String accountNumber) {
        if (accountNumber.length() != 10) {
            return new ResponseEntity<>("Account Number must have 10 digits", HttpStatus.NOT_FOUND);
        }
        if (Utils.verifyNumber(accountNumber) == false) {
            return new ResponseEntity<>("CBU only accept digits numbers", HttpStatus.NOT_FOUND);
        }
        Account account = accountServiceInter.findByAccountNumber(accountNumber);
        return new ResponseEntity<>(account, HttpStatus.ACCEPTED);
    }

}
