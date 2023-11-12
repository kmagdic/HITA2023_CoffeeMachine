package t2_ivan.coffeemachine.coffemachine;

import java.util.Date;

public class LogEntry {
    private Date date;
    private int coffeeTypeId;
    private boolean success;
    private int amount;

    public LogEntry(Date date, int coffeeTypeId, boolean success, int amount) {
        this.date = date;
        this.coffeeTypeId = coffeeTypeId;
        this.success = success;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public int getCoffeeTypeId() {
        return coffeeTypeId;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getAmount() {
        return amount;
    }
}
