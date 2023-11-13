package t2_ivan.coffeemachine.coffemachine;


import t2_ivan.coffeemachine.coffemachine.repositories.LogRepository;

import java.util.Date;
import java.util.List;

public class Logger {

    private final LogRepository logRepository;

    public Logger(LogRepository repository) {
        this.logRepository = repository;
    }

    void log(CoffeeType coffee, int sugarAmount, boolean success) {
        Date currentDate = new Date(System.currentTimeMillis());
        logRepository.logTransaction(currentDate, coffee, sugarAmount, success, 1);
    }

    public void printLog() {
        List<LogEntry> logs = logRepository.getAllLogs();

        for (LogEntry logEntry : logs) {
            System.out.println("Date: " + logEntry.getDate());
            System.out.println("Coffee Type ID: " + logEntry.getCoffeeTypeId());
            System.out.println("Success: " + logEntry.isSuccess());
            System.out.println("Sugar: " + logEntry.getSugarAmount());
            System.out.println("Amount: " + logEntry.getAmount());
            System.out.println();
        }
    }
}
