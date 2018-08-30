package lj.com.ljstaysafe.model;

import java.util.Date;
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
public class Feed {
    private String id;
    private String ownerId;
    private String ownerFullname;
    private Date publishedDate;
    private String content;
}
