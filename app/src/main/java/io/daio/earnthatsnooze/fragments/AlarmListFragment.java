package io.daio.earnthatsnooze.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.daio.earnthatsnooze.App;
import io.daio.earnthatsnooze.R;
import io.daio.earnthatsnooze.alarm.Alarm;
import io.daio.earnthatsnooze.alarm.AlarmFactory;
import io.daio.earnthatsnooze.alarm.AlarmModelService;
import io.daio.earnthatsnooze.managers.AlarmManager;
import io.daio.earnthatsnooze.views.adapters.AlarmRecyclerViewAdapter;


public class AlarmListFragment extends Fragment implements AlarmModelService.OnSaveAlarmListener {

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    private AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
    private AlarmManager alarmManager;
    private AlarmModelService alarmModelService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmModelService = AlarmFactory.getAlarmModelService();
        alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(AlarmFactory.getAlarmModelService());
        alarmManager = new AlarmManager(App.getAppContext(), AlarmFactory.getAlarmModelService());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(App.getAppContext()));
        mRecyclerView.setAdapter(alarmRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        addListeners();
    }

    @Override
    public void onPause() {
        super.onPause();
        removeListeners();
    }

    @Override
    public void onStop() {
        super.onStop();
        removeListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeListeners();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        removeListeners();
    }

    private void addListeners() {
        alarmModelService.addListener(this);
        alarmModelService.addListener(alarmManager);
    }

    private void removeListeners() {
        alarmModelService.removeListener(this);
        alarmModelService.removeListener(alarmManager);
    }

    @Override
    public void onAlarmDataChanged(Alarm alarm) {
        alarmRecyclerViewAdapter.setAlarms(alarmModelService.getAllAlarms());
    }
}
