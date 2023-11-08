package t5_fran.coffeemachine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String dateTime;
    private String coffeeType;
    private String action;

    public Transaction(String typeOfCoffee, String theAction) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
        dateTime = dtf.format(LocalDateTime.now());
        coffeeType = typeOfCoffee;
        action = theAction;

    }

    public String getTransactionInfo() {

        String transactionInfo = "Date/time: " + dateTime + ", coffee type: " + coffeeType + ", action: " + action;
        return transactionInfo;

    }

}