package io.daio.earnthatsnooze.alarm;

import android.support.annotation.Nullable;

import java.util.List;

import io.daio.earnthatsnooze.models.AlarmDBModel;
import io.daio.earnthatsnooze.repository.AlarmRepository;

public final class AlarmModelService {

    private AlarmRepository alarmRepository;
    private AlarmTransformer alarmTransformer;

    public AlarmModelService(AlarmRepository alarmRepository,
                             AlarmTransformer alarmTransformer) {
        this.alarmRepository = alarmRepository;
        this.alarmTransformer = alarmTransformer;
    }

    public List<Alarm> getAllAlarms() {
        List<AlarmDBModel> alarmDBModels = alarmRepository.getAll();
        return alarmTransformer.transform(alarmDBModels);
    }

    public void saveOrUpdate(Alarm alarm) {
        AlarmDBModel alarmDBModel = alarmTransformer.transform(alarm);
        alarmRepository.save(alarmDBModel);
    }

    public Alarm createNewAlarm(int hour, int minute) {
        return AlarmFactory.newAlarm(System.currentTimeMillis(), hour, minute);
    }

}
