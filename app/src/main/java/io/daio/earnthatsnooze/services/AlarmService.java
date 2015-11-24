package io.daio.earnthatsnooze.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import io.daio.earnthatsnooze.App;
import io.daio.earnthatsnooze.Constants;
import io.daio.earnthatsnooze.R;
import io.daio.earnthatsnooze.alarm.AlarmModelService;
import io.daio.earnthatsnooze.alarm.AlarmTransformer;
import io.daio.earnthatsnooze.managers.AlarmManager;
import io.daio.earnthatsnooze.repository.RepositoryFactory;

public class AlarmService extends Service {

    AlarmManager alarmManager = new AlarmManager(App.getAppContext(),
            new AlarmModelService(RepositoryFactory.getAlarmRepository(App.getAppContext()), new AlarmTransformer()));

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Testing alarms firing off a notification
        int id= intent.getIntExtra(Constants.ALARM_ID_KEY, 0);
        int hour = intent.getIntExtra(Constants.ALARM_HOUR_KEY, 0);
        int minute = intent.getIntExtra(Constants.ALARM_MINUTE_KEY, 0);

        Notification n  = new Notification.Builder(this)
                .setContentTitle("Alarm has Fired!")
                .setContentText("Alarm id: " + id + " has fired at " + hour + ":" + minute)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true).build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

        alarmManager.setAlarms();

        System.out.println("ALARM FIRED!");
        return super.onStartCommand(intent, flags, startId);
    }


}
