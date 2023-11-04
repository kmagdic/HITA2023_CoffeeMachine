package t4_luka.coffeemachine;

import java.text.SimpleDateFormat;
import java.util.Date;

    public class Transaction {
        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        public Date date;
        public String whatHappened;

        public Date getDate() {
            return date;
        }

        public String getWhatHappened() {
            return whatHappened;
        }

        public Transaction(String whatHappened){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            date = new Date();
            this.whatHappened = whatHappened;
        }
    }