package lj.com.ljstaysafe.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.model.DateConverter;
import lj.com.ljstaysafe.model.DeveloperLog;
import lj.com.ljstaysafe.model.DrivingHistory;
import lj.com.ljstaysafe.model.WhitelistContact;
import lj.com.ljstaysafe.repository.developer.DeveloperLogRepository;
import lj.com.ljstaysafe.repository.driving_history.DrivingHistoryRepository;
import lj.com.ljstaysafe.repository.whitelist_contact.WhitelistContactRepository;

@Database(entities = {WhitelistContact.class, DeveloperLog.class, DrivingHistory.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract WhitelistContactRepository whitelistContactRepository();
    public abstract DeveloperLogRepository developerLogRepository();
    public abstract DrivingHistoryRepository drivingHistoryRepository();
    private static AppDatabase DB_INSTANCE;
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
