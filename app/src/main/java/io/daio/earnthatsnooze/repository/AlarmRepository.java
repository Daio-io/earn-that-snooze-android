package io.daio.earnthatsnooze.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.daio.earnthatsnooze.models.AlarmModel;
import io.realm.Realm;
import io.realm.RealmResults;

public final class AlarmRepository {

    private static Realm realm;
    private static Set<OnChangeListener> listeners;

    public AlarmRepository(@NonNull Realm databaseContext) {
        realm = databaseContext;
        listeners = new HashSet<>();
    }

    public void save(AlarmModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
        notifyConsumers();
    }

    public void enableAlarm(AlarmModel model, boolean enable) {
        realm.beginTransaction();
        model.setIsEnabled(enable);
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

    @Nullable
    public AlarmModel getByName(String name) {
        RealmResults<AlarmModel> models = realm.where(AlarmModel.class)
                .equalTo("alarmName", name)
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
        notifyConsumers();
    }

    public void addListener(OnChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(OnChangeListener listener) {
        listeners.remove(listener);
    }


    private void notifyConsumers() {
        for (OnChangeListener onChangeListener : listeners) {
            onChangeListener.onDataChanged();
        }
    }

    public interface OnChangeListener {
        void onDataChanged();
    }
}
