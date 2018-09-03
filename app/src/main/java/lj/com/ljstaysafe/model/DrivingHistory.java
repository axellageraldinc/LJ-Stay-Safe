package lj.com.ljstaysafe.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
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
public class DrivingHistory implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "createdDate")
    @TypeConverters({DateConverter.class})
    private Date createdDate;

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

    protected DrivingHistory(Parcel in) {
        id = in.readString();
        suddenBrakeScore = in.readString();
        turnScore = in.readString();
        honkingScore = in.readString();
        highSpeedScore = in.readString();
        distractedScore = in.readString();
        overallScore = in.readString();
    }

    public static final Creator<DrivingHistory> CREATOR = new Creator<DrivingHistory>() {
        @Override
        public DrivingHistory createFromParcel(Parcel in) {
            return new DrivingHistory(in);
        }

        @Override
        public DrivingHistory[] newArray(int size) {
            return new DrivingHistory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(suddenBrakeScore);
        dest.writeString(turnScore);
        dest.writeString(honkingScore);
        dest.writeString(highSpeedScore);
        dest.writeString(distractedScore);
        dest.writeString(overallScore);
    }
}
