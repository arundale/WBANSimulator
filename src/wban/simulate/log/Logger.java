package wban.simulate.log;

import java.util.Calendar;

public class Logger {

    public static void log(String s) {
        System.out.print(Calendar.getInstance());
        System.out.print(" ");
        System.out.println(s);
    }

}
