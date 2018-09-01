package lj.com.ljstaysafe.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "driving_history")
public class DrivingHistory {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "sudden_brake_score")
    private String suddenBrakeScore;

    @ColumnInfo(name = "turn_score")
    private String turnScore;

    @ColumnInfo(name = "honking_score")
    private String honkingScore;

    @ColumnInfo(name = "high_speed_score")
    private String highSpeedScore;

    @ColumnInfo(name = "distracted_score")
    private String distractedScore;

    @ColumnInfo(name = "overall_score")
    private String overallScore;
}
