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
        notifyListeners();
    }

    public void updateAlarmState(AlarmModel model, boolean enabled) {
        realm.beginTransaction();
        model.setIsEnabled(enabled);
        realm.commitTransaction();
        notifyListenersStateChange();
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
        notifyListeners();
    }

    public void addListener(OnChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(OnChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (OnChangeListener onChangeListener : listeners) {
            onChangeListener.onDataChanged();
        }
    }

    private void notifyListenersStateChange() {
        for (OnChangeListener onChangeListener : listeners) {
            onChangeListener.onAlarmStateChanged();
        }
    }

    public interface OnChangeListener {
        void onDataChanged();
        void onAlarmStateChanged();
    }
}
