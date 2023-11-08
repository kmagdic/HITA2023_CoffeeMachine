package t4_luka.coffeemachine;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

    public class Transaction {
        public Date date;
        public SimpleDateFormat simpleDate;
        String croatianDateStr;
        public String whatHappened;

        public String getWhatHappened() {
            return whatHappened;
        }

        public String getCroatianDateStr() {
            return croatianDateStr;
        }

        public Transaction(String whatHappened){
            date = new Date();
            simpleDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            croatianDateStr = simpleDate.format(date);
            this.whatHappened = whatHappened;
        }
    }