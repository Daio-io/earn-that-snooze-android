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
import io.daio.earnthatsnooze.managers.AlarmManager;
import io.daio.earnthatsnooze.repository.AlarmRepository;
import io.daio.earnthatsnooze.repository.RepositoryFactory;
import io.daio.earnthatsnooze.views.adapters.AlarmRecyclerViewAdapter;


public class AlarmListFragment extends Fragment implements AlarmRepository.OnChangeListener {

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    private AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
    private AlarmManager alarmManager;
    private AlarmRepository alarmRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmRepository = RepositoryFactory.getAlarmRepository(App.getAppContext());
        alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(alarmRepository);
        alarmManager = new AlarmManager(App.getAppContext(), alarmRepository);
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

    @Override
    public void onDataChanged() {
        alarmRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAlarmStateChanged() {

    }

    private void addListeners() {
        alarmRepository.addListener(this);
        alarmRepository.addListener(alarmManager);
    }

    private void removeListeners() {
        alarmRepository.removeListener(this);
        alarmRepository.removeListener(alarmManager);
    }
}
