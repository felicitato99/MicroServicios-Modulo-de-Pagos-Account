package com.accenture.modulosPago.controllers;

import com.accenture.modulosPago.dtos.TransactionInfoDto;
import com.accenture.modulosPago.entities.Account;
import com.accenture.modulosPago.models.User;
import com.accenture.modulosPago.repositories.AccountRepository;
import com.accenture.modulosPago.services.AccountServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.Utils;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    @Qualifier("serviceFeignAccount")
    private AccountServiceInter accountServiceInter;
    @GetMapping("/list")
    public List<Account> getListAccounts(){
        return accountServiceInter.findAll();
    }

    @GetMapping("/list/{id}")
    public Account getById(@PathVariable Long id){
        return accountServiceInter.findById(id);
    }


    @PostMapping ("/createdAccount")
    public ResponseEntity<Object> createdAccount(@RequestBody User user){
        try{
            int a = user.getDni().length();
            if (!(a > 6 && a < 9)) {
                return new ResponseEntity<>("DNI field must have 7 or 8 digits",HttpStatus.NOT_ACCEPTABLE);
            }
            if (!Utils.verifyNumber(user.getDni())) {
                return new ResponseEntity<>("Error in DNI field, please check it only numbers.",HttpStatus.NOT_ACCEPTABLE);
            }
            if (user.getName().isEmpty() || user.getLastname().isEmpty() || user.getDni().isEmpty()
                    || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
                return new ResponseEntity<>("Missing data, please check all fields",HttpStatus.NOT_ACCEPTABLE);
            }
            if(!accountServiceInter.findAll().isEmpty()) {
                if (accountServiceInter.findLastUserWithAccount(user.getId()) == 0L) {
                    return new ResponseEntity<>("This User NO exist, check data", HttpStatus.NOT_ACCEPTABLE);
                }
            }

            Account accountNew = accountServiceInter.createdAccount(user);
            return new ResponseEntity<>(accountNew, HttpStatus.CREATED);

        } catch (Exception ex){
            ex.printStackTrace();
            ex.getMessage();
            return new ResponseEntity<>("Unexpected Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lastAccountCreated/{idUser}")
    public Account lastAccountCreated(@PathVariable Long idUser){
        return accountServiceInter.LastAccountCreated(idUser);
    }
    @GetMapping("/listAccount/{userId}")
    public List<Account> listAccountUserId(@PathVariable Long userId){
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
    @GetMapping("/list/number/{number}")
    public ResponseEntity<Object> getByAccountNumber(@PathVariable String number) {
        if (number.length() != 10) {
            return new ResponseEntity<>("Account Number must have 10 digits", HttpStatus.NOT_FOUND);
        }
        if (Utils.verifyNumber(number) == false) {
            return new ResponseEntity<>("CBU only accept digits numbers", HttpStatus.NOT_FOUND);
        }
        Account account = accountServiceInter.findByAccountNumber(number);
        return new ResponseEntity<>(account, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long accountId){
        if(accountServiceInter.deleteById(accountId)) {
            return new ResponseEntity<>("Account Deleted Successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Account no exists or Account have money in balance",HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PostMapping("/updateBalance")
    public void updateBalance(@RequestBody TransactionInfoDto transactionInfoDto){
        accountServiceInter.updateBalance(transactionInfoDto);
    }


}
