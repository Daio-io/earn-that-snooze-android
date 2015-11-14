package io.daio.earnthatsnooze.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.daio.earnthatsnooze.R;
import io.daio.earnthatsnooze.models.AlarmModel;
import io.daio.earnthatsnooze.repository.AlarmRepository;


public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmListViewHolder> {

    private List<AlarmModel> alarmModels;
    private AlarmRepository alarmRepository;


    public AlarmRecyclerViewAdapter(AlarmRepository alarmRepository) {
        this.alarmModels = alarmRepository.getAll();
        this.alarmRepository = alarmRepository;
    }

    @Override
    public AlarmListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm_list, parent, false);

        return new AlarmListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmListViewHolder holder, final int position) {
        AlarmModel currentAlarm = alarmModels.get(position);
        String displayString = Integer.toString(currentAlarm.getHour())
                + ":" + Integer.toString(currentAlarm.getMinute());
        holder.mAlarmTimeText.setText(displayString);
        holder.mDaysAvailable.setText("mon, tue, fri");
        holder.mEnableSwitch.setChecked(currentAlarm.isEnabled());
        holder.mEnableSwitch.setOnCheckedChangeListener(new AlarmSwitchCheckedListener(currentAlarm, alarmRepository));
    }

    @Override
    public int getItemCount() {
        return alarmModels != null ? alarmModels.size() : 0;
    }


    class AlarmListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.alarmTime) TextView mAlarmTimeText;
        @Bind(R.id.daysText) TextView mDaysAvailable;
        @Bind(R.id.enableSwitch) Switch mEnableSwitch;

        public AlarmListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class AlarmSwitchCheckedListener implements CompoundButton.OnCheckedChangeListener {

        private AlarmModel alarmModel;
        private AlarmRepository alarmRepository;

        public AlarmSwitchCheckedListener(AlarmModel alarmModel, AlarmRepository alarmRepository) {
            this.alarmModel = alarmModel;
            this.alarmRepository = alarmRepository;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (alarmModel != null && alarmModel.isEnabled() != isChecked) {
                alarmRepository.updateAlarmState(alarmModel, isChecked);
            }
        }
    }


}
