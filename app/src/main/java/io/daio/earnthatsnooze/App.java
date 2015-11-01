package io.daio.earnthatsnooze;

import android.app.Application;
import android.content.Context;

import io.daio.earnthatsnooze.repository.AlarmRepository;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate(){
        super.onCreate();

        mContext = getApplicationContext();

        initRepositories();

    }

    public static Context getAppContext() {
        return mContext;
    }

    // Initialise the Realm Database and Repositories
    private void initRepositories() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext)
                .name("ets.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm realm = Realm.getInstance(realmConfig);
        AlarmRepository.initRepository(realm);
    }

}
