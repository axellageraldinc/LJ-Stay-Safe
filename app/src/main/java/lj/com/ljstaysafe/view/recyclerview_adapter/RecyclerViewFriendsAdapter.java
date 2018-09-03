package lj.com.ljstaysafe.view.recyclerview_adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.model.Friend;

public class RecyclerViewFriendsAdapter extends RecyclerView.Adapter<RecyclerViewFriendsAdapter.ViewHolder> {

    private List<Friend> friendList;

    public RecyclerViewFriendsAdapter(List<Friend> friendList) {
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend friend = friendList.get(position);
        holder.tvUserId.setText(friend.getFriendId());
        holder.tvFriendFullname.setText(friend.getFriendFullname());
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    static class ViewHolder extends RecyclerViewHomeAdapter.ViewHolder{

        private TextView tvUserId, tvFriendFullname;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tvUserId);
            tvFriendFullname = itemView.findViewById(R.id.tvFriendFullname);
        }
    }
}
