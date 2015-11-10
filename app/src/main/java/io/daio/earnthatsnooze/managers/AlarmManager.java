package io.daio.earnthatsnooze.managers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
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

public class AlarmManager extends BroadcastReceiver {

    private static AlarmRepository alarmRepository = AlarmRepository.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    public void setAlarms(Context context) {
        cancelAlarms(context);

        Iterable<AlarmModel> alarms = alarmRepository.getAll();

        for (AlarmModel alarm : alarms) {
            PendingIntent pendingIntent = createNewPendingIntent(context, alarm);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
            calendar.set(Calendar.SECOND, 0);

            // TODO loop repeating days and set alarms
            List<WeekDayModel> repeatingDays = alarm.getRepeatingDays();

            setAlarm(context, calendar, pendingIntent);
        }

    }

    public void cancelAlarms(Context context) {
        Iterable<AlarmModel> alarms = alarmRepository.getAll();

        for (AlarmModel alarm : alarms) {
            if (alarm.isEnabled()) {

                PendingIntent pendingIntent = createNewPendingIntent(context, alarm);

                android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            }
        }

    }

    private void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);

        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);

        } else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }

    private PendingIntent createNewPendingIntent(Context context, AlarmModel alarmModel) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(Constants.ALARM_ID_KEY, alarmModel.getId());
        intent.putExtra(Constants.ALARM_HOUR_KEY, alarmModel.getHour());
        intent.putExtra(Constants.ALARM_MINUTE_KEY, alarmModel.getMinute());
        intent.putExtra(Constants.ALARM_ENABLED_KEY, alarmModel.isEnabled());

        return PendingIntent.getService(context, (int) alarmModel.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

}