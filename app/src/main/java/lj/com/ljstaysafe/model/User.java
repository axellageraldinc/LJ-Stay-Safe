package lj.com.ljstaysafe.model;

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
}
