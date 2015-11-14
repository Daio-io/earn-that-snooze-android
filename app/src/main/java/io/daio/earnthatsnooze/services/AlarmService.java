package io.daio.earnthatsnooze.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import io.daio.earnthatsnooze.managers.AlarmManager;
import io.daio.earnthatsnooze.repository.RepositoryFactory;

public class AlarmService extends Service {

    private AlarmManager alarmManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarmManager = new AlarmManager(getApplicationContext(),
                RepositoryFactory.getAlarmRepository(getApplicationContext()));

        System.out.println("ALARMS AND SHIZ");
        alarmManager.setAlarms();
        return super.onStartCommand(intent, flags, startId);
    }

}
