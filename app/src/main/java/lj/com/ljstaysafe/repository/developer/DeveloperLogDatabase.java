package lj.com.ljstaysafe.repository.developer;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.model.DeveloperLog;

@Database(entities = {DeveloperLog.class}, version = 1)
public abstract class DeveloperLogDatabase extends RoomDatabase {
    public abstract DeveloperLogRepository developerLogRepository();
    private static DeveloperLogDatabase DB_INSTANCE;
    public static DeveloperLogDatabase getDbInstance(final Context context){
        if(DB_INSTANCE==null){
            synchronized (DeveloperLogDatabase.class){
                if(DB_INSTANCE==null){
                    DB_INSTANCE = Room.databaseBuilder(context, DeveloperLogDatabase.class, context.getString(R.string.room_db_name)).build();
                }
            }
        }
        return DB_INSTANCE;
    }
}
