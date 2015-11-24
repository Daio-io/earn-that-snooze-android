package io.daio.earnthatsnooze.alarm;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.daio.earnthatsnooze.models.AlarmDBModel;
import io.daio.earnthatsnooze.models.WeekDayDBModel;
import io.realm.RealmList;

public class AlarmTransformer {

    public List<Alarm> transform(List<AlarmDBModel> repoAlarms) {

        List<Alarm> alarms = new ArrayList<>();

        for (int i = 0; i < repoAlarms.size(); i++) {
            AlarmDBModel alarmDBModel = repoAlarms.get(i);
            Alarm alarm = AlarmFactory.newAlarm(alarmDBModel.getId(),
                    alarmDBModel.getHour(),
                    alarmDBModel.getMinute());

            addRepeatingDays(alarm, alarmDBModel);
            alarms.add(i, alarm);
        }

        return alarms;
    }

    public AlarmDBModel transform(Alarm alarm) {
        AlarmDBModel alarmDBModel = new AlarmDBModel();
        alarmDBModel.setId(alarm.getId());
        alarmDBModel.setIsEnabled(alarm.isEnabled());
        alarmDBModel.setMinute(alarm.getMinute());
        alarmDBModel.setHour(alarm.getHour());

        HashMap<Integer, WeekDay> repeatingDays = alarm.getRepeatingDays();
        RealmList<WeekDayDBModel> dbRepeatingDays = new RealmList<>();
        int i = 0;
        for (WeekDay weekDay : repeatingDays.values()) {
            WeekDayDBModel weekDayDBModel = new WeekDayDBModel();
            weekDayDBModel.setWeekDay(weekDay.getValue());
            dbRepeatingDays.add(i, weekDayDBModel);
            i++;
        }
        alarmDBModel.setRepeatingDays(dbRepeatingDays);
        return alarmDBModel;
    }

    private void addRepeatingDays(Alarm alarm, AlarmDBModel alarmDBModel) {
        List<WeekDayDBModel> days = alarmDBModel.getRepeatingDays();
        for (int i = 0; i < days.size(); i++) {
            WeekDay weekDay = getWeekday(days.get(i));
            if (weekDay != null) {
                alarm.addRepeatingDay(getWeekday(days.get(i)));
            }
        }
    }

    private WeekDay getWeekday(WeekDayDBModel weekDayDBModel) {

        switch (weekDayDBModel.getWeekDay()) {
            case WeekDayDBModel.SUNDAY:
                return WeekDay.SUNDAY;
            case WeekDayDBModel.MONDAY:
                return WeekDay.MONDAY;
            case WeekDayDBModel.TUESDAY:
                return WeekDay.TUESDAY;
            case WeekDayDBModel.WEDNESDAY:
                return WeekDay.WEDNESDAY;
            case WeekDayDBModel.THURSDAY:
                return WeekDay.THURSDAY;
            case WeekDayDBModel.FRIDAY:
                return WeekDay.FRIDAY;
            case WeekDayDBModel.SATURDAY:
                return WeekDay.SATURDAY;
            default:
                return null;
        }

    }
}
