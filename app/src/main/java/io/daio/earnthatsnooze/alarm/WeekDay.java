package io.daio.earnthatsnooze.alarm;


public enum WeekDay {
    SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4),
    THURSDAY(5), FRIDAY(6), SATURDAY(7);

    private final int value;
    private WeekDay(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}