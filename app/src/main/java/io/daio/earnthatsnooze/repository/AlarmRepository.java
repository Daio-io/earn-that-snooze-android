package io.daio.earnthatsnooze.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.daio.earnthatsnooze.models.AlarmDBModel;
import io.realm.Realm;
import io.realm.RealmResults;

public final class AlarmRepository {

    private Realm realm;
    private Set<OnChangeListener> listeners;

    public AlarmRepository(@NonNull Realm databaseContext) {
        realm = databaseContext;
        listeners = new HashSet<>();
    }

    public void save(AlarmDBModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
        notifyListeners();
    }

    public List<AlarmDBModel> getAll() {
        return realm.where(AlarmDBModel.class)
                .findAll();
    }

    @Nullable
    public AlarmDBModel getById(long id) {
        RealmResults<AlarmDBModel> models = realm.where(AlarmDBModel.class)
                .equalTo("id", id)
                .findAll();

        if (models.isEmpty()) {
            return null;
        }
        return models.first();
    }

    public void removeById(long id) {
        realm.beginTransaction();
        RealmResults<AlarmDBModel> results = realm.where(AlarmDBModel.class)
                .equalTo("id", id)
                .findAll();

        results.clear();
        realm.commitTransaction();
        notifyListeners();
    }

    public void clearAll() {
        realm.beginTransaction();
        realm.where(AlarmDBModel.class)
                .findAll().clear();
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

    public interface OnChangeListener {
        void onDataChanged();
    }
}
