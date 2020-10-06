package ua.aleks4ay.manufplan.domain.tools;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public final class DateConverter {
    public static Calendar calendar = Calendar.getInstance();
    public static Calendar calendarAfterPeriod = Calendar.getInstance();


    public static long getNowDate(){
        return new Date().getTime();
    }

    public static String getNowDateString(){
        return dateToString(new Date().getTime());
    }

    public static int getYear(long millis) {
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }

    public static int getYearShort(long millis) {
        String year = String.valueOf(getYear(millis));
        return Integer.valueOf(year.substring(2));
    }

    public static long offset (long millisecond, int duration) {
        calendarAfterPeriod.setTimeInMillis(millisecond);
        calendarAfterPeriod.add(Calendar.DATE, Integer.valueOf(duration));
        return calendarAfterPeriod.getTimeInMillis();
    }

    public static String dateToString(long millis) {
        if ( (Long)millis == null | millis == 0 ) {
            return "-";
        }
        String date = "";
        calendar.setTimeInMillis(millis);
        int day, month;
        if ((day = calendar.get(Calendar.DAY_OF_MONTH))<10) {
            date += "0" + day + ".";
        }
        else {
            date += "" + day + ".";
        }
        if ((month = calendar.get(Calendar.MONTH) + 1 )<10) {
            date += "0" + month;
        }
        else {
            date += month;
        }
        return date + "." + calendar.get(Calendar.YEAR);
    }

    public static String timeToString(long millis) {
        calendar.setTimeInMillis(millis);
        String time = "";
        int hour, min;
        if ((hour = calendar.get(Calendar.HOUR_OF_DAY))<10) {
            time += "0" + hour + ":";
        }
        else {
            time += hour + ":";
        }

        if ((min = calendar.get(Calendar.MINUTE) )<10) {
            time += "0" + min;
        }
        else {
            time += min;
        }
        return time;
    }

    public static Timestamp getTimestampFromString(String DeyMonthYearDividedDashOrPoint) {
//        String[] date = DeyMonthYearDividedDashOrPoint.split("-");
        String[] date = DeyMonthYearDividedDashOrPoint.split("\\.");
        if (date.length < 2) {
            date = DeyMonthYearDividedDashOrPoint.split("-");
        }
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 0, 0);
        return Timestamp.valueOf(localDateTime);
    }

    public static LocalDate getLocalDateFromString(String DeyMonthYearDividedDashOrPoint) {
        String[] date = DeyMonthYearDividedDashOrPoint.split("\\.");
        if (date.length < 2) {
            date = DeyMonthYearDividedDashOrPoint.split("-");
        }
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        LocalDate localDate = LocalDate.of(year, month, day);
        return localDate;
    }



    public static Timestamp localDateToTimestamp (LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Timestamp result = Timestamp.valueOf(localDateTime);
        return result;
    }

    public static boolean isTheSameDay(Timestamp t1, Timestamp t2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTimeInMillis(t1.getTime());
        c2.setTimeInMillis(t2.getTime());
        boolean yearEquals = c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
        boolean monthEquals = c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
        boolean dayEquals = c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
        return yearEquals & monthEquals & dayEquals;
    }

}
