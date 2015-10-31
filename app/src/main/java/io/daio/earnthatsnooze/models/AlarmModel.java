package io.daio.earnthatsnooze.models;

import android.net.Uri;

public class AlarmModel {

    public static int MONDAY = 1, TUESDAY = 2, WEDNESDAY = 3,
        THURSDAY = 4, FRIDAY = 5, SATURDAY = 6, SUNDAY = 7;

    public long id;
    public int hour;
    public int minute;
    public Uri alertTone;
    public boolean isEnabled;
    public boolean repeatWeekly;

    private boolean[] daysToRepeat;

    public AlarmModel() {
        daysToRepeat = new boolean[7];
    }

    public void setDayToRepeat(int weekDay, boolean value) {
        daysToRepeat[weekDay] = value;
    }

    public boolean getDayToRepeat(int weekDay) {
        return daysToRepeat[weekDay];
    }

}
