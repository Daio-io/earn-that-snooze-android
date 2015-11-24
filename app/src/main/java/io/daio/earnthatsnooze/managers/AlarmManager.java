package io.daio.earnthatsnooze.managers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import io.daio.earnthatsnooze.Constants;
import io.daio.earnthatsnooze.alarm.Alarm;
import io.daio.earnthatsnooze.alarm.AlarmModelService;
import io.daio.earnthatsnooze.alarm.WeekDay;
import io.daio.earnthatsnooze.repository.AlarmRepository;
import io.daio.earnthatsnooze.services.AlarmService;

import static io.daio.earnthatsnooze.utils.CalendarUtil.hasTimePassed;
import static io.daio.earnthatsnooze.utils.CalendarUtil.isNextWeek;
import static io.daio.earnthatsnooze.utils.CalendarUtil.isThisWeek;
import static io.daio.earnthatsnooze.utils.CalendarUtil.isToday;

public class AlarmManager implements AlarmRepository.OnChangeListener {

    private AlarmModelService alarmModelService;
    private Context mContext;

    public AlarmManager(Context context, AlarmModelService alarmModelService) {
        this.alarmModelService = alarmModelService;
        this.mContext = context;
    }

    public void setAlarms() {
        cancelAlarms();

        for (Alarm alarm : alarmModelService.getAllAlarms()) {
            if (alarm.isEnabled()) {
                Calendar alarmCalendar = Calendar.getInstance(Locale.ENGLISH);
                alarmCalendar.setTimeInMillis(System.currentTimeMillis());
                alarmCalendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
                alarmCalendar.set(Calendar.MINUTE, alarm.getMinute());
                alarmCalendar.set(Calendar.SECOND, 0);
                setNextAlarm(alarm, alarmCalendar);
            }
        }

    }

    public void setNextAlarm(Alarm alarm, Calendar alarmCalendar) {
        HashMap<Integer, WeekDay> repeatingDays = alarm.getRepeatingDays();
        PendingIntent pendingIntent = createNewPendingIntent(alarm, (int) alarm.getId());

        if (repeatingDays.size() > 0) {
            setNextRepeatingAlarm(alarm, alarmCalendar, repeatingDays, pendingIntent);
        } else if (hasTimePassed(alarm.getHour(), alarm.getMinute())) {
            alarmCalendar.add(Calendar.DAY_OF_WEEK, 1);
            setAlarm(alarmCalendar, pendingIntent);
        } else {
            setAlarm(alarmCalendar, pendingIntent);
        }
    }

    public void setNextRepeatingAlarm(Alarm alarm, Calendar alarmCalendar,
                                      HashMap<Integer, WeekDay> repeatingDays,
                                      PendingIntent pendingIntent) {
        boolean alarmSet = false;
        for (WeekDay weekDay : repeatingDays.values()) {
            int alarmDay = weekDay.getValue();
            alarmCalendar.set(Calendar.DAY_OF_WEEK, alarmDay);

            if ((isToday(alarmDay) && !hasTimePassed(alarm.getHour(), alarm.getMinute()))
                    || isThisWeek(alarmDay)) {
                setAlarm(alarmCalendar, pendingIntent);
                alarmSet = true;
                break;
            }
        }
        if (!alarmSet) {
            for (WeekDay weekDay : repeatingDays.values()) {
                int alarmDay = weekDay.getValue();
                if (isNextWeek(alarmDay)){
                    alarmCalendar.set(Calendar.DAY_OF_WEEK, alarmDay);
                    alarmCalendar.add(Calendar.WEEK_OF_YEAR, 1);
                    setAlarm(alarmCalendar, pendingIntent);
                    break;
                }
            }
        }
    }

    public void cancelAlarms() {
        Iterable<Alarm> alarms = alarmModelService.getAllAlarms();

        for (Alarm alarm : alarms) {
            PendingIntent pendingIntent = createNewPendingIntent(alarm, (int) alarm.getId());
            cancelAlarm(pendingIntent);
        }
    }

    private void cancelAlarm(PendingIntent pendingIntent) {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private void setAlarm(Calendar calendar, PendingIntent pIntent) {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);

        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);

        } else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }

    private PendingIntent createNewPendingIntent(Alarm alarm, int id) {
        Intent intent = new Intent(mContext, AlarmService.class);
        intent.putExtra(Constants.ALARM_ID_KEY, id);
        intent.putExtra(Constants.ALARM_HOUR_KEY, alarm.getHour());
        intent.putExtra(Constants.ALARM_MINUTE_KEY, alarm.getMinute());
        intent.putExtra(Constants.ALARM_ENABLED_KEY, alarm.isEnabled());

        return PendingIntent.getService(mContext, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onDataChanged() {
        setAlarms();
    }

    @Override
    public void onAlarmStateChanged() {
        setAlarms();
    }

}
