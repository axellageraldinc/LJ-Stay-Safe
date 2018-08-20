package lj.com.ljstaysafe.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageSetting {
    private Boolean isAutoReplySms;
    private String message;
}
