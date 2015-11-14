package io.daio.earnthatsnooze.managers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;
import java.util.List;

import io.daio.earnthatsnooze.Constants;
import io.daio.earnthatsnooze.models.AlarmModel;
import io.daio.earnthatsnooze.models.WeekDayModel;
import io.daio.earnthatsnooze.repository.AlarmRepository;
import io.daio.earnthatsnooze.services.AlarmService;

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
            if (alarm.isEnabled()){
                PendingIntent pendingIntent = createNewPendingIntent(alarm);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
                calendar.set(Calendar.MINUTE, alarm.getMinute());
                calendar.set(Calendar.SECOND, 0);

                List<WeekDayModel> repeatingDays = alarm.getRepeatingDays();

                for (WeekDayModel weekDayModel : repeatingDays) {

                    if (calendar.get(Calendar.DAY_OF_WEEK) > weekDayModel.getWeekDay()
                            || (calendar.get(Calendar.DAY_OF_WEEK) == weekDayModel.getWeekDay()
                            && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > alarm.getHour()) ){

                        int repeat = calendar.get(Calendar.WEEK_OF_MONTH);
                        calendar.set(Calendar.WEEK_OF_MONTH, repeat++);
                    }
                    calendar.set(Calendar.DAY_OF_WEEK, weekDayModel.getWeekDay());
                }

                setAlarm(calendar, pendingIntent);
            }
        }

    }

    public void cancelAlarms() {
        Iterable<AlarmModel> alarms = alarmRepository.getAll();

        for (AlarmModel alarm : alarms) {

            PendingIntent pendingIntent = createNewPendingIntent(alarm);

            android.app.AlarmManager alarmManager = (android.app.AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

        }

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

    private PendingIntent createNewPendingIntent(AlarmModel alarmModel) {
        Intent intent = new Intent(mContext, AlarmService.class);
        intent.putExtra(Constants.ALARM_ID_KEY, alarmModel.getId());
        intent.putExtra(Constants.ALARM_HOUR_KEY, alarmModel.getHour());
        intent.putExtra(Constants.ALARM_MINUTE_KEY, alarmModel.getMinute());
        intent.putExtra(Constants.ALARM_ENABLED_KEY, alarmModel.isEnabled());

        return PendingIntent.getService(mContext, (int) alarmModel.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
