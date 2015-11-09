package io.daio.earnthatsnooze.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import io.daio.earnthatsnooze.managers.AlarmManager;

public class AlarmService extends Service {

    AlarmManager alarmManager = new AlarmManager();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarmManager.setAlarms(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

}
