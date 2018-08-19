package lj.com.ljstaysafe.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Friend {
    private String userId;
    private String userFullname;
}
