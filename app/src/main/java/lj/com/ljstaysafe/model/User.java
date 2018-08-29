package lj.com.ljstaysafe.model;

import java.util.List;

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
public class User {
    private String id;
    private String fullname;
    private String email;
    private int points;
    private List<Friend> friendList;
    private String drivingHistoryId;
}
