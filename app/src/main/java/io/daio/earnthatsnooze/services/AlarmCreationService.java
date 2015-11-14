package io.daio.earnthatsnooze.services;

import io.daio.earnthatsnooze.models.AlarmModel;
import io.daio.earnthatsnooze.models.WeekDayModel;
import io.daio.earnthatsnooze.repository.AlarmRepository;
import io.realm.RealmList;


public final class AlarmCreationService {

    private AlarmRepository alarmRepository;

    public AlarmCreationService(AlarmRepository alarmRepository) {

        this.alarmRepository = alarmRepository;
    }

    public void createNewAlarm(int hour, int minute){

        AlarmModel newAlarm = new AlarmModel();
        newAlarm.setId(System.currentTimeMillis());
        newAlarm.setHour(hour);
        newAlarm.setMinute(minute);
        newAlarm.setRepeatingDays(new RealmList<WeekDayModel>());
        newAlarm.setIsEnabled(true);

        alarmRepository.save(newAlarm);

    }

}
