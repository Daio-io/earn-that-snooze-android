package io.daio.earnthatsnooze.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import io.daio.earnthatsnooze.App;
import io.daio.earnthatsnooze.repository.RepositoryFactory;
import io.daio.earnthatsnooze.services.AlarmCreationService;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private AlarmCreationService alarmCreationService;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        alarmCreationService = new AlarmCreationService(RepositoryFactory.getAlarmRepository(App.getAppContext()));

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        alarmCreationService.createNewAlarm(hourOfDay, minute);
    }
}
