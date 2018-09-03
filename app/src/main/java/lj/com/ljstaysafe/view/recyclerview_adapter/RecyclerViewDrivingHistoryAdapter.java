package lj.com.ljstaysafe.view.recyclerview_adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.model.DrivingHistory;

public class RecyclerViewDrivingHistoryAdapter extends RecyclerView.Adapter<RecyclerViewDrivingHistoryAdapter.ViewHolder> {

    private List<DrivingHistory> drivingHistoryList;
    private static SimpleDateFormat SIMPLE_DATE_FORMAT_DAY_MONTH_YEAR, SIMPLE_DATE_FORMAT_HOUR_MINUTE;

    @SuppressLint("SimpleDateFormat")
    public RecyclerViewDrivingHistoryAdapter(List<DrivingHistory> drivingHistoryList) {
        this.drivingHistoryList = drivingHistoryList;
        SIMPLE_DATE_FORMAT_DAY_MONTH_YEAR = new SimpleDateFormat("dd-MM-yyyy");
        SIMPLE_DATE_FORMAT_HOUR_MINUTE = new SimpleDateFormat("HH:mm");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_driving_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrivingHistory drivingHistory = drivingHistoryList.get(position);
        holder.tvOverallScore.setText(String.valueOf(drivingHistory.getOverallScore()));
        holder.tvDate.setText(SIMPLE_DATE_FORMAT_DAY_MONTH_YEAR.format(drivingHistory.getCreatedDate()));
        holder.tvTime.setText(SIMPLE_DATE_FORMAT_HOUR_MINUTE.format(drivingHistory.getCreatedDate()));
        holder.tvSuddenBrakeScore.setText(String.valueOf(drivingHistory.getSuddenBrakeScore()));
        holder.tvTurnScore.setText(String.valueOf(drivingHistory.getTurnScore()));
        holder.tvHonkingScore.setText(String.valueOf(drivingHistory.getHonkingScore()));
        holder.tvHighSpeedScore.setText(String.valueOf(drivingHistory.getHighSpeedScore()));
        holder.tvDistractedScore.setText(String.valueOf(drivingHistory.getDistractedScore()));
    }

    @Override
    public int getItemCount() {
        return drivingHistoryList.size();
    }

    static class ViewHolder extends RecyclerViewHomeAdapter.ViewHolder {
        private TextView tvOverallScore, tvDate, tvTime, tvSuddenBrakeScore, tvTurnScore, tvHonkingScore, tvHighSpeedScore, tvDistractedScore;
        public ViewHolder(View itemView) {
            super(itemView);
            tvOverallScore = itemView.findViewById(R.id.tvOverallScore);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvSuddenBrakeScore = itemView.findViewById(R.id.tvSuddenBrakeScore);
            tvTurnScore = itemView.findViewById(R.id.tvTurnScore);
            tvHonkingScore = itemView.findViewById(R.id.tvHonkingScore);
            tvHighSpeedScore = itemView.findViewById(R.id.tvHighSpeedScore);
            tvDistractedScore = itemView.findViewById(R.id.tvDistractedScore);
        }
    }
}
