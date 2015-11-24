package io.daio.earnthatsnooze.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WeekDayDBModel extends RealmObject {

    public final static int SUNDAY = 1, MONDAY = 2, TUESDAY = 3, WEDNESDAY = 4,
            THURSDAY = 5, FRIDAY = 6, SATURDAY = 7;

    @PrimaryKey
    private int weekDay;

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

}
