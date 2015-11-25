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
import io.daio.earnthatsnooze.alarm.Alarm;
import io.daio.earnthatsnooze.alarm.AlarmModelService;


public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmListViewHolder> {

    private List<Alarm> alarms;
    private AlarmModelService alarmModelService;

    public AlarmRecyclerViewAdapter(AlarmModelService alarmModelService) {
        this.alarmModelService = alarmModelService;
        this.alarms = alarmModelService.getAllAlarms();
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    @Override
    public AlarmListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm_list, parent, false);

        return new AlarmListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmListViewHolder holder, final int position) {
        Alarm currentAlarm = alarms.get(position);
        String displayString = Integer.toString(currentAlarm.getHour())
                + ":" + Integer.toString(currentAlarm.getMinute());
        holder.mAlarmTimeText.setText(displayString);
        holder.mDaysAvailable.setText("mon, tue, fri");
        holder.mEnableSwitch.setChecked(currentAlarm.isEnabled());
        holder.mEnableSwitch.setOnCheckedChangeListener(new AlarmSwitchCheckedHandler(currentAlarm, alarmModelService));
    }

    @Override
    public int getItemCount() {
        return alarms != null ? alarms.size() : 0;
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

    class AlarmSwitchCheckedHandler implements CompoundButton.OnCheckedChangeListener {

        private final AlarmModelService alarmModelService;
        private Alarm alarm;

        public AlarmSwitchCheckedHandler(Alarm alarm, AlarmModelService alarmModelService) {
            this.alarm = alarm;
            this.alarmModelService = alarmModelService;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (alarm != null && alarm.isEnabled() != isChecked) {
                alarm.setIsEnabled(isChecked);
                alarmModelService.saveOrUpdate(alarm);
            }
        }
    }


}
