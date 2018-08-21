package lj.com.ljstaysafe.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.model.DeveloperLog;
import lj.com.ljstaysafe.model.WhitelistContact;
import lj.com.ljstaysafe.repository.developer.DeveloperLogRepository;
import lj.com.ljstaysafe.repository.whitelist_contact.WhitelistContactRepository;

@Database(entities = {WhitelistContact.class, DeveloperLog.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WhitelistContactRepository whitelistContactRepository();
    private static AppDatabase DB_INSTANCE;
    public abstract DeveloperLogRepository developerLogRepository();
    public static AppDatabase getDbInstance(final Context context){
        if(DB_INSTANCE==null){
            synchronized (AppDatabase.class){
                if(DB_INSTANCE==null){
                    DB_INSTANCE = Room.databaseBuilder(context, AppDatabase.class, context.getString(R.string.room_db_name)).build();
                }
            }
        }
        return DB_INSTANCE;
    }
}
