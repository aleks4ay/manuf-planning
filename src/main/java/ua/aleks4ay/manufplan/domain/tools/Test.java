package ua.aleks4ay.manufplan.domain.tools;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    private static final String MIN_DEY_TO_SHIPMENT = "2020-03-15";
    private static final String MAX_DEY_TO_SHIPMENT = "2020-08-15";

    public static void main(String[] args) {
        test4();
    }


    public static void test1() {
        String s = "2020-08-15";
        String[] date = s.split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 0, 0);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        System.out.println(localDateTime);
        System.out.println(timestamp.getDate());
    }

    public static void test2() {
        String beginDay = "20.04.2020";

//        String[] date = beginDay.split("\\.");
//        if (date.length < 2) {
//            date = beginDay.split("-");
//        }

//        int day = Integer.parseInt(date[0]);
//        int month = Integer.parseInt(date[1]);
//        int year = Integer.parseInt(date[2]);
//        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 0, 0);
//        Timestamp.valueOf(localDateTime);


        Timestamp start = DateConverter.getTimestampFromString(beginDay);

        System.out.println(DateConverter.dateToString(start.getTime()));
    }

    public static void test3() {
        System.out.println(DateConverter.getNowDateString());
    }

    public static void test4() {
        List<String> list = Arrays.asList("t0 Епіцентр erer", "t1", "t2 Епіцентр", "Епіцентр t3", "t4", "t5");
        List<String> result = new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            if (! list.get(i).contains("Епіцентр")) {
                result.add(list.get(i));
            }
        }
        result.forEach(System.out::println);
    }
}
