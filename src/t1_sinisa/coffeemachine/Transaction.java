package t1_sinisa.coffeemachine;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    Date currentDate = new Date();
    private int id;
    private Timestamp timestamp;
    private String drinkType;
    private String buyStatus;

    public Transaction() {

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }

    public Timestamp getFormattedDate() {
        return timestamp;
    }

    public void setFormattedDate(Date currentDate) {
        this.timestamp = new Timestamp(currentDate.getTime());
    }

    public String getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(String boughtOrNot) {
        this.buyStatus = boughtOrNot;
    }

}
