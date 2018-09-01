package lj.com.ljstaysafe.view.recyclerview_adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.model.DeveloperLog;

public class RecyclerViewDeveloperLogAdapter extends RecyclerView.Adapter<RecyclerViewDeveloperLogAdapter.ViewHolder> {

    private List<DeveloperLog> developerLogList;

    @SuppressLint("SimpleDateFormat")
    public RecyclerViewDeveloperLogAdapter(List<DeveloperLog> developerLogList) {
        this.developerLogList = developerLogList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_developer_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeveloperLog developerLog = developerLogList.get(position);
        holder.tvDate.setText(developerLog.getDate());
        holder.tvLogValue.setText(developerLog.getValue());
    }

    @Override
    public int getItemCount() {
        return developerLogList.size();
    }

    static class ViewHolder extends RecyclerViewHomeAdapter.ViewHolder{
        private TextView tvDate, tvLogValue;
        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLogValue = itemView.findViewById(R.id.tvLogValue);
        }
    }
}
