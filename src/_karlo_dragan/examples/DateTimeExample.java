package _karlo_dragan.examples;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeExample {

    public static void main(String[] args) {
        Date date = new Date(System.currentTimeMillis());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");


        System.out.println(simpleDateFormat.format(date));
    }
}
