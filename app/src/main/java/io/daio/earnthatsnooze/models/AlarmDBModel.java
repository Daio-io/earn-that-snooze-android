package io.daio.earnthatsnooze.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlarmDBModel extends RealmObject {

    @PrimaryKey
    private long id;

    private int hour;
    private int minute;
    private RealmList<WeekDayDBModel> repeatingDays;

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

    public RealmList<WeekDayDBModel> getRepeatingDays() {
        return repeatingDays;
    }

    public void setRepeatingDays(RealmList<WeekDayDBModel> repeatingDays) {
        this.repeatingDays = repeatingDays;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}
