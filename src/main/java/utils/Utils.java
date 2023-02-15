package utils;

import com.accenture.modulosPago.entities.Account;

import java.math.BigDecimal;

public class Utils {

    public Utils() {
    }

    public static String generateRandomDigits(int digit) {

        String newNumber = "";
        for (int i = 0; i < digit; i++) {
            int newNum = (int) (Math.random() * 10);
            newNumber += String.valueOf(newNum);
        }
        return newNumber;
    }

    public static Boolean verifyNumber(String number){
        try {
            Double.parseDouble(number);
            return true;
        }catch (NumberFormatException e){
            e.getMessage();
            return false;
        }
    }

    public static Boolean verifyBalanceAccount(Account account){
        Boolean flag = false;
        if(account.getBalance().doubleValue() == 0.00){
            flag = true;
        }
        return flag;
    }
    public static BigDecimal updateBalance(String type, Account account, Double amount){
        BigDecimal total;

        total = account.getBalance();
        switch (type) {
            case "CREDIT":
                total = total.add(BigDecimal.valueOf(amount));
                break;
            case "DEBIT":
                total =  total.subtract(BigDecimal.valueOf(amount));
                break;
        }
        return total;
    }

}
