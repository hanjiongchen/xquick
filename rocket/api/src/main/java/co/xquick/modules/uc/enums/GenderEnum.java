package co.xquick.modules.uc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 性别
 *
 * @author Charles zhanchaoxu@gmail.com
 */
public enum GenderEnum {

    MALE(0, "男"),
    FEMALE(1, "女"),
    UNKNOWN(2, "未知");

    GenderEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String name;

}
