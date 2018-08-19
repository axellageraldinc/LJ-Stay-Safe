package lj.com.ljstaysafe.model;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Feed {
    private String id;
    private String ownerId;
    private String ownerFullname;
    private Date publishedDate;
    private String content;
    private List<Comment> commentList;
}
