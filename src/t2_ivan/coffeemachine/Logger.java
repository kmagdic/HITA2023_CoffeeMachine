package t2_ivan.coffeemachine;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Logger {

    private final List<String> logList = new LinkedList<>();

    void log(String text) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String formattedDate = simpleDateFormat.format(date);

        String logText = "Date: " + formattedDate + " " + text;
        logList.add(logText);
    }

    void printLog() {
        for (String log: logList) {
            System.out.println(log);
        }
    }

}
