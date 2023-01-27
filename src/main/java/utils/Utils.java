package utils;

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

    public static Boolean verifyNumber(String number) {
        try {
            Long.parseLong(number);
            return true;
        } catch (NumberFormatException e) {
            e.getMessage();
            return false;
        }
    }

}
