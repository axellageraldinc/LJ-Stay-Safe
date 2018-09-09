package lj.com.ljstaysafe.repository.audio;

import android.content.Context;
import android.content.SharedPreferences;

import lj.com.ljstaysafe.R;

public class AudioRepositoryImpl implements AudioRepository {

    private Context context;
    private SharedPreferences sharedPreferences;

    public AudioRepositoryImpl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(AudioRepositoryImpl.class.getName(), Context.MODE_PRIVATE);
    }

    @Override
    public void saveRingerMode(int ringerMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.ringer_mode), ringerMode);
        editor.apply();
    }

    @Override
    public int getSavedRingerMode() {
        return sharedPreferences.getInt(context.getString(R.string.ringer_mode), 0);
    }
}
