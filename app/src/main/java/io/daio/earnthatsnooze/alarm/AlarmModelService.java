package io.daio.earnthatsnooze.alarm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.daio.earnthatsnooze.models.AlarmDBModel;
import io.daio.earnthatsnooze.repository.AlarmRepository;

public final class AlarmModelService {

    private AlarmRepository alarmRepository;
    private AlarmTransformer alarmTransformer;
    private Set<OnSaveAlarmListener> listeners;

    public AlarmModelService(AlarmRepository alarmRepository,
                             AlarmTransformer alarmTransformer) {
        this.alarmRepository = alarmRepository;
        this.alarmTransformer = alarmTransformer;
        listeners = new HashSet<>();
    }

    public List<Alarm> getAllAlarms() {
        List<AlarmDBModel> alarmDBModels = alarmRepository.getAll();
        return alarmTransformer.transform(alarmDBModels);
    }

    public void saveOrUpdate(Alarm alarm) {
        AlarmDBModel alarmDBModel = alarmTransformer.transform(alarm);
        alarmRepository.save(alarmDBModel);
        notifyListeners(alarm);
    }

    public Alarm createNewAlarm(int hour, int minute) {
        return AlarmFactory.newAlarm(System.currentTimeMillis(), hour, minute);
    }

    public void addListener(OnSaveAlarmListener listener) {
        listeners.add(listener);
    }

    public void removeListener(OnSaveAlarmListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(Alarm alarm) {
        for (OnSaveAlarmListener onChangeListener : listeners) {
            onChangeListener.onAlarmDataChanged(alarm);
        }
    }

    public interface OnSaveAlarmListener {
        void onAlarmDataChanged(Alarm alarm);
    }
}
