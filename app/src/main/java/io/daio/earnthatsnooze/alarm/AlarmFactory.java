package io.daio.earnthatsnooze.alarm;

public final class AlarmFactory {

    public static Alarm newAlarm(long id, int hour, int minute) {
        Alarm alarm = new Alarm();
        alarm.setId(id);
        alarm.setHour(hour);
        alarm.setMinute(minute);
        alarm.setIsEnabled(true);
        return alarm;
    }

}
