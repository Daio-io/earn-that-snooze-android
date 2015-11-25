package io.daio.earnthatsnooze.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import io.daio.earnthatsnooze.alarm.Alarm;
import io.daio.earnthatsnooze.alarm.AlarmFactory;
import io.daio.earnthatsnooze.alarm.AlarmModelService;
import io.daio.earnthatsnooze.alarm.WeekDay;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private AlarmModelService alarmModelService;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        alarmModelService = AlarmFactory.getAlarmModelService();

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Alarm alarm = alarmModelService.createNewAlarm(hourOfDay, minute);
        alarm.addRepeatingDay(WeekDay.WEDNESDAY);
        alarmModelService.saveOrUpdate(alarm);
    }
}
