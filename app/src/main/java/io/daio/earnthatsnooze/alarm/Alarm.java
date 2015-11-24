package io.daio.earnthatsnooze.alarm;

import java.util.HashMap;

public final class Alarm {

    public Alarm() {
        repeatingDays = new HashMap<>();
    }

    private long id;
    private int hour;
    private int minute;
    private HashMap<Integer, WeekDay> repeatingDays;

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

    public HashMap<Integer, WeekDay> getRepeatingDays() {
        return repeatingDays;
    }

    public void addRepeatingDay(WeekDay day) {
        this.repeatingDays.put(day.getValue(), day);
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}
