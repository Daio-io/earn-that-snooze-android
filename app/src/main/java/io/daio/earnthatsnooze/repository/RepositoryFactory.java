package io.daio.earnthatsnooze.repository;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RepositoryFactory {

    private static AlarmRepository alarmRepository;

    public static AlarmRepository getAlarmRepository(Context context){
        if (alarmRepository == null){
            RealmConfiguration realmConfig = new RealmConfiguration.Builder(context)
                    .name("ets.realm")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();

            Realm realm = Realm.getInstance(realmConfig);
            alarmRepository = new AlarmRepository(realm);
        }
        return alarmRepository;
    }
}
