package io.daio.earnthatsnooze.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlarmModel extends RealmObject {

    @PrimaryKey
    private int id;

    private String alarmName;
    private int hour;
    private int minute;
    private RealmList<WeekDayModel> repeatingDays;

    private boolean isEnabled = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String name) {
        this.alarmName = name;
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

    public RealmList<WeekDayModel> getRepeatingDays() {
        return repeatingDays;
    }

    public void setRepeatingDays(RealmList<WeekDayModel> repeatingDays) {
        this.repeatingDays = repeatingDays;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}
