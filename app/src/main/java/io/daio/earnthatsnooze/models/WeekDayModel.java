package io.daio.earnthatsnooze.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WeekDayModel extends RealmObject{

    public static int MONDAY = 1, TUESDAY = 2, WEDNESDAY = 3,
            THURSDAY = 4, FRIDAY = 5, SATURDAY = 6, SUNDAY = 7;

    @PrimaryKey
    private int weekDay;

    private boolean isEnabled = false;

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
