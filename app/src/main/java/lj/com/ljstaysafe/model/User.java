package lj.com.ljstaysafe.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private String id;
    private String fullname;
    private String email;
    private int points;
    private List<Friend> friendList;
}
