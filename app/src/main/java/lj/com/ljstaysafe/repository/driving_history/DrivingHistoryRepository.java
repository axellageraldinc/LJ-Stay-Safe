package lj.com.ljstaysafe.repository.driving_history;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import lj.com.ljstaysafe.model.DrivingHistory;

@Dao
public interface DrivingHistoryRepository {
    @Insert
    void saveDrivingHistory(DrivingHistory drivingHistory);
    @Query(value = "SELECT * FROM driving_history")
    List<DrivingHistory> findAllDrivingHistory();
}
