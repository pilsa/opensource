package com.pilsa.invest.biz.service.dto;

import com.pilsa.invest.common.code.YesNoCode;
import lombok.*;

/**
 * The type Invest alliance dto.
 *
 * @author pilsa_home1
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvestAllianceDTO {

    private String allianceCode;            /* 투자연계금융기관코드 */
    private String allianceNm;              /* 투자연계금융기관명 */
    private YesNoCode onlineInvestLawYn;    /* 온투업기관여부 */
    private int totalLimitAmount;           /* 전체한도금액 */
    private int onecLimitAmount;            /* 1회투자한도금액 */
    private int realtyLimitAmount;          /* 부동산투자한도금액 */
    private int creditLimitAmount;          /* 동일차주한도금액 */
    private YesNoCode useYn;                /* 사용여부 */

}
