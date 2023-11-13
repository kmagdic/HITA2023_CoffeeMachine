package t4_zoran.coffeemachine;

import java.sql.*;

public class Transaction {

    private int id;
    private int coffeeTypeID;
    private boolean action;
    private String missing;
    private String CoffeeTypeName;

    public Transaction(int coffeeTypeID, boolean action, String missing) {

        this.coffeeTypeID = coffeeTypeID;
        this.action = action;
        this.missing = missing;
    }

    public Transaction() {}

    public String getCoffeeTypeName() {
        return CoffeeTypeName;
    }

    public void setCoffeeTypeName(String coffeeTypeName) {
        CoffeeTypeName = coffeeTypeName;
    }

    public void setCoffeeType(int coffeeTypeID) {
        this.coffeeTypeID = coffeeTypeID;
    }

    public void setCoffeeTypeID(int coffeeTypeID) {
        this.coffeeTypeID = coffeeTypeID;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public int getCoffeeTypeID() {
        return coffeeTypeID;
    }

    public boolean isAction() {
        return action;
    }

    public Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public void setTimestamp(Timestamp timestamp) {
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