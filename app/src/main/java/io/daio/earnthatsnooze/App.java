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
        
    }

    public static Context getAppContext() {
        return mContext;
    }


}
