package t1_sinisa.coffeemachine;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private String formattedDate;
    private String drinkType;
    private String buyStatus;
    private String explanation;

    public Log () {

    }

    public String getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(Date date) {
        this.formattedDate = simpleDateFormat.format(date);
    }

    public String getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(String boughtOrNot) {
        this.buyStatus = boughtOrNot;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
