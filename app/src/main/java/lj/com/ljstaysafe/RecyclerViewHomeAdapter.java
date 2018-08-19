package lj.com.ljstaysafe;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import lj.com.ljstaysafe.model.Feed;

public class RecyclerViewHomeAdapter extends RecyclerView.Adapter<RecyclerViewHomeAdapter.ViewHolder> {

    private List<Feed> feedList;
    private static final String DATE_PATTERN = "dd-MM-yyyy";
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);

    public RecyclerViewHomeAdapter(List<Feed> feedList) {
        this.feedList = feedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feed feed = feedList.get(position);
        holder.tvId.setText(feed.getId());
        holder.tvOwnerId.setText(feed.getOwnerId());
        holder.tvOwnerFullname.setText(feed.getOwnerFullname());
        holder.tvPublishedDate.setText(SIMPLE_DATE_FORMAT.format(feed.getPublishedDate()));
        holder.tvContent.setText(feed.getContent());
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvId, tvOwnerId, tvOwnerFullname, tvPublishedDate, tvContent;
        private ImageView ivLove, ivComment;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvOwnerId = itemView.findViewById(R.id.tvOwnerId);
            tvOwnerFullname = itemView.findViewById(R.id.tvOwnerFullname);
            tvPublishedDate = itemView.findViewById(R.id.tvPublishedDate);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivLove = itemView.findViewById(R.id.ivLove);
            ivComment = itemView.findViewById(R.id.ivComment);
        }
    }
}
