package lj.com.ljstaysafe.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.FriendsContract;

public class AddFriendDialog extends DialogFragment implements Button.OnClickListener {

    private EditText etFriendEmail;
    private Button btnAddFriend;

    private static FriendsContract.Presenter presenter;

    public AddFriendDialog() {
    }

    public static AddFriendDialog newInstance(FriendsContract.Presenter presenter){
        AddFriendDialog addFriendDialog = new AddFriendDialog();
        addFriendDialog.setStyle(STYLE_NO_TITLE, 0);
        AddFriendDialog.presenter = presenter;
        return addFriendDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_new_friend, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etFriendEmail = view.findViewById(R.id.etFriendEmail);
        btnAddFriend = view.findViewById(R.id.btnAddFriend);
        btnAddFriend.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = Objects.requireNonNull(getDialog().getWindow()).getAttributes();
        params.width = (getResources().getDisplayMetrics().widthPixels);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddFriend:
                presenter.saveNewFriend(etFriendEmail.getText().toString());
                etFriendEmail.setText("");
                dismiss();
                break;
        }
    }
}
