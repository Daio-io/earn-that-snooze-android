package io.daio.earnthatsnooze.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.daio.earnthatsnooze.R;
import io.daio.earnthatsnooze.models.AlarmModel;


public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmListViewHolder> {

    private List<AlarmModel> alarmModels;

    public AlarmRecyclerViewAdapter(List<AlarmModel> alarmModels){
        this.alarmModels = alarmModels;
    }

    @Override
    public AlarmListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm_list, null);

        return new AlarmListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmListViewHolder holder, int position) {
        holder.mAlarmTimeText.setText("7:50pm");
        holder.mDaysAvailable.setText("mon, tue, fri");
        holder.mEnableSwitch.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return alarmModels != null ? alarmModels.size() : 1;
    }


    public class AlarmListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.alarmTime) TextView mAlarmTimeText;
        @Bind(R.id.enableSwitch) Switch mEnableSwitch;
        @Bind(R.id.daysText) TextView mDaysAvailable;

        public AlarmListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
