package io.daio.earnthatsnooze.repository;

import android.support.annotation.Nullable;

import io.daio.earnthatsnooze.App;
import io.daio.earnthatsnooze.models.AlarmModel;
import io.realm.Realm;
import io.realm.RealmResults;

public final class AlarmRepository {

    private static AlarmRepository alarmRepo;
    private Realm realm = Realm.getInstance(App.getAppContext());

    private AlarmRepository() {}

    public static AlarmRepository getInstance() {
        if (alarmRepo == null) {
            alarmRepo = new AlarmRepository();
        }
        return alarmRepo;
    }

    public void save(AlarmModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public Iterable<AlarmModel> getAll() {
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
