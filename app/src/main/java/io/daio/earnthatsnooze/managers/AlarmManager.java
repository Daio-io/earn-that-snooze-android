package io.daio.earnthatsnooze.managers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.daio.earnthatsnooze.Constants;
import io.daio.earnthatsnooze.models.AlarmModel;
import io.daio.earnthatsnooze.models.WeekDayModel;
import io.daio.earnthatsnooze.repository.AlarmRepository;
import io.daio.earnthatsnooze.services.AlarmService;

import static io.daio.earnthatsnooze.utils.CalendarUtil.*;

public class AlarmManager implements AlarmRepository.OnChangeListener {

    private AlarmRepository alarmRepository;
    private Context mContext;

    public AlarmManager(Context context, AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
        this.mContext = context;
    }

    public void setAlarms() {
        cancelAlarms();

        for (AlarmModel alarm : alarmRepository.getAll()) {
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

    public void setNextAlarm(AlarmModel alarm, Calendar alarmCalendar) {
        List<WeekDayModel> repeatingDays = alarm.getRepeatingDays();
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

    public void setNextRepeatingAlarm(AlarmModel alarm, Calendar alarmCalendar,
                                      List<WeekDayModel> repeatingDays,
                                      PendingIntent pendingIntent) {
        boolean alarmSet = false;
        for (WeekDayModel weekDayModel : repeatingDays) {
            int alarmDay = weekDayModel.getWeekDay();
            alarmCalendar.set(Calendar.DAY_OF_WEEK, alarmDay);

            if ((isToday(alarmDay) && !hasTimePassed(alarm.getHour(), alarm.getMinute()))
                    || isThisWeek(alarmDay)) {
                setAlarm(alarmCalendar, pendingIntent);
                alarmSet = true;
                break;
            }
        }
        if (!alarmSet) {
            for (WeekDayModel weekDayModel : repeatingDays) {
                int alarmDay = weekDayModel.getWeekDay();
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
        Iterable<AlarmModel> alarms = alarmRepository.getAll();

        for (AlarmModel alarm : alarms) {
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

    private PendingIntent createNewPendingIntent(AlarmModel alarmModel, int id) {
        Intent intent = new Intent(mContext, AlarmService.class);
        intent.putExtra(Constants.ALARM_ID_KEY, id);
        intent.putExtra(Constants.ALARM_HOUR_KEY, alarmModel.getHour());
        intent.putExtra(Constants.ALARM_MINUTE_KEY, alarmModel.getMinute());
        intent.putExtra(Constants.ALARM_ENABLED_KEY, alarmModel.isEnabled());

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
