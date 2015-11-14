package io.daio.earnthatsnooze.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.daio.earnthatsnooze.R;
import io.daio.earnthatsnooze.fragments.TimePickerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_alarm_fab)
    public void showTimerPickerFragement() {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getFragmentManager(), "ADD_ALARM");
    }

}
