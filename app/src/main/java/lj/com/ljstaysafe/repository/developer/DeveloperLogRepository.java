package lj.com.ljstaysafe.repository.developer;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import lj.com.ljstaysafe.model.DeveloperLog;

@Dao
public interface DeveloperLogRepository {
    @Insert
    void saveLog(DeveloperLog developerLog);
    @Query("SELECT * FROM developer_log")
    List<DeveloperLog> findAllLog();
}
