package com.pilsa.invest.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.EnumSet;

/**
 * The enum Product status code.
 *
 * @author pilsa_home1
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductStatusCode implements EnumCode{

    INITIAL("01", "초기상태"),
    RECRUIT_ING("02", "모집중"),
    RECRUIT_END("03", "모집완료"),
    REPAY("04", "상환중"),
    COMPLETE("05", "상환완료"),
    END("99", "종료")
    ;

    private String key;
    private String value;

    /**
     * Get code by string product status code.
     *
     * @param key the key
     * @return the product status code
     */
    public static ProductStatusCode getCodeByString(String key){
        if (StringUtils.isEmpty(key)) return null;
        return EnumSet.allOf(ProductStatusCode.class).stream()
                .filter(e -> e.getKey().equals(key)).findAny().orElse(null);
    }
}
