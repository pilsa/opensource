package com.pilsa.invest.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The enum Parameter type code.
 *
 * @author pilsa_home1
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ParameterTypeCode{

    NUMBER("NUMBER", "정수형 숫자"),
    DECIMAL("DECIMAL", "소수형 숫자"),
    DATE("DATE", "날짜(YYYYMMDD)"),
    DATETIME("DATETIME", "날짜 및 시분초 (YYYYMMDDhhmmss)"),
    STRING("STRING", "문자열"),
    BOOLEAN("BOOLEAN", "Boolean (true/false)")
    ;

    private String type;
    private String desc;

}
