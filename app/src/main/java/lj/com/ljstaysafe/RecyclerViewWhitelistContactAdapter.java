package lj.com.ljstaysafe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lj.com.ljstaysafe.model.WhitelistContact;

public class RecyclerViewWhitelistContactAdapter extends RecyclerView.Adapter<RecyclerViewWhitelistContactAdapter.ViewHolder> {

    private List<WhitelistContact> whitelistContactList;

    public RecyclerViewWhitelistContactAdapter(List<WhitelistContact> whitelistContactList) {
        this.whitelistContactList = whitelistContactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_whitelist_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WhitelistContact whitelistContact = whitelistContactList.get(position);
        holder.tvWhitelistContactName.setText(whitelistContact.getName());
        holder.tvWhitelistContactPhoneNumber.setText(whitelistContact.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return whitelistContactList.size();
    }

    static class ViewHolder extends RecyclerViewHomeAdapter.ViewHolder{

        private TextView tvWhitelistContactName, tvWhitelistContactPhoneNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWhitelistContactName = itemView.findViewById(R.id.tvWhitelistContactName);
            tvWhitelistContactPhoneNumber = itemView.findViewById(R.id.tvWhitelistContactPhoneNumber);
        }
    }
}
