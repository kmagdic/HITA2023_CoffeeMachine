package t2_ivan.coffeemachine.coffemachine;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Logger {

    private final LogRepository logRepository;

    public Logger(LogRepository repository) {
        this.logRepository = repository;
    }

    void log(CoffeeType coffee, boolean success) {
        Date currentDate = new Date(System.currentTimeMillis());
        logRepository.logTransaction(currentDate, coffee, success, 1);
    }

    void printLog() {
        //TODO: Dodati sql query za dohvacanje svih logova, slicno kao za dohvacanje svih kava
    }
}
