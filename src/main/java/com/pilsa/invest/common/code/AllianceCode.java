package com.pilsa.invest.common.code;

import lombok.Builder;
import lombok.Getter;


/**
 * The type Alliance code.
 *
 * @author pilsa_home1
 */
@Getter
@Builder
public class AllianceCode implements EnumCode {

    private String key;                     /* 투자연계금융기관코드 */
    private String value;                   /* 투자연계금융기관명 */
    private YesNoCode onlineInvestLawYn;    /* 온투업기관여부 */
    private int totalLimitAmount;           /* 전체한도금액 */
    private int onecLimitAmount;            /* 1회투자한도금액 */
    private int realtyLimitAmount;          /* 부동산투자한도금액 */
    private int creditLimitAmount;          /* 동일차주한도금액 */

}
