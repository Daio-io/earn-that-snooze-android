package io.daio.earnthatsnooze.alarm;

import io.daio.earnthatsnooze.App;
import io.daio.earnthatsnooze.repository.RepositoryFactory;

public final class AlarmFactory {

    private static AlarmModelService alarmModelService;

    public static Alarm newAlarm(long id, int hour, int minute) {
        Alarm alarm = new Alarm();
        alarm.setId(id);
        alarm.setHour(hour);
        alarm.setMinute(minute);
        alarm.setIsEnabled(true);
        return alarm;
    }

    // TODO: remove this - Temp placement while testing
    public static AlarmModelService getAlarmModelService() {
        if (alarmModelService == null) {
            alarmModelService = new AlarmModelService(RepositoryFactory.getAlarmRepository(App.getAppContext()), new AlarmTransformer());
        }
        return alarmModelService;
    }
}
