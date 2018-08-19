package lj.com.ljstaysafe.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Comment {
    private String id;
    private String ownerId;
    private String ownerFullname;
    private String content;
}
