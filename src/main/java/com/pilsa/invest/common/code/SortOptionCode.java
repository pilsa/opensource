package com.pilsa.invest.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The enum Sort option code.
 *
 * @author pilsa_home1
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SortOptionCode implements EnumCode{

    INCOME("INCOME","01","높은 수익률순"),
    PERIOD("PERIOD","02","짧은 기간순"),
    CLOSING("CLOSING","03","마감임박순");

    private String key;
    private String value;
    private String dec;
}
