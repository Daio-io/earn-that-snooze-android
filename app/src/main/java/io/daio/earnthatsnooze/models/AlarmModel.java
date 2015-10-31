package io.daio.earnthatsnooze.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlarmModel extends RealmObject {

    public static int MONDAY = 1, TUESDAY = 2, WEDNESDAY = 3,
        THURSDAY = 4, FRIDAY = 5, SATURDAY = 6, SUNDAY = 7;

    @PrimaryKey
    private long id;

    private int hour;
    private int minute;
    private boolean isEnabled = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}
