package io.daio.earnthatsnooze.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import io.daio.earnthatsnooze.managers.ETSAlarmManager;

public class ETSAlarmService extends Service {

    ETSAlarmManager alarmManager = new ETSAlarmManager();

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
