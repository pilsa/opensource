package com.pilsa.invest.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The enum Transaction type code.
 *
 * @author pilsa_home1
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TransactionTypeCode implements EnumCode {

    INVEST("01", "투자"),
    CANCEL("02", "취소");

    private String key;
    private String value;
}
