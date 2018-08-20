package lj.com.ljstaysafe.repository.whitelist_contact;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.model.WhitelistContact;

@Database(entities = {WhitelistContact.class}, version = 1)
public abstract class WhitelistContactDatabase extends RoomDatabase {
    public abstract WhitelistContactRepository whitelistContactRepository();
    private static WhitelistContactDatabase DB_INSTANCE;
    public static WhitelistContactDatabase getDbInstance(final Context context){
        if(DB_INSTANCE==null){
            synchronized (WhitelistContactDatabase.class){
                if(DB_INSTANCE==null){
                    DB_INSTANCE = Room.databaseBuilder(context, WhitelistContactDatabase.class, context.getString(R.string.room_db_name)).build();
                }
            }
        }
        return DB_INSTANCE;
    }
}
