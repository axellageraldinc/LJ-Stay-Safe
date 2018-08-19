package lj.com.ljstaysafe.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DrivingHistory {
    private String id;
    private String ownerId;
    private Date date;
    private Double suddenBrakeScore;
    private Double turnScore;
    private Double honkingScore;
    private Double highSpeedScore;
    private Double distractedScore;
    private Double overallScore;
}
