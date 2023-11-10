package t4_zoran.coffeemachine;

import java.sql.*;

class Transaction {

    private int id;
    private Timestamp timestamp;
    private String coffeeType;
    private boolean action;
    private String missing;

    public Transaction(String coffeeType, boolean action, String missing) {

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
//        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        this.coffeeType = coffeeType;
        this.action = action;
        this.missing = missing;
    }

    public Transaction() {

    }

    public void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public boolean isAction() {
        return action;
    }

    public Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMissing() {
        return missing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMissing(String missing) {
        this.missing = missing;
    }
}