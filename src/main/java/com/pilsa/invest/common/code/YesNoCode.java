package com.pilsa.invest.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.EnumSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum YesNoCode implements EnumCode{

    Y("Y", "Yes", 1, true),
    N("N", "No", 0, false);

    private String key;
    private String value;
    private int code;
    private boolean bool;

    public static YesNoCode getYesNoCodeByBool(boolean bool){
        return EnumSet.allOf(YesNoCode.class).stream()
                .filter(e -> e.isBool() == bool).findAny().orElse(null);
    }

    public static YesNoCode getYesNoCodeByString(String bool){
        if (StringUtils.isEmpty(bool)) return null;
        return EnumSet.allOf(YesNoCode.class).stream()
                .filter(e -> e.isBool() == Boolean.valueOf(bool)).findAny().orElse(null);
    }

    public static String getStringByYesNoCode(YesNoCode yesNoCode){
        if (StringUtils.isEmpty(yesNoCode)) return null;
        return EnumSet.allOf(YesNoCode.class).stream()
                .filter(e -> e == yesNoCode).findAny().get().getKey();
    }
}
