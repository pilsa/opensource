package com.pilsa.invest.biz.service.dto;

import lombok.*;

/**
 * The type Member invest info.
 *
 * @author pilsa_home1
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberInvestInfo {

    private int realSumAmt;          /* 회원의 부동산 관련 상품 투자금액 총 합계 */
    private int investPrdCnt;        /* 회원의 특정 상품 정상거래 투자건수 */
    private int totalSumAmt;         /* 회원의 유효거래 중 투자금액 총 합계 */

}
