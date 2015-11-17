package io.daio.earnthatsnooze.utils;

import java.util.Calendar;

public class CalenderUtil {

    public static boolean isToday(int dateToCheck) {

        return dateToCheck == getCurrentDay();
    }

    public static boolean isThisWeek(int dateToCheck) {

        int dayOfWeek = getCurrentDay();

        if (dayOfWeek == Calendar.SATURDAY) {
            return dayOfWeek > dateToCheck;
        } else {
            return dayOfWeek < dateToCheck;
        }
    }

    public static boolean isNextWeek(int dateToCheck) {

        int dayOfWeek = getCurrentDay();

        if (dayOfWeek == Calendar.SATURDAY) {
            return dayOfWeek < dateToCheck;
        } else {
            return dayOfWeek > dateToCheck;
        }

    }

    public static boolean hasTimePassed(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        return currentHour > hour || currentHour <= hour && currentMinute > minute;
    }

    private static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

}
