package t4_luka.coffeemachine;

import java.text.SimpleDateFormat;
import java.util.Date;

    public class Transaction {
        public Date date;
        public SimpleDateFormat simpleDate;
        public String croatianDateStr;
        public int coffeeTypeId;
        public String success;

        public String getSuccess() {
            return success;
        }

        public String getCroatianDateStr() {
            return croatianDateStr;
        }

        public Transaction(String success){
            date = new Date();
            simpleDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            croatianDateStr = simpleDate.format(date);
            this.success = success;
        }
    }