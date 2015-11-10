package io.daio.earnthatsnooze.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.daio.earnthatsnooze.models.AlarmModel;
import io.realm.Realm;
import io.realm.RealmResults;

public final class AlarmRepository {

    private static AlarmRepository alarmRepo;
    private static Realm realm;

    private AlarmRepository() {}

    public static void initRepository(@NonNull Realm databaseContext) {
        if (alarmRepo == null){
            realm = databaseContext;
            alarmRepo = new AlarmRepository();
        }
    }

    public static AlarmRepository getInstance() {
        if (alarmRepo == null) {
            throw new Error("Repository has not been initialised. You must call InitRepository first");
        }
        return alarmRepo;
    }

    public void save(AlarmModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public List<AlarmModel> getAll() {
        return realm.where(AlarmModel.class)
                .findAll();
    }

    @Nullable
    public AlarmModel getById(long id) {
        RealmResults<AlarmModel> models = realm.where(AlarmModel.class)
                .equalTo("id", id)
                .findAll();

        if (models.isEmpty()) {
            return null;
        }
        return models.first();
    }

    public void removeById(long id) {
        realm.beginTransaction();
        RealmResults<AlarmModel> results = realm.where(AlarmModel.class)
                .equalTo("id", id)
                .findAll();

        results.clear();
        realm.commitTransaction();
    }
}
