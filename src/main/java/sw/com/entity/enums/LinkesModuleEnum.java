package sw.com.entity.enums;

import lombok.Getter;

@Getter
public enum LinkesModuleEnum {
    BLOG("博客"),
    ACTIVITY( "活动");

    private final String value;

    LinkesModuleEnum(String value) {
        this.value = value;
    }

}
